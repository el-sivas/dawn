package de.elsivas.gol.gui;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class GolContentValidator {

	private static final Log LOG = LogFactory.getLog(GolContentValidator.class);

	public static void validate(CellContent[][] a) {
		LOG.info("validate...");
		int size = a.length;
		int contentSize = -1;
		for (CellContent[] cellObjects : a) {
			if (cellObjects.length != size) {
				throw new RuntimeException("size invalid");
			}
			LOG.info("...cells...");
			for (CellContent cellContent : cellObjects) {
				if (contentSize < 0) {
					contentSize = cellContent.getContentSize();
				} else {
					if (contentSize != cellContent.getContentSize()) {
						throw new RuntimeException("content size invalid. expected: " + contentSize + " current: "
								+ cellContent.getContentSize());
					}
				}

			}
		}
	}

}
