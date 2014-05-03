package frameWork.manager.info;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import frameWork.manager.SettingPanel;

public class Info extends SettingPanel {
	private static String FILE_NAME = "./src/info.xml";
	private JTextField textField;
	private JTextField txtCnewwavesystem;
	private JLabel lblPath;
	private JLabel lblSdystemId;
	private JPanel panel;
	private JPanel panel_1;
	private JLabel label;
	
	public Info() {
		initialize();
	}
	
	private void initialize() {
		setLayout(new BorderLayout(10, 0));
		{
			this.panel = new JPanel();
			add(this.panel, BorderLayout.CENTER);
			final GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWidths = new int[] {
			        10, 200, 0, 0
			};
			gbl_panel.rowHeights = new int[] {
			        0, 0, 0
			};
			gbl_panel.columnWeights = new double[] {
			        0.0, 0.0, 0.0, Double.MIN_VALUE
			};
			gbl_panel.rowWeights = new double[] {
			        0.0, 0.0, Double.MIN_VALUE
			};
			this.panel.setLayout(gbl_panel);
			{
				this.lblPath = new JLabel("PATH");
				final GridBagConstraints gbc_lblPath = new GridBagConstraints();
				gbc_lblPath.fill = GridBagConstraints.HORIZONTAL;
				gbc_lblPath.insets = new Insets(0, 0, 5, 5);
				gbc_lblPath.gridx = 0;
				gbc_lblPath.gridy = 0;
				this.panel.add(this.lblPath, gbc_lblPath);
			}
			{
				this.txtCnewwavesystem = new JTextField();
				final GridBagConstraints gbc_txtCnewwavesystem = new GridBagConstraints();
				gbc_txtCnewwavesystem.fill = GridBagConstraints.HORIZONTAL;
				gbc_txtCnewwavesystem.insets = new Insets(0, 0, 5, 5);
				gbc_txtCnewwavesystem.gridx = 1;
				gbc_txtCnewwavesystem.gridy = 0;
				this.panel.add(this.txtCnewwavesystem, gbc_txtCnewwavesystem);
				this.txtCnewwavesystem.setText("C:/newwave/_system");
			}
			{
				this.lblSdystemId = new JLabel("SDYSTEM ID");
				final GridBagConstraints gbc_lblSdystemId = new GridBagConstraints();
				gbc_lblSdystemId.fill = GridBagConstraints.HORIZONTAL;
				gbc_lblSdystemId.insets = new Insets(0, 0, 0, 5);
				gbc_lblSdystemId.gridx = 0;
				gbc_lblSdystemId.gridy = 1;
				this.panel.add(this.lblSdystemId, gbc_lblSdystemId);
			}
			{
				this.textField = new JTextField();
				final GridBagConstraints gbc_textField = new GridBagConstraints();
				gbc_textField.insets = new Insets(0, 0, 0, 5);
				gbc_textField.fill = GridBagConstraints.HORIZONTAL;
				gbc_textField.gridx = 1;
				gbc_textField.gridy = 1;
				this.panel.add(this.textField, gbc_textField);
				this.textField.setColumns(10);
			}
		}
		{
			this.panel_1 = new JPanel();
			final FlowLayout flowLayout = (FlowLayout) this.panel_1.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			add(this.panel_1, BorderLayout.NORTH);
			final JButton btnNewButton = new JButton("info.xml作成");
			this.panel_1.add(btnNewButton);
			btnNewButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					create(txtCnewwavesystem.getText(), textField.getText());
				}
			});
		}
		{
			this.label = new JLabel("");
			add(this.label, BorderLayout.WEST);
		}
	}
	
	private void create(final String path, final String systemID) {
		final Properties properties = new Properties();
		properties.setProperty("Path", path);
		if (!systemID.isEmpty()) {
			properties.setProperty("SystemID", systemID);
		}
		try (FileOutputStream os = new FileOutputStream(FILE_NAME)) {
			properties.storeToXML(os, "");
		}
		catch (final IOException e1) {
			e1.printStackTrace();
		}
	}
	
	@Override
	public void update() {
		final Properties properties = new Properties();
		try {
			final File file = new File(FILE_NAME);
			if (file.exists()) {
				try (FileInputStream fileInputStream = new FileInputStream(file)) {
					properties.loadFromXML(fileInputStream);
					if (properties.getProperty("Path") != null) {
						txtCnewwavesystem.setText(properties.getProperty("Path"));
					}
					if (properties.getProperty("SystemID") != null) {
						textField.setText(properties.getProperty("SystemID"));
					}
				}
			}
		}
		catch (final Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String getListName() {
		return "Info作成";
	}
}
