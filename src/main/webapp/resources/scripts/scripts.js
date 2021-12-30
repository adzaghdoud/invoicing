//**************************intialisation appel WS
	var url_api_insee="";
	var url_api_checkiban="";
	var url_api_bank="";
	var url_api_insee_unite_legal="";
	var token_api_insee="";
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
	     url_api_insee_unite_legal = messageResource.get('api.insee.unite.legal', 'config');
         token_api_insee = messageResource.get('api.insee.token', 'config');
		 
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
			    "Authorization": "Bearer "+token_api_insee
			},
	        url: url_api_insee+'/'+$("#siretnvviamodal").val(),
	        type: 'GET',
	        async: false,
	        success: function (response) { 
	        document.getElementById('nomclientnvviamodal').value=response.etablissement.uniteLegale.denominationUniteLegale;
	        document.getElementById('adressenvviamodal').value=response.etablissement.adresseEtablissement.numeroVoieEtablissement+" "+response.etablissement.adresseEtablissement.typeVoieEtablissement+" "+response.etablissement.adresseEtablissement.libelleVoieEtablissement
	        document.getElementById('villenvviamodal').value=response.etablissement.adresseEtablissement.libelleCommuneEtablissement;
	        document.getElementById('codepostalenvviamodal').value=response.etablissement.adresseEtablissement.codePostalEtablissement;
                
            $.ajax({
			headers : {
				 "Accept" : "application/json; charset=utf-8",
			    "Authorization": "Bearer "+token_api_insee
			},
	        url: url_api_insee_unite_legal+'/'+response.etablissement.siren,
	        type: 'GET',
	        async: false,
            success: function (response) { 
	        document.getElementById('numtvanvviamodal').value=response.unite_legale.numero_tva_intra; 
	         }
             });
               
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




function downloadkbis() {
$('#cover-spin').show(0);
var xhr = new XMLHttpRequest();
    xhr.open('GET', 'Download_Kbis', true);
    xhr.responseType = 'arraybuffer';
    xhr.onload = function(e) {
       if (this.status == 200) {
	      $('#cover-spin').hide();
          var blob=new Blob([this.response], {type:"application/pdf"});
          var link=document.createElement('a');
          link.href=window.URL.createObjectURL(blob);
          link.download=xhr.getResponseHeader('content-disposition').split('filename=')[1].split(';')[0];
          link.click();
       }
         if (this.status == 404) {
	              $('#cover-spin').hide();
                  document.getElementById("msgmodalnotif").innerHTML="<b>Erreur Téléchargement Kbis , voir log</b>";    
		          document.getElementById("titleModalnotify").innerHTML=" <span style='color: red;'><i class='fas fa-exclamation-triangle'></i> ERREUR</span>";
				  $("#Modalnotify").modal(); 
       }

    };
xhr.send();
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
                         $("#numtva").val(response.numtva);
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
	var numtvam = document.getElementById('numtvanvviamodal').value;
	//client professionnel
	
	if (document.getElementById('professionnel').checked  && nomclientm.length > 0 && adressem.length > 0 && siretm.length >0) {
		if (emailm.length >0  &&  telm.length >0 ) {
			$("#spinnerbuttoncreatenewclient").show();
			var ItemJSON = {"rs": nomclientm,"adresse":adressem,"cp": codepostalem,"ville":villem,"telephone":telm,"mail":emailm,"siret":siretm,"numtva":numtvam};
		    var myJSON = JSON.stringify(ItemJSON);	 
		    $.ajax({
			      type: "POST",
			      contentType : 'application/json; charset=utf-8',
			      url: "createclient",
			      data: myJSON,
			      success :function(response) {
			    	  $("#spinnerbuttoncreatenewclient").hide();
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
	$("#numtvamodal").val($("#numtva").val());
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
                
                 $("#client_name_for_sms").append("<option value='"+data[i].rs+"'>"+data[i].rs+"</option>");
                }
        }
        });
	
}

function getclientinfo(client_name,field) {
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
        		if(field.localeCompare('email') == 0) {
                $("#client_email").val(data[i].mail);	
                }
                if(field.localeCompare('tel') == 0) {
                $("#client_tel").val(data[i].telephone);
                }
        		}
                }
        }
        });
	
}



