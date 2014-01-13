package cn.jhc.um.generator;

import javax.servlet.http.HttpServletRequest;

/**
 * Interface to generate file path when a file is uploaded to server.  
 * The default PathGenerator implemetation is {@link DateBasedPathGenerator}.
 * Other implementation should be set in kindmanager.properties.
 * @author luyanfei
 *
 */
public interface PathGenerator {

	/**
	 * 
	 * @param request
	 * 		HttpServletRequest object for dynamic information.
	 * @param originalName
	 * 		this will be local file name in file system before upload in most cases. 
	 * @return
	 * 		relative path denote the path generated in server. 
	 * 		<em>Caution:</em> returned string should not begin with "/".
	 */
	public String generate(HttpServletRequest request, String originalName);
}
