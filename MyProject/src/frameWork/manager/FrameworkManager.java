package frameWork.manager;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.EventQueue;
import java.io.File;

import javax.swing.AbstractListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

@SuppressWarnings({
        "rawtypes", "unchecked"
})
public class FrameworkManager {
	
	private JFrame frame;
	private final CardLayout cl_panel = new CardLayout(0, 0);
	private JPanel panel;
	
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
		this.frame.setBounds(100, 100, 450, 300);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		{
			final JTabbedPane tabbedPane = new JTabbedPane(SwingConstants.TOP);
			this.frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
			{
				final JPanel panel_1 = new JPanel();
				tabbedPane.addTab("サーバー管理", null, panel_1, null);
			}
			{
				final JPanel panel_1 = new JPanel();
				tabbedPane.addTab("プロジェクト管理", null, panel_1, null);
				panel_1.setLayout(new BorderLayout(0, 0));
				{
					final JScrollPane scrollPane = new JScrollPane();
					panel_1.add(scrollPane, BorderLayout.CENTER);
					{
						panel = new JPanel();
						scrollPane.setViewportView(panel);
						panel.setLayout(cl_panel);
						{
							final Project project = new Project();
							this.panel.add(project, project.getListName());
						}
						{
							final Info info = new Info();
							this.panel.add(info, info.getListName());
						}
						{
							final Authority authority = new Authority();
							this.panel.add(authority, authority.getListName());
						}
					}
				}
				{
					final JScrollPane scrollPane = new JScrollPane();
					panel_1.add(scrollPane, BorderLayout.WEST);
					{
						final JList list = new JList();
						list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						list.addListSelectionListener(new ListSelectionListener() {
							@Override
							public void valueChanged(final ListSelectionEvent e) {
								try {
									cl_panel.show(panel, list.getSelectedValue().toString());
								}
								catch (final Exception exp) {
									//NOP
								}
							}
						});
						list.setModel(new AbstractListModel() {
							String[] values = new String[] {
							        "プロジェクト作成", "Info作成", "権限作成"
							};
							
							@Override
							public int getSize() {
								return values.length;
							}
							
							@Override
							public Object getElementAt(final int index) {
								return values[index];
							}
						});
						list.setSelectedIndex(0);
						scrollPane.setViewportView(list);
					}
				}
				{
					final JLabel lblNewLabel = new JLabel(new File("").getAbsolutePath());
					panel_1.add(lblNewLabel, BorderLayout.NORTH);
				}
			}
		}
	}
}
