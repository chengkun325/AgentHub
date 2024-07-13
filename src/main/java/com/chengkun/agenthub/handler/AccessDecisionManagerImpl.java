package com.chengkun.agenthub.handler;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/* 
 * 定义了在用户请求访问受保护资源时如何进行权限控制和决策。它根据用户的角色和权限信息，
 * 与资源所需的权限进行比较，决定是否允许访问。
 */
@Component
public class AccessDecisionManagerImpl implements AccessDecisionManager { // 用于自定义决策管理逻辑。
    /* 
     * 根据用户的认证信息（Authentication 对象）、目标资源（Object 对象）
     * 以及权限要求（Collection<ConfigAttribute> 对象）进行访问决策。
     */
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        List<String> permissionList = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()); // 获取用户的所有权限，并将其转换为权限名称的列表。
        // 遍历 collection 中的 ConfigAttribute 对象，检查用户是否具有所需的权限。
        for (ConfigAttribute item : collection) {
            if (permissionList.contains(item.getAttribute())) {
                return;
            }
        }
        throw new AccessDeniedException("权限不足");
    }

    /* 
     * 用于指示 AccessDecisionManager 是否支持传入的 ConfigAttribute。
     */
    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    /* 
     * 用于指示 AccessDecisionManager 是否支持传入的 Class 类型。
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
