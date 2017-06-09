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
					<h3>${project.name}</h3>
					<p>${project.desc}</p>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col-sm-12">
				<h4>Pistas pendientes:</h4>
			</div>
			<div class="col-sm-12">
				<div class="row">
					<c:forEach items="${project.pendingTracks}" var="t">
						<div class="row" style="margin: 15px;">
							<div class="col-md-1">
								<img alt="Track" class="img-responsive img-circle"
									style="height: 50px; margin: auto;" src="../static/img/0404-negro.jpg">
							</div>
							<div class="col-md-11" style="line-height: 50px; height: 50px;">
								<a href="../editor/${t.id}" style="color: ghostwhite; font-size: 20px;">${t.name}</a>
							</div>
							<div class="col-md-11" style="line-height: 50px; height: 50px;">
								<a href="../user/${t.creator.name}" style="color: ghostwhite; font-size: 20px;">${t.creator.name}</a>
							</div>
							<form action="/acceptTrack" method="post">
							<div class="col-sm-10">
								<input type="hidden" name="track"  value="${t.id}"> 
								<input type="hidden" name="project" value="${project.id}" /> 
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
							</div>
							<div class="col-md-2">
								<input type="submit" class="btn btn-success form-control" value="Aceptar Track">
							</div>
							</form>
							
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
	</div>
</body>
</html>