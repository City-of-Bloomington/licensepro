package license.model;
/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.sql.*;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import license.list.*;
import license.utils.*;

public class YearTest implements java.io.Serializable{

    static final long serialVersionUID = 216L;		
    static Logger logger = LogManager.getLogger(YearTest.class);

    String year = "",
	per_alcohol="10", // default values
	per_drug="50",
	period_type="Quarterly", 
	period_count="4", 
	checksum="0";
    boolean isCurrentYear = false, isNewYear = false;
    List<TestRun> testRuns = null;
    public YearTest(){
    }
    public YearTest(String val){
	setId(val);
    }
    public YearTest(String val, String val2, String val3,
		    String val4, String val5,
		    String val6){
	setId(val);
	setPer_alcohol(val2);
	setPer_drug(val3);
	setPeriod_type(val4);
	setPeriod_count(val5);
	setChecksum(val6);
    }
    public String getId(){
	return year;
    }
    public String getYear(){
	if(year.equals("")){
	    year = ""+Helper.getCurrentYear();
	}
	return year;
    }		
    public String getPer_alcohol(){
	return per_alcohol;
    }
    public String getPer_drug(){
	return per_drug;
    }
    public String getPeriod_type(){
	return period_type;
    }
    public String getPeriod_count(){
	return period_count;
    }
    public String getChecksum(){
	return checksum;
    }		
    //
    // setters
    //
    public void setId (String val){
	if(val != null)
	    year = val;
    }
    public void setYear (String val){
	if(val != null)
	    year = val;
    }		
    public void setPer_alcohol (String val){
	if(val != null)
	    per_alcohol = val;
    }
    public void setPer_drug(String val){
	if(val != null)
	    per_drug = val;
    }		
    public void setPeriod_type(String val){
	if(val != null)
	    period_type = val;
    }
    public void setPeriod_count(String val){
	if(val != null)
	    period_count = val;
    }		
    public void setChecksum(String val){
	if(val != null)
	    checksum = val;
    }
		
