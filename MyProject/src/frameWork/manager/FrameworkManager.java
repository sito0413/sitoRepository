package frameWork.manager;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

@SuppressWarnings({
        "rawtypes", "unchecked"
})
public class FrameworkManager {
	
	private JFrame frame;
	private JTextField txtCnewwavesystem;
	private final CardLayout cl_panel = new CardLayout(0, 0);
	private JPanel panel;
	private JTextField textField;
	
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
			final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
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
							final JPanel panel_1_1 = new JPanel();
							final FlowLayout fl_panel_1_1 = (FlowLayout) panel_1_1.getLayout();
							fl_panel_1_1.setAlignment(FlowLayout.LEFT);
							panel.add(panel_1_1, "プロジェクト作成");
							{
								final JButton btnNewButton_1 = new JButton("作成");
								btnNewButton_1.addActionListener(new ActionListener() {
									@Override
									public void actionPerformed(final ActionEvent e) {
										new File("src").mkdirs();
										new File("authority").mkdirs();
									}
								});
								panel_1_1.add(btnNewButton_1);
							}
						}
						{
							final JPanel panel_1_1 = new JPanel();
							panel.add(panel_1_1, "Info作成");
							panel_1_1.setLayout(null);
							{
								this.txtCnewwavesystem = new JTextField();
								this.txtCnewwavesystem.setBounds(5, 5, 150, 28);
								this.txtCnewwavesystem.setText("C:/newwave/_system");
								panel_1_1.add(this.txtCnewwavesystem);
							}
							{
								this.textField = new JTextField();
								this.textField.setBounds(5, 38, 150, 28);
								panel_1_1.add(this.textField);
								this.textField.setColumns(10);
							}
							{
								final JButton btnNewButton = new JButton("作成");
								btnNewButton.addActionListener(new ActionListener() {
									@Override
									public void actionPerformed(final ActionEvent e) {
										final Properties properties = new Properties();
										properties.setProperty("Path", txtCnewwavesystem.getText());
										if (!textField.getText().isEmpty()) {
											properties.setProperty("SystemID", textField.getText());
										}
										try (FileOutputStream os = new FileOutputStream("./src/info.xml")) {
											properties.storeToXML(os, "");
										}
										catch (final IOException e1) {
											e1.printStackTrace();
										}
									}
								});
								btnNewButton.setBounds(155, 38, 52, 28);
								panel_1_1.add(btnNewButton);
							}
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
							        "プロジェクト作成", "Info作成"
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
