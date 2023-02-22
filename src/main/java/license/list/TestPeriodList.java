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

public class TestPeriodList{

    static final long serialVersionUID = 333L;		
    static Logger logger = LogManager.getLogger(TestPeriodList.class);
    List<TestPeriod> testPeriods = null;
    //
    public TestPeriodList(){
    }
    //
    // setters
    //
    public List<TestPeriod> getTestPeriods(){
	return testPeriods;
    }
    public String find(){
	String msg = "", qw="";
	String qq = " select id,name,frequency from test_periods";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	if(!qw.equals("")){
	    qq += " where "+qw;
	}				
	String qo = " order by id ";
	qq += qo;
	logger.debug(qq);
	try{
	    testPeriods = new ArrayList<TestPeriod>();
	    con = Helper.getConnection();
	    if(con == null){
		msg = "Could not connect ";
		return msg;
	    }
	    pstmt = con.prepareStatement(qq);
	    int jj=1;
	    rs = pstmt.executeQuery();	
	    while(rs.next()){
		TestPeriod one =
		    new TestPeriod(rs.getString(1),
				   rs.getString(2),
				   rs.getString(3)
				   );
		testPeriods.add(one);
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
