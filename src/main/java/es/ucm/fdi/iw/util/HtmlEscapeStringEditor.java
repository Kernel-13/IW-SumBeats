package es.ucm.fdi.iw.util;

import java.beans.PropertyEditorSupport;
import org.springframework.web.util.HtmlUtils;

public class HtmlEscapeStringEditor extends PropertyEditorSupport {
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		String out = "";
		if (text != null)
			out = HtmlUtils.htmlEscape(text.trim());

		setValue(out);
	}

	@Override
	public String getAsText() {
		String out = (String) getValue();
		if (out == null)
			out = "";
		return out;
	}

	public String sanitize(String string) {
		return string.replaceAll("(?i)<script.*?>.*?</script.*?>", "") 	// <script> are removed
				.replaceAll("(?i)<.*?javascript:.*?>.*?</.*?>", "") 	// javascript: call are removed
				.replaceAll("(?i)<.*?\\s+on.*?>.*?</.*?>", ""); 		// remove on* attributes
	}
}