<%@  include file="header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->

<s:form action="testRun" id="form_id" method="post">
	<s:hidden name="action2" id="action2" value="" />
	<s:if test="testRun.id == ''">
		<h1>New Test Run</h1>
	</s:if>
	<s:else>
		<h1>Test Run <s:property value="testRun.info" /></h1>
		<s:hidden name="testRun.id" value="%{testRun.id}" />
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
	<s:hidden name="testRun.year" value="%{testRun.year}" />
	<s:hidden name="testRun.test_run" value="%{testRun.test_run}" />
	<s:hidden name="testRun.quant_alco" value="%{testRun.quant_alco}" />
	<s:hidden name="testRun.quant_drug" value="%{testRun.quant_drug}" />
	<s:hidden name="testRun.total_pool" value="%{testRun.total_pool}" />
	<s:if test="testRun.id == ''">
		<s:if test="testRun.hasAlcohol_selected()">
			<s:iterator var="one" value="testRun.alcohol_selected">
				<input type="hidden" name="testRun.add_alco" value="<s:property value='id' />" />
			</s:iterator>
		</s:if>
		<s:if test="testRun.hasDrug_selected()">
			<s:iterator var="one" value="testRun.drug_selected">
				<input type="hidden" name="testRun.add_drug" value="<s:property value='id' />" />
			</s:iterator>
		</s:if>				
	</s:if>
	<s:if test="testRun.id == ''">
		<p>* Required field <br />			
			You must hit 'Save' button to save data
		</p>			
	</s:if>
	<dl class="fn1-output-field">
		<dt>Year </dt>
		<dd><a href="<s:property value='#application.url' />yearTest.action?year=<s:property value='%{testRun.year}' />"> <s:property value="%{testRun.year}" /></a>		
		</dd>
	</dl>
	<dl class="fn1-output-field">
		<dt>Test Run </dt>
		<dd><s:property value="%{testRun.test_run}" />
		</dd>
	</dl>
	<dl class="fn1-output-field">
		<dt>Acohol Testers </dt>
		<dd><s:property value="%{testRun.quant_alco}" />
		</dd>
	</dl>
	<dl class="fn1-output-field">
		<dt>Drug Testers </dt>
		<dd><s:property value="%{testRun.quant_drug}"  /> 
		</dd>
	</dl>
	<dl class="fn1-output-field">
		<dt>Total Pool </dt>
		<dd><s:property value="%{testRun.total_pool}" />
		</dd>
	</dl>
	<s:if test="testRun.id == ''">
		<dl class="fn1-output-field">
			<dt>Selection Date</dt>
			<dd><s:textfield name="testRun.date" value="%{testRun.date}" size="10" maxlength="10" cssClass="date" />*
			</dd>			
		</dl>
		<dl class="fn1-output-field">
			<dt>Selection Time</dt>
			<dd><s:textfield name="testRun.time" value="%{testRun.time}" size="10" maxlength="10" cssClass="time" />*
			</dd>			
		</dl>
	</s:if>
	<dl class="fn1-output-field">
		<dt>Performed </dt>
		<dd><s:checkbox name="testRun.done" value="%{testRun.done}" /> 
		</dd>
	</dl>
	<s:if test="testRun.id == ''">	
		<dl class="fn1-output-field">
			<dt>First Witness</dt>
			<dd><s:textfield name="testRun.witness" value="%{testRun.witness}" size="30" maxlength="30" />
			</dd>			
		</dl>
		<dl class="fn1-output-field">
			<dt>Second Witness</dt>
			<dd><s:textfield name="testRun.witness2" value="%{testRun.witness2}" size="30" maxlength="30" />
			</dd>			
		</dl>
	</s:if>
	<s:else>
		<dl class="fn1-output-field">
			<dt>First Witness</dt>
			<dd><s:textfield name="testRun.witness" value="%{testRun.witness}" size="30" maxlength="30" required="true" />*
			</dd>			
		</dl>
		<dl class="fn1-output-field">
			<dt>Second Witness</dt>
			<dd><s:textfield name="testRun.witness2" value="%{testRun.witness2}" size="30" maxlength="30" required="true" />*
			</dd>			
		</dl>
	</s:else>
	<s:if test="testRun.id == ''">
		<s:if test="testRun.hasAlcohol_selected()">
			<table class="fn1-table">
				<caption>Selected Employees for Alcohol Test</caption>
				<thead>
					<tr>
						<th align="center"><b>ID</b></th>
						<th align="center"><b>Name</b></th>
						<th align="center"><b>DLN</b></th>
						<th align="center"><b>Type</b></th>
						<th align="center"><b>State</b></th>			
						<th align="center"><b>DOB</b></th>			
						<th align="center"><b>Dept/Division</b></th>
					</tr>
				</thead>
				<tbody>
					<s:iterator var="one" value="testRun.alcohol_selected">
						<tr>
							<td><s:property value="id" /></td>
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
		</s:if>
		<s:if test="testRun.hasDrug_selected()">
			<table class="fn1-table">
				<caption>Selected Employees for Drug Test</caption>
				<thead>
					<tr>
						<th align="center"><b>ID</b></th>
						<th align="center"><b>Name</b></th>
						<th align="center"><b>DLN</b></th>
						<th align="center"><b>Type</b></th>
						<th align="center"><b>State</b></th>			
						<th align="center"><b>DOB</b></th>			
						<th align="center"><b>Dept/Division</b></th>
					</tr>
				</thead>
				<tbody>
					<s:iterator var="one" value="testRun.drug_selected">
						<tr>
							<td><s:property value="id" /></td>
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
		</s:if>
		<s:submit name="action" type="button" value="Save" class="fn1-btn"/>
	</s:if>
	<s:else>
		<s:submit name="action" type="button" value="Save Changes" class="fn1-btn"/>
	</s:else>
</s:form>

<s:if test="testRun.hasTestSelections()">
	<s:set var="testSelections" value="%{testRun.testSelections}" />
	<s:set var="testSelectionsTitle" value="testSelectionsTitle" />
	<%@  include file="testSelections.jsp" %>
</s:if>

<%@  include file="footer.jsp" %>


