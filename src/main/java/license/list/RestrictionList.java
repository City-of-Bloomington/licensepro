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

public class RestrictionList{

    static final long serialVersionUID = 133L;		
    static Logger logger = LogManager.getLogger(RestrictionList.class);
    List<Restriction> restrictions = null;
    String name = "", code="", type="", license_id="";
    public RestrictionList(){
    }
    public RestrictionList(String val){
	setLicense_id(val);
    }		
    //
    // setters
    //
    public void setLicense_id(String val){ 
	if(val != null)
	    license_id = val; 
    }		
    public void setName(String val){
	if(val != null)
	    name = val;
    }
    public void setCode(String val){
	if(val != null)
	    code = val;
    }
    public void setType(String val){
	if(val != null)
	    type = val;
    }
    public String getCode(){
	return code;
    }
    public String getType(){
	return type;
    }
    public String getName(){
	return name;
    }		
    public List<Restriction> getRestrictions(){
	return restrictions;
    }
    public String find(){
	String msg = "", qw="";
	String qq = " select r.id,r.type,r.code,r.name from restrictions r ";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	if(!license_id.equals("")){
	    qq += ", lic_restriction lr ";
	    qw += " lr.emp_id = ? and lr.restr_id=r.id ";
	}
	if(!type.equals("")){
	    if(!qw.equals("")) qw += " and ";
	    qw += " r.type = ? ";
	}
	if(!code.equals("")){
	    if(!qw.equals("")) qw += " and ";
	    qw += " r.code = ? ";
	}
	if(!name.equals("")){
	    if(!qw.equals("")) qw += " and ";						
	    qw += " r.name like ? ";
	}				

	if(!qw.equals("")){
	    qq += " where "+qw;
	}				
	String qo = " order by id ";
	qq += qo;
	logger.debug(qq);
	try{
	    restrictions = new ArrayList<Restriction>();
	    con = Helper.getConnection();
	    if(con == null){
		msg = "Could not connect ";
		return msg;
	    }
	    pstmt = con.prepareStatement(qq);
	    int jj=1;
	    if(!type.equals("")){
		pstmt.setString(jj++, type);
	    }
	    if(!code.equals("")){
		pstmt.setString(jj++, code);
	    }
	    if(!name.equals("")){
		pstmt.setString(jj++, name);
	    }						
	    rs = pstmt.executeQuery();	
	    while(rs.next()){
		Restriction one = new Restriction(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
		restrictions.add(one);
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
