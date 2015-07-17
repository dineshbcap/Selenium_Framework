package utils.genericutility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.poi.util.IOUtils;

public class FileUtility {

	/***
	 * Copy a file from one location to another
	 * 
	 * @param f1
	 *            - Source file
	 * @param f2
	 *            - Destination File
	 * @throws IOException
	 */
	private FileUtility(){
		
	}
	public static void copyFile(File f1, File f2) {
		try {

			InputStream in = new FileInputStream(f1);
			OutputStream out = new FileOutputStream(f2);
			try {

				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}

				IOUtils.closeQuietly(in);
				IOUtils.closeQuietly(out);

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				in.close();
				out.close();
			}
		} catch (IOException e) {
			// TODO: error handling
		}

	}

	/***
	 * Though the method is primarily rename, we have written it to perform copy and delete of the specified file to a
	 * new destination file due to some constraints
	 * 
	 * @param oldFile
	 * @param newFile
	 * @throws IOException
	 */
	public static void moveFile(String oldFile, String newFile) throws IOException {
		File oldfile = new File(oldFile);
		File newfile = new File(newFile);
		copyFile(oldfile, newfile);
		oldfile.delete();
	}
}
