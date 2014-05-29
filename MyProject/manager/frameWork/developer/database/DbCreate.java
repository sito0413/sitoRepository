package frameWork.developer.database;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import frameWork.architect.database.Database;
import frameWork.architect.database.Field;
import frameWork.architect.database.Table;
import frameWork.developer.Constant;
import frameWork.manager.tab.TabPanel;

@SuppressWarnings({
        "rawtypes", "unchecked"
})
public class DbCreate extends TabPanel {
	private JList databaseList;
	private JList tableList;
	private JList fieldList;
	private JScrollPane scrollPane1;
	private JScrollPane scrollPane2;
	private JScrollPane scrollPane3;
	private JButton button;
	private JButton button3;
	private JButton button2;
	private JButton button1;
	
	public static void createFile() {
		Database.createFile();
	}
	
	public DbCreate() {
		initialize();
	}
	
	private void initialize() {
		setLayout(new BorderLayout(0, 0));
		{
			final JPanel panel = new JPanel();
			final FlowLayout flowLayout = (FlowLayout) panel.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			add(panel, BorderLayout.NORTH);
			{
				{
					button = new JButton("Class作成");
					this.button.setIcon(new ImageIcon(DbCreate.class.getResource("/frameWork/architect/cog.png")));
					panel.add(button);
					button1 = new JButton("データベース編集");
					panel.add(this.button1);
					this.button1.setIcon(new ImageIcon(DbCreate.class.getResource("/frameWork/architect/pencil.png")));
					button1.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(final ActionEvent e) {
							Database.edit();
						}
					});
					button.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(final ActionEvent e) {
							create();
						}
					});
				}
			}
		}
		{
			final JPanel panel = new JPanel();
			add(panel, BorderLayout.CENTER);
			final GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWidths = new int[] {
			        0, 0, 0
			};
			gbl_panel.rowHeights = new int[] {
			        0, 0
			};
			gbl_panel.columnWeights = new double[] {
			        0.0, 0.0, 1.0
			};
			gbl_panel.rowWeights = new double[] {
			        1.0, Double.MIN_VALUE
			};
			panel.setLayout(gbl_panel);
			{
				final JPanel panel_2 = new JPanel();
				final GridBagConstraints gbc_panel_2 = new GridBagConstraints();
				gbc_panel_2.fill = GridBagConstraints.BOTH;
				gbc_panel_2.insets = new Insets(0, 0, 0, 5);
				gbc_panel_2.gridx = 0;
				gbc_panel_2.gridy = 0;
				panel.add(panel_2, gbc_panel_2);
				panel_2.setLayout(new BorderLayout(0, 0));
				{
					final JPanel panel_1 = new JPanel();
					final FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
					flowLayout.setAlignment(FlowLayout.LEFT);
					panel_2.add(panel_1, BorderLayout.SOUTH);
					{
						button2 = new JButton("テーブル編集");
						panel_1.add(this.button2);
						this.button2.setIcon(new ImageIcon(DbCreate.class
						        .getResource("/frameWork/architect/pencil.png")));
						button2.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(final ActionEvent e) {
								final Database d = (Database) databaseList.getSelectedValue();
								if (d != null) {
									Table.edit(d.name);
								}
							}
						});
					}
					{
						scrollPane1 = new JScrollPane();
						panel_2.add(scrollPane1, BorderLayout.CENTER);
						{
							databaseList = new JList();
							this.databaseList.addListSelectionListener(new ListSelectionListener() {
								@Override
								public void valueChanged(final ListSelectionEvent e) {
									final Database d = (Database) databaseList.getSelectedValue();
									if (d == null) {
										scrollPane2.setVisible(false);
										button3.setVisible(false);
									}
									else {
										final List<Table> table = Table.getTable(d.name);
										if (table.isEmpty()) {
											scrollPane2.setVisible(false);
											button3.setVisible(false);
										}
										else {
											scrollPane2.setVisible(true);
											button3.setVisible(true);
											tableList.setModel(new AbstractListModel() {
												@Override
												public int getSize() {
													return table.size();
												}
												
												@Override
												public Object getElementAt(final int index) {
													if (getSize() <= index) {
														return null;
													}
													return table.get(index);
												}
											});
										}
									}
									scrollPane3.setVisible(false);
									DbCreate.this.revalidate();
								}
							});
							databaseList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
							scrollPane1.setViewportView(databaseList);
						}
					}
					{
						final JPanel panel_3 = new JPanel();
						final GridBagConstraints gbc_panel_3 = new GridBagConstraints();
						gbc_panel_3.insets = new Insets(0, 0, 0, 5);
						gbc_panel_3.fill = GridBagConstraints.BOTH;
						gbc_panel_3.gridx = 1;
						gbc_panel_3.gridy = 0;
						panel.add(panel_3, gbc_panel_3);
						panel_3.setLayout(new BorderLayout(0, 0));
						{
							scrollPane2 = new JScrollPane();
							panel_3.add(scrollPane2, BorderLayout.CENTER);
							{
								tableList = new JList();
								this.tableList.addListSelectionListener(new ListSelectionListener() {
									@Override
									public void valueChanged(final ListSelectionEvent e) {
										final Database d = (Database) databaseList.getSelectedValue();
										final Table t = (Table) tableList.getSelectedValue();
										if (t == null) {
											scrollPane3.setVisible(false);
										}
										else {
											final List<Field> field = Field.getField(d.name, t.name);
											if (field.isEmpty()) {
												scrollPane3.setVisible(false);
											}
											else {
												scrollPane3.setVisible(true);
												fieldList.setModel(new AbstractListModel() {
													
													@Override
													public int getSize() {
														return field.size();
													}
													
													@Override
													public Object getElementAt(final int index) {
														if (getSize() <= index) {
															return null;
														}
														return field.get(index);
													}
												});
											}
										}
										DbCreate.this.revalidate();
									}
								});
								tableList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
								scrollPane2.setViewportView(tableList);
							}
						}
						{
							final JPanel panel_4 = new JPanel();
							panel_3.add(panel_4, BorderLayout.SOUTH);
							final FlowLayout fl_panel_4 = (FlowLayout) panel_4.getLayout();
							fl_panel_4.setAlignment(FlowLayout.LEFT);
							{
								button3 = new JButton("フィールド編集");
								panel_4.add(this.button3);
								this.button3.setIcon(new ImageIcon(DbCreate.class
								        .getResource("/frameWork/architect/pencil.png")));
								button3.addActionListener(new ActionListener() {
									@Override
									public void actionPerformed(final ActionEvent e) {
										final Database d = (Database) databaseList.getSelectedValue();
										if (d != null) {
											final Table t = (Table) tableList.getSelectedValue();
											if (t != null) {
												Field.edit(d.name, t.name);
											}
										}
									}
								});
							}
						}
					}
					{
						final JPanel panel_3 = new JPanel();
						final GridBagConstraints gbc_panel_3 = new GridBagConstraints();
						gbc_panel_3.fill = GridBagConstraints.BOTH;
						gbc_panel_3.gridx = 2;
						gbc_panel_3.gridy = 0;
						panel.add(panel_3, gbc_panel_3);
						panel_3.setLayout(new BorderLayout(0, 0));
						{
							scrollPane3 = new JScrollPane();
							panel_3.add(scrollPane3, BorderLayout.CENTER);
							{
								fieldList = new JList();
								fieldList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
								scrollPane3.setViewportView(fieldList);
							}
						}
					}
				}
			}
		}
	}
	
	@Override
	public void update() {
		if (new File(Constant.InfoFile).exists()) {
			setEnabled(true);
			button.setEnabled(true);
			button1.setVisible(true);
			final List<Database> database = Database.getDatabase();
			if (database.isEmpty()) {
				scrollPane1.setVisible(false);
				button2.setVisible(false);
			}
			else {
				scrollPane1.setVisible(true);
				button2.setVisible(true);
				databaseList.setModel(new AbstractListModel() {
					@Override
					public int getSize() {
						return database.size();
					}
					
					@Override
					public Object getElementAt(final int index) {
						if (getSize() <= index) {
							return null;
						}
						return database.get(index);
					}
				});
			}
		}
		else {
			setEnabled(false);
			button.setEnabled(false);
			button1.setVisible(false);
			scrollPane1.setVisible(false);
			button2.setVisible(false);
		}
		scrollPane2.setVisible(false);
		button3.setVisible(false);
		scrollPane3.setVisible(false);
		revalidate();
		
	}
	
	public void create() {
		Database.createJavaFile();
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				update();
			}
		});
	}
	
	@Override
	public String getTabName() {
		return "DB定義";
	}
}
