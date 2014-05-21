package frameWork.operator.print;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import frameWork.architect.Literal;
import frameWork.architect.SettingPanel;
import frameWork.architect.SrcUtil;
import frameWork.base.core.authority.Authority;
import frameWork.base.core.routing.RoutingHandler;
import frameWork.base.core.routing.TargetFilter;
import frameWork.base.core.state.State;

@SuppressWarnings({
        "rawtypes", "unchecked"
})
public class Print extends SettingPanel {
	private JTextField textField;
	private JTextField textField_1;
	private List<String> __list = new ArrayList<>();
	private String systemID;
	private JList list;

	public Print() {
		initialize();
	}

	private void initialize() {
		setLayout(new BorderLayout(10, 0));
		{
			final JPanel panel = new JPanel();
			final FlowLayout flowLayout = (FlowLayout) panel.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			add(panel, BorderLayout.NORTH);
			{
				final JLabel lblNewLabel = new JLabel("URL");
				panel.add(lblNewLabel);
			}
			{
				final JPanel panel_1 = new JPanel();
				panel.add(panel_1);
				final FlowLayout fl_panel_1 = (FlowLayout) panel_1.getLayout();
				fl_panel_1.setVgap(0);
				fl_panel_1.setHgap(0);
				{
					this.textField_1 = new JTextField();
					this.textField_1.setEnabled(false);
					this.textField_1.setHorizontalAlignment(SwingConstants.RIGHT);
					panel_1.add(this.textField_1);
					this.textField_1.setEditable(false);
					this.textField_1.setColumns(10);
				}
				{
					this.textField = new JTextField();
					this.textField.setHorizontalAlignment(SwingConstants.LEFT);
					panel_1.add(this.textField);
					this.textField.setColumns(10);
				}
			}
			final JButton btnNewButton_1 = new JButton("作成");
			panel.add(btnNewButton_1);
			btnNewButton_1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					create();
					EventQueue.invokeLater(new Runnable() {
						@Override
						public void run() {
							update();
						}
					});
				}
			});
		}
		{
			final JScrollPane scrollPane = new JScrollPane();
			add(scrollPane);
			{
				list = new JList();
				list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				list.setSelectedIndex(0);
				scrollPane.setViewportView(list);
			}
		}
		revalidate();
		{
			final JLabel label = new JLabel("");
			add(label, BorderLayout.WEST);
		}
	}

	private void create() {
		final String[] value = TargetFilter.getClassName(textField.getText(), '/');
		if (value == null) {
			return;
		}//"controller"
		final String packageName = Literal.packageName + "/" + systemID + value[0];
		final File src = new File(Literal.src + "/" + packageName + "/" + value[1] + ".java");
		final File view = new File(Literal.system + "/" + systemID + "/" + Literal.viewer + "/" + systemID + value[0]
		        + "/" + value[1] + ".jsp");
		try {
			if (!src.exists()) {
				src.getParentFile().mkdirs();
				try (final PrintWriter printWriter = new PrintWriter(src)) {
					printWriter.println("package " + packageName.replace('/', '.') + ";");
					printWriter.println("");
					printWriter.println("import " + Authority.class.getCanonicalName() + ";");
					printWriter.println("import " + State.class.getCanonicalName() + ";");
					printWriter.println("import " + RoutingHandler.class.getCanonicalName() + ";");
					printWriter.println("");
					printWriter.println(SrcUtil.getComment());
					printWriter.println("@" + Authority.class.getSimpleName());
					printWriter.println("public class " + value[1] + " implements "
					        + RoutingHandler.class.getSimpleName() + "{");
					printWriter.println("\t@" + Authority.class.getSimpleName());
					printWriter.println("\t@Override");
					printWriter.println("\tpublic void get(" + State.class.getSimpleName() + " "
					        + State.class.getSimpleName().toLowerCase() + "){");
					printWriter.println("\t\t//NOOP");
					printWriter.println("\t}");
					printWriter.println("\t@" + Authority.class.getSimpleName());
					printWriter.println("\t@Override");
					printWriter.println("\tpublic void post(" + State.class.getSimpleName() + " "
					        + State.class.getSimpleName().toLowerCase() + "){");
					printWriter.println("\t\t//NOOP");
					printWriter.println("\t}");
					printWriter.println("}");
					final Properties properties = new Properties();
					final File file = new File(Literal.system + "/" + systemID + "/" + Literal.config,
					        Literal.routing_xml);
					if (file.exists()) {
						try (FileInputStream fileInputStream = new FileInputStream(file)) {
							properties.loadFromXML(fileInputStream);
						}
					}
					else {
						file.getParentFile().mkdirs();
					}
					try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
						properties.setProperty("/" + systemID + value[0] + "/" + value[1],
						        "." + systemID + value[0].replace('/', '.') + "." + value[1]);
						properties.storeToXML(fileOutputStream, "");
					}
					catch (final Exception e) {
						e.printStackTrace();
					}
				}
			}
			if (!view.exists()) {
				view.getParentFile().mkdirs();
				try (final PrintWriter printWriter = new PrintWriter(view, "utf-8")) {
					printWriter
					        .println("<%@ page language=\"java\" contentType=\"text/html;charset=utf-8\" pageEncoding=\"utf-8\"%>");
					printWriter.println("<!DOCTYPE html>");
					printWriter.println("<html lang=\"ja\">");
					printWriter.println("\t<head>");
					printWriter.println("\t\t<meta charset=\"utf-8\">");
					printWriter.println("\t\t<title>タイトル</title>");
					printWriter.println("\t</head>");
					printWriter.println("\t<body>");
					printWriter.println("\t</body>");
					printWriter.println("</html>");
				}
			}
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update() {
		__list = new ArrayList<>();
		systemID = Literal.Temp;
		{
			try {
				final File file = new File(Literal.src + "/" + Literal.info_xml);
				if (file.exists()) {
					try (FileInputStream fileInputStream = new FileInputStream(file)) {
						final Properties properties = new Properties();
						properties.loadFromXML(fileInputStream);
						systemID = properties.getProperty(Literal.SystemID, Literal.Temp);
					}
					final Properties properties = new Properties();
					final File xml = new File(Literal.system + "/" + systemID + "/" + Literal.config + "/"
					        + Literal.routing_xml);
					if (xml.exists()) {
						try (FileInputStream fileInputStream = new FileInputStream(xml)) {
							properties.loadFromXML(fileInputStream);
							for (final Object key : properties.keySet()) {
								__list.add(key + " => " + Literal.packageName + properties.get(key));
							}
						}
						catch (final IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
			catch (final Exception e) {
				e.printStackTrace();
			}
		}
		textField_1.setText(systemID + "/");
		Collections.sort(__list);
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
	}

	@Override
	public String getListName() {
		return "URL(画面)定義";
	}

}