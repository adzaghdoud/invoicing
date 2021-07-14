<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.6.0/css/bootstrap.min.css" integrity="sha512-P5MgMn1jBN01asBgU0z60Qk4QxiXo86+wlFahKrsQf37c9cro517WzVSPPV1tDKzhku2iJ2FVgL67wG03SGnNA==" crossorigin="anonymous" referrerpolicy="no-referrer" />
<link rel="stylesheet" href="https://cdn.datatables.net/1.10.25/css/dataTables.bootstrap4.min.css" >
<link rel="stylesheet" href="https://cdn.datatables.net/responsive/2.2.9/css/responsive.bootstrap4.min.css" >   
   
<body>
<div class="card card-outline-secondary">
                        <div class="card-header">
                            <h5 class="mb-0">Liste des Articles</h5>
                        </div>
                        <div class="card-body">
                                <div class="form-group row">
<div class="container">
<table id="example" class="table table-striped table-bordered nowrap" style="width:100%">
                <thead>
            <tr>
                <th>Designation</th>
                <th>Famille</th>
                <th>PV HT</th>
                <th>PA HT</th>
                <th>TAXE</th>
                <th>VAL</th>
                <th>PV TTC</th>
            </tr>
           </thead>
            <tbody>
            <c:forEach var="liste" items="${ Listarticles }">
            <tr>
            <td>${liste.designation}</td>
            <td>${liste.famille}</td>
            <td>${liste.pvht}</td>
            <td>${liste.paht}</td>
            <td>${liste.taxe}</td>
            <td>${liste.valtaxe} %</td>
            <td>${liste.pvttc}</td>
            </c:forEach>
            </tbody>
    </table>
            </div>
            </div>
            </div>
            </div>
<script src="https://code.jquery.com/jquery-3.5.1.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script src="https://cdn.datatables.net/1.10.25/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/1.10.25/js/dataTables.bootstrap.min.js"></script>
<script src="https://cdn.datatables.net/fixedheader/3.1.9/js/dataTables.fixedHeader.min.js"></script>   
<script src="https://cdn.datatables.net/responsive/2.2.9/js/dataTables.responsive.min.js"></script>   
<script src="https://cdn.datatables.net/responsive/2.2.9/js/responsive.bootstrap.min.js"></script>   
<script>
$(document).ready(function() {
    $('#example').DataTable( {
        responsive: {
            details: {
                display: $.fn.dataTable.Responsive.display.modal( {
                    header: function ( row ) {
                        var data = row.data();
                        return 'Details for '+data[0]+' '+data[1];
                    }
                } ),
                renderer: $.fn.dataTable.Responsive.renderer.tableAll( {
                    tableClass: 'table'
                } )
            }
        }
    } );
} );
</script>
<body>
</body>
</html>