function saveupdateclient() {
	
	
    var ItemJSON = {"rs": $("#rs").val(),"siret":$("#siret").val(),"adresse": $("#adresse").val(),"cp":$("#cp").val(),"ville":$("#ville").val(),"telephone":$("#tel").val(),"mail":$("#email").val()};
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
	$('#cover-spin').show(0);
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
        	$('#cover-spin').hide();
            // download pdf from Amazone S3
       var resultmsg=response.msg;
       if (resultmsg.includes("ERROR")) {
	        document.getElementById("msgModalnotify").innerHTML="<b> "+response.msg+"</b>";
            document.getElementById("titlemodal").innerHTML="<span style='color: red;'><i class='fas fa-exclamation-triangle'></i> Error </span>";
            $("#Modalnotify").modal()
        
        }else {
	       $('#cover-spin').hide();
	      document.getElementById("msgModalnotify").innerHTML="<b>"+response.msg+"</b>";
            document.getElementById("titlemodal").innerHTML="<span style='color: green;'><i class='fas fa-check-circle'></i> Confirmation</span>";
            $("#Modalnotify").modal();
            setTimeout(function(){
            	  $('#Modalnotify').modal('hide')
           	}, 2000);
	      
     
        

var xhr = new XMLHttpRequest();
    xhr.open('GET', 'Download_From_Amazone_AS_Bytes/'+response.Invoice_path, true);
    xhr.responseType = 'arraybuffer';
    xhr.onload = function(e) {
       if (this.status == 200) {
          var blob=new Blob([this.response], {type:"application/pdf"});
          var link=document.createElement('a');
          link.href=window.URL.createObjectURL(blob);
          link.download=response.Invoice_name;
          link.click();
       }
    };
xhr.send();


}
}
});
}

function checkdate(date){
var today = new Date().toISOString().slice(0, 10);
if (date < today) {
 document.getElementById("msgModalnotify").innerHTML="<b> Merci de choisir une date supérieure ou égale à celle d'aujourd'hui</b>";
 document.getElementById("titlemodal").innerHTML="<span style='color: red;'><i class='fas fa-exclamation-triangle'></i> Error </span>";
 $("#Modalnotify").modal()	
}
}




