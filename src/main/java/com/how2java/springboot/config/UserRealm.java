package com.how2java.springboot.config;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import java.util.HashMap;
import java.util.Map;

public class UserRealm extends AuthorizingRealm {

	/**
	 * 授权逻辑
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		Subject                 subject = SecurityUtils.getSubject();
		Map<String, String>     map     = (HashMap<String, String>) subject.getPrincipal();
		SimpleAuthorizationInfo info    = new SimpleAuthorizationInfo();
		info.addStringPermission(map.get("perms"));
		return info;
	}

	/**
	 * 认证逻辑
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		HashMap<String, String> map = new HashMap<>();
		map.put("username", "username");
		map.put("password", "password");
		map.put("perms", "user:add");
		UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
		if (!map.get("username").equals(token.getUsername())) {
			return null;
		}
		return new SimpleAuthenticationInfo(map, map.get("password"), "");
	}
}
