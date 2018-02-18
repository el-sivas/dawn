package de.elsivas.gol.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import de.elsivas.basic.SleepUtils;
import de.elsivas.gol.gui.GolGui.GolParameter;

public class GolGuiFrame extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;

	private GolParameter parameter;

	public void init(GolParameter g) {
		parameter = g;
	}

	public void start0(GolParameter g) {
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);

		int round = 0;
		while (true) {
			rerender(round++, g);
			if (!this.isShowing()) {
				break;
			}
		}
	}

	private void rerender(int round, GolParameter g) {
		CellContent[][] content;
		if (g.isRandom()) {
			content = GolRandomContentGenerator.generateContent();
		} else {
			content = GolFileContentReader.content(g.getWorkdir(), g.chars(), g.getCellContentFactory());
		}
		GolContentValidator.validate(content);
		rerender(content, round);
		SleepUtils.sleepFor(2500);
	}

	private void rerender(CellContent[][] content, int round) {
		final Dimension dimension = dimension(content);
		this.setSize(dimension);
		final JTable table = new JTable(new GolTableModel(content));

		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {

			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {

				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				if (value instanceof CellContent) {
					((CellContent) value).format(c);
				}
				return c;
			}
		};
		table.setDefaultRenderer(String.class, renderer);

		renderer.setHorizontalAlignment(JLabel.CENTER);
		this.add(table, BorderLayout.CENTER);

		for (int i = 0; i < content.length; i++) {
			TableColumn column = table.getColumnModel().getColumn(i);
			column.setWidth(content[0][0].getContentSize());
			column.setCellRenderer(renderer);
		}

		this.add(table);
		this.setTitle("view: " + round + " (" + (int) dimension.getWidth() + "x" + (int) dimension.getHeight() + ")");
	}

	private Dimension dimension(CellContent[][] content) {
		int length = content.length;
		int contentSize = content[0][0].getContentSize();

		int w = length * 16 * contentSize;
		int h = length * 16 + 50;
		final Dimension d = new Dimension(w, h);
		return d;
	}

	@Override
	public void run() {
		start0(parameter);
	}

}
