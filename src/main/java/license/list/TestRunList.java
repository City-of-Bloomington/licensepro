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

public class TestRunList{

    static final long serialVersionUID = 335L;		
    static Logger logger = LogManager.getLogger(TestRunList.class);
    String year = "";
    List<TestRun> testRuns = null;
    //
    public TestRunList(){
    }
    public TestRunList(String val){
	setYear(val);
    }		
    //
    // setters
    //
    public void setYear(String val){
	if(val != null)
	    year = val;
    }
    public List<TestRun> getTestRuns(){
	return testRuns;
    }
    public String find(){
	String msg = "", qw="";
	String qq = " select id,year,test_run,done,quant_alco,quant_drug,total_pool,witness,witness2  from test_runs ";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	if(!year.equals("")){
	    qw += " year = ? ";
	}
	if(!qw.equals("")){
	    qq += " where "+qw;
	}				
	String qo = " order by id ";
	qq += qo;
	logger.debug(qq);
	try{
	    testRuns = new ArrayList<TestRun>();
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
	    rs = pstmt.executeQuery();	
	    while(rs.next()){
		TestRun one =
		    new TestRun(rs.getString(1),
				rs.getString(2),
				rs.getString(3),
				rs.getString(4),
				rs.getString(5),
				rs.getString(6),
				rs.getString(7),
				rs.getString(8),
				rs.getString(9)
				);
		testRuns.add(one);
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
