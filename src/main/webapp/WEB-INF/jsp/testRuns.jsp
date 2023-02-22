<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<table class="fn1-table">	
	<caption><s:property value="#testRunsTitle" /></caption>
	<thead>
		<tr>
			<th align="center"><b>ID</b></th>			
			<th align="center"><b>Year</b></th>
			<th align="center"><b>Test Run</b></th>
			<th align="center"><b>Performed?</b></th>			
			<th align="center"><b>Alcohol Count</b></th>
			<th align="center"><b>Drug Count/b></th>
			<th align="center"><b>Total Pool</b></th>
			<th align="center"><b>Witnesses</b></th>			
		</tr>
	</thead>
	<tbody>
		<s:iterator var="one" value="#testRuns">
			<tr>
				<td><a href="<s:property value='#application.url' />testRun.action?id=<s:property value='id' />"> Details</a></td>
				<td><s:property value="year" /></td>
				<td><s:property value="test_run" /></td>
				<td><s:property value="done" /></td>
				<td><s:property value="quant_alco" /></td>
				<td><s:property value="quant_drug" /></td>
				<td><s:property value="total_pool" /></td>
				<td><s:property value="witnesses" /></td>
			</tr>
		</s:iterator>
	</tbody>
</table>
