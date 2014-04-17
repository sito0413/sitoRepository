package frameWork.manager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JTextField;

import frameWork.manager.core.SettingPanel;

public class Info extends SettingPanel {
	private JTextField textField;
	private JTextField txtCnewwavesystem;
	
	public Info() {
		setLayout(null);
		{
			this.txtCnewwavesystem = new JTextField();
			this.txtCnewwavesystem.setBounds(5, 5, 150, 28);
			this.txtCnewwavesystem.setText("C:/newwave/_system");
			add(this.txtCnewwavesystem);
		}
		{
			this.textField = new JTextField();
			this.textField.setBounds(5, 38, 150, 28);
			add(this.textField);
			this.textField.setColumns(10);
		}
		{
			final JButton btnNewButton = new JButton("info.xml作成");
			btnNewButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					create(txtCnewwavesystem.getText(), textField.getText());
				}
			});
			btnNewButton.setBounds(155, 38, 52, 28);
			add(btnNewButton);
		}
	}
	
	private void create(final String path, final String systemID) {
		final Properties properties = new Properties();
		properties.setProperty("Path", path);
		if (!systemID.isEmpty()) {
			properties.setProperty("SystemID", systemID);
		}
		try (FileOutputStream os = new FileOutputStream("./src/info.xml")) {
			properties.storeToXML(os, "");
		}
		catch (final IOException e1) {
			e1.printStackTrace();
		}
	}
	
	@Override
	public String getListName() {
		return "Info作成";
	}
}
