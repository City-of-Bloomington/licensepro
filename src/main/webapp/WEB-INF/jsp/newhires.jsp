<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<table class="fn1-table">
	<caption><s:property value="#newhiresTitle" /></caption>
	<thead>
		<tr>
			<th align="center"><b>ID</b></th>
			<th align="center"><b>Date</b></th>
		</tr>
	</thead>
	<tbody>
		<s:iterator var="one" value="#newhires">
			<tr>
				<td><a href="<s:property value='#application.url' />newhire.action?id=<s:property value='id' />">Details</a></td>
				<td><s:property value="date" /></td>
			</tr>
		</s:iterator>
	</tbody>
</table>
