package license.model;
/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import license.list.*;
import license.utils.*;

public class TestRun implements java.io.Serializable{

    String id = "", year="", test_run="", done="",
	witness="", witness2="";
    int total_pool = 0, quant_alco = 0, quant_drug = 0;
    String date="", time="";
    static final long serialVersionUID = 219L;		
    static Logger logger = LogManager.getLogger(TestRun.class);
    public static Random rand = new Random(System.nanoTime());
    List<Employee> employees = null;
    List<Employee> alco_selected = null;
    List<Employee> drug_selected = null;
    List<TestSelection> testSelections = null;
    YearTest yearTest = null;
    TestSelection alcoTest = null, drugTest = null;
	 
    String add_alco[] = null;
    String add_drug[] = null;
    public TestRun(){
    }

    public TestRun(String val){
	setId(val);
    }
    public TestRun(String val,
		   String val2,
		   String val3,
		   String val4,
		   String val5,
		   String val6,
		   String val7,
		   String val8,
		   String val9){

	setId(val);
	setYear(val2);
	setTest_run(val3);
	setDone(val4 != null && !val4.equals(""));
	setQuant_alco(val5);
	setQuant_drug(val6);
	setTotal_pool(val7);
	setWitness(val8);
	setWitness2(val9);
    }

    //
    //
    public String getId(){
	return id;
    }
    public String getYear(){
	return year;
    }		
    public String getTest_run(){
	return test_run;
    }
    public boolean getDone(){
	return !done.equals("");
    }
    public String getQuant_alco(){
	return ""+quant_alco;
    }
    public String getQuant_drug(){
	return ""+quant_drug;
    }
    public String getTotal_pool(){
	return ""+total_pool;
    }		
    public String getWitness(){
	return witness;
    }
    public String getWitness2(){
	return witness2;
    }
    public String getDate(){
	if(date.equals("") && id.equals("")){
	    date = Helper.getToday();
	}
	return date;
    }
    public String getTime(){
	if(time.equals("") && id.equals("")){
	    time = Helper.getCurrentTime();
	}
	return time;
    }		
    public String getWitnesses(){
	String ret = witness;
	if(!ret.equals("")){
	    ret += ", ";
	    ret += witness2;
	}
	return ret;
    }
    public String getInfo(){
	String ret = "";
	if(!year.equals("")){
	    ret = "("+year+") ";
	}
	ret += test_run;
	return ret;
    }
    //
    // setters
    //
    public void setId (String val){
	if(val != null)
	    id = val;
    }

