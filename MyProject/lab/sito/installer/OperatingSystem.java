/*
 * OperatingSystem.java
 *
 * Originally written by Slava Pestov for the jEdit installer project. This work
 * has been placed into the public domain. You may use this work in any way and
 * for any purpose you wish.
 *
 * THIS SOFTWARE IS PROVIDED AS-IS WITHOUT WARRANTY OF ANY KIND, NOT EVEN THE
 * IMPLIED WARRANTY OF MERCHANTABILITY. THE AUTHOR OF THIS SOFTWARE, ASSUMES
 * _NO_ RESPONSIBILITY FOR ANY CONSEQUENCE RESULTING FROM THE USE, MODIFICATION,
 * OR REDISTRIBUTION OF THIS SOFTWARE.
 */

package sito.installer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/*
 * Abstracts away operating-specific stuff, like finding out the installation
 * directory, creating a shortcut to start to program, and such.
 */
public abstract class OperatingSystem {
	abstract String getInstallDirectory(String name, String version);
	
	abstract void exec(final String[] args) throws IOException;
	
	abstract void mkdirs(final String directory) throws IOException;
	
	abstract void perform(final Installer installer, String installDir, List<String> filesets, final String kickJar)
	        throws IOException;
	
	static OperatingSystem getOperatingSystem() {
		if (os != null) {
			return os;
		}
		if (System.getProperty("os.name").indexOf("Windows") != -1) {
			os = new Windows();
		}
		else {
			os = new Unix();
		}
		
		return os;
	}
	
	// private members
	private static OperatingSystem os;
	
	private static class Unix extends OperatingSystem {
		@Override
		public String getInstallDirectory(final String name, final String version) {
			String dir = "/usr/local/share/";
			if (!new File(dir).canWrite()) {
				dir = System.getProperty("user.home");
			}
			return new File(dir, name + "/" + version).getPath();
		}
		
		@Override
		void mkdirs(final String directory) throws IOException {
			final File file = new File(directory);
			if (!file.exists()) {
				final String[] mkdirArgs = {
				        "mkdir", "-m", "755", "-p", directory
				};
				exec(mkdirArgs);
			}
		}
		
		@Override
		void exec(final String[] args) throws IOException {
			final Process proc = Runtime.getRuntime().exec(args);
			proc.getInputStream().close();
			proc.getOutputStream().close();
			proc.getErrorStream().close();
			try {
				proc.waitFor();
			}
			catch (final InterruptedException ie) {
			}
		}
		
		@Override
		public void perform(final Installer installer, final String installDir, final List<String> filesets,
		        final String kickJar) throws IOException {
			String dir = "/usr/local/";
			if (!new File(dir).canWrite()) {
				dir = System.getProperty("user.home");
			}
			final String directory = new File(dir, "bin").getPath();
			if (!((directory != null) && (directory.trim().length() != 0))) {
				return;
			}
			
			os.mkdirs(directory);
			
			// create app start script
			final String name = installer.getAppName();
			final String script = directory + File.separatorChar + name.toLowerCase();
			
			// Delete existing copy
			new File(script).delete();
			
			// Write simple script
			final FileWriter out = new FileWriter(script);
			out.write("#!/bin/sh\n");
			out.write("#\n");
			out.write("# Find a java installation.\n");
			out.write("if [ -z \"${JAVA_HOME}\" ]; then\n");
			out.write("	echo 'Warning: $JAVA_HOME environment variable not set! Consider setting it.'\n");
			out.write("	echo '         Attempting to locate java...'\n");
			out.write("	j=`which java 2>/dev/null`\n");
			out.write("	if [ -z \"$j\" ]; then\n");
			out.write("		echo \"Failed to locate the java virtual machine! Bailing...\"\n");
			out.write("		exit 1\n");
			out.write("	else\n");
			out.write("		echo \"Found a virtual machine at: $j...\"\n");
			out.write("		JAVA=\"$j\"\n");
			out.write("	fi\n");
			out.write("else\n");
			out.write("	JAVA=\"${JAVA_HOME}/bin/java\"\n");
			out.write("fi\n");
			out.write("\n");
			out.write("# Launch application.\n");
			out.write("\n");
			out.write("exec \"${JAVA}\" -Dawt.useSystemAAFontSettings=on -Dswing.aatext=true -jar \"" + installDir
			        + File.separator + "jedit.jar\" -reuseview \"$@\"\n");
			out.close();
			
			// Make it executable
			final String[] chmodArgs = {
			        "chmod", "755", script
			};
			os.exec(chmodArgs);
		}
	}
	
	private static class Windows extends OperatingSystem {
		@Override
		public String getInstallDirectory(final String name, final String version) {
			String programDir = System.getenv("ProgramFiles");
			if (programDir == null) {
				programDir = "%ProgramFiles%";
			}
			return programDir + "\\" + name + "\\" + version;
		}
		
		@Override
		void exec(final String[] args) throws IOException {
			try {
				Runtime.getRuntime().exec(args).waitFor();
			}
			catch (final IOException io) {
			}
			catch (final InterruptedException ie) {
			}
		}
		
		@Override
		void mkdirs(final String directory) throws IOException {
			final File file = new File(directory);
			if (!file.exists()) {
				file.mkdir();
			}
		}
		
		@Override
		void perform(final Installer installer, final String installDir, final List<String> filesets,
		        final String kickJar) throws IOException {
			//NOP
		}
		
	}
}
