package com.chengkun.agenthub.strategy.context;

import com.chengkun.agenthub.enums.MarkdownTypeEnum;
import com.chengkun.agenthub.strategy.ArticleImportStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/* 
 * 根据不同的文章类型，选择合适的导入策略进行文章导入操作。
 * 通过依赖注入和 Map 的方式，实现了策略的动态选择和执行。
 */
@Service
public class ArticleImportStrategyContext {

    @Autowired
    private Map<String, ArticleImportStrategy> articleImportStrategyMap;

    /**
     * @param file：要导入的文章文件，通常是 MultipartFile 类型。
     * @param type：文章类型的标识，用于从 articleImportStrategyMap 中获取相应的导入策略。
     * 根据给定的类型选择相应的文章导入策略，并执行导入操作。
     */
    public void importArticles(MultipartFile file, String type) {
        // 根据 type 获取相应的文章类型标识。
        // 从 articleImportStrategyMap 中根据文章类型标识获取对应的 ArticleImportStrategy 实例。
        // 调用选择的导入策略的 importArticles 方法来执行具体的导入操作。
        articleImportStrategyMap.get(MarkdownTypeEnum.getMarkdownType(type)).importArticles(file);
    }
}
