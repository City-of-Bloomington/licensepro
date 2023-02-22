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
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import license.model.*;
import license.list.*;
import license.utils.*;

public class ReportAction extends TopAction{

    static final long serialVersionUID = 80L;	
   
    static Logger logger = LogManager.getLogger(ReportAction.class);
    Report report = null;
    List<Type> types = null;
    List<String> years = null;
    List<Dept> depts = null;
    List<Employee> employees = null;
    String employeesTitle = "Total Matching ";
    boolean showDept = false;
    public String execute(){
	String ret = INPUT;
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
	    ret = SUCCESS;
	    back = report.find();
	    if(!back.equals("")){
		addActionError(back);
		ret = INPUT;
	    }
	    else{
		List<Employee> emps = report.getEmployees();
		if(emps != null && emps.size() > 0){
		    employeesTitle = "Total Matching "+emps.size();
		    employees = emps;
		}
		showDept = report.showDept();
	    }
	}
	return ret;
    }			 
    public Report getReport(){
	if(report == null){
	    report = new Report();
	}
	return report;
    }
    public void setReport(Report val){
	if(val != null)
	    report = val;
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
    public List<String> getYears(){
	if(years == null){
	    int yy = Helper.getCurrentYear();
	    years = new ArrayList<String>(11);
	    years.add("");
	    for(int i=0;i<3;i++){
		int y2 = yy - i;
		years.add(""+y2);
	    }
	}
	return years;
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
    public List<Employee> getEmployees(){
	return employees;
    }
    public String getEmployeesTitle(){
	return employeesTitle;
    }
    public boolean getShowDept(){
	return showDept;
    }
    public String getDeptDivNames(){
	// if certain dept is selected, we need the name
	String ret = "";
	if(employees != null && employees.size() > 0){
	    Employee one = employees.get(0);
	    ret = one.getDeptDivNames();
	}
	return ret;
    }

}





































