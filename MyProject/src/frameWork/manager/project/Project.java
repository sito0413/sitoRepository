package frameWork.manager.project;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import frameWork.manager.SettingPanel;
import frameWork.manager.authority.Authority;
import frameWork.manager.database.Database;

public class Project extends SettingPanel {
	private JCheckBox authority;
	private JCheckBox database;
	private JCheckBox config;
	
	public Project() {
		initialize();
	}
	
	private void initialize() {
		setLayout(new BorderLayout(10, 0));
		{
			final JPanel panel = new JPanel();
			final FlowLayout flowLayout = (FlowLayout) panel.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			add(panel, BorderLayout.NORTH);
			final JButton btnNewButton_1 = new JButton("作成");
			panel.add(btnNewButton_1);
			btnNewButton_1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					create();
				}
			});
		}
		{
			final JPanel panel = new JPanel();
			add(panel);
			final GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWidths = new int[] {
			        0, 0
			};
			gbl_panel.rowHeights = new int[] {
			        0, 0, 0, 0
			};
			gbl_panel.columnWeights = new double[] {
			        0.0, Double.MIN_VALUE
			};
			gbl_panel.rowWeights = new double[] {
			        0.0, 0.0, 0.0, Double.MIN_VALUE
			};
			panel.setLayout(gbl_panel);
			{
				authority = new JCheckBox("権限定義");
				this.authority.setSelected(true);
				final GridBagConstraints gbc_authority = new GridBagConstraints();
				gbc_authority.anchor = GridBagConstraints.WEST;
				gbc_authority.insets = new Insets(0, 0, 5, 0);
				gbc_authority.gridx = 0;
				gbc_authority.gridy = 0;
				panel.add(authority, gbc_authority);
			}
			{
				database = new JCheckBox("データベース定義");
				this.database.setSelected(true);
				final GridBagConstraints gbc_database = new GridBagConstraints();
				gbc_database.insets = new Insets(0, 0, 5, 0);
				gbc_database.anchor = GridBagConstraints.WEST;
				gbc_database.gridx = 0;
				gbc_database.gridy = 1;
				panel.add(database, gbc_database);
			}
			{
				config = new JCheckBox("設定ファイル");
				this.config.setSelected(true);
				final GridBagConstraints gbc_config = new GridBagConstraints();
				gbc_config.anchor = GridBagConstraints.WEST;
				gbc_config.gridx = 0;
				gbc_config.gridy = 2;
				panel.add(config, gbc_config);
			}
		}
		{
			final JLabel label = new JLabel("");
			add(label, BorderLayout.WEST);
		}
	}
	
	private void create() {
		new File("src").mkdirs();
		if (authority.isSelected()) {
			Authority.createFile();
		}
		if (database.isSelected()) {
			Database.createFile();
		}
	}
	
	@Override
	public String getListName() {
		return "プロジェクト作成";
	}
	
}
