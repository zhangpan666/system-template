package com.gallery.manage.common.config.shiro;

import com.gallery.manage.common.filter.SignFilter;
import com.light.config.filter.HttpFilter;
import com.light.config.filter.LogFilter;
import com.light.shiro.config.RedisCacheManager;
import com.light.shiro.util.ShiroUtil;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class CustomShiroConfig {


    @Bean
    public Realm customAuthorizingRealm(HashedCredentialsMatcher hashedCredentialsMatcher, RedisCacheManager cacheManager) {
        CustomAuthorizingRealm customAuthorizingRealm = new CustomAuthorizingRealm();
        customAuthorizingRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        customAuthorizingRealm.setCacheManager(cacheManager);
        customAuthorizingRealm.setAuthenticationCachingEnabled(false);
        customAuthorizingRealm.setAuthorizationCachingEnabled(true);
        return customAuthorizingRealm;
    }

    @Bean
    public CookieRememberMeManager rememberMeManager(SimpleCookie simpleCookie) {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(simpleCookie);
        cookieRememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
        return cookieRememberMeManager;
    }

    @Bean
    public DefaultWebSecurityManager securityManager(Realm realm, SessionManager sessionManager, CookieRememberMeManager
            cookieRememberMeManager, RedisCacheManager cacheManager) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(realm);
        defaultWebSecurityManager.setCacheManager(cacheManager);
        defaultWebSecurityManager.setSessionManager(sessionManager);
        defaultWebSecurityManager.setRememberMeManager(cookieRememberMeManager);
        return defaultWebSecurityManager;
    }


    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager, ShiroUserFilter
            shiroUserFilter, LogFilter logFilter, SignFilter signFilter, HttpFilter httpFilter) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        Map<String, Filter> filters = new LinkedHashMap<>();
        filters.put("logFilter", logFilter);
        filters.put("httpFilter", httpFilter);
        filters.put("signFilter", signFilter);
        filters.put("shiroUserFilter", shiroUserFilter);
        shiroFilterFactoryBean.setFilters(filters);
        shiroFilterFactoryBean.setLoginUrl("/gallery/manage/admin/auth/toLogin");
        shiroFilterFactoryBean.setSuccessUrl("/gallery/manage/admin/index");
        shiroFilterFactoryBean.setUnauthorizedUrl("/gallery/manage/admin/auth/403");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(ShiroUtil.getFilterChainDefinitionMap());
        return shiroFilterFactoryBean;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }


    @Bean
    public ShiroUserFilter shiroUserFilter() {
        return new ShiroUserFilter();
    }

    @Bean
    public LogFilter logFilter() {
        return new LogFilter();
    }

    @Bean
    public SignFilter signFilter() {
        return new SignFilter();
    }

    @Bean
    public HttpFilter httpFilter() {
        return new HttpFilter();
    }

}


