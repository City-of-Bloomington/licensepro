package license.list;
/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import license.model.*;
import license.utils.*;

public class DivisionList{

    static final long serialVersionUID = 124L;		
    static Logger logger = LogManager.getLogger(DivisionList.class);
    List<Division> divisions = null;
    String dept_id = "", active="";
    public DivisionList(){
    }
    public DivisionList(String val){
	setDept_id(val);
    }		
    //
    // setters
    //
    public void setDept_id(String val){
	if(val != null)
	    dept_id = val;
    }
    public void setActiveOnly(){
	active = "y";
    }
    public List<Division> getDivisions(){
	return divisions;
    }

    public String find(){
	String msg = "", qw="";
	String qq = " select id,dept_id,name,active from divisions ";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	if(!dept_id.equals("")){
	    qw += " dept_id = ? ";
	}
	if(!active.equals("")){
	    if(!qw.equals("")) qw += " and ";
							 
	    qw += " active is not null ";
	}				
	String qo = " order by id ";
	if(!qw.equals("")){
	    qq += " where "+qw;
	}
	qq += qo;
	logger.debug(qq);
	try{
	    divisions = new ArrayList<Division>();
	    con = Helper.getConnection();
	    if(con == null){
		msg = "Could not connect ";
		return msg;
	    }
	    pstmt = con.prepareStatement(qq);
	    if(!dept_id.equals("")){
		pstmt.setString(1, dept_id);
	    }
	    rs = pstmt.executeQuery();	
	    while(rs.next()){
		Division one = new Division(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
		divisions.add(one);
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
