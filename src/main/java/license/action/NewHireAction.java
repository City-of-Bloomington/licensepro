package license.action;
/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.util.*;
import java.io.*;
import java.text.*;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;  
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import license.model.*;
import license.list.*;
import license.utils.*;

public class NewHireAction extends TopAction{

    static final long serialVersionUID = 239L;	
    static Logger logger = LogManager.getLogger(NewHireAction.class);
    // static String ldap_url="", ldap_principle="",ldap_password="";
    //
    // for NW database to verify active users (temporary till we use ldap)
    //
    //    String msSqlUrl = "", msDb="", msUser="",msPass="";
    EnvBean envBean = null; 
    NewHire newhire = null;
    String newhiresTitle = "Most Recent New Hire Runs";
    String employeesTitle = " Newly hired employees found in this run  ";
    List<NewHire> newhires = null;
    List<Employee> employees = null;
    public String execute(){
	String ret = SUCCESS;
	String back = doPrepare();
	if(!back.equals("")){
	    try{
		HttpServletResponse res = ServletActionContext.getResponse();
		String str = url+"Login";
		res.sendRedirect(str);
		return super.execute();
	    }catch(Exception ex){
		System.err.println(ex);
	    }	
	}
	if(action.equals("Start")){
	    //
	    // we need this only first time
	    //
	    envBean = new EnvBean();
	    envBean.setUrl(ldap_url);
	    envBean.setPrinciple(ldap_principle);
	    envBean.setPassword(ldap_password);
	    envBean.setMsSqlUrl(msSqlUrl);
	    envBean.setMsDb(msDb);
	    envBean.setMsUser(msUser);
	    envBean.setMsPass(msPass);
	    getNewhire();						
	    newhire.setEnvBean(envBean);
	    back = newhire.doStart();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		id = newhire.getId();
		employees = newhire.getEmployees();
		if(employees == null || employees.size() == 0){
		    addActionMessage("No new employee found ");
		}
		else{
		    employeesTitle = employees.size()+employeesTitle;
		    addActionMessage("Found "+employees.size()+" new employees");
		}
	    }
	}
	else if(action.equals("Delete")){ 
	    back = newhire.doDelete();
	    if(!back.equals("")){
		// back to the same page 
		addActionError(back);
	    }
	    else{
		addActionMessage("Deleted Successfully");								
	    }
	}
	else if(!id.equals("")){ 
	    getNewhire();
	    back = newhire.doSelect();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		employees = newhire.getEmployees();
		if(employees != null && employees.size() > 0){
		    employeesTitle = employees.size()+" "+employeesTitle; 
		}
		else{
		    employeesTitle = "No newly hired employee found"; 
		}
	    }
	}
	else{
	    getNewhire();
	}
	return ret;
    }
    /**
    @Override  
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
		val = ctx.getInitParameter("ldap_url");
		if(val != null)
		    ldap_url = val;
		val = ctx.getInitParameter("ldap_principle");
		if(val != null)
		    ldap_principle = val;
		val = ctx.getInitParameter("ldap_password");
		if(val != null)
		    ldap_password = val;
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
	    }
	}catch(Exception ex){
	    System.out.println(ex);
	}		
	return back;
    }
    */
    public NewHire getNewhire(){
	if(newhire == null){
	    if(!id.equals("")){
		newhire = new NewHire(id);
	    }
	    else{
		newhire = new NewHire();
	    }
	}
	return newhire;
    }
    public List<NewHire> getNewhires(){
	if(newhires == null){
	    NewHireList tpl = new NewHireList();
	    String back = tpl.find();
	    if(back.equals("")){
		newhires = tpl.getNewhires();
	    }
	}
	return newhires;
    }

    public void setNewHire(NewHire val){
	if(val != null){
	    newhire = val;
	    if(!id.equals("")){
		newhire.setId(id);
	    }
	}
    }

    public String getNewhiresTitle(){
	return newhiresTitle;
    }
    public String getEmployeesTitle(){
	return employeesTitle;
    }		
    public void setAction2(String val){
	if(val != null && !val.equals(""))		
	    action = val;
    }
    public String getId(){
	if(id.equals("") && newhire != null){
	    id = newhire.getId();
	}
	return id;
    }
		

}





































