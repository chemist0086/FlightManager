package com.mywork.actions;

import org.apache.struts2.ServletActionContext;

public class LogoutAction {
	public String execute(){
		ServletActionContext.getContext().getSession().remove("LoginSuccess");
		return "LogoutSuccess";
	}
}
