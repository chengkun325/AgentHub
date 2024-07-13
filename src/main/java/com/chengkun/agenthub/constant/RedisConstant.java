package com.chengkun.agenthub.constant;

public interface RedisConstant {

    long CODE_EXPIRE_TIME = 15 * 60; // 表示验证码的过期时间，单位为秒，即 15 分钟。

    String USER_CODE_KEY = "code:"; // 表示用户验证码的键前缀，具体键名可能会在这个前缀后加上用户标识。

    String BLOG_VIEWS_COUNT = "blog_views_count"; // 表示博客总浏览量的键名。

    String ARTICLE_VIEWS_COUNT = "article_views_count"; // 表示文章总浏览量的键名。

    String WEBSITE_CONFIG = "website_config"; // 表示网站配置的键名。

    String USER_AREA = "user_area"; // 表示用户地区信息的键名。

    String VISITOR_AREA = "visitor_area"; // 表示访客地区信息的键名。

    String ABOUT = "about"; // 表示“关于”信息的键名。

    String UNIQUE_VISITOR = "unique_visitor"; // 表示唯一访客的键名。

    String LOGIN_USER = "login_user"; // 表示登录用户信息的键名。

    String ARTICLE_ACCESS = "article_access:"; // 表示文章访问记录的键前缀，具体键名可能会在这个前缀后加上文章标识。

}
