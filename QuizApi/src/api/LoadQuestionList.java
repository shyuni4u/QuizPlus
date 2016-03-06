package api;

import java.io.IOException;
import java.util.Random;
import java.util.Vector;

import javax.servlet.http.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import control.*;
import ety.*;
import misc.UnitMisc;

public class LoadQuestionList extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final int question_size = 10;

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
		int i_user_id, i_chapter_id;
		
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
		
		if( control.isFirstQuestion(i_user_id, i_chapter_id) ) {
		
			Vector <QuestionEty> list = control.getQuestionList(i_chapter_id);
			Vector <QuestionEty> random_list = new Vector<QuestionEty>();
			
			Random oRandom = new Random();
			int idx = -1;
			
			for( int i = 0 ; i < question_size ; i++ ) {
				idx = oRandom.nextInt(list.size());
				random_list.add(list.get(idx));
				list.remove(idx);
				if( list.size() == 0 ) break;
			}
			
			try {
				jsonRootResp = new JSONObject(); {
					JSONArray result = new JSONArray();
					
					if( list != null ) {
						for( QuestionEty tempEty : random_list ) {
							JSONObject jsonItem = new JSONObject(); {
								jsonItem.put("chapter_id",		tempEty.getChapter_id());
								jsonItem.put("question_id",		tempEty.getQuestion_id());
								jsonItem.put("content",			tempEty.getContent());
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
			
		} else {
			
			jsonRootResp = new JSONObject();
			try {
				jsonRootResp.put("question_list_result", "Warning");
			} catch( JSONException ex) {
				ex.printStackTrace();
			}
			
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