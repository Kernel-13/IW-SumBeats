<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>

<html>
<head>
<%@ include file="../jspf/head.jspf"%>
<link rel="stylesheet" type="text/css" href="../../static/css/main.css">
<title>${project.name}</title>
</head>
<body>
	<%@ include file="../jspf/navbar.jspf"%>
	<div class="container illust-container">
		<div class="row">
			<div class="col-sm-10 project-info">
				<img alt="p-img" src="../../static/img/logPro.png">
				<div class="search-text">
					<h3>${project.name}</h3>
					<p>${project.desc}</p>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col-lx-12 col-md-12 col-sm-12 col-xs-12">
				<h4>Pistas pendientes:</h4>
			</div>
			<div class="col-lx-12 col-md-12 col-sm-12 col-xs-12">
				<div class="row">
					<c:forEach items="${project.pendingTracks}" var="t">
						<div class="row" style="margin: 15px;">
							<div class="col-lx-1 col-md-1 col-sm-1 col-xs-1"
								style="height: 50px; padding: 0;">
								<img alt="Track" class="img-responsive img-circle"
									style="max-height: 50px; margin: auto;"
									src="../../static/img/0404-negro.jpg">
							</div>
							<div class="col-lx-9 col-md-9 col-sm-9 col-xs-9"
								style="line-height: 50px; height: 50px;">
								<a href="../editor/${t.id}"
									style="color: ghostwhite; font-size: 26px;">"${t.name}"</a> <a
									href="../user/${t.creator.name}"
									style="color: gray; font-size: 16px;">by ${t.creator.name}</a>
							</div>
							<form action="/acceptTrack" method="post">
								<div class="col-lx-2 col-md-2 col-sm-2 col-xs-2">
									<input type="hidden" name="track" value="${t.id}"> <input
										type="hidden" name="project" value="${project.id}" /> <input
										type="hidden" name="${_csrf.parameterName}"
										value="${_csrf.token}" /> <input type="submit"
										class="btn btn-success form-control" value="Aceptar Track">
								</div>
							</form>
							<hr>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
	</div>
</body>
</html>