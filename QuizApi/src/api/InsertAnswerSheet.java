package api;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.http.*;

import org.json.JSONException;
import org.json.JSONObject;

import control.*;
import ety.*;
import misc.UnitMisc;

public class InsertAnswerSheet extends HttpServlet {

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
		String answersheet = req.getParameter("answersheet");
		int i_user_id, i_chapter_id;
		
		try {
			i_user_id = Integer.parseInt(str_user_id);
			i_chapter_id = Integer.parseInt(str_chapter_id);
		} catch(NumberFormatException ex) {
			i_user_id = -1;
			i_chapter_id = -1;
		}

		Vector <QuestionEty> list = new Vector<QuestionEty>();
		String [] question_answer = answersheet.split("_");
		
		for( int i = 0; i < question_answer.length; i++ ) {
			String [] question_answer_split = question_answer[i].split("-");
			QuestionEty tempEty = new QuestionEty();
			tempEty.setStudent_id(i_user_id);
			tempEty.setChapter_id(i_chapter_id);
			tempEty.setQuestion_id(Integer.parseInt(question_answer_split[0]));
			tempEty.setAnswer(Integer.parseInt(question_answer_split[1]));
			list.add(tempEty);
			//System.out.println("question: "+Integer.parseInt(question_answer_split[0]));
			//System.out.println("answer: "+Integer.parseInt(question_answer_split[1]));
		}
		
		//	response
		JSONObject jsonRootResp = null;
		
		Control control = new Control();
		
		if( control.isFirstQuestion(i_user_id, i_chapter_id) ) {
			
			try {
				jsonRootResp = new JSONObject(); {
					
					if( control.insertAnswerSheet(list) )	jsonRootResp.put("insert_result", "ok");
					else									jsonRootResp.put("insert_result", "fail");
				}
			} catch( JSONException ex) {
				ex.printStackTrace();
			}
			
		} else {
			
			jsonRootResp = new JSONObject();
			try {
				jsonRootResp.put("insert_result", "fail");
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