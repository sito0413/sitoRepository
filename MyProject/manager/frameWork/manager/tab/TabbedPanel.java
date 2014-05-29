package frameWork.manager.tab;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

public class TabbedPanel extends JTabbedPane {
	public TabbedPanel() {
		setTabPlacement(JTabbedPane.LEFT);
		setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	}
	
	public void addTab(final String title, final Icon icon, final TabPanel component, final String tip) {
		super.addTab("", null, component, null);
		component.setBed(getTabCount() - 1, this);
		final JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(new JLabel(component.getTabIcon(), SwingConstants.LEFT), BorderLayout.WEST);
		panel.add(new JLabel(component.getTabName(), SwingConstants.LEFT), BorderLayout.CENTER);
		panel.setToolTipText(component.getTip());
		panel.setOpaque(false);
		panel.setPreferredSize(new Dimension(100, 25));
		setTabComponentAt(getTabCount() - 1, panel);
	}
}
