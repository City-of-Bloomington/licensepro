<%@  include file="header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<s:form action="employee" id="form_id" method="post" onsubmit="return employeeValidate()">
	<s:hidden name="action2" id="action2" value="" />
	<s:if test="id == ''">
		<h1>New Employee</h1>
	</s:if>
	<s:else>
		<h1>Edit Employee <s:property value="employee.id" /></h1>
		<s:hidden name="employee.id" value="%{employee.id}" />
		<s:hidden name="employee.userid" value="%{employee.userid}" />
		<s:hidden name="employee.date" value="%{employee.date}" />
		<s:hidden name="employee.userid2" value="%{employee.userid2}" />
		<s:hidden name="employee.date2" value="%{employee.date2}" />
	</s:else>
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
  <p>* Required field <br />
		** Employee Number is needed, ask HR for if the field is empty.<br />
		*** Uncheck if the employee is terminated <br />
			<s:if test="id != ''">
				If you make any change, please hit the 'Save Changes' button
			</s:if>
			<s:else>
				You must hit 'Save' button to save data
			</s:else>
	</p>
	<div class="tt-row-container">	
		<div class="tt-split-container">		
			<dl class="fn1-output-field">
				<dt>First Name </dt>
				<dd><s:textfield name="employee.fname" value="%{employee.fname}" size="20" maxlength="70" id="fname" required="true" />* 
				</dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Last Name </dt>
				<dd><s:textfield name="employee.lname" value="%{employee.lname}" size="20" maxlength="70" id="lname" required="true" />* </dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Department </dt>
				<dd><s:select name="employee.dept_id" value="%{employee.dept_id}" requiredLabel="true" required="true" list="depts" listKey="id" listValue="name" id="dept_id" headerKey="-1" headerValue="Pick Department" onchange="getDivisions()" />* </dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Employee Num </dt>
				<dd><s:textfield name="employee.employee_num" value="%{employee.employee_num}" size="10" maxlength="10" />** </dd>
			</dl>			
			<dl class="fn1-output-field">
				<dt>DLN</dt>
				<dd><s:textfield name="employee.lic_number" value="%{employee.lic_number}" size="20" maxlength="30" id="lname" required="true" />*
					 </dd>			
			</dl>
			<dl class="fn1-output-field">
				<dt>Expire Date</dt>
				<dd><s:textfield name="employee.exp_date" value="%{employee.exp_date}" cssClass="date" required="true" size="10" />*  </dd>
			</dl>
			<dl class="fn1-output-field">	
				<dt><s:checkbox name="employee.cdl_required" value="%{employee.cdl_required}" /></dt>
				<dd>Required by City to Have CDL</dd>
			</dl>			
		</div>
		<div class="tt-split-container">
			<dl class="fn1-output-field">				
				<dt>Middle Name </dt>
				<dd><s:textfield name="employee.mname" value="%{employee.mname}" size="10" maxlength="10" /></dd>
			</dl>
			<dl class="fn1-output-field">				
				<dt>DOB </dt>
				<dd><s:textfield name="employee.dob" value="%{employee.dob}" size="10" cssClass="date" required="true" />* </dd>
			</dl>
			<dl class="fn1-output-field">				
				<dt>Division </dt>
				<dd><s:select name="employee.div_id" value="%{employee.div_id}" list="divisions" listKey="id" listValue="name" headerKey="-1" headerValue="Pick Division" id="division_id" /></dd>
			</dl>
			<dl class="fn1-output-field">				
				<dt>License Type </dt>
				<dd><s:select name="employee.type_id" value="%{employee.type_id}" required="true" list="types" listKey="id" listValue="name" headerKey="-1" headerValue="Pick Type"/>* </dd>		
			</dl>
			<dl class="fn1-output-field">				
				<dt>Issued State </dt>
				<dd> <s:select name="employee.state_id" value="%{employee.state_id}" required="true" list="states" listKey="id" listValue="name" headerKey="-1" headerValue="Pick State"/>* </dd>
			</dl>
			<dl class="fn1-output-field">
				<dt><s:checkbox name="employee.driver" value="%{employee.driver}" /></dt>
				<dd>Has Driver's License </dd>
			</dl>
			<dl class="fn1-output-field">
				<dt><s:checkbox name="employee.active" value="%{employee.active}" />***</dt>			
				<dd>Active (Still Employeed)</dd>
			</dl>						
		</div>
	</div>
	<s:if test="employee.id == ''">
		<s:submit name="action" type="button" value="Save" class="fn1-btn"/></dd>
	</s:if>
	<s:else>
		<s:submit name="action" type="button" value="Save Changes" class="fn1-btn"/>
		<a href="<s:property value='#application.url'/>restriction.action?emp_id=<s:property value='employee.id' />" class="fn1-btn"> Restrictions</a>	
	</s:else>
</s:form>

<s:if test="id == ''">
	<s:set var="employees" value="employees" />
	<s:set var="employeesTitle" value="employeesTitle" />
	<%@  include file="employees.jsp" %>
</s:if>
<%@  include file="footer.jsp" %>

<script type="text/javascript">
 $(function() {
  $('#form_id').areYouSure();
});
/**
 * populate sub features select list
 * using jquery
 */
function getDivisions(){
	var select_id = document.getElementById("division_id");
	var selected_id = $("#dept_id").val();
	//
	// we are interested in the features that have sub_features only
	//
	if(selected_id && selected_id != ""){
		$.getJSON("<s:property value='#application.url' />DivisionService?id="+selected_id,function(data){
					//
					// reomve the old ones if the user selected another item
					//
					var len = $('#division_id option').length;
					if(len > 1){
						for(var jj=len-1;jj>0;jj--){
							$('#division_id option').eq(jj).remove();
						}
					}
					$.each( data, function(key, item) {
						$('#division_id').append($('<option>', { 
							value: item.id,
							text : item.value 
						}));
					});
					// $('#division_id').css({'display':'inline'});
				}); 
	}
	else{
		var len = select_id.length;
		if(len > 1){
			for(var jj=len-1;jj>0;jj--){
				select_id.remove(jj);
			}
		}
		// select_id.style.display="none";				
	}
}

</script>
