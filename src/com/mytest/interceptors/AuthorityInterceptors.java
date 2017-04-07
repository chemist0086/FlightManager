package com.mytest.interceptors;

import org.apache.struts2.ServletActionContext;

import com.mytest.beans.User;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class AuthorityInterceptors extends AbstractInterceptor {

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		User user =(User)ServletActionContext.getContext().getSession().get("LoginSuccess");
    	if(user.getAuthority()==1){
    		return "success";
    	}
		return "loginFail";
	}
}
