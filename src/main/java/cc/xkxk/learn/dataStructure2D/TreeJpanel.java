package cc.xkxk.learn.dataStructure2D;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import cc.xkxk.learn.dataStructure2D.RedBlackTree.Entry;

public class TreeJpanel extends JPanel {
	private static final long serialVersionUID = -8983985863229007323L;
	private int width = 1000;
	private int height = 400;
	private int diameter = 20;
	private int distanceX = 11;
	private int distanceY = 30;
	private Entry root = null;
	private List<String> process = null;
	private List<Graph> list = new ArrayList<>();

	public TreeJpanel(Entry root, List<String> process) {
		setPreferredSize(new Dimension(width, height));
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setLayout(null);
		this.root = root;
		this.process = process;
	}

	private int countOffset(int depth) {
		int n = (int) (distanceX * Math.pow(depth, 1.8) / 1.7);
		return n;
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

	public void collectNode(Entry node, int px, int py, boolean isLeft) {
		int x, y, offset;
		if (node == root) {
			if (root.depthL > 6 || root.depthR > 6) {
				x = (width * root.depthL) / (root.depthL + root.depthR);
			} else {
				x = width / 2 - diameter / 2;
			}
			y = 20;
		} else {
			y = py + distanceY;
			if (node == null) {
				return;
			} else {
				offset = countOffset(isLeft ? node.parent.depthL : node.parent.depthR);
				x = isLeft ? (px - offset) : (px + offset);
			}
		}

		int ax = x + diameter / 2;
		int ay = y + diameter / 2;
		int lax, lay, rax, ray;
		if (node.right != null) {
			rax = x + countOffset(node.depthR) + diameter / 2;
			ray = y + distanceY + diameter / 2;
			list.add(new Graph(0, Color.BLUE, ax, ay, rax, ray));
		}
		if (node.left != null) {
			lax = x - countOffset(node.depthL) + diameter / 2;
			lay = y + distanceY + diameter / 2;
			list.add(new Graph(0, Color.BLUE, ax, ay, lax, lay));
		}

		list.add(new Graph(1, node.color ? Color.BLACK : Color.RED, x, y, diameter, diameter));
		list.add(new Graph(3, Color.BLACK, x, y, 0, 0, String.valueOf(node.key), null));
		System.out.println("key:" + node.key + ",R:" + node.depthR + ",L:" + node.depthL);
		collectNode(node.left, x, y, true);
		collectNode(node.right, x, y, false);
	}

	private void collectProcess() {
		String str = process.get(0);
		Color color = null;
		Font font = new Font("宋体", Font.PLAIN, 20);
		int y = 20;
		if (str.startsWith("put")) {
			color = Color.BLACK;
		} else {
			color = Color.BLACK;
		}
		for (String s : process) {
			list.add(new Graph(10, color, 10, y, 0, 0, s, font));
			y = y + 20;
		}

	}

	private void drawNode(Graphics2D graphics2D) {
		list.sort(new Comparator<Graph>() {
			@Override
			public int compare(Graph o1, Graph o2) {
				return o1.type - o2.type;
			}
		});

		for (Graph g : list) {
			graphics2D.setColor(g.color);
			graphics2D.setFont(g.font);
			if (g.type == 0) {
				graphics2D.drawLine(g.x1, g.y1, g.x2, g.y2);
			}
			if (g.type == 1) {
				graphics2D.fillArc(g.x1, g.y1, g.x2, g.y2, 0, 360);
			}
			if (g.type == 3) {
				graphics2D.drawString(g.value, g.x1, g.y1);
			}
			if (g.type == 10) {
				graphics2D.drawString(g.value, g.x1, g.y1);
			}
		}
	}

	public void paint(Graphics graphics) {
		super.paint(graphics);
		Graphics2D graphics2D = (Graphics2D) graphics.create();
		graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		if (list.isEmpty()) {
			countDepth(root, root, true);
			collectNode(root, 0, 0, false);
			collectProcess();
		}
		drawNode(graphics2D);
	}

	class Graph {
		int type;// 0 - 直线; 1 - 实心圆； 3 - 写字 ； 10 - 过程描述
		Color color;
		int x1;
		int y1;
		int x2;
		int y2;
		String value;
		Font font;

		public Graph(int type, Color color, int x1, int y1, int x2, int y2) {
			super();
			this.type = type;
			this.color = color;
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
		}

		public Graph(int type, Color color, int x1, int y1, int x2, int y2, String value, Font font) {
			super();
			this.type = type;
			this.color = color;
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
			this.value = value;
			this.font = font;
		}
	}
}
