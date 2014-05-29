package frameWork.manager.info;

import java.awt.BorderLayout;
import java.awt.EventQueue;
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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import frameWork.architect.Literal;
import frameWork.developer.Constant;
import frameWork.manager.tab.TabPanel;

public class Info extends TabPanel {
	private static String Default_Path = "C:/newwave/_system";
	
	public static void createFile() {
		final File file = new File(Constant.TestInfoFile);
		if (!file.exists()) {
			final Properties properties = new Properties();
			properties.setProperty(Literal.Path, new File(Literal.system).getAbsolutePath());
			properties.setProperty(Literal.SystemID, Literal.Temp);
			properties.setProperty(Literal.Developer, Literal.Developer);
			try (FileOutputStream os = new FileOutputStream(file)) {
				properties.storeToXML(os, "");
			}
			catch (final IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	private JTextField textField;
	private JTextField txtCnewwavesystem;
	private JLabel lblPath;
	private JLabel lblSdystemId;
	private JPanel panel;
	private JPanel panel_1;
	private JLabel label;
	private JButton btnNewButton;
	
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
			        10, 200, 0
			};
			gbl_panel.rowHeights = new int[] {
			        0, 0, 0
			};
			gbl_panel.columnWeights = new double[] {
			        0.0, 0.0, Double.MIN_VALUE
			};
			gbl_panel.rowWeights = new double[] {
			        0.0, 0.0, Double.MIN_VALUE
			};
			this.panel.setLayout(gbl_panel);
			{
				this.lblPath = new JLabel(Literal.Path);
				this.lblPath.setIcon(new ImageIcon(Info.class.getResource("/frameWork/architect/folder.png")));
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
				gbc_txtCnewwavesystem.insets = new Insets(0, 0, 5, 0);
				gbc_txtCnewwavesystem.gridx = 1;
				gbc_txtCnewwavesystem.gridy = 0;
				this.panel.add(this.txtCnewwavesystem, gbc_txtCnewwavesystem);
			}
			{
				this.lblSdystemId = new JLabel(Literal.SystemID);
				this.lblSdystemId.setIcon(new ImageIcon(Info.class.getResource("/frameWork/architect/tag_blue.png")));
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
			btnNewButton = new JButton(Literal.info_xml + "作成");
			this.btnNewButton.setIcon(new ImageIcon(Info.class.getResource("/frameWork/architect/cog.png")));
			this.panel_1.add(btnNewButton);
			btnNewButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					create(txtCnewwavesystem.getText(), textField.getText());
					EventQueue.invokeLater(new Runnable() {
						@Override
						public void run() {
							update();
						}
					});
				}
				
			});
		}
		{
			this.label = new JLabel("");
			add(this.label, BorderLayout.WEST);
		}
	}
	
	private void create(final String path, final String systemID) {
		{
			final Properties properties = new Properties();
			properties.setProperty(Literal.Path, new File(Literal.system).getAbsolutePath());
			properties.setProperty(Literal.SystemID, systemID.isEmpty() ? Literal.Temp : systemID);
			properties.setProperty(Literal.Developer, Literal.Developer);
			final File file = new File(Constant.TestInfoFile);
			file.getParentFile().mkdirs();
			try (FileOutputStream os = new FileOutputStream(file)) {
				properties.storeToXML(os, "");
			}
			catch (final IOException e1) {
				e1.printStackTrace();
			}
		}
		{
			final Properties properties = new Properties();
			properties.setProperty(Literal.Path, path);
			properties.setProperty(Literal.SystemID, systemID.isEmpty() ? Literal.Temp : systemID);
			final File file = new File(Constant.InfoFile);
			file.getParentFile().mkdirs();
			try (FileOutputStream os = new FileOutputStream(file)) {
				properties.storeToXML(os, "");
			}
			catch (final IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	@Override
	public void update() {
		final Properties properties = new Properties();
		try {
			final File file1 = new File(Constant.TestInfoFile);
			if (file1.exists()) {
				try (FileInputStream fileInputStream1 = new FileInputStream(file1)) {
					properties.loadFromXML(fileInputStream1);
					if ((properties.getProperty(Literal.SystemID) == null)
					        || properties.getProperty(Literal.SystemID).isEmpty()) {
						txtCnewwavesystem.setText(Default_Path);
						textField.setText(Literal.Temp);
					}
					else {
						final File file2 = new File(Constant.InfoFile);
						if (file2.exists()) {
							try (FileInputStream fileInputStream2 = new FileInputStream(file2)) {
								properties.loadFromXML(fileInputStream2);
								if (properties.getProperty(Literal.Path) != null) {
									txtCnewwavesystem.setText(properties.getProperty(Literal.Path));
								}
								if (properties.getProperty(Literal.SystemID) != null) {
									textField.setText(properties.getProperty(Literal.SystemID));
								}
							}
						}
						else {
							file2.getParentFile().mkdirs();
							txtCnewwavesystem.setText(Default_Path);
							textField.setText(properties.getProperty(Literal.SystemID));
						}
					}
				}
			}
			else {
				file1.getParentFile().mkdirs();
				txtCnewwavesystem.setText(Default_Path);
				textField.setText(Literal.Temp);
			}
		}
		catch (final Exception e) {
			e.printStackTrace();
		}
		if (new File(Constant.InfoFile).exists()) {
			textField.setEditable(false);
			txtCnewwavesystem.setEditable(false);
			btnNewButton.setEnabled(false);
		}
		else {
			textField.setEditable(true);
			txtCnewwavesystem.setEditable(true);
			btnNewButton.setEnabled(true);
		}
	}
	
	@Override
	public String getTabName() {
		return "Info作成";
	}
	
}
