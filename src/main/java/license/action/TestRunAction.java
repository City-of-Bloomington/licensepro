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

public class TestRunAction extends TopAction{

    static final long serialVersionUID = 39L;	
    static Logger logger = LogManager.getLogger(TestRunAction.class);
    //
    String year = "";
    TestRun testRun = null;
    List<TestRun> testRuns = null;
    String testRunsTitle = "Most Recent Test Runs";
    String testSelectionsTitle = "Related Test Selections";
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
	if(action.equals("prepare")){
	    if(testRun == null)
		getTestRun();
	    if(!year.equals("")){
		testRun.setYear(year);
	    }
	    back = testRun.doPrepare();
	    if(!back.equals("")){
		addActionError(back);
	    }
	}								
	if(action.equals("Save")){ 
	    back = testRun.doSave();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		id = testRun.getId();
		addActionMessage("Saved Successfully");
	    }
	}				
	else if(action.equals("Save Changes")){ 
	    back = testRun.doUpdate();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		addActionMessage("Updated Successfully");
	    }
	}
	else if(action.equals("Delete")){ 
	    back = testRun.doDelete();
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
	    testRun = new TestRun(id);
	    back = testRun.doSelect();
	    if(!back.equals("")){
		addActionError(back);
	    }
	}
								
	else if(action.equals("Refresh")){
	    // nothing
	}				
	else if(!id.equals("")){ 
	    testRun = new TestRun(id);
	    back = testRun.doSelect();
	    if(!back.equals("")){
		addActionError(back);
	    }
	}		
	return ret;
    }
    public TestRun getTestRun(){ 
	if(testRun == null){
	    if(!id.equals("")){
		testRun = new TestRun(id);
	    }
	    else{
		testRun = new TestRun();
	    }
	}		
	return testRun;
    }

    public void setTestRun(TestRun val){
	if(val != null)
	    testRun = val;
    }

    public String getTestRunsTitle(){
	return testRunsTitle;
    }
    public String getTestSelectionsTitle(){
	return testSelectionsTitle;
    }		
    public void setAction2(String val){
	if(val != null && !val.equals(""))		
	    action = val;
    }
    public void setYear(String val){
	if(val != null && !val.equals(""))		
	    year = val;
    }		
    public String getId(){
	if(id.equals("") && testRun != null){
	    id = testRun.getId();
	}
	return id;
    }
    // most recent
    public List<TestRun> getTestRuns(){ 
	if(testRuns == null){
	    TestRunList dl = new TestRunList();
	    String back = dl.find();
	    testRuns = dl.getTestRuns();
	}		
	return testRuns;
    }

}





































