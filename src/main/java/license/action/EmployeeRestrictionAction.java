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

public class EmployeeRestrictionAction extends TopAction{

    static final long serialVersionUID = 403L;	
    static Logger logger = LogManager.getLogger(EmployeeRestrictionAction.class);
    //
    String emp_id = "";
    Employee employee = null;
    EmployeeRestriction emprestr = null;
    String emprestrTitle = " Restrictions ";		
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
	if(action.equals("Save")){ 
	    back = emprestr.doSave();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		addActionMessage("Saved Successfully");
	    }
	}				
	else if(action.equals("Delete")){ 
	    back = emprestr.doDelete();
	    if(!back.equals("")){
		// back to the same page 
		addActionError(back);
	    }
	    else{
		addActionMessage("Deleted Successfully");								
		ret = "search";
	    }
	}
	else if(!emp_id.equals("")){ 
	    emprestr = new EmployeeRestriction(emp_id);
	    back = emprestr.doSelect();
	    if(!back.equals("")){
		addActionError(back);
	    }
	}		
	return ret;
    }
    public Employee getEmployee(){ 
	if(employee == null){
	    if(!emp_id.equals("")){
		employee = new Employee(emp_id);
	    }
	    else{
		employee = new Employee();
	    }
	}		
	return employee;
    }
    public EmployeeRestriction getEmprestr(){ 
	if(emprestr == null){
	    if(!emp_id.equals("")){
		emprestr = new EmployeeRestriction(emp_id);
	    }
	    else{
		emprestr = new EmployeeRestriction();
	    }
	}		
	return emprestr;
    }
		
    public void setEmprestr(EmployeeRestriction val){
	if(val != null)
	    emprestr = val;
    }
    public void setEmployee(Employee val){
	if(val != null){
	    employee = val;
	    emp_id = employee.getId();
	}
    }
    public void setEmp_id(String val){
	if(val != null)
	    emp_id = val;
    }		
    public String getEmp_id(){
	if(emp_id.equals("") && employee != null){
	    emp_id = employee.getId();
	}
	return emp_id;
    }

    public String getEmprestrTitle(){
	return emprestrTitle;
    }		
    public void setAction2(String val){
	if(val != null && !val.equals(""))		
	    action = val;
    }		


}





































