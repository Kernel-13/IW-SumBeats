<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>

<html>
<head>
<%@ include file="../jspf/head.jspf"%>
<link rel="stylesheet" type="text/css"
	href="../../static/css/session-style.css">
<link rel="stylesheet" type="text/css" href="../../static/css/main.css">
<script src="https://code.jquery.com/jquery-3.0.0.min.js"
	type="text/javascript"></script>
<title>Cambio de Avatar</title>
</head>
<body>
	<%@ include file="../jspf/navbar.jspf"%>

	<div class="container-fluid login">
		<form class="form-horizontal" enctype="multipart/form-data"
			action="changePicture" method="post">
			
			<div class="row form-class" style="width: 942px;">
				<div class="col-lg-12">
					<div class="panel panel-info">
						<div class="panel-heading">Sube tu propia Imagen de Perfil</div>
						<div class="panel-body">
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
							<div class="form-group">
								<input type="file" name="photo"> <br/>
								<input type="hidden" name="id" value="${user.id}" />
								<div class="col-sm-12 submitButton">
									<input type="submit" class="btn btn-info"
										value="Aplicar Cambios">
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</form>
	</div>
</body>
</html>