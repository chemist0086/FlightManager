package com.mywork.interceptors;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class PermissionInterceptor extends AbstractInterceptor {

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		Object object = ServletActionContext.getContext().getSession().get("LoginSuccess");
		if(object != null){
			return invocation.invoke();
		}else{
			return "loginFail";
		}
	}

}
