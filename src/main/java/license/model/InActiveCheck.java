package license.model;
/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */

import java.sql.*;
import java.text.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import license.list.*;
import license.utils.*;

public class InActiveCheck{

    String id = "", date="", confirmed="";
    EnvBean envBean = null;
    static final long serialVersionUID = 115L;
    static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    static Logger logger = LogManager.getLogger(InActiveCheck.class);
    List<Employee> employees = null;
    String[] add_inactive = null;
    public InActiveCheck(){
    }
    public InActiveCheck(String val){
	setId(val);
    }
    public InActiveCheck(String val, String val2, String val3){
	setId(val);
	setDate(val2);
	setConfirmed(val3 != null && !val3.equals(""));
    }
    public String getId(){
	return id;
    }
    public String getDate(){
	return date;
    }
    public boolean isConfirmed(){
	return !confirmed.equals("");
    }
    public boolean hasEmployees(){
	return employees != null && employees.size() > 0;
    }
    public List<Employee> getEmployees(){
	if(employees == null && !id.equals("")){
	    EmployeeList dl = new EmployeeList();
	    dl.setInactive_check_id(id);
	    dl.setNoLimit();
	    String back = dl.find();
	    if(back.equals("")){
		List<Employee> list = dl.getEmployees();
		if(list != null && list.size() > 0){
		    employees = list;
		}
	    }
	}
	return employees;
    }
    //
    // setters
    //
    public void setId (String val){
	if(val != null)
	    id = val;
    }
    public void setDate (String val){
	if(val != null)
	    date = val;
    }
    public void setConfirmed (boolean val){
	if(val)
	    confirmed = "y";
    }
    public void setEnvBean(EnvBean val){
	if(val != null)
	    envBean = val;
    }
    public void setAdd_inactive(String[] vals){
	if(vals != null)
	    add_inactive = vals;
    }		
    //
    public String doSelect(){
				
	String msg = "";
	String qq = " select date_format(date,'%m/%d/%Y'),confirmed from inactive_checks where id=?";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	logger.debug(qq);
	try{
	    con = Helper.getConnection();
	    if(con == null){
		msg = "Could not connect ";
		return msg;
	    }
	    pstmt = con.prepareStatement(qq);
	    pstmt.setString(1, id);
	    rs = pstmt.executeQuery();	
	    if(rs.next()){
		setDate(rs.getString(1));
		String str = rs.getString(2);
		if(str != null) confirmed = str;
	    }
	}catch(Exception e){
	    msg += e+":"+qq;
	    logger.error(msg);
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	}
	return msg;
    }
    //
    public String doStart(){
	String msg = "";
	String qq = " insert into inactive_checks values(0,now(),null)";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	logger.debug(qq);
	try{
	    con = Helper.getConnection();
	    if(con == null){
		msg = "Could not connect ";
		return msg;
	    }
	    pstmt = con.prepareStatement(qq);
	    pstmt.executeUpdate();
	    //
	    qq = "select LAST_INSERT_ID() ";
	    logger.debug(qq);
	    pstmt = con.prepareStatement(qq);
	    rs = pstmt.executeQuery();
	    if(rs.next()){
		id = rs.getString(1);
	    }
	    date = Helper.getToday();
	}catch(Exception e){
	    msg += e+":"+qq;
	    logger.error(msg);
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	}
	if(msg.equals("")){
	    msg = findAndInactiveEmployees();
	    if(employees != null && employees.size() > 0){
		msg += addInactiveEmployeeToCheckList();
	    }
	}
	return msg;
    }
    public String findAndInactiveEmployees(){
	String back = "";
	EmployeeList empList = new EmployeeList();
	empList.setActiveOnly();
	empList.setHasEmployeeNum();
	empList.setExcludeDept("20"); // remove Transit from the list
	empList.setNoLimit();
	back = empList.find();
	if(back.equals("")){
	    employees = empList.getEmployees();
	    System.err.println(" Emp size "+employees.size());						
	}
	else{
	    System.err.println(back);
	}
	List<Employee> inActiveEmployees = new ArrayList<Employee>();
	// using ldap
	// we turn off this for now till ldap is complete with employees info
	// thne we switch to ldap
	//
	// Set<String> set = Helper.getLdapEmployeeNums(envBean);
	//
	// using NW db for now
	Set<String> set = getNWEmployeeNums();
	System.err.println(" set "+set.size());
	int jj=1;
	if(employees != null && set != null){
	    if(employees.size() > 0 && set.size() > 0){
		for(Employee one:employees){
		    String str = one.getEmployee_num();
		    if(!set.contains(str)){
			inActiveEmployees.add(one);
			System.err.println((jj++)+" Not found "+str);
		    }
		}
		employees = new ArrayList<Employee>();						
		employees = inActiveEmployees;
	    }
	}
	return back;
    }
    public Set<String> getNWEmployeeNums(){
	String msg = "";
	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	Set<String> set = new HashSet<String>();
	/*
	  1 EmployeeJobID
	  2 EffectiveDate
	  3 EffectiveEndDate
	  4 EmployeeID
	  5 GradeId
	  6 GradeType
	  7 GradeStepId
	  8 CycleHours
	  9 DailyHours
	  10 DepartmentId
	  11 RateAmount
	  12 PositionId
	  13 JobId
	  14 JobTitle
	  15 PositionDetailESD
	  16 PositionDetailEED
	  17 IsPrimaryJob
	  18 JobEventReasonId
	  19 PositionNumber
	  20 GradeCode
	  21 EmployeeId
	  22 EmployeeNumber
	  23 DepartmentId
	  24 OrgStructureDescconcatenated
	  25 BenefitGroupId
	  26 xGroupCodeDesc
	  27 PositionTitle
	  28 PositionNumberMasked
	  29 vsEmploymentStatusId
	  30 EmploymentStatus
	  31 EmployeeSSN
	  32 FirstName
	  33 MiddleName
	  34 LastName
	  35 EmployeeName
	  36 Pending
	  37 ProcessStatus
	  38 StandardWeeklyHours
	*/
	String qq = "select ejp.*, g.GradeCode, ei.* , ej.StandardWeeklyHours from HR.vwEmployeeJobWithPosition ejp, "+
	    " HR.vwEmployeeInformation ei, "+
	    " HR.Grade g, "+
	    " HR.EmployeeJob ej "+
	    " where ej.EmployeeID=ejp.EmployeeID "+
	    " and ej.GradeId = g.GradeId "+
	    " and ej.effectiveEndDate >= GETDATE() "+
	    " and ej.effectivedate <= GETDATE() "+						
	    " and ei.[vsEmploymentStatusId] = 258 "+
	    " and ei.employeeID = ejp.employeeID "+
	    " and g.GradeId = ejp.GradeId "+
	    " and ejp.effectivedate <= GETDATE() "+
	    " and ejp.EffectiveEndDate >= GETDATE() "+
	    " and ejp.PositionDetailEED >= GETDATE() "+
	    " and ejp.PositionDetailESD <= GETDATE() "+
	    " and ejp.IsPrimaryJob = 1 ";
	// " and ejp.departmentId = 16 "; // ITS:16
	logger.debug(qq);				
				
	try{
	    con = Helper.getMsSqlDatabaseConnect(envBean);
	    if(con == null){
		msg = "Could not connect to MS Db";
		logger.error(msg);
		return null;
	    }
	    stmt = con.createStatement();
	    rs = stmt.executeQuery(qq);
	    /*
	      ResultSetMetaData rsmd = rs.getMetaData();
	      int numColumns = rsmd.getColumnCount();

	      // Get the column names; column indices start from 1
	      for (int i=1; i<= numColumns; i++) {
	      String str = rsmd.getColumnName(i);
	      System.err.println(i+" "+str);
	      }
	    */
	    int jj=1;
	    while(rs.next()){
		String str  = rs.getString(22); // empl num
		// String str2 = rs.getString(8); // number
		// String str2 = rs.getString(31); // ss 
		String str2 = rs.getString(35); // fullname
		if(str != null && str2 != null){
		    if(!set.contains(str)){
			set.add(str);
			// System.err.println((jj++)+" "+str+" "+str2);
		    }
		}
	    }
	}
	catch(Exception ex){
	    System.err.println(ex);
	}
	finally{
	    Helper.databaseDisconnect(con, stmt, rs);
	}
	return set;
    }
    public String addInactiveEmployeeToCheckList(){
	String msg = "";
	String qq = " insert into inactive_employees values(?,?) ";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	logger.debug(qq);
	try{
	    con = Helper.getConnection();
	    if(con == null){
		msg = "Could not connect ";
		return msg;
	    }
	    pstmt = con.prepareStatement(qq);
	    for(Employee one:employees){
		pstmt.setString(1, id);
		pstmt.setString(2, one.getId());
		pstmt.executeUpdate();
	    }
	}catch(Exception e){
	    msg += e+":"+qq;
	    logger.error(msg);
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	}
	return msg;

    }
    //
    public String doConfirm(){
	String msg = "";
	String qq = " update inactive_checks set confirmed='y' where id=?";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	logger.debug(qq);
	try{
	    con = Helper.getConnection();
	    if(con == null){
		msg = "Could not connect ";
		return msg;
	    }
	    pstmt = con.prepareStatement(qq);
	    pstmt.setString(1, id);
	    pstmt.executeUpdate();
	    confirmed = "y";
	    //
	    // if need to delete some divisions
	    //
	}catch(Exception e){
	    msg += e+":"+qq;
	    logger.error(msg);
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	}
	if(msg.equals("")){
	    msg = doInactive();
	}
	return msg;
    }
    //
    public String doDelete(){
	String msg = "";
	String qq = " delete from inactive_employees where check_id=?";
	String qq2 = " delete from inactive_checks where id=?";				
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	logger.debug(qq);
	//
	try{
	    con = Helper.getConnection();
	    if(con == null){
		msg = "Could not connect ";
		return msg;
	    }
	    pstmt = con.prepareStatement(qq);
	    pstmt.setString(1, id);
	    pstmt.executeUpdate();
	    qq = qq2;
	    pstmt = con.prepareStatement(qq);
	    pstmt.setString(1, id);
	    pstmt.executeUpdate();						
	}catch(Exception e){
	    msg += e+":"+qq;
	    logger.error(msg);
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	}
	return msg;
    }
    //
    // inactivate the employees that were marked as being inactive
    // by the user
    //
    public String doInactive(){
	String msg = "";
	String qq = " update employees set active=null where id=?";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	logger.debug(qq);
	//
	try{
	    con = Helper.getConnection();
	    if(con == null){
		msg = "Could not connect ";
		return msg;
	    }
	    for(String str:add_inactive){
		pstmt = con.prepareStatement(qq);
		pstmt.setString(1, str);
		pstmt.executeUpdate();
	    }
	}catch(Exception e){
	    msg += e+":"+qq;
	    logger.error(msg);
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	}
	return msg;
    }		

}
