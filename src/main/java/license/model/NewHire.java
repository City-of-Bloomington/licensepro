package license.model;
/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */

import java.sql.*;
import java.text.*;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Hashtable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import license.list.*;
import license.utils.*;

public class NewHire{

    String id = "", date="", confirmed="";
    EnvBean envBean = null;
    static final long serialVersionUID = 115L;
    static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    static Logger logger = LogManager.getLogger(NewHire.class);
    List<Employee> employees = null;
    List<Employee> new_hires = null;
    String[] add_hire = null;
    public NewHire(){
    }
    public NewHire(String val){
	setId(val);
    }
    public NewHire(String val, String val2){
	setId(val);
	setDate(val2);
    }
    public String getId(){
	return id;
    }
    public String getDate(){
	if(date.equals("")){
	    date = Helper.getToday();
	}
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
	    EmployeeList empl = new EmployeeList();
	    empl.setNewhire_id(id);
	    empl.setNoLimit();
	    empl.setSortBy("d.name,r.lname");
	    String back = empl.find();
	    if(back.equals("")){
		List<Employee> emps = empl.getEmployees();
		if(emps != null && emps.size() > 0){
		    employees = emps;
		    System.err.println(" emps "+emps.size());
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
    public void setEnvBean(EnvBean val){
	if(val != null)
	    envBean = val;
    }
    public void setAdd_hire(String[] vals){
	if(vals != null)
	    add_hire = vals;
    }		
    //
    public String doSelect(){
				
	String msg = "";
	String qq = " select date_format(date,'%m/%d/%Y') from new_hires where id=?";
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
	String qq = " insert into new_hires values(0,now())";
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
	    msg = findNewHires();
	    if(msg.equals("")){
		if(new_hires != null && new_hires.size() > 0){
		    msg = addNewHires();
		}
	    }
	}
	return msg;
    }
    public String findNewHires(){
	String back = "";

	new_hires = new ArrayList<Employee>();
	//
	// using NW db for now
	List<Employee> pot_hires = getNwNewHires();
	// System.err.println( " size "+pot_hires.size());
	//
	// current employee_num set
	Set<String> set = getEmployeeNumSet();
	// System.err.println(set);
	//
	int jj=1;
	if(pot_hires != null && set != null){
	    if(pot_hires.size() > 0 && set.size() > 0){
		for(Employee one:pot_hires){
		    String str = one.getEmployee_num();
		    if(!set.contains(str)){
			back += one.doSave();
			new_hires.add(one);
		    }
		}
	    }
	}
	return back;
    }
    public List<Employee> getNwNewHires(){
	String msg = "";
	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	List<Employee> list = new ArrayList<Employee>();
	Set<String> set = new HashSet<String>();
	//
	// since last 6 months
	String lyDate = Helper.getDateMonthsFromNow(-48);
	Hashtable<String, String> table = getDept_correspond();
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
	  39 DateOfBirth
	  40 lic_number
	  41 exp_date
	  42 EntryValue (state 2 letters)
	  43 license type (Operator, CDL, etc)
	*/
	String qq = "select ejp.*, g.GradeCode, ei.* , ej.StandardWeeklyHours, "+
	    " convert(varchar, ed.DateOfBirth,101), "+ // mm/dd/yyyy format
	    " el.licenseNumber,convert(varchar,el.ExpirationDate,101), "+
	    " vst.EntryValue, "+ //state code, vse.description (state name)
	    " vtp.EntryValue "+ // licenseType 
	    " from HR.vwEmployeeJobWithPosition ejp, "+
	    " HR.vwEmployeeInformation ei, "+
	    " HR.Grade g, "+
	    " HR.EmployeeJob ej, "+
	    " HR.EmployeeDemographics ed, "+
            " HR.employeelicense el, "+
	    " dbo.ValidationSetEntry vst, "+
	    " dbo.ValidationSetEntry vtp "+
	    " where ej.EmployeeID=ejp.EmployeeID "+
	    " and el.EmployeeID=ejp.EmployeeID "+
	    " and vst.SetID = 3 "+
	    " and el.vsStateId = vst.EntryID "+
	    " and vtp.SetID = 41 "+
	    " and el.vsLicenseTypeId = vtp.EntryID "+									
	    " and ei.EmployeeID=ejp.EmployeeID "+
	    " and ej.EmployeeID=ed.EmployeeID "+
	    " and ej.GradeId = g.GradeId "+
	    " and ej.effectiveEndDate >= GETDATE() "+
	    " and ej.effectivedate >= '"+lyDate+"'"+						
	    " and ei.[vsEmploymentStatusId] = 258 "+
	    " and g.GradeId = ejp.GradeId "+
	    " and ejp.effectivedate >= '"+lyDate+"'"+
	    " and ejp.EffectiveEndDate >= GETDATE() "+
	    " and ejp.PositionDetailEED >= GETDATE() "+
	    " and ejp.PositionDetailESD <= GETDATE() "+
	    " and ejp.IsPrimaryJob = 1 ";
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
	    int jj=1;
	    while(rs.next()){
		/**
		for(int j=1;j<45;j++){
		    String strr = rs.getString(j);
		    System.err.println(j+" "+strr);
		}
		*/
		String str  = rs.getString(10); // dept_id
		String strd = rs.getString(24); // dept name 
		String str2 = rs.getString(32); // first name
		String str3 = rs.getString(33); // middle
		String str4 = rs.getString(34); // last
		String str5 = rs.getString(22); // employee_num
		String str7 = rs.getString(40); // dob
		String str8 = rs.getString(41); // license num		
		String str6 = rs.getString(42); // exp_date
		String str9 = rs.getString(43); // state code
		String str10 = rs.getString(44); // license Type 
		if(set.contains(str5)) continue;
		set.add(str5);
		if(table.containsKey(str)){
		    str = table.get(str);
		}
		else{
		    System.err.println(str+":"+strd+" dept not found");
		    str = null;
		}
		Employee one = new Employee();
		one.setDept_id(str);
		one.setFname(str2);
		one.setMname(str3);
		one.setLname(str4);
		one.setEmployee_num(str5);
		one.setDob(str7);
		if(str9.equals("IN")){
		    one.setIndianaLic_number(str8);
		}
		else{
		    one.setLic_number(str8);
		}
		one.setExp_date(str6);
		one.setState_id(str9);
		// System.err.println(" type "+str10);
		if(str10.startsWith("Operator")){
		    one.setType_id("62");
		    str10 = "62";
		}
		else if(str10.equals("CDL")){
		    one.setType_id("18"); //4:CDL A, 18:CDL B, 49:CDL C
		    one.setCdl_required(true);
		    str10 = "18";
		}
		// System.err.println(" type "+str10);								
		list.add(one);
	    }
	}
	catch(Exception ex){
	    System.err.println(ex);
	}
	finally{
	    Helper.databaseDisconnect(con, stmt, rs);
	}
	return list;
    }
    Hashtable<String, String> getDept_correspond(){
	Hashtable<String, String> table = new Hashtable<String, String>(50);
	String msg = "";
	String qq = " select * from nwdept_to_depts ";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	logger.debug(qq);
	try{
	    con = Helper.getConnection();
	    if(con == null){
		msg = "Could not connect ";
		logger.error(msg);
		return table;
	    }
	    pstmt = con.prepareStatement(qq);
	    rs = pstmt.executeQuery();
	    while(rs.next()){
		String str = rs.getString(1);
		String str2 = rs.getString(2);
		table.put(str, str2);
	    }
	}catch(Exception e){
	    msg += e+":"+qq;
	    logger.error(msg);
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	}
	return table;
    }
    Set<String> getEmployeeNumSet(){
	String msg = "";
	String qq = " select employee_num from employees where employee_num is not null ";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Set<String> set = new HashSet<String>();
	logger.debug(qq);
	try{
	    con = Helper.getConnection();
	    if(con == null){
		msg = "Could not connect ";
		logger.error(msg);
		return null;
	    }
	    pstmt = con.prepareStatement(qq);
	    rs = pstmt.executeQuery();
	    while(rs.next()){
		String str = rs.getString(1);
		set.add(str);
	    }
	}catch(Exception e){
	    msg += e+":"+qq;
	    logger.error(msg);
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	}				
	return set;
    }
    public String addNewHires(){
	String msg = "";
	String qq = " insert into newhire_employees values(?,?) ";
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
	    for(Employee one:new_hires){
		pstmt.setString(1, id);
		pstmt.setString(2, one.getId());
		pstmt.executeUpdate();
	    }
	    new_hires = null;
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
    public String doDelete(){
	String msg = "";
	String qq = "delete from employees e,newhire_employees n where e.id=n.emp_id and n.new_hire_id=? ";
	String qq2 = " delete from newhire_employees where newhire_id=?";
	String qq3 = " delete from new_hires where id=?";				
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
	    qq = qq3;
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

}
