package license.model;
/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.sql.*;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import license.list.*;
import license.utils.*;

public class TestSelection implements java.io.Serializable{

    String id = "", select_date="", select_time="", type="",
	elegible="", selected="", percent="", test_run_id="";
    static final long serialVersionUID = 221L;		
    static Logger logger = LogManager.getLogger(TestSelection.class);
    List<Employee> employees = null;
    TestRun testRun = null;
    public TestSelection(){
    }

    public TestSelection(String val){
	setId(val);
    }
    public TestSelection(String val,
			 String val2,
			 String val3,
			 String val4,
			 String val5,
			 String val6,
			 String val7,
			 String val8){
				
	setId(val);
	setSelect_date(val2);
	setSelect_time(val3);
	setType(val4);
				
	setElegible(val5);
	setSelected(val6);
	setPercent(val7);
	setTest_run_id(val8);
    }

    //
    //
    public String getId(){
	return id;
    }
    public String getSelect_date(){
	return select_date;
    }		
    public String getSelect_time(){
	return select_time;
    }
    public String getType(){
	return type;
    }
    public String getElegible(){
	return elegible;
    }
    public String getSelected(){
	return selected;
    }
    public String getPercent(){
	return percent;
    }
    public String getTest_run_id(){
	return test_run_id;
    }
    public TestRun getTestRun(){
	if(testRun == null && !test_run_id.equals("")){
	    TestRun one = new TestRun(test_run_id);
	    String back = one.doSelect();
	    if(back.equals("")){
		testRun = one;
	    }
	}
	return testRun;
    }
    //
    // setters
    //
    public void setId (String val){
	if(val != null)
	    id = val;
    }
    public void setSelect_date(String val){
	if(val != null)
	    select_date = val;
    }
    public void setSelect_time(String val){
	if(val != null)
	    select_time = val;
    }
    public void setType(String val){
	if(val != null)
	    type = val;
    }		
    public void setElegible(String val){
	if(val != null)
	    elegible = val;
    }
    public void setSelected(String val){
	if(val != null)
	    selected = val;
    }
    public void setPercent(String val){
	if(val != null)
	    percent = val;
    }
    public void setTest_run_id (String val){
	if(val != null)
	    test_run_id = val;
    }
    public boolean hasEmployees(){
	getEmployees();
	return employees != null && employees.size() > 0;
    }
    public List<Employee> getEmployees(){
	if(!id.equals("") && employees == null){
	    EmployeeList empl = new EmployeeList();
	    empl.setTest_select_id(id);
	    String back = empl.find();
	    if(back.equals("")){
		List<Employee> ones = empl.getEmployees();
		if(ones != null && ones.size() > 0){
		    employees = ones;
		}
	    }
	}
	return employees;
    }
    public String toString(){
	return id;
    }
    public String doSelect(){
	String msg = "";
	String qq = " select date_format(select_date,'%m/%d/%Y'),select_time,type,elegible,selected,percent,test_run_id from test_selections where id=?";
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
		setSelect_date(rs.getString(1));
		setSelect_time(rs.getString(2));
		setType(rs.getString(3));
		setElegible(rs.getString(4));
		setSelected(rs.getString(5));
		setPercent(rs.getString(6));
		setTest_run_id(rs.getString(7));
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
    public String doSave(){
	String msg = "";
	String qq = " insert into test_selections values(0,now(),now(),?,?, ?,?,?)";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	if(type.equals("") || selected.equals("")){
	    msg = " type or selected is not set";
	    return msg;
	}
	select_date = Helper.getToday();
	logger.debug(qq);
	try{
	    con = Helper.getConnection();
	    if(con == null){
		msg = "Could not connect ";
		return msg;
	    }
	    pstmt = con.prepareStatement(qq);
	    pstmt.setString(1, type);
	    pstmt.setString(2, elegible);

	    pstmt.setString(3, selected);
	    pstmt.setString(4, percent);
	    pstmt.setString(5, test_run_id);
	    pstmt.executeUpdate();
	    qq = "select LAST_INSERT_ID() ";
	    logger.debug(qq);
	    pstmt = con.prepareStatement(qq);
	    rs = pstmt.executeQuery();
	    if(rs.next()){
		id = rs.getString(1);
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
    public String doUpdate(){
	String msg = "";
	String qq = " update test_selections set type=?,elegible=?,selected=?,percent=?,test_run_id=? where id=?";
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
	    pstmt.setString(1, type);
	    pstmt.setString(2, elegible);

	    pstmt.setString(3, selected);
	    pstmt.setString(4, percent);
	    pstmt.setString(5, test_run_id);								
	    pstmt.setString(6, id);						
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
    public String doDelete(){
	String msg = "";
	String qq = " delete from test_selections where id=?";
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
