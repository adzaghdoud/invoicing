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

</style>
<body>
<div class="container" id="articlescontainer">
    <h3><i class="far fa-list-alt"></i>  Liste des Articles</h3>
    <hr>
    <div class="row">
        <div class="panel panel-primary filterable">
            <div class="panel-heading">
                <h3 class="panel-title">Articles</h3>
                <div class="pull-right">
                    <button class="btn btn-default btn-xs btn-filter"><span class="glyphicon glyphicon-filter"></span> Filter</button>
                </div>
             
                
            </div>
            <table  class="table" id="tablearticle">
                <thead>
                    <tr class="filters">
                        <th><input type="text" class="form-control" placeholder="Designation"  disabled></th>
                        <th><input type="text" class="form-control" placeholder="Famille" disabled></th>
                        <th><input type="text" class="form-control" placeholder="PV HT" disabled></th>
                        <th><input type="text" class="form-control" placeholder="PA HT" disabled></th>
						<th><input type="text" class="form-control" placeholder="TAXE" disabled></th>
						<th><input type="text" class="form-control" placeholder="VAL TAXE" disabled></th>
						<th><input type="text" class="form-control" placeholder="PV TTC" disabled></th>
						<th><input type="text" class="form-control" placeholder="Action" disabled></th>
                    </tr>
                </thead>
                <tbody>
            <c:forEach var="liste" items="${ Listarticles }">
            <tr>
            <td>${liste.designation}</td>
            <td>${liste.famille}</td>
            <td>${liste.pvht} <i class="fas fa-euro-sign"></i></td>
            <td>${liste.paht} <i class="fas fa-euro-sign"></i></td>
            <td>${liste.taxe}</td>
            <td>${liste.valtaxe} %</td>
            <td>${liste.pvttc} <i class="fas fa-euro-sign"></i></td>
            <td><a href="#" onclick="showupdatemodal()"><span style="color:green"><i class="far fa-edit" ></i></span></a> <a href="#" onclick="showmodaldelete()"><span style="color:red"><i class="far fa-trash-alt"></i></span></a></td>
            </c:forEach>
                </tbody>
            </table>
            
        </div>
           <div>
                   <button class="btn btn-success" onclick="javascript:$('#Modalnewarticle').modal()"><i class="fas fa-plus"></i> Nouveau Article </button>
                   <a href="DowloadProductsCSV"><button class="btn btn-secondary" ><i class="fas fa-file-download"></i> Download </button></a>
            </div>
            
          </div>
    </div>    
            
   <div class="modal fade" id="Modalnewarticle" tabindex="-1"  role="dialog" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h4 class="modal-title" id="titleModalnewarticle"> <span style="color:green">Nouveau Article</span></h4>
      </div>
      <div class="modal-body">  
                                 <div class="form-group row" >
                                 <label class="col-sm-4 col-form-label"><b>Designation</b></label>
                                 <div class="col-sm-6">
                                 <input type="text" id="designation" class="form-control" /> 
                                 </div>
                                 </div>
                                 <div class="form-group row" >
                                 <label class="col-sm-4 col-form-label"><b>Famille</b></label>
                                 <div class="col-sm-6">
                                 <input type="text" id="famille" class="form-control" /> 
                                 </div>
                                 </div>
                                 <div class="form-group row" >
                                 <label class="col-sm-4 col-form-label"><b>Prix vente HT</b></label>
                                 <div class="col-sm-6">
                                 <input type="text" id="pvht" class="form-control" /> 
                                 </div>
                                 </div>
                                 
                                 <div class="form-group row" >
                                 <label class="col-sm-4 col-form-label"><b>Prix achat HT</b></label>
                                 <div class="col-sm-6">
                                 <input type="text" id="paht" class="form-control" placeholder="0"/> 
                                 </div>
                                 </div>
                                 
                                 
                                 
                                 
                                 
                                 
                                 <div class="form-group row" >
                                 <label class="col-sm-4 col-form-label"><b>Taxe</b></label>
                                 <div class="col-sm-6">
                                 <select   id ="taxe" class="form-group">
                                 <option value=""></option>
                                 <option value="TVA">TVA </option>
                                 </select>
                                 </div>
                                 </div>
                                 
                                 <div class="form-group row" >
                                 <label class="col-sm-4 col-form-label"><b>Valeur Taxe %</b></label>
                                 <div class="col-sm-6">
                                 <input type="text" id="valtaxe" class="form-control" oninput="setttcfield()" placeholder="20"/> 
                                 </div>
                                 </div>
                                 
                                 <div class="form-group row" >
                                 <label class="col-sm-4 col-form-label"><b>Prix vente TTC</b></label>
                                 <div class="col-sm-6">
                                 <input type="text" id="pvttc" class="form-control" /> 
                                 </div>
                                 </div>
                                 
                                 
      
      </div>
       <div class="modal-footer">
        <button type="button" class="btn btn-success" onclick="createnewarticle()" id="buttonsave" >save</button>
        <button type="button" class="btn btn-primary" data-dismiss="modal">close</button>
       
      </div>
       <div class="loader" style="display:none;" id="loader">
                <p>Loading...</p>
                <div class="loader-inner"></div>
                <div class="loader-inner"></div>
                <div class="loader-inner"></div>
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



