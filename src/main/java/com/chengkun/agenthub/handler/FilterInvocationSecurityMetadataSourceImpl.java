package com.chengkun.agenthub.handler;

import com.chengkun.agenthub.model.dto.ResourceRoleDTO;
import com.chengkun.agenthub.mapper.RoleMapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;

/* 
 * 根据请求的 URL 和方法来提供相应的权限信息。
 */
@Component
// 实现了 FilterInvocationSecurityMetadataSource 接口，用于提供基于 URL 和请求方法的权限配置信息。
public class FilterInvocationSecurityMetadataSourceImpl implements FilterInvocationSecurityMetadataSource {


    @Autowired
    private RoleMapper roleMapper;

    private static List<ResourceRoleDTO> resourceRoleList;

    /**
     * 从数据库中加载资源和角色的映射信息，并缓存到 resourceRoleList 中。
     */
    @PostConstruct // 在类初始化完成后调用该方法。
    private void loadResourceRoleList() {
        resourceRoleList = roleMapper.listResourceRoles();
    }

    /**
     * 用于清空缓存的资源角色列表，在需要时重新加载。
     */
    public void clearDataSource() {
        resourceRoleList = null;
    }

    /* 
     * 根据请求的 URL 和方法，返回对应的权限配置。
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        if (CollectionUtils.isEmpty(resourceRoleList)) {
            this.loadResourceRoleList();
        }
        // 获取请求的 URL 和方法。
        FilterInvocation fi = (FilterInvocation) object;
        String method = fi.getRequest().getMethod();
        String url = fi.getRequest().getRequestURI();
        // 使用 AntPathMatcher 进行 URL 匹配。
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        // 遍历资源角色列表，找到与请求 URL 和方法匹配的项。
        for (ResourceRoleDTO resourceRoleDTO : resourceRoleList) {
            if (antPathMatcher.match(resourceRoleDTO.getUrl(), url) && resourceRoleDTO.getRequestMethod().equals(method)) {
                List<String> roleList = resourceRoleDTO.getRoleList();
                if (CollectionUtils.isEmpty(roleList)) {
                    // 如果找到匹配项，则返回对应的角色列表作为权限配置；如果角色列表为空，则返回 "disable"。
                    return SecurityConfig.createList("disable");
                }
                return SecurityConfig.createList(roleList.toArray(new String[]{}));
            }
        }
        // 如果没有匹配项，返回 null。
        return null;
    }

    /* 
     * 返回 null，表示不需要提供所有的配置权限。
     */
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    /* 
     * 检查是否支持给定的类，返回 true 表示支持 FilterInvocation 类及其子类。
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
