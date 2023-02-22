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

public class YearList{

    static final long serialVersionUID = 122L;		
    static Logger logger = LogManager.getLogger(YearList.class);
    List<Type> years = null;
    boolean future_years = false;
    String name = "";
    public YearList(){
    }	
    public YearList(boolean val){
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
	int start_year = 1996; // start of the app in production
	years = new ArrayList<Type>();
	if(start_year <= this_year){
	    for(int year=this_year;year>=start_year;year--){
		Type one = new Type(""+year, ""+year);
		years.add(one);
	    }
	}
	if(future_years){
	    for(int year=this_year+1;year<this_year+5;year++){
		Type one = new Type(""+year, ""+year);
		years.add(one);
	    }
	}
	return "";
    }
	
}
