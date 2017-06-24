<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>

<html>
<head>
<%@ include file="../jspf/head.jspf"%>
<link rel="stylesheet" type="text/css" href="/static/css/flagged.css">
<title>Lista de Usuarios</title>
</head>
<body>
	<%@ include file="../jspf/navbar.jspf"%>
	<div class="container illust-container">
		<div class="row">
			<div class="col-md-12"
				style="text-align: center; margin-bottom: 50px;">
				<h1>Lista de Usuarios</h1>
			</div>

			<div class="col-sm-12 illust-box">
				<table style="width: 100%; text-align: center;">
					<tr style="font-size: 20px;">
						<td>Avatar</td>
						<td>Nombre</td>
						<td>Descripción</td>
						<td>Nº de Proyectos</td>
						<td>Nº de Tracks</td>
					</tr>

					<c:forEach items="${lista}" var="c">
						<tr>
							<td><img alt="${c.name}" class="img-circle" style="max-height: 100px; max-width: 100px;"
									src="../user/photo?id=${c.id}"></td>
							<td><a style="font-size: 20px;" href="/user/${c.safeName()}">${c.name}</a></td>
							<td style="max-width: 350px;"><p>${c.description}</p></td>
							<td><h4>${c.projects.size()}</h4></td>
							<td><h4>${c.tracks.size()}</h4></td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>
</body>
</html>