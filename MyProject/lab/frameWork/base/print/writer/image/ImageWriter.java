package frameWork.base.print.writer.image;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import frameWork.base.core.fileSystem.FileSystem;
import frameWork.base.print.ReportException;
import frameWork.base.print.element.Page;
import frameWork.base.print.writer.Writer;

public class ImageWriter extends Writer {
	
	@Override
	public void print() throws ReportException {
		final double scale = 2.5;
		try {
			for (final Page page : this) {
				page.setScale(1);
				final File file = FileSystem.Temp.PrintDir.ImageDir.getImagefile("png");
				final BufferedImage image = new BufferedImage((int) (page.getWidth() * scale),
				        (int) (page.getHeight() * scale), BufferedImage.TYPE_INT_BGR);
				final Graphics2D g2d = image.createGraphics();
				
				g2d.scale(scale, scale);
				page.paint(g2d);
				g2d.dispose();
				
				try (final FileOutputStream foStream = new FileOutputStream(file)) {
					ImageIO.write(image, "png", foStream);
				}
				page.setFile(file);
			}
		}
		catch (final IOException exp) {
			exp.printStackTrace();
		}
	}
}