    public String toString(){
	return year;
    }
    public boolean isNewYear(){
	boolean ret = false;
	String current_year = ""+Helper.getCurrentYear();
	if(year.equals("")){
	    year = current_year;

	}
	doSelect();				
	return isNewYear;
    }		
    //
    // check if we have any records for current year
    //
    public boolean isCurrentYear(){
	boolean ret = false;
	String current_year = ""+Helper.getCurrentYear();
	if(year.equals("")){
	    year = current_year;
	    doSelect();
	    isCurrentYear = true;
	}
	else{
	    isCurrentYear = year.equals(current_year);
	}
	return isCurrentYear;
    }
    public boolean canHaveMoreRuns(){
	// if this is a new year we can not have more runs
	if(isCurrentYear()){
	    return true;
	}
	return false;
    }
    public boolean hasTestRuns(){
	getTestRuns();
	return testRuns != null && testRuns.size() > 0;
    }
    public double getPer_alcohol_per_run(){
	double ret = 0;
	try{
	    ret = 0.+Integer.parseInt(per_alcohol);
	    if(period_type.equals("Quarterly")){
		ret /= 4.;
	    }						
	    else if(period_type.equals("Monthly")){
		ret /= 12.;
	    }
	    // for annually divide by 1 (not needed)

	}catch(Exception ex){
	    System.err.println(ex);
	}
	return ret;
    }
    public double getPer_drug_per_run(){
	double ret = 0;
	try{
	    ret = 0.+Integer.parseInt(per_drug);
	    if(period_type.equals("Quarterly")){
		ret /= 4.;
	    }						
	    else if(period_type.equals("Monthly")){
		ret /= 12.;
	    }
	    // for annually divide by 1 (not needed)

	}catch(Exception ex){
	    System.err.println(ex);
	}
	return ret;
    }
    public int getMaxRunCount(){
	int ret = 1;
	if(period_type.equals("Quarterly")){
	    ret = 4;
	}						
	else if(period_type.equals("Monthly")){
	    ret = 12;
	}
	return ret;
    }
    //
    public List<TestRun> getTestRuns(){
	if(testRuns == null && !year.equals("")){
	    TestRunList ptl = new TestRunList();
	    ptl.setYear(year);
	    String back = ptl.find();
	    if(back.equals("")){
		testRuns = ptl.getTestRuns();
	    }
	}
	return testRuns;
    }
    @Override
    public int hashCode() {
	int hash = 3, id_int = 0;
	if(!year.equals("")){
	    try{
		id_int = Integer.parseInt(year);
	    }catch(Exception ex){}
	}
	hash = 53 * hash + id_int; 
	return hash;
    }
    @Override
    public boolean equals(Object obj) {
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	final YearTest other = (YearTest) obj;
	return this.year.equals(other.year);
    }
    //
    public String doSelect(){
				
	String msg = "";
	String qq = " select per_alcohol,per_drug,period_type,period_count,checksum from year_tests where year=?";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	logger.debug(qq);
	if(year.equals("")){
	    year = ""+Helper.getCurrentYear();
	}
	try{
	    con = Helper.getConnection();
	    if(con == null){
		msg = "Could not connect ";
		return msg;
	    }
	    pstmt = con.prepareStatement(qq);
	    pstmt.setString(1, year);
	    rs = pstmt.executeQuery();	
	    if(rs.next()){
		setPer_alcohol(rs.getString(1));
		setPer_drug(rs.getString(2));
		setPeriod_type(rs.getString(3));
		setPeriod_count(rs.getString(4));
		setChecksum(rs.getString(5));
		isNewYear = false;
	    }
	    else{
		isNewYear = true;
		msg = "Record not found";
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
    public String doSave(){
	String msg = "";
	String qq = " insert into year_tests values(?,?,?,?,?, 0)";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	if(year.equals("") || per_alcohol.equals("") || per_drug.equals("")){
	    msg = " year, per alcohol, or per drug is missing";
	    return msg;
	}
	logger.debug(qq);
	try{
	    con = Helper.getConnection();
	    if(con == null){
		msg = "Could not connect ";
		return msg;
	    }
	    pstmt = con.prepareStatement(qq);
	    pstmt.setString(1, year);
	    pstmt.setString(2, per_alcohol);
	    pstmt.setString(3, per_drug);
	    pstmt.setString(4, period_type);
	    pstmt.setString(5, period_count);
	    // pstmt.setString(6, checksum);
	    pstmt.executeUpdate();
	}catch(Exception e){
	    msg += e+":"+qq;
	    logger.error(msg);
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	}
	return msg;
    }
    public String doUpdate(){
	String msg = "";
	String qq = " update year_tests set per_alcohol=?,per_drug=?, period_type=?,period_count=?,checksum=? where year=?";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	if(year.equals("") || per_alcohol.equals("") || per_drug.equals("")){
	    msg = " year, per alcohol, or per drug is missing";
	    return msg;
	}
	logger.debug(qq);
	try{
	    con = Helper.getConnection();
	    if(con == null){
		msg = "Could not connect ";
		return msg;
	    }
	    pstmt = con.prepareStatement(qq);
	    pstmt.setString(1, per_alcohol);
	    pstmt.setString(2, per_drug);
	    pstmt.setString(3, period_type);
	    pstmt.setString(4, period_count);
	    pstmt.setString(5, checksum);
	    pstmt.setString(6, year);
	    pstmt.executeUpdate();	
	}catch(Exception e){
	    msg += e+":"+qq;
	    logger.error(msg);
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	}
	return msg;
    }			
    public String doDelete(){
	String msg = "";
	String qq = " delete from year_tests where year=?";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	logger.debug(qq);
	try{
	    con = Helper.getConnection();
	    if(con == null){
		msg = "Could not connect ";
		return msg;
	    }
	    pstmt = con.prepareStatement(qq);
	    pstmt.setString(1, year);
	    pstmt.executeUpdate();	
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
