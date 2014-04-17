package frameWork.manager;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;

import frameWork.manager.core.SettingPanel;

public class Project extends SettingPanel {
	public Project() {
		setLayout(new FlowLayout(FlowLayout.LEFT));
		final JButton btnNewButton_1 = new JButton("作成");
		btnNewButton_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				create();
			}
		});
		add(btnNewButton_1);
	}
	
	private void create() {
		new File("src").mkdirs();
		Authority.createFile();
	}
	
	@Override
	public String getListName() {
		return "プロジェクト作成";
	}
}
