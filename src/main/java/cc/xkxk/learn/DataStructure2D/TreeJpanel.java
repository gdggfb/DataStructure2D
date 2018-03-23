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
	private static int width = 1000;
	private static int height = 400;
	private static int diameter = 20; // ��ֱ��
	private static int distanceX = 8; // ��������ˮƽ����
	private static int distanceY = 30; // �������Ĵ�ֱ����
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

	public void countDepth(Entry node, Entry nodeP, boolean isLeft) {
		if (node == null) {
			if (isLeft) {
				nodeP.depthL = 1;
			} else {
				nodeP.depthR = 1;
			}
			int i = 1;
			for (Entry n = nodeP; n.parent != null; n = n.parent) {
				i++;
				if (n == n.parent.left) {
					if (n.parent.depthL >= i) {
						break;
					} else {
						n.parent.depthL = i;
					}
				} else {
					if (n.parent.depthR >= i) {
						break;
					} else {
						n.parent.depthR = i;
					}
				}
			}
			return;
		}

		countDepth(node.left, node, true);
		countDepth(node.right, node, false);
	}

	public void drawNode(Graphics graphics, Graphics2D graphics2D, Entry node, int px, int py, boolean isLeft) {
		int x, y, offset, depth;
		if (node == root) {
			x = width / 2 - diameter / 2 - (root.depthR - root.depthL) * distanceX * 2;
			y = 20;
		} else {
			y = py + distanceY;
			if (node == null) {
				graphics2D.drawArc(isLeft ? (px - distanceX) : (px + distanceX), y, diameter, diameter, 0, 360);
				return;
			} else {
				depth = isLeft ? node.parent.depthL : node.parent.depthR;
				offset = distanceX * depth * 3;
				x = isLeft ? (px - offset) : (px + offset);
			}
		}

		int ax = x + diameter / 2;
		int ay = y + diameter / 2;
		int lax, lay, rax, ray;
		if (node.right == null) {
			rax = x + distanceX + diameter / 2;
			ray = y + distanceY + diameter / 2;
		} else {
			rax = x + distanceX * node.depthR * 3 + diameter / 2;
			ray = y + distanceY + diameter / 2;
		}
		if (node.left == null) {
			lax = x - distanceX + diameter / 2;
			lay = y + distanceY + diameter / 2;
		} else {
			lax = x - distanceX * node.depthL * 3 + diameter / 2;
			lay = y + distanceY + diameter / 2;
		}
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
			font = new Font("����", Font.PLAIN, 18);
		}
	}

	@Override
	protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D graphics2D = (Graphics2D) graphics.create();
		graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		checkFont();

		countDepth(root, root, true);

		drawNode(graphics, graphics2D, root, 0, 0, false);

		graphics2D.dispose();
	}
}
