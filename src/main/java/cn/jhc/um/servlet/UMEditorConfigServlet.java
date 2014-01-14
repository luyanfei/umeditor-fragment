package cn.jhc.um.servlet;


import static cn.jhc.um.util.Constants.*;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UMEditorConfigServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		final ServletContext servletContext = req.getServletContext();
		Properties config = (Properties) servletContext.getAttribute(SC_UM_CONFIG);
		req.setAttribute("upload_servlet_url", config.getProperty(UPLOAD_SERVLET_URL));
		req.setAttribute("toolbar", config.getProperty(UM_TOOLBAR));
		req.getRequestDispatcher("umeditor.config.jsp").forward(req, resp);
	}
}
