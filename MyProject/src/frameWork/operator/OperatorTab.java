package frameWork.operator;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.Icon;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import frameWork.architect.Tab;
import frameWork.operator.fileBrowser.FileBrowser;

@SuppressWarnings({
        "rawtypes", "unchecked"
})
public class OperatorTab extends Tab {
	private final CardLayout cl_panel = new CardLayout(0, 0);
	private JPanel panel;
	
	public OperatorTab() {
		initialize();
	}
	
	private void initialize() {
		final List<String> __list = new ArrayList<>();
		setLayout(new BorderLayout(0, 0));
		{
			final JScrollPane scrollPane = new JScrollPane();
			add(scrollPane, BorderLayout.CENTER);
			{
				panel = new JPanel();
				scrollPane.setViewportView(panel);
				panel.setLayout(cl_panel);
				{
					final FileBrowser fileBrowser = new FileBrowser();
					this.panel.add(fileBrowser, fileBrowser.getListName());
					__list.add(fileBrowser.getListName());
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
	}
	
	@Override
	public Icon getTabIcon() {
		return null;
	}
	
	@Override
	public String gettTabName() {
		return "運用管理";
	}
}
