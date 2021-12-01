<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

<head>
<title>Invoicing</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel = "icon" href =  "${pageContext.request.contextPath}/resources/images/logo.png"  type = "image/x-icon"> 
<style type="text/css">
  <%@include file="/resources/css/main.css" %>
</style>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap3-dialog/1.34.7/css/bootstrap-dialog.min.css">
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<script src="https://kit.fontawesome.com/b16c365929.js"></script>
</head>
<body>
<div class="nav-side-menu">
        <%
        java.util.Properties prop = new java.util.Properties();
        prop.load(getServletContext().getResourceAsStream("/META-INF/MANIFEST.MF"));
        String applVersion = prop.getProperty("Implementation-Version");  
        %>
    <div class="brand"><img src="${pageContext.request.contextPath}/resources/images/logo.png" width="100" height="100" alt="invoicing">
    <br/>
    <span style="font-size:smaller ;">Version : <%=applVersion%></span>
    <div><i class="far fa-user-circle"></i> ${welcome}</div>
    </div>
 
   
 
 
 
    <i class="fa fa-bars fa-2x toggle-btn" data-toggle="collapse" data-target="#menu-content"></i>
  
        <div class="menu-list">
  
            <ul id="menu-content" class="menu-content collapse out">
                <li>
                  <a href="charts" class="link">
                  <i class="fa fa-dashboard fa-lg"></i> Dashboard </a>        
                  </li>

                <li  data-toggle="collapse" data-target="#clients" class="collapsed">
                  <a href="#" aria-controls="v-pills-home"><i class="fas fa-users"></i> Clients <span class="arrow"></span></a>
                
                <ul class="sub-menu collapse" id="clients">
                    <li><a  class="link"href="clients" id="submenuclient">Liste clients</a></li>
                </ul>
                </li>


                <li data-toggle="collapse" data-target="#articles" class="collapsed">
                  <a href="#"><i class="fas fa-shopping-cart"></i> Articles <span class="arrow"></span></a>
                 
                <ul class="sub-menu collapse" id="articles">
                
                   <li ><a  class="link"href="articles">Liste Articles</a></li>
                </ul>
                </li> 

                <li data-toggle="collapse" data-target="#new" class="collapsed">
                  <a href="#"><i class="fas fa-chart-area"></i> Reporting <span class="arrow"></span></a>
                
                <ul class="sub-menu collapse" id="new">
                  <li><a class="link" href="prestations">Liste de prestations</a></li>
                </ul>
                </li>
                 <li>
                  <a href="profile" class="link">
                  <i class="fa fa-user fa-lg"></i> Profile
                  </a>
                  </li>

                <li data-toggle="collapse" data-target="#action" class="collapsed">
                  <a href="#"><i class="far fa-plus-square"></i> Actions <span class="arrow"></span></a>
                
                <ul class="sub-menu collapse" id="action">
                  <li><a class="link"href="newinvoice">Création nouvelle facture</a></li> 
                   <li> <a href="notifyclient" class="link">Relancer une facture </a> </li>
                   
                    
                </ul>
                </li>
                
                 <li data-toggle="collapse" data-target="#settings" class="collapsed">
                  <a href="#"><i class="fas fa-cogs"></i> Settings <span class="arrow"></span></a>
                 <ul class="sub-menu collapse" id="settings">
                  <li><a class="link" href="Companysettings">Company Settings</a></li>
                  <li><a class="link" href="General_settings">General Settings</a></li>
                  
                  </ul>
                  </li>           
                 <li data-toggle="collapse" data-target="#messagerie" class="collapsed">
                  <a href="#"><i class="fas fa-envelope"></i> Messagerie <span class="arrow"></span></a>
                 <ul class="sub-menu collapse" id="messagerie">
                  <li><a class="linkmodal" href="#" data-toggle="modal" data-target="#modalmessagerie">Envoyer email</a></li>
                </ul>
                </li>  
                
                  
                   <li data-toggle="collapse" data-target="#paiement" class="collapsed">
                  <a href="#"><i class="fas fa-receipt"></i> Paiement <span class="arrow"></span></a>
                 <ul class="sub-menu collapse" id="paiement">
                  <li><a class="link" href="validate_paiement">Valider paiement</a></li>
                </ul>
                </li> 
                <li data-toggle="collapse" data-target="#bank" class="collapsed">
                <a href="#"><i class="fas fa-university"></i>Transactions bancaires<span class="arrow"></span></a>
                <ul class="sub-menu collapse" id="bank">
                <li><a href="liste_transactions_bank" class="link">Liste transactions  ${company_bank_name}</a></li>
                <li><a href="Get_Tracking_Batch" class="link">Suivi Batch Import</a></li>
                <li><a href="tva_collectee" class="link">Détail TVA</a></li>
                <li><a href="#" onclick="showmodaltva()">Déclaration TVA</a></li>
                </ul>
                </li>            
                <li>
                <a href="logout">
                <i class="fas fa-sign-out-alt"></i> Exit </a>        
                </li>
                
             
                
                
                     
            </ul>
     </div>
     
      <div class="brand">
       <i class="far fa-building"></i> ${company_name}
      </div>
     
     
     
     </div>
     
     
     <div class="modal fade" id="modalmessagerie" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel"> <span style="color:green"><i class="fas fa-envelope-open-text"></i>  Nouveau message</span></h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <form>
                                 
                                 
                                 <div class="form-group row">
                                 <label class="col-sm-4 col-form-label"><b>Client</b></label>
                                 <div class="col-sm-8">
                                  <select   id ="client_name"  class="form-control"   onchange="getclientemail(this.value)" >
                                  <option value=""></option>
                                 </select>
                                 </div>
                                 </div>
                                 
                                 <div class="form-group row">
                                 <label class="col-sm-4 col-form-label"><b>Adresse mail</b></label>
                                 <div class="col-sm-8">
                                 <input type="text"   id ="client_email"  class="form-control">
                                 </div>
                                 </div>
                                 
          
                                 <div class="form-group row">
                                 <label class="col-sm-4 col-form-label"><b>Piéce jointe</b></label>
                                 <div class="col-sm-8">
                                 <input type="file"  id ="file"  class="form-control" onchange="checkfilesize(this.files[0].size)" multiple>
                                 </div>
                                 </div>
                                 
                                 <div class="form-group row">
                                 <label class="col-sm-4 col-form-label"><b>Template</b></label>
                                 <div class="col-sm-8">
                                 <select   id ="templatecontain"  class="form-control" onchange="gettemplatecontain(this.value,$('#client_name').val())">
                                 <option value=""></option>
                                 <option value="Relance_Paiement">Relance Paiement</option>
                                 <option value="Envoi_Facture">Envoi Facture</option>
                                 </select>
                                 </div>
                                 </div>
                                 
                                 <div class="form-group row">
                                 <label class="col-sm-4 col-form-label"><b>Sujet</b></label>
                                 <div class="col-sm-8">
                                 <input type="text" id ="subject"  class="form-control">
                                 </div>
                                 </div>
                                 <div class="form-group">
                                 <label for="message-text" class="col-form-label"><b>Message</b></label>
                                 <textarea class="form-control" id="containmail" rows="5"></textarea>
                                 </div>
        </form>
      </div>
      <div class="modal-footer">
      <button type="button" class="btn btn-success" onclick="sendmail($('#client_email').val(),$('#subject').val(),$('#containmail').val())">Send message <i class="far fa-paper-plane"></i> <img src="${pageContext.request.contextPath}/resources/images/icon_refresh.gif" width="20" height="20" style="display: none" id="refresh_gif"></button>
      <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
      </div>
      
      
      <div class="alert alert-success" role="alert" style="display:none;" id="alertok">
      <span class="alert-icon"><span class="sr-only">Success</span></span>
      <span id="msgok"></span>
      </div>
    
      <div class="alert alert-danger" role="alert" id="alertko" style="display:none;">
      <span class="alert-icon"><span class="sr-only">Danger</span></span>
     <span id="msgko"></span>
     </div>
    
    
     </div>
  </div>
