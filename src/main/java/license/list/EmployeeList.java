package license.list;
/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.util.*;
import java.sql.*;
import java.io.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import license.model.*;
import license.utils.*;

public class EmployeeList implements java.io.Serializable{

    static final long serialVersionUID = 37L;	
   
    static Logger logger = LogManager.getLogger(EmployeeList.class);
    static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");	
    String id="", which_date="r.exp_date", type_id="",
	state_id="",dept_id= "", div_id="", newhire_id="";
    String name="", lic_number="", employee_num="";
    String date_from="", date_to="", sortBy="r.id DESC";
    String year = "", exp_year="", exclude_dept_id="";
    String test_select_id="", inactive_check_id="", emp_num_status="";
    String limit=" limit 20";
    boolean activeOnly = false, hasEmployeeNum = false;
    boolean requiredTesting = false, notExpired = false;
    boolean allCdl = false, emp_num_set=false, emp_num_unset=false;
    boolean inactive = false;
    boolean cdl_req_set = false, cdl_req_unset = false; 
    List<Employee> employees = null;
	
    public EmployeeList(){
    }	
    public void setId(String val){
	if(val != null)
	    id = val;
    }
    public void setName(String val){
	if(val != null)
	    name = val;
    }
    public void setEmployee_num(String val){
	if(val != null)
	    employee_num = val;
    }		
    public void setLic_number(String val){
	if(val != null)
	    lic_number = val;
    }
    public void setInactive_check_id(String val){
	if(val != null)
	    inactive_check_id = val;
    }		
    public void setType_id(String val){
	if(val != null && !val.equals("-1"))
	    type_id = val;
    }
    public void setDiv_id(String val){
	if(val != null && !val.equals("-1"))
	    div_id = val;
    }
    public void setDept_id(String val){
	if(val != null && !val.equals("-1"))
	    dept_id = val;
    }
    public void setState_id(String val){
	if(val != null && !val.equals("-1"))
	    state_id = val;
    }		
    public void setYear(String val){
	if(val != null && !val.equals("-1"))
	    year = val;
    }
    public void setWhich_date(String val){
	if(val != null)
	    which_date = val;
    }
    public void setDate_from(String val){
	if(val != null)
	    date_from = val;
    }
    public void setDate_to(String val){
	if(val != null)
	    date_to = val;
    }
    public void setSortBy(String val){
	if(val != null)
	    sortBy = val;
    }
    public void setAllCdl(boolean val){
	if(val){
	    allCdl = true;
	    setNoLimit();
	}
    }
    public void setExcludeDept(String val){
	if(val != null && !val.equals("-1"))
	    exclude_dept_id = val;
    }		
    public void setEmpNumStatus(String val){
	if(val != null && !val.equals("-1")){
	    if(val.endsWith("_unset"))
		emp_num_unset = true;
	    else if(val.endsWith("_set"))
		emp_num_set = true;
	    setNoLimit();
	}
    }
    public void setActiveStatus(String val){
	if(val != null && !val.equals("-1")){
	    if(val.equals("active"))
		activeOnly = true;
	    else if(val.endsWith("inactive"))
		inactive = true;
	    setNoLimit();
	}
    }		
    public void setTest_select_id(String val){
	if(val != null){
	    test_select_id = val;
	    setNoLimit();
	}
    }
    public void setNoLimit(){
	limit = "";
    }
    public void setNewhire_id(String val){
	if(val != null)
	    newhire_id = val;
    }		
    public void setCdl_req_status(String val){
	if(val != null && !val.equals("-1")){
	    if(val.equals("y")){
		cdl_req_set = true;
	    }
	    else if(val.equals("n")){
		cdl_req_unset = true;
	    }
	}
    }
    //
    public String getId(){
	return id;
    }
    public String getDept_id(){
	return dept_id;
    }
    public String getExcludeDept(){
	if(exclude_dept_id.equals("")){
	    return "-1";
	}
	return exclude_dept_id;
    }
    public String getCdl_req_status(){
	if(cdl_req_set){
	    return "y";
	}
	else if(cdl_req_unset){
	    return "n";
	}
	return "-1";
    }
    public String getName(){
	return name;
    }
    public String getEmployee_num(){
	return employee_num;
    }		
    public String getDiv_id(){
	return div_id;
    }		
    public String getType_id(){
	return type_id;
    }
    public String getState_id(){
	return state_id;
    }
    public String getLic_number(){
	return lic_number;
    }
    public String getExp_year(){
	return exp_year;
    }		
    public String getWhich_date(){
	return which_date;
    }
    public String getDate_from(){
	return date_from ;
    }
    public String getDate_to(){
	return date_to ;
    }
    public String getSortBy(){
	return sortBy ;
    }
    public String getEmpNumStatus(){
	if(emp_num_unset)
	    return "emp_num_unset";
	else if(emp_num_set)
	    return "emp_num_set";
	else
	    return "-1";
    }
    public String getActiveStatus(){
	if(activeOnly)
	    return "active";
	else if(inactive)
	    return "inactive";
	else
	    return "-1";
    }		
    public boolean getAllCdl(){
	return allCdl;
    }
    public void setActiveOnly(){
	activeOnly = true;
    }
    public void setHasEmployeeNum(){
	hasEmployeeNum = true;
    }		
    public void setRequiredTesting(){
	setNoLimit();
	requiredTesting = true;
	setNotExpired();
	setActiveOnly();
    }
    public void setNotExpired(){
	notExpired = true;
    }
    public List<Employee> getEmployees(){
	return employees;
    }
    //
    public String find(){
	String qq = "select r.id,r.fname,r.mname,r.lname,r.div_id,"+
	    "r.dept_id,r.lic_number,date_format(r.dob,'%m/%d/%Y'),"+
	    "r.driver,r.use_vehicle,r.city_vehicle,r.own_vehicle,"+
	    "r.cdl_required,r.state_id,r.type_id,"+
	    "r.userid,date_format(r.date,'%m/%d/%Y'),r.userid2,"+
	    "date_format(r.date2,'%m/%d/%Y'),r.active, "+
	    "date_format(r.exp_date,'%m/%d/%Y'),r.employee_num ";
	String qf = " from employees r left join depts d on d.id=r.dept_id ";
	String qw = "";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String msg = "";
	if(!id.equals("")){
	    if(!qw.equals("")) qw += " and ";
	    qw += " r.id = ? ";
	}
	else if(!employee_num.equals("")){
	    if(!qw.equals("")) qw += " and ";
	    qw += " r.employee_num = ? ";
	}
	else {
	    if(!newhire_id.equals("")){
		qf += " left join newhire_employees nhe on nhe.emp_id = r.id ";
		if(!qw.equals("")) qw += " and ";
		qw += " nhe.newhire_id = ? ";
	    }						
	    if(!name.equals("")){
		if(!qw.equals("")) qw += " and ";
		qw += " (r.fname like ? or r.lname like ?) ";
	    }
	    if(!lic_number.equals("")){
		if(!qw.equals("")) qw += " and ";
		qw += " r.lic_number = ? ";
	    }						
	    if(!state_id.equals("")){
		if(!qw.equals("")) qw += " and ";
		qw += " r.state_id = ? ";
	    }						
	    if(!type_id.equals("")){
		if(type_id.equals("cdl")){
		    if(!qw.equals("")) qw += " and ";
		    qw += " r.type_id in (4,18,49,33) ";
		}
		else{
		    qw += " r.type_id = ? ";
		}
	    }
	    if(!dept_id.equals("")){
		if(!qw.equals("")) qw += " and ";
		qw += " r.dept_id = ? ";
	    }
	    else if(!exclude_dept_id.equals("")){
		if(!qw.equals("")) qw += " and ";
		qw += " not r.dept_id = ? ";
	    }
	    if(!div_id.equals("")){
		if(!qw.equals("")) qw += " and ";
		qw += " r.div_id = ? ";
	    }

	    if(!which_date.equals("")){
		if(!date_from.equals("")){
		    if(!qw.equals("")) qw += " and ";					
		    qw += which_date+" >= ? ";					
		}
		if(!date_to.equals("")){
		    if(!qw.equals("")) qw += " and ";
		    qw += which_date+" <= ? ";					
		}
	    }
	    if(!year.equals("")){
		if(!qw.equals("")) qw += " and ";					
		qw += " year("+which_date+") = ? ";		
	    }
	    if(activeOnly){
		if(!qw.equals("")) qw += " and ";
		qw += " r.active is not null ";								
	    }
	    else if(inactive){
		if(!qw.equals("")) qw += " and ";
		qw += " r.active is null ";								
	    }						
	    if(hasEmployeeNum){
		if(!qw.equals("")) qw += " and ";
		qw += " not r.employee_num is null ";								
	    }						
	    if(requiredTesting){
		// CDL required flag, exlude fire dept
		if(!qw.equals("")) qw += " and ";								
		qw += " r.cdl_required is not null ";
	    }
	    else if(allCdl){
		if(!qw.equals("")) qw += " and ";								
		qw += " r.type_id in (4,18,49,33) ";
	    }
	    if(!test_select_id.equals("")){
		if(!qw.equals("")) qw += " and ";											
		qf += ", employee_selections es ";
		qw += " r.id = es.emp_id and es.test_select_id = ? ";
	    }
	    if(notExpired){
		if(!qw.equals("")) qw += " and ";
		qw += " r.exp_date > now() ";
	    }
	    if(!inactive_check_id.equals("")){
		qf += ", inactive_employees ie ";
		if(!qw.equals("")) qw += " and ";								
		qw += " ie.emp_id=r.id and ie.check_id=? ";
	    }
	    if(emp_num_set){
		if(!qw.equals("")) qw += " and ";
		qw += " r.employee_num is not null ";
	    }
	    else if(emp_num_unset){
		if(!qw.equals("")) qw += " and ";
		qw += " r.employee_num is null ";
	    }
	    if(cdl_req_set){
		if(!qw.equals("")) qw += " and ";								
		qw += " r.cdl_required is not null ";
	    }
	    else if(cdl_req_unset){
		if(!qw.equals("")) qw += " and ";
		qw += " r.cdl_required is null ";
	    }
	}
	qq += qf;
	if(!qw.equals(""))
	    qq += " where "+qw;
	if(!sortBy.equals("")){
	    qq += " order by "+sortBy;
	}
	qq += limit;
	logger.debug(qq);
	//
	try{
	    con = Helper.getConnection();
	    if(con == null){
		msg = "Could not connect ";
		return msg;
	    }
	    pstmt = con.prepareStatement(qq);
	    int jj=1;
	    if(!id.equals("")){
		pstmt.setString(jj++,id);
	    }
	    else if(!employee_num.equals("")){
		pstmt.setString(jj++,employee_num);
	    }
	    else{
		if(!newhire_id.equals("")){
		    pstmt.setString(jj++,newhire_id);
		}
		if(!name.equals("")){
		    pstmt.setString(jj++,name+"%");
		    pstmt.setString(jj++,name+"%");										
		}
		if(!lic_number.equals("")){
		    pstmt.setString(jj++,"%"+lic_number+"%");
		}										
		if(!state_id.equals("")){
		    pstmt.setString(jj++,state_id);
		}								
		if(!type_id.equals("") && !type_id.equals("cdl")){
		    pstmt.setString(jj++,type_id);
		}
		if(!dept_id.equals("")){
		    pstmt.setString(jj++,dept_id);
		}
		else if(!exclude_dept_id.equals("")){
		    pstmt.setString(jj++,exclude_dept_id);
		}
		if(!div_id.equals("")){
		    pstmt.setString(jj++,div_id);
		}
		if(!which_date.equals("")){
		    if(!date_from.equals("")){
			pstmt.setDate(jj++, new java.sql.Date(dateFormat.parse(date_from).getTime()));
		    }
		    if(!date_to.equals("")){
			pstmt.setDate(jj++, new java.sql.Date(dateFormat.parse(date_to).getTime()));
		    }
		}
		if(!year.equals("")){
		    pstmt.setString(jj++, year);
		}								
		if(!test_select_id.equals("")){
		    pstmt.setString(jj++, test_select_id);
		}
		if(!inactive_check_id.equals("")){
		    pstmt.setString(jj++, inactive_check_id);
		}
	    }
	    rs = pstmt.executeQuery();
	    employees = new ArrayList<Employee>();
	    while(rs.next()){
		Employee one = new Employee(
					    rs.getString(1),
					    rs.getString(2),
					    rs.getString(3),
					    rs.getString(4),
					    rs.getString(5),
					    rs.getString(6),
					    rs.getString(7),
					    rs.getString(8),
					    rs.getString(9),
					    rs.getString(10),
					    rs.getString(11),
					    rs.getString(12),
					    rs.getString(13),
					    rs.getString(14),
					    rs.getString(15),
					    rs.getString(16),
					    rs.getString(17),
					    rs.getString(18),
					    rs.getString(19),
					    rs.getString(20),
					    rs.getString(21),
					    rs.getString(22)
					    );
		if(!employees.contains(one))
		    employees.add(one);
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





































