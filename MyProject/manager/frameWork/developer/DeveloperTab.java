package frameWork.developer;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JPanel;

import frameWork.developer.authority.Authority;
import frameWork.developer.database.DbCreate;
import frameWork.developer.lock.Lock;
import frameWork.developer.routing.Routing;
import frameWork.manager.tab.TabPanel;
import frameWork.manager.tab.TabbedPanel;

public class DeveloperTab extends TabPanel {
	private Authority authority;
	private Lock lock;
	private DbCreate database;
	private Routing routing;
	
	public DeveloperTab() {
		initialize();
		update();
	}
	
	private void initialize() {
		setLayout(new BorderLayout(0, 0));
		{
			final TabbedPanel tabbedPanel = new TabbedPanel();
			add(tabbedPanel, BorderLayout.CENTER);
			{
				authority = new Authority();
				tabbedPanel.addTab("New tab", null, this.authority, null);
				
				database = new DbCreate();
				tabbedPanel.addTab("New tab", null, this.database, null);
				
				routing = new Routing();
				tabbedPanel.addTab("New tab", null, this.routing, null);
				
				lock = new Lock();
				tabbedPanel.addTab("New tab", null, this.lock, null);
			}
		}
		{
			final JPanel panel_1 = new JPanel();
			add(panel_1, BorderLayout.SOUTH);
			final FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
			flowLayout.setAlignment(FlowLayout.RIGHT);
			{
				final JButton btnNewButton_1 = new JButton("ビルド");
				btnNewButton_1.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(final ActionEvent e) {
						if (new File(Constant.InfoFile).exists()) {
							EventQueue.invokeLater(new Runnable() {
								@Override
								public void run() {
									authority.create();
									database.create();
									routing.create();
									lock.create();
								}
							});
						}
					}
				});
				panel_1.add(btnNewButton_1);
			}
		}
	}
	
	@Override
	public void update() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				final File file = new File(Constant.InfoFile);
				if (file.exists()) {
					Authority.createFile();
					DbCreate.createFile();
					Routing.createFile();
					Lock.createFile();
					setEnabled(true);
					authority.update();
					database.update();
					routing.update();
					lock.update();
				}
				else {
					setEnabled(false);
				}
			}
		});
	}
	
	@Override
	public String getTabName() {
		return "開発管理";
	}
}