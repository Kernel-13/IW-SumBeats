<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>

<html>
<head>
<%@ include file="../jspf/head.jspf"%>
<link rel="stylesheet" type="text/css" href="../static/css/main.css">
<script src="../static/js/abcjs_editor_midi_3.1.2-min.js"
	type="text/javascript"></script>
<link rel="stylesheet" type="text/css"
	href="../static/css/abcjs-midi.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css"
	href="../static/css/profile-view.css">
<link rel="stylesheet" type="text/css" href="../static/css/messages.css">
<title>Bandeja de entrada</title>
</head>
<body>
	<%@ include file="../jspf/navbar.jspf"%>
	<div class="container">
		<!-- Sending a Message -->
		<div class="row section">
			<div class="col-lg-12">
				<h3>Enviar Mensaje</h3>
			</div>
			<form action="/sendMessage" method="POST">
				<div class="col-lg-12">
					<div class="form-group">
						<div class="col-md-12 section">
							<label class="sr-only" for="receptor"> Destinatario </label> <input
								class="form-control" type="text" id="receptor" name="dest"
								required="required" placeholder="Destinatario" maxlength="20">
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-12 section">
							<label class="sr-only" for="mensajes"> Mensaje </label>
							<textarea class="form-control" name="msg" required="required"
								id="mensajes" placeholder="Escribe aquí tu mensaje"
								maxlength="500"></textarea>
						</div>
					</div>
				</div>

				<div class="col-md-12 section">
					<input type="submit" name="submit" value="Enviar"
						class="btn btn-success">
				</div>
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
			</form>
		</div>

		<!-- Message Management -->
		<div class="row section">
			<div class="col-lg-12">
				<h3>Bandeja de Entrada</h3>
			</div>
			<div class="col-lg-12">

				<!-- Tabs -->
				<ul class="nav nav-tabs nav-justified">
					<li class="active"><a data-toggle="tab" href="#new">Recibidos</a></li>
					<li><a data-toggle="tab" href="#sent">Enviados</a></li>
				</ul>

				<!-- Tab Content -->
				<div class="tab-content">

					<!-- New  Messages -->
					<div id="new" class="tab-pane fade in active user-posts">
						<!-- MENSAJES RECIBIDOS -->
						<c:forEach items="${input}" var="m">
							<div class="row section">
								<div class="col-md-12 message">
									<div class="media">
										<div class="media-left" style="padding-right: 25px;">
											<a href="/user/${m.author.safeName()}"> <img alt="pr1"
												src="../user/photo?id=${m.author.id}"
												class="img-circle media-object"
												style="max-height: 100px; margin: auto;" /></a>
										</div>
										<div class="media-body media-right"
											style="padding-left: 25px;">
											<h4 class="media-heading" style="font-weight: bolder;">De
												${m.author.name}</h4>
											<p>${m.message}</p>
										</div>
									</div>
								</div>
							</div>
						</c:forEach>
					</div>

					<!-- Sent Messages -->
					<div id="sent" class="tab-pane fade user-posts">
						<!-- MENSAJES ENVIADOS -->
						<c:forEach items="${output}" var="m">
							<div class="row section">
								<div class="col-md-2">
									<div>
										<h4 class="media-heading">
											<a href="/user/${m.destinatario.safeName()}"> <img alt="pr1"
												src="../user/photo?id=${m.destinatario.id}"
												class="img-responsive img-circle"
												style="max-height: 125px; margin: auto;" />
											</a>
										</h4>
									</div>
								</div>
								<div class="col-md-10">
									<div class="media">
										<div class="media-body media-right">
											<h4 style="font-weight: bolder;">Mensaje para
												${m.destinatario.name}</h4>
											<p>${m.message}</p>
										</div>
									</div>
								</div>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>

	</div>

</body>
</html>