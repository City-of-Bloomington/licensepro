<%@  include file="header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->

<h1>Year Test <s:property value="yearTest.id" /></h1>
If you would like to run a new test, it is recommended that you run <br />
<a href="<s:property value='#application.url' />check.action" />Inactive Check</a>
first to eleminate the inactive employees from the pool.<br />
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
<dl class="fn1-output-field">
	<dt>Year </dt>
	<dd><s:property value="%{yearTest.year}" />
	</dd>
</dl>
<dl class="fn1-output-field">
	<dt>Alcohol Percent </dt>
	<dd><s:property value="%{yearTest.per_alcohol}"  />
	</dd>
</dl>
<dl class="fn1-output-field">
	<dt>Drug Percent </dt>
	<dd><s:property value="%{yearTest.per_drug}" />
	</dd>
</dl>	
<dl class="fn1-output-field">
	<dt>Period </dt>
	<dd><s:property value="%{yearTest.period_type}" />, Frequency: <s:property value="%{yearTest.period_count}" />
	</dd>			
</dl>
<dl class="fn1-output-field">
	<dt>Checksum</dt>
	<dd><s:property value="yearTest.checksum" /></dd>
</dl>
<s:if test="yearTest.canHaveMoreRuns()">
	<a href="<s:property value='#application.url'/>yearTest.action?year=<s:property value='yearTest.year' />&action=Edit" class="fn1-btn">Edit </a>
	<a href="<s:property value='#application.url'/>testRun.action?year=<s:property value='yearTest.year' />&action=prepare" class="fn1-btn">New Test Run </a>	
</s:if>

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

</script>
