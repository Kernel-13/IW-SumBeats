<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>

<html>
<head>
	<%@ include file="../jspf/head.jspf" %>
	<link rel="stylesheet" type="text/css" href="static/css/session-style.css">
</head>
<body>
	<%@ include file="../jspf/navbar.jspf" %>

	<div class="container-fluid login">
		<div class="row form-class">
			<div class="col-lg-12">
				<%@ include file="../jspf/authinfo.jspf"%>
				<div class="panel panel-info">
					<div class="panel-heading">
						Fin de Sesión
					</div>
					<div class="panel-body">
						<form class="form-horizontal" action="/login" method="post">
							Pulsa el botón para cerrar tu sesión actual
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
							<div class="form-group"> 
								<div class="col-sm-12 submitButton">
									<input type="submit" class="btn btn-success" value="Cerrar Sesión">
								</div>
							</div>
						</form>
					</div>
				</div>	
			</div>
		</div>			
	</div>
</body>
</html>