package license.web;

import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import license.model.*;
import license.list.*;
import license.utils.*;

@WebServlet(urlPatterns = {"/DivisionService"})
public class DivisionService extends TopServlet{

    static final long serialVersionUID = 180L;	
    static Logger logger = LogManager.getLogger(DivisionService.class);
    
    public void doGet(HttpServletRequest req, 
		      HttpServletResponse res) 
	throws ServletException, IOException {
	doPost(req,res);
    }
    /**
     * @link #doGetost
     */

    public void doPost(HttpServletRequest req, 
		       HttpServletResponse res) 
	throws ServletException, IOException {
    
	String message="", action="";
	res.setContentType("application/json");
	PrintWriter out = res.getWriter();
	String name, value;
	String fullName="", term= "", id="", type="feature"; // default
	boolean success = true;
	HttpSession session = null;
	Enumeration<String> values = req.getParameterNames();
	String [] vals = null;
	while (values.hasMoreElements()){
	    name = values.nextElement().trim();
	    vals = req.getParameterValues(name);
	    value = vals[vals.length-1].trim();	
	    if (name.equals("id")) { // dept id
		id = value;
	    }
	    else if (name.equals("action")){ 
		action = value;  
	    }
	    else{
		// System.err.println(name+" "+value);
	    }
	}
	List<Division> divs = null;
	if(!id.equals("")){				
	    DivisionList dl = new DivisionList(id);
	    dl.setActiveOnly();
	    String back = dl.find();
	    if(back.equals(""))
		divs = dl.getDivisions();

	}
	if(divs != null && divs.size() > 0){
	    String json = writeJson(divs);
	    out.println(json);
	}
	out.flush();
	out.close();
    }
    String writeJson(List<Division> ones){
	String json="";
	if(ones.size() > 0){
	    for(Division one:ones){
		if(!json.equals("")) json += ",";
		json += "{\"id\":\""+one.getId()+"\",\"value\":\""+one.getName()+"\"}";
	    }
	}
	json = "["+json+"]";
	// System.err.println("json "+json);
	return json;
    }

}






















































