<%@  include file="header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->

<s:form action="testSearch" id="form_id" method="post">
	<h1>Tests Search </h1>
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
		<dd><s:select name="yearTestList.year" value="%{yearTestList.year}" list="years" headerKey="-1" headerValue="All" listKey="id" listValue="name" /></dd>
	</dl>
	<dl class="fn1-output-field">
		<dt>Test Type</dt>
		<dd><s:select name="yearTestList.test_type" value="%{yearTestList.test_type}" list="#{'-1':'All','Alcohol':'Alcohol','Drug':'Drug'}" /></dd>
	</dl>
	<dl class="fn1-output-field">
		<dt>Test Selection Date </dt>
		<dd>From:<s:textfield name="yearTestList.date_from" value="%{yearTestList.date_from}" size="10" cssClass="date" /> To:<s:textfield name="yearTestList.date_to" value="%{yearTestList.date_to}" size="10" cssClass="date" /></dd>
	</dl>
	<dl class="fn1-input-field">
		<dt></dt>
		<dd><s:submit name="action" type="button" value="Submit" cssClass="fn1-btn" /></dd>
	</dl>
</s:form>
<s:if test="yearTests != null && yearTests.size() > 0">
	<s:set var="yearTests" value="yearTests" />
	<s:set var="yearTestsTitle" value="yearTestsTitle" />
	<%@  include file="yearTests.jsp" %>
</s:if>

<%@  include file="footer.jsp" %>
