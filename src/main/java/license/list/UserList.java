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

public class UserList{

    static final long serialVersionUID = 1120L;		
    static Logger logger = LogManager.getLogger(UserList.class);
    String fullname = "", active="";
		
    List<User> users = null;
    String name = "";

    public UserList(){
    }	
    //
    // setters
    //
    public void setFullname(String val){
	if(val != null)
	    fullname = val;
    }
    public void setActiveOnly(){
	active = "y";
    }
    public List<User> getUsers(){
	return users;
    }
    public String find(){
	String msg = "", qw="";
	String qq = " select * from users ";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	qw = "";
	if(!fullname.equals("")){
	    if(!qw.equals("")) qw += " and ";
	    qq += " fullname like ? ";
	}
	if(!active.equals("")){
	    if(!qw.equals("")) qw += " and ";
	    qq += " active is not null ";
	}
	if(!qw.equals("")){
	    qq += " where "+qw;
	}
	String qo = " order by fullname ";
	qq += qo;
	//
	logger.debug(qq);
	try{
	    con = Helper.getConnection();
	    if(con == null){
		msg = "Could not connect ";
		return msg;
	    }
	    users = new ArrayList<User>();
	    pstmt = con.prepareStatement(qq);
	    if(!fullname.equals("")){
		pstmt.setString(1, "%"+fullname+"%");
	    }
	    rs = pstmt.executeQuery();	
	    while(rs.next()){
		User one = new User(rs.getString(1),
				    rs.getString(2),
				    rs.getString(3),
				    rs.getString(4),
				    rs.getString(5)
				    );
		users.add(one);
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
