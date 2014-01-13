package cn.jhc.um.util;

import static cn.jhc.um.util.Constants.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;

/**
 * Convenient class for check subdirectory, file upload size, file name extensions, etc.
 * Kind Editor predefined some subdirectories in upload root directory, such as: image,flash,file,media {@link Constants}.
 * @author luyanfei
 *
 */
public class ConstraintChecker {
	
	private static final List<String> LIST_OF_ALLOWED_EXTS = new ArrayList<String>();

	public ConstraintChecker(Properties properties) {
		String[] allowedExts = properties.getProperty(ALLOWED_EXTS).split(",");
		for (String ext : allowedExts) {
			LIST_OF_ALLOWED_EXTS.add(ext);
		}
	}

	/**
	 * Check weather ext is in allowed exts list.
	 * @param ext
	 * 		File's extension.
	 * @return
	 * 		true if ext is permitted, otherwise return false.
	 */
	public boolean checkFileExtension(String ext) {
		return LIST_OF_ALLOWED_EXTS.contains(ext);
	}
}
