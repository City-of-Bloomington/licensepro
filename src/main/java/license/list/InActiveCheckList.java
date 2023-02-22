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

public class InActiveCheckList{

    static final long serialVersionUID = 222L;		
    static Logger logger = LogManager.getLogger(InActiveCheckList.class);
    List<InActiveCheck> inActiveChecks = null;
    // String name = "", active="";
    public InActiveCheckList(){
    }	
    //
    // setters
    //
    /*
      public void setName(String val){
      if(val != null)
      name = val;
      }
      public void setActiveOnly(){
      active = "y";
      }
    */
    public List<InActiveCheck> getInActiveChecks(){
	return inActiveChecks;
    }
    public String find(){
	String msg = "", qw="";
	String qq = " select id,date_format(date,'%m/%d/%Y'),confirmed from inactive_checks ";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	if(!qw.equals("")){
	    qq += " where "+qw;
	}				
	String qo = " order by id desc ";
	qq += qo;
	logger.debug(qq);
	try{
	    inActiveChecks = new ArrayList<InActiveCheck>();
	    con = Helper.getConnection();
	    if(con == null){
		msg = "Could not connect ";
		return msg;
	    }
	    pstmt = con.prepareStatement(qq);
	    rs = pstmt.executeQuery();	
	    while(rs.next()){
		InActiveCheck one = new InActiveCheck(rs.getString(1), rs.getString(2), rs.getString(3));
		inActiveChecks.add(one);
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
