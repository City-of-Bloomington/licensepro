package license.model;
/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.util.*;
import java.sql.*;
import java.io.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import license.list.*;
import license.utils.*;

public class Employee implements java.io.Serializable{

    static final long serialVersionUID = 10L;	
   
    static Logger logger = LogManager.getLogger(Employee.class);
    static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    String id="", fname="", mname="",lname="", dob="", lic_number="";
    String exp_month = "", exp_year="", exp_date="";
    String div_id ="", dept_id="", type_id="", state_id="";
    String driver="",use_vehicle="",city_vehicle="",own_vehicle="",
	cdl_required="";
    String date = "", date2="", userid="", userid2="", active="",
	employee_num="";// from New World /Ldap
		
    //
    // objects
    //
    User user = null, user2 = null;
    Type type = null, state = null;
    Dept dept = null;
    List<Restriction> restrictions = null;
    Division division = null;

    public Employee(){
    }	
    public Employee(String val){
	setId(val);
    }	
    public Employee(
		    String val,
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
	setValues( val,
		   val2,
		   val3,
		   val4,
		   val5,
		   val6,
		   val7,
		   val8,
		   val9,
		   val10,
		   val11,
		   val12,
		   val13,
		   val14,
		   val15,
		   val16,
		   val17,
		   val18,
		   val19,
		   val20,
		   val21,
		   val22
		   );
		
    }
    void setValues(
		   String val,
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
	setId(val);
	setFname(val2);
	setMname(val3);
	setLname(val4);
	setDiv_id(val5);
	setDept_id(val6);
	setLic_number(val7);
	setDob(val8);
	setDriver(val9 != null && !val9.equals(""));
	setUse_vehicle(val10 != null && !val10.equals(""));
	setCity_vehicle(val11 != null && !val11.equals(""));
	setOwn_vehicle(val12 != null && !val12.equals(""));
	setCdl_required(val13 != null && !val13.equals(""));
	setState_id(val14);
	setType_id(val15);
	setUserid(val16);
	setDate(val17);
	setUserid2(val18);
	setDate2(val19);
	setActive(val20 != null && !val20.equals(""));
	setExp_date(val21);
	setEmployee_num(val22);
    }

    public void setId(String val){
	if(val != null)
	    id = val;
    }
    public void setEmployee_num(String val){
	if(val != null)
	    employee_num = val;
    }		
    public void setFname(String val){
	if(val != null)
	    fname = val.trim();
    }
    public void setMname(String val){
	if(val != null)
	    mname = val.trim();
    }
    public void setLname(String val){
	if(val != null)
	    lname = val.trim();
    }
    public void setDiv_id(String val){
	if(val != null && !val.equals("-1"))
	    div_id = val;
    }
    public void setDept_id(String val){
	if(val != null && !val.equals("-1"))
	    dept_id = val;
    }		
    public void setType_id(String val){
	if(val != null && !val.equals("-1"))
	    type_id = val;
    }	
    public void setLic_number(String val){
	if(val != null)
	    lic_number = val.trim();
    }
    // indiana lic format ####-##-####
    public void setIndianaLic_number(String val){
	if(val != null){
	    if(val.indexOf("-") > 0)
		lic_number = val.trim();
	    else{
		try{
		    lic_number = val.substring(0,4)+"-"+val.substring(4,6)+"-"+val.substring(6);
		}catch(Exception ex){
		    lic_number = val;
		}
	    }
	}
						
    }		
    public void setDob(String val){
	if(val != null)
	    dob = val;
    }
    public void setState_id(String val){
	if(val != null && !val.equals("-1")){
	    if(val.length() > 2){
		val = val.substring(0,2);
	    }
	    state_id = val;
	}
    }
    public void setExp_date(String val){
	if(val != null)
	    exp_date = val;
    }		
    public void setDate(String val){
	if(val != null)
	    date = val;
    }
    public void setDate2(String val){
	if(val != null)
	    date2 = val;
    }
    public void setUserid(String val){
	if(val != null)
	    userid = val;
    }
    public void setUserid2(String val){
	if(val != null)
	    userid2 = val;
    }
		
