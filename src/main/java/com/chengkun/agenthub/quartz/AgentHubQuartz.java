package com.chengkun.agenthub.quartz;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.alibaba.fastjson.JSON;
import com.chengkun.agenthub.model.dto.ArticleSearchDTO;
import com.chengkun.agenthub.model.dto.UserAreaDTO;
import com.chengkun.agenthub.entity.*;
import com.chengkun.agenthub.mapper.ElasticsearchMapper;
import com.chengkun.agenthub.mapper.UniqueViewMapper;
import com.chengkun.agenthub.mapper.UserAuthMapper;
import com.chengkun.agenthub.service.*;
import com.chengkun.agenthub.util.BeanCopyUtil;
import com.chengkun.agenthub.util.IpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static com.chengkun.agenthub.constant.CommonConstant.UNKNOWN;
import static com.chengkun.agenthub.constant.RedisConstant.*;

@Component("agenthubQuartz")
public class AgentHubQuartz {

    @Autowired
    private RedisService redisService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private JobLogService jobLogService;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private RoleResourceService roleResourceService;

    @Autowired
    private UniqueViewMapper uniqueViewMapper;

    @Autowired
    private UserAuthMapper userAuthMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ElasticsearchMapper elasticsearchMapper;


    @Value("${website.url}")
    private String websiteUrl;

    /**
     * 保存唯一访问记录到数据库中，统计前一天的唯一访客数。
     */
    public void saveUniqueView() {
        Long count = redisService.sSize(UNIQUE_VISITOR);
        UniqueView uniqueView = UniqueView.builder()
                .createTime(LocalDateTimeUtil.offset(LocalDateTime.now(), -1, ChronoUnit.DAYS))
                .viewsCount(Optional.of(count.intValue()).orElse(0))
                .build();
        uniqueViewMapper.insert(uniqueView);
    }

    /* 
     * 清除 Redis 中的唯一访问记录和访客地区记录。
     */
    public void clear() {
        redisService.del(UNIQUE_VISITOR);
        redisService.del(VISITOR_AREA);
    }

    /* 
     * 统计用户访问 IP 的省份信息，并存储到 Redis 中。
     */
    public void statisticalUserArea() {
        Map<String, Long> userAreaMap = userAuthMapper.selectList(new LambdaQueryWrapper<UserAuth>().select(UserAuth::getIpSource))
                .stream()
                .map(item -> {
                    if (Objects.nonNull(item) && StringUtils.isNotBlank(item.getIpSource())) {
                        return IpUtil.getIpProvince(item.getIpSource());
                    }
                    return UNKNOWN;
                })
                .collect(Collectors.groupingBy(item -> item, Collectors.counting()));
        List<UserAreaDTO> userAreaList = userAreaMap.entrySet().stream()
                .map(item -> UserAreaDTO.builder()
                        .name(item.getKey())
                        .value(item.getValue())
                        .build())
                .collect(Collectors.toList());
        redisService.set(USER_AREA, JSON.toJSONString(userAreaList));
    }

    /* 
     * 将网站中的文章链接提交给百度搜索引擎进行 SEO 优化。
     */
    public void baiduSeo() {
        List<Integer> ids = articleService.list().stream().map(Article::getId).collect(Collectors.toList());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Host", "data.zz.baidu.com");
        headers.add("User-Agent", "curl/7.12.1");
        headers.add("Content-Length", "83");
        headers.add("Content-Type", "text/plain");
        ids.forEach(item -> {
            String url = websiteUrl + "/articles/" + item;
            HttpEntity<String> entity = new HttpEntity<>(url, headers);
            restTemplate.postForObject("https://www.baidu.com", entity, String.class);
        });
    }

    /* 
     * 清理任务执行日志。
     */
    public void clearJobLogs() {
        jobLogService.cleanJobLogs();
    }

    /* 
     * 导入 Swagger 文档定义的资源，并为角色分配相关资源。
     */
    public void importSwagger() {
        resourceService.importSwagger();
        List<Integer> resourceIds = resourceService.list().stream().map(Resource::getId).collect(Collectors.toList());
        List<RoleResource> roleResources = new ArrayList<>();
        for (Integer resourceId : resourceIds) {
            roleResources.add(RoleResource.builder()
                    .roleId(1)
                    .resourceId(resourceId)
                    .build());
        }
        roleResourceService.saveBatch(roleResources);
    }

    /* 
     * 清空 Elasticsearch 中的数据，然后将数据库中的文章数据导入到 Elasticsearch 中进行搜索。
     */
    public void importDataIntoES() {
        elasticsearchMapper.deleteAll();
        List<Article> articles = articleService.list();
        for (Article article : articles) {
            elasticsearchMapper.save(BeanCopyUtil.copyObject(article, ArticleSearchDTO.class));
        }
    }
}
