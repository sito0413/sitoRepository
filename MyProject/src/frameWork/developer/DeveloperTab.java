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
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import frameWork.architect.Tab;
import frameWork.developer.authority.Authority;
import frameWork.developer.database.Database;
import frameWork.developer.info.Info;
import frameWork.developer.routing.Routing;

@SuppressWarnings({
        "rawtypes", "unchecked"
})
public class DeveloperTab extends Tab {
	private final CardLayout cl_panel = new CardLayout(0, 0);
	private Info info;
	private Authority authority;
	private Database database;
	private Routing routing;
	private JPanel panel;
	private final JPanel panel_2 = new JPanel();
	final List<String> __list = new ArrayList<>();
	
	public DeveloperTab() {
		initialize();
		update();
	}
	
	private void initialize() {
		setLayout(new BorderLayout(0, 0));
		{
			final JScrollPane scrollPane = new JScrollPane();
			add(scrollPane, BorderLayout.CENTER);
			{
				panel = new JPanel();
				scrollPane.setViewportView(panel);
				panel.setLayout(cl_panel);
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
				{
					routing = new Routing();
					this.panel.add(routing, routing.getListName());
					__list.add(routing.getListName());
				}
			}
		}
		{
			final JScrollPane scrollPane = new JScrollPane();
			add(scrollPane, BorderLayout.WEST);
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
		add(this.panel_2, BorderLayout.NORTH);
	}
	
	private void update() {
		Info.createFile();
		Authority.createFile();
		Database.createFile();
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				info.update();
				authority.update();
				database.update();
				routing.update();
			}
		});
	}
	
	@Override
	public Icon getTabIcon() {
		return null;
	}
	
	@Override
	public String gettTabName() {
		return "プロジェクト管理";
	}
}