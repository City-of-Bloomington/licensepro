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

public class DeptList{

    static final long serialVersionUID = 122L;		
    static Logger logger = LogManager.getLogger(DeptList.class);
    List<Dept> depts = null;
    String name = "", active="";
    public DeptList(){
    }	
    //
    // setters
    //
    public void setName(String val){
	if(val != null)
	    name = val;
    }
    public void setActiveOnly(){
	active = "y";
    }		
    public List<Dept> getDepts(){
	return depts;
    }
    public String find(){
	String msg = "", qw="";
	String qq = " select id,name,active from depts ";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	if(!name.equals("")){
	    qw += " name like ? ";
	}
	if(!active.equals("")){
	    if(!qw.equals("")) qw += " and ";
	    qw += " active is not null ";
	}
	if(!qw.equals("")){
	    qq += " where "+qw;
	}				
	String qo = " order by name ";
	qq += qo;
	logger.debug(qq);
	try{
	    depts = new ArrayList<Dept>();
	    con = Helper.getConnection();
	    if(con == null){
		msg = "Could not connect ";
		return msg;
	    }
	    pstmt = con.prepareStatement(qq);
	    if(!name.equals("")){
		pstmt.setString(1, "%"+name+"%");
	    }
	    rs = pstmt.executeQuery();	
	    while(rs.next()){
		Dept one = new Dept(rs.getString(1), rs.getString(2), rs.getString(3));
		depts.add(one);
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
