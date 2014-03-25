package frameWork;

import frameWork.compressor.LZ4Compressor;
import frameWork.compressor.LZ4Decompressor;

public class LZ4Example {
	
	public static void main(final String[] args) throws Exception {
		final String value = "12sdfsd345345dsfsdfsdgsdxcvdhfdzsdgsfdsdfhdf2sdfsdf3fsdgd45dsfsd72asasdf1sdg234ssdfsdgjh"
		        + "fsdfdfssdfsdf4thjkjhol/kldxzcfsdzxcfcc5v34dfhdfh5xcvxc234572ddfhdsdffhfhfdh12xv3sdfs434553zsdfxdfvdfz"
		        + "4ssdf52sdfsd3457sdfsdf212dfhdfh345345sd2fdfhcvbxcvdfhdsdfshdfh7dsf21234sdfsdf4s3dfdf53dfhdfh452372dhd"
		        + "4s345345sd2fdfhcvbxcvdfhdsdfshdfh7dsf245sd2fdfhcvbxcvdfhdsd1234sdfsdf4s3dfdf53dfhdfh452372fshdfh7dsf2"
		        + "4ssdf52sdfsd3457sdfsdf212dfhdfh3453fsh45sd2fdfhcvbxcvdfhdsddfh7dsf21234sdfsdf4s3dfdf53dfhdfh452372dhd"
		        + "4ssdf52sdfsd3457sdfsdf212dfhdfsdf52sdfsd3457sdfsdf212dfhdfhh345345sd2fdfhcvbxcvdfhds3xcvxcvcx4523cc5d"
		        + "45ghdfhd1234dffhd53f452hdsdfh457212345dfhdfh3dfhdfh45234572123dfhdfh453xcvxcvcx4523cc5vxcvvxcv.72ddas";
		
		final byte[] data = value.getBytes("UTF-8");
		final int decompressedLength = data.length;
		final LZ4Compressor compressor = new LZ4Compressor();
		final int maxCompressedLength = compressor.maxCompressedLength(decompressedLength);
		final byte[] compressed = new byte[maxCompressedLength];
		final int compressedLength = compressor.compress(data, data.length, compressed, 0);
		
		final LZ4Decompressor decompressor = new LZ4Decompressor();
		final byte[] restored = new byte[decompressedLength];
		final int compressedLength2 = decompressor.decompress(compressed, restored, restored.length);
		for (int i = 0; i < data.length; i++) {
			System.out.println(i + " " + data[i] + " " + restored[i]);
		}
		System.out.println(value + "\r\n" + data.length + " " + compressedLength + " " + compressedLength2 + "\r\n"
		        + new String(restored, "UTF-8"));
	}
}
