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
		document.getElementById("refresh_gif").style.display = 'inline';
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
	        	document.getElementById("refresh_gif").style.display = 'none';           
                  if (response !== null ) {
	        		  if (response.rs !== null){
	             	  
                         $("#rs").val(response.rs);
                         $("#siret").val(response.siret);
                         $("#adresse").val(response.adresse);
                         $("#cp").val(response.cp);
                         $("#ville").val(response.ville);
                         $("#tel").val(response.telephone);
                         $("#email").val(response.mail);
                         $("#rib").val(response.rib);
                         $("#divcontainer").show();             
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
	            	document.getElementById("refresh_gif").style.display = 'none';
	            	document.getElementById("msgmodalnotifyclient").innerHTML="<b> Aucun client n'a été trouvé avec ce critére </b>";
	                document.getElementById("titlemodalnotifyclient").innerHTML="<span style='color: red;'>ERROR</span>";
	                $("#Modalnotifyclient").modal();   	
	            	
	            }
	        },
	        error: function (xhr) {document.getElementById("refresh_gif").style.display = 'none';
	        document.getElementById("msgmodalnotifyclient").innerHTML="<b> Erreur technique</b>";
	        document.getElementById("titlemodalnotifyclient").innerHTML="<span style='color: red;'>ERROR</span>";
	        $("#Modalnotifyclient").modal();   
	        }            
	        });
		}else {
			
			document.getElementById("refresh_gif").style.display = 'none';
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



function saveupdateclient() {
	
	
    var ItemJSON = {"rs": $("#rs").val(),"siret":$("#siret").val(),"adresse": $("#adresse").val(),"cp":$("#cp").val(),"ville":$("#ville").val(),"telephone":$("#tel").val(),"mail":$("#email").val(),"rib":$("#rib").val()};
    var myJSON = JSON.stringify(ItemJSON);
	$.ajax({
        url: "updateclient",
        type: 'POST',
        async: false,
        processData: false,
        contentType: "application/json; charset=utf-8",
        data: myJSON,
        success: function (response) {
	    document.getElementById("msgmodalnotifyclient").innerHTML="<b>"+response+ "</b>";
		document.getElementById("titlemodalnotifyclient").innerHTML=" <span style='color: green;'><i class='fas fa-check'></i> Confirmation update</span>";
		$("#Modalnotifyclient").modal();
		  setTimeout(function(){
            	  $('#Modalnotifyclient').modal('hide')
            	}, 2000);

    

	    },
        error : function (jqXHR) {
	    document.getElementById("msgmodalnotifyclient").innerHTML="<b>"+jqXHR.responseText+ "</b>";
		document.getElementById("titlemodalnotifyclient").innerHTML=" <span style='color: red;'><i class='fas fa-exclamation-circle'></i> Error update</span>";
		$("#Modalnotifyclient").modal();
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
	var ItemJSON = {"quantite": $("#quantite").val(),"montantHT":$("#prix_HT").val(),"montantTTC": $("#prixttc").val(),"totalttc":$("#totalprixttc").val(),"article":$("#article").val(),"client":$("#nomclient").val(),"taxe":$("#taxe").val(),"valtaxe":$("#valtaxe").val(),"modepaiement":$("#modepaiement").val(),"datepaiementattendue":$("#date_attendue").val()};
    var myJSON = JSON.stringify(ItemJSON);
	$.ajax({
        url: "generateinvoice",
        type: 'POST',
        async: false,
        processData: false,
        contentType: "application/json; charset=utf-8",
        data: myJSON,
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
	$("#refresh_gif").show();
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
	    $("#refresh_gif").hide();
        document.getElementById("msgok").innerHTML="<b>"+response+"</b>"
        $("#alertok").show();
        },
        error :function (jqXHR) {
	    $("#refresh_gif").hide();
        document.getElementById("msgko").innerHTML="<b>"+jqXHR.responseText+"</b>"
        $("#alertko").show();
        }
	});	
}


function gettemplatecontain(value,clientname) {
	if(value.localeCompare('Relance_Paiement') == 0) {
     $("#subject").val("Relance Paiement "+document.getElementById('file').files[0].name);
    document.getElementById("containmail").innerHTML="Bonjour "+clientname+",\nMerci de penser de régler la facture ci-jointe\nCordialement";
    
	}
	if(value.localeCompare('Envoi_Facture') == 0) {
    $("#subject").val("Nouvelle Facture "+document.getElementById('file').files[0].name);   
    document.getElementById("containmail").innerHTML="Bonjour "+clientname+",\nci-joint  la facture à régler\nCordialement";
    
	}
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
	    $('.modal-backdrop').hide();
        $('body').removeClass('modal-open');
        $('#Modalnewarticle').modal('hide');  
        $("#articlescontainer").load("articles");


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



function showupdatemodal() {
	$("#tablearticle tr").click(function() {//Add a click event to the row of the table
	    var tr = $(this);//Find tr element
	    var td = tr.find("td");//Find td element
       document.getElementById("titlemodalmodifyarticle").innerHTML="<b> <span style='color: green;'> Modificaion Article "+td[0].innerText+"</span></b>"
       $("#designationmodify").val(td[0].innerText);
       $("#famillemodify").val(td[1].innerText);
       $("#pvhtmodify").val(td[2].innerText);
       $("#pahtmodify").val(td[3].innerText);
       $("#taxemodify").val(td[4].innerText);
       $("#valtaxemodify").val(td[5].innerText);
       $("#pvttcmodify").val(td[6].innerText);
       $("#Modalmodifyarticle").modal();
   });	
}

function saveupdatearticle () {
	
	var pvht = parseFloat($("#pvhtmodify").val());
	var paht = parseFloat($("#pahtmodify").val());
	var valtaxe = parseInt($("#valtaxemodify").val().toString().substring(0,$("#valtaxemodify").val().indexOf('%')));
	var pvttc = parseFloat($("#pvttcmodify").val());
	var ItemJSON = {"designation": $("#designationmodify").val(),"famille":$("#famillemodify").val(),"pvht": pvht,"paht":paht,"taxe":$("#taxemodify").val(),"valtaxe":valtaxe,"pvttc":pvttc};
    var myJSON = JSON.stringify(ItemJSON);
	
	
	$.ajax({
        url: "updatearticle",
        type: 'POST',
        async: false,
        processData: false,
        contentType: "application/json; charset=utf-8",
        data: myJSON,
        success: function (response) {
	   $('.modal-backdrop').hide();
        $('body').removeClass('modal-open');
        $('#Modalmodifyarticle').modal('hide');  
        $("#articlescontainer").load("articles");
	    }, 
       error : function() {
	
	   }
       });
		
}

function showmodaldelete(){
	$("#tablearticle tr").click(function() {//Add a click event to the row of the table
	    var tr = $(this);//Find tr element
	    var td = tr.find("td");//Find td element
        document.getElementById("designation_article").innerHTML =td[0].innerText;
        $("#Modalconfirmdelete").modal()


});
	
}
function suppression_article(designation) {
        $.ajax({
        url: "deletearticle/"+designation,
        type: 'POST',
        async: false,
        processData: false,
        contentType : false,
        success: function (response) {
        $('.modal-backdrop').hide();
        $('body').removeClass('modal-open');
        $('#Modalconfirmdelete').modal('hide');  
        $("#articlescontainer").load("articles");
	    }, 
       error : function() {
	
	   }
       });


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


function refresh_transactions() {	
$("#refresh").show();
$.ajax({
type : 'GET',
url : 'refresh_transactions',
async: true,
processData: false,
contentType: false,
success : function(response) {
	$("#refresh").hide();
	if (response > 0) {
	            document.getElementById("msgModalnotify").innerHTML="<b>"+response+" Nouvelles transactions ont été importées avec succés</b>";
		        document.getElementById("titlemodal").innerHTML="<span style='color: green;'><i class='fas fa-file-upload'></i> Confirmation</span>";
                $("#container_transaction").load("liste_transactions_bank");
                $("#Modalnotify").modal();
                


	}
	if (response == 0) {
	            document.getElementById("msgModalnotify").innerHTML="<b> Pas de nouvelles transactions à importer</b>";
		        document.getElementById("titlemodal").innerHTML="<span style='color: green;'><i class='fas fa-file-upload'></i> Confirmation</span>"; 
                $("#Modalnotify").modal();	
 
	}			
		
	},
error : function() {
$("#refresh").hide();
console.log("****************ko");
}

});
}

function addtva(value) {

	  
		$("#table_transactions tr").click(function() {//Add a click event to the row of the table

        var tr = $(this);//Find tr element
	    var td = tr.find("td");//Find td element
        
       var formData = new FormData();
       formData.append('typeoperation', 'add');
       formData.append('amountttc', td[0].innerText);
       formData.append('settled_at', td[6].innerText);
		$.ajax({
	        url: "GestionTVA",
	        type: 'POST',
	        async: false,
	        processData: false,
	        contentType: false,
	        data: formData,
	        success: function () {
		      $("#container_transaction").load("liste_transactions_bank");
		      document.getElementById("msgModalnotify").innerHTML="<b> La TVA a été bien appliquée </b>";
              document.getElementById("titlemodal").innerHTML="<span style='color: green;'><i class='far fa-check-circle'></i>Confirmation</span>";
              $("#Modalnotify").modal();
              
             },
            error : function(){
	          document.getElementById("msgModalnotify").innerHTML="<b> Erreur application TVA</b>";
              document.getElementById("titlemodal").innerHTML="<span style='color: red;'><i class='fas fa-exclamation-circle'></i> Error</span>";
              $("#Modalnotify").modal();
             }
            });

});
}	
	
function reducetva(value) {
	  
		$("#table_transactions tr").click(function() {//Add a click event to the row of the table

        var tr = $(this);//Find tr element
	    var td = tr.find("td");//Find td element
        
       var formData = new FormData();
       formData.append('typeoperation', 'reduce');
       formData.append('amountttc', td[0].innerText);
       formData.append('settled_at', td[6].innerText);
		$.ajax({
	        url: "GestionTVA",
	        type: 'POST',
	        async: false,
	        processData: false,
	        contentType: false,
	        data: formData,
	        success: function () {
		      $("#container_transaction").load("liste_transactions_bank");
              document.getElementById("msgModalnotify").innerHTML="<b> La TVA a été enlevée </b>";
              document.getElementById("titlemodal").innerHTML="<span style='color: green;'><i class='far fa-check-circle'></i> Confirmation</span>";
              $("#Modalnotify").modal();
            
             },
            error : function(){
	          document.getElementById("msgModalnotify").innerHTML="<b> Erreur suppression TVA </b>";
              document.getElementById("titlemodal").innerHTML="<span style='color: red;'><i class='fas fa-exclamation-circle'></i> Error</span>";
              $("#Modalnotify").modal();
             }
        
            });

});
}	

function totaltva () {
         $.ajax({
	        url: "totaltva/"+$('#datedeb').val()+"/"+$('#datefin').val(),
	        type: 'GET',
	        async: false,
	        processData: false,
	        contentType: false,
	        success: function (response) {
            $("#totaltva").val(response)
	
            },
           error : function () {
	
}
});

            $.ajax({
	        url: "Getransactionsbetween/"+$('#datedeb').val()+"/"+$('#datefin').val(),
	        type: 'GET',
	        async: false,
	        processData: false,
	        contentType: false,
	        success: function (data) {
        var trHTML = '';
        $('#tbobytransaction').empty();	
        $.each(data, function (i, item) {
            trHTML += '<tr><td>' + item.label + '</td><td>'+item.reference +'</td><td>' + item.settled_at + '</td><td>' + item.side + '</td><td>'+item.operation_type+'</td><td>'+ item.amount  +'</td><td>'+item.amount_HT +'</td></tr>' ;
        });
	       $('#tbobytransaction').append(trHTML);
            },
           error : function () {
	
}
});
     
}


//*********************************************************************************JS profil 

function changeavatar(file) {
var formData = new FormData();
formData.append('avatar', file);	
	 $.ajax({
	        url: "updateavatar",
	        type: 'POST',
	        async: false,
	        processData: false,
	        contentType: false,
            data: formData,
	        success: function (response) {
            $("#containerprofil").load("profile");
       
	    },
        error : function (jqXHR) {
	    document.getElementById("msgmodalnotifyprofil").innerHTML="<b>"+jqXHR.responseText+ "</b>";
		document.getElementById("titlemodalnotifyprofil").innerHTML=" <span style='color: red;'><i class='fas fa-exclamation-circle'></i> Error update</span>";
		$("#Modalnotifyprofil").modal();
        }
		
		
		
		});
}


function updatepassword () {
	
	if ( $("#newpassword").val().localeCompare($("#repeatnewpassword").val()) == 0 ) {
		if ($("#newpassword").val().length == 0) {
		document.getElementById("msgmodalnotifyprofil").innerHTML="<b> les deux mots de passes sont vides</b>";
		document.getElementById("titlemodalnotifyprofil").innerHTML=" <span style='color: red;'><i class='fas fa-exclamation-circle'></i> Error update</span>";
		$("#Modalnotifyprofil").modal();	
		} else {
	
	 $.ajax({
	        url: "updatepassword/"+$("#login").val()+"/"+$("#newpassword").val(),
	        type: 'POST',
	        async: false,
	        processData: false,
	        contentType: false,
	        success: function (response) {
	    document.getElementById("msgmodalnotifyprofil").innerHTML="<b>"+response+ "</b>";
		document.getElementById("titlemodalnotifyprofil").innerHTML=" <span style='color: green;'><i class='fas fa-user-edit'></i> Confirmation update</span>";
		$("#Modalnotifyprofil").modal();
             },
            error : function (jqXHR) {
	    document.getElementById("msgmodalnotifyprofil").innerHTML="<b>"+jqXHR.responseText+ "</b>";
		document.getElementById("titlemodalnotifyprofil").innerHTML=" <span style='color: red;'><i class='fas fa-exclamation-circle'></i> Error update</span>";
		$("#Modalnotifyprofil").modal();
            }
	});
	
	}
}else {
	    document.getElementById("msgmodalnotifyprofil").innerHTML="<b>Les deux mots de passes ne sont pas identiques </b>";
		document.getElementById("titlemodalnotifyprofil").innerHTML=" <span style='color: red;'><i class='fas fa-exclamation-circle'></i> Error</span>";
		$("#Modalnotifyprofil").modal();
	
}
}

//********************************************************************** JS GLOBAL SETTINGS

function update_global_settings(){
	   var formData = new FormData();
       formData.append('smtphost', $("#smtphost").val());
       formData.append('smptport', $("#smptport").val());
       formData.append('smtpusername', $("#smtpusername").val());
       formData.append('smtppassword', $("#smtppassword").val());
       formData.append('companyemail', $("#companyemail").val());
       formData.append('ldapadmin', $("#ldapadmin").val());
       formData.append('ldappassword', $("#ldappassword").val());
       formData.append('ldaphost', $("#ldaphost").val());
       formData.append('ldapport', $("#ldapport").val());
	 $.ajax({
	        url: "update_general_settings",
	        type: 'POST',
	        async: false,
	        processData: false,
	        contentType: false,
            data: formData,
	        success: function (response) {
		 document.getElementById("msgmodalnotif").innerHTML="<b>"+response+"</b>";
         document.getElementById("titleModalnotify").innerHTML="<span style='color: green;'>Confirmation</span>";
         $("#Modalnotify").modal();	
		}, 
		error : function (jqXHR){
		 document.getElementById("msgmodalnotif").innerHTML="<b>"+jqXHR.responseText+"</b>";
         document.getElementById("titleModalnotify").innerHTML="<span style='color: red;'>ERROR</span>";
         $("#Modalnotify").modal();	
			
		}
	
	});
}

