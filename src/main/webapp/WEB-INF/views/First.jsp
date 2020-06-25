<!DOCTYPE html>
<%@ page pageEncoding="UTF-8"%>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.css" />
<script src="http://code.jquery.com/jquery-2.0.1.min.js"></script>
<script
	src="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.js"></script>
<meta charset="utf-8" />
<title>First</title>
</head>

<body>
	<p>Ceci est une générée depuis une JSP.</p>

	<p>
	<% 
	String attribut = (String) request.getAttribute("sirop");
	out.println(attribut);
	
	%>
	</p>
</body>
</html>