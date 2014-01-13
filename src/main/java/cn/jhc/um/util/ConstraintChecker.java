package cn.jhc.um.util;

import static cn.jhc.um.util.Constants.*;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;

/**
 * Convenient class for check subdirectory, file upload size, file name extensions, etc.
 * Kind Editor predefined some subdirectories in upload root directory, such as: image,flash,file,media {@link Constants}.
 * Both {@link UploadServlet} and {@link FileManagerServlet} needs check subdirectory's existence.
 * @author luyanfei
 *
 */
public class ConstraintChecker {
	private static final String DEFAULT_SUBDIRECTORY = "image";
	private final Map<String, List<String>> extsMap = new HashMap<String, List<String>>();

	public ConstraintChecker(Properties properties) {
		String imgExts = properties.getProperty(IMG_DIR_EXT);
		extsMap.put(properties.getProperty(IMG_DIR), Arrays.asList(imgExts.split(",")));
		String fileExts = properties.getProperty(FILE_DIR_EXT);
		extsMap.put(properties.getProperty(FILE_DIR), Arrays.asList(fileExts.split(",")));
		String mediaExts = properties.getProperty(FILE_DIR_EXT);
		extsMap.put(properties.getProperty(MEDIA_DIR), Arrays.asList(mediaExts.split(",")));
		String flashExts = properties.getProperty(FLASH_DIR_EXT);
		extsMap.put(properties.getProperty(FLASH_DIR), Arrays.asList(flashExts.split(",")));
	}

	/**
	 * Check subdirectory's existence.
	 * @param rootPath
	 * 		Upload root path.
	 * @param subdir
	 * 		Subdirectory in root path.
	 * @return
	 * 		File object of the subdirectory.
	 * @throws ServletException
	 * 		Subdirectory doesn't exist or is not a directory.
	 */
	public File checkSubDirectory(String rootPath, String subdir)
			throws ServletException {
		subdir = subdir == null ? DEFAULT_SUBDIRECTORY : subdir;
		File subDirectory = new File(rootPath, subdir);
		if (!subDirectory.isDirectory()) {
			throw new ServletException("Invalid Subirectory.");
		}
		return subDirectory;
	}
	
	/**
	 * Check weather ext is in subdir's allowed exts list.
	 * @param subdir
	 * 		Subdirectory to be checked.
	 * @param ext
	 * 		File's extension.
	 * @return
	 * 		true if ext is permitted in this subdir, otherwise return false.
	 */
	public boolean checkFileExtension(String subdir, String ext) {
		if(subdir == null || ext == null)
			return false;
		List<String> exts = extsMap.get(subdir);
		return exts == null ? false : exts.contains(ext);
	}
}
