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

/**
 * User class
 *
 */

public class User implements java.io.Serializable{

    static final long serialVersionUID = 135L;		
    static Logger logger = LogManager.getLogger(User.class);		
    String userid="", fullname="", role="", id="", active="",
	type=""; // Plan Eng All
    boolean userExists = false;

    String errors = "";
    public User(){
    }		
    public User(String val){
	setId(val);
    }	
    public User(String val, String val2){
	setId(val);
	setUserid(val2);
    }
    public User(String val, String val2,
		String val3, String val4,
		String val5){
	setValues(val, val2, val3, val4, val5);
    }
    void setValues(String val,
		   String val2,
		   String val3,
		   String val4,
		   String val5){
	setId(val);
	setUserid(val2);
	setFullname(val3);
	setRole(val4);
	setActive(val5 != null && !val5.equals(""));
	userExists = true;
    }
    //
    public boolean hasRole(String val){
	if(role.indexOf(val) > -1) return true;
	return false;
    }
    public boolean canEdit(){
	return hasRole("Edit");
    }
    public boolean canDelete(){
	return (hasRole("Delete") || isAdmin());
    }
    public boolean isAdmin(){
	return hasRole("Admin");
    }
    public boolean hasUserid(){
	return !userid.equals("");
    }
    //
    // getters
    //
    public String getId(){
	return id;
    }	
    public String getUserid(){
	return userid;
    }
    public String getFullname(){
	return fullname;
    }
    public String getRole(){
	return role;
    }
    public String getRoleInfo(){
	String ret = "View Only";
	if(role.equals("Edit"))
	    ret = "Edit";
	if(role.equals("Edit:Delete"))
	    ret = "Edit & Delte";
	if(role.indexOf("Admin") > -1)
	    ret = "All (Admin)";
	return ret;
    }
		
    public boolean isActive(){
	if(id.equals("")) active = "y";
	return !active.equals("");
    }
    //
    // setters
    //
    public void setId(String val){
	if(val != null)
	    id = val;
    }	
    public void setUserid (String val){
	if(val != null)
	    userid = val;
    }
    public void setFullname (String val){
	if(val != null)
	    fullname = val;
    }
    public void setRole (String val){
	if(val != null && !val.equals("-1"))
	    role = val;
    }
    public void setActive (boolean val){
	if(val)
	    active = "y";
    }
    public boolean userExists(){
	return userExists;
    }
    public String toString(){
	return fullname;
    }
    @Override
    public int hashCode() {
	int hash = 7, id_int = 0;
	if(!id.equals("")){
	    try{
		id_int = Integer.parseInt(id);
	    }catch(Exception ex){}
	}
	hash = 67 * hash + id_int;
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
	final User other = (User) obj;
	return this.id.equals(other.id);
    }	
    public String doSelect(){
	String msg="";
	PreparedStatement pstmt = null;
	Connection con = null;
	ResultSet rs = null;		
	String qq = "select * from users ", qw="";
	if(!userid.equals("")){
	    qw += " where userid = ?";
	}
	else if(!id.equals("")){
	    qw += " where id = ?";
	}
	qq += qw;
	System.err.println(qq);
	System.err.println("userid "+userid);				
	logger.debug(qq);
	con = Helper.getConnection();
	if(con == null){
	    msg += " could not connect to database";
	    System.err.println(msg);
	    return msg;
	}		
	try{
	    pstmt = con.prepareStatement(qq);
	    if(!userid.equals(""))
		pstmt.setString(1, userid);
	    else
		pstmt.setString(1, id);				
	    rs = pstmt.executeQuery();
	    if(rs.next()){
		String str = rs.getString(1);
		id = str;
		str = rs.getString(2);
		if(str != null)
		    userid = str;
		str = rs.getString(3);
		if(str != null)
		    fullname = str;
		str = rs.getString(4);
		if(str != null)
		    role = str;
		str = rs.getString(5);
		if(str != null)
		    active = str;
		userExists = true;
	    }
	}
	catch(Exception ex){
	    msg += " "+ex;
	    logger.error(ex+":"+qq);
	    System.err.println(ex+":"+qq);
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	}
	return msg;
    }
    public String doSave(){
	String msg = "";
	String qq = " insert into users values(0,?,?,?,'y')";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	if(userid.equals("") || fullname.equals("")){
	    msg = " userid or fullname not set";
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
	    pstmt.setString(1, userid);
	    pstmt.setString(2, fullname);
	    if(role.equals(""))
		pstmt.setNull(3, Types.VARCHAR);
	    else
		pstmt.setString(3, role);
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
	String qq = " update users set userid=?,fullname=?, role=?,active=? where id=?";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	if(userid.equals("") || fullname.equals("")){
	    msg = " userid or fullname not set";
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
	    pstmt.setString(1, userid);
	    pstmt.setString(2, fullname);
	    if(role.equals(""))
		pstmt.setNull(3, Types.VARCHAR);
	    else
		pstmt.setString(3, role);
	    if(active.equals(""))
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
	String qq = " delete from users where id=?";
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
