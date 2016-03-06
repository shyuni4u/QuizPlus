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

public class LoadSolutionList extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

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
		String str_user_id = req.getParameter("user_id");
		String str_chapter_id = req.getParameter("chapter_id");
		int i_user_id;
		int i_chapter_id;
		
		try {
			i_user_id = Integer.parseInt(str_user_id);
			i_chapter_id = Integer.parseInt(str_chapter_id);
		} catch(NumberFormatException ex) {
			i_user_id = -1;
			i_chapter_id = -1;
		}
		
		//	response
		JSONObject jsonRootResp = null;
		
		Control control = new Control();
		
		Vector <QuestionEty> list = control.getSolutionList(i_user_id, i_chapter_id);
		
		try {
			jsonRootResp = new JSONObject(); {
				JSONArray result = new JSONArray();
				
				if( list != null ) {
					for( QuestionEty tempEty : list ) {
						JSONObject jsonItem = new JSONObject(); {
							jsonItem.put("chapter_id",		tempEty.getChapter_id());
							jsonItem.put("question_id",		tempEty.getQuestion_id());
							jsonItem.put("content",			tempEty.getContent());
							jsonItem.put("answer",			tempEty.getAnswer());
							jsonItem.put("ox",				( tempEty.isOx() ) ? "O":"X");
							jsonItem.put("solution",		tempEty.getSolution());
							jsonItem.put("point",			tempEty.getPoint());
							jsonItem.put("type",			tempEty.getType());
						}
						result.put(jsonItem);
					}
				}
				
				jsonRootResp.put("question_list_result", result);
				
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