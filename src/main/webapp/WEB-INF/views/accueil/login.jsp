<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Invoicing</title>
<link rel = "icon" href =  "${pageContext.request.contextPath}/resources/images/logo.png" type = "image/x-icon">  
<style type="text/css">
  <%@include file="/resources/css/login.css" %>
</style>

<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<script src="https://kit.fontawesome.com/b16c365929.js"></script>
</head>
<body>
<div class="wrapper fadeInDown">
  <div id="formContent">
    <!-- Tabs Titles -->

    <!-- Icon -->
    <div class="fadeIn first">
     <img src="${pageContext.request.contextPath}/resources/images/logo.png">
    </div>
    
    
    <!-- Login Form -->
    <form action="login" method="post">
      <input type="text" name="login" class="fadeIn second" placeholder="login">
      <input type="password" name="password" class="fadeIn third"  placeholder="password">
      
      <button  class="fadeIn fourth" onclick="javascript:$('#refresh_gif').show()">Log In <img src="${pageContext.request.contextPath}/resources/images/icon_refresh.gif" width="25" height="25" style="display: none" id="refresh_gif" ></button>
       <c:if test = "${not empty  erromsg}"> 
       <hr/>
       <div class="alert alert-danger" role="alert">
      <i class="fas fa-exclamation-triangle"></i> ${erromsg}
      </div>
      </c:if>
    </form>

    <!-- Remind Passowrd -->
    <div id="formFooter">
      <a class="underlineHover" href="#" onclick="javascript:$('#pwdModal').modal()"><i class="fas fa-unlock-alt"></i> Forgot Password?</a>
        <%
        java.util.Properties prop = new java.util.Properties();
        prop.load(getServletContext().getResourceAsStream("/META-INF/MANIFEST.MF"));
        String applVersion = prop.getProperty("Implementation-Version");  
        String applName = prop.getProperty("Implementation-Title"); 
        %>
        <br/>
        <span>&copy; 2021 <%=applName%> <%=applVersion%></span>
    </div>
  </div>
</div>

  <!--modal-->
<div id="pwdModal" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true">
  <div class="modal-dialog">
  <div class="modal-content">
      <div class="modal-header">
          <h4 class="text-center"><i class="fas fa-lock"></i> Reset password</h4>
      </div>
      <div class="modal-body">
          <div class="col-md-12">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div class="text-center">
                          
                          <p>If you have forgotten your password you can reset it here.</p>
                            <div class="panel-body">
                                <fieldset>
                                    <div class="form-group">
                                    <input class="form-control" placeholder="E-mail Address" id="email" type="email">
                                    </div>
                                    <button class="btn btn-lg btn-success btn-block" onclick="gettempopassword($('#email').val())">Send My Password <img src="${pageContext.request.contextPath}/resources/images/icon_refresh.gif" width="25" height="25" style="display: none" id="refresh_gif_send_tempo" ></button>
                                </fieldset>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
      </div>
      <div class="modal-footer">
          <div class="col-md-12">
          <button class="btn" data-dismiss="modal" aria-hidden="true" onclick="$('#email').val('')">Cancel</button>
		  </div>	
      </div>
  </div>
  </div>
</div>
<script>
function gettempopassword(email){
	$("#refresh_gif_send_tempo").show();
	$.ajax({
	        url: "checkemail/"+email,
	        type: 'POST',
	        async: false,
	        processData: false,
	        contentType: false,
	        success: function (response) {
		    if(! response) {
		    document.getElementById("email").style.color = 'red';
		    }else {
		    	document.getElementById("email").style.color = 'green';
		    	
		    	$.ajax({
			        url: "GenerateTempoPassword/"+email,
			        type: 'POST',
			        async: false,
			        processData: false,
			        contentType: false,
			        success: function (response) {
			        	$("#refresh_gif_send_tempo").hide();	
			        }
		    	
		    	});
		    	
		    	
		    }
	        },
            error: function() {
            }
});
}
</script>
</body>
</html>