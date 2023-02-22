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

public class Dept implements java.io.Serializable{

    String id = "", name="", active="";
    static final long serialVersionUID = 112L;		
    static Logger logger = LogManager.getLogger(Dept.class);
    // Parking Services 30 (in Nw)
    // Environmental Services 51 (in Nw)
    // add the following
    // insert into nwdept_to_depts values(30,25);
    // insert into depts values(57,'Utilities Environmental Services','y');
    // insert into nwdept_to_depts values(51,57);
    List<Division> divisions = null;
    String[] del_divisions = null;
    String[] add_divisions = null;
    public Dept(){
    }
    public Dept(String val){
	setId(val);
    }
    public Dept(String val, String val2, String val3){
	setId(val);
	setName(val2);
	setActive(val3);
    }
    //
    //
    public String getId(){
	return id;
    }
    public String getName(){
	return name;
    }
    public boolean getActive(){
	if(id.equals("")) active = "y";
	return !active.equals("");
    }
    public boolean isActive(){
	if(id.equals("")) active = "y";
	return !active.equals("");
    }
    public boolean hasDivisions(){
	getDivisions();
	return divisions != null;
    }
    public List<Division> getDivisions(){
	if(divisions == null && !id.equals("")){
	    DivisionList dl = new DivisionList(id);
	    String back = dl.find();
	    if(back.equals("")){
		List<Division> list = dl.getDivisions();
		if(list != null && list.size() > 0){
		    divisions = list;
		}
	    }
	}
	return divisions;
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
	    name = val.trim();
    }
    public void setActive(boolean val){
	if(val)
	    active = "y";
    }
    public void setActive(String val){
	if(val != null)
	    active = val;
    }		
    public String toString(){
	return name;
    }
    public void setDel_divisions(String[] vals){
	del_divisions = vals;
    }
    public void setAdd_divisions(String[] vals){
	add_divisions = vals;
    }
    //
    public String doSelect(){
				
	String msg = "";
	String qq = " select name,active from depts where id=?";
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
		setActive(rs.getString(2));
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
    public String doSave(){
	String msg = "";
	String qq = " insert into depts values(0,?,'y')";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	logger.debug(qq);
	active = "y";
	try{
	    con = Helper.getConnection();
	    if(con == null){
		msg = "Could not connect ";
		return msg;
	    }
	    pstmt = con.prepareStatement(qq);
	    pstmt.setString(1, name);
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
    //
    public String doUpdate(){
	String msg = "";
	String qq = " update depts set name=?,active=? where id=?";
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
	    pstmt.setString(1, name);
	    if(active.equals(""))
		pstmt.setNull(2, Types.CHAR);
	    else
		pstmt.setString(2, "y");
	    pstmt.setString(3, id);
	    pstmt.executeUpdate();
	    //
	    // if need to delete some divisions
	    //
	    msg += addDivisions();
	    msg += doDeleteDivisions(false);
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
	String qq = " delete from depts where id=?";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	logger.debug(qq);
	//
	// if we could not delete divisions then we can not delete the dept
	//
	msg = doDeleteDivisions(true);
	if(!msg.equals("")){
	    return msg;
	}
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
    //
    // delete some/all divisions
    //
    public String doDeleteDivisions(boolean all){
	String msg = "";
	if(all){
	    getDivisions();
	    if(divisions != null){
		for(Division one:divisions){
		    msg += one.doDelete();
		}
	    }
	}
	else if(del_divisions != null){
	    for(String str:del_divisions){
		Division one = new Division(str);
		msg += one.doDelete();
	    }
	}
	return msg;
    }
    //
    // add new divisions
    //
    public String addDivisions(){
	String msg = "";
	if(add_divisions != null){
	    for(String str:add_divisions){
		if(str != null && !str.trim().equals("")){
		    Division one = new Division(null, id, str.trim(), "y");
		    msg += one.doSave();
		}
	    }
	}
	return msg;
    }
}
