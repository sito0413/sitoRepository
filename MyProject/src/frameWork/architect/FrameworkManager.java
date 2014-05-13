package frameWork.architect;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import frameWork.developer.DeveloperTab;
import frameWork.operator.OperatorTab;

public class FrameworkManager {
	
	private JFrame frame;
	private DeveloperTab developerTab;
	private OperatorTab operatorTab;
	
	public static void main(final String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		}
		catch (final Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					final FrameworkManager window = new FrameworkManager();
					window.frame.setVisible(true);
				}
				catch (final Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public FrameworkManager() {
		initialize();
	}
	
	private void initialize() {
		this.frame = new JFrame();
		this.frame.setTitle("フレームワーク管理ツール");
		this.frame.setBounds(100, 100, 800, 550);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		{
			final JTabbedPane tabbedPane = new JTabbedPane(SwingConstants.TOP);
			this.frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
			{
				developerTab = new DeveloperTab();
				tabbedPane.addTab(developerTab.gettTabName(), developerTab.getTabIcon(), this.developerTab, null);
			}
			{
				this.operatorTab = new OperatorTab();
				tabbedPane.addTab(operatorTab.gettTabName(), operatorTab.getTabIcon(), this.operatorTab, null);
			}
		}
		
	}
}
