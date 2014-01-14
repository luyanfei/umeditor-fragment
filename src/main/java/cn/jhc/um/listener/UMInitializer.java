package cn.jhc.um.listener;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebListener;

import cn.jhc.um.generator.PathGenerator;
import cn.jhc.um.servlet.UMEditorConfigServlet;
import cn.jhc.um.servlet.UploadServlet;
import cn.jhc.um.util.ConstraintChecker;
import static cn.jhc.um.util.Constants.*;

/**
 * This is the umeditor-fragment's initialize entrance. When ServletContext is initialized, some things will happen:
 * <ol>
 * <li> A properties file named "umeditor.properties" will be searched in classpath, configuration properties
 * in this file will be merged with default configuration properties. The Properties object will be kept
 * in ServletContext with the attribute name {@link Constants.SC_UM_CONFIG}, other servlet will need these configuration
 * properties.</li>
 * <li> The directory for upload files will be checked, if it does not exist or cann't be written, a RuntimeException
 * will be thrown. </li>
 * <li> PathGenerator object will be created from class name configured in properties file, and this object will be 
 * kept in ServletContext with the attribute name {@link Constants.SC_PATH_GENERATOR}.</li>
 * </ol>
 * @author luyanfei
 * @see cn.jhc.um.util.Constants
 */

@WebListener
public class UMInitializer implements ServletContextListener {
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext context = sce.getServletContext();
		Properties properties = readFromConfigFile(context);
		computeHumanReadableSizeLimit(properties);
		//for test
		properties.list(System.err);
		context.setAttribute(SC_UM_CONFIG, properties);
		checkUploadDirectories(properties);
		PathGenerator pathGenerator = loadPathGenerator(properties);
		context.setAttribute(SC_PATH_GENERATOR, pathGenerator);
		
		ConstraintChecker checker = new ConstraintChecker(properties);
		context.setAttribute(SC_CONSTRAINT_CHECKER, checker);
		
		initServlets(context, properties);
	}

	private void computeHumanReadableSizeLimit(Properties properties) {
		long maxFileSize = new Long(properties.getProperty(UPLOAD_FILE_SIZE_LIMIT));
		properties.setProperty(HUMAN_UPLOAD_FILE_SIZE_LIMIT, toHumanReadable(maxFileSize));
		long maxRequestSize = new Long(properties.getProperty(UPLOAD_REQUEST_SIZE_LIMIT));
		properties.setProperty(HUMAN_REQUEST_SIZE_LIMIT, toHumanReadable(maxRequestSize));
	}

	private String toHumanReadable(long sizeOfBytes) {
		long kb = 0;
		if(sizeOfBytes<1024)
			return sizeOfBytes + "B";
		else {
			kb = sizeOfBytes / 1024;
			if(kb<1024) {
				return kb + "KB";
			}
			else {
				long mb = kb / 1024;
				return mb + "MB";
			}
		}
	}

	private void initServlets(ServletContext context, Properties properties) {
		ServletRegistration.Dynamic uploadDynamic = context.addServlet("uploadServlet", UploadServlet.class);
		uploadDynamic.addMapping(properties.getProperty(UPLOAD_SERVLET_URL));
		long maxFileSize = new Long(properties.getProperty(UPLOAD_FILE_SIZE_LIMIT));
		long maxRequestSize = new Long(properties.getProperty(UPLOAD_REQUEST_SIZE_LIMIT));
		MultipartConfigElement mce = new MultipartConfigElement("", maxFileSize, maxRequestSize, 0);
		uploadDynamic.setMultipartConfig(mce);
		
		ServletRegistration.Dynamic configDynamic = context.addServlet("configServlet", UMEditorConfigServlet.class);
		configDynamic.addMapping("/umeditor/umeditor.config.js");
	}

	private PathGenerator loadPathGenerator(Properties properties) {
		String className = properties.getProperty(PATH_GENERATOR);
		className = className == null ? DEFAULT_PATH_GENERATOR_CLASS : className;
		
		Class<?> clazz = null;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		//check whether this class implemented PathGenerator interface 
		boolean foundInterface = false;
		for(Class<?> clz : clazz.getInterfaces()) {
			if(PathGenerator.class.isAssignableFrom(clz)) {
				foundInterface = true;
				break;
			}
		}
		if(!foundInterface)
			throw new RuntimeException("Cann't assign to PathGenerator interface, "
					+ "the " + PATH_GENERATOR + " in configure file is wrong.");
		PathGenerator pathGenerator = null;
		try {
			pathGenerator = (PathGenerator)clazz.newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		return pathGenerator;
	}

	private void checkUploadDirectories(Properties properties) {
		String root = properties.getProperty(UPLOAD_ROOT);
		File rootDir = new File(root);
		rootDir.mkdirs();
		if(!rootDir.isAbsolute() || !rootDir.exists() || !rootDir.canWrite())
			throw new RuntimeException("Upload root directory: " + root + "does not exists or can not be written.");
	}

	private Properties readFromConfigFile(ServletContext context) {
		ClassLoader loader = context.getClassLoader();
		InputStream defaultConfig = loader.getResourceAsStream("umeditor_default.properties");
		InputStream config = loader.getResourceAsStream("umeditor.properties");
		Properties properties = new Properties();
		Properties userProperties = new Properties();
		try {
			properties.load(defaultConfig);
			if(config != null)
				userProperties.load(config);
			properties.putAll(userProperties);
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(defaultConfig != null) 
					defaultConfig.close();
				if(config != null)
					config.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		String webUploadDir = properties.getProperty(DEFAULT_WEB_UPLOAD_DIR);
		webUploadDir = webUploadDir == null ? "upload" : webUploadDir;
		String uploadRoot = properties.getProperty(UPLOAD_ROOT);
		if( uploadRoot == null) {
			String defaultUploadRoot = context.getRealPath("/") + webUploadDir + File.separator;
			properties.setProperty(UPLOAD_ROOT, defaultUploadRoot);
		}
		else {
			//make sure upload root is ended with seperator
			if(!uploadRoot.endsWith(File.separator))
				properties.setProperty(UPLOAD_ROOT, uploadRoot + File.separator);
		}
		
		String destUrlPrefix = properties.getProperty(DEST_URL_PREFIX);
		if(destUrlPrefix == null) {
			String defaultPrefix = context.getContextPath() + "/" + webUploadDir +"/";
			properties.setProperty(DEST_URL_PREFIX, defaultPrefix);
		}
		else {
			//prepend servlet context path
			destUrlPrefix = context.getContextPath() + "/" + webUploadDir;
			//make sure prefix end with "/"
			if(!destUrlPrefix.endsWith("/")) {
				properties.setProperty(DEST_URL_PREFIX, destUrlPrefix + "/");
			}
		}
		return properties;
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

}
