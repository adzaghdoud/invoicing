<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
  <%@include file="/resources/css/main.css" %>
</style>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<script type="text/javascript"  src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>
<script type="text/javascript"  src="https://cdn.datatables.net/1.10.12/js/dataTables.bootstrap.min.js"></script>
<script type="text/javascript"  src="https://cdn.datatables.net/fixedheader/3.1.9/js/dataTables.fixedHeader.min.js"></script>
<script type="text/javascript"  src="https://cdn.datatables.net/responsive/2.2.9/js/dataTables.responsive.min.js"></script>
<script type="text/javascript"  src="https://cdn.datatables.net/responsive/2.2.9/js/responsive.bootstrap4.min.js"></script>
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
    </div>
 
    <i class="fa fa-bars fa-2x toggle-btn" data-toggle="collapse" data-target="#menu-content"></i>
  
        <div class="menu-list">
  
            <ul id="menu-content" class="menu-content collapse out">
                <li>
                  <a href="#">
                  <i class="fa fa-dashboard fa-lg"></i> Dashboard </a>        
                  </li>

                <li  data-toggle="collapse" data-target="#clients" class="collapsed">
                  <a href="#" aria-controls="v-pills-home"><i class="fas fa-users"></i> Clients <span class="arrow"></span></a>
                
                <ul class="sub-menu collapse" id="clients">
                    <li><a  class="link"href="clients" id="submenuclient">Liste clients</a></li>
                </ul>
                </li>


                <li data-toggle="collapse" data-target="#articles" class="collapsed">
                  <a href="#"><i class="fa fa-globe fa-lg"></i> Articles <span class="arrow"></span></a>
                 
                <ul class="sub-menu collapse" id="articles">
                  <li ><a  class="link"href="articles">Liste Articles</a></li>
                </ul>
                </li> 

                <li data-toggle="collapse" data-target="#new" class="collapsed">
                  <a href="#"><i class="fas fa-chart-area"></i> Reporting <span class="arrow"></span></a>
                
                <ul class="sub-menu collapse" id="new">
                  <li>Liste de prestations</li>
                  <li >Chiffre d'affaire</li>
                </ul>
                </li>
                 <li>
                  <a href="#">
                  <i class="fa fa-user fa-lg"></i> Profile
                  </a>
                  </li>

                 <li data-toggle="collapse" data-target="#action" class="collapsed">
                  <a href="#"><i class="far fa-plus-square"></i> Actions <span class="arrow"></span></a>
                
                <ul class="sub-menu collapse" id="action">
                  <li>Relancer une facture</li>
                  <li><a class="link"href="newinvoice">Créer une facture</a></li>
                </ul>
                </li>

            </ul>
     </div>
     </div>
     <div id="page-content-wrapper" class="containside">
     </div>
     <script type="text/javascript">  
     $('.link').on('click', function (e) {
    	 e.preventDefault();
         var page = $(this).attr('href');
         $('#page-content-wrapper').load(page);
});
     </script>
</body>
</html>