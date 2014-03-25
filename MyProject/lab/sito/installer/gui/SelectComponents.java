package sito.installer.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import sito.installer.InstallComponent;

class SelectComponents extends JPanel implements ActionListener {
	private final JLabel sizeLabel;
	private final List<InstallComponent> componentList;
	private final List<JCheckBox> checkBoxs;
	
	SelectComponents(final List<InstallComponent> componentList) {
		super(new BorderLayout());
		this.componentList = componentList;
		this.sizeLabel = new JLabel("", SwingConstants.LEFT);
		
		this.checkBoxs = new ArrayList<>();
		final JPanel panel = new JPanel(new GridLayout(componentList.size(), 1));
		for (int i = 0; i < componentList.size(); i++) {
			final JCheckBox checkBox = new JCheckBox(componentList.get(i).getLabel());
			checkBox.getModel().setSelected(true);
			checkBox.addActionListener(this);
			checkBox.setRequestFocusEnabled(false);
			panel.add(checkBox);
			checkBoxs.add(checkBox);
		}
		final Dimension dim = panel.getPreferredSize();
		dim.width = Integer.MAX_VALUE;
		panel.setMaximumSize(dim);
		final JPanel viewportView = new JPanel(new BorderLayout());
		viewportView.add(BorderLayout.NORTH, panel);
		final JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(viewportView);
		add(BorderLayout.CENTER, scrollPane);
		add(BorderLayout.SOUTH, sizeLabel);
		updateSize();
	}
	
	@Override
	public void actionPerformed(final ActionEvent evt) {
		updateSize();
	}
	
	private void updateSize() {
		sizeLabel.setText("Estimated disk usage of selected" + " components: " + getTotalSize() + "Kb");
	}
	
	List<String> getInstallComponents() {
		final List<String> components = new ArrayList<>();
		for (int i = 0; i < checkBoxs.size(); i++) {
			final JCheckBox checkBox = checkBoxs.get(i);
			final InstallComponent component = componentList.get(i);
			if (checkBox.isSelected()) {
				components.add(component.getName());
			}
		}
		return components;
	}
	
	private int getTotalSize() {
		int size = 0;
		for (int i = 0; i < checkBoxs.size(); i++) {
			final JCheckBox checkBox = checkBoxs.get(i);
			final InstallComponent component = componentList.get(i);
			if (checkBox.isSelected()) {
				size += component.getSize();
			}
		}
		return size;
	}
}