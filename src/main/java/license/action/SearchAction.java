package license.action;
/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.util.*;
import java.io.*;
import java.text.*;

import com.opensymphony.xwork2.ModelDriven;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;  
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import license.model.*;
import license.list.*;
import license.utils.*;

public class SearchAction extends TopAction{

    static final long serialVersionUID = 33L;	
    static Logger logger = LogManager.getLogger(SearchAction.class);
    //
    EmployeeList employeeList = null;
    List<Employee> employees = null;
    List<Type> types = null,
	states = null;
    List<Dept> depts = null;
    List<Division> divisions = null;
    List<Type> expYears = null;
    List<Type> sortOptions = null;
    String employeesTitle = "Most Recent Employees";		
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
	if(action.equals("Submit")){
	    employeeList.setNoLimit();
	    back = employeeList.find();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		employees = employeeList.getEmployees();
		if(employees != null && employees.size() > 0){
		    employeesTitle = "Matching Employees "+employees.size();
		    if(employees.size() == 1){
			Employee one = employees.get(0);
			try{
			    HttpServletResponse res = ServletActionContext.getResponse();
			    String str = url+"employee.action?id="+one.getId();
			    res.sendRedirect(str);
			    return super.execute();
			}catch(Exception ex){
			    System.err.println(ex);
			}													
		    }
		}
		else{
		    addActionMessage("No match found");
		}
	    }
	}
	else{ // show most recent
	    getEmployeeList();
	    back = employeeList.find();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		employees = employeeList.getEmployees();
	    }
	}
	return ret;
    }

    public EmployeeList getEmployeeList(){ // starting a new redeem
	if(employeeList == null){
	    employeeList = new EmployeeList();
	}		
	return employeeList;
    }
    public void setEmployeeList(EmployeeList val){
	if(val != null)
	    employeeList = val;
    }
    public String getEmployeesTitle(){
	return employeesTitle;
    }		
    // most recent
    public List<Employee> getEmployees(){ 
	return employees;
    }
    public List<Type> getTypes(){ 
	if(types == null){
	    TypeList dl = new TypeList();
	    String back = dl.find();
	    if(back.equals("")){
		types = dl.getTypes();
		types.add(new Type("cdl","Any CDL"));
	    }
	}
	return types;
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
	    DivisionList dl = new DivisionList();
	    String back = dl.find();
	    if(back.equals(""))
		divisions = dl.getDivisions();
	}		
	return divisions;
    }
    public List<Type> getExpYears(){ 
	if(expYears == null){
	    ExpYearList dl = new ExpYearList();
	    String back = dl.find();
	    if(back.equals(""))
		expYears = dl.getYears();
	}		
	return expYears;
    }
    public List<Type> getSortOptions(){ 
	if(sortOptions == null){
	    sortOptions = new ArrayList<Type>(4);
	    Type one = new Type("r.id DESC","ID");
	    sortOptions.add(one);
	    one = new Type("r.lname","Last Name");
	    sortOptions.add(one);
	    one = new Type("d.name,r.lname","Department, Last Name");						
	    sortOptions.add(one);
	}		
	return sortOptions;
    }		
}





































