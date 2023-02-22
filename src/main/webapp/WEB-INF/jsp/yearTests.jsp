<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<table class="fn1-table">
	<caption><s:property value="#yearTestsTitle" /></caption>
	<thead>
		<tr>
			<th align="center"><b>Year</b></th>
			<th align="center"><b>% Alcohol</b></th>
			<th align="center"><b>% Drug</b></th>
			<th align="center"><b>Period</b></th>
			<th align="center"><b>Frequency</b></th>
			<th align="center"><b>Checksum</b></th>			
		</tr>
	</thead>
	<tbody>
		<s:iterator var="one" value="#yearTests">
			<tr>
				<td><a href="<s:property value='#application.url' />yearTest.action?id=<s:property value='id' />"> <s:property value="year" /></a></td>
				<td><s:property value="per_alcohol" /></td>
				<td><s:property value="per_drug" /></td>
				<td><s:property value="period_type" /></td>
				<td><s:property value="period_count" /></td>
				<td><s:property value="checksum" /></td>
			</tr>
		</s:iterator>
	</tbody>
</table>
