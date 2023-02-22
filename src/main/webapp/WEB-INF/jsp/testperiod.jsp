<%@  include file="header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->

<s:form action="testing" id="form_id" method="post" >
	<s:hidden name="action2" id="action2" value="" />
	<h1>Select Test Period</h1>
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
	</p>
	<div class="tt-row-container">
		<dl class="fn1-input-field">
			<dt>Period </dt>
			<dd>
				<s:select name="perTest.period_id" value="" list="testPeriods" listKey="id" listValue="name" headerKey="-1" headerValue="Select Period" required="true" />*
			</dd>
		</dl>
		<s:submit name="action" type="button" value="Next" class="fn1-btn"/></dd>
	</div>
</s:form>

<%@  include file="footer.jsp" %>