</div>
    
     <div id="page-content-wrapper" class="containside">
     </div> 

  <div class="modal fade" id="Modalnotify" tabindex="-1"  aria-labelledby="titleModalnotify" role="dialog" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="titleModalnotify"></h5>
      </div>
      <div class="modal-body">
       <span id="msgmodalnotif"></span>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>


  <div class="modal fade" id="Modaldeclarationtva" tabindex="-1"  role="dialog" aria-hidden="true">
  <div class="modal-dialog modal-lg" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" ><span style='color: green;'><i class="fas fa-university"></i> Historique déclaration TVA</span></h5>
      </div>
      <div class="modal-body">
      <table class="table table-striped">
    <thead>
      <tr>
        <th>Déclaration</th>
        <th>Date </th>
        <th>Statut</th>
        <th>Montant</th>
      </tr>
    </thead>
    <tbody id="tabletva">
    </tbody>
    </table>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>
 
     
    
     <script type="text/javascript">  
     $('.link').on('click', function (e) {
    	 e.preventDefault();
         var page = $(this).attr('href');
         $('#page-content-wrapper').load(page);
});
   
 </script>
<script src="${pageContext.request.contextPath}/resources/scripts/messageResource.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/scripts.js" ></script> 
<script>
$(document).ready(function () {
	$('#page-content-wrapper').load("charts");
	getclientsnames();
	});
</script>
<script>
$('#modalmessagerie').on('hidden.bs.modal', function () {
    $(this).find('form').trigger('reset');
    $("#containmail").val("");
    $("#alertok").hide();
    $("#alertko").hide();
})
</script>

<c:if test = "${flag_reset_password == 'YES'}"> 
<script>
document.getElementById("msgmodalnotif").innerHTML="<b> Merci de modifier votre password </b>";
document.getElementById("titleModalnotify").innerHTML="<span style='color: orange;'><i class='fas fa-bell'></i> Information</span>";
$("#Modalnotify").modal();
</script>
</c:if>


</body>
</html>