/**
 * 
 */
package framework2.architect.fileBrowser;

import java.io.File;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.filechooser.FileSystemView;

class FileTable extends JTable {

	static final int ROWICON_PADDING = 6;

	private final FileTableModel fileTableModel;

	public FileTable(final FileSystemView fileSystemView) {
		fileTableModel = new FileTableModel(fileSystemView);
		setModel(fileTableModel);
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setAutoCreateRowSorter(true);
		setShowVerticalLines(false);
	}

	public File getFile(final int row) {
		return fileTableModel.getFile(row);
	}

	public void setFiles(final File[] files) {
		fileTableModel.setFiles(files);
	}

}