package com.chengkun.agenthub.strategy.context;

import com.chengkun.agenthub.model.dto.ArticleSearchDTO;
import com.chengkun.agenthub.strategy.SearchStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.chengkun.agenthub.enums.SearchModeEnum.getStrategy;

@Service
public class SearchStrategyContext {

    @Value("${search.mode}")
    private String searchMode;

    @Autowired
    private Map<String, SearchStrategy> searchStrategyMap;

    public List<ArticleSearchDTO> executeSearchStrategy(String keywords) {
        // 根据 searchMode 获取配置的搜索策略标识。
        // 从 searchStrategyMap 中根据搜索策略标识获取对应的 SearchStrategy 实例。
        // 调用选择的搜索策略的 searchArticle 方法来执行具体的搜索操作，返回搜索结果列表。
        return searchStrategyMap.get(getStrategy(searchMode)).searchArticle(keywords);
    }

}
