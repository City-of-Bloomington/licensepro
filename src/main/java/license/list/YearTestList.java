package license.list;
/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.text.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import license.model.*;
import license.utils.*;

public class YearTestList{

    static final long serialVersionUID = 233L;		
    static Logger logger = LogManager.getLogger(YearTestList.class);
    static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");	
    List<YearTest> yearTests = null;
    String year = "", date_from="", date_to="", test_type="";
    public YearTestList(){
    }
    //
    // setters
    //

    public List<YearTest> getYearTests(){
	return yearTests;
    }
    public void setYear(String val){
	if(val != null)
	    year = val;
    }
    // test selections date
    public void setDate_from(String val){
	if(val != null)
	    date_from = val;
    }
    // test selection date
    public void setDate_to(String val){
	if(val != null)
	    date_to = val;
    }
    public void setTest_type(String val){
	if(val != null && !val.equals("-1"))
	    test_type = val;
    }
    public String getYear(){
	return year;
    }		
    public String getDate_from(){
	return date_from;
    }
    public String getDate_to(){
	return date_to;
    }
    public String getTest_type(){
	if(test_type.equals("")){
	    return "-1";
	}
	return test_type;
    }		
    public String find(){
	String msg = "", qw="";
	String qq = " select y.year,y.per_alcohol,y.per_drug,y.period_type,y.period_count,y.checksum from year_tests y "+
	    " left join test_runs r on r.year=y.year "+
	    " left join test_selections s on s.test_run_id=r.id ";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	if(!year.equals("")){
	    if(!qw.equals("")) qw += " and ";
	    qw += " y.year = ? ";
	}
	if(!date_from.equals("")){
	    if(!qw.equals("")) qw += " and ";					
	    qw += " s.date >= ? ";					
	}
	if(!date_to.equals("")){
	    if(!qw.equals("")) qw += " and ";
	    qw += "s.date  <= ? ";					
	}				
	if(!qw.equals("")){
	    qq += " where "+qw;
	}				
	String qo = " order by year desc";
	qq += qo;
	logger.debug(qq);
	try{
	    yearTests = new ArrayList<YearTest>();
	    con = Helper.getConnection();
	    if(con == null){
		msg = "Could not connect ";
		return msg;
	    }
	    pstmt = con.prepareStatement(qq);
	    int jj=1;
	    if(!year.equals("")){
		pstmt.setString(jj++, year);
	    }
	    if(!date_from.equals("")){
		pstmt.setDate(jj++, new java.sql.Date(dateFormat.parse(date_from).getTime()));
	    }
	    if(!date_to.equals("")){
		pstmt.setDate(jj++, new java.sql.Date(dateFormat.parse(date_to).getTime()));
	    }										
	    rs = pstmt.executeQuery();	
	    while(rs.next()){
		YearTest one = new YearTest(rs.getString(1),
					    rs.getString(2),
					    rs.getString(3),
					    rs.getString(4),
					    rs.getString(5),
					    rs.getString(6)
					    );
		if(!yearTests.contains(one))
		    yearTests.add(one);
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
