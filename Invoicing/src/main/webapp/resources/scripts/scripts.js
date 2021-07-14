//**************************intialisation appel WS
	var url_api_insee="";
	var url_api_checkiban="";
	messageResource.init({
		  // path to directory containing config.properties
		  filePath : 'resources/config/'
		});

		// load config.properties file
		messageResource.load('config', function(){ 
		  // load file callback 

		  // get value corresponding  to a key from config.properties  
	
		 url_api_insee = messageResource.get('api.insee', 'config');
		 url_api_checkiban = messageResource.get('api.checkiban', 'config');
		});
//************************************************JS sharded tools 
function checkEmail(email) {
    var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
}

function checkiban(iban) {
	var res=false;
	$.ajax({
		headers : {
			 "Accept" : "application/json; charset=utf-8"
		},
        url: url_api_checkiban+'/'+iban,
        type: 'GET',
        async: false,
        success: function (response) {
        	if (response.valid) {
        	res=true;
        	}else {
        	res=false	
        	}     
        }
	});
	return res;
}


function changelabelclientname(Label){
	
	document.getElementById("labelnomclient").innerHTML="<b>"+Label+"</b>";
}


function handleValuesiret() {
	if ($("#siretnvviamodal").val().length === 14) {
		$.ajax({
			headers : {
				 "Accept" : "application/json; charset=utf-8",
			    "Authorization": "Bearer e91e3e52-d363-333a-b010-5778ea3ea9f5"
			},
	        url: url_api_insee+'/'+$("#siretnvviamodal").val(),
	        type: 'GET',
	        async: false,
	        success: function (response) { 
	            document.getElementById('nomclientnvviamodal').value=response.etablissement.uniteLegale.denominationUniteLegale;
	            document.getElementById('adressenvviamodal').value=response.etablissement.adresseEtablissement.numeroVoieEtablissement+" "+response.etablissement.adresseEtablissement.typeVoieEtablissement+" "+response.etablissement.adresseEtablissement.libelleVoieEtablissement
	            document.getElementById('villenvviamodal').value=response.etablissement.adresseEtablissement.libelleCommuneEtablissement;
	            document.getElementById('codepostalenvviamodal').value=response.etablissement.adresseEtablissement.codePostalEtablissement;
	        },
	        error: function (jqXHR) {
	       if (jqXHR.status == 404) {
	        document.getElementById("msgmodalnotifyclient").innerHTML="<b>L'entreprise n'existe pas </b>";
		    document.getElementById("titlemodalnotifyclient").innerHTML=" <span style='color: red;'><i class='fas fa-exclamation-triangle'></i> ERREUR</span>";
		    $("#Modalnotifyclient").modal();
	        }
	        
	        }
			
		});
	}
	
}


function autocomplete() {
	
	$(function() {
		var emails=[];
		var noms=[];
		$.ajax({
	        url: "Getallnamesclients",
	        type: 'GET',
	        async: false,
	        success: function (data) {	
	        	for (var i in data){ 
	        		emails.push(data[i].email);
	        		noms.push(data[i].nom);
	                }	
                $("#nomfromsearchclient").autocomplete({
	               source: noms
	             });
	        	$("#emailfromsearchclient").autocomplete({
		               source: emails
		             });
	        }
	        
		});
		
     });
	
	
	
}



//*********************************************** JS clients

