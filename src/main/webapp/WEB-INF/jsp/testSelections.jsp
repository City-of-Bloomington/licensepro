<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<table class="fn1-table">
	<caption><s:property value="#testSelectionsTitle" /></caption>
	<thead>
		<tr>
			<th align="center"><b>ID</b></th>			
			<th align="center"><b>Selection Date</b></th>
			<th align="center"><b>Selection Time</b></th>
			<th align="center"><b>Test Type</b></th>			
			<th align="center"><b>Elegible</b></th>
			<th align="center"><b>Selected</b></th>
			<th align="center"><b>Percent</b></th>
		</tr>
	</thead>
	<tbody>
		<s:iterator var="one" value="#testSelections">
			<tr>
				<td><a href="<s:property value='#application.url' />testSelection.action?id=<s:property value='id' />">Details <s:property value="id" /></a></td>
				<td><s:property value="select_date" /></td>
				<td><s:property value="select_time" /></td>
				<td><s:property value="type" /></td>
				<td><s:property value="elegible" /></td>
				<td><s:property value="selected" /></td>
				<td><s:property value="percent" /></td>
			</tr>
		</s:iterator>
	</tbody>
</table>
