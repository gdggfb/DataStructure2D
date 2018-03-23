package cc.xkxk.learn.DataStructure2D;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import cc.xkxk.learn.DataStructure2D.RedBlackTree.Entry;

public class TreeJpanel extends JPanel {
	private static final long serialVersionUID = -8983985863229007323L;
	private static Font font = null;
	private static int width = 600;
	private static int height = 400;
	private static int diameter = 20; // 球直径
	private static int distanceX = 10; // 两球球心水平距离
	private static int distanceY = 30; // 两球球心垂直距离
	private static int maxDepth = 0; //
	private Entry root = null;

	public TreeJpanel() {
		setPreferredSize(new Dimension(width, height));
		setLayout(null);
	}

	public TreeJpanel(Entry root) {
		setPreferredSize(new Dimension(width, height));
		setLayout(null);
		this.root = root;
	}

	public void countDepth(Entry node) {
		if (node == null) {
			return;
		}

		if (node == root) {
			node.depth = 0;
		} else {
			node.depth = node.parent.depth + 1;
		}

		if (maxDepth < node.depth) {
			maxDepth = node.depth;
		}

		countDepth(node.left);
		countDepth(node.right);
	}

	public void drawNode(Graphics graphics, Graphics2D graphics2D, Entry node, int px, int py, boolean isLeft) {
		int x, y;
		if (node == root) {
			x = width / 2 - diameter / 2;
			y = 20;
		} else {
			int offset = distanceX * maxDepth;
			x = isLeft ? (px - offset) : (px + offset);
			y = py + distanceY;
			if (node == null) {
				graphics2D.drawArc(x, y, diameter, diameter, 0, 360);
				return;
			}
		}

		int ax = x + diameter / 2;
		int ay = y + diameter / 2;
		int lax = x - distanceX + diameter / 2;
		int lay = y + distanceY + diameter / 2;
		int rax = x + distanceX + diameter / 2;
		int ray = y + distanceY + diameter / 2;
		graphics2D.setColor(Color.BLUE);
		graphics2D.drawLine(ax, ay, lax, lay);
		graphics2D.drawLine(ax, ay, rax, ray);
		graphics2D.setColor(node.color ? Color.BLACK : Color.RED);
		graphics2D.fillArc(x, y, diameter, diameter, 0, 360);
		graphics2D.drawString(String.valueOf(node.key), x, y);
		drawNode(graphics, graphics2D, node.left, x, y, true);
		drawNode(graphics, graphics2D, node.right, x, y, false);
	}

	private void checkFont() {
		if (font == null) {
			font = new Font("宋体", Font.PLAIN, 18);
		}
	}

	@Override
	protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D graphics2D = (Graphics2D) graphics.create();
		graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		checkFont();

		countDepth(root);
		maxDepth++;

		drawNode(graphics, graphics2D, root, 0, 0, false);

		graphics2D.dispose();
	}
}
