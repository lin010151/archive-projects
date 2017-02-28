package edu.gdei.jobmessage.util;

import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class CheckLogin implements Interceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void destroy() {
		System.out.println("------CheckLogin.destroy------");
	}

	@Override
	public void init() {
		System.out.println("------CheckLogin.init------");

	}

	@SuppressWarnings("rawtypes")
	@Override
	public String intercept(ActionInvocation arg0) throws Exception {
		
		System.out.println("------CheckLogin.intercept------");
		
		Map session=ActionContext.getContext().getSession();
		
		if(session.get("managerid")!=null)
		{
			return arg0.invoke();
		}
		
		return "checkLoginFail";
	}

}
