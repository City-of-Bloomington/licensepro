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

public class Restriction implements java.io.Serializable{

    String id = "", name="", type="", code="";
    static final long serialVersionUID = 116L;		
    static Logger logger = LogManager.getLogger(Restriction.class);
    public Restriction(){
    }
    public Restriction(String val){
	setId(val);
    }
    public Restriction(String val, String val2, String val3, String val4){
	setId(val);
	setType(val2);
	setCode(val3);
	setName(val4);
    }
    //
    //
    public String getId(){
	return id;
    }
    public String getName(){
	return name;
    }
    public String getCode(){
	return code;
    }
    public String getCodeAndName(){
	String ret = code;
	if(!name.equals("")){
	    ret += " - "+name;
	}
	return ret;
    }
    public String getType(){
	return type;
    }
    //
    // setters
    //
    public void setId (String val){
	if(val != null)
	    id = val;
    }
    public void setName (String val){
	if(val != null)
	    name = val;
    }
    public void setType(String val){
	if(val != null)
	    type = val;
    }
    public void setCode(String val){
	if(val != null)
	    code = val;
    }		
    public String toString(){
	return name;
    }
    public String doSelect(){
	String msg = "";
	String qq = " select type,code,name from restrictions where id=?";
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
		setType(rs.getString(1));
		setCode(rs.getString(2));
		setName(rs.getString(3));
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
	String qq = " insert into restrictions values(0,?,?,?)";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	if(name.equals("") || type.equals("") || code.equals("")){
	    msg = " type, code or name is missing";
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
	    pstmt.setString(1, type);
	    pstmt.setString(2, code);
	    pstmt.setString(3, name);
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
	String qq = " update restrictions set type=?,code=?, name=? where id=?";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	if(name.equals("") || type.equals("") || code.equals("")){
	    msg = " type, code or name is missing";
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
	    pstmt.setString(1, type);
	    pstmt.setString(2, code);
	    pstmt.setString(3, name);
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
	String qq = " delete from restrictions where id=?";
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
