<%@  include file="header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<s:form action="report" method="post">    
  <h3> Reports</h3>
  <s:if test="hasActionErrors()">
	<div class="errors">
      <s:actionerror/>
	</div>
  </s:if>
  <s:if test="hasActionMessages()">
	<div class="welcome">
      <s:actionmessage/>
	</div>
  </s:if>
  <p>You may set some of the options below</p>
  <dl class="fn1-output-field">
      <dt>Report type: </dt>
      <dd> License Expire Report</dd>
  </dl>	
  <dl class="fn1-output-field">			
      <dt>Department: </label></dt>
      <dd><s:select name="report.dept_id" list="depts" value="%{report.dept_id}" listKey="id" listValue="name" headerKey="-1" headerValue="All" /> </dd>
  </dl>
  <dl class="fn1-output-field">
      <dt>License Type</dt>
      <dd><s:select name="report.type_id" value="%{report.type_id}" list="types" listKey="id" listValue="name" headerKey="-1" headerValue="All" /></dd>
  </dl>			
  <dl class="fn1-output-field">			
      <dt>Expire Year: </label></dt>
      <dd><s:select name="report.year" list="years" value="%{report.year}" /> , or</dd>
  </dl>
  <dl class="fn1-output-field">
      <dt>Expire Period: </dt>
      <dd><input type="radio" name="report.expirePeriod" value="expire6months" <s:if test="report.expirePeriod == 'expire6months'"> checked="checked"</s:if> /> Expire within 6 Months</dd>
      <dd><input type="radio" name="report.expirePeriod" value="expireOneYear" <s:if test="report.expirePeriod == 'expireOneYear'"> checked="checked"</s:if> /> Expire within One Year</dd>
      <dd><input type="radio" name="report.expirePeriod" value="expireSetDate"  <s:if test="report.expirePeriod == 'expireSetDate'"> checked="checked"</s:if>/> From:<s:textfield name="report.date_from" maxlength="10" size="10" value="%{report.date_from}" cssClass="date" /><label> To: </label><s:textfield name="report.date_to" maxlength="10" size="10" value="%{report.date_to}" cssClass="date" /></dd>
  </dl>
  <dl class="fn1-output-field">			
      <dt></dt>			
      <dd><s:submit name="action" type="button" value="Submit" cssClass="fn1-btn" /></dd>
  </dl>
</s:form>

<%@  include file="footer.jsp" %>	






































