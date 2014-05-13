package framework2.architect.fileBrowser;

import javax.swing.JTree;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

class FileTree extends JTree {
	public FileTree(final FileSystemView fileSystemView, final TreeNode treeNode) {
		super(new DefaultTreeModel(treeNode));
		setRootVisible(false);
		setCellRenderer(new FileTreeCellRenderer(fileSystemView));
		expandRow(0);
		setVisibleRowCount(15);
	}
}