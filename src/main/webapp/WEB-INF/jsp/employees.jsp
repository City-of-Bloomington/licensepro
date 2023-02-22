<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
<table class="stat" border="1" width="95%">
	-->

<table <s:if test="#printable != null">border="1" width="90%"</s:if><s:else> class="fn1-table" </s:else>>	
	<caption><s:property value="#employeesTitle" /></caption>
	<thead>
		<tr>
			<th align="center"><b>Full Name</b></th>
			<th align="center"><b>DLN</b></th>
			<th align="center"><b>Type</b></th>
			<th align="center"><b>State</b></th>			
			<th align="center"><b>DOB</b></th>			
			<th align="center"><b>Exp Date</b></th>
			<th align="center"><b>Dept/Division</b></th>
			<s:if test="#doNotShowActive == null"> 
				<th align="center"><b>Active?</b></th>
			</s:if>
		</tr>
	</thead>
	<tbody>
		<s:iterator var="one" value="#employees">
			<tr>
				<td><a href="<s:property value='#application.url' />employee.action?id=<s:property value='id' />"><s:property value="fullName" /> </a></td>
				<td><s:property value="lic_number" /></td>
				<td><s:property value="type" /></td>
				<td><s:property value="state_id" /></td>
				<td><s:property value="dob" /></td>
				<td><s:property value="exp_date" /></td>
				<td><s:property value="deptDivNames" /></td>
				<s:if test="#doNotShowActive == null"> 
					<td><s:property value="active" /></td>
				</s:if>
			</tr>
		</s:iterator>
	</tbody>
</table>
