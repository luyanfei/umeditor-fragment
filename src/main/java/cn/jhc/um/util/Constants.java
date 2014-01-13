package cn.jhc.um.util;

import cn.jhc.um.generator.PathGenerator;

/**
 * Constants used in ueditor-mini-fragment, most of them are configure file's property names.
 * @author luyanfei
 *
 */
public interface Constants {

	/**
	 * Attribute name in ServletContext used for keep configure Properties object.
	 */
	public static final String SC_KIND_CONFIG = "umeditor_config";
	/**
	 * Attribute name in ServletContext used for keep {@link PathGenerator} object.
	 */
	public static final String SC_PATH_GENERATOR = "umeditor_path_generator";

	/**
	 * Attribute name in ServletContext used for keep {@link ConstraintChecker} object. 
	 * The object is used for check filename extensions, upload size limit, etc.
	 */
	public static final String SC_CONSTRAINT_CHECKER = "constraint_checker";
	
	//property names of the config file
	public static final String UPLOAD_SERVLET_URL = "upload_servlet_url";
	public static final String UPLOAD_ROOT = "upload_root";
	public static final String UPLOAD_FILE_SIZE_LIMIT = "upload_file_size_limit";
	public static final String UPLOAD_REQUEST_SIZE_LIMIT = "upload_request_size_limit";
	public static final String IMG_DIR = "img_dir";
	public static final String IMG_DIR_EXT = "img_dir_ext";
	public static final String FLASH_DIR = "flash_dir";
	public static final String FLASH_DIR_EXT = "flash_dir_ext";
	public static final String MEDIA_DIR = "media_dir";
	public static final String MEDIA_DIR_EXT = "media_dir_ext";
	public static final String FILE_DIR = "file_dir";
	public static final String FILE_DIR_EXT = "file_dir_ext";
	public static final String ALLOWED_DIRS = "allowed_dirs";
	/**
	 * Human readable file size limit, not configurable, but saved in properties object. 
	 * @see UMInitializer
	 */
	public static final String HUMAN_UPLOAD_FILE_SIZE_LIMIT = "human_upload_file_size_limit";
	/**
	 * Human readable request size limit, not configurable, but saved in properties object. 
	 * @see UMInitializer
	 */
	public static final String HUMAN_REQUEST_SIZE_LIMIT = "human_upload_request_size_limit";
	/**
	 * After upload successed, server should return uploaded file's reference url,
	 * this property is used for config url prefix.
	 */
	public static final String DEST_URL_PREFIX = "dest_url_prefix";
	/**
	 * Configure file's property, the value will be class name of the implemented PathGenerator.
	 */
	public static final String PATH_GENERATOR = "path_generator";
	public static final String DEFAULT_PATH_GENERATOR_CLASS = "cn.jhc.um.util.DateBasedPathGenerator";
	/**
	 * ResourceBundle property name for upload_size_exceeded.
	 */
	public static final String MSG_UPLOAD_EXCEEDED = "upload_size_exceeded";
	/**
	 * ResourceBundle property name for ext_violated.
	 */
	public static final String MSG_EXT_VIOLATED = "ext_violated";

}
