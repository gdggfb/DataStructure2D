package cc.xkxk.learn.dataStructure2D;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.HeadlessException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class StartFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final String title = "DataStructure2D - v1.0 - create by github.com/gdggfb";
	private static final String putButtonDesc = "插入";
	private static final String removeButtonDesc = "删除";

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> new StartFrame());
	}

	public StartFrame() throws HeadlessException {
		super(title);
		doDraw();
	}

	public void action(JTextField textField, JPanel board, RedBlackTree tree, int action) {
		doAction(textField, board, tree, action);
		textField.requestFocus();
	}

	public void doAction(JTextField textField, JPanel board, RedBlackTree tree, int action) {
		String value = textField.getText();
		if (value == null || value.isEmpty()) {
			return;
		}
		textField.setText("");
		switch (action) {
		case 0:
			if (!tree.put(Integer.valueOf(value))) {
				return;
			}
			break;
		case 1:
			if (!tree.remove(Integer.valueOf(value)) || tree.size < 1) {
				return;
			}
			break;
		default:
			break;
		}
		board.add(new TreeJpanel(tree.root, tree.process), 0);
		board.revalidate();
	}

	public void doDraw() {
		JPanel board = createBoard();
		JTextField textField = createTextField();
		JPanel buttonPanel = createButtonPanel(board, textField);
		JScrollPane scrollPane = createScrollPane(board);
		JPanel container = createContainer(scrollPane, buttonPanel);
		JMenuBar menubar = createMenuBar();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setJMenuBar(menubar);
		setSize(new Dimension(1366, 760));
		setContentPane(container);
		setVisible(true);
	}

	private JMenuBar createMenuBar() {
		JMenuBar menubar = new JMenuBar();

		JMenu setMenu = new JMenu("设置");
		JMenu helpMenu = new JMenu("帮助");
		JMenuItem aboutItem = new JMenuItem("关于");
		menubar.setFont(new Font("宋体", Font.PLAIN, 12));

		helpMenu.add(aboutItem);
		menubar.add(setMenu);
		menubar.add(helpMenu);
		return menubar;
	}

	private JPanel createContainer(JScrollPane scrollPane, JPanel buttonPanel) {
		JPanel container = new JPanel();
		container.setLayout(new BorderLayout());
		container.add(buttonPanel, BorderLayout.WEST);
		container.add(scrollPane, BorderLayout.EAST);
		container.setOpaque(false);
		return container;
	}

	private JScrollPane createScrollPane(JPanel board) {
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.getViewport().add(board);
		scrollPane.getVerticalScrollBar().setUnitIncrement(15);
		return scrollPane;
	}

	private JPanel createButtonPanel(JPanel board, JTextField textField) {
		JButton putButton = new JButton(putButtonDesc);
		JButton removeButton = new JButton(removeButtonDesc);
		RedBlackTree tree = new RedBlackTree();
		putButton.addActionListener((e) -> action(textField, board, tree, 0));
		removeButton.addActionListener((e) -> action(textField, board, tree, 1));

		JPanel textFieldWrap = new JPanel();
		textFieldWrap.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
		textFieldWrap.add(textField);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setPreferredSize(new Dimension(80, 760));
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		buttonPanel.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
		buttonPanel.add(Box.createVerticalStrut(600));
		buttonPanel.add(textFieldWrap);
		buttonPanel.add(putButton);
		buttonPanel.add(removeButton);
		buttonPanel.add(Box.createVerticalStrut(50));
		return buttonPanel;
	}

	private JPanel createBoard() {
		JPanel board = new JPanel();
		board.setLayout(new BoxLayout(board, BoxLayout.Y_AXIS));
		return board;
	}

	private JTextField createTextField() {
		JTextField textField = new JTextField();
		textField.setColumns(4);
		textField.setDocument(new PlainDocument() {
			private static final long serialVersionUID = 1L;

			public void insertString(int offset, String str, AttributeSet attrSet) throws BadLocationException {
				if (str == null) {
					return;
				}
				if ((getLength() + str.length()) <= 3) {
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
		});

		return textField;
	}

}
