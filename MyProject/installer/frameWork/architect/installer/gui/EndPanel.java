package frameWork.architect.installer.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

class EndPanel extends JPanel {
	EndPanel(final String text, final int w, final int h) {
		super(new BorderLayout());
		this.add(BorderLayout.CENTER, new JScrollPane(new JEditorPane("text/html", text) {
			{
				setEditable(false);
			}
		}) {
			{
				setPreferredSize(new Dimension() {
					{
						width = w;
						height = h;
					}
				});
			}
		});
		setName("インストールが完了しました");
	}
}