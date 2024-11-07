package license.utils;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */

import java.util.*;
import java.sql.*;
import java.io.*;
import java.text.*;
import javax.sql.*;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobDataMap;
import javax.servlet.ServletContext;
import license.model.*;
import license.list.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NewHireJob implements Job{

    boolean debug = true;
    static final long serialVersionUID = 55L;		
    static Logger logger = LogManager.getLogger(NewHireJob.class);
    boolean activeMail = false;
    EnvBean envBean = null;
    NewHire newHire = null;
    public NewHireJob(){

    }
    public void execute(JobExecutionContext context)
        throws JobExecutionException {
				
	try{
	    doInit();
	    doWork();
	    doDestroy();
	}
	catch(Exception ex){
	    logger.error(ex);
	    System.err.println(ex);
	}
    }
    public void doInit(){

    }
    public void doDestroy() {

    }
    
    public void doWork(){
	newHire = new NewHire();
	ServletContext ctx = SingleContextHolder.getContext();
	if(ctx != null){
	    System.err.println(" ctx is Ok");				
	    String ldap_url = ctx.getInitParameter("ldap_url");
	    String ldap_principle = ctx.getInitParameter("ldap_principle");
	    String ldap_password = ctx.getInitParameter("ldap_password");
	    String msSqlUrl = ctx.getInitParameter("msSqlUrl");
	    String msDb = ctx.getInitParameter("msDb");
	    String msUser = ctx.getInitParameter("msUser");
	    String msPass = ctx.getInitParameter("msPass");	    
	    EnvBean envBean = new EnvBean();
	    envBean.setUrl(ldap_url);
	    envBean.setPrinciple(ldap_principle);
	    envBean.setPassword(ldap_password);
	    envBean.setMsSqlUrl(msSqlUrl);
	    envBean.setMsDb(msDb);
	    envBean.setMsUser(msUser);
	    envBean.setMsPass(msPass);
	    newHire.setEnvBean(envBean);
	    String back = newHire.doStart();
	    if(!back.equals("")){
		logger.error(back);
	    }
	    else{
		List<Employee> employees = newHire.getEmployees();
		if(employees == null || employees.size() == 0){
		    System.err.println("No new employee found ");
		}
		else{
		    System.err.println("Scheduled Job New Hire found "+employees.size()+" new employees");
		}		
	    }	    
	}
	else{
	    System.err.println(" ctx is null");
	}
    }

}





















































