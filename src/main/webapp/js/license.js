clicked_button_id="";
var icons = {
    header:"ui-icon-circle-plus",
    activeHeader:"ui-icon-circle-minus"
};

$(".date").datepicker({
    nextText: "Next",
    prevText:"Prev",
    buttonText: "Pick Date",
    showOn: "both",
    navigationAsDateFormat: true,
    buttonImage: "/licensepro/js/calendar.gif"
});

jQuery(function ($) {
    var launcherClick = function(e)  {
            var openMenus   = $('.menuLinks.open'),
                menu        = $(e.target).siblings('.menuLinks');
            openMenus.removeClass('open');
            setTimeout(function() { openMenus.addClass('closed'); }, 300);

            menu.removeClass('closed');
            menu.   addClass('open');
            e.stopPropagation();
        },
        documentClick = function(e) {
            var openMenus   = $('.menuLinks.open');

            openMenus.removeClass('open');
            setTimeout(function() { openMenus.addClass('closed'); }, 300);
        };
    $('.menuLauncher').click(launcherClick);
    $(document       ).click(documentClick);
});
$(document).on("click","button", function (event) {
	clicked_button_id = event.target.id;
});
function projectValidate(){
		var xx = document.getElementById("project_name");		
		if(xx && xx.value.trim() == ""){
				alert("Project Name is required");
				xx.focus();
				return false;
		}
		xx = document.getElementById("project_type_id");
		if(xx){
				var yy = xx.value;
				if(yy == "-1"){
						alert("Project type is required");
						xx.focus();
						return false;
				}
		}
		xx = document.getElementById("phase_rank_id");
		if(xx){
				var yy = xx.value;
				if(yy == "-1"){
						xx.focus();						
						alert("Phase rank is required");
						return false;
				}
		}		
		return true;
}
function doRefresh(){
		document.getElementById("action2").value="Refresh";		
		document.getElementById("form_id").submit();				
}


