import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import org.junit.runner.JUnitCore;

import frameWork.base.barcode.Barcode;

public class FontTest {
	
	private JFrame frame;
	private JTextField textField;
	private JLabel lblNewLabel;
	
	/**
	 * Launch the application.
	 */
	public static void main(final String[] args) {
		JUnitCore.main("frameWork.base.core.AllTests");
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					final FontTest window = new FontTest();
					window.frame.setVisible(true);
				}
				catch (final Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the application.
	 */
	public FontTest() {
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.frame = new JFrame();
		this.frame.setBounds(100, 100, 450, 300);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.getContentPane().setLayout(null);
		{
			this.textField = new JTextField();
			this.textField.addCaretListener(new CaretListener() {
				@Override
				public void caretUpdate(final CaretEvent e) {
					lblNewLabel.setFont(Barcode.getBarcodeFont(textField.getFont().getSize()));
					lblNewLabel.setText(Barcode.createBarcode(textField.getText()));
				}
			});
			this.textField.setBounds(12, 10, 96, 19);
			this.frame.getContentPane().add(this.textField);
			this.textField.setColumns(10);
		}
		{
			lblNewLabel = new JLabel("");
			this.lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
			lblNewLabel.setBounds(12, 39, 410, 192);
			this.frame.getContentPane().add(lblNewLabel);
		}
	}
}
