<%@  include file="header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->

	<s:if test="testSelection.id != ''">
		<h1>Test Selection <s:property value="testSelection.id" /></h1>
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
	<div class="tt-row-container">	
		<div class="tt-split-container">			
			<dl class="fn1-output-field">
				<dt>Year Test </dt>
				<dd><a href="<s:property value='#application.url' />yearTest.action?id=<s:property value='%{testSelection.testRun.year}' />"> <s:property value="%{testSelection.testRun.year}" /> </a>		
				</dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Test Run</dt>			
				<dd><a href="<s:property value='#application.url' />testRun.action?id=<s:property value='%{testSelection.test_run_id}' />"> <s:property value="%{testSelection.testRun.test_run}" /></a>		
				</dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Selection Date </dt>
				<dd><s:property value="%{testSelection.select_date}" />
				</dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Selection Time </dt>
				<dd><s:property value="%{testSelection.select_time}" />
				</dd>
			</dl>
		</div>
		<div class="tt-split-container">				
			<dl class="fn1-output-field">
				<dt>Test Type </dt>
				<dd><s:property value="%{testSelection.type}"  /> 
				</dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Elegible #</dt>
				<dd><s:property value="%{testSelection.elegible}" />
				</dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Selected #</dt>
				<dd><s:property value="%{testSelection.selected}" />
				</dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Percent </dt>
				<dd><s:property value="%{testSelection.percent}" />
				</dd>
			</dl>
		</div>
	</div>
	<s:if test="action == ''">
		<a href="<s:property value='#application.url'/>testSelection.action?id=<s:property value='testSelection.id' />&action=print" class="fn1-btn">Printable</a>
	</s:if>
	<s:if test="testSelection.hasEmployees()">
		<s:set var="employees" value="%{testSelection.employees}" />
		<s:set var="employeesTitle" value="employeesTitle" />
		<s:set var="doNotShowActive" value="'true'" />
		<s:if test="action != ''">
			<s:set var="printable" value="'true'" />			
		</s:if>
		<%@  include file="employees.jsp" %>
	</s:if>
<%@  include file="footer.jsp" %>

