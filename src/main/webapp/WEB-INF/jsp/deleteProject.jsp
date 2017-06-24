<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html>

<html>
<head>
<%@ include file="../jspf/head.jspf"%>
<link rel="stylesheet" type="text/css" href="../static/css/session-style.css">
<link rel="stylesheet" type="text/css" href="../static/css/main.css">
</head>
<body>
	<%@ include file="../jspf/navbar.jspf"%>
	<div class="container-fluid login">
		<div class="row delete-class">
			<div class="col-lg-12">
				<div class="panel panel-info">
					<div class="panel-heading">
						<h4>¿Estas seguro de que deseas borrar "${project.name}" ?</h4>
					</div>
					<div class="panel-body">
						<form class="form-horizontal" action="/deleteProject"
							method="post">
							<div class="form-group">
								<input type="hidden" name="proyecto" value="${project.id}" /> 
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /> 
								<div class="col-sm-12 submitButton">
									<button type="submit" name="yes" class="btn btn-danger">Si</button>
									<a style="width: 120px; font-size: 20px;" href="/project/${project.safeName()}" class="btn btn-info">No</a>
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