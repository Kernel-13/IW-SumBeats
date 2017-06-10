package es.ucm.fdi.iw.util;

public class HtmlEscapeStringEditor {
	
	public String sanitize(String string) {
		return string.replaceAll("(?i)<script.*?>.*?</script.*?>", "") 	// <script> are removed
				.replaceAll("(?i)<.*?javascript:.*?>.*?</.*?>", "") 	// javascript: call are removed
				.replaceAll("(?i)<.*?\\s+on.*?>.*?</.*?>", ""); 		// remove on* attributes
	}
}