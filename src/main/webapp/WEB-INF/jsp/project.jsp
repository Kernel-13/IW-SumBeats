<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>

<html>
<head>
<%@ include file="../jspf/head.jspf"%>
<link rel="stylesheet" type="text/css" href="../static/css/main.css">
<title>${project.name}</title>
</head>
<body style="margin-bottom: 30px;">
	<%@ include file="../jspf/navbar.jspf"%>
	<div class="container illust-container">
		<div class="row">
			<div class="col-lx-10 col-md-10 col-sm-8 col-xs-8 project-info">
				<img alt="p-img" src="../static/img/logPro.png">
				<div class="search-text">
					<h3>${project.name}
						by <a href="/user/${project.author.getName()}">${project.author.getName()}</a>
					</h3>
					<p>${project.desc}</p>
				</div>
			</div>
			<div class="col-lx-2 col-md-2 col-sm-4 col-xs-4 project-ratings"
				style="text-align: center;">
				<!-- <h4>Rating: ???</h4>-->
				<div class="col-lx-12 col-md-12 col-sm-12 col-xs-12">
					<h4>Rating: ${project.getGlobalRating()}</h4>
				</div>
				<c:if test="${project.author.name == us}">
					<div class="col-lx-12 col-md-12 col-sm-12 col-xs-12 submitButton" style="padding: 0;">
						<a href="${project.name}/pendingTracks"
							class="btn btn-info form-control">Tracks Pendientes</a>
					</div>
				</c:if>
			</div>
		</div>

		<div class="row">
			<div class="col-lx-12 col-md-12 col-sm-12 col-xs-12">
				<h4>Participantes:</h4>
			</div>

			<c:if test="${project.author.name == us}">
				<form class="form-horizontal" action="/addCollaborator"
					method="post">
					<div class="col-lx-10 col-md-10 col-sm-8 col-xs-8">
						<input type="text" name="colaborador" class="form-control"
							id="colaborador" placeholder="Nombre usuario" required="required"
							maxlength="255"> <input type="hidden" name="project"
							value="${project.id}" /> <input type="hidden"
							name="${_csrf.parameterName}" value="${_csrf.token}" />
					</div>
					<div class="col-lx-2 col-md-2 col-sm-4 col-xs-4 submitButton">
						<input type="submit" class="btn btn-success form-control"
							value="Añadir colaborador" />
					</div>
				</form>
			</c:if>



			<div class="col-lx-12 col-md-12 col-sm-12 col-xs-12 collaborators">
				<c:forEach items="${project.collaborators}" var="c">
					<div class="col-lx-2 col-md-2 col-sm-2 col-xs-2">
						<a href="/user/${c.name}"><img alt="pr1"
							src="../static/img/logUsu.png" class="img-responsive"
							style="max-height: 100px; margin: auto;" /></a>
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
			<div class="col-lx-12 col-md-12 col-sm-12 col-xs-12">
				<h4>Recursos:</h4>
			</div>
			<div class="col-lx-12 col-md-12 col-sm-12 col-xs-12">
				<div class="row">
					<c:forEach items="${project.currentTracks}" var="t">
						<div class="row" style="margin: 15px;">
							<div class="col-lx-1 col-md-1 col-sm-1 col-xs-1"
								style="height: 50px; padding: 0;">
								<img alt="Track" class="img-responsive img-circle"
									style="max-height: 50px; margin: auto;"
									src="../static/img/0404-negro.jpg">
							</div>
							<div class="col-lx-11 col-md-11 col-sm-11 col-xs-11"
								style="line-height: 50px; height: 50px;">
								<a href="../editor/${t.id}"
									style="color: ghostwhite; font-size: 20px;">${t.name}</a>
							</div>
						</div>
					</c:forEach>
				</div>
				<div class="row">
					<form class="form-horizontal" action="/addTrack" method="post">
						<div class="col-lx-10 col-md-10 col-sm-8 col-xs-8">
							<input type="text" name="track" class="form-control" id="title"
								placeholder="Titulo" required="required" maxlength="255">
							<input type="hidden" name="project" value="${project.id}" /> <input
								type="hidden" name="${_csrf.parameterName}"
								value="${_csrf.token}" />
						</div>
						<div class="col-lx-2 col-md-2 col-sm-4 col-xs-4">
							<input type="submit" class="btn btn-success form-control"
								value="Crear Track">
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>