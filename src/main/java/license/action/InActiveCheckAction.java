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

public class InActiveCheckAction extends TopAction{

    static final long serialVersionUID = 229L;	
    static Logger logger = LogManager.getLogger(InActiveCheckAction.class);
    //
    InActiveCheck check = null;
    String checksTitle = "Most Recent Inactive Employees Checks";
    String employeesTitle = " inactive employees found in this run  ";
    List<InActiveCheck> checks = null;
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
	if(action.startsWith("Start")){ // start schedule
	    //
	    // we need this only first time
	    //
	    InactiveScheduler sheduler = new InactiveScheduler(Helper.getToday());
	    try{
		back = sheduler.run();
		if(!back.equals("")){
		    // 
		    addActionError(back);
		}
		else{
		    addActionMessage("Started Successfully");
		}
	    }catch(Exception ex){
		addActionError(""+ex);
		System.err.println(ex);
	    }
	}
	else if(action.equals("Confirm")){ 
	    back = check.doConfirm();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		addActionMessage("Confirmed Successfully");								
	    }
	}
	else if(action.startsWith("Run")){
	    InactiveJob job = new InactiveJob();
	    job.doWork();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		addActionMessage("Ran Successfully");								
	    }
	}	
	else if(action.equals("Delete")){ 
	    back = check.doDelete();
	    if(!back.equals("")){
		// back to the same page 
		addActionError(back);
	    }
	    else{
		addActionMessage("Deleted Successfully");
	    }
	}
	
	else if(!id.equals("")){ 
	    getCheck();
	    back = check.doSelect();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		employees = check.getEmployees();
		if(employees != null && employees.size() > 0){
		    employeesTitle = employees.size()+" "+employeesTitle; 
		}
		else{
		    employeesTitle = "No inactive employee found"; 
		}
	    }
	}
	return ret;
    }

    public InActiveCheck getCheck(){ 
	if(check == null){
	    if(!id.equals("")){
		check = new InActiveCheck(id);
	    }
	    else{
		check = new InActiveCheck();
	    }
	}
	return check;
    }
    public List<InActiveCheck> getChecks(){
	if(checks == null){
	    InActiveCheckList tpl = new InActiveCheckList();
	    String back = tpl.find();
	    if(back.equals("")){
		checks = tpl.getInActiveChecks();
	    }
	}
	return checks;
    }

    public void setInActiveCheck(InActiveCheck val){
	if(val != null){
	    check = val;
	    if(!id.equals("")){
		check.setId(id);
	    }
	}
    }

    public String getChecksTitle(){
	return checksTitle;
    }
    public String getEmployeesTitle(){
	return employeesTitle;
    }		
    public void setAction2(String val){
	if(val != null && !val.equals(""))		
	    action = val;
    }
    public String getId(){
	if(id.equals("") && check != null){
	    id = check.getId();
	}
	return id;
    }
		

}





































