package framework2.base.report.image;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

public class ImageReportWriter {

	/**
	 * JComponentをイメージに変換する
	 * 
	 * @param components
	 *            変換するJComponentのVector
	 * @return 変換したJPEGの格納先URLのVector
	 */
	@SuppressWarnings("nls")
	public Vector<String> convert(final List<? extends JComponent> components) {
		Vector<String> retVector = new Vector<String>();
		if (components.size() == 0) {
			return retVector;
		}
		// ダミー表示用ダイアログ生成
		final double scale = 2.5;

		int componentsSize = components.size();

		Integer intEmproyeeNo = 99999999;
		String fileBase = "C:\\" + intEmproyeeNo;
		File objFile = new File(fileBase);
		objFile.mkdir();
		String fileName = "temp";
		String fileURL = "../report/image/" + intEmproyeeNo + '/' + fileName;
		int index = 0;
		String extend = ".png";
		String filePath = fileBase + '\\' + fileName;

		for (int i = 0; i < componentsSize; i++) {
			final JComponent component = components.get(i);
			// 以下、出力処理

			File file = new File(filePath + (++index) + extend);
			while (file.exists()) {
				file = new File(filePath + (++index) + extend);
			}
			retVector.add(fileURL + index + extend);
			BufferedImage image = new BufferedImage((int) (component.getWidth() * scale), (int) (component.getHeight() * scale),
			        BufferedImage.TYPE_INT_BGR);
			Graphics2D g2d = image.createGraphics();

			g2d.scale(scale, scale);
			component.paint(g2d);
			g2d.dispose();

			try {
				final FileOutputStream foStream = new FileOutputStream(file);
				ImageIO.write(image, "png", foStream);
				image = null;
				foStream.close();
			}
			catch (IOException exp) {
				exp.printStackTrace();
			}

		}
		return retVector;
	}
}
