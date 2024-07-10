package com.chengkun.agenthub.service;

import com.chengkun.agenthub.model.dto.CommentAdminDTO;
import com.chengkun.agenthub.model.dto.CommentDTO;
import com.chengkun.agenthub.model.dto.ReplyDTO;
import com.chengkun.agenthub.entity.Comment;
import com.chengkun.agenthub.model.vo.CommentVO;
import com.chengkun.agenthub.model.vo.ConditionVO;
import com.chengkun.agenthub.model.dto.PageResultDTO;
import com.chengkun.agenthub.model.vo.ReviewVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface CommentService extends IService<Comment> {

    void saveComment(CommentVO commentVO);

    PageResultDTO<CommentDTO> listComments(CommentVO commentVO);

    List<ReplyDTO> listRepliesByCommentId(Integer commentId);

    List<CommentDTO> listTopSixComments();

    PageResultDTO<CommentAdminDTO> listCommentsAdmin(ConditionVO conditionVO);

    void updateCommentsReview(ReviewVO reviewVO);

}
