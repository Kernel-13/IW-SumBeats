<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>

<html>
<head>
	<%@ include file="../jspf/head.jspf" %>
</head>
<body>
	<%@ include file="../jspf/navbar.jspf" %>
	<div class="container-fluid illust-container">
		<div class="row">
			<div class="col-sm-10 project-info">
				<img alt="p-img" src="static/img/logPro.png">
					<div class="search-text">
						<h3>Project Name</h3>
						<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut 
						labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris
						 nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit 
						 esse cillum dolore eu fugiat nulla pariatur.</p>
					</div>
			</div>
			<div class="col-sm-2 project-ratings">
				<h4>Rating: ???</h4>
				 
			</div>
		</div>
		<div class="row">
		<div style="margin-left: 25px;"><h4>Participantes:</h4></div>
			<div class="col-sm-12 collaborators">
				<div class="project-collaborators">
					<a href="user"><img alt="pr1" src="static/img/logUsu.png"></a>
					<div class="caption">
						<p>
							Collaborator #1
						</p>
					</div>
				</div>
				<div class="project-collaborators">
					<a href="user"><img alt="pr1" src="static/img/logUsu.png"></a>
					<div class="caption">
						<p>
							Collaborator #2
						</p>
					</div>
				</div>
				<div class="project-collaborators">
					<a href="user"><img alt="pr1" src="static/img/logUsu.png"></a>
					<div class="caption">
						<p>
							Collaborator #3
						</p>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
		<div style="margin-left: 25px;"><h4>Recursos:</h4></div>
			<div class="col-sm-12">
				<div style="margin-left: 100px;">
					<ul>
						<li><a href="studio">Estudio #1</a></li>
						<li><a href="studio">Estudio #2</a></li>
						<li><a href="studio">Estudio #3</a></li>
						<li><a href="studio">Estudio #4</a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>
</body>
</html>