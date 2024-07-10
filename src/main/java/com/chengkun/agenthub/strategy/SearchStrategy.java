package com.chengkun.agenthub.strategy;

import com.chengkun.agenthub.model.dto.ArticleSearchDTO;

import java.util.List;

public interface SearchStrategy {

    List<ArticleSearchDTO> searchArticle(String keywords);

}
