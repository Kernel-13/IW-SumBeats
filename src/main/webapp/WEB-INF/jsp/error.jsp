<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>

<html>
<head>
	<%@ include file="../jspf/head.jspf" %>
	<link rel="stylesheet" type="text/css" href="static/css/session-style.css">
</head>
<body>
	<%@ include file="../jspf/navbar.jspf" %>

	<div class="container login">
		<div class="row" style="text-align: center;">
			<div class="col-lg-12">
				<h2>La pagina a la que intentas acceder no existe!</h2>
				<h3>Si has intentado registrarte, puede que el nombre o email ya esten registrados. Prueba con otros</h3>
				<h3>Si estas intentando acceder a un proyecto, asegurate de que exista</h3>
				<h3>Si estas intentando ver el perfil de un usuario, asegurate de que este registrado</h3>
			</div>
		</div>			
	</div>
</body>
</html>