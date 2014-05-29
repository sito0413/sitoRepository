package frameWork.operator;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.File;

import frameWork.developer.Constant;
import frameWork.manager.tab.TabPanel;
import frameWork.manager.tab.TabbedPanel;
import frameWork.operator.fileBrowser.FileBrowser;

public class OperatorTab extends TabPanel {
	private FileBrowser fileBrowser;
	
	public OperatorTab() {
		initialize();
	}
	
	private void initialize() {
		setLayout(new BorderLayout(0, 0));
		{
			final TabbedPanel tabbedPanel = new TabbedPanel();
			add(tabbedPanel, BorderLayout.CENTER);
			{
				fileBrowser = new FileBrowser();
				tabbedPanel.addTab("New tab", null, this.fileBrowser, null);
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
					setEnabled(true);
					fileBrowser.update();
				}
				else {
					setEnabled(false);
				}
			}
		});
	}
	
	@Override
	public String getTabName() {
		return "運用管理";
	}
}
