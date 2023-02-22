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

public class TestSelectionList{

    static final long serialVersionUID = 337L;		
    static Logger logger = LogManager.getLogger(TestSelectionList.class);
    List<TestSelection> testSelections = null;
    String year = "", date_from="", date_to="", test_run_id="";
		
    //
    public TestSelectionList(){
    }
    public TestSelectionList(String val){
	setTest_run_id(val);
    }
    public void setTest_run_id(String val){
	if(val != null)
	    test_run_id = val;
    }
    //
    // setters
    //
    public List<TestSelection> getTestSelections(){
	return testSelections;
    }
    public String find(){
	String msg = "", qw="";
	String qq = " select id,date_format(select_date,'%m/%d/%Y'),select_time, type,elegible,selected,percent,test_run_id from test_selections ";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	if(!test_run_id.equals("")){
	    if(!qw.equals("")) qw += " and ";
	    qw += " test_run_id = ?" ;
	}
				
	if(!qw.equals("")){
	    qq += " where "+qw;
	}				
	String qo = " order by id ";
	qq += qo;
	logger.debug(qq);
	try{
	    testSelections = new ArrayList<TestSelection>();
	    con = Helper.getConnection();
	    if(con == null){
		msg = "Could not connect ";
		return msg;
	    }
	    pstmt = con.prepareStatement(qq);
	    int jj=1;
	    if(!test_run_id.equals("")){
		pstmt.setString(jj++, test_run_id);
	    }
	    rs = pstmt.executeQuery();	
	    while(rs.next()){
		TestSelection one =
		    new TestSelection(rs.getString(1),
				      rs.getString(2),
				      rs.getString(3),
				      rs.getString(4),
				      rs.getString(5),
				      rs.getString(6),
				      rs.getString(7),
				      rs.getString(8)
				      );
		testSelections.add(one);
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
