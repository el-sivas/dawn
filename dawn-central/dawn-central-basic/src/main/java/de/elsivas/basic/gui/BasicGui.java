package de.elsivas.basic.gui;

import java.awt.Dimension;

import javax.swing.JFrame;

public class BasicGui extends JFrame {

	private static final long serialVersionUID = -3278948113684630561L;

	public static BasicGui create(BasicGuiConfig config) {
		final BasicGui basicGui = new BasicGui();
		setSize(config, basicGui);
		basicGui.setUndecorated(!config.isWithDecoration());
		basicGui.add(config.getRootPane());

		basicGui.setVisible(true);
		return basicGui;
	}

	private static void setSize(BasicGuiConfig config, final BasicGui basicGui) {
		if (config.isFullscreen()) {
			basicGui.setExtendedState(JFrame.MAXIMIZED_BOTH);
			return;
		}
		basicGui.setSize(dimension(config));
	}

	private static Dimension dimension(BasicGuiConfig config) {
		return new Dimension(800, 600);
	}

}
