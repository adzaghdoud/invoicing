<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
<head>
<title>Invoicing</title>
<link rel = "icon" href =  "${pageContext.request.contextPath}/resources/images/logo.png" type = "image/x-icon">  
<style type="text/css">
  <%@include file="/resources/css/login.css" %>
</style>
<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
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
      <input type="submit" class="fadeIn fourth" value="Log In">
       <c:if test = "${not empty  erromsg}"> 
       <hr/>
       <div class="alert alert-danger" role="alert">
      <i class="fas fa-exclamation-triangle"></i> ${erromsg}
      </div>
      </c:if>
    </form>

    <!-- Remind Passowrd -->
    <div id="formFooter">
      <a class="underlineHover" href="#">Forgot Password?</a>
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
</body>
</html>