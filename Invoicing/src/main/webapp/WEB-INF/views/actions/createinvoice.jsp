<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"></script>
</head>
<body>
 <div class="card card-outline-secondary">
                        <div class="card-header">
                            <h5 class="mb-0">Création  nouvelle facture</h5>
                        </div>
                        <div class="card-body">
                              
                                
                                 <div class="form-group row">
                                 <label class="col-sm-2 col-form-label"><b>Client</b></label>
                                 <div class="col-sm-4">
                                 <select   id ="nomclient"  class="form-control">
                                 <option value=""></option>
                                 <c:forEach var="client" items="${ listeclients }">
                                 <option value="${client.rs}"> <c:out value="${client.rs}"/> </option>  
                                 </c:forEach>
                                 </select> 
                                 </div>
                                 </div>
                                 
                                 <div class="form-group row">
                                 <label class="col-sm-2 col-form-label"><b>Article</b></label>
                                 <div class="col-sm-4">
                                 <select   id ="article"  class="form-control" onchange="showinfoarticle(this.value)">
                                 <option value=""></option>
                                 <c:forEach var="article" items="${ listearticles }">
                                 <option value="${article.designation}"> <c:out value="${article.designation}"/> </option>  
                                 </c:forEach>
                                 </select> 
                                 </div>
                                 </div>  
                                        
                                 <div class="form-group row"  id="divfamille" >
                                 <label class="col-sm-2 col-form-label"><b>Famille</b></label>
                                 <div class="col-sm-4">
                                 <input type="text" id="famille" class="form-control"/> 
                                 </div>
                                 </div>
                                 
                                 <div class="form-group row" id="divprixHT">
                                 <label class="col-sm-2 col-form-label"><b>Prix HT</b></label>
                                 <div class="col-sm-4">
                                 <input type="text" id="prix_HT" class="form-control"/> 
                                 </div>
                                 </div>
                                  
                                 <div class="form-group row"  id="divtaxe">
                                 <label class="col-sm-2 col-form-label"><b>Taxe</b></label>
                                 <div class="col-sm-4">
                                 <input type="text" id="taxe" class="form-control"/> 
                                 </div>
                                 </div>
                                 
                                 <div class="form-group row" id="divvaltaxe">
                                 <label class="col-sm-2 col-form-label"><b>Valeur Taxe</b></label>
                                 <div class="col-sm-4">
                                 <input type="text" id="valtaxe" class="form-control"/> 
                                 </div>
                                 </div>

                                 <div class="form-group row" id="divprixttc">
                                 <label class="col-sm-2 col-form-label"><b>Prix TTC</b></label>
                                 <div class="col-sm-4">
                                 <input type="text" id="prixttc" class="form-control"/> 
                                 </div>
                                 </div>
                                
                                <div class="form-group row">
                                 <button class="btn btn-success" onclick="Generateinvoice()" disabled><i class="fas fa-file-invoice-dollar"></i> Générer facture <span class="spinner-border spinner-border-sm"  id="spinnerbutton" style="display:none;"></span> </button> 
                                 <div class="col-sm-1">
                                 <button class="btn btn-secondary">Effacer</button>
                                 </div>
                                 </div>
                                 
                                </div>
                                </div>
                           


   <div class="modal fade" id="Modalconfirmaddfournisseur" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="titleaddfournisseur"></h5>
       
      </div>
      <div class="modal-body">
      <span id="msgModaladdfournisseur"></span>
      </div>
      <div class="modal-footer">
       <button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
  </div>
<script src="${pageContext.request.contextPath}/resources/scripts/messageResource.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/scripts.js" ></script> 
</body>
</html>