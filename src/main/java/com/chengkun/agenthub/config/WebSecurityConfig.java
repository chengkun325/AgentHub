package com.chengkun.agenthub.config;

import com.chengkun.agenthub.filter.JwtAuthenticationTokenFilter;
import com.chengkun.agenthub.handler.AccessDecisionManagerImpl;
import com.chengkun.agenthub.handler.FilterInvocationSecurityMetadataSourceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // 这是 Spring 框架的注解，表明这个类是一个配置类，可以在 Spring 容器中注册 bean。
@EnableWebSecurity // 这是 Spring Security 的注解，用于启用 Web 安全性。
// 继承了 WebSecurityConfigurerAdapter 类，用于自定义 Spring Security 的配置。
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired // 这是 Spring 框架的注解，用于自动注入 Spring 容器中的 bean。
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    /**
     * @return
     * 定义了 FilterInvocationSecurityMetadataSource 的 bean，用于动态获取请求资源的安全配置信息。
     */
    @Bean
    public FilterInvocationSecurityMetadataSource securityMetadataSource() {
        return new FilterInvocationSecurityMetadataSourceImpl();
    }

    /**
     * @return
     * 定义了 AccessDecisionManager 的 bean，用于决定请求是否允许访问。
     */
    @Bean
    public AccessDecisionManager accessDecisionManager() {
        return new AccessDecisionManagerImpl();
    }

    /* 
     * 覆盖了父类的方法，用于获取 AuthenticationManager 的 bean。
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * @return
     * 定义了密码编码器 BCryptPasswordEncoder 的 bean，用于对用户密码进行加密。
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception { // 配置了 HTTP 请求的安全性。
        http.formLogin() // 配置基于表单的登录，设置了登录处理 URL、登录成功处理器和登录失败处理器。
                .loginProcessingUrl("/users/login")
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler);
        http.authorizeRequests() // 配置请求的授权规则，通过 withObjectPostProcessor 方法设置了动态权限控制的配置源和决策管理器。
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O fsi) {
                        fsi.setSecurityMetadataSource(securityMetadataSource());
                        fsi.setAccessDecisionManager(accessDecisionManager());
                        return fsi;
                    }
                })
                .anyRequest().permitAll() // 允许所有请求访问。
                .and()
                .csrf().disable().exceptionHandling() // 禁用 CSRF。配置异常处理，设置了认证入口点和访问拒绝处理器。
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
                .and()
                .sessionManagement() // 配置会话管理策略为无状态。
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                // 在 UsernamePasswordAuthenticationFilter 之前添加了自定义的 JWT Token 过滤器 jwtAuthenticationTokenFilter。
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
