package com.chengkun.agenthub.service.impl;

import com.alibaba.fastjson.JSON;
import com.chengkun.agenthub.model.dto.*;
import com.chengkun.agenthub.entity.Article;
import com.chengkun.agenthub.entity.Comment;
import com.chengkun.agenthub.entity.Talk;
import com.chengkun.agenthub.entity.UserInfo;
import com.chengkun.agenthub.enums.CommentTypeEnum;
import com.chengkun.agenthub.exception.BizException;
import com.chengkun.agenthub.mapper.ArticleMapper;
import com.chengkun.agenthub.mapper.CommentMapper;
import com.chengkun.agenthub.mapper.TalkMapper;
import com.chengkun.agenthub.mapper.UserInfoMapper;
import com.chengkun.agenthub.service.AuroraInfoService;
import com.chengkun.agenthub.service.CommentService;
import com.chengkun.agenthub.util.HTMLUtil;
import com.chengkun.agenthub.util.PageUtil;
import com.chengkun.agenthub.util.UserUtil;
import com.chengkun.agenthub.model.vo.CommentVO;
import com.chengkun.agenthub.model.vo.ConditionVO;
import com.chengkun.agenthub.model.dto.PageResultDTO;
import com.chengkun.agenthub.model.vo.ReviewVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.SneakyThrows;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.chengkun.agenthub.constant.CommonConstant.*;
import static com.chengkun.agenthub.constant.RabbitMQConstant.EMAIL_EXCHANGE;
import static com.chengkun.agenthub.enums.CommentTypeEnum.*;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Value("${website.url}")
    private String websiteUrl;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private TalkMapper talkMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private AuroraInfoService auroraInfoService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final List<Integer> types = new ArrayList<>();

    @PostConstruct
    public void init() {
        CommentTypeEnum[] values = CommentTypeEnum.values();
        for (CommentTypeEnum value : values) {
            types.add(value.getType());
        }
    }

    @Override
    public void saveComment(CommentVO commentVO) {
        checkCommentVO(commentVO);
        WebsiteConfigDTO websiteConfig = auroraInfoService.getWebsiteConfig();
        Integer isCommentReview = websiteConfig.getIsCommentReview();
        commentVO.setCommentContent(HTMLUtil.filter(commentVO.getCommentContent()));
        Comment comment = Comment.builder()
                .userId(UserUtil.getUserDetailsDTO().getUserInfoId())
                .replyUserId(commentVO.getReplyUserId())
                .topicId(commentVO.getTopicId())
                .commentContent(commentVO.getCommentContent())
                .parentId(commentVO.getParentId())
                .type(commentVO.getType())
                .isReview(isCommentReview == TRUE ? FALSE : TRUE)
                .build();
        commentMapper.insert(comment);
        String fromNickname = UserUtil.getUserDetailsDTO().getNickname();
        if (websiteConfig.getIsEmailNotice().equals(TRUE)) {
            CompletableFuture.runAsync(() -> notice(comment, fromNickname));
        }
    }

    @Override
    public PageResultDTO<CommentDTO> listComments(CommentVO commentVO) {
        Integer commentCount = commentMapper.selectCount(new LambdaQueryWrapper<Comment>()
                .eq(Objects.nonNull(commentVO.getTopicId()), Comment::getTopicId, commentVO.getTopicId())
                .eq(Comment::getType, commentVO.getType())
                .isNull(Comment::getParentId)
                .eq(Comment::getIsReview, TRUE)).intValue();
        if (commentCount == 0) {
            return new PageResultDTO<>();
        }
        List<CommentDTO> commentDTOs = commentMapper.listComments(PageUtil.getLimitCurrent(), PageUtil.getSize(), commentVO);
        if (CollectionUtils.isEmpty(commentDTOs)) {
            return new PageResultDTO<>();
        }
        List<Integer> commentIds = commentDTOs.stream()
                .map(CommentDTO::getId)
                .collect(Collectors.toList());
        List<ReplyDTO> replyDTOS = commentMapper.listReplies(commentIds);
        Map<Integer, List<ReplyDTO>> replyMap = replyDTOS.stream()
                .collect(Collectors.groupingBy(ReplyDTO::getParentId));
        commentDTOs.forEach(item -> item.setReplyDTOs(replyMap.get(item.getId())));
        return new PageResultDTO<>(commentDTOs, commentCount);
    }

    @Override
    public List<ReplyDTO> listRepliesByCommentId(Integer commentId) {
        return commentMapper.listReplies(Collections.singletonList(commentId));
    }

    @Override
    public List<CommentDTO> listTopSixComments() {
        return commentMapper.listTopSixComments();
    }

    @SneakyThrows
    @Override
    public PageResultDTO<CommentAdminDTO> listCommentsAdmin(ConditionVO conditionVO) {
        CompletableFuture<Integer> asyncCount = CompletableFuture.supplyAsync(() -> commentMapper.countComments(conditionVO));
        List<CommentAdminDTO> commentBackDTOList = commentMapper.listCommentsAdmin(PageUtil.getLimitCurrent(), PageUtil.getSize(), conditionVO);
        return new PageResultDTO<>(commentBackDTOList, asyncCount.get());
    }

    @Override
    public void updateCommentsReview(ReviewVO reviewVO) {
        List<Comment> comments = reviewVO.getIds().stream().map(item -> Comment.builder()
                        .id(item)
                        .isReview(reviewVO.getIsReview())
                        .build())
                .collect(Collectors.toList());
        this.updateBatchById(comments);
    }

    public void checkCommentVO(CommentVO commentVO) {
        if (!types.contains(commentVO.getType())) {
            throw new BizException("参数校验异常");
        }
        if (Objects.requireNonNull(getCommentEnum(commentVO.getType())) == ARTICLE || Objects.requireNonNull(getCommentEnum(commentVO.getType())) == TALK) {
            if (Objects.isNull(commentVO.getTopicId())) {
                throw new BizException("参数校验异常");
            } else {
                if (Objects.requireNonNull(getCommentEnum(commentVO.getType())) == ARTICLE) {
                    Article article = articleMapper.selectOne(new LambdaQueryWrapper<Article>().select(Article::getId, Article::getUserId).eq(Article::getId, commentVO.getTopicId()));
                    if (Objects.isNull(article)) {
                        throw new BizException("参数校验异常");
                    }
                }
                if (Objects.requireNonNull(getCommentEnum(commentVO.getType())) == TALK) {
                    Talk talk = talkMapper.selectOne(new LambdaQueryWrapper<Talk>().select(Talk::getId, Talk::getUserId).eq(Talk::getId, commentVO.getTopicId()));
                    if (Objects.isNull(talk)) {
                        throw new BizException("参数校验异常");
                    }
                }
            }
        }
        if (Objects.requireNonNull(getCommentEnum(commentVO.getType())) == LINK
                || Objects.requireNonNull(getCommentEnum(commentVO.getType())) == ABOUT
                || Objects.requireNonNull(getCommentEnum(commentVO.getType())) == MESSAGE) {
            if (Objects.nonNull(commentVO.getTopicId())) {
                throw new BizException("参数校验异常");
            }
        }
        if (Objects.isNull(commentVO.getParentId())) {
            if (Objects.nonNull(commentVO.getReplyUserId())) {
                throw new BizException("参数校验异常");
            }
        }
        if (Objects.nonNull(commentVO.getParentId())) {
            Comment parentComment = commentMapper.selectOne(new LambdaQueryWrapper<Comment>().select(Comment::getId, Comment::getParentId, Comment::getType).eq(Comment::getId, commentVO.getParentId()));
            if (Objects.isNull(parentComment)) {
                throw new BizException("参数校验异常");
            }
            if (Objects.nonNull(parentComment.getParentId())) {
                throw new BizException("参数校验异常");
            }
            if (!commentVO.getType().equals(parentComment.getType())) {
                throw new BizException("参数校验异常");
            }
            if (Objects.isNull(commentVO.getReplyUserId())) {
                throw new BizException("参数校验异常");
            } else {
                UserInfo existUser = userInfoMapper.selectOne(new LambdaQueryWrapper<UserInfo>().select(UserInfo::getId).eq(UserInfo::getId, commentVO.getReplyUserId()));
                if (Objects.isNull(existUser)) {
                    throw new BizException("参数校验异常");
                }
            }
        }
    }

    private void notice(Comment comment, String fromNickname) {
        if (comment.getUserId().equals(comment.getReplyUserId())) {
            if (Objects.nonNull(comment.getParentId())) {
                Comment parentComment = commentMapper.selectById(comment.getParentId());
                if (parentComment.getUserId().equals(comment.getUserId())) {
                    return;
                }
            }
        }
        if (comment.getUserId().equals(BLOGGER_ID) && Objects.isNull(comment.getParentId())) {
            return;
        }
        if (Objects.nonNull(comment.getParentId())) {
            Comment parentComment = commentMapper.selectById(comment.getParentId());
            if (!comment.getReplyUserId().equals(parentComment.getUserId())
                    && !comment.getReplyUserId().equals(comment.getUserId())) {
                UserInfo userInfo = userInfoMapper.selectById(comment.getUserId());
                UserInfo replyUserinfo = userInfoMapper.selectById(comment.getReplyUserId());
                Map<String, Object> map = new HashMap<>();
                String topicId = Objects.nonNull(comment.getTopicId()) ? comment.getTopicId().toString() : "";
                String url = websiteUrl + getCommentPath(comment.getType()) + topicId;
                map.put("content", userInfo.getNickname() + "在" + Objects.requireNonNull(getCommentEnum(comment.getType())).getDesc()
                        + "的评论区@了你，"
                        + "<a style=\"text-decoration:none;color:#12addb\" href=\"" + url + "\">点击查看</a>");
                EmailDTO emailDTO = EmailDTO.builder()
                        .email(replyUserinfo.getEmail())
                        .subject(MENTION_REMIND)
                        .template("common.html")
                        .commentMap(map)
                        .build();
                rabbitTemplate.convertAndSend(EMAIL_EXCHANGE, "*", new Message(JSON.toJSONBytes(emailDTO), new MessageProperties()));
            }
            if (comment.getUserId().equals(parentComment.getUserId())) {
                return;
            }
        }
        String title;
        Integer userId = BLOGGER_ID;
        String topicId = Objects.nonNull(comment.getTopicId()) ? comment.getTopicId().toString() : "";
        if (Objects.nonNull(comment.getReplyUserId())) {
            userId = comment.getReplyUserId();
        } else {
            switch (Objects.requireNonNull(getCommentEnum(comment.getType()))) {
                case ARTICLE:
                    userId = articleMapper.selectById(comment.getTopicId()).getUserId();
                    break;
                case TALK:
                    userId = talkMapper.selectById(comment.getTopicId()).getUserId();
                default:
                    break;
            }
        }
        if (Objects.requireNonNull(getCommentEnum(comment.getType())).equals(ARTICLE)) {
            title = articleMapper.selectById(comment.getTopicId()).getArticleTitle();
        } else {
            title = Objects.requireNonNull(getCommentEnum(comment.getType())).getDesc();
        }
        UserInfo userInfo = userInfoMapper.selectById(userId);
        if (StringUtils.isNotBlank(userInfo.getEmail())) {
            EmailDTO emailDTO = getEmailDTO(comment, userInfo, fromNickname, topicId, title);
            rabbitTemplate.convertAndSend(EMAIL_EXCHANGE, "*", new Message(JSON.toJSONBytes(emailDTO), new MessageProperties()));
        }
    }

    private EmailDTO getEmailDTO(Comment comment, UserInfo userInfo, String fromNickname, String topicId, String title) {
        EmailDTO emailDTO = new EmailDTO();
        Map<String, Object> map = new HashMap<>();
        if (comment.getIsReview().equals(TRUE)) {
            String url = websiteUrl + getCommentPath(comment.getType()) + topicId;
            if (Objects.isNull(comment.getParentId())) {
                emailDTO.setEmail(userInfo.getEmail());
                emailDTO.setSubject(COMMENT_REMIND);
                emailDTO.setTemplate("owner.html");
                String createTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(comment.getCreateTime());
                map.put("time", createTime);
                map.put("url", url);
                map.put("title", title);
                map.put("nickname", fromNickname);
                map.put("content", comment.getCommentContent());
            } else {
                Comment parentComment = commentMapper.selectOne(new LambdaQueryWrapper<Comment>().select(Comment::getUserId, Comment::getCommentContent, Comment::getCreateTime).eq(Comment::getId, comment.getParentId()));
                if (!userInfo.getId().equals(parentComment.getUserId())) {
                    userInfo = userInfoMapper.selectById(parentComment.getUserId());
                }
                emailDTO.setEmail(userInfo.getEmail());
                emailDTO.setSubject(COMMENT_REMIND);
                emailDTO.setTemplate("user.html");
                map.put("url", url);
                map.put("title", title);
                String createTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(parentComment.getCreateTime());
                map.put("time", createTime);
                map.put("toUser", userInfo.getNickname());
                map.put("fromUser", fromNickname);
                map.put("parentComment", parentComment.getCommentContent());
                if (!comment.getReplyUserId().equals(parentComment.getUserId())) {
                    UserInfo mentionUserInfo = userInfoMapper.selectById(comment.getReplyUserId());
                    if (Objects.nonNull(mentionUserInfo.getWebsite())) {
                        map.put("replyComment", "<a style=\"text-decoration:none;color:#12addb\" href=\""
                                + mentionUserInfo.getWebsite()
                                + "\">@" + mentionUserInfo.getNickname() + " " + "</a>" + parentComment.getCommentContent());
                    } else {
                        map.put("replyComment", "@" + mentionUserInfo.getNickname() + " " + parentComment.getCommentContent());
                    }
                } else {
                    map.put("replyComment", comment.getCommentContent());
                }
            }
        } else {
            String adminEmail = userInfoMapper.selectById(BLOGGER_ID).getEmail();
            emailDTO.setEmail(adminEmail);
            emailDTO.setSubject(CHECK_REMIND);
            emailDTO.setTemplate("common.html");
            map.put("content", "您收到了一条新的回复，请前往后台管理页面审核");
        }
        emailDTO.setCommentMap(map);
        return emailDTO;
    }

}
