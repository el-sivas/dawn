package de.elsivas.odd.standalone.taskmanager;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;

import de.elsivas.basic.SimpleLogFactory;
import de.elsivas.basic.gui.BasicGuiConfig;

public class TMGuiConfig implements BasicGuiConfig {

	private static final Log LOG = SimpleLogFactory.getLog(TMGuiConfig.class);

	private final JPanel root = new JPanel();

	private final JPanel inner = new JPanel();

	private final JButton button = new JButton("");

	private final JLabel label = new JLabel("", SwingConstants.CENTER);

	private final JLabel imageLabel = new JLabel();

	private boolean fullscreen;

	public static TMGuiConfig getConfig(final boolean fullscreen) {
		final TMGuiConfig config = new TMGuiConfig();
		final BoxLayout box = new BoxLayout(config.inner, BoxLayout.PAGE_AXIS);
		final FlowLayout flow = new FlowLayout(FlowLayout.CENTER);

		config.root.setLayout(flow);

		config.inner.setLayout(box);
		config.inner.setAlignmentY(Component.CENTER_ALIGNMENT);

		config.root.add(config.inner);

		config.inner.add(config.label);
		config.inner.add(config.imageLabel);
		config.inner.setBackground(Color.BLACK);
		config.root.setBackground(Color.BLACK);

		config.fullscreen = fullscreen;
		return config;
	}

	@Override
	public boolean isFullscreen() {
		return fullscreen;
	}

	@Override
	public boolean isWithDecoration() {
		return !fullscreen;
	}

	@Override
	public Component getRootPane() {
		return root;
	}

	public void setImage(final String file) {
		if (StringUtils.isEmpty(file)) {
			imageLabel.setIcon(null);
			return;
		}
		LOG.debug("set image: " + file);
		try {
			imageLabel.setIcon(new ImageIcon(ImageIO.read(new File(file))));
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void setText(final String text) {
		label.setText("<html><font size=\"8\"color='red'>" + text + "</font></html>");
	}

}
