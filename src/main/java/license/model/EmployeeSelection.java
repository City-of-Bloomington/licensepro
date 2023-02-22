package license.model;
/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.sql.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import license.list.*;
import license.utils.*;

public class EmployeeSelection implements java.io.Serializable{

    String id = "", emp_id="", test_select_id="", ret_duty="", followup="";
    static final long serialVersionUID = 215L;		
    static Logger logger = LogManager.getLogger(EmployeeSelection.class);
    Employee employee = null;
    TestSelection testSelection  = null;
    public EmployeeSelection(){
    }
    public EmployeeSelection(String val){
	setId(val);
    }
    public EmployeeSelection(String val, String val2, String val3,
			     String val4, String val5){
	setId(val);
	setEmp_id(val2);
	setTest_select_id(val3);
	setRet_duty(val4);
	setFollowup(val5);
    }
    //
    //
    public String getId(){
	return id;
    }
    public String getTest_select_id(){
	return test_select_id;
    }
    public String getRet_duty(){
	return ret_duty;
    }
    public String getEmp_id(){
	return emp_id;
    }		
    public String getFollowup(){
	return followup;
    }		
    public Employee getEmployee(){
	if(employee == null && !emp_id.equals("")){
	    Employee ll = new Employee(emp_id);
	    String back = ll.doSelect();
	    if(back.equals("")){
		employee = ll;
	    }
	}
	return employee;
    }
    public TestSelection getTestSelection(){
	if(testSelection == null && !test_select_id.equals("")){
	    TestSelection ts = new TestSelection(test_select_id);
	    String back = ts.doSelect();
	    if(back.equals("")){
		testSelection = ts;
	    }
	}
	return testSelection;
    }
    //
    // setters
    //
    public void setId (String val){
	if(val != null)
	    id = val;
    }
    public void setEmp_id (String val){
	if(val != null)
	    emp_id = val;
    }		
    public void setTest_select_id (String val){
	if(val != null)
	    test_select_id = val;
    }
    public void setRet_duty(String val){
	if(val != null)
	    ret_duty = val;
    }
    public void setFollowup(String val){
	if(val != null)
	    followup = val;
    }		
    public String toString(){
	return id;
    }
    public String doSelect(){
	String msg = "";
	String qq = " select emp_id,test_select_id,ret_duty,followup from employee_selections where id=?";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
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
		setEmp_id(rs.getString(1));
		setTest_select_id(rs.getString(2));
		setRet_duty(rs.getString(3));
		setFollowup(rs.getString(4));
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
	String qq = " insert into employee_selections values(0, ?,?,?,?)";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	if(emp_id.equals("") || test_select_id.equals("")){
	    msg = "employee id or test id not set";
	    return msg;
	}
	logger.debug(qq);
	try{
	    con = Helper.getConnection();
	    if(con == null){
		msg = "Could not connect ";
		return msg;
	    }
	    pstmt = con.prepareStatement(qq);
	    pstmt.setString(1, emp_id);						
	    pstmt.setString(2, test_select_id);
	    if(ret_duty.equals(""))
		pstmt.setNull(3, Types.CHAR);
	    else
		pstmt.setString(3, "y");
	    if(followup.equals(""))
		pstmt.setNull(4, Types.CHAR);
	    else
		pstmt.setString(4, "y");						
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
	String qq = " update employee_selections set emp_id=?,test_select_id=?,retduty=?,followup=? where id=?";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	if(emp_id.equals("") || test_select_id.equals("")){
	    msg = "employee id or test id not set";
	    return msg;
	}
	logger.debug(qq);
	try{
	    con = Helper.getConnection();
	    if(con == null){
		msg = "Could not connect ";
		return msg;
	    }
	    pstmt = con.prepareStatement(qq);
	    pstmt.setString(1, emp_id);						
	    pstmt.setString(2, test_select_id);
	    if(ret_duty.equals(""))
		pstmt.setNull(3, Types.CHAR);
	    else
		pstmt.setString(3, "y");
	    if(followup.equals(""))
		pstmt.setNull(4, Types.CHAR);
	    else
		pstmt.setString(4, "y");						
	    pstmt.setString(5, id);
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
	String qq = " delete from employee_selections where id=?";
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
