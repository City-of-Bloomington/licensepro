/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
package license.action;
import java.util.*;
import java.io.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.opensymphony.xwork2.ModelDriven;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;  
import org.apache.struts2.dispatcher.SessionMap;  
import org.apache.struts2.action.SessionAware;  
import org.apache.struts2.action.ServletContextAware;  
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import license.model.*;
import license.list.*;
import license.utils.*;

public abstract class TopAction extends ActionSupport implements SessionAware, ServletContextAware{

    static final long serialVersionUID = 80L;	
   
    String action="", url="", id="";
    String msSqlUrl="",msDb="",msUser="",msPass="";
    static String ldap_url="", ldap_principle="",ldap_password="";
    static Logger logger = LogManager.getLogger(TopAction.class);
    // Report report = null;
    User user = null;
    ServletContext ctx;
    Map<String, Object> sessionMap;

    public void setAction(String val){
	action = val;
    }
    public String getAction(){
	return action;
    }
    public void setId(String val){
	if(val != null)
	    id = val;
    }
    public String getId(){
	return id;
    }
    public User getUser(){
	return user;
    }		
    String doPrepare(){
	String back = "";
	try{
	    user = (User)sessionMap.get("user");
	    if(user == null){
		back = LOGIN;
	    }
	    if(url.equals("")){
		String val = ctx.getInitParameter("url");
		if(val != null)
		    url = val;
		val = ctx.getInitParameter("msSqlUrl");
		if(val != null)
		    msSqlUrl = val;
		val = ctx.getInitParameter("msDb");
		if(val != null)
		    msDb = val;
		val = ctx.getInitParameter("msUser");
		if(val != null)
		    msUser = val;
		val = ctx.getInitParameter("msPass");
		if(val != null)
		    msPass = val;
		val = ctx.getInitParameter("ldap_url");
		if(val != null)
		    ldap_url = val;
		val = ctx.getInitParameter("ldap_principle");
		if(val != null)
		    ldap_principle = val;
		val = ctx.getInitParameter("ldap_password");
		if(val != null)
		    ldap_password = val;		
	    }
	}catch(Exception ex){
	    System.out.println(ex);
	}		
	return back;
    }		
    @Override  
    public void withSession(Map<String, Object> map) {  
	sessionMap=map;  
    }
    @Override  	
    public void withServletContext(ServletContext ctx) {  
        this.ctx = ctx;  
    }  	
}





































