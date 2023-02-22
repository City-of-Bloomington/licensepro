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

public class TestSearchAction extends TopAction{

    static final long serialVersionUID = 43L;	
    static Logger logger = LogManager.getLogger(TestSearchAction.class);
    //
    YearTestList yearTestList = null;
    List<YearTest> yearTests = null;
    List<Type> types = null,
	states = null;
    List<Dept> depts = null;
    List<Division> divisions = null;
    List<Type> years = null;
    String yearTestsTitle = "Most Recent Year Tests";		
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
	    back = yearTestList.find();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		yearTests = yearTestList.getYearTests();
		if(yearTests != null && yearTests.size() > 0){
		    yearTestsTitle = "Matching Year Tests "+yearTests.size();
		}
		else{
		    addActionMessage("No match found");
		}
	    }
	}
	else{ // show most recent
	    getYearTestList();
	    back = yearTestList.find();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		yearTests = yearTestList.getYearTests();
	    }
	}
	return ret;
    }

    public YearTestList getYearTestList(){ // starting a new redeem
	if(yearTestList == null){
	    yearTestList = new YearTestList();
	}		
	return yearTestList;
    }
    public void setYearTestList(YearTestList val){
	if(val != null)
	    yearTestList = val;
    }
    public String getYearTestsTitle(){
	return yearTestsTitle;
    }		
    // most recent
    public List<YearTest> getYearTests(){ 
	return yearTests;
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
    public List<Type> getYears(){ 
	if(years == null){
	    YearList dl = new YearList();
	    String back = dl.find();
	    if(back.equals(""))
		years = dl.getYears();
	}		
	return years;
    }		
}





































