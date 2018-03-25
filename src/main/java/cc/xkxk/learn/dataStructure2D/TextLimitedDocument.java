package cc.xkxk.learn.dataStructure2D;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class TextLimitedDocument extends PlainDocument {
	private static final long serialVersionUID = 1L;

	public void insertString(int offset, String str, AttributeSet attrSet) throws BadLocationException {
		if (str == null) {
			return;
		}
		if ((getLength() + str.length()) <= 2) {
			char[] chars = str.toCharArray();
			int length = 0;
			for (int i = 0; i < chars.length; i++) {
				if (chars[i] >= '0' && chars[i] <= '9') {
					chars[length++] = chars[i];
				}
			}
			super.insertString(offset, new String(chars, 0, length), attrSet);
		}
	}

}
