<%@  include file="header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<s:form action="check" id="form_id" method="post" >
	<s:hidden name="action2" id="action2" value="" />
	<s:if test="id == ''">
		<h1>Inactive Employee Finding Procedure</h1>
	</s:if>
	<s:else>
		<h1> Inactive Employee Finding Procedure <s:property value="check.id" /></h1>
		<s:hidden name="check.id" value="%{check.id}" />
	</s:else>
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
	<s:if test="id == ''">
		The system will try to find all the employees that are not active <br /> 			
		any more (left, retired, ..etc) against the directory server.<br />
		All the inactive employees will be listed below where you can <br />confirm that they are active/inactive. <br />
		To start click on 'Start' button <br />
	</s:if>
	<s:elseif test="!check.confirmed && check.hasEmployees()">
		The list below are all the employees that we think they are inactive but you can uncheck the ones that you think they are still active and keep checked the ones that you think they are inactive. You can check with HR or related department if not sure.<br />
		When done with your selection, hit the 'Confirm' button.<br />
		If not sure, you can invistigate this list and come back later to confirm it.
	</s:elseif>
	<div class="tt-row-container">
		<s:if test="check.id != ''">
			<dl class="fn1-output-field">
				<dt>ID </dt>
				<dd><s:property value="check.id" /> </dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Run Date </dt>
				<dd><s:property value="%{check.date}" /> </dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Confirmed?</dt>
				<s:if test="check.confirmed" >
					<dd>Yes </dd>
				</s:if>
				<s:else>
					<dd>No </dd>
				</s:else>
			</dl>
		</s:if>		
		<s:if test="check.id != ''">
			<s:if test="!check.confirmed">
				<table class="fn1-table">
					<caption><s:property value="employeesTitle" /></caption>
					<thead>
						<tr>
							<th align="center"><b>Employee #</b></th>
							<th align="center"><b>Name</b></th>
							<th align="center"><b>DLN</b></th>
							<th align="center"><b>Type</b></th>
							<th align="center"><b>State</b></th>			
							<th align="center"><b>DOB</b></th>			
							<th align="center"><b>Dept/Division</b></th>
						</tr>
					</thead>
					<tbody>
						<s:iterator var="one" value="%{check.employees}">
							<tr>
								<td><input type="checkbox" name="check.add_inactive" checked="checked" value="<s:property value='id' />" /> <s:property value="employee_num" /></td>
								<td><s:property value="fullName" /></td>
								<td><s:property value="lic_number" /></td>
								<td><s:property value="type" /></td>
								<td><s:property value="state_id" /></td>
								<td><s:property value="dob" /></td>
								<td><s:property value="deptDivNames" /></td>				
							</tr>						
						</s:iterator>
					</tbody>
				</table>
				<s:if test="check.hasEmployees()">
					<s:submit name="action" type="button" value="Confirm" class="fn1-btn"/>
				</s:if>
			</s:if>
			<s:else>
				<s:set var="employees" value="check.employees" />
				<s:set var="employeesTitle" value="employeesTitle" />
				<%@  include file="employees.jsp" %>
			</s:else>
		</s:if>
		<s:else>
			<s:submit name="action" type="button" value="Start" class="fn1-btn"/></dd>
		</s:else>
</s:form>
<s:if test="id == '' && checks != null && checks.size() > 0">
	<s:set var="checks" value="checks" />
	<s:set var="checksTitle" value="checksTitle" />
	<%@  include file="checks.jsp" %>
</s:if>
<%@  include file="footer.jsp" %>


