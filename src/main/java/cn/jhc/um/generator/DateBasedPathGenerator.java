package cn.jhc.um.generator;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

/**
 * Path generator based on current date.
 * @author luyanfei
 *
 */
public class DateBasedPathGenerator implements PathGenerator {

	private static final SimpleDateFormat YEARMONTH= new SimpleDateFormat("yyyyMM");
	private static final SimpleDateFormat PRECISE= new SimpleDateFormat("yyyyMMddHHmmssSSS");
	private static final Random RAND = new Random();
	
	@Override
	public String generate(HttpServletRequest request, String originalName) {
		final Date current = new Date();
		int index = originalName.lastIndexOf(".");
		String ext = index == -1 ? originalName : originalName.substring(index);
		return YEARMONTH.format(current) + File.separator + 
				PRECISE.format(current) + RAND.nextInt(1000) + ext;
	}

}
