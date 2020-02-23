package corp.asbp.platform.is.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpMethod;

import com.fasterxml.jackson.databind.ObjectMapper;

import corp.asbp.platform.is.dto.SsHeader;

public class CustomUtil {

	public static long currentTimeStamp() {
		return System.currentTimeMillis() / 1000L;
	}

	public static SsHeader getSsHeader(HttpServletRequest request) throws IOException {
		String ssHeaderStr = request.getHeader(Constants.SS_HEADER);

		ObjectMapper map = new ObjectMapper();
		System.out.println(ssHeaderStr);

		if (!isEmptyString(ssHeaderStr)) {

			return map.readValue(ssHeaderStr, SsHeader.class);
		}
		throw new IllegalArgumentException("Headers can't be empty!");
	}

	public static boolean isEmptyString(String string) {
		return string == null || string.isEmpty();
	}

	public static String getRequestUri(HttpServletRequest request) {
		return request.getHeader(Constants.REQUEST_URI);
	}

	public static HttpMethod getRequestMethod(HttpServletRequest request) {
		return HttpMethod.valueOf(request.getHeader(Constants.REQUEST_METHOD));
	}

}
