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

public class ExpYearList{

    static final long serialVersionUID = 120L;		
    static Logger logger = LogManager.getLogger(TypeList.class);
    List<Type> years = null;
    boolean future_years = false;
    String name = "";
    public ExpYearList(){
    }	
    public ExpYearList(boolean val){
	if(val)
	    future_years = true;
    }
    public List<Type> getYears(){
	return years;
    }
    //
    // setters
    //
    public void setFutureYearsOnly(){
	future_years = true;
    }
    public String find(){
	int this_year = Helper.getCurrentYear();
	int start_year = this_year;				
	if(!future_years){
	    start_year = findStartYear();
	}
	years = new ArrayList<Type>();
	if(start_year < this_year){
	    for(int year=start_year;year<this_year;year++){
		Type one = new Type(""+year, ""+year);
		years.add(one);
	    }
	}
	for(int year=this_year;year<this_year+6;year++){
	    Type one = new Type(""+year, ""+year);
	    years.add(one);
	}
	return "";
    }
    int findStartYear(){
	int start_year = Helper.getCurrentYear();
	String msg = "";
	String qq = " select min(exp_year) from employees ";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	logger.debug(qq);
	try{

	    con = Helper.getConnection();
	    if(con == null){
		msg = "Could not connect ";
		logger.error(msg);								
		return start_year;
	    }
	    pstmt = con.prepareStatement(qq);
	    rs = pstmt.executeQuery();	
	    if(rs.next()){
		start_year = rs.getInt(1);
	    }
	}catch(Exception e){
	    msg += e+":"+qq;
	    logger.error(msg);
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	}
	return start_year;
    }

	
}
