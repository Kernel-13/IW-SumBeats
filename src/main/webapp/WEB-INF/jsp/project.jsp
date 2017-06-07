<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>

<html>
<head>
<%@ include file="../jspf/head.jspf"%>
<link rel="stylesheet" type="text/css" href="../static/css/main.css">
<title>${project.name}</title>
</head>
<body>
	<%@ include file="../jspf/navbar.jspf"%>
	<div class="container illust-container">
		<div class="row">
			<div class="col-sm-10 project-info">
				<img alt="p-img" src="../static/img/logPro.png">
				<div class="search-text">
					<h3>${project.name}
						by <a href="/user/${project.author.getName()}">${project.author.getName()}</a>
					</h3>
					<p>${project.desc}</p>
				</div>
			</div>
			<div class="col-sm-2 project-ratings">
				<!-- <h4>Rating: ???</h4>-->
				<h4>Rating: ${project.getGlobalRating()}</h4>
			</div>
		</div>

		<div class="row">
			<div class="col-sm-12">
				<h4>Participantes:</h4>
			</div>

			<form class="form-horizontal" action="/addCollaborator" method="post">
				<div class="col-sm-10">
					<input type="text" name="colaborador" class="form-control"
						id="colaborador" placeholder="Nombre usuario" required="required" maxlength="255">
					<input type="hidden" name="project" value="${project.id}" /> <input
						type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				</div>

				<div class="col-sm-2 submitButton">
					<input type="submit" class="btn btn-success form-control"
						value="Añadir colaborador" />
				</div>

			</form>

			<div class="col-sm-12 collaborators">
				<c:forEach items="${project.collaborators}" var="c">
					<div class="col-md-2">
						<a href="/user/${c.name}"><img alt="pr1"
							src="../static/img/logUsu.png" style="height: 100px;" /></a>
						<div class="caption">
							<p>
								<a href="/user/${c.name}">${c.name}</a>
							</p>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12">
				<h4>Recursos:</h4>
			</div>
			<div class="col-sm-12">

				<div class="row">
					<c:forEach items="${project.currentTracks}" var="c">
						<div class="row" style="margin: 15px;">
							<div class="col-md-1">
								<img alt="Track" class="img-responsive img-circle"
									style="height: 50px; margin: auto;" src="../static/img/0404-negro.jpg">
							</div>
							<div class="col-md-11" style="line-height: 50px; height: 50px;">
								<a href="../editor/${c.id}" style="color: ghostwhite; font-size: 20px;">${c.name}</a>
							</div>
						</div>
					</c:forEach>
				</div>
				<div class="row">
					<form class="form-horizontal" action="/addTrack" method="post">
						<div class="col-sm-10">
							<input type="text" name="track" class="form-control" id="title"
								placeholder="Titulo" required="required" maxlength="255"> <input
								type="hidden" name="project" value="${project.id}" /> <input
								type="hidden" name="${_csrf.parameterName}"
								value="${_csrf.token}" />
						</div>
						<div class="col-md-2">
							<input type="submit" class="btn btn-success form-control" value="Crear Track">
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>