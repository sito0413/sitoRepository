package sito.installer.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

class TextPanel extends JPanel {
	TextPanel(final String name, final String text, final int w, final int h) {
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
		setName(name);
	}
}