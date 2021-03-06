function showNavBar(){
	if(myApp.getWhoAmI()!=null){
        $('#whoami').text(myApp.getWhoAmI().prenom + " " + myApp.getWhoAmI().nom);
    	$('nav').fadeIn();
		if(myApp.getWhoAmI().estAdmin){
            $('#navDashboardStudent').show();
            $('#anneeAcademique').text(myApp.getAcademicalYear());
            $('#iconWhoAmi').text('supervisor_account');
        } else {
            $('#anneeAcademique').text(myApp.getWhoAmI().anneeAcademique);
        }
	}
}

function updateNavBar(){
	$('#whoami').text(myApp.getWhoAmI().prenom + " " + myApp.getWhoAmI().nom);
}

function hideNavBar(){
    $('nav').hide();
    $('#navDashboardStudent').hide();
}

$(function() {

    $('#logout').click(function(){
		config = {

					'data': {
						'action': 'logout'
					}
				 }
        myApp.myajax(config);
        if(myApp.getWhoAmI().estAdmin) $('#iconWhoAmi').text('person');
        myApp.setWhoAmI(null);  
    	hideNavBar();
		FrontEnd.displayPage('pageLogin');
    });
    

    $('#navEntreprises').click(function(){
        $('#pageEntreprises').removeClass();
        $('#pageEntreprises').toggleClass("col-lg-12 col-md-12 col-sm-12 col-xs-12");
        $('#confirmInitiateContact').hide();
        $('#btnsContactEntreprises').show();
		FrontEnd.displayPage('pageEntreprises');
    });

    $('#navStagify').click(function(){
        if (myApp.getWhoAmI().estAdmin === false) {
            FrontEnd.displayPage('pageDashboardStudent');
        }
        else {
            FrontEnd.displayPage('pageDashboardTeacher');
        }
    });
    
    $('#navDashboardStudent').click(function(){
    	FrontEnd.displayPage('pageDashboardStudent');
    });

    $('#btnDonneesPerso').click(function(){
        FrontEnd.displayPage('pageDonneesPerso');       
    });
    
    $('#navDashboard').click(function(){
        //$('#pageEntreprises').removeClass();
        //$('#pageEntreprises').toggleClass("col-lg-12 col-md-12 col-sm-12 col-xs-12");
        //$(".new-contact-form").css('display', 'none');
		//$("#btnAddRow").css('display', 'block');
        if (myApp.getWhoAmI().estAdmin === false) {
            FrontEnd.displayPage('pageDashboardStudent');
        }
        else {
            FrontEnd.displayPage('pageDashboardTeacher');
        }
    });
});