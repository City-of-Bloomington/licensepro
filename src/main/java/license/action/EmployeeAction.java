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

public class EmployeeAction extends TopAction{

    static final long serialVersionUID = 28L;	
    static Logger logger = LogManager.getLogger(EmployeeAction.class);
    //
    Employee employee = null;
    List<Employee> employees = null;
    List<Type> types = null, states = null;
    List<Dept> depts = null;
    List<Division> divisions = null;
    List<Type> expYears = null;
    String employeesTitle = "Most Recent Active Employees";		
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
	    employee.setUser(user);
	    back = employee.doSave();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		id = employee.getId();
		addActionMessage("Saved Successfully");
	    }
	}				
	else if(action.equals("Save Changes")){ 
	    employee.setUser(user);
	    back = employee.doUpdate();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		addActionMessage("Updated Successfully");
	    }
	}
	else if(action.equals("Delete")){ 
	    back = employee.doDelete();
	    if(!back.equals("")){
		// back to the same page 
		addActionError(back);
	    }
	    else{
		addActionMessage("Deleted Successfully");								
		ret = "search";
	    }
	}
	else if(action.equals("Edit")){ 
	    employee = new Employee(id);
	    back = employee.doSelect();
	    if(!back.equals("")){
		addActionError(back);
	    }
	}
								
	else if(action.equals("Refresh")){
	    // nothing
	}				
	else if(!id.equals("")){ 
	    employee = new Employee(id);
	    back = employee.doSelect();
	    if(!back.equals("")){
		addActionError(back);
	    }
	}		
	return ret;
    }
    public Employee getEmployee(){ 
	if(employee == null){
	    if(!id.equals("")){
		employee = new Employee(id);
	    }
	    else{
		employee = new Employee();
	    }
	}		
	return employee;
    }
		

    public void setEmployee(Employee val){
	if(val != null)
	    employee = val;
    }

    public String getEmployeesTitle(){
	return employeesTitle;
    }		
    public void setAction2(String val){
	if(val != null && !val.equals(""))		
	    action = val;
    }		
    public String getId(){
	if(id.equals("") && employee != null){
	    id = employee.getId();
	}
	return id;
    }
    // most recent
    public List<Employee> getEmployees(){ 
	if(employees == null){
	    EmployeeList dl = new EmployeeList();
	    // dl.setStatus("Active");
	    String back = dl.find();
	    employees = dl.getEmployees();
	}		
	return employees;
    }
    public List<Type> getTypes(){ 
	if(types == null){
	    TypeList dl = new TypeList();
	    String back = dl.find();
	    if(back.equals(""))
		types = dl.getTypes();
	}		
	return types;
    }
    public List<Type> getExpYears(){ 
	if(expYears == null){
	    ExpYearList dl = new ExpYearList(); // future years only
	    String back = dl.find();
	    if(back.equals(""))
		expYears = dl.getYears();
	}		
	return expYears;
    }		
		
		
    public List<Type> getStates(){ 
	if(states == null){
	    TypeList dl = new TypeList("states");
	    String back = dl.find();
	    if(back.equals(""))
		states = dl.getTypes();
	}		
	return states;
    }
    public List<Dept> getDepts(){ 
	if(depts == null){
	    DeptList dl = new DeptList();
	    String back = dl.find();
	    if(back.equals(""))
		depts = dl.getDepts();
	}		
	return depts;
    }
    public List<Division> getDivisions(){ 
	if(divisions == null){
	    String dept_id = employee.getDept_id();
	    DivisionList dl = new DivisionList(dept_id);
	    String back = dl.find();
	    if(back.equals(""))
		divisions = dl.getDivisions();
	}		
	return divisions;
    }

}





































