//**************************intialisation appel WS
	var url_api_insee="";
	var url_api_checkiban="";
	var url_api_bank="";
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
		 url_api_bank = messageResource.get('api.bank', 'config');
		 
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
		var raisons_sociales=[];
		$.ajax({
	        url: "Getallclients",
	        type: 'GET',
	        async: false,
	        success: function (data) {	
	        	for (var i in data){ 
	        		emails.push(data[i].mail);
	        		raisons_sociales.push(data[i].rs);
	                }	
                if (document.getElementById("nomfromsearchclient")) {
                
	        	$("#nomfromsearchclient").autocomplete({
	               source: raisons_sociales
	             });
                }
	        	if (document.getElementById("emailfromsearchclient")) {
	        	
                $("#emailfromsearchclient").autocomplete({
		               source: emails
		             });
	        	}
	        
	        	if (document.getElementById("client_name")) {
	                $("#client_name").autocomplete({
			               source: raisons_sociales
			             });
		        	}
	        	
	         
	        
	        
	        
	        }
	        
		});
		
     });	
}

function checkfilesize(size) {
if (size>20000000) {
	 document.getElementById("msgmodalnotif").innerHTML="<b> La taille de la piece jointe > 20 MO </b>";
     document.getElementById("titleModalnotify").innerHTML="<span style='color: red;'>ERROR</span>";
     $("#Modalnotify").modal();
}
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

function showmodalcompany(){
	$("#raisonsocialemodal").val($("#raisonsociale").val());
	$("#siretmodal").val($("#siret").val());
	$("#ribmodal").val($("#rib").val());
	$("#adressemodal").val($("#adresse").val());
	$("#villemodal").val($("#ville").val());
	$("#cpmodal").val($("#cp").val());
	$("#bankenamemodal").val($("#bankname").val());
	$("#slugmodal").val($("#slug").val());
	$("#tokenmodal").val($("#token").val());
	$("#Modalmodifclient").modal();
}
function savecompanymodify(){
	$("#spinnerbutton").show();
	var formData = new FormData();
	if(! document.getElementById('raisonsocialemodal').disabled) {
	formData.append('rs', $("#raisonsocialemodal").val());
	}
	
	if(! document.getElementById('siretmodal').disabled) {
		formData.append('siret', $("#siretmodal").val());
	
		}
	
	if( ! document.getElementById('ribmodal').disabled) {
		formData.append('rib', $("#ribmodal").val());
	
		}
	
	if(!document.getElementById('adressemodal').disabled) {
		formData.append('adresse', $("#adressemodal").val());

		}
	if(!document.getElementById('villemodal').disabled) {
		formData.append('ville', $("#villemodal").val());

		}
	
	if(!document.getElementById('cpmodal').disabled) {
		formData.append('cp', $("#cpmodal").val());

		}
	
	if(!document.getElementById('bankenamemodal').disabled) {
		formData.append('bank', $("#bankenamemodal").val());

		}
	if(!document.getElementById('slugmodal').disabled) {
		formData.append('slug', $("#slugmodal").val());
	
		}
	
	if(!document.getElementById('tokenmodal').disabled) {
		formData.append('token', $("#tokenmodal").val());
		
		}
	
	$.ajax({
        url: "updatecompany",
        type: 'POST',
        async: false,
        processData: false,
        contentType: false,
        data: formData,
        success: function (response) {
        	$("#spinnerbutton").hide();
        	  document.getElementById("msgmodalnotif").innerHTML="<b>"+response+"</b>";
		      document.getElementById("titleModalnotify").innerHTML=" <span style='color: green;'><i class='far fa-check-square'></i> Confirmation</span>";
		      $("#Modalnotify").modal(); 
        },
        error: function (jqXHR) {
        	if (jqXHR.status == 501) {
        		$("#spinnerbutton").hide();
        		  document.getElementById("msgmodalnotif").innerHTML="<b>"+jqXHR.responseText+"</b>";    
		          document.getElementById("titleModalnotify").innerHTML=" <span style='color: red;'><i class='fas fa-exclamation-triangle'></i> ERREUR</span>";
				  $("#Modalnotify").modal(); 		
        	}
     
        }
        
	});
	
	
	

}


function getclientsnames() {
	$.ajax({
        url: "Getallclients",
        type: 'GET',
        async: false,
        success: function (data) {	      	
        	for (var i in data){ 
        		$("#client_name").append("<option value='"+data[i].rs+"'>"+data[i].rs+"</option>");
                }
        }
        });
	
}

function getclientemail(client_name) {
	if (client_name.localeCompare('') == 0) {
		$("#client_email").val('');
	}
	$.ajax({
        url: "Getallclients",
        type: 'GET',
        async: false,
        success: function (data) {	
        	for (var i in data){ 
        		if(data[i].rs.localeCompare(client_name) == 0) {
        		$("#client_email").val(data[i].mail);	
        		}
                }
        }
        });
	
}

//##########################################################################JS create invoice

function showinfoarticle(quantite,namearticle){
	$("#pleaseWaitDialog").modal();
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
        $("#totalprixttc").val(response.pvttc*quantite);
    
        
       
        if (response.pvttc*quantite >0) {
        document.getElementById("button_generate").disabled=false;
        }
        },
        error :function () {
 
        }
	});
}

