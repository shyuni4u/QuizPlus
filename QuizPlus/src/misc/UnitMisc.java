/**
 *	UnitMisc.java
 *	@author		arisu717
 */
package misc;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *	miscellaneous class
 */
public class UnitMisc {
	/**
	 *	make delete item
	 *	@return		deleted item
	 */
	public static String makeDeleteItem(String strItem, boolean fDelete) {
		return ((fDelete) ? "<s>" : "") + strItem + ((fDelete) ? "</s>" : "");
	}

	/**
	 *	make error json
	 *	@param		req			request
	 *	@param		resp		response
	 *	@param		nErrorCode		error code
	 *	@throws		IOException 
	 */
	public static void makeErrorJSON(HttpServletRequest req, HttpServletResponse resp, int nErrorCode)
			throws IOException {
		//	response
		String strResponse;
		switch (nErrorCode) {
		case 404:
			strResponse = "{\"result\":{\"strResult\":\"Not Found\",\"nCode\": " + nErrorCode + "}}";
			break;

		case 500:
			strResponse = "{\"result\":{\"strResult\":\"Internal Server Error\",\"nCode\": " + nErrorCode + "}}";
			break;

		default:
			strResponse = "{\"result\":{\"strResult\":\"Unknown\",\"nCode\": " + nErrorCode + "}}";
			break;
		}

		String strCallback = req.getParameter("callback");
		if (strCallback != null) {
			strResponse = strCallback + "(" + strResponse + ")";
		}

		//	set response
		resp.setHeader("Cache-Control", "no-cache");
		resp.setContentType("application/json");
		resp.setCharacterEncoding("utf-8");
		resp.getWriter().println(strResponse);
	}
}
