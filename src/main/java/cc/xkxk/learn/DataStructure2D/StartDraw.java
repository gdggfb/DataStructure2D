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

	public static void doDraw() {
		JPanel board = new JPanel();

		board.setLayout(new BoxLayout(board, BoxLayout.Y_AXIS));
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.getViewport().add(board);

		JButton actionButton = new JButton("插入");
		JButton closeButton = new JButton("删除");

		JTextField textField1 = new JTextField();
		textField1.setColumns(2);

		RedBlackTree tree = new RedBlackTree();
		JScrollBar sBar = scrollPane.getVerticalScrollBar();
		sBar.setUnitIncrement(15);

		actionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tree.put(Integer.valueOf(textField1.getText()));
				if (tree.size >= 1) {
					board.add(new TreeJpanel(tree.root), 0);
					board.revalidate();
				}
				textField1.setText("");;
			}
		});

		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		buttonPanel.add(textField1);
		buttonPanel.add(actionButton);
		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(closeButton);

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
		// frame.setLocationRelativeTo(null);
		frame.setContentPane(panelContainer);
		frame.setVisible(true);
	}
}
