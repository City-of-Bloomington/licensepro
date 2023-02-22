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

public class EmployeeSelectionList{

    static final long serialVersionUID = 128L;		
    static Logger logger = LogManager.getLogger(EmployeeSelectionList.class);
    List<EmployeeSelection> employeeSelections = null;
    String test_select_id = "", type="";
    public EmployeeSelectionList(){
    }
    public EmployeeSelectionList(String val){
	setTest_select_id(val);
    }		
    //
    // setters
    //
    public void setTest_select_id(String val){
	if(val != null)
	    test_select_id = val;
    }
    public void setType(String val){
	if(val != null)
	    type = val;
    }
    //
    // getters
    //
    public String getTest_select_id(){
	return test_select_id;
    }
    //
    public String getType(){
	return type;
    }		
		
    public List<EmployeeSelection> getEmployeeSelections(){
	return employeeSelections;
    }

    public String find(){
	String msg = "", qw="";
	String qq = " select e.id,e.emp_id,e.test_select_id,e.retduty,e.followup from employee_selections e ";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	if(!test_select_id.equals("")){
	    qw += " e.test_select_id = ? ";
	}
	if(!type.equals("")){
	    qq += ", test_selections t ";
	    if(!qw.equals("")) qw += " and ";
							 
	    qw += " e.test_select_id=t.id and t.type=? ";
	}				
	String qo = " order by e.id ";
	if(!qw.equals("")){
	    qq += " where "+qw;
	}
	qq += qo;
	logger.debug(qq);
	try{
	    employeeSelections = new ArrayList<EmployeeSelection>();
	    con = Helper.getConnection();
	    if(con == null){
		msg = "Could not connect ";
		return msg;
	    }
	    pstmt = con.prepareStatement(qq);
	    int jj=1;
	    if(!test_select_id.equals("")){
		pstmt.setString(jj++, test_select_id);
	    }
	    if(!type.equals("")){
		pstmt.setString(jj++, type);
	    }						
	    rs = pstmt.executeQuery();	
	    while(rs.next()){
		EmployeeSelection one = new EmployeeSelection(rs.getString(1),
							      rs.getString(2),
							      rs.getString(3),
							      rs.getString(4),
							      rs.getString(5));
		employeeSelections.add(one);
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
