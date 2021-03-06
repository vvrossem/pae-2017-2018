var dtUtilisateurs;
var dtResponsables;
var isInit;
var rowSelectorEntreprise;
var rowSelectorResponsable;
var rowSelectorEtudiant;


FrontEnd.onInitPage('pageDashboardTeacher', function() {
	dtUtilisateurs =  initialiserDataTablesUtilisateurs();
	dtResponsables =  initialiserDataTablesResponsables(); 
	
	FrontEnd.onDisplayPage('pageDashboardTeacher', function() {
		$('body').removeClass().toggleClass('theme-teal');
		showNavBar();
		remplirUtilisateurs();
		remplirResponsables();
	    google.charts.load('current', {'packages':['corechart']});
		google.charts.setOnLoadCallback(drawChartTeacher);
		
		//Affiche la page active dans la navbar
		if(!($('#navDashboard a').hasClass("actif"))){
			$('#navDashboard a').toggleClass('actif');
			$('#navEntreprises a').removeClass('actif');
			$('#navDashboardStudent a').removeClass('actif');
		}
	});
	
	$('#dtUtilisateurs tbody').on( 'click', 'tr', function () {
		  var rowData = dtUtilisateurs.row( this ).data();
		  afficherButton(rowData);	 
	});
	
	$('#dtResponsables tbody').on( 'click', 'tr', function () {
		  var rowData = dtResponsables.row( this ).data();
		  afficherButton(rowData);	 
	});
	
	$('#tabsUsersEtResponsables').on( 'click', 'a', function () {
		$('#btnsUsersEtResponsables').html('');
		dtUtilisateurs.rows().deselect();
		dtResponsables.rows().deselect();

	});
	
	FrontEnd.onLeavePage('pageDashboardTeacher', function() {
		$('a[href="#utilisateurs"]').click();
		$('#notification').html('');
		$('#btnsUsersEtResponsables').html('');
	});

	function drawChartTeacher() {
		
		var stats;
		
		config = {
			'data': {
				'action': 'fillChartTeacher'
			},
			'success': function (reponse) {
				stats =reponse;
				
		        var data = google.visualization.arrayToDataTable([
		          ['Task', 'Hours per Day'],
		          ['Aucun Contact', stats.aucuncontact],
		          ['Contacts Refusés', stats.contactrefuses],
		          ['Contacts Initiés', stats.contactinities],
		          ['Contact Accepté', stats.contactacceptes],
		          ['Stage en ordre', stats.contactstageenordre]
		        ]);

		        var options = {
		        		chartArea:{width:'100%',height:'70%'},
		        		legend:{position:'bottom'}
		        };

		        var chart = new google.visualization.PieChart(document.getElementById('piechartTeacher'));

		        chart.draw(data, options);
			}
		}
		myApp.myajax(config);	
    }	
	function initialiserDataTablesUtilisateurs(){
		return $('#dtUtilisateurs').DataTable({
			
			bAutoWidth : false,
			language : {
				"info": "_START_ à _END_ données sur _TOTAL_ affichées",
	    		"infoEmpty": "0 données",
	    		"infoFiltered":   "(sur _MAX_ données)",
	    		
				"search" : "Rechercher : ",
				"zeroRecords": "Aucun résultat",
				
				"emptyTable": "Pas de données disponibles dans la table",
				"lengthMenu": "Montrer _MENU_ entrées",
				"paginate": {
					"first":      "Premier",
					"last":       "Dernier",
					"next":       "Suivant",
					"previous":   "Précédent"
				}
			},
			select: {
				style: 'single',
			},
			
			columns : [ 
				{"data" : getNom},
				{"data" : getPrenom},
				{"data" : getEmail},
				{"data" : getTelephone},
				{"data" : getEtatPlusAvance}
			]
		});
	}
	
	function remplirUtilisateurs() {
		myApp.myajax({
			data : {
				'action' : 'visualiserStudCurYear',
			},
			success : function(data) {
				dtUtilisateurs.clear();
				dtUtilisateurs.rows.add(data).draw();
			}
		});
	}
	
	function initialiserDataTablesResponsables(){
		return $('#dtResponsables').DataTable({
			
			bAutoWidth : false,
			language : {
				"info": "_START_ à _END_ données sur _TOTAL_ affichées",
	    		"infoEmpty": "0 données",
	    		"infoFiltered":   "(sur _MAX_ données)",
	    		
				"search" : "Rechercher : ",
				"zeroRecords": "Aucun résultat",
				
				"emptyTable": "Pas de données disponibles dans la table",
				"lengthMenu": "Montrer _MENU_ entrées",
				"paginate": {
					"first":      "Premier",
					"last":       "Dernier",
					"next":       "Suivant",
					"previous":   "Précédent"
				}
			},
			select: {
				style: 'single',
			},
			
			columns : [ 
				{"data" : getNom},
				{"data" : getPrenom},
				{"data" : getEmail},
				{"data" : getTelephone},
				{"data" : getEntreprise}
			]
		});
	}
	function remplirResponsables(){
		myApp.myajax({
			data : {
				'action' : 'visualiserResponsables',
			},
			success : function(data) {
				dtResponsables.clear();
				dtResponsables.rows.add(data).draw();
			}
		});
	}
	
	function afficherButton(data){
		if (data.hasOwnProperty('idUtilisateur')) {
			var btn = $('<button id="btnToStudentDashboard" class="btn btn-lg bg-blue-grey waves-effect" type="button">Visualiser le tableau de bord</button>')
				.on( 'click', data, toStudentDashboard) ;
		} else {
			var btn = $('<button id="btnToEntreprise" class="btn btn-lg bg-blue-grey waves-effect" type="button">Visualiser l\'entreprise</button>')
				.on( 'click', data, toEntreprise) ;
		}
		 $('#btnsUsersEtResponsables').html(btn);
	}

	function toStudentDashboard(event){
		// charge la page Tableau de bord de l'étudiant avec l'étudiant sélectionné
		rowSelectorEtudiant = event.data.idUtilisateur;
		FrontEnd.displayPage('pageDashboardStudent');
	}

	function toEntreprise(event){
		// charge la page Entreprise avec l'idEntreprise de la personne de contact sélectionnée
		rowSelectorEntreprise = "#"+event.data.entreprise;
		rowSelectorResponsable = "#"+event.data.idPersonneContact;
		FrontEnd.displayPage('pageEntreprises');
	}

	function getNom(data){
		if (data.nom !== null) {
			return data.nom;
		}
		return "-";
	}

	function getPrenom(data){
		if (data.prenom !== null) {
			return data.prenom;
		}
		return "-";	}

	function getTelephone(data){
		if (data.tel !== null) {
			return data.tel;
		}
		return "-";	}

	function getEmail(data){
		if (data.email !== null) {
			return data.email;
		}
		return "-";	}
	
	function getEntreprise(data){
		if (data.entrepriseDto.denomination !== null) {
			return data.entrepriseDto.denomination;
		}
		return "-";
	}
	
	function getEtatPlusAvance(data){
		switch(data.etatPlusAvance){
		case 0:
			return "Aucun contact";
		case 1:
			return "Refusé";
		case 2:
			return "Initié";
		case 3:
			return "Accepté";
		case 4:
			return "Stage en ordre";
		default:
			return "";
		}
	}
});