package com.chengkun.agenthub.constant;

public interface CommonConstant {

    int ONE = 1; // 表示数字 1。

    int ZERO = 0; // 表示数字 0。

    int FALSE = 0; // 表示布尔值的假（false），使用 0 表示。

    int TRUE = 1; // 表示布尔值的真（true），使用 1 表示。

    int BLOGGER_ID = 1; // 表示博主的 ID，默认值为 1。

    int DEFAULT_CONFIG_ID = 1; // 表示默认配置的 ID，默认值为 1。

    int DEFAULT_ABOUT_ID = 1; // 表示默认“关于”页面的 ID，默认值为 1。

    String PRE_TAG = "<mark>"; // 表示高亮文本的起始标签，用于搜索结果中的高亮显示。

    String POST_TAG = "</mark>"; // 表示高亮文本的结束标签，用于搜索结果中的高亮显示。

    String CURRENT = "current"; // 表示当前页的键名，用于分页。

    String SIZE = "size"; // 表示每页显示条数的键名，用于分页。

    String DEFAULT_SIZE = "10"; // 表示每页默认显示的条数。

    String DEFAULT_NICKNAME = "用户"; // 表示默认昵称，用于未设置昵称的用户。

    String COMPONENT = "Layout"; // 表示组件名称，用于前端布局。

    String UNKNOWN = "未知"; // 表示未知状态或值的字符串。

    String APPLICATION_JSON = "application/json;charset=utf-8"; // 表示 JSON 数据的 MIME 类型及字符集，用于 HTTP 响应中指定内容类型。

    String CAPTCHA = "验证码"; // 表示验证码的中文字符串，可能用于提示用户输入验证码。

    String CHECK_REMIND = "审核提醒"; // 表示审核提醒的中文字符串，用于提示审核相关操作。

    String COMMENT_REMIND = "评论提醒"; // 表示评论提醒的中文字符串，用于提示评论相关操作。

    String MENTION_REMIND = "@提醒"; // 表示提及提醒的中文字符串，用于提示用户被提及。

}
