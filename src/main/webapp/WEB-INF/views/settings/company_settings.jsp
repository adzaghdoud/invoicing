<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js"></script>
<style>
#cover-spin {
    position:fixed;
    width:100%;
    left:0;right:0;top:0;bottom:0;
    background-color: rgba(255,255,255,0.1);
    z-index:9999;
    display:none;
}

@-webkit-keyframes spin {
	from {-webkit-transform:rotate(0deg);}
	to {-webkit-transform:rotate(360deg);}
}

@keyframes spin {
	from {transform:rotate(0deg);}
	to {transform:rotate(360deg);}
}

#cover-spin::after {
    content:'';
    display:block;
    position:absolute;
    left:48%;top:40%;
    width:40px;height:40px;
    border-style:solid;
    border-color:black;
    border-top-color:transparent;
    border-width: 4px;
    border-radius:50%;
    -webkit-animation: spin .8s linear infinite;
    animation: spin .8s linear infinite;
}
</style>
</head>
<body>
<div id="cover-spin"></div>
<div class="container" id="containtersettings">
		<div class="main-body">
			<div class="row">
				<div class="col-lg-4">
					<div class="card">
						<div class="card-body">
							<div class="d-flex flex-column align-items-center text-center">
								<c:choose>
								<c:when test="${empty encodedimage}">
                                <img src="https://bootdey.com/img/Content/avatar/avatar7.png">
                                </c:when>
                                <c:otherwise>
                                <img src="data:image/jpg;base64,${encodedimage}" style="width:70%;height:70%">
                                </c:otherwise>
                                </c:choose>	                    
			                    <input type="file" id="imgupload" style="display:none" accept="image/*"/> 
			                    <button type="button" id="buttonmodifyimage"  class="btn btn-warning"><i class="fas fa-user-cog"></i> Update Logo</button>
							</div>
							<hr class="my-4">
						
						</div>
					</div>
				</div>
				<div class="col-lg-8">
					<div class="card">
						<div class="card-body">
							<div class="row mb-3">
								<div class="col-sm-3">
									<h6 class="mb-0"><b>Raison Sociale</b></h6>
								</div>
								<div class="col-sm-9 text-secondary">
									<input type="text" id="raisonsociale" class="form-control" value="${info.rs}" disabled/>
								</div>
							</div>
							<div class="row mb-3">
								<div class="col-sm-3">
									<h6 class="mb-0"><b>Siret</b></h6>
								</div>
								<div class="col-sm-9 text-secondary">
									<input type="text" id="siret" class="form-control" value="${info.siret}" disabled >
								</div>
							</div>
							
							<div class="row mb-3">
								<div class="col-sm-3">
									<h6 class="mb-0"><b>Num TVA</b></h6>
								</div>
								<div class="col-sm-9 text-secondary">
									<input type="text" id="numtva" class="form-control" value="${info.numtva}" disabled >
								</div>
							</div>
							
						     <div class="row mb-3">
								<div class="col-sm-3">
									<h6 class="mb-0"><b>Date clôture exercice</b></h6>
								</div>
								<div class="col-sm-9 text-secondary">
									<input type="text" id="datecloture" class="form-control" value="${info.date_cloture_comptable}" disabled >
								</div>
							</div>
							
							
							<div class="row mb-3">
								<div class="col-sm-3">
									<h6 class="mb-0"><b>RIB</b></h6>
								</div>
								<div class="col-sm-9 text-secondary">
									<input type="text" id="rib" class="form-control" value="${info.rib}" disabled/>
								</div>
							</div>
							<div class="row mb-3">
								<div class="col-sm-3">
									<h6 class="mb-0"><b>Adresse</b></h6>
								</div>
								<div class="col-sm-9 text-secondary">
									<input type="text" id="adresse" class="form-control" value="${info.adresse}" disabled/>
								</div>
							</div>
							<div class="row mb-3">
								<div class="col-sm-3">
									<h6 class="mb-0"><b>Ville</b></h6>
								</div>
								<div class="col-sm-9 text-secondary">
									<input type="text" id="ville" class="form-control" value="${info.ville}" disabled/>
								</div>
							</div>
							
								<div class="row mb-3">
								<div class="col-sm-3">
									<h6 class="mb-0"><b>Code postale</b></h6>
								</div>
								<div class="col-sm-9 text-secondary">
									<input type="text" id="cp" class="form-control" value="${info.cp}" disabled/>
								</div>
							</div>
							
							
							
								<div class="row mb-3">
								<div class="col-sm-3">
									<h6 class="mb-0"><b>Bankname</b></h6>
								</div>
								<div class="col-sm-9 text-secondary">
									<input type="text" id="bankname" class="form-control" value="${info.bankname}" disabled/>
								</div>
							</div>
							
							
								<div class="row mb-3">
								<div class="col-sm-3">
									<h6 class="mb-0"><b>Slug</b></h6>
								</div>
								<div class="col-sm-9 text-secondary">
									<input type="text" id="slug" class="form-control" value="${info.slug}" disabled/>
								</div>
							</div>
							
							
								<div class="row mb-3">
								<div class="col-sm-3">
									<h6 class="mb-0"><b>Token</b></h6>
								</div>
								<div class="col-sm-9 text-secondary">
									<input type="text" id="token" class="form-control" value="${info.token}" disabled/>
								</div>
							</div>
							
							
							<div class="row mb-3">
							<div class="col-sm-3">
						    <h6 class="mb-0"><b>KBIS</b></h6>
							</div>
						    <div class="col-sm-9 text-secondary">
						    <a href="#" onclick="downloadkbis()" >${info.kbis_file_name}</a>
							</div>
							</div>
							<c:set var = "declaration" value = "${info.declaration_tva}"/>
							<c:if test = "${fn:contains(declaration, 'trimestrielle')}">
							<div class="row mb-3">
							<div class="col-sm-3">
						    <h6 class="mb-0"><b>Déclaration TVA</b></h6>
							</div>
							<div class="form-check">
                            
                            <input class="form-check-input" type="checkbox" value="" id="trimestrielle" checked >
                            
                            <label class="form-check-label" for="flexCheckDefault">
                            Trimestrielle  
                            </label>
                             </div>

                             <div class="form-check">
                             
                             <input class="form-check-input" type="checkbox" value="" id="semestrielle"  >
                           
                             <label class="form-check-label" for="flexCheckChecked">
                             Semestrielle
                             </label>
                             </div> 
                             
                             </div>
							</c:if>
							
							
							
							<c:if test = "${fn:contains(declaration, 'semestrielle')}">
							<div class="row mb-3">
							<div class="col-sm-3">
						    <h6 class="mb-0"><b>Déclaration TVA</b></h6>
							</div>
							<div class="form-check">
                            
                            <input class="form-check-input" type="checkbox" value="" id="trimestrielle"  >
                            
                            <label class="form-check-label" for="flexCheckDefault">
                            Trimestrielle  
                            </label>
                             </div>

                             <div class="form-check">
                             
                             <input class="form-check-input" type="checkbox" value="" id="semestrielle"  checked>
                           
                             <label class="form-check-label" for="flexCheckChecked">
                             Semestrielle
                             </label>
                             </div> 
                             
                             </div>
							</c:if>
							
							
							
							<div class="row">
								<div class="col-sm-3"></div>
								<div class="col-sm-9 text-secondary">
									<button class="btn btn-success" onclick="showmodalcompany()" ><i class="fas fa-edit"></i> Modifier <span class="spinner-border spinner-border-sm"  id="spinnerbutton" style="display:none;"></span> </button>
								</div>
							</div>
						</div>
					</div>
			
				</div>
			</div>
		</div>
	</div>
	
	 <div class="modal fade" id="Modalmodifclient" tabindex="-1"  aria-labelledby="titleModalmodifclient" role="dialog" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="titleModalmodifclient"> <span style="color:green">Informations à modifier</span></h5>
      </div>
      <div class="modal-body">  
                                 <div class="form-group row" >
                                 <label class="col-sm-3 col-form-label"><b>Raison Sociale</b></label><a href="#" onclick="javascript:document.getElementById('raisonsocialemodal').removeAttribute('disabled');"><span style="color:blue"><i class="far fa-edit"></i></span></a>
                                 <div class="col-sm-8">
                                 <input type="text" id="raisonsocialemodal" class="form-control" disabled/> 
                                 </div>
                                 </div>
                                 
                                 
                                 <div class="form-group row" >
                                 <label class="col-sm-3 col-form-label"><b>Siret</b></label><a href="#" onclick="javascript:document.getElementById('siretmodal').removeAttribute('disabled');"><span style="color:blue"><i class="far fa-edit"></i></span></a>
                                 <div class="col-sm-8">
                                 <input type="text" id="siretmodal" class="form-control" disabled/> 
                                 </div>
                                 </div>
                                 
                                  <div class="form-group row" >
                                 <label class="col-sm-3 col-form-label"><b>Num TVA</b></label><a href="#" onclick="javascript:document.getElementById('numtvamodal').removeAttribute('disabled');"><span style="color:blue"><i class="far fa-edit"></i></span></a>
                                 <div class="col-sm-8">
                                 <input type="text" id="numtvamodal" class="form-control" disabled/> 
                                 </div>
                                 </div>
                                 
                                 
                                 
                                  <div class="form-group row" >
                                 <label class="col-sm-3 col-form-label"><b>RIB</b></label><a href="#" onclick="javascript:document.getElementById('ribmodal').removeAttribute('disabled');"><span style="color:blue"><i class="far fa-edit"></i></span></a>
                                 <div class="col-sm-8">
                                 <input type="text" id="ribmodal" class="form-control" disabled/> 
                                 </div>
                                 </div>
                                 
                                 <div class="form-group row" >
                                 <label class="col-sm-3 col-form-label"><b>Adresse</b></label><a href="#" onclick="javascript:document.getElementById('adressemodal').removeAttribute('disabled');"><span style="color:blue"><i class="far fa-edit"></i></span></a>
                                 <div class="col-sm-8">
                                 <input type="text" id="adressemodal" class="form-control" disabled/> 
                                 </div>
                                 </div>
                                 
                                 <div class="form-group row" >
                                 <label class="col-sm-3 col-form-label"><b>Ville</b></label><a href="#" onclick="javascript:document.getElementById('villemodal').removeAttribute('disabled');"><span style="color:blue"><i class="far fa-edit"></i></span></a>
                                 <div class="col-sm-8">
                                 <input type="text" id="villemodal" class="form-control" disabled/> 
                                 </div>
                                 </div>
                                 
                                 <div class="form-group row" >
                                 <label class="col-sm-3 col-form-label"><b>Code postale</b></label><a href="#" onclick="javascript:document.getElementById('cpmodal').removeAttribute('disabled');"><span style="color:blue"><i class="far fa-edit"></i></span></a>
                                 <div class="col-sm-8">
                                 <input type="text" id="cpmodal" class="form-control"disabled/> 
                                 </div>
                                 </div>
                                 
                                 <div class="form-group row" >
                                 <label class="col-sm-3 col-form-label"><b>Bankname</b></label><a href="#" onclick="javascript:document.getElementById('bankenamemodal').removeAttribute('disabled');"><span style="color:blue"><i class="far fa-edit"></i></span></a>
                                 <div class="col-sm-8">
                                 <input type="text" id="bankenamemodal" class="form-control" disabled/> 
                                 </div>
                                 </div>
                                 
                                 <div class="form-group row" >
                                 <label class="col-sm-3 col-form-label"><b>Slug</b></label><a href="#" onclick="javascript:document.getElementById('slugmodal').removeAttribute('disabled');"><span style="color:blue"><i class="far fa-edit"></i></span></a>
                                 <div class="col-sm-8">
                                 <input type="text" id="slugmodal" class="form-control" disabled/> 
                                 </div>
                                 </div>
                                 
                                 
                                 <div class="form-group row" >
                                 <label class="col-sm-3 col-form-label"><b>Token</b></label><a href="#" onclick="javascript:document.getElementById('tokenmodal').removeAttribute('disabled');"><span style="color:blue"><i class="far fa-edit"></i></span></a>
                                 <div class="col-sm-8">
                                 <input type="text" id="tokenmodal" class="form-control" disabled/> 
                                 </div>
                                 </div>
      
      </div>
       <div class="modal-footer">
        <button type="button" class="btn btn-primary" onclick="savecompanymodify()">save <span class="spinner-border spinner-border-sm"  id="spinnerbutton" > </span></button>
        <button type="button" class="btn btn-secondary" data-dismiss="modal">close</button>
      </div>
     
      <div id="cover-spin"></div>
      
    </div>
  </div>
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

<script>
$('#buttonmodifyimage').click(function(){ $('#imgupload').trigger('click'); 
});
$("#imgupload").change(function(){
changeavatar(document.getElementById('imgupload').files[0],"logo") ; 
});

$('input[type="checkbox"]').on('change', function() {
	   $('input[type="checkbox"]').not(this).prop('checked', false);
	});

</script>


<script src="${pageContext.request.contextPath}/resources/scripts/messageResource.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/scripts.js" ></script> 
</body>
</html>