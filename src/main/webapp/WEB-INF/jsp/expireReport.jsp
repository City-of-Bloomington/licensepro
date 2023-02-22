<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
<table class="stat" border="1" width="95%">
	-->
<s:if test="#showDept == null">
	<h3>Dept/Division: <s:property value="deptDivNames" /></h3>
</s:if>
<p>
	This list shows employees with a driver's license expiring soon. Once a driver's license has been renewed, please send a copy to the Risk Department. 
</p>
<table class="fn1-table">	
	<caption><s:property value="#employeesTitle" /></caption>
	<thead>
		<tr>
			<th align="center"><b>Full Name</b></th>
			<th align="center"><b>DL Type</b></th>
			<th align="center"><b>State</b></th>			
			<th align="center"><b>Exp Date</b></th>
			<s:if test="#showDept != null"> 
				<th align="center"><b>Dept/Division</b></th>
			</s:if>
		</tr>
	</thead>
	<tbody>
		<s:iterator var="one" value="#employees">
			<tr>
				<td><a href="<s:property value='#application.url' />employee.action?id=<s:property value='id' />"><s:property value="fullName" /> </a></td>
				<td><s:property value="type" /></td>
				<td><s:property value="state_id" /></td>
				<td><s:property value="exp_date" /></td>
				<s:if test="#showDept != null"> 
					<td><s:property value="deptDivNames" /></td>
				</s:if>
			</tr>
		</s:iterator>
	</tbody>
</table>
