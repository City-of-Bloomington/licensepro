<%@  include file="header_forprint.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<s:if test="employees != null">
    <s:set var="employees" value="employees" />
    <s:set var="printable" value="'true'" />
    <s:if test="showDept" >
	<s:set var="showDept" value="'true'" />
    </s:if>
    <s:else>
	<s:set var="deptDivNames" value="deptDivNames" />		
    </s:else>
    <s:set var="employeesTitle" value="employeesTitle" />
    <%@  include file="expireReport.jsp" %>
</s:if>
<s:else>
    <h3>No Match Found</h3>
</s:else>
<%@  include file="footer_forprint.jsp" %>























































