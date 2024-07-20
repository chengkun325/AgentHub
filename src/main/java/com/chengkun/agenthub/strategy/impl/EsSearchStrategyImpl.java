package com.chengkun.agenthub.strategy.impl;

import com.chengkun.agenthub.model.dto.ArticleSearchDTO;
import com.chengkun.agenthub.strategy.SearchStrategy;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.log4j.Log4j2;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.chengkun.agenthub.constant.CommonConstant.*;
import static com.chengkun.agenthub.enums.ArticleStatusEnum.PUBLIC;

@Log4j2
@Service("esSearchStrategyImpl")
public class EsSearchStrategyImpl implements SearchStrategy {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Override
    public List<ArticleSearchDTO> searchArticle(String keywords) {
        if (StringUtils.isBlank(keywords)) {
            return new ArrayList<>();
        }
        // 调用 buildQuery(keywords) 方法构建 Elasticsearch 的查询条件
        return search(buildQuery(keywords));
    }

    /**
     * @param keywords
     * @return
     * 构建查询条件
     */
    private NativeSearchQueryBuilder buildQuery(String keywords) {
        // 使用 NativeSearchQueryBuilder 构建器创建查询。
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        // 使用 BoolQueryBuilder 构建布尔查询，包括匹配文章标题和文章内容是否包含关键字 keywords，同时限定文章未删除且状态为公开的条件。
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("articleTitle", keywords))
                        .should(QueryBuilders.matchQuery("articleContent", keywords)))
                .must(QueryBuilders.termQuery("isDelete", FALSE))
                .must(QueryBuilders.termQuery("status", PUBLIC.getStatus()));
        nativeSearchQueryBuilder.withQuery(boolQueryBuilder);
        return nativeSearchQueryBuilder;
    }

    /**
     * @param nativeSearchQueryBuilder
     * @return
     * 执行搜索方法。
     */
    private List<ArticleSearchDTO> search(NativeSearchQueryBuilder nativeSearchQueryBuilder) {
        // 构建标题和内容的高亮显示设置，通过 HighlightBuilder.Field 实现。
        HighlightBuilder.Field titleField = new HighlightBuilder.Field("articleTitle");
        titleField.preTags(PRE_TAG);
        titleField.postTags(POST_TAG);
        HighlightBuilder.Field contentField = new HighlightBuilder.Field("articleContent");
        contentField.preTags(PRE_TAG);
        contentField.postTags(POST_TAG);
        contentField.fragmentSize(50);
        nativeSearchQueryBuilder.withHighlightFields(titleField, contentField);
        try {
            // 调用 elasticsearchRestTemplate.search() 方法执行查询，并将结果映射为 ArticleSearchDTO 类型的搜索结果。
            SearchHits<ArticleSearchDTO> search = elasticsearchRestTemplate.search(nativeSearchQueryBuilder.build(), ArticleSearchDTO.class);
            // 如果查询成功，将高亮显示的内容设置到对应的文章搜索结果中并返回。
            return search.getSearchHits().stream().map(hit -> {
                ArticleSearchDTO article = hit.getContent();
                List<String> titleHighLightList = hit.getHighlightFields().get("articleTitle");
                if (CollectionUtils.isNotEmpty(titleHighLightList)) {
                    article.setArticleTitle(titleHighLightList.get(0));
                }
                List<String> contentHighLightList = hit.getHighlightFields().get("articleContent");
                if (CollectionUtils.isNotEmpty(contentHighLightList)) {
                    article.setArticleContent(contentHighLightList.get(contentHighLightList.size() - 1));
                }
                return article;
            }).collect(Collectors.toList());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new ArrayList<>();
    }

}

