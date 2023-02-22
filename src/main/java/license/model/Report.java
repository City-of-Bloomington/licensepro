/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
package license.model;

import java.util.*;
import java.sql.*;
import java.io.*;
import java.text.*;
import javax.sql.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import license.list.*;
import license.utils.*;

public class Report{
	
    static Logger logger = LogManager.getLogger(Report.class);
    static final long serialVersionUID = 70L;
    static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();	
    String year = "",date_from="",date_to="", type_id="";
    String title = "", which_date="p.date",by="", 
	prev_year="", next_year="", dept_id="", expire_period="";
    boolean byType=false, byOwner=true, byFund=false, byFeature=false, byLead=false, byRank=false, byStatus=false;
    List<List<ReportRow>> all = new ArrayList<List<ReportRow>>();
    Hashtable<String, ReportRow> all2 = new Hashtable<String, ReportRow>(4);
    DecimalFormat decFormat = new DecimalFormat("###,###.##");
    List<ReportRow> rows = null; 
    ReportRow columnTitle = null;
    List<Employee> employees = null;
    //
    int totalIndex = 2; // DB index for row with 2 items
    public Report(){
    }
    public void setYear(String val){
	if(val != null && !val.equals("-1"))
	    year = val;
    }
    public void setPrev_year(String val){
	if(val != null && !val.equals("-1"))
	    prev_year = val;
    }
    public void setNext_year(String val){
	if(val != null && !val.equals("-1"))
	    next_year = val;
    }	
    public void setDept_id(String val){
	if(val != null && !val.equals("-1"))
	    dept_id = val;
    }
    public void setType_id(String val){
	if(val != null && !val.equals("-1"))
	    type_id = val;
    }		
    public void setDate_from(String val){
	if(val != null)
	    date_from = val;
    }	
    public void setDate_to(String val){
	if(val != null)
	    date_to = val;
    }
    public void setExpirePeriod(String val){
	if(val != null){ // withing 6 month
	    expire_period = val;
	    if(val.equals("expire6months")){
		date_from = Helper.getToday();
		date_to = Helper.getDateMonthsFromNow(6);
	    }
	    else if(val.equals("expireOneYear")){
		date_from = Helper.getToday();								
		date_to = Helper.getDateMonthsFromNow(12);
	    }
	}
    }
    //
    // getters
    //
    public String getYear(){
	return year;
    }
    public String getPrev_year(){
	return prev_year;
    }
    public String getNext_year(){
	return next_year;
    }	
    public String getDate_from(){
	return date_from ;
    }	
    public String getDate_to(){
	return date_to ;
    }
    public String getExpirePeriod(){
	return expire_period;
    }

    public String getTitle(){
	return title;
    }
    public String getDept_id(){
	if(dept_id.equals("")){
	    return "-1";
	}
	return dept_id;
    }
    public String getType_id(){
	if(type_id.equals("")){
	    return "-1";
	}
	return type_id;
    }		
    public List<ReportRow> getRows(){
	return rows;
    }
    public List<List<ReportRow>> getAll(){
	return all;
    }
    public List<ReportRow> getInventoryList(){
	List<ReportRow> list = new ArrayList<ReportRow>();
	if(all2 != null){
	    for(String key:all2.keySet()){
		ReportRow one = all2.get(key);
		list.add(one);
	    }
	}
	return list;
    }
    public ReportRow getColumnTitle(){
	return columnTitle;
    }
    public List<Employee> getEmployees(){
	return employees;
    }
    //
    // we show depts if no dept is selected
    //
    public boolean showDept(){
	return dept_id.equals("");
    }
    public String find(){
	String msg = "";
	EmployeeList empl = new EmployeeList();
	empl.setActiveOnly();
	if(!dept_id.equals("")){
	    empl.setDept_id(dept_id);
	}
	if(!type_id.equals("")){
	    empl.setType_id(type_id);
	}				
	if(!year.equals("")){
	    empl.setYear(year);
	}
	if(!date_from.equals("")){
	    empl.setDate_from(date_from);
	}
	if(!date_to.equals("")){
	    empl.setDate_to(date_to);
	}
	if(!year.equals("") || !date_from.equals("") || !date_to.equals("")){
	    empl.setNoLimit();
	    empl.setWhich_date("r.exp_date");
	}
	empl.setSortBy("r.lname");
	empl.find();
	employees = empl.getEmployees();
	return msg;
    }


}






















































