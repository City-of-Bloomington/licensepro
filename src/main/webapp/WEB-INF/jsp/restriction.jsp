<%@  include file="header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->


<s:form action="restriction" id="form_id" method="post">
	<s:hidden name="emprestr.emp_id" value="%{emprestr.emp_id}" />
	<s:hidden name="action2" id="action2" value="" />
	<h1>Employee License Restrictions </h1>
  <s:if test="hasActionErrors()">
		<div class="errors">
      <s:actionerror/>
	</div>
  </s:if>
  <s:elseif test="hasActionMessages()">
		<div class="welcome">
      <s:actionmessage/>
		</div>
  </s:elseif>

	<dl class="fn1-output-field">
		<dt>Employee </dt>
		<dd><a href="<s:property value='#application.url'/>employee.action?id=<s:property value='emprestr.emp_id' />" class="fn1-btn"><s:property value="emprestr.employee" /></a></dd> 
	</dl>
	<dl class="fn1-output-field">
		<dt>Restrictions </dt>
		<dd> </dd>
	</dl>
	<div class="tt-row-container">	
		<div class="tt-split-container">			
			<dl class="fn1-output-field">
				<dt><s:checkbox name="emprestr.restr_1" value="%{emprestr.restr_1}" />
				</dt>
				<dd>A - Glasses or Contacts</dd>
			</dl>
			<dl class="fn1-output-field">
				<dt><s:checkbox name="emprestr.restr_2" value="%{emprestr.restr_2}" />
				</dt>
				<dd>B - Outside Rearview Mirror</dd>
			</dl>
			<dl class="fn1-output-field">
				<dt><s:checkbox name="emprestr.restr_3" value="%{emprestr.restr_3}" />
				</dt>
				<dd>C - Daylight Driving only</dd>
			</dl>
			<dl class="fn1-output-field">
				<dt><s:checkbox name="emprestr.restr_4" value="%{emprestr.restr_4}" />
				</dt>
				<dd>D - Automatic Transmission Only</dd>
			</dl>
			<dl class="fn1-output-field">
				<dt><s:checkbox name="emprestr.restr_5" value="%{emprestr.restr_5}" />
				</dt>
				<dd>K - CDL Intrastate only</dd>
			</dl>
			<dl class="fn1-output-field">
				<dt><s:checkbox name="emprestr.restr_6" value="%{emprestr.restr_6}" />
				</dt>
				<dd>L - Vehicle Without Airbrakes</dd>
			</dl>
			<dl class="fn1-output-field">
				<dt><s:checkbox name="emprestr.restr_7" value="%{emprestr.restr_7}" />
				</dt>
				<dd>P - Class C Public Passenger</dd>
			</dl>
			<dl class="fn1-output-field">
				<dt><s:checkbox name="emprestr.restr_8" value="%{emprestr.restr_8}" />
				</dt>
				<dd>Q - Bus Only</dd>
			</dl>
		</div>
		<div class="tt-split-container">		
			<dl class="fn1-output-field">
				<dt><s:checkbox name="emprestr.restr_9" value="%{emprestr.restr_9}" />
				</dt>
				<dd>U - Power Steering</dd>
			</dl>
			<dl class="fn1-output-field">
				<dt><s:checkbox name="emprestr.restr_10" value="%{emprestr.restr_10}" />
				</dt>
				<dd>V - PP Chauffeurs Rest. To Taxi On</dd>
			</dl>
			<dl class="fn1-output-field">
				<dt><s:checkbox name="emprestr.restr_11" value="%{emprestr.restr_11}" />
				</dt>
				<dd>Z - Blind</dd>
			</dl>
			<dl class="fn1-output-field">
				<dt><s:checkbox name="emprestr.restr_12" value="%{emprestr.restr_12}" />
				</dt>
				<dd>3 - Photo Exempt</dd>
			</dl>
			<dl class="fn1-output-field">
				<dt><s:checkbox name="emprestr.restr_13" value="%{emprestr.restr_13}" />
				</dt>
				<dd>4 - BMV Restrictions</dd>
			</dl>
			<dl class="fn1-output-field">
				<dt><s:checkbox name="emprestr.restr_14" value="%{emprestr.restr_14}" />
				</dt>
				<dd>5 - Conditional</dd>
			</dl>
			<dl class="fn1-output-field">
				<dt><s:checkbox name="emprestr.restr_15" value="%{emprestr.restr_15}" />
				</dt>
				<dd>6 - Interlock Device</dd>
			</dl>
		</div>
	</div>
	<dl class="fn1-output-field">
		<dt>Enforcement:</dt>
		<dd></dd>
	</dl>
	<div class="tt-row-container">	
		<div class="tt-split-container">		
			<dl class="fn1-output-field">
				<dt><s:checkbox name="emprestr.restr_16" value="%{emprestr.restr_16}" />
				</dt>
				<dd>T - Double/Triple Trailers</dd>
			</dl>
			<dl class="fn1-output-field">
				<dt><s:checkbox name="emprestr.restr_17" value="%{emprestr.restr_17}" />
				</dt>
				<dd>P - Passanger Transport</dd>
			</dl>
			<dl class="fn1-output-field">
				<dt><s:checkbox name="emprestr.restr_18" value="%{emprestr.restr_18}" />
				</dt>
				<dd>N - Tank Vehicles</dd>
			</dl>
		</div>
		<div class="tt-split-container">		
			<dl class="fn1-output-field">
				<dt><s:checkbox name="emprestr.restr_19" value="%{emprestr.restr_19}" />
				</dt>
				<dd>H - Hazardous Materials</dd>
			</dl>
			<dl class="fn1-output-field">
				<dt><s:checkbox name="emprestr.restr_20" value="%{emprestr.restr_20}" />
				</dt>
				<dd>X - Combination of H and N</dd>
			</dl>	
			<dl class="fn1-output-field">
				<dt><s:checkbox name="emprestr.restr_21" value="%{emprestr.restr_21}" />
				</dt>
				<dd>M - Motorcycle</dd>
			</dl>
		</div>
	</div>
	<s:submit name="action" type="button" value="Save" class="fn1-btn"/>

</s:form>
<%@  include file="footer.jsp" %>


