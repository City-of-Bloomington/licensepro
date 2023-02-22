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

public class Division implements java.io.Serializable{

    String id = "", dept_id="", name="", active="";
    static final long serialVersionUID = 114L;		
    static Logger logger = LogManager.getLogger(Division.class);
    Dept dept = null;
    public Division(){
    }
    public Division(String val){
	setId(val);
    }
    public Division(String val, String val2, String val3, String val4){
	setId(val);
	setDept_id(val2);
	setName(val3);
	setActive(val4);
    }
    //
    //
    public String getId(){
	return id;
    }
    public String getName(){
	return name;
    }
    public String getActive(){
	return active;
    }
    public String getDept_id(){
	return dept_id;
    }		
    public boolean isActive(){
	return !active.equals("");
    }
    public Dept getDept(){
	if(dept == null){
	    Dept dd = new Dept(dept_id);
	    String back = dd.doSelect();
	    if(back.equals("")){
		dept = dd;
	    }
	}
	return dept;
    }
    //
    // setters
    //
    public void setId (String val){
	if(val != null)
	    id = val;
    }
    public void setDept_id (String val){
	if(val != null)
	    dept_id = val;
    }		
    public void setName (String val){
	if(val != null)
	    name = val.trim();
    }
    public void setActive(String val){
	if(val != null)
	    active = val;
    }
    public void setActive(boolean val){
	if(val)
	    active = "y";
    }		
    public String toString(){
	return name;
    }
    public String doSave(){
	String msg = "";
	String qq = " insert into divisions values(0,?,?,'y')";
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
	    pstmt.setString(1, dept_id);
	    pstmt.setString(2, name);
	    pstmt.executeUpdate();
	    //
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
    public String doSelect(){
	String msg = "";
	String qq = " select dept_id,name,active from divisions where id=?";
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
		setDept_id(rs.getString(1));
		setName(rs.getString(2));
		setActive(rs.getString(3));
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
	String qq = " update divisions set dept_id=?,name=?,active=? where id=?";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	if(id.equals("") || dept_id.equals("") || name.equals("")){
	    msg = "id, name or dept id not set";
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
	    pstmt.setString(1, dept_id);						
	    pstmt.setString(2, name);
	    if(active.equals(""))
		pstmt.setNull(3, Types.CHAR);
	    else
		pstmt.setString(3, "y");
	    pstmt.setString(4, id);
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
	String qq = " delete from divisions where id=?";
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
