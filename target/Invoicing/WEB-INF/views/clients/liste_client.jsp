<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
<!-- Bootstrap CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<style>
<%@include file="/resources/css/profil.css" %>
</style>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/scripts/messageResource.js"></script> 
<body>

<div class="card h-100">
                        
                        <div class="card-body">
                                <div class="form-group row">
                                    <label for="id" class="col-sm-2 col-form-label">Nom:</label>
                                    <div class="col-sm-4">
                                  <input type="text" placeholder="Nom/raison social"  id="nomfromsearchclient" class="form-control autocomplete" oninput="javascript:$('#tableclient').hide();" />
                                  </div>
                                  </div>     
                                  <div class="form-group row">
                                    <label for="id" class="col-sm-2 col-form-label">Email:</label>
                                    <div class="col-sm-4">
                                  <input type="email" placeholder="email du client" id="emailfromsearchclient" class="form-control autocomplete" oninput="javascript:$('#tableclient').hide();" />
                                  </div>
                                  </div>
                             
                                  <button  class="btn btn-primary" onclick="Getclient()" id="btsearch"><i class="fas fa-search"></i> Rechecher <img src="${pageContext.request.contextPath}/resources/images/icon_refresh.gif" width="25" height="25" style="display: none" id="refresh_gif" ></button>
                                  
                                  <button type="button"  class="btn btn-success"  data-toggle="modal" data-target="#modalnvclient"><i class="fas fa-plus"></i> Nouveau client</button>
                         
                     
                        </div>
                        </div>
                        
<div>
</div>
<div class="modal fade" id="modalnvclient" tabindex="-1" aria-labelledby="ariamodalcreate" aria-hidden="true" role="dialog">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="ariamodalcreate"><span style='color: green;'>Création nouveau client</span></h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body"> 
            <div class="form-group row">
            <label for="nomclient" class="col-sm-4 col-form-label"><b>Type Client:</b></label> 
            <div class="col-sm-6">
                               <div class="form-check">
                               <input class="form-check-input" type="radio" id="particulier" name="typeclient" checked onclick="javascript:changelabelclientname('Nom');$('#siretproffield').hide()">
                               <label class="form-check-label" for="particulier">Particulier</label>
                               </div>

                              <div class="form-check">
                              <input class="form-check-input" type="radio"  id="professionnel" name="typeclient" onclick="changelabelclientname('Raison Sociale');$('#siretproffield').show()">
                              <label class="form-check-label" for="professionnel">Professionnel</label> 
                              </div>
            </div>
            </div>
            <div class="form-group row" style="display:none;" id="siretproffield" >
            <label for="nomclient" class="col-sm-4 col-form-label"><b>SIRET Client:</b></label> 
            <div class="col-sm-6">
            <input type="text"  class="form-control" id="siretnvviamodal"  oninput="handleValuesiret()"/>
            </div>
            </div>
                            
           <div class="form-group row">
            <label for="nomclient" class="col-sm-4 col-form-label" id="labelnomclient"><b>Nom Client:</b></label> 
            <div class="col-sm-6">
            <input type="text"  class="form-control" id="nomclientnvviamodal" />
            </div>
            </div>
            <div class="form-group row">
            <label for="nomclient" class="col-sm-4 col-form-label"><b>Email:</b></label> 
            <div class="col-sm-6">
            <input type="email"  class="form-control" id="emailnvviamodal" />
            </div>
            </div>
            
            <div class="form-group row">
            <label for="nomclient" class="col-sm-4 col-form-label"><b>Adresse:</b></label> 
            <div class="col-sm-6">
            <input type="text"  class="form-control" id="adressenvviamodal" />
            </div>
            </div>
            
            <div class="form-group row">
            <label for="nomclient" class="col-sm-4 col-form-label"><b>Ville:</b></label> 
            <div class="col-sm-6">
            <input type="text"  class="form-control" id="villenvviamodal" />
            </div>
            </div>
            
            <div class="form-group row">
            <label for="nomclient" class="col-sm-4 col-form-label"><b>Code Postale:</b></label> 
            <div class="col-sm-6">
            <input type="text"  class="form-control" id="codepostalenvviamodal" />
            </div>
            </div>
             <div class="form-group row">
            <label for="nomclient" class="col-sm-4 col-form-label"><b>TEl:</b></label> 
            <div class="col-sm-6">
            <input type="text"  class="form-control" id="telnvviamodal" />
            </div>
            </div>
            
            <div class="form-group row">
            <label for="rib" class="col-sm-4 col-form-label"><b>RIB:</b></label> 
            <div class="col-sm-6">
            <input type="text"  class="form-control" id="ribnvviamodal" />
            </div>
            </div>
            
          </div>
      
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary float-left" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary float-right" onclick="createnewclient()">Save <span class="spinner-border spinner-border-sm"  id="spinnerbuttoncreatenewclient" style="display:none;"></span></button>
      </div>
      </div>
      </div>
      </div>
      
      
  <div class="modal fade" id="Modalnotifyclient" tabindex="-1"  aria-labelledby="titlemodalnotifyclient" role="dialog" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="titlemodalnotifyclient"></h5>
      </div>
      <div class="modal-body">
       <span id="msgmodalnotifyclient"></span>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>

<div class="container" style="display: none" id="divcontainer">
<div class="row gutters">
<div class="col-xl-9 col-lg-9 col-md-12 col-sm-12 col-12">
<div class="card h-100">
	<div class="card-body">
		<div class="row gutters">
			<div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
				<h6 class="mb-2 text-primary">Information du Client</h6>
				<hr/>
			</div>
			<div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
				<div class="form-group">
					<label for="fullName">Raison Sociale</label>
					<input type="text" class="form-control" id="rs" readonly>
				</div>
			</div>
			
			<div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
				<div class="form-group">
					<label for="fullName">siret</label>
					<input type="text" class="form-control" id="siret" readonly>
				</div>
			</div>
			
			<div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
				<div class="form-group">
					<label for="eMail">adresse</label>
					<input type="email" class="form-control" id="adresse">
				</div>
			</div>
			<div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
				<div class="form-group">
					<label for="phone">Code postale</label>
					<input type="text" class="form-control" id="cp">
				</div>
			</div>
				<div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
				<div class="form-group">
					<label for="phone">ville</label>
					<input type="text" class="form-control" id="ville">
				</div>
			</div>
			
			
			<div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
				<div class="form-group">
					<label for="phone">Tel</label>
					<input type="text" class="form-control" id="tel">
				</div>
			</div>
			
			<div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
				<div class="form-group">
					<label for="phone">Email</label>
					<input type="text" class="form-control" id="email" >
				</div>
			</div>
			
				<div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
				<div class="form-group">
					<label for="rib">Rib</label>
					<input type="text" class="form-control" id="rib" >
				</div>
			</div>
			
			
		</div>
		<hr/>
	
		<div class="row gutters">
			<div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
				<div class="text-right">
					<button type="button" id="submit" name="submit" class="btn btn-warning" onclick="saveupdateclient()">Update</button>
				</div>
			</div>
		</div>
	</div>
</div>
</div>
</div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/scripts.js" ></script> 
<script>
$(document).ready(function () {
	autocomplete();
	});
</script>
<script>
var input = document.getElementById("nomfromsearchclient");
input.addEventListener("keyup", function(event) {
  if (event.keyCode === 13) {
   event.preventDefault();
   document.getElementById("btsearch").click();
  }
});
</script>
</body>
</html>