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

public class TestPeriod implements java.io.Serializable{

    String id = "", name="", frequency="";
    static final long serialVersionUID = 217L;		
    static Logger logger = LogManager.getLogger(TestPeriod.class);
    public TestPeriod(){
    }

    public TestPeriod(String val){
	setId(val);
    }
    public TestPeriod(String val, String val2, String val3){
	setId(val);
	setName(val2);
	setFrequency(val3);
    }

    //
    //
    public String getId(){
	return id;
    }
    public String getName(){
	return name;
    }		
    public String getFequency(){
	return frequency;
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
    public void setFrequency(String val){
	if(val != null)
	    frequency = val;
    }
    public String toString(){
	return name+": "+frequency;
    }
    public String doSelect(){
	String msg = "";
	String qq = " select name,frequency  from test_periods where id=?";
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
		setName(rs.getString(1));
		setFrequency(rs.getString(2));
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
	String qq = " insert into test_periods values(0,?,?)";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	if(name.equals("") || frequency.equals("")){
	    msg = " name or frequency is missing";
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
	    pstmt.setString(1, name);
	    pstmt.setString(2, frequency);
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
	String qq = " update test_periods set name=?,frequency=?  where id=?";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	if(name.equals("") || frequency.equals("") || id.equals("")){
	    msg = " name, frequency or id is missing";
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
	    pstmt.setString(1, name);
	    pstmt.setString(2, frequency);
	    pstmt.setString(3, id);
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
	String qq = " delete from test_periods where id=?";
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
