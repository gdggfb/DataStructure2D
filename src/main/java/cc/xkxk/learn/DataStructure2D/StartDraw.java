package cc.xkxk.learn.DataStructure2D;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class StartDraw {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				doDraw();
			}
		});
	}

	public static void action(JTextField textField, JPanel board, RedBlackTree tree, int action) {
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

	public static void doDraw() {
		JPanel board = new JPanel();
		board.setLayout(new BoxLayout(board, BoxLayout.Y_AXIS));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.getViewport().add(board);

		JScrollBar sBar = scrollPane.getVerticalScrollBar();
		sBar.setUnitIncrement(15);

		JTextField textField = new JTextField();
		textField.setColumns(2);
		textField.setDocument(new TextLimitedDocument());

		JButton putButton = new JButton("插入");
		JButton removeButton = new JButton("删除");
		RedBlackTree tree = new RedBlackTree();

		putButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				action(textField, board, tree, 0);
			}
		});

		removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				action(textField, board, tree, 1);
			}
		});

		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		buttonPanel.add(textField);
		buttonPanel.add(putButton);
		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(removeButton);

		bottomPanel.add(Box.createVerticalStrut(10));
		bottomPanel.add(buttonPanel);
		bottomPanel.add(Box.createVerticalStrut(10));

		JPanel panelContainer = new JPanel();
		panelContainer.setLayout(new GridBagLayout());

		GridBagConstraints c1 = new GridBagConstraints();
		c1.gridx = 0;
		c1.gridy = 0;
		c1.weightx = 1.0;
		c1.weighty = 1.0;

		c1.fill = GridBagConstraints.BOTH;
		panelContainer.add(scrollPane, c1);

		GridBagConstraints c3 = new GridBagConstraints();
		c3.gridx = 1;
		c3.gridy = 0;
		c3.weightx = 0;
		c3.weighty = 1.0;
		c3.fill = GridBagConstraints.HORIZONTAL;
		panelContainer.add(bottomPanel, c3);

		JFrame frame = new JFrame("Tool");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panelContainer.setOpaque(false);
		frame.setSize(new Dimension(1366, 760));
		frame.setContentPane(panelContainer);
		frame.setVisible(true);
	}
}
