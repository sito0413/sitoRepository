package frameWork.manager.tab;

import java.awt.SystemColor;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

public class TabPanel extends JPanel {
	private TabbedPanel tabbedPanel;
	private int index;
	
	public TabPanel() {
		setBorder(new MatteBorder(1, 1, 1, 1, SystemColor.controlShadow));
	}
	
	public Icon getTabIcon() {
		try {
			return new ImageIcon(getClass().getClassLoader().getResource(
			        getClass().getPackage().getName().replace(".", "/") + "/icon.png"));
		}
		catch (final Exception e) {
			return null;
		}
	}
	
	public String getTabName() {
		return "";
	}
	
	public void update() {
	}
	
	public String getTip() {
		return null;
	}
	
	@Override
	public void setEnabled(final boolean enabled) {
		super.setEnabled(enabled);
		tabbedPanel.setEnabledAt(index, enabled);
	}
	
	public void setBed(final int index, final TabbedPanel tabbedPanel) {
		this.index = index;
		this.tabbedPanel = tabbedPanel;
	}
}
