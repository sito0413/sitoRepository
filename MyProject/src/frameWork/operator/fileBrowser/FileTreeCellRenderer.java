package frameWork.operator.fileBrowser;

import java.awt.Component;
import java.io.File;

import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

class FileTreeCellRenderer extends DefaultTreeCellRenderer {
	private final FileSystemView fileSystemView;
	private final JLabel label;
	
	FileTreeCellRenderer(final FileSystemView fileSystemView) {
		label = new JLabel();
		label.setOpaque(true);
		this.fileSystemView = fileSystemView;
	}
	
	@SuppressWarnings("hiding")
	@Override
	public Component getTreeCellRendererComponent(final JTree tree, final Object value, final boolean selected,
	        final boolean expanded, final boolean leaf, final int row, final boolean hasFocus) {
		final DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
		final File file = (File) node.getUserObject();
		label.setIcon(fileSystemView.getSystemIcon(file));
		label.setText(fileSystemView.getSystemDisplayName(file));
		label.setToolTipText(file.getPath());
		if (selected) {
			label.setBackground(backgroundSelectionColor);
			label.setForeground(textSelectionColor);
		}
		else {
			label.setBackground(backgroundNonSelectionColor);
			label.setForeground(textNonSelectionColor);
		}
		return label;
	}
}