function Generate_post_invoice() {
   $('#cover-spin').show(0);
   $("#Prestation_table tr").click(function() {//Add a click event to the row of the table
	var tr = $(this);//Find tr element
	var td = tr.find("td");//Find td element
    var xhr = new XMLHttpRequest();
    xhr.open('POST', 'Generate_Post_Invoice'+"/"+td[1].innerText, true);
    xhr.responseType = 'arraybuffer';
    xhr.onload = function(response) {
       if (this.status == 200) {
	      $('#cover-spin').hide();
          var blob=new Blob([this.response], {type:"application/pdf"});
          var link=document.createElement('a');
          link.href=window.URL.createObjectURL(blob);
          link.download=td[1].innerText+".pdf";
          link.click();
            document.getElementById("msgModalnotify").innerHTML="<b> "+td[1].innerText+" a été bien générée</b>";
            document.getElementById("titlemodal").innerHTML="<span style='color: green;'><i class='fas fa-check-circle'></i> Confirmation</span>";
            $("#Modalnotify").modal();
            setTimeout(function(){
            	  $('#Modalnotify').modal('hide')
           	}, 2000);


       }else {
	        document.getElementById("msgModalnotify").innerHTML="<b> "+jqXHR.responseText+"</b>";
            document.getElementById("titlemodal").innerHTML="<span style='color: red;'><i class='fas fa-exclamation-triangle'></i> Error </span>";
            $("#Modalnotify").modal();	
       }
    };
xhr.send();
});
}
//****************************************************** JS send mail 
function sendmail(mailto,subject,contain){
	$("#refresh_gif").show();
	var formData = new FormData();
	formData.append('mailto', mailto);
	formData.append('subject', encodeURIComponent(subject));
	formData.append('contain', encodeURIComponent(contain));
	formData.append('attached_file', $('input[type=file]')[0].files[0]);
	if (typeof document.getElementById('file').files[0] !== 'undefined') {
	formData.append('attached_file_name', document.getElementById('file').files[0].name);
	}
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


function sendsms(tel,contain){
	if (tel.length === 0 || contain.length === 0){
		document.getElementById("msgkosms").innerHTML="<b>Tel ou corps sms manquants</b>"
        $("#alertkosms").show();
		
	}else{
	$("#refresh_gif_sms").show();
	var formData = new FormData();
	formData.append('contain', encodeURIComponent(contain));
	$.ajax({
        url: "sendsms/+33"+tel.substring(1),
        type: 'POST',
        async: false,
        processData: false,
        contentType: false,
        data: formData,
        success: function (response) {
	    $("#refresh_gif_sms").hide();
        document.getElementById("msgoksms").innerHTML="<b>"+response+"</b>"
        $("#alertoksms").show();
        },
        error :function (jqXHR) {
	    $("#refresh_gif_sms").hide();
        document.getElementById("msgkosms").innerHTML="<b>"+jqXHR.responseText+"</b>"
        $("#alertkosms").show();
        }
	});	
}
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
	if ($("#designation").val().length >0 && $("#famille").val().length > 0 && $("#pvht").val().length >0  
			 && $("#taxe").val().length >0 && $("#valtaxe").val().length>0 && $("#pvttc").val().length >0 ) {
    $("#loader").show();
	var ItemJSON = {"designation": $("#designation").val(),"famille":$("#famille").val(),"pvht":$("#pvht").val(),"paht": $("#paht").val(),"taxe":$("#taxe").val(),"valtaxe":$("#valtaxe").val(),"pvttc":$("#pvttc").val()};
    var myJSON = JSON.stringify(ItemJSON);
	
	$.ajax({
        url: "createnewarticle",
        type: 'POST',
        async: true,
        processData: false,
        contentType: "application/json; charset=utf-8",
        data: myJSON,
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
       document.getElementById("titlemodalmodifyarticle").innerHTML="<b> <span style='color: green;'><i class='fas fa-edit'></i> Modificaion Article "+td[0].innerText+"</span></b>"
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

function GetAllTransactionsWithProof(){
	
	
}
//*************************************************************charts

function drawcharts() {
	$("#loading").show();
	$.ajax({
		type : 'GET',
		url : 'liste_prestations_by_year',
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
		$("#loading").hide();
		}
		
	});
	}
function drawChartca(result) {
	var data = new google.visualization.DataTable();
	data.addColumn('string', 'date');
	data.addColumn('number', 'total');
	var dataArray = [];
	$.each(result, function(i,obj) {
		if (obj.date.localeCompare('') !== 0) {	
		var datesub=obj.date.toString().substring(0,7);
		var somme=0;
		$.each(result, function(i,obj) {
		 var date=obj.date.toString().substring(0,7);
		 if (date.localeCompare(datesub) == 0) {
			somme=somme+obj.totalttc; 
			obj.date="";
		 }
		});
		data.addRow([datesub,somme]);	
		}
	});
	
	
	
	 
	var combochart_options = {
			title : 'Evolution Chiffre affaire en Euro',
			vAxis: {title: 'Chiffre affaire en euro'},
	        hAxis: {title: 'Mois'},
	        seriesType: 'bars',
	        series: {5: {type: 'line'} }};
	       
		var combochart = new google.visualization.ComboChart(document.getElementById('combodiv'));
		combochart.draw(data, combochart_options);

		
}

function show_modal_prestation() {
	
	$("#tableprestations tr").click(function() {//Add a click event to the row of the table
	var tr = $(this);//Find tr element
	var td = tr.find("td");//Find td element
   $.ajax({
   type : 'GET',
   url : 'Get_Prestation_by_numfacture'+"/"+td[0].innerText,
   async: true,
   processData: false,
   contentType: false,
   success : function(response) {
	document.getElementById('nomfacture').value=response.nomfacture+".pdf";
	document.getElementById('client').value=response.client;
	document.getElementById('date').value=response.date;
	document.getElementById('article').value=response.article;
	document.getElementById('quantite').value=response.quantite;
	document.getElementById('montantht').value=response.montantHT;
	document.getElementById('totalttc').value=response.totalttc;
	document.getElementById('statutpaiement').value=response.statut_paiement;
	document.getElementById('modepaiement').value=response.modepaiement;
	$("#Modalprestation").modal();
	}
    });
});
	
	
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


function UpdateTransaction(){
$("#modalupdatetransaction").modal();	

$("#table_transactions tr").click(function() {//Add a click event to the row of the table
	var tr = $(this);//Find tr element
	var td = tr.find("td");//Find td element
	
	$("#button_submit").click(function()
{
	$('#cover-spin').show(0);
    var formData = new FormData();
	formData.append('amount', td[0].innerText);
	formData.append('settled_at', td[6].innerText);
	formData.append('updated_at', td[7].innerText);
	formData.append('proof_file_name', document.getElementById('proof').files[0].name);
	formData.append('proof_file', document.getElementById('proof').files[0]);
	formData.append('label', td[5].innerText);
	formData.append('reference', td[8].innerText);
	formData.append('montant', td[0].innerText);
	
	var formData2 = new FormData();
	formData2.append('settled_at', td[6].innerText);
	formData2.append('updated_at', td[7].innerText);
	
	
	$.ajax({
	        url: "CheckExistProof",
	        type: 'POST',
	        async: false,
	        processData: false,
	        contentType: false,
	        data: formData2,
	        success: function  (response) {
            if ((response.msg.indexOf('Checkok') != -1)) {
	        $.ajax({
	        url: "UploadProof",
	        type: 'POST',
	        async: false,
	        processData: false,
	        contentType: false,
	        data: formData,
	        success: function  () {
           $('#cover-spin').hide();
           document.getElementById("msgokupload").innerHTML="Le justificatif "+document.getElementById('proof').files[0].name+" a été bien uploadé dans Amazone bucket"
		   $("#divconfirmok").show();
           },
           error : function() {
	       $('#cover-spin').hide();
          document.getElementById("msgkoupload").innerHTML="Error lors de l'upload du justificatif "+document.getElementById('proof').files[0].name+"  dans Amazone bucket"
		   $("#divconfirmko").show()
       }
  });
	}else{
	       $('#cover-spin').hide();
           document.getElementById("msgkoupload").innerHTML=response.msg;
		   $("#divconfirmko").show()	
	}
	
	}
	});
	
});
	});	
}


function UploadNewProof(){
$("#modalupdatetransaction").modal();	

$("#Proofs_Table tr").click(function() {//Add a click event to the row of the table
	var tr = $(this);//Find tr element
	var td = tr.find("td");//Find td element
	
	$("#button_submit").click(function()
   {
	 $('#cover-spin').show();
	  $.ajax({
	        url: "DeleteProof/"+td[0].innerText.substring(0,4)+"/"+td[5].innerText+"/"+td[0].innerText+"/"+td[1].innerText+"/"+td[2].innerText+"/"+td[3].innerText+"/"+td[4].innerText,
	        type: 'POST',
	        async: false,
	        processData: false,
	        contentType: false,
	    success: function () {
	     var formData = new FormData();
         formData.append('settled_at', td[0].innerText);
	     formData.append('updated_at', 'NULL');
	     formData.append('proof_file_name', document.getElementById('proof').files[0].name);
	     formData.append('proof_file', document.getElementById('proof').files[0]);
         formData.append('label', td[1].innerText);
	     formData.append('reference', td[2].innerText);
	     formData.append('montant', td[3].innerText);
	        $.ajax({
	        url: "UploadProof",
	        type: 'POST',
	        async: false,
	        processData: false,
	        contentType: false,
	        data: formData,
	        success: function  () {
           $('#cover-spin').hide();
            $("#container_proofs").load("List_Proofs");
           },
           error : function() {
	       $('#cover-spin').hide();
       }
  });
       
               },
        error : function (jqXHR) {
	     $("#cover-spin").hide();
	
          }
	});
	
	
	
	});
	});
	}






function Deleteproof() {
$("#Proofs_Table tr").click(function() {//Add a click event to the row of the table
	var tr = $(this);//Find tr element
	var td = tr.find("td");//Find td element
	    $("#cover-spin").show();
        $.ajax({
	        url: "DeleteProof/"+td[0].innerText.substring(0,4)+"/"+td[5].innerText+"/"+td[0].innerText+"/"+td[1].innerText+"/"+td[2].innerText+"/"+td[3].innerText+"/"+td[4].innerText,
	        type: 'POST',
	        async: false,
	        processData: false,
	        contentType: false,
	    success: function () {
	    $("#cover-spin").hide();
        $("#container_proofs").load("List_Proofs");
             },
        error : function (jqXHR) {
	     $("#cover-spin").hide();
	    document.getElementById("msgmodalnotif").innerHTML="<b>"+jqXHR.responseText+ "</b>";
		document.getElementById("titleModalnotify").innerHTML=" <span style='color: red;'><i class='fas fa-exclamation-circle'></i> Error Delete</span>";
		$("#Modalnotify").modal();
            }
	});
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
	if (response.nb_credit > 0 ||  response.nb_debit > 0) {
	            document.getElementById("msgModalnotify").innerHTML="<b> Nouvelles transactions ont été importées avec succés</b>: <br/>"+
                +response.nb_credit+ " <b>Crédits</b> <br/>"+
                +response.nb_debit+" <b>Débits</b>"
		        document.getElementById("titlemodal").innerHTML="<span style='color: green;'><i class='fas fa-file-upload'></i> Confirmation</span>";
                $("#container_transaction").load("liste_transactions_bank");
                $("#Modalnotify").modal();
                


	}
	if (response.nb_credit == 0 && response.nb_debit == 0) {
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
       formData.append('updated_at', td[7].innerText);
		$.ajax({
	        url: "GestionTVA",
	        type: 'POST',
	        async: false,
	        processData: false,
	        contentType: false,
	        data: formData,
	        success: function () {
		      document.getElementById("msgModalnotify").innerHTML="<b> La TVA a été bien appliquée </b>";
              document.getElementById("titlemodal").innerHTML="<span style='color: green;'><i class='far fa-check-circle'></i>Confirmation</span>";
              $('#Modalnotify').appendTo("body").modal('show');
              $("#container_transaction").load("liste_transactions_bank");
        
              
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
       formData.append('updated_at', td[7].innerText); 



		$.ajax({
	        url: "GestionTVA",
	        type: 'POST',
	        async: false,
	        processData: false,
	        contentType: false,
	        data: formData,
	         success: function () {   
              document.getElementById("msgModalnotify").innerHTML="<b> La TVA a été enlevée </b>";
              document.getElementById("titlemodal").innerHTML="<span style='color: green;'><i class='far fa-check-circle'></i> Confirmation</span>";
              $('#Modalnotify').appendTo("body").modal('show');
              $("#container_transaction").load("liste_transactions_bank");
    
            
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
	        success: function (data) {
            $("#totaltva").val(data.totaltvarecoltes);	        
            $("#totaltvadu").val(data.totaltvadu);
            },
           error : function () {
	
}
});

            $.ajax({
	        url: "Getransactionsbetween_with_tva/"+$('#datedeb').val()+"/"+$('#datefin').val(),
	        type: 'GET',
	        async: false,
	        processData: false,
	        contentType: false,
	        success: function (data) {
        var trHTML = '';
        $('#tbobytransaction').empty();	
        $.each(data, function (i, item) {
        if (item.operation_type.localeCompare('income') == 0) {
         trHTML += '<tr style="color:green"><td>' + item.label + '</td><td>'+item.reference +'</td><td>' + item.settled_at + '</td><td>' + item.side + '</td><td>'+item.operation_type+'</td><td>'+ item.amount  +'</td><td>'+item.amount_HT +'</td></tr>' ;
        }
        else {
	  trHTML += '<tr><td>' + item.label + '</td><td>'+item.reference +'</td><td>' + item.settled_at + '</td><td>' + item.side + '</td><td>'+item.operation_type+'</td><td>'+ item.amount  +'</td><td>'+item.amount_HT +'</td></tr>' ;
           }
        });
	       $('#tbobytransaction').append(trHTML);
            },
           error : function () {
	
}
});
     
}




function showmodaltva () {
	
const settings = {
  "async": true,
  "crossDomain": true,
  "url": "https://thirdparty.qonto.com/v2/organizations/zohratec-7289",
  "method": "GET",
  "dataType": "jsonp",
  "headers": {
    "Content-Type": "application/json",
    "Authorization": "zohratec-7289:9843cd2bbe87db673e74817b3f89960f"
  }
};

$.ajax(settings).done(function (response) {
  console.log(response);
});
	
	
	 var d = new Date();
     var m = d.getMonth();
     var y = d.getFullYear();
    //declaration 1ere trim 
    if (m+1>=1 && m+1<=3) {
    $.ajax({
	        url: "totaltva/"+y+"-01-01/"+y+"-03-31",
	        type: 'GET',
	        async: false,
	        processData: false,
	        contentType: false,
	        success: function (data) {
		   $('#tabletva').empty();
           var trHTML="<tr><td>Déclaration 1ere trimestre</td><td>"+y+"-01-01 au "+y+"-03-30</td><td>En cours</td> <td>"+data.totaltvadu+"</td></tr>";
           $('#tabletva').append(trHTML);
           $('#Modaldeclarationtva').modal('toggle'); 
		}
         
    });
    }
    //
    if (m+1>=4 && m+1<=6) {
	
	    $.ajax({
	        url: "totaltva/"+y+"-01-01/"+y+"-03-31",
	        type: 'GET',
	        async: false,
	        processData: false,
	        contentType: false,
	        success: function (data) {
		   $('#tabletva').empty();
       var trHTML="<tr><td>Déclaration 1ére trimestre</td><td>"+y+"-01-01 au "+y+"-03-30</td><td><span style='color: green;'><i class='far fa-check-circle'></i> Done</span></td> <td>"+data.totaltvadu+"</td></tr>";
           $('#tabletva').append(trHTML);
           

          $.ajax({
	        url: "totaltva/"+y+"-04-01/"+y+"-06-30",
	        type: 'GET',
	        async: false,
	        processData: false,
	        contentType: false,
	        success: function (data) {
           var trHTML="<tr><td>Déclaration 2éme trimestre</td><td>"+y+"-04-01 au "+y+"-06-30</td><td>En cours</td> <td>"+data.totaltvadu+"</td></tr>";
           $('#tabletva').append(trHTML);
		  	
		}
    
    });
	 $('#Modaldeclarationtva').modal('toggle');
	}
	});
    }

    if (m+1>=7 && m+1<=9) {
	
	 $.ajax({
	        url: "totaltva/"+y+"-01-01/"+y+"-03-31",
	        type: 'GET',
	        async: false,
	        processData: false,
	        contentType: false,
	        success: function (data) {
		   $('#tabletva').empty();
           var trHTML="<tr><td>Déclaration 1ere trimestre</td><td>"+y+"-01-01 au "+y+"-03-30</td><td><span style='color: green;'><i class='far fa-check-circle'></i> Done</td> <td>"+data.totaltvadu+"</td></tr>";
           $('#tabletva').append(trHTML);
	
	
	$.ajax({
	        url: "totaltva/"+y+"-04-01/"+y+"-06-30",
	        type: 'GET',
	        async: false,
	        processData: false,
	        contentType: false,
	        success: function (data) {
           var trHTML="<tr><td>Déclaration 2éme trimestre</td><td>"+y+"-04-01 au "+y+"-06-30</td><td><span style='color: green;'><i class='far fa-check-circle'></i> Done</span></td> <td>"+data.totaltvadu+"</td></tr>";
           $('#tabletva').append(trHTML);
	  $.ajax({
	        url: "totaltva/"+y+"-07-01/"+y+"-09-30",
	        type: 'GET',
	        async: false,
	        processData: false,
	        contentType: false,
	        success: function (data) {
           var trHTML="<tr><td>Déclaration 3éme trimestre</td><td>"+y+"-07-01 au "+y+"-09-30</td><td><span style='color: orange;'>En cours</span></td> <td>"+data.totaltvadu+"</td></tr>";
           $('#tabletva').append(trHTML);
		}
    
    });
	}
	});
    $('#Modaldeclarationtva').modal('toggle');
    }
    });
  }
   if (m+1>=10 && m+1<=12) {
	
$.ajax({
	        url: "totaltva/"+y+"-01-01/"+y+"-03-31",
	        type: 'GET',
	        async: false,
	        processData: false,
	        contentType: false,
	        success: function (data) {
		   $('#tabletva').empty();
           var trHTML="<tr><td>Déclaration 1ere trimestre</td><td>"+y+"-01-01 au "+y+"-03-30</td><td><span style='color: green;'><i class='far fa-check-circle'></i> Done</td> <td>"+data.totaltvadu+"</td></tr>";
           $('#tabletva').append(trHTML);
	
	
	$.ajax({
	        url: "totaltva/"+y+"-04-01/"+y+"-06-30",
	        type: 'GET',
	        async: false,
	        processData: false,
	        contentType: false,
	        success: function (data) {
           var trHTML="<tr><td>Déclaration 2éme trimestre</td><td>"+y+"-04-01 au "+y+"-06-30</td><td><span style='color: green;'><i class='far fa-check-circle'></i> Done</span></td> <td>"+data.totaltvadu+"</td></tr>";
           $('#tabletva').append(trHTML);
	  $.ajax({
	        url: "totaltva/"+y+"-07-01/"+y+"-09-30",
	        type: 'GET',
	        async: false,
	        processData: false,
	        contentType: false,
	        success: function (data) {
           var trHTML="<tr><td>Déclaration 3éme trimestre</td><td>"+y+"-07-01 au "+y+"-09-30</td><td><span style='color: green;'><i class='far fa-check-circle'></i> Done</span></td> <td>"+data.totaltvadu+"</td></tr>";
           $('#tabletva').append(trHTML);
		
		 	$.ajax({
	        url: "totaltva/"+y+"-10-01/"+y+"-12-31",
	        type: 'GET',
	        async: false,
	        processData: false,
	        contentType: false,
	        success: function (data) {
           var trHTML="<tr><td>Déclaration 4éme trimestre</td><td>"+y+"-07-01 au "+y+"-09-30</td><td><span style='color: orange;'>En cours</span></td> <td>"+data.totaltvadu+"</td></tr>";
           $('#tabletva').append(trHTML);
		}
		
		
		});
		
    }
    });
	}
	
	});
    $('#Modaldeclarationtva').modal('toggle');
    }
});




}

}


//*********************************************************************************JS profil 

function changeavatar(file,applied_to) {
var formData = new FormData();
formData.append('avatar', file);	
formData.append('applied_to', applied_to);
	 $.ajax({
	        url: "updateavatar",
	        type: 'POST',
	        async: false,
	        processData: false,
	        contentType: false,
            data: formData,
	        success: function () {
            if (applied_to.localeCompare("avatar") == 0) {
            $("#containerprofil").load("profile");
            }else {
            $("#containtersettings").load("Companysettings");
            }
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
function updateinfo(){
	
		 $.ajax({
	        url: "updateinfo/"+$("#eMail").val()+"/"+$("#phone").val()+"/"+$("#company").val(),
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

//********************************************************* JS reset login password */
