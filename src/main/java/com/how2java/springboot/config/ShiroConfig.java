package com.how2java.springboot.config;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

@Configuration
public class ShiroConfig {

	/**
	 * 【1】创建ShiroFilterFactoryBean,并且关联安全管理器
	 */
	@Bean
	public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("defaultWebSecurityManager") DefaultWebSecurityManager defaultWebSecurityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

		//添加shiro内置过滤器，实现权限拦截
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put("/login", "anon");
		map.put("/noauth", "anon");
		map.put("/to_login", "anon");

		map.put("/*", "authc");

		map.put("/add", "perms[user:add]");
		map.put("/update", "perms[user:update]");

		//关联安全管理器
		shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
		shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
		shiroFilterFactoryBean.setLoginUrl("/login");
		shiroFilterFactoryBean.setUnauthorizedUrl("/noauth");
		return shiroFilterFactoryBean;
	}

	/**
	 * 【2】创建DefaultWebSecurityManager,并且关联Realm
	 */
	@Bean(name = "defaultWebSecurityManager")
	public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm) {
		DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
		defaultWebSecurityManager.setRealm(userRealm);
		return defaultWebSecurityManager;
	}

	/**
	 * 【3】创建Realm
	 */
	@Bean(name = "userRealm")
	public UserRealm getUserRealm() {
		return new UserRealm();
	}
}
