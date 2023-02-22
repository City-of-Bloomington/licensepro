package license.utils;


public class EnvBean {

    String url = "", principle = "", method="", password = "", ctxFactory = "";
    // for new world db
    String msSqlUrl="", msDb="", msUser="", msPass="";
    public EnvBean(){};
    /**
     * setters
     */
    public void setUrl(String val){
	if(val != null)
	    url = val;
    }
    public void setPrinciple(String val){
	if(val != null)
	    principle = val;
    }	
    public void setMethod(String val){
	if(val != null)
	    method = val;
    }
    public void setPassword(String val){
	if(val != null)
	    password = val;
    }
    public void setCtxFactory(String val){
	if(val != null)
	    ctxFactory = val;
    }
    public void setMsSqlUrl(String val){
	if(val != null)
	    msSqlUrl = val;
    }
    public void setMsDb(String val){
	if(val != null)
	    msDb = val;
    }
    public void setMsUser(String val){
	if(val != null)
	    msUser = val;
    }
    public void setMsPass(String val){
	if(val != null)
	    msPass = val;
    }
    /**
     * getters
     */
    public String getUrl(){
	return url;
    }
    public String getPrinciple(){
	return principle;
    }	
    public String getMethod(){
	return method;
    }
    public String getPassword(){
	return password;
    }
    public String getCtxFactory(){
	return ctxFactory;
    }
    public String getMsSqlUrl(){
	return msSqlUrl;
    }
    public String getMsDb(){
	return msDb;
    }
    public String getMsUser(){
	return msUser;
    }
    public String getMsPass(){
	return msPass;
    }		
    public String toString(){
	String str = "";
	if(!url.equals("")) str += " url; "+url;
	if(!principle.equals("")) str += " principle; "+principle;	
	if(!method.equals("")) str += " method; "+method;
	if(!ctxFactory.equals("")) str += " factory; "+ctxFactory;
	if(!password.equals("")) str += " pass; "+password;
	return str;
    }
	
}
