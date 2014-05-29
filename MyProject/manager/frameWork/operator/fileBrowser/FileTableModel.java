package frameWork.operator.fileBrowser;

import java.io.File;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.AbstractTableModel;

@SuppressWarnings("nls")
class FileTableModel extends AbstractTableModel {
	private static final String[] COLUMNS = {
	        "Icon", "File", "Size", "Last Modified", "R", "W", "E",
	};
	private File[] files;
	private final FileSystemView fileSystemView;
	
	FileTableModel(final FileSystemView fileSystemView) {
		this.files = new File[0];
		this.fileSystemView = fileSystemView;
	}
	
	@Override
	public Object getValueAt(final int row, final int column) {
		final File file = files[row];
		switch ( column ) {
			case 0 :
				return fileSystemView.getSystemIcon(file);
			case 1 :
				return fileSystemView.getSystemDisplayName(file);
			case 2 :
				return file.length();
			case 3 :
				return file.lastModified();
			case 4 :
				return file.canRead();
			case 5 :
				return file.canWrite();
			case 6 :
				return file.canExecute();
			default :
				System.err.println("Logic Error");
		}
		return "";
	}
	
	@Override
	public int getColumnCount() {
		return COLUMNS.length;
	}
	
	@Override
	public Class<?> getColumnClass(final int column) {
		switch ( column ) {
			case 0 :
				return ImageIcon.class;
			case 2 :
				return Long.class;
			case 3 :
				return Date.class;
			case 4 :
			case 5 :
			case 6 :
				return Boolean.class;
		}
		return String.class;
	}
	
	@Override
	public String getColumnName(final int column) {
		return COLUMNS[column];
	}
	
	@Override
	public int getRowCount() {
		return files.length;
	}
	
	public File getFile(final int row) {
		return files[row];
	}
	
	public void setFiles(final File[] files) {
		this.files = files;
		fireTableDataChanged();
	}
}