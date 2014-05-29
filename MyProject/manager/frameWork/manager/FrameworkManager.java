package frameWork.manager;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import frameWork.architect.ArchitectTab;
import frameWork.architect.Literal;
import frameWork.base.core.fileSystem.FileSystem;
import frameWork.developer.DeveloperTab;
import frameWork.manager.info.Info;
import frameWork.manager.tab.TabbedPanel;
import frameWork.operator.OperatorTab;

public class FrameworkManager {
	
	private JFrame frame;
	private Info info;
	private ArchitectTab architectTab;
	private DeveloperTab developerTab;
	private OperatorTab operatorTab;
	private TabbedPanel tabbedPanel;
	private final JPanel panel = new JPanel();
	private JLabel ver;
	
	public static void main(final String[] args) {
		Info.createFile();
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
		{
			info.update();
			final Properties properties = new Properties();
			try {
				final File file1 = new File(Literal.src + "/" + Literal.info_xml);
				if (file1.exists()) {
					try (FileInputStream fileInputStream1 = new FileInputStream(file1)) {
						properties.loadFromXML(fileInputStream1);
						if (properties.getProperty(Literal.Developer) == null) {
							this.operatorTab = new OperatorTab();
							tabbedPanel.addTab(operatorTab.getTabName(), operatorTab.getTabIcon(), this.operatorTab,
							        null);
							return;
						}
					}
				}
			}
			catch (final Exception e) {
				e.printStackTrace();
			}
			{
				
				{
					developerTab = new DeveloperTab();
					this.tabbedPanel.addTab("New tab", null, this.developerTab, null);
					developerTab.update();
				}
				{
					this.operatorTab = new OperatorTab();
					this.tabbedPanel.addTab("New tab", null, this.operatorTab, null);
					operatorTab.update();
				}
				{
					architectTab = new ArchitectTab();
					this.tabbedPanel.addTab("New tab", null, this.architectTab, null);
				}
			}
		}
		{
			try {
				final URL url = FileSystem.class.getResource("/frameWork/architect/info.xml");
				if (url != null) {
					final URLConnection connection = url.openConnection();
					if (connection != null) {
						final Properties properties = new Properties();
						properties.loadFromXML(connection.getInputStream());
						ver.setText(properties.getProperty("Ver", "--------------"));
					}
					else {
						ver.setText("NOT CONNECT");
					}
				}
				else {
					ver.setText("NOT BUILD");
				}
			}
			catch (final IOException e1) {
				ver.setText("ERROR");
			}
		}
	}
	
	private void initialize() {
		this.frame = new JFrame();
		this.frame.setIconImage(Toolkit.getDefaultToolkit().getImage(
		        FrameworkManager.class.getResource("/frameWork/architect/application_form_edit.png")));
		final BorderLayout borderLayout = (BorderLayout) this.frame.getContentPane().getLayout();
		borderLayout.setVgap(5);
		borderLayout.setHgap(5);
		this.frame.setTitle("フレームワーク管理ツール");
		this.frame.setBounds(100, 100, 800, 550);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.getContentPane().add(this.panel, BorderLayout.CENTER);
		this.panel.setLayout(new BorderLayout(5, 5));
		final JPanel panel_1 = new JPanel();
		this.panel.add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(new BorderLayout(0, 0));
		{
			final JButton btnNewButton = new JButton("");
			btnNewButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					EventQueue.invokeLater(new Runnable() {
						@Override
						public void run() {
							info.update();
							developerTab.update();
							operatorTab.update();
						}
					});
				}
			});
			btnNewButton.setIconTextGap(0);
			btnNewButton.setIcon(new ImageIcon(FrameworkManager.class
			        .getResource("/frameWork/architect/arrow_refresh.png")));
			panel_1.add(btnNewButton, BorderLayout.EAST);
		}
		{
			final JTextField lblNewLabel = new JTextField(" " + new File("").getAbsolutePath());
			lblNewLabel.setEditable(false);
			panel_1.add(lblNewLabel, BorderLayout.CENTER);
		}
		{
			final JLabel label = new JLabel("");
			label.setIcon(new ImageIcon(FrameworkManager.class.getResource("/frameWork/architect/folder.png")));
			panel_1.add(label, BorderLayout.WEST);
		}
		{
			info = new Info();
		}
		this.tabbedPanel = new TabbedPanel();
		this.panel.add(this.tabbedPanel, BorderLayout.CENTER);
		{
			final JPanel panel_2 = new JPanel();
			final FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
			flowLayout.setAlignment(FlowLayout.RIGHT);
			this.panel.add(panel_2, BorderLayout.SOUTH);
			{
				ver = new JLabel("");
				panel_2.add(ver);
			}
		}
		tabbedPanel.addTab("New tab", null, this.info, null);
		{
			final JLabel lblNewLabel_3 = new JLabel("");
			this.frame.getContentPane().add(lblNewLabel_3, BorderLayout.NORTH);
		}
		{
			final JLabel lblNewLabel_2 = new JLabel("");
			this.frame.getContentPane().add(lblNewLabel_2, BorderLayout.SOUTH);
		}
		{
			final JLabel lblNewLabel_1 = new JLabel("");
			this.frame.getContentPane().add(lblNewLabel_1, BorderLayout.WEST);
		}
		{
			final JLabel lblNewLabel_4 = new JLabel("");
			this.frame.getContentPane().add(lblNewLabel_4, BorderLayout.EAST);
		}
		
	}
}
