package de.elsivas.gol.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.elsivas.basic.SleepUtils;

public class GolGui extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final Log LOG = LogFactory.getLog(GolGui.class);

	private static GolGui gui;

	public static void main(String[] args) {
		gui = new GolGui();
		try {
			gui.start("/home/sebastian/test/gol");
		} catch (Throwable t) {
			LOG.error("Error while programm execution", t);
		}
		System.exit(1);
	}

	private void start(String path) {
		this.setLayout(new BorderLayout());
		gui.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		gui.setVisible(true);

		int round = 0;
		while (true) {
			rerender(round++, path);
		}
	}

	private void rerender(int round, String path) {
		CellContent[][] content = GolFileContentReader.content(path);
		GolContentValidator.validate(content);
		rerender(content, round);
		SleepUtils.sleepFor(1000);
	}

	private void rerender(CellContent[][] content, int round) {
		final Dimension dimension = dimension(content);
		gui.setSize(dimension);
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

}
