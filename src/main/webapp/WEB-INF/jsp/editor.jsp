<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>

<html>
<head>
<%@ include file="../jspf/head.jspf"%>
<script src="static/js/abcjs_editor_midi_3.1.2-min.js"
	type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="static/css/abcjs-midi.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
<title>Editor Test</title>
</head>
<body>
	<%@ include file="../jspf/navbar.jspf"%>
	<div class="container-fluid illust-container">
		<div class="row">
			<div class="col-sm-5">
				<h4>Edit your track using this Text-Box</h4>
				<br>
				<textarea class="text-editor form-control" id="abc" cols="80"
					rows="15">X: 1
T: Cooley's
M: 4/4
L: 1/8
R: reel
K: Emin
|:D2|EB{c}BA B2 EB|~B2 AB dBAG|FDAD BDAD|FDAD dAFD|
EBBA B2 EB|B2 AB defg|afe^c dBAF|DEFD E2:|
|:gf|eB B2 efge|eB B2 gedB|A2 FA DAFA|A2 FA defg|
eB B2 eBgB|eB B2 defg|afe^c dBAF|DEFD E2:|
</textarea>
				<br>
				<div id="midi-download"></div>

			</div>

			<div class="col-sm-7" style="padding-top: 40px;">
				<div id="warnings"></div>
				<div id="midi"></div>
				<div id="music"></div>
				<div style="display: none">
					<div id="selection"></div>
				</div>
				<br>
				<hr>
				<br>
				<div style="background: white; padding: 0; maring: 0;">
					<div id="paper0"></div>
				</div>
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
			new ABCJS.Editor("abc", {
				paper_id : "paper0",
				generate_midi : true,
				midi_id : "midi",
				midi_download_id : "midi-download",
				generate_warnings : true,
				warnings_id : "warnings",
				midi_options : {
					generateDownload : true,
					program : 6
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