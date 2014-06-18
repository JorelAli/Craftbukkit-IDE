package io.github.Skepter;
//There is a TemplateCompletion class that you could use instead of ShorthandCompletion. Unfortunately this did not seem to work for me (tried it in the AutoCompleteDemo example).
//
//Alternative approach is to use a CodeTemplateManager and extend the CodeTemplate. Below is an example (comes from CodeTemplateDemo).
//
//First extend AbstractCodeTemplate:

import java.io.IOException;
import java.io.ObjectInputStream;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.Element;

import org.fife.ui.rsyntaxtextarea.RSyntaxDocument;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.RSyntaxUtilities;

import org.fife.ui.rsyntaxtextarea.templates.*;

public class ArtemisCodeTemplate extends StaticCodeTemplate {

	private static final long serialVersionUID = 1;

	/**
	 * The code inserted before the caret position.
	 */
	private String beforeCaret;

	/**
	 * The code inserted after the caret position.
	 */
	private String afterCaret;

	/**
	 * Cached value representing whether <code>beforeCaret</code> contains one
	 * or more newlines.
	 */
	private transient int firstBeforeNewline;

	/**
	 * Cached value representing whether <code>afterCaret</code> contains one or
	 * more newlines.
	 */
	private transient int firstAfterNewline;

	private static final String EMPTY_STRING = "";

	/**
	 * Placeholder for the selected keyword
	 */
	private String placeholder;

	/**
	 * Constructor. This constructor only exists to support persistance through
	 * serialization.
	 */
	public ArtemisCodeTemplate() {
	}

	/**
	 * Constructor.
	 * 
	 * @param id
	 *            The ID of this code template.
	 * @param beforeCaret
	 *            The text to place before the caret.
	 * @param afterCaret
	 *            The text to place after the caret.
	 */
	public ArtemisCodeTemplate(String id, String beforeCaret, String placeholder, String afterCaret) {
		super(id, beforeCaret, afterCaret);
		setPlaceholder(placeholder);
	}

	public String getPlaceholder() {
		return placeholder;
	}

	public void setPlaceholder(String text) {
		this.placeholder = text;
	}

	private String getAfterTextIndented(String indent) {
		return getTextIndented(getAfterCaretText(), firstAfterNewline, indent);
	}

	private String getBeforeTextIndented(String indent) {
		return getTextIndented(getBeforeCaretText(), firstBeforeNewline, indent);
	}

	private String getTextIndented(String text, int firstNewline, String indent) {
		if (firstNewline == -1) {
			return text;
		}
		int pos = 0;
		int old = firstNewline + 1;
		StringBuffer sb = new StringBuffer(text.substring(0, old));
		sb.append(indent);
		while ((pos = text.indexOf('\n', old)) > -1) {
			sb.append(text.substring(old, pos + 1));
			sb.append(indent);
			old = pos + 1;
		}
		if (old < text.length()) {
			sb.append(text.substring(old));
		}
		return sb.toString();
	}

	public void invoke(RSyntaxTextArea textArea) throws BadLocationException {

		Caret c = textArea.getCaret();
		int dot = c.getDot();
		int mark = c.getMark();
		int p0 = Math.min(dot, mark);
		int p1 = Math.max(dot, mark);
		RSyntaxDocument doc = (RSyntaxDocument) textArea.getDocument();
		Element map = doc.getDefaultRootElement();

		int lineNum = map.getElementIndex(dot);
		Element line = map.getElement(lineNum);
		int start = line.getStartOffset();
		int end = line.getEndOffset() - 1; // Why always "-1"?
		String s = textArea.getText(start, end - start);
		int len = s.length();

		// endWS is the end of the leading whitespace
		// of the current line.
		int endWS = 0;
		while (endWS < len && RSyntaxUtilities.isWhitespace(s.charAt(endWS))) {
			endWS++;
		}
		s = s.substring(0, endWS);
		p0 -= getID().length();
		String beforeText = getBeforeTextIndented(s);
		String afterText = getAfterTextIndented(s);
		doc.replace(p0, p1 - p0, beforeText + placeholder + afterText, null);
		textArea.setCaretPosition(p0 + beforeText.length());
		textArea.moveCaretPosition(p0 + beforeText.length() + placeholder.length());
	}

	private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException {
		in.defaultReadObject();
		// "Resetting" before and after text to the same values will replace
		// nulls with empty srings, and set transient "first*Newline" values.
		setBeforeCaretText(this.beforeCaret);
		setAfterCaretText(this.afterCaret);
	}

	/**
	 * Returns a string representation of this template for debugging purposes.
	 * 
	 * @return A string representation of this template.
	 */
	public String toString() {
		return "[ArtemisCodeTemplate: id=" + getID() + ", text=" + getBeforeCaretText() + "|" + getPlaceholder() + "|" + getAfterCaretText() + "]";
	}

	public static String getEmptyString() {
		return EMPTY_STRING;
	}

}
// And then add it to the CodeTemplateManager:
//
// RSyntaxTextArea.setTemplatesEnabled(true);
// CodeTemplateManager ctm = RSyntaxTextArea.getCodeTemplateManager();
// CodeTemplate ct = new ArtemisCodeTemplate("sout", "System.out.println(",
// "<replaceme>", ");");
// ctm.addTemplate(ct);
// If you type "sout" and hit ctrl+shift+space it will expand to
// System.out.println(<replaceme>); and the <replaceme> part will be selected so
// you can type over it.