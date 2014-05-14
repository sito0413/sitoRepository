package frameWork.operator.fileBrowser;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import frameWork.architect.SettingPanel;

public class FileBrowser extends SettingPanel implements TreeSelectionListener {
	private final FileSystemView fileSystemView;
	private JLabel titel;
	private JTree tree;
	private JTable table;
	private final FileTableModel fileTableModel;
	
	public FileBrowser() {
		final File dir = new File("");
		fileSystemView = FileSystemView.getFileSystemView();
		fileTableModel = new FileTableModel(fileSystemView);
		final DefaultMutableTreeNode node = createTreeNode(dir);
		initialize();
		tree.setModel(new DefaultTreeModel(node));
		tree.setRootVisible(false);
		tree.setCellRenderer(new FileTreeCellRenderer(fileSystemView));
		tree.expandRow(0);
		tree.addTreeSelectionListener(this);
		table.setModel(fileTableModel);
		showChildren(node);
		titel.setText(fileSystemView.getSystemDisplayName(dir));
		titel.setIcon(fileSystemView.getSystemIcon(dir));
	}
	
	private void initialize() {
		setLayout(new BorderLayout(0, 0));
		{
			final JPanel panel = new JPanel();
			add(panel);
			panel.setLayout(new BorderLayout(0, 0));
			{
				final JScrollPane scrollPane = new JScrollPane();
				panel.add(scrollPane, BorderLayout.WEST);
				{
					tree = new JTree();
					scrollPane.setViewportView(tree);
				}
			}
			{
				final JPanel panel_1 = new JPanel();
				panel.add(panel_1, BorderLayout.CENTER);
				panel_1.setLayout(new BorderLayout(0, 0));
				{
					titel = new JLabel("");
					panel_1.add(this.titel, BorderLayout.NORTH);
				}
				{
					final JScrollPane scrollPane = new JScrollPane();
					panel_1.add(scrollPane);
					{
						this.table = new JTable();
						this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						this.table.setAutoCreateRowSorter(true);
						this.table.setVerifyInputWhenFocusTarget(false);
						scrollPane.setViewportView(this.table);
					}
				}
			}
		}
	}
	
	private DefaultMutableTreeNode createTreeNode(final File rootPath) {
		final DefaultMutableTreeNode root = new DefaultMutableTreeNode(rootPath);
		if (rootPath.isDirectory()) {
			for (final File file : rootPath.listFiles()) {
				if (file.isDirectory()) {
					root.add(createTreeNode(file));
				}
			}
		}
		return root;
	}
	
	@Override
	public void valueChanged(final TreeSelectionEvent tse) {
		final DefaultMutableTreeNode node = (DefaultMutableTreeNode) tse.getPath().getLastPathComponent();
		showChildren(node);
		final File file = (File) node.getUserObject();
		titel.setText(fileSystemView.getSystemDisplayName(file));
		titel.setIcon(fileSystemView.getSystemIcon(file));
		revalidate();
	}
	
	void showChildren(final DefaultMutableTreeNode node) {
		tree.setEnabled(false);
		final SwingWorker<Void, File> worker = new SwingWorker<Void, File>() {
			@Override
			public Void doInBackground() {
				final File file = (File) node.getUserObject();
				File[] files;
				if (file.isDirectory()) {
					files = fileSystemView.getFiles(file, true);
				}
				else {
					files = new File[0];
				}
				setTableData(files);
				return null;
			}
			
			@Override
			protected void done() {
				tree.setEnabled(true);
			}
		};
		worker.execute();
	}
	
	/** Update the table on the EDT */
	void setTableData(final File[] files) {
		fileTableModel.setFiles(files);
		if (files.length != 0) {
			final Icon icon = fileSystemView.getSystemIcon(files[0]);
			table.setRowHeight(icon.getIconHeight() + 6);
			setColumnWidth(0, -1);
			setColumnWidth(2, 60);
			setColumnWidth(3, -1);
			setColumnWidth(4, -1);
			setColumnWidth(5, -1);
			setColumnWidth(6, -1);
		}
	}
	
	void setColumnWidth(final int column, final int width) {
		int lWidth = width;
		final TableColumn tableColumn = table.getColumnModel().getColumn(column);
		if (lWidth < 0) {
			// use the preferred width of the header..
			final JLabel label = new JLabel((String) tableColumn.getHeaderValue());
			final Dimension preferred = label.getPreferredSize(); // altered
			lWidth = (int) preferred.getWidth() + 14;
		}
		tableColumn.setPreferredWidth(lWidth);
		tableColumn.setMaxWidth(lWidth);
		tableColumn.setMinWidth(lWidth);
	}
	
	@Override
	public String getListName() {
		return "システムディレクトリ";
	}
}
