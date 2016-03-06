package api;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.http.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import control.*;
import ety.*;
import misc.UnitMisc;

public class LoadChapterList extends HttpServlet {

	private static final long serialVersionUID = 8016334551733784977L;

	//------------------------------------------------------------------------------------------------------------------
	//	handler
	//------------------------------------------------------------------------------------------------------------------
	/**
	 *	do GET
	 *	@param		req			request
	 *	@param		resp		response
	 *	@throws		IOException
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		//System.out.println("-----------------------------------------------------------------------------------------");
		//System.out.println("begin GET " + req.getRequestURI());
		//System.out.println(req.getQueryString());

		try {
			__doGet(req, resp);
		}
		catch (IOException e) {
			e.printStackTrace();
			UnitMisc.makeErrorJSON(req, resp, 500);
		}

		//System.out.println("end GET");
	}

	//------------------------------------------------------------------------------------------------------------------
	//	action
	//------------------------------------------------------------------------------------------------------------------
	/**
	 *	do GET
	 *	@param		req			request
	 *	@param		resp		response
	 *	@throws		IOException
	 */
	private void __doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {		
		//	response
		JSONObject jsonRootResp = null;
		
		String str_user_id = req.getParameter("user_id");
		int i_user_id = -1;
		
		try {
			i_user_id = Integer.parseInt(str_user_id);
		} catch(NumberFormatException ex) {
			i_user_id = -1;
		}
		
		Control control = new Control();
		
		Vector <ChapterEty> list = control.getChapterList(i_user_id);
		
		try {
			jsonRootResp = new JSONObject(); {
				JSONArray result = new JSONArray();
				
				if( list != null ) {
					for( ChapterEty tempEty : list ) {
						JSONObject jsonItem = new JSONObject(); {
							jsonItem.put("chapter_id",		tempEty.getId());
							jsonItem.put("title",			tempEty.getTitle());
							jsonItem.put("subtitle",		tempEty.getSub_title());
							jsonItem.put("state",			tempEty.getState());
							jsonItem.put("score",			tempEty.getScore());
						}
						result.put(jsonItem);
					}
				}
				
				jsonRootResp.put("chapter_list_result", result);
				
			}
		} catch( JSONException ex) {
			ex.printStackTrace();
		}

		String strResponse = jsonRootResp.toString();

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