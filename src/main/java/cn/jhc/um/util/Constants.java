package cn.jhc.um.util;

import cn.jhc.um.generator.PathGenerator;

/**
 * Constants used in umeditor-fragment, most of them are configure file's property names.
 * @author luyanfei
 *
 */
public interface Constants {

	/**
	 * Attribute name in ServletContext used for keep configure Properties object.
	 */
	public static final String SC_UM_CONFIG = "umeditor_config";
	/**
	 * Attribute name in ServletContext used for keep {@link PathGenerator} object.
	 */
	public static final String SC_PATH_GENERATOR = "umeditor_path_generator";

	/**
	 * Attribute name in ServletContext used for keep {@link ConstraintChecker} object. 
	 * The object is used for check filename extensions, upload size limit, etc.
	 */
	public static final String SC_CONSTRAINT_CHECKER = "constraint_checker";
	/**
	 * Configure option in umeditor.properties, used in {@link UploadSevlet}. 
	 * Check umeditor_default.properties for default value.
	 */
	public static final String UPLOAD_SERVLET_URL = "upload_servlet_url";
	/**
	 * Configure option in umeditor.properties, this is the upload root directory,
	 * all uploaded images will be in this directory. The default value is setted 
	 * in {@link UMInitializer}, looks like:<br/>
	 * context.getRealPath("/") + "upload/";
	 */
	public static final String UPLOAD_ROOT = "upload_root";
	/**
	 * Configure option in umeditor.properties, this is the default upload directory name in webapp fold.
	 * "default_web_upload_dir=upload" means all image files will be put in directory:<br/>
	 * context.getRealPath("/") + "upload/"<br/>
	 * Check umeditor_default.properties for default value.
	 */
	public static final String DEFAULT_WEB_UPLOAD_DIR = "default_web_upload_dir";
	/**
	 * Configure option in umeditor.properties, this is the single file's upload size limit.
	 * Check umeditor_default.properties for default value.
	 */
	public static final String UPLOAD_FILE_SIZE_LIMIT = "upload_file_size_limit";
	/**
	 * Configure option in umeditor.properties, this is the single request's upload size limit.
	 * Check umeditor_default.properties for default value.
	 */
	public static final String UPLOAD_REQUEST_SIZE_LIMIT = "upload_request_size_limit";
	/**
	 * Configure option in umeditor.properties, this is the allowed extensions for upload image files.
	 * Check umeditor_default.properties for default value.
	 */
	public static final String ALLOWED_EXTS = "allowed_exts";
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
	 * Configure option in umeditor.properties, after upload successed, servlet should return 
	 * uploaded file's reference url, this option is used for config prefix. <br/>
	 * <em>Note: Servlet context path will be prepended automatically, so do not include 
	 * context path in this option.</em><br/>
	 * @see UMInitializer
	 */
	public static final String DEST_URL_PREFIX = "dest_url_prefix";
	/**
	 * Configure option in umeditor.properties, the value will be class name of the implemented PathGenerator.
	 */
	public static final String PATH_GENERATOR = "path_generator";
	/**
	 * Default value of path_generator.
	 */
	public static final String DEFAULT_PATH_GENERATOR_CLASS = "cn.jhc.um.util.DateBasedPathGenerator";
	/**
	 * ResourceBundle property name for upload_size_exceeded.
	 */
	public static final String MSG_UPLOAD_EXCEEDED = "upload_size_exceeded";
	/**
	 * ResourceBundle property name for ext_violated.
	 */
	public static final String MSG_EXT_VIOLATED = "ext_violated";
	
	public static final String MSG_WRONG_ENCTYPE = "wrong_enctype";
	
	public static final String MSG_IO_ERROR = "io_error";
	/**
	 * Configure option in umeditor.properties, this is the UMEditor's toolbar options.
	 * Check umeditor_default.properties for default value.
	 */
	public static final String UM_TOOLBAR = "umeditor_toolbar";
}
