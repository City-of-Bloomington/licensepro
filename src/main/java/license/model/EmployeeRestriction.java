package license.model;
/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.sql.*;
import java.util.Set;
import java.util.HashSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import license.list.*;
import license.utils.*;

public class EmployeeRestriction implements java.io.Serializable{

    String id = "", emp_id="", test_select_id="", ret_duty="", followup="";
    static final long serialVersionUID = 217L;		
    static Logger logger = LogManager.getLogger(EmployeeRestriction.class);
    Employee employee = null;
    Set<Integer> restrictions = new HashSet<Integer>();
    public EmployeeRestriction(){
    }
    public EmployeeRestriction(String val){
	setEmp_id(val);
    }
    public EmployeeRestriction(String val,
			       String val2,
			       String val3,
			       String val4,
			       String val5,
			       String val6,
			       String val7,
			       String val8,
			       String val9,
			       String val10,
															 
			       String val11,
			       String val12,
			       String val13,
			       String val14,
			       String val15,
			       String val16,
			       String val17,
			       String val18,
			       String val19,
			       String val20,
															 
			       String val21,
			       String val22
			       ){
	setEmp_id(val);
	setRestr_1(val2 != null && !val2.equals(""));
	setRestr_2(val3 != null && !val3.equals(""));
	setRestr_3(val4 != null && !val4.equals(""));
	setRestr_4(val5 != null && !val5.equals(""));
	setRestr_5(val6 != null && !val6.equals(""));
	setRestr_6(val7 != null && !val7.equals(""));
	setRestr_7(val8 != null && !val8.equals(""));
	setRestr_8(val9 != null && !val9.equals(""));
	setRestr_9(val10 != null && !val10.equals(""));
	setRestr_10(val11 != null && !val11.equals(""));
	setRestr_11(val12 != null && !val12.equals(""));
	setRestr_12(val13 != null && !val13.equals(""));
	setRestr_13(val14 != null && !val14.equals(""));
	setRestr_14(val15 != null && !val15.equals(""));
	setRestr_15(val16 != null && !val16.equals(""));
	setRestr_16(val17 != null && !val17.equals(""));
	setRestr_17(val18 != null && !val18.equals(""));
	setRestr_18(val19 != null && !val19.equals(""));
	setRestr_19(val20 != null && !val20.equals(""));
	setRestr_20(val21 != null && !val21.equals(""));
	setRestr_21(val22 != null && !val22.equals(""));				
    }
    //
    //
    public String getEmp_id(){
	return emp_id;
    }		
    public boolean getRestr_1(){
	return restrictions.contains(new Integer(1));
    }
    public boolean getRestr_2(){
	return restrictions.contains(new Integer(2));
    }
    public boolean getRestr_3(){
	return restrictions.contains(new Integer(3));
    }
    public boolean getRestr_4(){
	return restrictions.contains(new Integer(4));
    }
    public boolean getRestr_5(){
	return restrictions.contains(new Integer(5));
    }
    public boolean getRestr_6(){
	return restrictions.contains(new Integer(6));
    }
    public boolean getRestr_7(){
	return restrictions.contains(new Integer(7));
    }
    public boolean getRestr_8(){
	return restrictions.contains(new Integer(8));
    }
    public boolean getRestr_9(){
	return restrictions.contains(new Integer(9));
    }
    public boolean getRestr_10(){
	return restrictions.contains(new Integer(10));
    }
    public boolean getRestr_11(){
	return restrictions.contains(new Integer(11));
    }
    public boolean getRestr_12(){
	return restrictions.contains(new Integer(12));
    }
    public boolean getRestr_13(){
	return restrictions.contains(new Integer(13));
    }
    public boolean getRestr_14(){
	return restrictions.contains(new Integer(14));
    }
    public boolean getRestr_15(){
	return restrictions.contains(new Integer(15));
    }
    public boolean getRestr_16(){
	return restrictions.contains(new Integer(16));
    }
    public boolean getRestr_17(){
	return restrictions.contains(new Integer(17));
    }
    public boolean getRestr_18(){
	return restrictions.contains(new Integer(18));
    }
    public boolean getRestr_19(){
	return restrictions.contains(new Integer(19));
    }
    public boolean getRestr_20(){
	return restrictions.contains(new Integer(20));
    }
    public boolean getRestr_21(){
	return restrictions.contains(new Integer(21));
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
    //
    // setters
    //
    public void setEmp_id (String val){
	if(val != null)
	    emp_id = val;
    }
    public void setRestr_1 (boolean val){
	if(val)
	    restrictions.add(new Integer(1));
    }
    public void setRestr_2 (boolean val){
	if(val)
	    restrictions.add(new Integer(2));
    }
    public void setRestr_3 (boolean val){
	if(val)
	    restrictions.add(new Integer(3));
    }
    public void setRestr_4 (boolean val){
	if(val)
	    restrictions.add(new Integer(4));
    }
    public void setRestr_5 (boolean val){
	if(val)
	    restrictions.add(new Integer(5));
    }
    public void setRestr_6 (boolean val){
	if(val)
	    restrictions.add(new Integer(6));
    }
    public void setRestr_7 (boolean val){
	if(val)
	    restrictions.add(new Integer(7));
    }
    public void setRestr_8 (boolean val){
	if(val)
	    restrictions.add(new Integer(8));
    }
    public void setRestr_9 (boolean val){
	if(val)
	    restrictions.add(new Integer(9));
    }
    public void setRestr_10 (boolean val){
	if(val)
	    restrictions.add(new Integer(10));
    }
    public void setRestr_11 (boolean val){
	if(val)
	    restrictions.add(new Integer(11));
    }
    public void setRestr_12 (boolean val){
	if(val)
	    restrictions.add(new Integer(12));
    }
    public void setRestr_13 (boolean val){
	if(val)
	    restrictions.add(new Integer(13));
    }
    public void setRestr_14 (boolean val){
	if(val)
	    restrictions.add(new Integer(14));
    }
    public void setRestr_15 (boolean val){
	if(val)
	    restrictions.add(new Integer(15));
    }
    public void setRestr_16 (boolean val){
	if(val)
	    restrictions.add(new Integer(16));
    }
    public void setRestr_17 (boolean val){
	if(val)
	    restrictions.add(new Integer(17));
    }
    public void setRestr_18 (boolean val){
	if(val)
	    restrictions.add(new Integer(18));
    }
    public void setRestr_19 (boolean val){
	if(val)
	    restrictions.add(new Integer(19));
    }
    public void setRestr_20 (boolean val){
	if(val)
	    restrictions.add(new Integer(20));
    }
    public void setRestr_21 (boolean val){
	if(val)
	    restrictions.add(new Integer(21));
    }		
    public String toString(){
	return emp_id;
    }
    public String doSave(){
	String msg = "";
	String qq = " insert into employee_restrictions values(?,?) ";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	if(emp_id.equals("")){
	    msg = "Employee id not set ";
	    return msg;
	}
	msg = doDelete(); 				
	if(restrictions.size() == 0) return "";

	try{
	    con = Helper.getConnection();
	    if(con == null){
		msg = "Could not connect ";
		return msg;
	    }
	    pstmt = con.prepareStatement(qq);
	    for(Integer one:restrictions){
		pstmt.setString(1, emp_id);
		pstmt.setInt(2, one.intValue());
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
    public String doSelect(){
	String msg = "";
	String qq = " select restr_id from employee_restrictions where emp_id=?";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	//
	// we delete the old records first then we add the new ones
	//
	logger.debug(qq);
	try{
	    con = Helper.getConnection();
	    if(con == null){
		msg = "Could not connect ";
		return msg;
	    }
	    pstmt = con.prepareStatement(qq);
	    pstmt.setString(1, emp_id);
	    rs = pstmt.executeQuery();	
	    while(rs.next()){
		int item = rs.getInt(1);
		System.err.println("adding "+item);
		restrictions.add(new Integer(item));
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

    public String doDelete(){
	String msg = "";
	String qq = " delete from employee_restrictions where emp_id=?";
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
	    pstmt.setString(1, emp_id);
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