function Generateinvoice(){
	$("#loader").show();
	var formData = new FormData();
	formData.append('quantite', $("#quantite").val());
	formData.append('pvht', $("#prix_HT").val());
	formData.append('pvttc', $("#prixttc").val());
	formData.append('totalttc', $("#totalprixttc").val());
	formData.append('namearticle', $("#article").val());
	formData.append('nomclient', $("#nomclient").val());
	formData.append('taxe', $("#taxe").val());
	formData.append('valtaxe', $("#valtaxe").val());
	$.ajax({
        url: "generateinvoice",
        type: 'POST',
        async: false,
        processData: false,
        contentType: false,
        data: formData,
        success: function (response) {
        	$("#loader").hide();
        	document.getElementById("msgModalnotify").innerHTML="<b>"+response+"</b>";
            document.getElementById("titlemodal").innerHTML="<span style='color: green;'><i class='fas fa-check-circle'></i> Confirmation</span>";
            $("#Modalnotify").modal();
            setTimeout(function(){
            	  $('#Modalnotify').modal('hide')
            	}, 2000);
        },
        error : function (jqXHR) {
        	$("#loader").hide();
        	document.getElementById("msgModalnotify").innerHTML="<b> "+jqXHR.responseText+"</b>";
            document.getElementById("titlemodal").innerHTML="<span style='color: red;'><i class='fas fa-exclamation-triangle'></i> Error </span>";
            $("#Modalnotify").modal();	
        }
        
	}); 
}
//****************************************************** JS send mail 
function sendmail(mailto,subject,contain){
	var formData = new FormData();
	formData.append('mailto', mailto);
	formData.append('subject', subject);
	formData.append('contain', contain);
	formData.append('attached_file', $('input[type=file]')[0].files[0]);
	formData.append('attached_file_name', document.getElementById('file').files[0].name);
	$.ajax({
        url: "sendmail",
        type: 'POST',
        async: false,
        processData: false,
        contentType: false,
        data: formData,
        success: function (response) {
        document.getElementById("msgok").innerHTML="<b>"+response+"</b>"
        $("#alertok").show();
        },
        error :function (jqXHR) {
        document.getElementById("msgko").innerHTML="<b>"+jqXHR.responseText+"</b>"
        $("#alertko").show();
        }
	});	
}


// ************************************************************ JS ARTICLES


function setttcfield(){
	var input = document.querySelector('#valtaxe');
	input.addEventListener('keyup', function (e) {
		var ttc = parseInt($("#pvht").val())+parseInt(($("#pvht").val()*($("#valtaxe").val()/100)));
		$("#pvttc").val(ttc);
	});
}
function createnewarticle() {
	var formData = new FormData();
	if ($("#designation").val().length >0 && $("#famille").val().length > 0 && $("#pvht").val().length >0  
			&& $("#paht").val().length >0 && $("#taxe").val().length >0 && $("#valtaxe").val().length>0 && $("#pvttc").val().length >0 ) {
    $("#loader").show();
	formData.append('designation', $("#designation").val());
	formData.append('famille', $("#famille").val());
	formData.append('pvht', $("#pvht").val());
	formData.append('paht', $("#paht").val());
	formData.append('taxe', $("#taxe").val());
	formData.append('valtaxe', $("#valtaxe").val());
	formData.append('pvttc', $("#pvttc").val());
	
	$.ajax({
        url: "createnewarticle",
        type: 'POST',
        async: true,
        processData: false,
        contentType: false,
        data: formData,
        success: function (response) {
        	 $("#loader").hide();
        		document.getElementById("msgModalnotify").innerHTML="<b> Le nouveau article a été bien créé </b>";
                document.getElementById("titlemodal").innerHTML="<span style='color: green;'><i class='fas fa-check-circle'></i> Confirmation</span>";
                $("#Modalnotify").modal();
                setTimeout(function(){
                	  $('#Modalnotify').modal('hide')
                	}, 4000);
        },
        error :function () {
        	 $("#loader").hide();
        	 document.getElementById("msgModalnotify").innerHTML="<b> Erreur lors de la création du nouveau article </b>";
             document.getElementById("titlemodal").innerHTML="<span style='color: red;'><i class='fas fa-exclamation'></i> Error</span>";
             $("#Modalnotify").modal(); 
        }
	});	
	
	
	
	
	
	}
	else {
		//$("#msgko").val("vous devez saisir tous les champs");
	}

	
}


