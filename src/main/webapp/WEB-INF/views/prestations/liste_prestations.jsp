<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="//netdna.bootstrapcdn.com/bootstrap/3.1.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.0/js/bootstrap.min.js"></script>
<script src="https://kit.fontawesome.com/b16c365929.js"></script>
<style>
.filterable {
    margin-top: 15px;
}
.filterable .panel-heading .pull-right {
    margin-top: -20px;
}
.filterable .filters input[disabled] {
    background-color: transparent;
    border: none;
    cursor: auto;
    box-shadow: none;
    padding: 0;
    height: auto;
}
.filterable .filters input[disabled]::-webkit-input-placeholder {
    color: #333;
}
.filterable .filters input[disabled]::-moz-placeholder {
    color: #333;
}
.filterable .filters input[disabled]:-ms-input-placeholder {
    color: #333;
}
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
<body>
  <div id="cover-spin"></div>
<div class="container" id="PrestationContainer">
    <h3><i class="fas fa-list"></i> Liste des prestations</h3>
    <hr>
    <div class="row">
        <div class="panel panel-primary filterable">
            <div class="panel-heading">
                <h3 class="panel-title">Prestations</h3> 
                <div class="pull-right">
                    <a href="DownloadPrestations"><button type="button" class="btn btn-success btn-xs"><i class="fas fa-file-download"></i> Download CSV</button></a>
                    <button class="btn btn-default btn-xs btn-filter"><span class="glyphicon glyphicon-filter"></span> Filter</button>
                </div>
             
                
            </div>
            <table class="table table-striped" id="Prestation_table">
                <thead>
                    <tr class="filters">
                        <th><input type="text" class="form-control" placeholder="Article" disabled></th>
                        <th><input type="text" class="form-control" placeholder="Nom Facture" disabled></th>
                        <th><input type="text" class="form-control" placeholder="Paiement" disabled></th>
                        <th><input type="text" class="form-control" placeholder="Date" disabled></th>
                        <th><input type="text" class="form-control" placeholder="Quantité" disabled></th>
                        <th><input type="text" class="form-control" placeholder="PU HT" disabled></th>
                        <th><input type="text" class="form-control" placeholder="Taxe" disabled></th>
						<th><input type="text" class="form-control" placeholder="Val Taxe" disabled></th>
                        <th><input type="text" class="form-control" placeholder="PU TTC" disabled></th>
						<th><input type="text" class="form-control" placeholder="Total TTC" disabled></th>
						<th>Action</th>
                    </tr>
                </thead>
                <tbody>
                              <c:forEach var="prestation" items="${ Liste_prestations }">
            <tr>
            <td>${prestation.article}</td>
            <td>${prestation.nomfacture}</td>
            <c:if test = "${prestation.statut_paiement == 'validé'}"> 
            <td><span class="label label-success">Validé</span>  </td>
            </c:if>
             <c:if test = "${prestation.statut_paiement == 'en attente'}"> 
            <td><span class="label label-warning">En attente</span> </td>
            </c:if>
            <td>${prestation.date}</td>
            <td>${prestation.quantite}</td>
            <td>${prestation.montantHT}</td>
            <td>${prestation.taxe}</td>
            <td>${prestation.valtaxe} %</td>
            <td>${prestation.montantTTC}</td>
            <td>${prestation.totalttc}</td>
            <td><a href="#" onclick="javascript:Generate_post_invoice()"><span style='color: green;'><i class="far fa-file-pdf"></i></span></a>
            <a href="#" onclick="javascript:UpdatePrestation()"><span style='color: orange;'><i class="fas fa-edit"></i></span></a>
            </td>
            </tr>
            </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    </div>
    
    
    
  <div class="modal fade" id="ModalmodifyPrestation" tabindex="-1" role="dialog"  aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h4 class="modal-title" id="titlemodalmodifyPrestation"></h4>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
        <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
                                 <div class="form-group row" >
                                 <label class="col-sm-4 col-form-label"><b>Article</b></label>
                                 <div class="col-sm-8">
                                 <input type="text" id="PrestationModifyArticle" class="form-control" disabled/> 
                                 </div>
                                 </div>
                                 
                                 <div class="form-group row" >
                                 <label class="col-sm-4 col-form-label"><b>Nom Facture</b></label>
                                 <div class="col-sm-8">
                                 <input type="text" id="PrestationModifyNomfacture" class="form-control"  disabled/> 
                                 </div>
                                 </div>
                                 
                                 <div class="form-group row" >
                                 <label class="col-sm-4 col-form-label"><b>Paiement</b></label>
                                 <div class="col-sm-8">
                                 <select   id ="PrestationModifyPaiement"  class="form-control btn btn-default dropdown-toggle" style="height:40px">
                                 <option>validé</option>
                                 <option>en attente</option>
                                 </select>              
                                 </div>
                                 </div>
                                       
                                 <div class="form-group row" >
                                 <label class="col-sm-4 col-form-label"><b>Quantité</b></label>
                                 <div class="col-sm-8">
                                 <input type="text" id="PrestationModifyQuantite" class="form-control" /> 
                                 </div>
                                 </div>

                                 <div class="form-group row" >
                                 <label class="col-sm-4 col-form-label"><b>PU HT </b></label>
                                 <div class="col-sm-8">
                                 <input type="text" id="PrestationModifyMontantht" class="form-control" /> 
                                 </div>
                                 </div>
                                 
                                 <div class="form-group row" >
                                 <label class="col-sm-4 col-form-label"><b>Taxe</b></label>
                                 <div class="col-sm-8">
                                 <input type="text" id="PrestationModifyTaxe" class="form-control" /> 
                                 </div>
                                 </div>
                                 
                                 <div class="form-group row" >
                                 <label class="col-sm-4 col-form-label"><b>Val Taxe</b></label>
                                 <div class="col-sm-8">
                                 <input type="text" id="PrestationModifyValtaxe" class="form-control" /> 
                                 </div>
                                 </div>
                                 
                                 <div class="form-group row" >
                                 <label class="col-sm-4 col-form-label"><b>PU TTC</b></label>
                                 <div class="col-sm-8">
                                 <input type="text" id="PrestationModifyMontantttc" class="form-control" /> 
                                 </div>
                                 </div>
                                 
                                 
                                   
                                 <div class="form-group row" >
                                 <label class="col-sm-4 col-form-label"><b>Total TTC</b></label>
                                 <div class="col-sm-8">
                                 <input type="text" id="PrestationModifyTotalttc" class="form-control" /> 
                                 </div>
                                 </div>
   
                                                                                   
        </div>
        <div class="modal-footer">
        <button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-warning" onclick="DeletePrestation($('#PrestationModifyNomfacture').val())">Delete</button>
        <button type="button" class="btn btn-success" onclick="ModifyPrestation()">Modify</button>
         </div> 
      <div class="alert alert-success" role="alert" style="display:none;" id="alertokPres">
      <span class="alert-icon"><span class="sr-only">Success</span></span>
      <span id="msgokPres"></span>
      </div>
    
      <div class="alert alert-danger" role="alert" id="alertkoPres" style="display:none;">
      <span class="alert-icon"><span class="sr-only">Danger</span></span>
     <span id="msgkoPres"></span>
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
    
    
    
    
    <script>
    /*
    Please consider that the JS part isn't production ready at all, I just code it to show the concept of merging filters and titles together !
    */
    $(document).ready(function(){
        $('.filterable .btn-filter').click(function(){
            var $panel = $(this).parents('.filterable'),
            $filters = $panel.find('.filters input'),
            $tbody = $panel.find('.table tbody');
            if ($filters.prop('disabled') == true) {
                $filters.prop('disabled', false);
                $filters.first().focus();
            } else {
                $filters.val('').prop('disabled', true);
                $tbody.find('.no-result').remove();
                $tbody.find('tr').show();
            }
        });

        $('.filterable .filters input').keyup(function(e){
            /* Ignore tab key */
            var code = e.keyCode || e.which;
            if (code == '9') return;
            /* Useful DOM data and selectors */
            var $input = $(this),
            inputContent = $input.val().toLowerCase(),
            $panel = $input.parents('.filterable'),
            column = $panel.find('.filters th').index($input.parents('th')),
            $table = $panel.find('.table'),
            $rows = $table.find('tbody tr');
            /* Dirtiest filter function ever ;) */
            var $filteredRows = $rows.filter(function(){
                var value = $(this).find('td').eq(column).text().toLowerCase();
                return value.indexOf(inputContent) === -1;
            });
            /* Clean previous no-result if exist */
            $table.find('tbody .no-result').remove();
            /* Show all rows, hide filtered ones (never do that outside of a demo ! xD) */
            $rows.show();
            $filteredRows.hide();
            /* Prepend no-result row if all rows are filtered */
            if ($filteredRows.length === $rows.length) {
                $table.find('tbody').prepend($('<tr class="no-result text-center"><td colspan="'+ $table.find('.filters th').length +'">No result found</td></tr>'));
            }
        });
    });
    </script>
    
</body>
</html>