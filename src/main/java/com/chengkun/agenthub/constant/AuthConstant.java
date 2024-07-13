package com.chengkun.agenthub.constant;

public interface AuthConstant {

    int TWENTY_MINUTES = 20; // 用于设置认证操作或令牌的有效期限。

    int EXPIRE_TIME = 7 * 24 * 60 * 60; // 表示过期时间，计算为 7 天的秒数。这个常量通常用于设置长期令牌或会话的过期时间。

    String TOKEN_HEADER = "Authorization"; // 表示 HTTP 请求头中用于传递认证令牌的名称。在标准的认证流程中，通常使用 Authorization 头来传递令牌信息。

    String TOKEN_PREFIX = "Bearer "; // 表示令牌的前缀字符串。在使用 JWT（JSON Web Token）时，通常令牌以 Bearer 开头，后跟实际的 JWT 字符串。

}
