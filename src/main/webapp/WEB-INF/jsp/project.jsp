<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>

<html>
<head>
<%@ include file="../jspf/head.jspf"%>
<link rel="stylesheet" type="text/css" href="../static/css/main.css">
</head>
<body>
	<%@ include file="../jspf/navbar.jspf"%>
	<div class="container illust-container">
		<div class="row">
			<div class="col-sm-10 project-info">
				<img alt="p-img" src="../static/img/logPro.png">
				<div class="search-text">
					<h3>${project.name}</h3>
					<p>${project.desc}</p>
				</div>
			</div>
			<div class="col-sm-2 project-ratings">
				<h4>Rating: ???</h4>

			</div>
		</div>
		<div class="row">
			<div style="margin-left: 25px;">
				<h4>Participantes:</h4>
			</div>
			<div class="col-sm-12 collaborators">
				<c:forEach items="${project.collaborators}" var="c">
					<div class="project-collaborators">
						<a href="user"><img alt="pr1" src="static/img/logUsu.png"></a>
						<div class="caption">
							<p>${c.name}</p>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
		<div class="row">
			<div style="margin-left: 25px;">
				<h4>Recursos:</h4>
			</div>
			<div class="col-sm-12">
				<div style="margin-left: 100px;">
					<c:forEach items="${project.currentTracks}" var="c">
						<div class="col-12-md project-collaborators">
							<img alt="Track" class="img-responsive img-circle"
								style="height: 50px" src="../static/img/0404-negro.jpg"> <a
								href="../editor/${c.id}">${c.name}</a>
							</li>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
	</div>
</body>
</html>