// ***************************************************************** JS paiement 

function validate_paiement(){
	$("#tablepaiement tr").click(function() {//Add a click event to the row of the table
	    var tr = $(this);//Find tr element
	    var td = tr.find("td");//Find td element
	    
	    
	    $.ajax({
	        url: "validatepaiement/"+td[0].innerText,
	        type: 'POST',
	        async: true,
	        processData: false,
	        contentType: false,
	        success: function (response) {
	        		document.getElementById("msgModalnotify").innerHTML="<b> Le Paiement de la facture "+td[0].innerText+" a été bien validé</b>";
	                document.getElementById("titlemodal").innerHTML="<span style='color: green;'><i class='fas fa-check-circle'></i> Confirmation</span>";
	                $("#Modalnotify").modal();
	                setTimeout(function(){
	                	  $('#Modalnotify').modal('hide')
	                	}, 4000);
	         
	        },
	        error :function () {
	        	 document.getElementById("msgModalnotify").innerHTML="<b> Le Paiement de la facture "+td[0].innerText+"  n'a pas pu être validé </b>";
	             document.getElementById("titlemodal").innerHTML="<span style='color: red;'><i class='fas fa-exclamation'></i> Error</span>";
	             $("#Modalnotify").modal(); 
	        }
		});	
	    
	    
	    
	    
	  });
}


function relance_paiement (){
	document.getElementById("spinnerbutton").style.display = 'inline';
	$("#tablepaiement tr").click(function() {//Add a click event to the row of the table
	    var tr = $(this);//Find tr element
	    var td = tr.find("td");
	    $.ajax({
	        url: "relance_paiement/"+td[0].innerText+"/"+td[1].innerText,
	        type: 'POST',
	        async: true,
	        processData: false,
	        contentType: false,
	        success: function (response) {
	        	$("#spinnerbutton").hide();
	        		document.getElementById("msgModalnotify").innerHTML="<b> Le client a été bien relancé </b>";
	                document.getElementById("titlemodal").innerHTML="<span style='color: green;'><i class='fas fa-check-circle'></i> Confirmation</span>";
	                $("#Modalnotify").modal();
	                setTimeout(function(){
	                	  $('#Modalnotify').modal('hide')
	                	}, 4000);
	         
	        },
	        error :function () {
	 
	        	 document.getElementById("msgModalnotify").innerHTML="<b> erreur lors de l'appel du controller relance_paiement</b>";
	             document.getElementById("titlemodal").innerHTML="<span style='color: red;'><i class='fas fa-exclamation'></i> Error</span>";
	             $("#Modalnotify").modal(); 
	        }
		});	
	   	    
	
	});	
	
}
//*************************************************************charts

function drawcharts() {
	$.ajax({
		type : 'GET',
		url : 'liste_prestations',
		sync: true,
	     processData: false,
	     contentType: false,
		success : function(result) {
			google.charts.load('current', {
				'packages' : [ 'corechart' ]
			});
			google.charts.setOnLoadCallback(function() {
				drawChartca(result);
			
			});
		}
		
	});
	}
function drawChartca(result) {
	var data = new google.visualization.DataTable();
	data.addColumn('string', 'date');
	data.addColumn('number', 'totalttc');
	var dataArray = [];
	$.each(result, function(i,obj) {
		if (obj.date.localeCompare('') !== 0) {	
		var datesub=obj.date.toString().substring(0,10);
		var somme=0;
		$.each(result, function(i,obj) {
		 var date=obj.date.toString().substring(0,10);
		 if (date.localeCompare(datesub) == 0) {
			somme=somme+obj.totalttc; 
			obj.date="";
		 }
		});
		data.addRow([datesub,somme]);	
		}
	});
	
	
	
	 
	var combochart_options = {
			title : 'Evolution CA ',
			vAxis: {title: 'Chiffre affaire en euro'},
	        hAxis: {title: 'Date facture'},
	        seriesType: 'bars',
	        series: {5: {type: 'line'} }};
	       
		var combochart = new google.visualization.ComboChart(document.getElementById('combodiv'));
		combochart.draw(data, combochart_options);

		
}
//**********************************************JS bank
var slug="zohratec-7289";
var iban= "FR7616958000018388151027285";
var token="9843cd2bbe87db673e74817b3f89960f"
function getinfofrombank(iban,slug,token){
	var settings = {
			  "url": "https://thirdparty.qonto.com/v2/transactions?iban=FR7616958000018388151027285",
			  "method": "GET",
			  "timeout": 0,
			  "headers": {
			    "Access-Control-Allow-Origin": "*",
			    "Authorization": "zohratec-7289:9843cd2bbe87db673e74817b3f89960f"
			  },
			};

			$.ajax(settings).done(function (response) {
			  console.log(response);
			});
}