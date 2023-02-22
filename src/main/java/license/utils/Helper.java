package license.utils;
/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */

import java.io.*;
import java.sql.*;
import java.util.*;
import java.text.*;
import javax.sql.*;
import javax.naming.*;
import javax.naming.directory.*;
import java.security.MessageDigest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 *
 *
 */

public class Helper{

    static int c_con = 0;
    //
    // Non static variables
    //
    static Logger logger = LogManager.getLogger(Helper.class);

    //
    // basic constructor
    public Helper(boolean deb){
	//
	// initialize
	//
    }
    //
    public final static String getHashCodeOf(String buffer){

	String key = "Apps Secret Key "+getToday();
	byte[] out = performDigest(buffer.getBytes(),buffer.getBytes());
	String ret = bytesToHex(out);
	return ret;
	// System.err.println(ret);

    }
    //
    public final static byte[] performDigest(byte[] buffer, byte[] key) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(buffer);
            return md5.digest(key);
        } catch (Exception e) {
	    System.err.println(e);
        }
        return null;
    }
    //
    final static String bytesToHex(byte in[]) {
	byte ch = 0x00;
	int i = 0; 
	if (in == null || in.length <= 0)
	    return null;
	String pseudo[] = {"0", "1", "2",
	    "3", "4", "5", "6", "7", "8",
	    "9", "A", "B", "C", "D", "E",
	    "F"};
	StringBuffer out = new StringBuffer(in.length * 2);
	while (i < in.length) {
	    ch = (byte) (in[i] & 0xF0); // Strip off high nibble
		
	    ch = (byte) (ch >>> 4);
	    // shift the bits down
	    
	    ch = (byte) (ch & 0x0F);    
	    // must do this is high order bit is on!

	    out.append(pseudo[ (int) ch]); // convert the nibble to a String Character
	    ch = (byte) (in[i] & 0x0F); // Strip off low nibble 
	    out.append(pseudo[ (int) ch]); // convert the nibble to a String Character
	    i++;
	}
	String rslt = new String(out);
	return rslt;
    }    
    //
    /**
     * Adds escape character before certain characters
     *
     */
    final static String escapeIt(String s) {
		
	StringBuffer safe = new StringBuffer(s);
	int len = s.length();
	int c = 0;
	boolean noEscapeBefore = true;
	while (c < len) {                           
	    if ((safe.charAt(c) == '\'' ||
		 safe.charAt(c) == '"') && noEscapeBefore){
		safe.insert(c, '\\');
		c += 2;
		len = safe.length();
		noEscapeBefore = true;
	    }
	    else if(safe.charAt(c) == '\\'){ // to avoid double \\ before '
		noEscapeBefore = false;
		c++;
	    }
	    else {
		noEscapeBefore = true;
		c++;
	    }
	}
	return safe.toString();
    }
    //
    // users are used to enter comma in numbers such as xx,xxx.xx
    // as we can not save this in the DB as a valid number
    // so we remove any comma
    //
    public final static String cleanNumber(String s) {

	if(s == null) return null;
	String ret = "";
	int len = s.length();
	int c = 0;
	int ind = s.indexOf(",");
	if(ind > -1){
	    ret = s.substring(0,ind);
	    if(ind < len)
		ret += s.substring(ind+1);
	}
	else
	    ret = s;
	return ret;
    }
    /**
     * replaces the special chars that has certain meaning in html
     *
     * @param s the passing string
     * @returns string the modified string
     */
    public final static String replaceSpecialChars(String s) {
	char ch[] ={'\'','\"','>','<'};
	String entity[] = {"&#39;","&#34;","&gt;","&lt;"};
	//
	// &#34; = &quot;

	String ret ="";
	int len = s.length();
	int c = 0;
	boolean in = false;
	while (c < len) {             
	    for(int i=0;i< entity.length;i++){
		if (s.charAt(c) == ch[i]) {
		    ret+= entity[i];
		    in = true;
		}
	    }
	    if(!in) ret += s.charAt(c);
	    in = false;
	    c ++;
	}
	return ret;
    }
    public final static String replaceQuote(String s) {
	char ch[] ={'\''};
	String entity[] = {"_"};
	//
	// &#34; = &quot;

	String ret ="";
	int len = s.length();
	int c = 0;
	boolean in = false;
	while (c < len) {             
	    for(int i=0;i< entity.length;i++){
		if (s.charAt(c) == ch[i]) {
		    ret+= entity[i];
		    in = true;
		}
	    }
	    if(!in) ret += s.charAt(c);
	    in = false;
	    c ++;
	}
	return ret;
    }
    /**
     * get a database connection from the pool
     */
    public final static Connection getConnection(){

	Connection con = null;
	int trials = 0;
	boolean pass = false;
	while(trials < 3 && !pass){
	    try{
		trials++;
		logger.debug("Connection try "+trials);
		Context initCtx = new InitialContext();
		Context envCtx = (Context) initCtx.lookup("java:comp/env");
		DataSource ds = (DataSource)envCtx.lookup("jdbc/MySQL_license");
		con = ds.getConnection();
		if(con == null){
		    String str = " Could not connect to DB ";
		    logger.error(str);
		}
		else{
		    pass = testCon(con);
		    if(pass){
			c_con++;
			logger.debug("Got connection: "+c_con);
			logger.debug("Got connection at try "+trials);
		    }
		}
	    }
	    catch(Exception ex){
		logger.error(ex);
	    }
	}
	return con;
    }
    public final static Connection getMsSqlDatabaseConnect(EnvBean envBean) {
	Connection con = null;
	String dbSql = envBean.getMsSqlUrl()+";database="+envBean.getMsDb()+";user="+envBean.getMsUser()+";password="+envBean.getMsPass();
	try{
	    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
	    con = DriverManager.getConnection(dbSql);
	    System.err.println(" MS sql connected");
			
	}
	catch (Exception sqle) {
	    sqle.printStackTrace();
	}
	return con;
    }				
    /**
     * test the database connection
     */
    final static boolean testCon(Connection con){
		
	boolean pass = false;
	Statement stmt  = null;
	ResultSet rs = null;
	String qq = "select 1+1";		
	try{
	    if(con != null){
		stmt = con.createStatement();
		logger.debug(qq);
		rs = stmt.executeQuery(qq);
		if(rs.next()){
		    pass = true;
		}
	    }
	    rs.close();
	    stmt.close();
	}
	catch(Exception ex){
	    logger.error(ex+":"+qq);
	}
	return pass;
    }
    /**
     * Disconnect the database and related statements and result sets
     * 
     * @param con
     * @param stmt
     * @param rs
     */
    public final static void databaseDisconnect(Connection con,
						Statement stmt,
						ResultSet rs) {
	try {
	    if(rs != null) rs.close();
	    rs = null;
	    if(stmt != null) stmt.close();
	    stmt = null;
	    if(con != null) con.close();
	    con = null;
			
	    logger.debug("Closed Connection "+c_con);
	    c_con--;
	    if(c_con < 0) c_con = 0;
	}
	catch (Exception e) {
	    e.printStackTrace();
	}
	finally{
	    if (rs != null) {
		try { rs.close(); } catch (SQLException e) { ; }
		rs = null;
	    }
	    if (stmt != null) {
		try { stmt.close(); } catch (SQLException e) { ; }
		stmt = null;
	    }
	    if (con != null) {
		try { con.close(); } catch (SQLException e) { ; }
		con = null;
	    }
	}
    }
    /**
     * handle disconnection multiple datbase connections
     */
    public final static void databaseDisconnect(Connection con,
						ResultSet rs,
						Statement ... stmts
						) {
	try {
	    if(rs != null) rs.close();
	    rs = null;
	    for(Statement stmt: stmts){
		if(stmt != null) stmt.close();
		stmt = null;
	    }
	    if(con != null) con.close();
	    con = null;
			
	    logger.debug("Closed Connection "+c_con);
	    c_con--;
	    if(c_con < 0) c_con = 0;
	}
	catch (Exception e) {
	    e.printStackTrace();
	}
	finally{
	    if (rs != null) {
		try { rs.close(); } catch (SQLException e) { ; }
		rs = null;
	    }
	    for(Statement stmt: stmts){			
		if (stmt != null) {
		    try { stmt.close(); } catch (SQLException e) { ; }
		    stmt = null;
		}
	    }
	    if (con != null) {
		try { con.close(); } catch (SQLException e) { ; }
		con = null;
	    }
	}
    }
    public final static String getCurrentTime(){
				
	Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	String str = sdf.format(cal.getTime());
	return str;
    }
    /**
     * find current year
     */
    public final static int getCurrentYear(){

	int year = 2015;
	Calendar current_cal = Calendar.getInstance();
	year = current_cal.get(Calendar.YEAR);
	return year;
    }
    public final static int getCurrentMonth(){

	int month = 1;
	Calendar current_cal = Calendar.getInstance();
	month = current_cal.get(Calendar.MONTH)+1;
	return month;
    }		
    /**
     * get today in mm/dd/yyyy format
     */
    public final static String getToday(){

	String day="",month="",year="";
	Calendar current_cal = Calendar.getInstance();
	int mm =  (current_cal.get(Calendar.MONTH)+1);
	int dd =   current_cal.get(Calendar.DATE);
	year = ""+ current_cal.get(Calendar.YEAR);
	if(mm < 10) month = "0";
	month += mm;
	if(dd < 10) day = "0";
	day += dd;
	return month+"/"+day+"/"+year;
    }
    public final static String getDateMonthsFromNow(int add_months){

	String day="",month="",year="";
	Calendar current_cal = Calendar.getInstance();
	current_cal.add(Calendar.MONTH, add_months);
	int mm =  (current_cal.get(Calendar.MONTH)+1);
	int dd =   current_cal.get(Calendar.DATE);
	year = ""+ current_cal.get(Calendar.YEAR);
	if(mm < 10) month = "0";
	month += mm;
	if(dd < 10) day = "0";
	day += dd;
	return month+"/"+day+"/"+year;
    }		
		
    /**
     * get the next day in mm/dd/yyyy format
     */
    public final static String getNextDay(String date){
	String ret = "";
	int mm = 0, dd = 0, yy = 0;
	if(date != null){
	    String[] arr = date.split("/");
	    if(arr.length == 3){
		try{
		    mm = Integer.parseInt(arr[0]);
		    dd = Integer.parseInt(arr[1]);
		    yy = Integer.parseInt(arr[2]);
		    Calendar cal = Calendar.getInstance();
		    cal.set(Calendar.MONTH, (mm -1));
		    cal.set(Calendar.DATE, dd+1);
		    cal.set(Calendar.YEAR, yy);
					
		    mm = cal.get(Calendar.MONTH)+1;
		    dd = cal.get(Calendar.DATE);
		    yy = cal.get(Calendar.YEAR);
		    ret = mm+"/"+dd+"/"+yy;
		}catch(Exception ex){
		    System.err.println(ex);
		}
	    }
	}
	return ret;
    }
    public final static String todaySubtract(String years, String months){
	Calendar cal = Calendar.getInstance();
	int yy = 0, mm = 0, dd = 0;
	try{
	    if(years != null && !years.equals("0")){
		yy = Integer.parseInt(years);
	    }
	    if(months != null && !months.equals("0")){
		mm = Integer.parseInt(months);
	    }
	    mm = mm + yy*12; // total months
	    cal.add(Calendar.MONTH, -mm);
			
	}catch(Exception ex){
	    System.err.println(ex);
	}
	mm =  cal.get(Calendar.MONTH)+1;
	dd =  cal.get(Calendar.DATE);
	yy =  cal.get(Calendar.YEAR);
		
	return ""+mm+"/"+dd+"/"+yy;		
		
    }
    //
    // initial cap a word
    //
    public final static String initCapWord(String str_in){
	String ret = "";
	if(str_in !=  null){
	    if(str_in.length() == 0) return ret;
	    else if(str_in.length() > 1){
		ret = str_in.substring(0,1).toUpperCase()+
		    str_in.substring(1).toLowerCase();
	    }
	    else{
		ret = str_in.toUpperCase();   
	    }
	}
	return ret;
    }
    //
    // init cap a phrase
    //
    public final static String initCap(String str_in){
	String ret = "";
	if(str_in != null){
	    if(str_in.indexOf(" ") > -1){
		String[] str = str_in.split("\\s"); // any space character
		for(int i=0;i<str.length;i++){
		    if(i > 0) ret += " ";
		    ret += initCapWord(str[i]);
		}
	    }
	    else
		ret = initCapWord(str_in);// it is only one word
	}
	return ret;
    }
    /**
       define('ADS_DN','DC=cob,DC=bloomington,DC=in,DC=gov');
       define('ADS_USERNAME_ATTRIBUTE','CN');
       define('ADS_ADMIN_USER', 'your user');
       define('ADS_ADMIN_PASS',"your password");
       define('ADS_SERVER','ldaps://dc1.cob.bloomington.in.gov');
       define('ADS_PASSWORD_ATTRIBUTE','unicodePwd');
       ldap://dc1.cob.bloomington.in.gov:389/OU=Departments,DC=cob,DC=bloomington,DC=in,DC=gov // ssl port 636, non ssl 389
       principle : adminro@bloomington.in.gov
       password : 
    */
    public final static Hashtable<String, String> connectToServer(EnvBean bean){
	Hashtable<String, String> env = new Hashtable<String, String>(11);
	if(bean != null){
	    env.put(Context.INITIAL_CONTEXT_FACTORY, 
		    "com.sun.jndi.ldap.LdapCtxFactory");
	    env.put(Context.PROVIDER_URL, bean.getUrl());
	    env.put(Context.SECURITY_AUTHENTICATION, "simple"); 
	    env.put(Context.SECURITY_PRINCIPAL, bean.getPrinciple());
	    env.put(Context.SECURITY_CREDENTIALS, bean.getPassword());
	}
	return env;
    }
    public final static void getLdapUsersList(EnvBean bean){
	//
	int i=0;
	String fname = "", lname="",phone="",dept="",div="",uid="", employeeNumber="";
	Hashtable<String, String> env = null;
	try{
	    env = connectToServer(bean);
	    DirContext ctx = new InitialDirContext(env);
						
	    SearchControls ctls = new SearchControls();
	    /*
	      String[] attrIDs = {"uid","givenname","departmentnumber",
	      "telephonenumber","mail","sn",
	      "businessCategory"};
	    */
	    String[] attrIDs = {"sAMAccountName","givenName","sn","department","businessCategory","employeeNumber"};
						
	    ctls.setReturningAttributes(attrIDs);
	    ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
	    // String filter = "(&(uid=Fr*)(ou=People))";
	    //String filter = "(uid=F*)";
	    // String filter = "(&(ou=people) (departmentnumber=ITS))";
	    String filter = "(&(objectClass=user)(employeeNumber=*))";
	    NamingEnumeration<SearchResult> answer = ctx.search("", filter, ctls);
						

	    while(answer.hasMore()){
								
		SearchResult sr = answer.next();
		Attributes atts = sr.getAttributes();
		Attribute  attr = null;
		dept = "";
		div= "";								
		attr = atts.get("department");
		if(attr != null){
		    dept = attr.get().toString();
		}
		attr = atts.get("businessCategory");
		if (attr != null){
		    div = attr.get().toString();
		}
		attr = atts.get("givenName");
		if (attr != null){
		    fname = attr.get().toString();
		}
		attr = atts.get("sn");
		if (attr != null){
		    lname = attr.get().toString();
		}
		attr = atts.get("employeeNumber");
		if (attr != null){
		    employeeNumber = attr.get().toString();
		}
		attr = atts.get("sAMAccountName");
		if (attr != null){
		    uid = attr.get().toString();
		}
		// ldapUsers.put(fname+lname, employeeNumber);
		i++;
		//if(uid.trim().startsWith("s"))
		System.err.println(""+i+": "+uid+" "+lname+" "+fname+  
				   " "+dept+" "+div+" "+employeeNumber);
	    }
	}
	catch(Exception ex){
	    System.err.println(ex);
	}
    }
    public final static Set<String> getLdapEmployeeNums(EnvBean bean){

	String str = "";
	Hashtable<String, String> env = null;
	HashSet<String> set = new HashSet<String>();
	try{
	    env = connectToServer(bean);
	    DirContext ctx = new InitialDirContext(env);
						
	    SearchControls ctls = new SearchControls();
	    String[] attrIDs = {"employeeNumber"};
						
	    ctls.setReturningAttributes(attrIDs);
	    ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
	    String filter = "(&(objectClass=user)(employeeNumber=*))";
	    NamingEnumeration<SearchResult> answer = ctx.search("", filter, ctls);
	    while(answer.hasMore()){
								
		SearchResult sr = answer.next();
		Attributes atts = sr.getAttributes();
		Attribute  attr = null;
		attr = atts.get("employeeNumber");
		if (attr != null){
		    str = attr.get().toString();
		    set.add(str);
		}
	    }
	}
	catch(Exception ex){
	    System.err.println(ex);
	}
	return set;
    }		
	
}






















































