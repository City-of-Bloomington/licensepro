<%@  include file="header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<h3>TrafficTracker</h3>
<s:if test="hasActionErrors()">
  <div class="errors">
    <s:actionerror/>
  </div>
</s:if>
<p>Note: You can click on any of the lines/points (if there is any) in the map to get the project name and link</p>
<div id="map" class="mapPanel"></div>
<div id="marker"></div>

<table class="fn1-table">
	<caption><s:property value="projectsTitle" /></caption>
	<thead>
		<tr>
			<th align="center"><b>ID</b></th>
			<th align="center"><b>Name</b></th>
			<th align="center"><b>PM Lead</b></th>
			<th align="center"><b>Eng Lead</b></th>			
			<th align="center"><b>Features</b></th>
			<th align="center"><b>Actual End Date</b></th>
			<th align="center"><b>Most Recent Phase Rank</b></th>
			<th>Geometry</th>
		</tr>
	</thead>
	<tbody>
		<s:iterator var="one" value="%{projects}">
			<tr>
				<td><a href="<s:property value='#application.url' />project.action?id=<s:property value='id' />"><s:property value="id" /> Details</a></td>
				<td><s:property value="name" /></td>
				<td><s:property value="lead" /></td>
				<td><s:property value="eng_lead" /></td>
				<td>
					<s:if test="hasFeatures()">
						<s:property value="allFeaturesText" />
					</s:if>
				</td>
				<td><s:property value="actual_end_date" /></td>				
				<s:if test="hasProjectUpdates()">
					<td><s:property value="lastProjectUpdate.phase_rank" /></td>
				</s:if>
				<s:else>
					<td>&nbsp;</td>
				</s:else>
				<s:if test="hasGeometry()">
					<td class="project">Yes
						<div id="<s:property value='id' />" class="<s:property value='firstFeatureId' />" >
							<div class="geography" style="display:none"><s:property value="geometry" /></div>
							<div class="project_name" style="display:none"><s:property value="name" /></div>
							<div class="url" style="display:none"><s:property value="#application.url" /></div>							
						</div>
					</td>
				</s:if>
				<s:else>
					<td>&nbsp;</td>
				</s:else>
			</tr>
		</s:iterator>
	</tbody>
</table>

<script type="text/javascript" src="<s:property value='#application.url' />js/ol.js"></script>
<script type="text/javascript" src="<s:property value='#application.url' />js/ol3-popup.js"></script>
<script type="text/javascript" src="<s:property value='#application.url' />js/showMap.js"></script>

<%@  include file="footer.jsp" %>
