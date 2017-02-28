/**
 * 
 */
package edu.gdei.jobmessage.action.common;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author dragonzhu
 *
 */
public abstract class BaseAction extends ActionSupport implements
		ServletRequestAware, ServletResponseAware, ServletContextAware,
		SessionAware {

	private ServletContext application;			// Servlet上下文
	private HttpServletRequest request;			// request对象
	private HttpServletResponse response;		// response对象
	@SuppressWarnings("rawtypes")
	private Map session;						// session对象
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see org.apache.struts2.interceptor.SessionAware#setSession(java.util.Map)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void setSession(Map sessionValues) {
		// TODO Auto-generated method stub
		this.session=sessionValues;
	}

	/* (non-Javadoc)
	 * @see org.apache.struts2.util.ServletContextAware#setServletContext(javax.servlet.ServletContext)
	 */
	@Override
	public void setServletContext(ServletContext application) {
		// TODO Auto-generated method stub
		this.application=application;
	}

	/* (non-Javadoc)
	 * @see org.apache.struts2.interceptor.ServletResponseAware#setServletResponse(javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void setServletResponse(HttpServletResponse response) {
		// TODO Auto-generated method stub
		this.response=response;
	}

	/* (non-Javadoc)
	 * @see org.apache.struts2.interceptor.ServletRequestAware#setServletRequest(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public void setServletRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		this.request=request;
	}

	/**
	 * @return the application
	 */
	public ServletContext getApplication() {
		return application;
	}

	/**
	 * @param application the application to set
	 */
	public void setApplication(ServletContext application) {
		this.application = application;
	}

	/**
	 * @return the request
	 */
	public HttpServletRequest getRequest() {
		return request;
	}

	/**
	 * @param request the request to set
	 */
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * @return the response
	 */
	public HttpServletResponse getResponse() {
		return response;
	}

	/**
	 * @param response the response to set
	 */
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	/**
	 * @return the session
	 */
	@SuppressWarnings("rawtypes")
	public Map getSession() {
		return session;
	}

}
