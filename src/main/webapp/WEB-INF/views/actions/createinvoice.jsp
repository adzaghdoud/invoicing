<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/scripts/messageResource.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/scripts.js" ></script> 
<style><style>
.loader{
    width: 70px;
    height: 70px;
    margin: 40px auto;
}
.loader p{
    font-size: 16px;
    color: #777;
}
.loader .loader-inner{
    display: inline-block;
    width: 15px;
    border-radius: 15px;
    background: #74d2ba;
}
.loader .loader-inner:nth-last-child(1){
    -webkit-animation: loading 1.5s 1s infinite;
    animation: loading 1.5s 1s infinite;
}
.loader .loader-inner:nth-last-child(2){
    -webkit-animation: loading 1.5s .5s infinite;
    animation: loading 1.5s .5s infinite;
}
.loader .loader-inner:nth-last-child(3){
    -webkit-animation: loading 1.5s 0s infinite;
    animation: loading 1.5s 0s infinite;
}
@-webkit-keyframes loading{
    0%{
        height: 15px;
    }
    50%{
        height: 35px;
    }
    100%{
        height: 15px;
    }
}
@keyframes loading{
    0%{
        height: 15px;
    }
    50%{
        height: 35px;
    }
    100%{
        height: 15px;
    }
}
</style>

</head>
<body>
 <div class="card card-outline-secondary">
                        <div class="card-header">
                            <h5 class="mb-0"><i class="far fa-plus-square"></i> Création  nouvelle facture</h5>
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
                                 <select   id ="article"  class="form-control">
                                 <option value=""></option>
                                 <c:forEach var="article" items="${ listearticles }">
                                 <option value="${article.designation}"> <c:out value="${article.designation}"/> </option>  
                                 </c:forEach>
                                 </select> 
                                 </div>
                                 </div> 
                                 
                                 
                                 <div class="form-group row"   >
                                 <label class="col-sm-2 col-form-label"><b>Quantité</b></label>
                                 <div class="col-sm-4">
                                 <input type="number" id ="quantite" class="form-control" min="1" onchange="showinfoarticle(this.value,$('#article').val())"/>
                                 </div>
                                 </div>
                                 
                                 <div class="form-group row" >
                                 <label class="col-sm-2 col-form-label"><b>Famille</b></label>
                                 <div class="col-sm-4">
                                 <input type="text" id="famille" class="form-control" disabled/> 
                                 </div>
                                 </div>
                                  
                                        
                                 
                                 
                                 <div class="form-group row" >
                                 <label class="col-sm-2 col-form-label"><b>Prix HT <i class="fas fa-euro-sign"></i></b></label>
                                 <div class="col-sm-4">
                                 <input type="text" id="prix_HT" class="form-control" disabled/> 
                                 </div>
                                 </div>
                                  
                                 <div class="form-group row"  >
                                 <label class="col-sm-2 col-form-label"><b>Taxe</b></label>
                                 <div class="col-sm-4">
                                 <input type="text" id="taxe" class="form-control" disabled/> 
                                 </div>
                                 </div>
                                 
                                 <div class="form-group row" >
                                 <label class="col-sm-2 col-form-label"><b>Valeur Taxe</b></label>
                                 <div class="col-sm-4">
                                 <input type="text" id="valtaxe" class="form-control" disabled/> 
                                 </div>
                                 </div>

                                 <div class="form-group row" >
                                 <label class="col-sm-2 col-form-label"><b>Prix TTC <i class="fas fa-euro-sign"></i></b></label>
                                 <div class="col-sm-4">
                                 <input type="text" id="prixttc" class="form-control" disabled/> 
                                 </div>
                                 </div>
                                 
                                 <div class="form-group row" >
                                 <label class="col-sm-2 col-form-label"><b>Total prix TTC <i class="fas fa-euro-sign"></i></b></label>
                                 <div class="col-sm-4">
                                 <input type="text" id="totalprixttc" class="form-control" disabled/> 
                                 </div>
                                 </div>
                                 
                                 <div class="form-group row">
                                 <label class="col-sm-2 col-form-label"><b>Mode de paiement</b></label>
                                 <div class="col-sm-4">
                                 <select   id ="modepaiement"  class="form-control">
                                 <option value=""></option>          
                                 <option value="Virement">Virement</option>  
                                 <option value="chéque">Chéque</option> 
                                 </select> 
                                 </div>
                                 </div> 
                                    <div class="form-group row">
                                 <label class="col-sm-2 col-form-label"><b>Date dû</b></label>
                                 <div class="col-sm-4">
                                <div class="datepicker date input-group">
                              <input type="date" placeholder="Choisir une date" class="form-control" id="date_attendue" onchange="checkdate(this.value)">
                             <div class="input-group-append"><span class="input-group-text px-4"><i class="fa fa-calendar"></i></span></div>
                            </div>
                           </div>
                           </div>     
                                <div class="form-group row">
                                 <button class="btn btn-success" onclick="javascirpt:Generateinvoice()"  id="button_generate"disabled><i class="fas fa-file-invoice-dollar"></i> Générer facture <span class="spinner-border spinner-border-sm"  id="spinnerbutton" style="display:none;"></span> </button> 
                                 <div class="col-sm-1">
                                 <button class="btn btn-secondary" onclick="javascript:document.getElementById('nomclient').value = '';document.getElementById('article').value = '';document.getElementById('quantite').value = '';document.getElementById('famille').value = '';document.getElementById('prix_HT').value = '';document.getElementById('taxe').value = '';document.getElementById('valtaxe').value = '';document.getElementById('prixttc').value = '';document.getElementById('totalprixttc').value = '';document.getElementById('modepaiement').value = '';document.getElementById('date_attendue').value = ''">Effacer</button>
                                 </div>
                                 </div>
                                 
                                   
                                 
                                </div>
                                </div>
                           
    <div class="loader"  id="loader" style="text-align:center;display:none" >
                <p>Loading...</p>
                <div class="loader-inner"></div>
                <div class="loader-inner"></div>
                <div class="loader-inner"></div>
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
  
  
  
    <div class="modal fade" id="Modalnotify" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="titlemodal"></h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
       <span id="msgModalnotify"></span>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div> 
    
  
  
</body>
</html>