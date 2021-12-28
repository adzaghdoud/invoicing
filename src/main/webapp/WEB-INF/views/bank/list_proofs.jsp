<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
<div class="container" id="container_proofs">
    <h3><span style="color:green"><i class="fas fa-receipt"></i> Liste des Justificatifs</span></h3>
    <hr>
    <div class="row">
        <div class="panel panel-primary filterable">
            <div class="panel-heading">
                <h3 class="panel-title">Transactions/Prestations</h3>
                <div class="pull-right">
                    <button class="btn btn-default btn-xs btn-filter"><span class="glyphicon glyphicon-filter"></span> Filter</button>
                </div>
             
                
            </div>
            <table class="table table-striped" id="Proofs_Table">
                <thead>
                    <tr class="filters">
                        <th><input type="text" class="form-control" placeholder="Date Transaction" disabled></th>
                        <th><input type="text" class="form-control" placeholder="Label" disabled></th>
                        <th><input type="text" class="form-control" placeholder="Référence" disabled></th>
                        <th><input type="text" class="form-control" placeholder="Montant" disabled></th>
                        <th><input type="text" class="form-control" placeholder="Type" disabled></th>
                        <th><input type="text" class="form-control" placeholder="Justificatif" disabled></th>
                        <th><input type="text" class="form-control" placeholder="Action" disabled></th>
                        
                    </tr>
                </thead>
                <tbody>
                  <c:forEach var="liste" items="${ list_proofs }">
                  <tr> 
                   <c:set var="date" value="${liste.settled_at}"/>
                   <c:set var="datetronc" value="${fn:substring(date, 0, 10)}" />
                   <c:set var="yeartronc" value="${fn:substring(date, 0, 4)}" />
                  <td> <c:out value="${datetronc}"/></td>
                  <td> ${liste.label}</td>
                  <td> ${liste.reference}</td>
                  <td> ${liste.amount}</td>
                  <td> ${liste.side}</td>
                  <td> <a href="Download_From_Amazone_AS_RE/${company}/PROOF/<c:out value="${yeartronc}"/>/${liste.proof_filename}"><span style="color:green">${liste.proof_filename}</span></a></td>               
                  <td><a href="#" onclick="UploadNewProof()"><span style="color:green"><i class="far fa-edit"></i> </span></a> <a href="#"onclick="javascript:Deleteproof()"><span style="color:red"><i class="far fa-trash-alt"></i></span></a></td>
                   </tr>
                   
                   </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    </div>
    <script>
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
   

  <div class="modal fade" id="modalupdatetransaction" tabindex="-1" role="dialog" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title"> <span style="color:green"><i class="fas fa-envelope-open-text"></i> Nouveau Justificatif</span></h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
      <div class="form-group row">
      <label class="col-sm-4 col-form-label"><b>Justificatif</b></label>
      <div class="col-sm-8">
      <input type="file"  id ="proof"  class="form-control"  multiple>  
      </div>
      </div>                                
      </div>
      <div class="modal-footer">
      <button type="button" id="button_submit" class="btn btn-success" onclick="UploadNewProof()">Upload</button>
      <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
      </div>  
      </div>
      </div>
      </div>
    </body>
    </html>