    public void setYear (String val){
	if(val != null)
	    year = val;
    }
    public void setTest_run (String val){
	if(val != null)
	    test_run = val;
    }
    public void setDone(boolean val){
	if(val)
	    done = "y";
    }		
    public void setQuant_alco(String val){
	if(val != null && !val.equals("")){
	    try{
		quant_alco = Integer.parseInt(val);
	    }catch(Exception ex){}
	}
    }
    public void setQuant_drug(String val){
	if(val != null && !val.equals("")){
	    try{
		quant_drug = Integer.parseInt(val);
	    }catch(Exception ex){}
	}
    }
    public void setTotal_pool(String val){
	if(val != null && !val.equals("")){
	    try{
		total_pool = Integer.parseInt(val);
	    }catch(Exception ex){}
	}
    }
    public void setWitness(String val){
	if(val != null)
	    witness = val;
    }
    public void setWitness2(String val){
	if(val != null)
	    witness2 = val;
    }
    public void setDate(String val){
	if(val != null)
	    date = val;
    }
    public void setTime(String val){
	if(val != null)
	    time = val;
    }		
    public void setAdd_alco(String[] vals){
	if(vals != null)
	    add_alco = vals;
    }
    public void setAdd_drug(String[] vals){
	if(vals != null)
	    add_drug = vals;
    }		
    public String toString(){
	return year+": "+test_run;
    }
    public List<Employee> getAlcohol_selected(){
	return alco_selected;
    }
    public List<Employee> getDrug_selected(){
	return drug_selected;
    }
    public boolean hasAlcohol_selected(){
	return alco_selected !=null && alco_selected.size() > 0;
    }
    public boolean hasDrug_selected(){
	return drug_selected !=null && drug_selected.size() > 0;
    }
    public boolean hasTestSelections(){
	if(!id.equals("")){
	    getTestSelections();
	    return (testSelections != null && testSelections.size() > 0);
	}
	return false;
    }
    public YearTest getYearTest(){
	if(yearTest == null){
	    if(year.equals("")){
		year = ""+Helper.getCurrentYear();
	    }
	    YearTest one = new YearTest(year);
	    String back = one.doSelect();
	    if(back.equals("")){
		yearTest = one;
	    }
	}
	return yearTest;
    }
    public String doPrepare(){
	//
	// given the year (current year)
	// find the current pool
	// use yearTest percentages to specify quant_alco and quant_drug
	//
	String msg = "";
	getYearTest();
	if(yearTest == null){
	    msg = "Year test not set ";
	    return msg;
	}
	EmployeeList empl = new EmployeeList();
	empl.setActiveOnly();
	empl.setRequiredTesting();
	msg = empl.find();
	if(msg.equals("")){
	    employees = empl.getEmployees();
	}
	if(employees != null && employees.size() > 0){
	    total_pool = employees.size();
	    double count = total_pool * yearTest.getPer_alcohol_per_run()/100.;
	    quant_alco = (int)Math.ceil(count);
	    count = total_pool * yearTest.getPer_drug_per_run()/100.;
	    quant_drug = (int)Math.ceil(count);
	}
	msg += findNextTestRunNumber();
	if(msg.equals("")){
	    msg += doEmployeeSelection();
	}
	return msg;
    }
    public String findNextTestRunNumber(){
	TestRunList trl = new TestRunList(year);
	String back = trl.find();
	if(back.equals("")){ 
	    List<TestRun> testRuns = trl.getTestRuns();
	    if(testRuns != null){
		test_run = ""+(testRuns.size() + 1);
	    }
	    else{
		test_run = "1";
	    }
	}
	return back;
    }
    //
    public String doEmployeeSelection(){
	String back = "";
	String all="", all2="";
	if(employees == null || employees.size() == 0){
	    back = "empty list, can not select";
	    return back;
	}
	int size = employees.size();
	Employee[] empArr = new Employee[size];
	alco_selected = new ArrayList<Employee>();
	drug_selected = new ArrayList<Employee>();
	int jj = 0;
	for(Employee one:employees){
	    empArr[jj] = one;
	    jj++;
	}
	do{
	    jj = rand.nextInt(size); // nextInt(max-min+1)+min
	    all += ", "+jj;
	    if(empArr[jj] != null){
		alco_selected.add(empArr[jj]);
		empArr[jj] = null;
	    }
	}while(alco_selected.size() < quant_alco);
	//
	do{
	    jj = rand.nextInt(size); // nextInt(max-min+1)+min
	    all2 += ", "+jj;
	    if(empArr[jj] != null){
		drug_selected.add(empArr[jj]);
		empArr[jj] = null;
	    }
	}while(drug_selected.size() < quant_drug);
	//
	return back;
    }
    public String doSelect(){
				
	String msg = "";
	String qq = " select year,test_run,done,quant_alco,quant_drug,total_pool,witness,witness2  from test_runs where id=?";
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
	    pstmt.setString(1, id);
	    rs = pstmt.executeQuery();	
	    if(rs.next()){
		setYear(rs.getString(1));
		setTest_run(rs.getString(2));
		setDone(rs.getString(3) != null && !(rs.getString(3)).equals(""));
		setQuant_alco(rs.getString(4));
		setQuant_drug(rs.getString(5));
		setTotal_pool(rs.getString(6));
		setWitness(rs.getString(7));
		setWitness2(rs.getString(8));								
	    }
	    else{
		msg = "No match found";
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
	String qq = " insert into test_runs values(0,?,?,?,?, ?,?,?,?)";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	//
	if(year.equals("") || test_run.equals("")){
	    msg = " year or test run is not set";
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
	    pstmt.setString(2, test_run);
	    if(!done.equals(""))
		pstmt.setString(3, "y");
	    else
		pstmt.setNull(3, Types.CHAR);

	    pstmt.setInt(4, quant_alco);
	    pstmt.setInt(5, quant_drug);
	    pstmt.setInt(6, total_pool);
	    if(!witness.equals(""))
		pstmt.setString(7, witness);
	    else
		pstmt.setNull(7, Types.VARCHAR);
	    if(!witness2.equals(""))						
		pstmt.setString(8, witness2);
	    else
		pstmt.setNull(8, Types.VARCHAR);						
	    pstmt.executeUpdate();
	    qq = "select LAST_INSERT_ID() ";
	    logger.debug(qq);
	    pstmt = con.prepareStatement(qq);
	    rs = pstmt.executeQuery();
	    if(rs.next()){
		id = rs.getString(1);
	    }
	}catch(Exception e){
	    msg += e+":"+qq;
	    logger.error(msg);
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	}
	if(msg.equals("")){
	    getYearTest();
	    if(yearTest != null){
		alcoTest = new TestSelection(null, date, time, "Alcohol",""+total_pool,""+quant_alco, yearTest.getPer_alcohol(), id);
		msg += alcoTest.doSave();
		drugTest = new TestSelection(null, date, time, "Drug",""+total_pool,""+quant_drug, yearTest.getPer_drug(), id);
		msg += drugTest.doSave();
	    }
	    if(msg.equals(""))
		msg = addEmployees();
	}
	return msg;
    }
    public String doUpdate(){
	String msg = "";
	String qq = " update test_runs set done=?,witness=?,witness2=?  where id=?";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	if(year.equals("") || test_run.equals("")){
	    msg = " year or test run is not set";
	    return msg;
	}
	if(witness.equals("") || witness2.equals("")){
	    msg = " witnesses are required ";
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
	    if(!done.equals(""))
		pstmt.setString(1, "y");
	    else{
		pstmt.setNull(1, Types.CHAR);
	    }
	    pstmt.setString(2, witness);
	    pstmt.setString(3, witness2);
	    pstmt.setString(4, id);						
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
	String qq = " delete from test_runs where id=?";
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
	    pstmt.setString(1, id);
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
    //
    public String addEmployees(){
	String msg = "";
	if(add_alco != null && add_alco.length > 0){
	    if(alcoTest != null){
		String select_id = alcoTest.getId();
		for(String str:add_alco){
		    EmployeeSelection one = new EmployeeSelection(null,str,select_id,null,null);
		    msg += one.doSave();
		}
	    }
	}
	if(add_drug != null && add_drug.length > 0){
	    if(drugTest != null){
		String select_id = drugTest.getId();
		for(String str:add_drug){
		    EmployeeSelection one = new EmployeeSelection(null,str,select_id,null,null);
		    msg += one.doSave();
		}
	    }
	}
	return msg;
    }
    public List<TestSelection> getTestSelections(){

	if(testSelections == null && !id.equals("")){ 
	    TestSelectionList tsl = new TestSelectionList(id); // test_run_id
	    String msg = tsl.find();
	    if(msg.equals("")){
		List<TestSelection> tests = tsl.getTestSelections();
		if(tests != null && tests.size() > 0){
		    testSelections = tests;
		}
	    }
	}
	return testSelections;
    }
		
}
