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

public class YearTestAction extends TopAction{

    static final long serialVersionUID = 29L;	
    static Logger logger = LogManager.getLogger(YearTestAction.class);
    //
    YearTest yearTest = null;
    List<YearTest> yearTests = null;
    String yearTestsTitle = "Most Recent Year Tests";
    String testRunsTitle = "Related Test Runs ";
				
    List<TestPeriod> testPeriods = null;
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
	    back = yearTest.doSave();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		id = yearTest.getId();
		addActionMessage("Saved Successfully");
	    }
	}
	else if(action.equals("Save Changes")){ 
	    back = yearTest.doUpdate();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		addActionMessage("Updated Successfully");
	    }
	}
	else if(action.equals("Delete")){ 
	    back = yearTest.doDelete();
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
	    yearTest = new YearTest(id);
	    back = yearTest.doSelect();
	    if(!back.equals("")){
		addActionError(back);
	    }
	}
	else if(!id.equals("")){
	    ret = "view";
	    getYearTest();
	    back = yearTest.doSelect();
	    if(!back.equals("")){
		addActionError(back);
	    }
	}
	else {
	    getYearTest();
	    back = yearTest.doSelect();
	    if(!back.equals("")){ // if brand new
		ret = "input";
	    }
	    else{
		ret = "view"; // if already exist
	    }
	}
	return ret;
    }
    public YearTest getYearTest(){ 
	if(yearTest == null){
	    if(!id.equals("")){
		yearTest = new YearTest(id);
	    }
	    else{
		yearTest = new YearTest();
	    }
	}		
	return yearTest;
    }
    public List<TestPeriod> getTestPeriods(){
	if(testPeriods == null){
	    TestPeriodList tpl = new TestPeriodList();
	    String back = tpl.find();
	    if(back.equals("")){
		testPeriods = tpl.getTestPeriods();
	    }
	}
	return testPeriods;
    }

    public void setYearTest(YearTest val){
	if(val != null){
	    yearTest = val;
	    if(!id.equals("")){
		yearTest.setId(id);
	    }
	}
    }

    public String getYearTestsTitle(){
	return yearTestsTitle;
    }		
    public void setAction2(String val){
	if(val != null && !val.equals(""))		
	    action = val;
    }
    public void setYear(String val){
	if(val != null && !val.equals(""))		
	    id = val; // id also
    }		
    public String getId(){
	if(id.equals("") && yearTest != null){
	    id = yearTest.getId();
	}
	return id;
    }
    public String getYear(){
	if(id.equals("") && yearTest != null){
	    id = yearTest.getId();
	}
	return id;
    }		
    // most recent
    public List<YearTest> getYearTests(){ 
	if(yearTests == null){
	    YearTestList dl = new YearTestList();
	    String back = dl.find();
	    yearTests = dl.getYearTests();
	}		
	return yearTests;
    }
    public String getTestRunsTitle(){
	return testRunsTitle;
    }
		

}





































