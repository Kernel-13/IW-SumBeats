<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>

<html>
<head>
<%@ include file="../jspf/head.jspf"%>
<link rel="stylesheet" type="text/css" href="/static/css/flagged.css">
<title>Mis Tracks</title>
</head>
<body>
	<%@ include file="../jspf/navbar.jspf"%>
	<div class="container illust-container">
		<div class="row section">
			<div class="col-md-12">
				<h2>Mis Tracks</h2>
				<table>
					<tr>
						<th>Titulo</th>
						<th>Proyecto</th>
						<th>Autor del Proyecto</th>
						<th>Estado</th>
						<th>Opciones</th>
					</tr>
					<c:forEach items="${lista}" var="t">
						<tr>
							<td>${t.name}</td>
							<td><a href="/project/${t.project.name}">${t.project.name}</a></td>
							<td><a href="/user/${t.project.author.name}">${t.project.author.name}</a></td>
							<td>${t.status}</td>
							<td>
								<div>
									<a class="btn btn-info" href="/editor/${t.id}">Editar
										Track</a>
								</div>
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>
</body>
</html>