package frameWork.developer.installer.gui;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

class ChooseDirectory extends JPanel {
	private String installDirectory;
	
	ChooseDirectory(final Frame frame, final String installDirectory) {
		super(new BorderLayout());
		this.installDirectory = installDirectory;
		final JPanel directoryPanel = new JPanel(new GridBagLayout());
		final GridBagConstraints c = new GridBagConstraints();
		
		//new line
		c.gridy++;
		
		//message
		final JTextField field = new JTextField(installDirectory);
		field.setEditable(false);
		
		c.insets.bottom = 3;
		c.gridx = 0;
		c.gridwidth = 3;
		c.insets.left = 0;
		c.insets.right = 0;
		c.anchor = GridBagConstraints.LINE_START;
		
		c.insets.bottom = 12;
		c.gridx = 0;
		c.gridy++;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.LINE_END;
		directoryPanel.add(new JLabel("インストール先:", SwingConstants.RIGHT), c);
		
		c.gridx = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.CENTER;
		c.insets.left = 12;
		c.insets.right = 12;
		c.weightx = 1.0;
		directoryPanel.add(field, c);
		
		final JButton choose = new JButton("変更");
		choose.setRequestFocusEnabled(false);
		choose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent evt) {
				final File directory = new File(field.getText());
				final JFileChooser chooser = new JFileChooser(directory.getParent());
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setSelectedFile(directory);
				if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
					field.setText(chooser.getSelectedFile().getPath());
					ChooseDirectory.this.installDirectory = field.getText();
				}
			}
		});
		c.gridx = 2;
		c.insets.left = 0;
		c.insets.right = 0;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0;
		directoryPanel.add(choose, c);
		add(BorderLayout.NORTH, directoryPanel);
	}
	
	public String getInstallDirectory() {
		return installDirectory;
	}
}