<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>

<html>
<head>
<%@ include file="../jspf/head.jspf"%>
<script src="../static/js/abcjs_editor_midi_3.1.2-min.js"
	type="text/javascript"></script>
<link rel="stylesheet" type="text/css"
	href="../static/css/abcjs-midi.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="../static/css/main.css">
<title>Editing "${track.name}"</title>
</head>
<body>
	<%@ include file="../jspf/navbar.jspf"%>
	<div class="container-fluid illust-container">

		<div class="row" style="margin-top: 20px; margin-bottom: 20px;">
			<div class="col-sm-5">
				<h3 style="text-align: center;">Edit "${track.name}"</h3>
				<br>

				<form class="form-horizontal" action="/saveTrack" method="post">
					<textarea class="text-editor form-control" id="abc3" style="width: 100%"
						name="abc" rows="20">${track.abc}</textarea>
					<input type="hidden" name="track" value="${track.id}" /> 
					<c:if test="${(track.creator.name == us) or (track.project.author.name == us)}">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
						<div id="cambio" style="padding: 20px; text-align: center;">
							<input type="submit" class="btn btn-success" value="Guardar cambios">
						</div>
					</c:if>
				</form>
				
			</div>

			<div class="col-sm-7" style="padding-top: 40px;">
				<div style="width: 100%" id="midi3"></div>
				<br>
				<div id="warnings3"></div>
				<div id="music3"></div>
				<div style="display: none">
					<div id="selection3"></div>
				</div>
				<div style="background: white; padding: 0; maring: 0;">
					<div id="paper3"></div>
				</div>

				<!--  
				<div id="cambio" style="padding: 20px; text-align: center;">
					<a href="#" class="btn btn-danger" style="font-size: 18px;">
						Aplicar Cambios</a>
				</div>
				-->
			</div>
		</div>


	</div>

	<script type="text/javascript">
		window.ABCJS.midi.soundfontUrl = "/static/soundfont/"

		function selectionCallback(abcelem) {
			var note = {};
			for ( var key in abcelem) {
				if (abcelem.hasOwnProperty(key) && key !== "abselem")
					note[key] = abcelem[key];
			}
			console.log(abcelem);
			var el = document.getElementById("selection");
			el.innerHTML = "<b>selectionCallback parameter:</b><br>"
					+ JSON.stringify(note);
		}

		function initEditor() {
			/*new ABCJS.Editor("abc2", {
				paper_id : "paper2",
				generate_midi : true,
				midi_id : "midi2",
				midi_download_id : "midi-download2",
				generate_warnings : true,
				warnings_id : "warnings2",
				midi_options : {
					generateDownload : true,
					program : 6
				},
				render_options : {
					listener : {
						highlight : selectionCallback
					}
				}
			});*/
			new ABCJS.Editor("abc3", {
				paper_id : "paper3",
				generate_midi : true,
				midi_id : "midi3",
				midi_download_id : "midi-download3",
				generate_warnings : true,
				warnings_id : "warnings3",
				midi_options : {
					generateDownload : false

				},
				render_options : {
					listener : {
						highlight : selectionCallback
					}
				}
			});
		}

		window.addEventListener("load", initEditor, false);
	</script>

</body>
</html>