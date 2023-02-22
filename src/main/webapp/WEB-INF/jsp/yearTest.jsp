<%@  include file="header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->

<s:form action="yearTest" id="form_id" method="post">
	<s:hidden name="action2" id="action2" value="" />
	<s:if test="yearTest.isNewYear()">
		<h1>New Year Test</h1>
	</s:if>
	<s:else>
		<h1>Edit Year Test <s:property value="yearTest.id" /></h1>
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
  <p>* Required field <br />
		<s:if test="yearTest.isNewYear()">
			If you make any change, please hit the 'Save Changes' button
		</s:if>
		<s:else>
			You must hit 'Save' button to save data
		</s:else>
	</p>
	<dl class="fn1-output-field">
		<dt>Year </dt>
		<dd><s:textfield name="yearTest.year" value="%{yearTest.year}" size="4" maxlength="4" requiredLabel="true" required="true" />* 
		</dd>
	</dl>
	<dl class="fn1-output-field">
		<dt>Alcohol Percent </dt>
		<dd><s:textfield name="yearTest.per_alcohol" value="%{yearTest.per_alcohol}" size="3" maxlength="3" requiredLabel="true"  required="true" />* 
		</dd>
	</dl>
	<dl class="fn1-output-field">
		<dt>Drug Percent </dt>
		<dd><s:textfield name="yearTest.per_drug" value="%{yearTest.per_drug}" size="3" maxlength="3" requiredLabel="true"  required="true" />* 
		</dd>
	</dl>	
	<dl class="fn1-output-field">
		<dt>Period </dt>
		<dd><s:select name="yearTest.period_type" value="%{yearTest.period_type}" requiredLabel="true" required="true" list="testPeriods" listKey="name" listValue="name" id="period_id" headerKey="-1" headerValue="Pick Period" onchange="setFrequency()" />*, Frequency: <s:textfield name="yearTest.period_count" value="%{yearTest.period_count}" size="3" maxlength="3" id="frequency_id" disabled="true" />
		</dd>			
	</dl>
	<s:if test="!yearTest.isNewYear()">
		<dl class="fn1-output-field">
			<dt>Checksum</dt>
			<dd><s:property value="yearTest.checksum" /></dd>
		</dl>
	</s:if>
	<s:if test="yearTest.isNewYear()">
		<s:submit name="action" type="button" value="Save" class="fn1-btn"/></dd>
	</s:if>
	<s:elseif test="yearTest.canHaveMoreRuns()">
		<s:submit name="action" type="button" value="Save Changes" class="fn1-btn"/>			
		<a href="<s:property value='#application.url'/>testRun.action?year=<s:property value='yearTest.year' />&action=prepare" class="fn1-btn">New Test Run </a>
	</s:elseif>
</s:form>

<s:if test="yearTest.hasTestRuns()">
	<s:set var="testRuns" value="%{yearTest.testRuns}" />
	<s:set var="testRunsTitle" value="testRunsTitle" />
	<%@  include file="testRuns.jsp" %>
</s:if>
<s:else>
	<s:set var="yearTests" value="yearTests" />
	<s:set var="yearTestsTitle" value="yearTestsTitle" />
	<%@  include file="yearTests.jsp" %>
</s:else>
<%@  include file="footer.jsp" %>

<script type="text/javascript">
function setFrequency(){
	xx = document.getElementById("period_id");
	var indx = xx.selectedIndex;
	var yy = xx.options[indx].value;

	var fequency = "";	
	if(yy != "-1"){
		if(yy == "Quarterly"){
			frequency = 4;
		}
		else if(yy == "Monthly"){
			frequency = 12;
		}
		else{
			frequency = 1;
		}
		if(frequency != ""){
			document.getElementById("frequency_id").value=frequency;
		}
	}
}

</script>
