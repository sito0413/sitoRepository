package frameWork.developer.database;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import frameWork.developer.SettingPanel;

@SuppressWarnings({
        "rawtypes", "unchecked"
})
public class Database extends SettingPanel {
	static String ROOT = "database";
	private JList databaseList;
	private JList tableList;
	private JList fieldList;
	
	public static void createFile() {
		Db.createFile();
	}
	
	public Database() {
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
					final JButton button = new JButton("Class作成");
					panel.add(button);
					button.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(final ActionEvent e) {
							Db.createJavaFile();
							update();
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
			        0, 0, 0, 0
			};
			gbl_panel.rowHeights = new int[] {
			        0, 0
			};
			gbl_panel.columnWeights = new double[] {
			        0.0, 0.0, 0.0, 1.0
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
					panel_2.add(panel_1, BorderLayout.NORTH);
					final JButton button_1 = new JButton("データベース編集");
					panel_1.add(button_1);
					{
						final JScrollPane scrollPane = new JScrollPane();
						panel_2.add(scrollPane, BorderLayout.CENTER);
						{
							databaseList = new JList();
							this.databaseList.addListSelectionListener(new ListSelectionListener() {
								@Override
								public void valueChanged(final ListSelectionEvent e) {
									final Db d = (Db) databaseList.getSelectedValue();
									if (d == null) {
										tableList.setModel(new AbstractListModel() {
											@Override
											public int getSize() {
												return 0;
											}
											
											@Override
											public Object getElementAt(final int index) {
												return null;
											}
										});
									}
									else {
										tableList.setModel(new AbstractListModel() {
											List<Table> table = Table.getTable(d.name);
											
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
									fieldList.setModel(new AbstractListModel() {
										@Override
										public int getSize() {
											return 0;
										}
										
										@Override
										public Object getElementAt(final int index) {
											return null;
										}
									});
									Database.this.revalidate();
								}
							});
							databaseList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
							scrollPane.setViewportView(databaseList);
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
							final JScrollPane scrollPane = new JScrollPane();
							panel_3.add(scrollPane, BorderLayout.CENTER);
							{
								tableList = new JList();
								this.tableList.addListSelectionListener(new ListSelectionListener() {
									@Override
									public void valueChanged(final ListSelectionEvent e) {
										final Db d = (Db) databaseList.getSelectedValue();
										final Table t = (Table) tableList.getSelectedValue();
										if (t == null) {
											fieldList.setModel(new AbstractListModel() {
												@Override
												public int getSize() {
													return 0;
												}
												
												@Override
												public Object getElementAt(final int index) {
													return null;
												}
											});
										}
										else {
											fieldList.setModel(new AbstractListModel() {
												List<Field> field = Field.getField(d.name, t.name);
												
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
										Database.this.revalidate();
									}
								});
								tableList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
								scrollPane.setViewportView(tableList);
							}
						}
						{
							final JPanel panel_4 = new JPanel();
							panel_3.add(panel_4, BorderLayout.NORTH);
							final FlowLayout fl_panel_4 = (FlowLayout) panel_4.getLayout();
							fl_panel_4.setAlignment(FlowLayout.LEFT);
							{
								final JButton button = new JButton("テーブル編集");
								button.addActionListener(new ActionListener() {
									@Override
									public void actionPerformed(final ActionEvent e) {
										final Db d = (Db) databaseList.getSelectedValue();
										if (d != null) {
											Table.edit(d.name);
										}
									}
								});
								panel_4.add(button);
							}
						}
					}
					{
						final JPanel panel_3 = new JPanel();
						final GridBagConstraints gbc_panel_3 = new GridBagConstraints();
						gbc_panel_3.insets = new Insets(0, 0, 0, 5);
						gbc_panel_3.fill = GridBagConstraints.BOTH;
						gbc_panel_3.gridx = 2;
						gbc_panel_3.gridy = 0;
						panel.add(panel_3, gbc_panel_3);
						panel_3.setLayout(new BorderLayout(0, 0));
						{
							final JScrollPane scrollPane = new JScrollPane();
							panel_3.add(scrollPane, BorderLayout.CENTER);
							{
								fieldList = new JList();
								fieldList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
								scrollPane.setViewportView(fieldList);
							}
						}
						{
							final JPanel panel_4 = new JPanel();
							panel_3.add(panel_4, BorderLayout.NORTH);
							final FlowLayout fl_panel_4 = (FlowLayout) panel_4.getLayout();
							fl_panel_4.setAlignment(FlowLayout.LEFT);
							{
								final JButton button = new JButton("フィールド編集");
								button.addActionListener(new ActionListener() {
									@Override
									public void actionPerformed(final ActionEvent e) {
										final Db d = (Db) databaseList.getSelectedValue();
										if (d != null) {
											final Table t = (Table) tableList.getSelectedValue();
											if (t != null) {
												Field.edit(d.name, t.name);
											}
										}
									}
								});
								panel_4.add(button);
							}
						}
					}
					button_1.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(final ActionEvent e) {
							Db.edit();
						}
					});
				}
			}
		}
	}
	
	@Override
	public void update() {
		databaseList.setModel(new AbstractListModel() {
			List<Db> database = Db.getDatabase();
			
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
		final Db d = (Db) databaseList.getModel().getElementAt(0);
		if (d == null) {
			tableList.setModel(new AbstractListModel() {
				@Override
				public int getSize() {
					return 0;
				}
				
				@Override
				public Object getElementAt(final int index) {
					return null;
				}
			});
		}
		else {
			tableList.setModel(new AbstractListModel() {
				List<Table> table = Table.getTable(d.name);
				
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
		fieldList.setModel(new AbstractListModel() {
			@Override
			public int getSize() {
				return 0;
			}
			
			@Override
			public Object getElementAt(final int index) {
				return null;
			}
		});
		revalidate();
	}
	
	@Override
	public String getListName() {
		return "データベース";
	}
}