function Getclient() {
		if ($('#nomfromsearchclient').val().length === 0 &&  $('#emailfromsearchclient').val().length === 0 ) {
			    document.getElementById("msgmodalnotifyclient").innerHTML="<b> Merci de saisir le nom ou l'email</b>";
		        document.getElementById("titlemodalnotifyclient").innerHTML="<span style='color: red;'>ERROR</span>";
		        $("#Modalnotifyclient").modal(); 
		}
		else {
		if (checkEmail($('#emailfromsearchclient').val()) || $('#nomfromsearchclient').val().length > 0) {
		$("#spinnerbuttonsearchclient").show();
		var formData = new FormData();
		formData.append('nom', $('#nomfromsearchclient').val());
		formData.append('email', $('#emailfromsearchclient').val());
		$.ajax({
	        url: "searchclient",
	        type: 'POST',
	        async: false,
	        processData: false,
	        contentType: false,
	        data: formData,
	        success: function (response) {
	        	$("#spinnerbuttonsearchclient").hide();
	        	document.getElementById("bodytablemodalgetclient").innerHTML = "";
	            if (response !== '' ) {
	        		  if (response.rs !== null){
	             	   $("#bodytablemodalgetclient").append('<tr>' +
	  	        		  '<td><b>'+response.rs+'</b></td>'+
	  	        		  '<td></td>'+
	  	        		  '<td><b>'+response.adresse+'</b></td>'+
	  	        		  '<td><b>'+response.cp+'</b></td>'+
	  	        		  '<td><b>'+response.ville+'</b></td>'+
	  	        		  '<td><b>'+response.telephone+'</b></td>'+
	  	        		  '<td><b>'+response.mail+'</b></td>'+
	  	        		  '<td><b>'+response.siret+'</b></td>'+
	  	        		  '</tr>')              
	        		  }
	        		  if (response.nom !== null){
		             	   $("#bodytablemodalgetclient").append('<tr>' +
		  	        		  '<td><b></b></td>'+
		  	        		  '<td>'+response.nom+'</td>'+
		  	        		  '<td><b>'+response.adresse+'</b></td>'+
		  	        		  '<td><b>'+response.cp+'</b></td>'+
		  	        		  '<td><b>'+response.ville+'</b></td>'+
		  	        		  '<td><b>'+response.telephone+'</b></td>'+
		  	        		  '<td><b>'+response.mail+'</b></td>'+
		  	        		  '<td><b>'+response.siret+'</b></td>'+
		  	        		  '</tr>')              
		        		  }
	        		  
	              $('#modalgetclient').modal();         
	            }
	            else {
	            	$("#spinnerbuttonsearchclient").hide();
	            	document.getElementById("msgmodalnotifyclient").innerHTML="<b> Aucun client n'a été trouvé avec ce critére </b>";
	                document.getElementById("titlemodalnotifyclient").innerHTML="<span style='color: red;'>ERROR</span>";
	                $("#Modalnotifyclient").modal();   	
	            	
	            }
	        },
	        error: function (xhr) {$("#spinnerbuttonsearchclient").hide(); 
	        document.getElementById("msgmodalnotifyclient").innerHTML="<b> Erreur technique</b>";
	        document.getElementById("titlemodalnotifyclient").innerHTML="<span style='color: red;'>ERROR</span>";
	        $("#Modalnotifyclient").modal();   
	        }            
	        });
		}else {
			document.getElementById("msgmodalnotifyclient").innerHTML="<b> L'adresse mail n'est pas valide </b>";
	        document.getElementById("titlemodalnotifyclient").innerHTML="<span style='color: red;'>ERROR</span>";
	        $("#Modalnotifyclient").modal();  
		}
		}	
	}

