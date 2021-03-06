var myApp=(function() {
	
	var varWhoAmI= null;

    myajax = function(config) {
        $.extend(config, {
            type: 'POST',
            url: '/',
            statusCode: {
                422: err422,
				401: err401,
				//409: err409,
                500: err500
            }
        });
		
		return $.ajax(config);
    }
    
    function getWhoAmI(){
    	return varWhoAmI;
    }
    
    function setWhoAmI(wai){
    	varWhoAmI = wai;
    }
    
    
    function whoAmI(){
		var currentPage;
		if(FrontEnd.currentPage() === null)
			currentPage = null;
		else
			currentPage = FrontEnd.currentPage().attr('id');
		config = {
			'data': {
				'action': 'whoami'
			},
			'success': function(reponse){
				varWhoAmI=reponse;
				if(varWhoAmI!==null){
					if(currentPage===null||currentPage==="pageLogin"||currentPage==="pageSignup"){
					    if (varWhoAmI.estAdmin === false) {
					        FrontEnd.displayPage('pageDashboardStudent');
					    }
					    else {
					        FrontEnd.displayPage('pageDashboardTeacher');
					    }
					}
					else
						FrontEnd.displayPage(currentPage);
				}else{
					if(currentPage==="pageSignup")
						FrontEnd.displayPage("pageSignup");
					else
						FrontEnd.displayPage("pageLogin");
				}
			}
		}
			
		return myajax(config);
	}
    
    function err500(message) {
       	var reponse = JSON.parse(message.responseText);
		FrontEnd.displayPage('pageFatalError');
        $('#err500').text(reponse.message);
    }
    
    function err422(message) {
    	var reponse = JSON.parse(message.responseText);
    	setAndShowNotification(reponse.message, $('#buttonAlert'));
    }
    
    function err401(message) {
    	var reponse = JSON.parse(message.responseText);
    
    	if(varWhoAmI==null||varWhoAmI==undefined)
    		FrontEnd.displayPage('pageLogin');
    	else{
		    if (varWhoAmI.estAdmin === false) {
		        FrontEnd.displayPage('pageDashboardStudent');
		    }
		    else {
		        FrontEnd.displayPage('pageDashboardTeacher');
		    }
    	}
    	setAndShowNotification(reponse.message, $('#buttonAlert'));
	}
    
    function form2Json(racine){
    	var o={};
    	
    	racine.find('input[type=text],input[type=date],input[type=password],textarea').each(function(i, el) {
    		el=$(el);
    		o[el.attr('name')] = (el.val()==""?null:el.val());
    	});
    	
    	racine.find('input[type=number]').each(function(i, el) {
    		el=$(el);
    		o[el.attr('name')] = parseFloat(el.val());
    	});
    	
    	racine.find('select[multiple]').each(function(i, el) {
    		el=$(el);
    		o[el.attr('name')] = (el.val()===null?[]:el.val());
    	});
    	
    	racine.find('select:not([multiple])').each(function(i, el) {
    		el=$(el);
    		o[el.attr('name')] = (el.val()==null?"":el.val());
    	});
    	
    	racine.find('input[type=checkbox]').each(function(i, el) {
    		el=$(el);
    		o[el.attr('name')] = el.prop('checked');
    	});
    	
    	racine.find('input[type=radio]').each(function(i, el) {
    		el=$(el);
    		o[el.attr('name')] = null;
    	});
    	
    	/*racine.find('input[type=radio]:checked').each(function(i, el) {
    		el=$(el);
    		o[el.attr('name')] = el.val();
    	});*/
    	racine.find("input[type='radio']").each(function(){
    		if($(this).is(":checked"))
        		o[el.attr('name')] = el.val();
		});
    	
    	return JSON.stringify(o);
    }
    
    function json2Form(racine, json){
    	for (var key in json){
    		var value = json[key];
    		var el = racine.find('[name="' + key + '"]');
    		
    		if (el.is('input[type=number],input[type=text],input[type=date],input[type=password],textarea')){
    			el.val(value);
    		} else if (el.is('select[multiple]')) {
    			el.val(value.length === 0 ? null : value);
    		} else if (el.is('select:not([multiple])')) {
    			el.val(value === null ? "" : value);
    		} else if (el.is('input[type=checkbox]')) {
    			el.prop('checked', value);
    		} else if (el.is('input[type=radio]')) {
    			if (value === null) {
    				el.prop('checked', false);
    			} else {
    				el.filter('[value="' + value + '"]').prop('checked', true);
    			}
    		}
    	}
	}

	function getAcademicalYear(){
		var date = new Date();
		var year = date.getFullYear();
		if(date.getMonth() >= 8) {
			return year + "-" + (year + 1);
		}
		return (year - 1) + "-" + year;
	}
    
    return {
    	err500:err500,
		err401:err401,
    	err422:err422,
		myajax:myajax,
		whoAmI:whoAmI,
		getWhoAmI:getWhoAmI,
		setWhoAmI:setWhoAmI,
		json2Form:json2Form,
		form2Json:form2Json,
		getAcademicalYear:getAcademicalYear
	}
})();
