<%@  include file="header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->

<s:form action="newhire" id="form_id" method="post" >
	<s:hidden name="action2" id="action2" value="" />
	<h1>Newly Hired Employee Finding Procedure</h1>	
	<s:if test="id != ''">
		<s:hidden name="newhire.id" value="%{newhire.id}" />
	</s:if>
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
		<p>
		The system will try to find all the newly hired employees automatically through New World database excluding Transit employees as they are on a different system.<br />
		The data will be copied to licensepro database but the data is not complete  and the missing data need to be added individually such DLN, State, lincense type etc.<br />
		To start click on 'Start' button <br />
		</p>
	</s:if>
	<div class="tt-row-container">
		<s:if test="newhire.id != ''">
			<dl class="fn1-output-field">
				<dt>ID </dt>
				<dd><s:property value="newhire.id" /> </dd>
			</dl>
		</s:if>
		<dl class="fn1-output-field">
			<dt>Run Date </dt>
			<dd><s:property value="newhire.date" /> </dd>
		</dl>
		<s:if test="newhire.id != ''">
			<s:if test="newhire.employees != null">
				<s:set var="employees" value="newhire.employees" />
				<s:set var="employeesTitle" value="employeesTitle" />
				<%@  include file="employees.jsp" %>				
			</s:if>
			<s:else>
				<p>No new hires found in this run</p>
			</s:else>
		</s:if>
		<s:else>
			<s:submit name="action" type="button" value="Start" class="fn1-btn"/>
		</s:else>
	</div>
</s:form>
<s:if test="id == '' && newhires != null && newhires.size() > 0">
	<s:set var="newhires" value="newhires" />
	<s:set var="newhiresTitle" value="newhiresTitle" />
	<%@  include file="newhires.jsp" %>
</s:if>
<%@  include file="footer.jsp" %>


