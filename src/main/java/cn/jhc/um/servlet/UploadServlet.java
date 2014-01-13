package cn.jhc.um.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import cn.jhc.um.generator.PathGenerator;
import cn.jhc.um.util.ConstraintChecker;
import static cn.jhc.um.util.Constants.*;

/**
 * Handle file upload request. This servlet use MultipartConfig in Servlet 3.0 Specification.
 * @author luyanfei
 *
 */
public class UploadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Pattern FILENAME_PATTERN = Pattern
			.compile("filename=\"([^\" ]+)\"");

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();

		final ServletContext servletContext = request.getServletContext();
		Properties config = (Properties) servletContext.getAttribute(SC_UM_CONFIG);
		String uploadRoot = config.getProperty(UPLOAD_ROOT);

		ConstraintChecker checker = (ConstraintChecker)servletContext.getAttribute(SC_CONSTRAINT_CHECKER);

		String destUrl = config.getProperty(DEST_URL_PREFIX);

		PathGenerator pathGenerator = (PathGenerator) servletContext.getAttribute(SC_PATH_GENERATOR);
		
		ResourceBundle bundle = ResourceBundle.getBundle("messages", request.getLocale());
		
	    String type = request.getParameter("type");
	    String editorId = request.getParameter("editorid");
		
		Collection<Part> parts = null;
		try {
			parts = request.getParts();
		} catch (IllegalStateException e) {
			String pattern = bundle.getString(MSG_UPLOAD_EXCEEDED);
			out.println(buildResponseScript(editorId, destUrl,
					MessageFormat.format(pattern, 
							config.getProperty(HUMAN_UPLOAD_FILE_SIZE_LIMIT),
							config.getProperty(HUMAN_REQUEST_SIZE_LIMIT))));
			return;
		} catch (ServletException e) {
			
			
		} catch (IOException e) {
			
		}
		for (Part part : parts) {
			String fileName = extractFileName(part);
			if(fileName == null) continue;
			// 检查扩展名
			String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1)
					.toLowerCase();
			if( !checker.checkFileExtension(fileExt) ) {
				out.println(buildErrorMessage(bundle.getString(MSG_EXT_VIOLATED)));
				return;
			}

			String path = pathGenerator.generate(request, fileName);
			int last = path.lastIndexOf(File.separator);
			if(last > 0) {
				File lastDir = new File(uploadRoot, path.substring(0, last));
				if(!lastDir.exists())
					lastDir.mkdirs();
			}
			
			part.write(uploadRoot + File.separator + path);

			out.println(buildSuccessMessage(destUrl +path));

		}
	}

	/**
	 * Extract filename property from Part's Content-Disposition header.
	 * @param part
	 * @return the extracted filename value.
	 */
	private String extractFileName(Part part) {
		String disposition = part.getHeader("Content-Disposition");
		if (disposition == null)
			return null;
		Matcher matcher = FILENAME_PATTERN.matcher(disposition);
		if (!matcher.find())
			return null;
		return matcher.group(1);
	}

	private String buildResponseScript(String editorId, String url, String state) {
		return "<script>parent.UM.getEditor('"+ editorId +"').getWidgetCallback('image')('" 
				+ url + "','" + state + "')</script>";
	}
	
	private String buildSuccessMessage(String url) {
		return "{\"error\":0,\"url\":\"" + url+ "\"}";
	}
	
	private String buildErrorMessage(String message) {
		return "{\"error\":1,\"message\":\"" + message + "\"}";
	}
}
