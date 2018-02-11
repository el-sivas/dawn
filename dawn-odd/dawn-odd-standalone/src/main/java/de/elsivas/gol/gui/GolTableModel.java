package de.elsivas.gol.gui;

import javax.swing.table.AbstractTableModel;

public class GolTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -5808120226452130117L;

	private Object[][] data;

	public GolTableModel(Object[][] data) {
		this.data = data;
	}

	@Override
	public int getRowCount() {
		return data.length;
	}

	@Override
	public int getColumnCount() {
		return data.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return data[rowIndex][columnIndex];
	}

}