function createnewclient(){
	var nomclientm = document.getElementById('nomclientnvviamodal').value;
	var adressem = document.getElementById('adressenvviamodal').value;
	var codepostalem = document.getElementById('codepostalenvviamodal').value;
	var villem = document.getElementById('villenvviamodal').value;
	var telm = document.getElementById('telnvviamodal').value;
	var emailm = document.getElementById('emailnvviamodal').value;
	var siretm = document.getElementById('siretnvviamodal').value;
	var ribm = document.getElementById('ribnvviamodal').value;
	//client professionnel
	
	if (document.getElementById('professionnel').checked  && nomclientm.length > 0 && adressem.length > 0 && siretm.length >0) {
		if (emailm.length >0  &&  telm.length >0 && ribm.length >0) {
			if (checkiban(ribm)) {
			$("#spinnerbuttoncreatenewclient").show();
			var ItemJSON = {"rs": nomclientm,"adresse":adressem,"cp": codepostalem,"ville":villem,"telephone":telm,"mail":emailm,"siret":siretm,"rib":ribm};
		    var myJSON = JSON.stringify(ItemJSON);	 
		    $.ajax({
			      type: "POST",
			      contentType : 'application/json; charset=utf-8',
			      url: "createclient",
			      data: myJSON,
			      success :function(response) {
			    	 $("#spinnerbuttoncreatenewclient").hide();
			    	 $("#modalnvclient").hide();
			    	 document.getElementById("msgmodalnotifyclient").innerHTML="<b> Le nouveau client "+nomclientm+" a été rajouté avec succés</b>";
				      document.getElementById("titlemodalnotifyclient").innerHTML=" <span style='color: green;'><i class='far fa-check-square'></i> Confirmation</span>";
				      $("#Modalnotifyclient").modal(); 
			      },
			    	 error : function (jqXHR) {
			    		  if (jqXHR.status == 550 || jqXHR.status == 551) {
					    	  $("#spinnerbuttoncreatenewclient").hide();
					    	  $("#modalnvclient").hide();
					    	  document.getElementById("msgmodalnotifyclient").innerHTML="<b>"+jqXHR.status+": "+jqXHR.responseText+"</b>";    
					    	  document.getElementById("titlemodalnotifyclient").innerHTML=" <span style='color: red;'><i class='fas fa-exclamation-triangle'></i> ERREUR</span>";
							  $("#Modalnotifyclient").modal();   		  
					    	  }else{
					    	  $("#spinnerbuttoncreatenewclient").hide();
					    	  document.getElementById("msgmodalnotifyclient").innerHTML="<b> Erreur Technique lors de l'appel du controlleur</b>";    
						      document.getElementById("titlemodalnotifyclient").innerHTML=" <span style='color: red;'><i class='fas fa-exclamation-triangle'></i> ERREUR</span>";
						      $("#Modalnotifyclient").modal();    
					    	 }
					    	 } 
			     
			  });	
			
		
			}else {
				  document.getElementById("msgmodalnotifyclient").innerHTML="<b>Le rib que vous avez saisi est invalide : "+ribm+"</b>";  
				  document.getElementById("titlemodalnotifyclient").innerHTML=" <span style='color: red;'><i class='fas fa-exclamation-triangle'></i> ERREUR</span>";
				  $("#Modalnotifyclient").modal();   		  
		    	  } 
			
			
			
			} else {
			  document.getElementById("msgmodalnotifyclient").innerHTML="<b>champs manquants ,email /tel/rib </b>";
			  document.getElementById("titlemodalnotifyclient").innerHTML=" <span style='color: red;'><i class='fas fa-exclamation-triangle'></i> ERREUR</span>";
			  $("#Modalnotifyclient").modal();   
		    }
		
		
		
	
	// client particulier
}else {
		if (nomclientm.length >0 && emailm.length >0 && adressem.length > 0 && telm.length >0 && ribm.length >0) {
			if (checkiban(ribm)) {
			$("#spinnerbuttoncreatenewclient").show();
			var ItemJSON = {"nom": nomclientm,"email":emailm,"adresse": adressem,"tel":telm,"rib":ribm};
			var myJSON = JSON.stringify(ItemJSON);	
			$.ajax({
			      type: "POST",
			      contentType : 'application/json; charset=utf-8',
			      url: "createclient",
			      data: myJSON,
			      success :function(response) {
			          $("#spinnerbuttoncreatenewclient").hide();
			    	  $("#modalnvclient").hide();
			      	  document.getElementById("msgmodalnotifyclient").innerHTML="<b>"+response+"</b>";
				      document.getElementById("titlemodalnotifyclient").innerHTML=" <span style='color: green;'><i class='far fa-check-square'></i> Confirmation</span>";
				      $("#Modalnotifyclient").modal(); 
			      },
			      error:function(jqXHR) {   	 
			    	  if (jqXHR.status == 550 || jqXHR.status == 551) {
			    	  $("#spinnerbuttoncreatenewclient").hide();
			    	  $("#modalnvclient").hide();
			    	  document.getElementById("msgmodalnotifyclient").innerHTML="<b>"+jqXHR.status+": "+jqXHR.responseText+"</b>";    
			    	  document.getElementById("titlemodalnotifyclient").innerHTML=" <span style='color: red;'><i class='fas fa-exclamation-triangle'></i> ERREUR</span>";
					  $("#Modalnotifyclient").modal();   		  
			    	  }else{
			    		  $("#spinnerbuttoncreatenewclient").hide();
				    	  document.getElementById("msgmodalnotifyclient").innerHTML="<b> Erreur Technique lors de l'appel du controlleur</b>";    
					      document.getElementById("titlemodalnotifyclient").innerHTML=" <span style='color: red;'><i class='fas fa-exclamation-triangle'></i> ERREUR</span>";
					      $("#Modalnotifyclient").modal();      
			    	  }
			    	  } 
			    	  
			     
			  });	
			
					  
			}else {
				
				  document.getElementById("msgmodalnotifyclient").innerHTML="<b>Le rib que vous avez saisi est invalide : "+ribm+"</b>";  
				  document.getElementById("titlemodalnotifyclient").innerHTML=" <span style='color: red;'><i class='fas fa-exclamation-triangle'></i> ERREUR</span>";
				  $("#Modalnotifyclient").modal();   
				
			}
			
		}else {
			  document.getElementById("msgmodalnotifyclient").innerHTML="<b>champs manquants</b>";
			  document.getElementById("titlemodalnotifyclient").innerHTML=" <span style='color: red;'><i class='fas fa-exclamation-triangle'></i> ERREUR</span>";
			  $("#Modalnotifyclient").modal();   
			
		}
		
	}
   
}
//##########################################################################JS create invoice

function showinfoarticle(namearticle){
	var formData = new FormData();
	formData.append('designation', namearticle);
	$.ajax({
        url: "GetArticlebyname",
        type: 'POST',
        async: false,
        processData: false,
        contentType: false,
        data: formData,
        success: function (response) {
        $("#famille").val(response.famille);
        $("#prix_HT").val(response.pvht);
        $("#taxe").val(response.taxe);
        $("#valtaxe").val(response.valtaxe);
        $("#prixttc").val(response.pvttc);
        },
        error :function () {
 
        }
	});
}