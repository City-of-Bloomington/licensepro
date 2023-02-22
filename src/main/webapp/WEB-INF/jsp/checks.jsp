<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<table class="fn1-table">
	<caption><s:property value="#checksTitle" /></caption>
	<thead>
		<tr>
			<th align="center"><b>ID</b></th>
			<th align="center"><b>Date</b></th>
			<th align="center"><b>Confirmed?</b></th>
		</tr>
	</thead>
	<tbody>
		<s:iterator var="one" value="#checks">
			<tr>
				<td><a href="<s:property value='#application.url' />check.action?id=<s:property value='id' />">Details</a></td>
				<td><s:property value="date" /></td>
				<td><s:property value="confirmed" /></td>
			</tr>
		</s:iterator>
	</tbody>
</table>
