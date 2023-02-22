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

public class NewHireList{

    static final long serialVersionUID = 232L;		
    static Logger logger = LogManager.getLogger(NewHireList.class);
    List<NewHire> newhires = null;
    // String name = "", active="";
    public NewHireList(){
    }	
    //
    public List<NewHire> getNewhires(){
	return newhires;
    }
    public String find(){
	String msg = "", qw="";
	String qq = " select id,date_format(date,'%m/%d/%Y') from new_hires ";
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
	    newhires = new ArrayList<NewHire>();
	    con = Helper.getConnection();
	    if(con == null){
		msg = "Could not connect ";
		return msg;
	    }
	    pstmt = con.prepareStatement(qq);
	    rs = pstmt.executeQuery();	
	    while(rs.next()){
		NewHire one = new NewHire(rs.getString(1), rs.getString(2));
		newhires.add(one);
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
