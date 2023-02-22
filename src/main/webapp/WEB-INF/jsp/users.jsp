<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<table class="fn1-table">
	<caption><s:property value="#usersTitle" /></caption>
	<thead>
		<tr>
			<th align="center"><b>Username</b></th>
			<th align="center"><b>Full Name</b></th>
			<th align="center"><b>Roles</b></th>
			<th align="center"><b>Active?</b></th>			
		</tr>
	</thead>
	<tbody>
		<s:iterator var="one" value="#users">
			<tr>
				<td><a href="<s:property value='#application.url' />user.action?id=<s:property value='id' />"> <s:property value="userid" /></a></td>
				<td><s:property value="fullname" /></td>
				<td><s:property value="roleInfo" /></td>				
				<td><s:property value="active" /></td>
			</tr>
		</s:iterator>
	</tbody>
</table>