    public void setUse_vehicle(boolean val){
	if(val)
	    use_vehicle = "y";
    }
    public void setCity_vehicle(boolean val){
	if(val)
	    city_vehicle = "y";
    }
    public void setOwn_vehicle(boolean val){
	if(val)
	    own_vehicle = "y";
    }		
    public void setCdl_required(boolean val){
	if(val)
	    cdl_required = "y";
    }
    public void setDriver(boolean val){
	if(val)
	    driver = "y";
    }
    public void setActive(boolean val){
	if(val)
	    active = "y";
    }		
    public void setUser(User val){
	if(val != null)
	    user = val;
    }
    //
    public String getId(){
	return id;
    }
    public String getEmployee_num(){
	return employee_num;
    }		
    public String getFname(){
	return fname;
    }
    public String getLname(){
	return lname;
    }
    public String getMname(){
	return mname;
    }
    public String getFullName(){
	String ret = lname;
	ret += ", ";
	ret += fname;				
	if(!mname.equals("")){
	    ret += " ";
	    ret += mname;
	}
	return ret;
    }		
    public String getDiv_id(){
	return div_id;
    }
    public String getDept_id(){
	if(dept_id.equals("") && !div_id.equals("")){
	    getDivision();
	    if(division != null){
		dept_id=division.getDept_id();
	    }
	}
	return dept_id;
    }		
    public String getType_id(){
		
	return type_id;
    }
    public String getLic_number(){
		
	return lic_number;
    }
    public String getDob(){
	return dob;
    }
    public String getState_id(){
	return state_id;
    }
    public String getDate(){
	return date;
    }
    public String getDate2(){
	return date2;
    }
    public String getUserid(){
	return userid;
    }
    public String getUserid2(){
	return userid2;
    }
    public String getExp_date(){
	if(exp_date.equals("") && !exp_year.equals("")){
	    exp_date = exp_month+"/01/"+exp_year;
	}
	return exp_date;
    }		
    public String getDeptDivNames(){
	String ret = "";
	if(!id.equals("")){
	    getDept();
	    if(dept != null){
		ret += dept.getName();
	    }
	    getDivision();
	    if(division != null){
		ret += "/"+division.getName();
	    }
	}
	return ret;
    }
    public boolean getDriver(){
	return !driver.equals("");
    }
    public boolean getUse_vehicle(){
	return !use_vehicle.equals("");
    }
    public boolean getCity_vehicle(){
	return !city_vehicle.equals("");
    }
    public boolean getOwn_vehicle(){
	return !own_vehicle.equals("");
    }		
    public boolean getCdl_required(){
	return !cdl_required.equals("");
    }
    public boolean getActive(){
	if(id.equals("")) active = "y";
	return !active.equals("");
    }
    public boolean isActive(){
	if(id.equals("")) active = "y";				
	return !active.equals("");
    }		
    public String toString(){
	String ret = lname;
	ret += ", "+fname;
	if(!mname.equals("")){
	    ret += " "+mname;
	}
	return ret;
    }
    public Type getType(){
	if(!type_id.equals("") && type == null){
	    Type one = new Type(type_id, null, "types");
	    String back = one.doSelect();
	    if(back.equals("")){
		type = one;
	    }
	}
	return type;
    }
    public Type getState(){
	if(!state_id.equals("") && state == null){
	    Type one = new Type(state_id, null, "states");
	    String back = one.doSelect();
	    if(back.equals("")){
		state = one;
	    }
	}
	return state;
    }
    public Dept getDept(){
	if(!dept_id.equals("") && dept == null){
	    Dept one = new Dept(dept_id);
	    String back = one.doSelect();
	    if(back.equals("")){
		dept = one;
	    }
	}
	return dept;
    }
    public Division getDivision(){
	if(!div_id.equals("") && division == null){
	    Division one = new Division(div_id);
	    String back = one.doSelect();
	    if(back.equals("")){
		division = one;
		if(dept == null){
		    dept = division.getDept();
		    if(dept != null){
			dept_id = dept.getId();
		    }
		}
	    }
	}
	return division;
    }
    public List<Restriction> getRestrictions(){
	if(restrictions == null && !id.equals("")){
	    RestrictionList rl = new RestrictionList(id);
	    String back = rl.find();
	    if(back.equals("")){
		List<Restriction> ones = rl.getRestrictions();
		if(ones != null && ones.size() > 0){
		    restrictions = ones;
		}
	    }
	}
	return restrictions;
    }
    public void setInActive(){
	active = "";
    }
    //
    @Override
    public int hashCode() {
	int hash = 3, id_int = 0;
	if(!id.equals("")){
	    try{
		id_int = Integer.parseInt(id);
	    }catch(Exception ex){}
	}
	hash = 53 * hash + id_int; 
	return hash;
    }
    @Override
    public boolean equals(Object obj) {
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	final Employee other = (Employee) obj;
	return this.id.equals(other.id);
    }
    //
    public String doSave(){

	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String msg = "";
	if(date.equals(""))
	    date = Helper.getToday();
	String qq = "insert into employees values(0,?,?,?,?, "+
	    "?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?) ";
	active = "y";
	logger.debug(qq);
	try{
	    con = Helper.getConnection();
	    if(con == null){
		msg = "Could not connect ";
		return msg;
	    }
	    pstmt = con.prepareStatement(qq);
	    msg = fillData(pstmt);
	    if(msg.equals("")){
		pstmt.executeUpdate();
		qq = "select LAST_INSERT_ID() ";
		logger.debug(qq);
		pstmt = con.prepareStatement(qq);
		rs = pstmt.executeQuery();
		if(rs.next()){
		    id = rs.getString(1);
		}
	    }
	}
	catch(Exception ex){
	    msg += ex+":"+qq;
	    logger.error(msg);
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	}
	return msg;
    }
    String fillData(PreparedStatement pstmt){
	String msg = "";
	int jj=1;
	try{
	    pstmt.setString(jj++, fname);
	    if(mname.equals(""))
		pstmt.setNull(jj++, Types.VARCHAR);		
	    else
		pstmt.setString(jj++, mname);
	    pstmt.setString(jj++, lname);						
	    if(div_id.equals(""))
		pstmt.setNull(jj++, Types.INTEGER);
	    else
		pstmt.setString(jj++, div_id);
	    if(dept_id.equals(""))
		pstmt.setNull(jj++, Types.INTEGER);
	    else
		pstmt.setString(jj++, dept_id);						
	    if(lic_number.equals(""))
		pstmt.setNull(jj++, Types.VARCHAR);
	    else
		pstmt.setString(jj++, lic_number);
	    if(!dob.equals(""))
		pstmt.setDate(jj++, new java.sql.Date(dateFormat.parse(dob).getTime()));
	    else
		pstmt.setNull(jj++, Types.DATE);						
	    if(!driver.equals(""))
		pstmt.setString(jj++, driver);
	    else
		pstmt.setNull(jj++, Types.CHAR);
	    if(!use_vehicle.equals(""))
		pstmt.setString(jj++, use_vehicle);
	    else
		pstmt.setNull(jj++, Types.CHAR);
	    if(!city_vehicle.equals(""))
		pstmt.setString(jj++, city_vehicle);
	    else
		pstmt.setNull(jj++, Types.CHAR);
	    if(!own_vehicle.equals(""))
		pstmt.setString(jj++, own_vehicle);
	    else
		pstmt.setNull(jj++, Types.CHAR);						
	    if(!cdl_required.equals(""))
		pstmt.setString(jj++, cdl_required);
	    else
		pstmt.setNull(jj++, Types.CHAR);
	    if(!state_id.equals(""))
		pstmt.setString(jj++, state_id);
	    else
		pstmt.setNull(jj++, Types.CHAR);
	    if(!type_id.equals(""))
		pstmt.setString(jj++, type_id);
	    else
		pstmt.setNull(jj++, Types.INTEGER);
	    if(!userid.equals(""))
		pstmt.setString(jj++, userid);
	    else
		pstmt.setNull(jj++, Types.VARCHAR);						
	    // 
	    if(!date.equals(""))
		pstmt.setDate(jj++, new java.sql.Date(dateFormat.parse(date).getTime()));
	    else
		pstmt.setNull(jj++, Types.DATE);

	    if(!userid2.equals(""))
		pstmt.setString(jj++, userid2);
	    else
		pstmt.setNull(jj++, Types.VARCHAR);
	    if(!date2.equals(""))
		pstmt.setDate(jj++, new java.sql.Date(dateFormat.parse(date2).getTime()));
	    else
		pstmt.setNull(jj++, Types.DATE);
	    if(!active.equals(""))
		pstmt.setString(jj++, "y");
	    else
		pstmt.setNull(jj++, Types.CHAR);
	    if(!exp_date.equals(""))
		pstmt.setDate(jj++, new java.sql.Date(dateFormat.parse(exp_date).getTime()));
	    else
		pstmt.setNull(jj++, Types.DATE);
	    if(!employee_num.equals(""))
		pstmt.setString(jj++, employee_num);
	    else
		pstmt.setNull(jj++, Types.CHAR);
	}
				
	catch(Exception ex){
	    msg += ex;
	    logger.error(msg);
	}
	return msg;
    }
    //
    public String doUpdate(){
	//
	String msg = "";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	int cc = 22;
	String qq = "update employees set fname=?,mname=?,lname=?,"+
	    "div_id=?,dept_id=?,lic_number=?,dob=?,"+
	    "driver=?,use_vehicle=?,city_vehicle=?,own_vehicle=?,"+
	    "cdl_required=?,state_id=?,type_id=?,userid=?,"+
	    "date=?,userid2=?,date2=?,active=?,exp_date=?,employee_num=? ";
	qq += "where id=?";
	logger.debug(qq);
	try{
	    con = Helper.getConnection();
	    if(con == null){
		msg = "Could not connect ";
		return msg;
	    }
	    pstmt = con.prepareStatement(qq);
	    fillData(pstmt);
	    pstmt.setString(cc, id);
	    pstmt.executeUpdate();
	}
	catch(Exception ex){
	    msg += ex+":"+qq;
	    logger.error(msg);
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	}
	return msg;
			
    }
    public String doDelete(){
	//
	String msg = "";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	//
	String qq = "delete from employees where id=?";				
	//
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
	    //
	    // reset the values
	    //
	    id =""; type_id=""; state_id="";div_id="";dept_id="";
	    dob="";lname="";mname="";fname="";
	    lic_number="";date = ""; date2=""; userid="";userid2="";
	    driver="";use_vehicle=""; own_vehicle=""; city_vehicle="";
	    cdl_required=""; active="";
	    exp_date=""; employee_num="";
	}
	catch(Exception ex){
	    msg += ex+":"+qq;
	    logger.error(msg);
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	}
	return msg;
			
    }	

    public String doSelect(){
		
	String qq = "select r.id,"+
	    "r.fname,"+
	    "r.mname,"+
	    "r.lname,"+
	    "r.div_id,"+
	    "r.dept_id,"+
	    "r.lic_number,"+
	    "date_format(r.dob,'%m/%d/%Y'),"+
	    "r.driver,"+
	    "r.use_vehicle,"+
	    "r.city_vehicle,"+
	    "r.own_vehicle,"+
	    "r.cdl_required,"+
	    "r.state_id,"+
	    "r.type_id,"+
	    "r.userid,"+
	    "date_format(r.date,'%m/%d/%Y'),"+
	    "r.userid2,"+
	    "date_format(r.date2,'%m/%d/%Y'), "+
	    "r.active, "+
	    "date_format(r.exp_date,'%m/%d/%Y'), "+
	    "r.employee_num "+
	    " from employees r where r.id=? ";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String msg = "";
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
		setValues(rs.getString(1),
			  rs.getString(2),
			  rs.getString(3),
			  rs.getString(4),
			  rs.getString(5),
			  rs.getString(6),
			  rs.getString(7),
			  rs.getString(8),
			  rs.getString(9),
			  rs.getString(10),
			  rs.getString(11),
			  rs.getString(12),
			  rs.getString(13),
			  rs.getString(14),
			  rs.getString(15),
			  rs.getString(16),
			  rs.getString(17),
			  rs.getString(18),
			  rs.getString(19),
			  rs.getString(20),
			  rs.getString(21),
			  rs.getString(22)
			  );
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





































