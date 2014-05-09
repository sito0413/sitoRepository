package frameWork.developer;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
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

import frameWork.developer.authority.Authority;
import frameWork.developer.database.Database;
import frameWork.developer.info.Info;
import frameWork.developer.project.Project;

@SuppressWarnings({
        "rawtypes", "unchecked"
})
public class FrameworkManager {
	
	private JFrame frame;
	private final CardLayout cl_panel = new CardLayout(0, 0);
	private JPanel panel;
	private final JPanel panel_2 = new JPanel();
	private Project project;
	private Info info;
	private Authority authority;
	private Database database;
	
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
		update();
	}
	
	private void update() {
		project.update();
		info.update();
		authority.update();
		database.update();
	}
	
	private void initialize() {
		this.frame = new JFrame();
		this.frame.setTitle("フレームワーク管理ツール");
		this.frame.setBounds(100, 100, 600, 450);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		{
			final JTabbedPane tabbedPane = new JTabbedPane(SwingConstants.TOP);
			this.frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
			{
				final List<String> __list = new ArrayList<>();
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
							project = new Project();
							this.panel.add(project, project.getListName());
							__list.add(project.getListName());
						}
						{
							info = new Info();
							this.panel.add(info, info.getListName());
							__list.add(info.getListName());
						}
						{
							authority = new Authority();
							this.panel.add(authority, authority.getListName());
							__list.add(authority.getListName());
						}
						{
							database = new Database();
							this.panel.add(database, database.getListName());
							__list.add(database.getListName());
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
							
							@Override
							public int getSize() {
								return __list.size();
							}
							
							@Override
							public Object getElementAt(final int index) {
								return __list.get(index);
							}
						});
						list.setSelectedIndex(0);
						scrollPane.setViewportView(list);
					}
				}
				panel_1.add(this.panel_2, BorderLayout.NORTH);
			}
		}
		this.panel_2.setLayout(new BorderLayout(0, 0));
		{
			final JLabel lblNewLabel = new JLabel(new File("").getAbsolutePath());
			this.panel_2.add(lblNewLabel);
		}
		{
			final JButton btnNewButton = new JButton("更新");
			btnNewButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					update();
				}
			});
			this.panel_2.add(btnNewButton, BorderLayout.EAST);
		}
	}
}
