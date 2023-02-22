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

public class TestSelectionAction extends TopAction{

    static final long serialVersionUID = 49L;	
    static Logger logger = LogManager.getLogger(TestSelectionAction.class);
    //
    TestSelection testSelection = null;
    List<TestSelection> testSelections = null;
    String employeesTitle = " Selected Employees ";
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
	if(!id.equals("")){ 
	    testSelection = new TestSelection(id);
	    back = testSelection.doSelect();
	    if(!back.equals("")){
		addActionError(back);
	    }
	}		
	return ret;
    }
    public TestSelection getTestSelection(){ 
	if(testSelection == null){
	    if(!id.equals("")){
		testSelection = new TestSelection(id);
	    }
	    else{
		testSelection = new TestSelection();
	    }
	}		
	return testSelection;
    }

    public void setTestSelection(TestSelection val){
	if(val != null)
	    testSelection = val;
    }

    public String getEmployeesTitle(){
	return employeesTitle;
    }		
    public void setAction2(String val){
	if(val != null && !val.equals(""))		
	    action = val;
    }
    public String getId(){
	if(id.equals("") && testSelection != null){
	    id = testSelection.getId();
	}
	return id;
    }
    // most recent
    public List<TestSelection> getTestSelections(){ 
	if(testSelections == null){
	    TestSelectionList dl = new TestSelectionList();
	    String back = dl.find();
	    testSelections = dl.getTestSelections();
	}		
	return testSelections;
    }

}





































