package frameWork.base.util.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class CSV extends ArrayList<List<String>> {
	
	CharSequence toString(final Config config) {
		final StringBuilder builder = new StringBuilder();
		for (final List<String> record : this) {
			for (final String field : record) {
				if (field.toString().contains(String.valueOf(config.getQuotation()))
				        || field.toString().contains(String.valueOf(config.getSeparator()))
				        || field.toString().contains(config.getLineSeparator())) {
					builder.append(config.getQuotation())
					        .append(field.toString().replace(String.valueOf(config.getQuotation()),
					                config.getEscape())).append(config.getQuotation())
					        .append(config.getSeparator());
				}
				else {
					builder.append(field.toString()).append(config.getSeparator());
				}
			}
			builder.append(config.getLineSeparator());
		}
		return builder;
	}
	
	private static List<String> create(final String lineString, final char quotation, final char separator) {
		final int loopMaxCount = lineString.length();
		final List<String> record = new ArrayList<>();
		for (int i = 0; i < loopMaxCount; i++) {
			boolean isQuotationBlock = false;
			boolean isQuotationBlockEscape = false;
			final StringBuilder buffer = new StringBuilder();
			char analyzedChar = lineString.charAt(i);
			if (analyzedChar == quotation) {
				isQuotationBlock = true;
			}
			else if (analyzedChar == separator) {
				record.add(create(buffer.toString(), isQuotationBlock, quotation));
				continue;
			}
			buffer.append(analyzedChar);
			for (i++; i < loopMaxCount; i++) {
				analyzedChar = lineString.charAt(i);
				if (analyzedChar == separator) {
					if (isQuotationBlock && !isQuotationBlockEscape) {
						buffer.append(analyzedChar);
					}
					else {
						break;
					}
				}
				else {
					buffer.append(analyzedChar);
					if ((analyzedChar == quotation) && isQuotationBlock) {
						isQuotationBlockEscape = !isQuotationBlockEscape;
					}
				}
			}
			record.add(create(buffer.toString(), isQuotationBlock, quotation));
		}
		return record;
	}
	
	private static String create(final String string, final boolean isQuotationBlock, final char quotation) {
		final StringBuilder buffer = new StringBuilder();
		if (!string.isEmpty()) {
			final int loopMaxCount = string.length() - 1;
			boolean isQuotationBlockEscape = false;
			if (isQuotationBlock) {
				for (int i = 1; i < loopMaxCount; i++) {
					final char analyzedChar = string.charAt(i);
					if (analyzedChar == quotation) {
						isQuotationBlockEscape = !isQuotationBlockEscape;
						if (isQuotationBlockEscape) {
							buffer.append(analyzedChar);
						}
					}
					else {
						buffer.append(analyzedChar);
					}
				}
			}
			else {
				buffer.append(string);
			}
		}
		return buffer.toString();
	}
	
	private boolean readLine(final Config config, final String readLine, final StringBuilder buffer,
	        final boolean isQuotationBlock) {
		boolean result = isQuotationBlock;
		int quotationCount = 0;
		for (int i = 0; i < readLine.length(); i++) {
			if (readLine.charAt(i) == config.getQuotation()) {
				quotationCount++;
			}
		}
		if (isQuotationBlock) {
			if ((quotationCount % 2) == 0) {
				buffer.append(readLine).append(config.getLineSeparator());
			}
			else {
				add(create(buffer.append(readLine).toString(), config.getQuotation(), config.getSeparator()));
				buffer.delete(0, buffer.length());
				result = false;
			}
		}
		else {
			if ((quotationCount % 2) == 0) {
				add(create(readLine, config.getQuotation(), config.getSeparator()));
			}
			else {
				buffer.append(readLine).append(config.getLineSeparator());
				result = true;
			}
		}
		return result;
	}
	
	void read(final Config config, final BufferedReader reader) throws IOException {
		boolean isQuotationBlock = false;
		final StringBuilder buffer = new StringBuilder();
		// 最終行まで読み込む
		while (reader.ready()) {
			isQuotationBlock = readLine(config, reader.readLine(), buffer, isQuotationBlock);
		}
	}
}