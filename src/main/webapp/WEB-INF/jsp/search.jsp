<%@  include file="header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->

<s:form action="search" id="form_id" method="post">
	<h1>Search Employees</h1>
  <s:if test="hasActionErrors()">
		<div class="errors">
      <s:actionerror/>
		</div>
  </s:if>
  <s:elseif test="hasActionMessages()">
		<div class="welcome">
      <s:actionmessage/>
		</div>
  </s:elseif>
	<div class="tt-row-container">	
		<div class="tt-split-container">			
			<dl class="fn1-output-field">
				<dt>Employee ID</dt>
				<dd><s:textfield name="employeeList.id" size="10" value="%{employeeList.id}" /></dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Employee Num</dt>
				<dd><s:textfield name="employeeList.employee_num" size="10" value="%{employeeList.employee_num}" /></dd>
			</dl>
			<dl class="fn1-output-field">			
				<dt> Employee Num </dt>
				<dd> Status <s:radio name="employeeList.empNumStatus" value="%{employeeList.empNumStatus}" list="#{'-1':'All','emp_num_set':'Set','emp_num_unset':'Unset'}" />
				</dd>
			</dl>	
			<dl class="fn1-output-field">
				<dt>Name </dt>
				<dd><s:textfield name="employeeList.name" value="%{employeeList.name}" size="15" maxlength="30" /> </dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>DLN </dt>
				<dd><s:textfield name="employeeList.lic_number" value="%{employeeList.lic_number}" size="15" maxlength="15" /> </dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>License Type</dt>
				<dd><s:select name="employeeList.type_id" value="%{employeeList.type_id}" list="types" listKey="id" listValue="name" headerKey="-1" headerValue="All" /></dd>
			</dl>
		</div>
		<div class="tt-split-container">
			<dl class="fn1-output-field">
				<dt>CDL Requir. Flag</dt>
				<dd>
					<s:radio name="employeeList.cdl_req_status" value="%{employeeList.cdl_req_status}" list="#{'-1':'All','y':'Set','n':'Unset'}" />
				</dd>
			</dl>	
			<dl class="fn1-output-field">
				<dt>State </dt>
				<dd><s:select name="employeeList.state_id" value="%{employeeList.state_id}" list="states" listKey="id" headerKey="-1" headerValue="All" listValue="name" /></dd>
			</dl>			
			<dl class="fn1-output-field">
				<dt>Department</dt>
				<dd><s:select name="employeeList.dept_id" value="%{employeeList.dept_id}" list="depts" listKey="id" headerKey="-1" headerValue="All" listValue="name" />
				</dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Exclude Depart.</dt>			
				<dd><s:select name="employeeList.excludeDept" value="%{employeeList.excludeDept}" list="depts" listKey="id" headerKey="-1" headerValue="None" listValue="name" /></dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Active Status</dt>
				<dd>
					<s:radio name="employeeList.activeStatus" value="%{employeeList.activeStatus}" list="#{'-1':'All','active':'Active Only','inactive':'Inactive Only'}" />
				</dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Sort by</dt>			
				<dd><s:select name="employeeList.sortBy" value="%{employeeList.sortBy}" list="sortOptions" listKey="id" listValue="name" /></dd>
			</dl>			
		</div>
	</div>
	<dl class="fn1-output-field">
		<dt>Expire Date</dt>
		<dd>From:<s:textfield name="employeeList.date_from" value="%{employeeList.date_from}" size="10" cssClass="date" /> To:<s:textfield name="employeeList.date_to" value="%{employeeList.date_to}" size="10" cssClass="date" /> 
		</dd>
	</dl>
	<dl class="fn1-input-field">
		<dt></dt>
		<dd><s:submit name="action" type="button" value="Submit" cssClass="fn1-btn" /></dd>
	</dl>
</s:form>
<s:if test="employees != null && employees.size() > 0">
	<s:set var="employees" value="employees" />
	<s:set var="employeesTitle" value="employeesTitle" />
	<%@  include file="employees.jsp" %>
</s:if>

<%@  include file="footer.jsp" %>