<div class="modal fade" id="Modalconfirmdelete" tabindex="-1" role="dialog"  aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="titlemodalconfirmdelete"> <span style='color: green;'>Confirmation</span> </h5>
      </div>
      <div class="modal-body">
       <b>Confirmer vous la suppression de l'article <span id="designation_article"></span> ?</b>
      </div>
        <div class="modal-footer">    
        <button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-success" data-dismiss="modal" onclick="suppression_article(document.getElementById('designation_article').innerHTML)">confirm</button>
      </div>
    </div>
  </div>
</div> 






  <div class="modal fade" id="Modalmodifyarticle" tabindex="-1" role="dialog" aria-labelledby="titlemodalmodifyarticle" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="titlemodalmodifyarticle"></h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
        <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
                                 <div class="form-group row" >
                                 <label class="col-sm-4 col-form-label"><b>Designation</b></label>
                                 <div class="col-sm-6">
                                 <input type="text" id="designationmodify" class="form-control"  readonly/> 
                                 </div>
                                 </div>
                                 
                                 <div class="form-group row" >
                                 <label class="col-sm-4 col-form-label"><b>Famille</b></label>
                                 <div class="col-sm-6">
                                 <input type="text" id="famillemodify" class="form-control" /> 
                                 </div>
                                 </div>
                                 
                                 <div class="form-group row" >
                                 <label class="col-sm-4 col-form-label"><b>PV HT</b></label>
                                 <div class="col-sm-6">
                                 <input type="text" id="pvhtmodify" class="form-control" /> 
                                 </div>
                                 </div>
                                 
                                 <div class="form-group row" >
                                 <label class="col-sm-4 col-form-label"><b>PA HT</b></label>
                                 <div class="col-sm-6">
                                 <input type="text" id="pahtmodify" class="form-control" /> 
                                 </div>
                                 </div>
                                 
                                 <div class="form-group row" >
                                 <label class="col-sm-4 col-form-label"><b>Taxe</b></label>
                                 <div class="col-sm-6">
                                 <input type="text" id="taxemodify" class="form-control" /> 
                                 </div>
                                 </div>

                                 <div class="form-group row" >
                                 <label class="col-sm-4 col-form-label"><b>Val TAXE </b></label>
                                 <div class="col-sm-6">
                                 <input type="text" id="valtaxemodify" class="form-control" /> 
                                 </div>
                                 </div>
                                 
                                 <div class="form-group row" >
                                 <label class="col-sm-4 col-form-label"><b>PV TTC </b></label>
                                 <div class="col-sm-6">
                                 <input type="text" id="pvttcmodify" class="form-control" /> 
                                 </div>
                                 </div>
                                 
   
                                                                                   
        </div>
        <div class="modal-footer">
        <button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-warning" onclick="saveupdatearticle()">save</button>
        
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