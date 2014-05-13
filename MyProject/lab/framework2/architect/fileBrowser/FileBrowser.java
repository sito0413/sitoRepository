package framework2.architect.fileBrowser;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingWorker;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultMutableTreeNode;


public class FileBrowser extends JPanel implements TreeSelectionListener
{
	public static void main(String[] args) {
		JFrame frame=	new JFrame();
		frame.getContentPane().add(new FileBrowser(new File(".")));
		frame.setVisible(true);
	}
    final FileSystemView fileSystemView;
    private final FileTable table;
    final FileTree tree;
    private final JLabel titel;
    
    public FileBrowser(File fileSystem)
    {
        super(new BorderLayout(3, 3));
        setOpaque(false);
        fileSystemView = FileSystemView.getFileSystemView();
        table = new FileTable(fileSystemView);
        DefaultMutableTreeNode node = createTreeNode(fileSystem);
        tree = new FileTree(fileSystemView, node);
        tree.addTreeSelectionListener(this);
        titel = new JLabel();
        JScrollPane treeScroll = new JScrollPane(tree);
        treeScroll.setPreferredSize(new Dimension(200, (int) treeScroll.getPreferredSize().getHeight()));
        
        JScrollPane tableScroll = new JScrollPane();
        Dimension d = tableScroll.getPreferredSize();
        tableScroll.setPreferredSize(new Dimension((int) d.getWidth(), 200));
        tableScroll.setViewportView(table);
        
        JPanel detailView = new JPanel(new BorderLayout(3, 3));
        detailView.add(titel, BorderLayout.NORTH);
        detailView.add(tableScroll, BorderLayout.CENTER);
        detailView.setOpaque(false);
        
        JSplitPane pane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treeScroll, detailView);
        pane.setDividerLocation(200);
        pane.setOpaque(false);
        showChildren(node);
        titel.setText(fileSystemView.getSystemDisplayName(fileSystem));
        titel.setIcon(fileSystemView.getSystemIcon(fileSystem));
        add(pane);
    }
    
    
    private DefaultMutableTreeNode createTreeNode(File rootPath)
    {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(rootPath);
        if (rootPath.isDirectory())
        {
                for (File file : rootPath.listFiles())
                {
                    if (file instanceof File)
                    {
                        
                        root.add(createTreeNode(file));
                    }
                }
        }
        return root;
    }
    
    public void valueChanged(final TreeSelectionEvent tse)
    {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tse.getPath().getLastPathComponent();
        showChildren(node);
        File file = (File) node.getUserObject();
        titel.setText(fileSystemView.getSystemDisplayName(file));
        titel.setIcon(fileSystemView.getSystemIcon(file));
        repaint();
    }
    
    void showChildren(final DefaultMutableTreeNode node)
    {
        tree.setEnabled(false);
        SwingWorker<Void, File> worker = new SwingWorker<Void, File>()
        {
            @Override
            public Void doInBackground()
            {
                File file = (File) node.getUserObject();
                File[] files;
                if (file.isDirectory())
                {
                    files = fileSystemView.getFiles(file, true);
                }
                else
                {
                    files = new File[0];
                }
                setTableData(files);
                return null;
            }
            
            @Override
            protected void done()
            {
                tree.setEnabled(true);
            }
        };
        worker.execute();
    }
    
    /** Update the table on the EDT */
    void setTableData(final File[] files)
    {
        table.setFiles(files);
        if (files.length != 0)
        {
            Icon icon = fileSystemView.getSystemIcon(files[0]);
            table.setRowHeight(icon.getIconHeight() + FileTable.ROWICON_PADDING);
            setColumnWidth(0, -1);
            setColumnWidth(2, 60);
            setColumnWidth(3, -1);
            setColumnWidth(4, -1);
            setColumnWidth(5, -1);
            setColumnWidth(6, -1);
        }
    }
    
    void setColumnWidth(final int column, final int width)
    {
        int lWidth = width;
        TableColumn tableColumn = table.getColumnModel().getColumn(column);
        if (lWidth < 0)
        {
            // use the preferred width of the header..
            JLabel label = new JLabel((String) tableColumn.getHeaderValue());
            Dimension preferred = label.getPreferredSize(); // altered
            lWidth = (int) preferred.getWidth() + 14;
        }
        tableColumn.setPreferredWidth(lWidth);
        tableColumn.setMaxWidth(lWidth);
        tableColumn.setMinWidth(lWidth);
    }
}
