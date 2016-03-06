package control;

import java.sql.* ;
import java.util.* ;
//import java.io.* ;
//import java.text.* ;
import db.* ;
import ety.* ;

public class Control {

	private DBConnectionPool connector; 

	public Control() {
		connector = DBConnectionPool.getInstance();
	}
	
	public Vector <ChapterEty> getChapterList( int user_id ) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		Vector <ChapterEty> list = new Vector <ChapterEty>();

		try {
			conn = connector.getConn();

			StringBuffer query = new StringBuffer();
			
			query.append(" select CS.chapter_id, CS.title, CS.sub_title, CS.state, CS.score, ifnull(CA.chapter_id, -1) as isfirst from (select C.chapter_id, C.title, C.sub_title, C.state, ifnull(S.score, 0) as score from tblChapter C left join tblScore S on C.chapter_id = S.chapter_id and S.student_id=?) as CS left join (select C.chapter_id from tblChapter C join tblAnswerSheet A on C.chapter_id = A.chapter_id and A.student_id=?) as CA on CS.chapter_id = CA.chapter_id order by CS.chapter_id desc ");
			
			pstmt = conn.prepareStatement(query.toString());
			
			int i = 1 ;
			pstmt.setInt(i++, user_id);
			pstmt.setInt(i++, user_id);
			
			resultSet = pstmt.executeQuery();

			while( resultSet.next() ) {
				ChapterEty chapterEty = new ChapterEty();
				chapterEty.setId(resultSet.getInt("chapter_id"));
				chapterEty.setTitle(resultSet.getString("title"));
				chapterEty.setSub_title(resultSet.getString("sub_title"));
				if( resultSet.getInt("isfirst") != -1 && resultSet.getInt("state") != 3 )	chapterEty.setState(2);			//	응시한 경우 
				else									chapterEty.setState(resultSet.getInt("state"));
				chapterEty.setScore(resultSet.getInt("score"));
				list.add(chapterEty);
			}
			conn.commit();
			
		} catch(Exception ex) { 
			try {
				conn.rollback();
			} catch(SQLException se){}
		} finally {
			try {
				if(resultSet != null) resultSet.close();
				if(pstmt != null) pstmt.close();
			} catch(Exception ex) {
				//ex.printStackTrace();
			}
			connector.freeConn(conn);
		}
		return list;
	}
	
	public boolean isFirstQuestion( int user_id, int chapter_id ) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		boolean isOk = true;

		try {
			conn = connector.getConn();

			StringBuffer query = new StringBuffer();
			
			query.append(" select answer from tblAnswerSheet where student_id=? and chapter_id=? ");
			
			pstmt = conn.prepareStatement(query.toString());
			
			int i = 1 ;
			pstmt.setInt(i++, user_id);
			pstmt.setInt(i++, chapter_id);
			
			resultSet = pstmt.executeQuery();

			if( resultSet.next() ) {
				isOk = false;
			}
			conn.commit();
			
		} catch(Exception ex) { 
			try {
				conn.rollback();
			} catch(SQLException se){}
		} finally {
			try {
				if(resultSet != null) resultSet.close();
				if(pstmt != null) pstmt.close();
			} catch(Exception ex) {
				//ex.printStackTrace();
			}
			connector.freeConn(conn);
		}
		return isOk;
	}
	
	public Vector <QuestionEty> getQuestionList( int chapter_id ) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		Vector <QuestionEty> list = new Vector <QuestionEty>();

		try {
			conn = connector.getConn();

			StringBuffer query = new StringBuffer();
			
			query.append(" select question_id, chapter_id, content, point, type from tblQuestion where chapter_id = ? ");
			
			pstmt = conn.prepareStatement(query.toString());
			
			int i = 1 ;
			pstmt.setInt(i++, chapter_id);
			
			resultSet = pstmt.executeQuery();

			while( resultSet.next() ) {
				QuestionEty questionEty = new QuestionEty();
				questionEty.setQuestion_id(resultSet.getInt("question_id"));
				questionEty.setChapter_id(resultSet.getInt("chapter_id"));
				questionEty.setContent(resultSet.getString("content"));
				questionEty.setPoint(resultSet.getInt("point"));
				questionEty.setType(resultSet.getInt("type"));
				list.add(questionEty);
			}
			conn.commit();
			
		} catch(Exception ex) { 
			try {
				conn.rollback();
			} catch(SQLException se){}
		} finally {
			try {
				if(resultSet != null) resultSet.close();
				if(pstmt != null) pstmt.close();
			} catch(Exception ex) {
				//ex.printStackTrace();
			}
			connector.freeConn(conn);
		}
		return list;
	}
	
	public boolean insertAnswerSheet( Vector <QuestionEty> userAnswerSheet ) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		boolean isOk = true;

		try {
			conn = connector.getConn();

			StringBuffer query = new StringBuffer();
			
			for( int i = 0; i < userAnswerSheet.size(); i++ ) {
			
				query.append(" insert into tblAnswerSheet values(?, ?, ?, ?); ");
				
				pstmt = conn.prepareStatement(query.toString());
				
				int j = 1 ;
				pstmt.setInt(j++, userAnswerSheet.get(i).getStudent_id());
				pstmt.setInt(j++, userAnswerSheet.get(i).getQuestion_id());
				pstmt.setInt(j++, userAnswerSheet.get(i).getChapter_id());
				pstmt.setInt(j++, userAnswerSheet.get(i).getAnswer());

				isOk &= (pstmt.executeUpdate() == 1) ? true : false ;
			}
			
			if( isOk )	conn.commit();
			else		conn.rollback();
			
		} catch(Exception ex) { 
			try {
				conn.rollback();
			} catch(SQLException se){}
		} finally {
			try {
				if(resultSet != null) resultSet.close();
				if(pstmt != null) pstmt.close();
			} catch(Exception ex) {
				//ex.printStackTrace();
			}
			connector.freeConn(conn);
		}
		return isOk;
	}


	public Vector<QuestionEty> getSolutionList(int user_id, int chapter_id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		Vector <QuestionEty> list = new Vector <QuestionEty>();

		try {
			conn = connector.getConn();

			StringBuffer query = new StringBuffer();
			
			query.append(" select Q.question_id, Q.chapter_id, Q.content, Q.answer as qa, A.answer as aa, Q.solution, Q.point, Q.type from tblQuestion Q, tblAnswerSheet A where A.student_id = ? and Q.chapter_id = ? and Q.question_id = A.question_id ");
			
			pstmt = conn.prepareStatement(query.toString());
			
			int i = 1 ;
			pstmt.setInt(i++, user_id);
			pstmt.setInt(i++, chapter_id);
			
			resultSet = pstmt.executeQuery();

			while( resultSet.next() ) {
				QuestionEty questionEty = new QuestionEty();
				questionEty.setChapter_id(resultSet.getInt("chapter_id"));
				questionEty.setQuestion_id(resultSet.getInt("question_id"));
				questionEty.setContent(resultSet.getString("content"));
				questionEty.setAnswer(resultSet.getInt("qa"));
				questionEty.setOx( ( resultSet.getInt("qa") == resultSet.getInt("aa") ) );
				questionEty.setSolution(resultSet.getString("solution"));
				questionEty.setPoint(resultSet.getInt("point"));
				questionEty.setType(resultSet.getInt("type"));
				list.add(questionEty);
			}
			conn.commit();
			
		} catch(Exception ex) { 
			try {
				conn.rollback();
			} catch(SQLException se){}
		} finally {
			try {
				if(resultSet != null) resultSet.close();
				if(pstmt != null) pstmt.close();
			} catch(Exception ex) {
				//ex.printStackTrace();
			}
			connector.freeConn(conn);
		}
		return list;
	}
}