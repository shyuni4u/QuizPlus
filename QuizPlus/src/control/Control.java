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

	public Vector <ChapterEty> getChapterList() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		Vector <ChapterEty> list = new Vector <ChapterEty>();

		try {
			conn = connector.getConn();

			StringBuffer query = new StringBuffer();
			
			query.append(" select chapter_id, title, sub_title, state from tblChapter order by chapter_id asc ");
			
			pstmt = conn.prepareStatement(query.toString());
			
			resultSet = pstmt.executeQuery();

			while( resultSet.next() ) {
				ChapterEty chapterEty = new ChapterEty();
				chapterEty.setId(resultSet.getInt("chapter_id"));
				chapterEty.setTitle(resultSet.getString("title"));
				chapterEty.setSub_title(resultSet.getString("sub_title"));
				chapterEty.setState(resultSet.getInt("state"));
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
	
	public boolean insertChapter( ChapterEty chapterEty ) {
		Connection conn = null ;
		PreparedStatement pstmt = null ;
		boolean isOk = false ;
	
		try {
			conn = connector.getConn() ;
			
			StringBuffer query = new StringBuffer() ;
			
			query.append(" insert into tblChapter(title, sub_title, state) values (?, ?, 0) ");
			pstmt = conn.prepareStatement(query.toString()) ;
			
			int i = 1 ;
			pstmt.setString(i++, chapterEty.getTitle());
			pstmt.setString(i++, chapterEty.getSub_title());
						
			isOk = (pstmt.executeUpdate() == 1) ? true : false ;
	
			if(isOk) { conn.commit() ; }
			else { conn.rollback() ; }
	
		} catch(Exception ex) { 
			ex.printStackTrace() ;
			try {
				conn.rollback() ;
			} catch(SQLException se){}
		} finally {
			try {
				if(pstmt != null) pstmt.close() ;
			} catch(Exception ex) {
				ex.printStackTrace() ;
			}
			connector.freeConn(conn) ;
		}				
		return isOk ;
	}
	
	public boolean updateChapter( ChapterEty chapterEty ) {
		Connection conn = null ;
		PreparedStatement pstmt = null ;
		boolean isOk = false ;
	
		try {
			conn = connector.getConn() ;
			
			StringBuffer query = new StringBuffer() ;
			
			query.append(" update tblChapter set title = ?, sub_title = ? where chapter_id = ? ");
			pstmt = conn.prepareStatement(query.toString()) ;
			
			int i = 1 ;
			pstmt.setString(i++, chapterEty.getTitle());
			pstmt.setString(i++, chapterEty.getSub_title());
			pstmt.setInt(i++, chapterEty.getId());
						
			isOk = (pstmt.executeUpdate() == 1) ? true : false ;
	
			if(isOk) { conn.commit() ; }
			else { conn.rollback() ; }
	
		} catch(Exception ex) { 
			ex.printStackTrace() ;
			try {
				conn.rollback() ;
			} catch(SQLException se){}
		} finally {
			try {
				if(pstmt != null) pstmt.close() ;
			} catch(Exception ex) {
				ex.printStackTrace() ;
			}
			connector.freeConn(conn) ;
		}				
		return isOk ;
	}
	
	public boolean deleteChapter( int id ) {
		Connection conn = null ;
		PreparedStatement pstmt = null ;
		boolean isOk = false ;
	
		try {
			conn = connector.getConn() ;
			
			StringBuffer query = new StringBuffer() ;
			
			query.append(" delete from tblChapter where chapter_id = ? ");
			pstmt = conn.prepareStatement(query.toString()) ;
			
			int i = 1 ;
			pstmt.setInt(i++, id);
						
			isOk = (pstmt.executeUpdate() == 1) ? true : false ;
	
			if(isOk) { conn.commit() ; }
			else { conn.rollback() ; }
	
		} catch(Exception ex) { 
			ex.printStackTrace() ;
			try {
				conn.rollback() ;
			} catch(SQLException se){}
		} finally {
			try {
				if(pstmt != null) pstmt.close() ;
			} catch(Exception ex) {
				ex.printStackTrace() ;
			}
			connector.freeConn(conn) ;
		}				
		return isOk ;
	}
	
	public QuestionEty getQuestion( int question_id ) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		QuestionEty questionEty = new QuestionEty();

		try {
			conn = connector.getConn();

			StringBuffer query = new StringBuffer();
			
			query.append(" select question_id, chapter_id, content, answer, solution, point, type from tblQuestion where question_id = ? order by question_id desc ");
			
			pstmt = conn.prepareStatement(query.toString());
			
			int i = 1 ;
			pstmt.setInt(i++, question_id);
			
			resultSet = pstmt.executeQuery();

			if( resultSet.next() ) {
				questionEty.setQuestion_id(resultSet.getInt("question_id"));
				questionEty.setChapter_id(resultSet.getInt("chapter_id"));
				questionEty.setContent(resultSet.getString("content"));
				questionEty.setAnswer(resultSet.getInt("answer"));
				questionEty.setSolution(resultSet.getString("solution"));
				questionEty.setPoint(resultSet.getInt("point"));
				questionEty.setType(resultSet.getInt("type"));
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
		return questionEty;
	}
	
	public Vector <QuestionEty> getQuestionList( int chapter_id ) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		Vector <QuestionEty> list = new Vector <QuestionEty>();

		try {
			conn = connector.getConn();

			StringBuffer query = new StringBuffer();
			
			query.append(" select question_id, chapter_id, content, answer, solution, point, type from tblQuestion where chapter_id = ? order by question_id desc ");
			
			pstmt = conn.prepareStatement(query.toString());
			
			int i = 1 ;
			pstmt.setInt(i++, chapter_id);
			
			resultSet = pstmt.executeQuery();

			while( resultSet.next() ) {
				QuestionEty questionEty = new QuestionEty();
				questionEty.setQuestion_id(resultSet.getInt("question_id"));
				questionEty.setChapter_id(resultSet.getInt("chapter_id"));
				questionEty.setContent(resultSet.getString("content"));
				questionEty.setAnswer(resultSet.getInt("answer"));
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
	
	public boolean insertQuestion( QuestionEty questionEty ) {
		Connection conn = null ;
		PreparedStatement pstmt = null ;
		boolean isOk = false ;
	
		try {
			conn = connector.getConn() ;
			
			StringBuffer query = new StringBuffer() ;
			
			query.append(" insert into tblQuestion(chapter_id, content, answer, solution, point, type) values (?, ?, ?, ?, ?, ?) ");
			pstmt = conn.prepareStatement(query.toString()) ;
			
			int i = 1 ;
			pstmt.setInt(i++, questionEty.getChapter_id());
			pstmt.setString(i++, questionEty.getContent());
			pstmt.setInt(i++, questionEty.getAnswer());
			pstmt.setString(i++, questionEty.getSolution());
			pstmt.setInt(i++, questionEty.getPoint());
			pstmt.setInt(i++, questionEty.getType());
						
			isOk = (pstmt.executeUpdate() == 1) ? true : false ;
	
			if(isOk) { conn.commit() ; }
			else { conn.rollback() ; }
	
		} catch(Exception ex) { 
			ex.printStackTrace() ;
			try {
				conn.rollback() ;
			} catch(SQLException se){}
		} finally {
			try {
				if(pstmt != null) pstmt.close() ;
			} catch(Exception ex) {
				ex.printStackTrace() ;
			}
			connector.freeConn(conn) ;
		}				
		return isOk ;
	}
	
	public boolean updateQuestion( QuestionEty questionEty ) {
		Connection conn = null ;
		PreparedStatement pstmt = null ;
		boolean isOk = false ;
	
		try {
			conn = connector.getConn() ;
			
			StringBuffer query = new StringBuffer() ;
			
			query.append(" update tblQuestion set content = ?, answer = ?, solution = ?, point = ?, type = ? where question_id = ? ");
			pstmt = conn.prepareStatement(query.toString()) ;
			
			int i = 1 ;
			pstmt.setString(i++, questionEty.getContent());
			pstmt.setInt(i++, questionEty.getAnswer());
			pstmt.setString(i++, questionEty.getSolution());
			pstmt.setInt(i++, questionEty.getPoint());
			pstmt.setInt(i++, questionEty.getType());
			pstmt.setInt(i++, questionEty.getQuestion_id());
						
			isOk = (pstmt.executeUpdate() == 1) ? true : false ;
	
			if(isOk) { conn.commit() ; }
			else { conn.rollback() ; }
	
		} catch(Exception ex) { 
			ex.printStackTrace() ;
			try {
				conn.rollback() ;
			} catch(SQLException se){}
		} finally {
			try {
				if(pstmt != null) pstmt.close() ;
			} catch(Exception ex) {
				ex.printStackTrace() ;
			}
			connector.freeConn(conn) ;
		}				
		return isOk ;
	}
	
	public boolean deleteQuestion( int id ) {
		Connection conn = null ;
		PreparedStatement pstmt = null ;
		boolean isOk = false ;
	
		try {
			conn = connector.getConn() ;
			
			StringBuffer query = new StringBuffer() ;
			
			query.append(" delete from tblQuestion where question_id = ? ");
			pstmt = conn.prepareStatement(query.toString()) ;
			
			int i = 1 ;
			pstmt.setInt(i++, id);
						
			isOk = (pstmt.executeUpdate() == 1) ? true : false ;
	
			if(isOk) { conn.commit() ; }
			else { conn.rollback() ; }
	
		} catch(Exception ex) { 
			ex.printStackTrace() ;
			try {
				conn.rollback() ;
			} catch(SQLException se){}
		} finally {
			try {
				if(pstmt != null) pstmt.close() ;
			} catch(Exception ex) {
				ex.printStackTrace() ;
			}
			connector.freeConn(conn) ;
		}				
		return isOk ;
	}
	
	public boolean changeState(int state, int chapter_id) {
		Connection conn = null ;
		PreparedStatement pstmt = null ;
		boolean isOk = false ;
	
		try {
			conn = connector.getConn() ;
			
			StringBuffer query = new StringBuffer() ;
			
			query.append(" update tblChapter set state = ? where chapter_id = ? ");
			pstmt = conn.prepareStatement(query.toString()) ;
			
			int i = 1 ;
			pstmt.setInt(i++, state);
			pstmt.setInt(i++, chapter_id);
						
			isOk = (pstmt.executeUpdate() == 1) ? true : false ;
	
			if(isOk) { conn.commit() ; }
			else { conn.rollback() ; }
	
		} catch(Exception ex) { 
			ex.printStackTrace() ;
			try {
				conn.rollback() ;
			} catch(SQLException se){}
		} finally {
			try {
				if(pstmt != null) pstmt.close() ;
			} catch(Exception ex) {
				ex.printStackTrace() ;
			}
			connector.freeConn(conn) ;
		}				
		return isOk ;
	}
	
	public Vector <ScoreEty> getScoreList( int chapter_id ) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		Vector <ScoreEty> list = new Vector <ScoreEty>();

		try {
			conn = connector.getConn();

			StringBuffer query = new StringBuffer();
			
			query.append(" select student_id, chapter_id, score from tblScore where chapter_id = ? order by score desc ");
			
			pstmt = conn.prepareStatement(query.toString());
			
			int i = 1 ;
			pstmt.setInt(i++, chapter_id);
			
			resultSet = pstmt.executeQuery();

			while( resultSet.next() ) {
				ScoreEty scoreEty = new ScoreEty();
				scoreEty.setChapter_id(resultSet.getInt("chapter_id"));
				scoreEty.setStudent_id(resultSet.getInt("student_id"));
				scoreEty.setScore(resultSet.getInt("score"));
				list.add(scoreEty);
			}
			
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
	
	public Vector <ChapterEty> getChapterListAVG() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		Vector <ChapterEty> list = new Vector <ChapterEty>();

		try {
			conn = connector.getConn();

			StringBuffer query = new StringBuffer();
			
			query.append(" select distinct A.chapter_id, A.title, A.sub_title, (SELECT AVG(score) FROM tblScore as B WHERE A.chapter_id = B.chapter_id) as avg_score FROM tblChapter as A, tblScore as B WHERE A.chapter_id = B.chapter_id AND A.state > 2 ORDER BY A.chapter_id ASC ");
			
			pstmt = conn.prepareStatement(query.toString());
			
			resultSet = pstmt.executeQuery();

			while( resultSet.next() ) {
				ChapterEty chapterEty = new ChapterEty();
				chapterEty.setId(resultSet.getInt("chapter_id"));
				chapterEty.setTitle(resultSet.getString("title"));
				chapterEty.setSub_title(resultSet.getString("sub_title"));
				chapterEty.setAvg_score(resultSet.getDouble("avg_score"));
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
	
	public Vector <ChapterEty> getChapterHighLowScore() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		Vector <ChapterEty> list = new Vector <ChapterEty>();

		try {
			conn = connector.getConn();

			StringBuffer query = new StringBuffer();
			
			query.append(" select distinct A.chapter_id, (SELECT MAX(score) FROM tblScore as B WHERE A.chapter_id = B.chapter_id) as high_score, (SELECT MIN(score) FROM tblScore as B WHERE A.chapter_id = B.chapter_id) as low_score from tblChapter as A, tblScore as B WHERE A.chapter_id = B.chapter_id AND A.state > 2 ORDER BY A.chapter_id ASC ");
			
			pstmt = conn.prepareStatement(query.toString());
			
			resultSet = pstmt.executeQuery();

			while( resultSet.next() ) {
				ChapterEty chapterEty = new ChapterEty();
				chapterEty.setId(resultSet.getInt("chapter_id"));
				chapterEty.setLow_score(resultSet.getInt("low_score"));
				chapterEty.setHigh_score(resultSet.getInt("high_score"));
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
	
	public boolean insertScore( int chapter_id ) {
		Connection conn = null ;
		PreparedStatement pstmt = null ;
		boolean isOk = false ;
	
		try {
			conn = connector.getConn() ;
			
			StringBuffer query = new StringBuffer() ;
			
			query.append(" INSERT INTO tblScore(student_id, score, chapter_id) (select R.student_id, " +
					" ifnull(((R.o_point/R.o_point+R.x_point)*10), 0) as result, B.chapter_id from " +
					"(" +
					"select O.o_point, ifnull(X.x_point, 0) as x_point, O.student_id from " +
					"(select sum(Q.point) as o_point, A.student_id from tblQuestion Q, " +
					"tblAnswerSheet A where A.answer = Q.answer and Q.chapter_id = ? and " +
					"A.chapter_id = ? and Q.question_id = A.question_id group by A.student_id) as O " +
					"left join" +
					"(select sum(Q.point) as x_point, A.student_id from tblQuestion Q, " +
					"tblAnswerSheet A where A.answer != Q.answer and Q.chapter_id = ? and " +
					"A.chapter_id = ? and Q.question_id = A.question_id group by A.student_id) as X " +
					"on O.student_id = X.student_id" +
					" union " +
					"select ifnull(O.o_point, 0) as o_point, X.x_point, X.student_id from " +
					" (select sum(Q.point) as o_point, A.student_id from tblQuestion Q, " +
					"tblAnswerSheet A where A.answer = Q.answer and Q.chapter_id = ? and " +
					"A.chapter_id = ? and Q.question_id = A.question_id group by A.student_id) as O " +
					"right join" +
					"(select sum(Q.point) as x_point, A.student_id from tblQuestion Q, " +
					"tblAnswerSheet A where A.answer != Q.answer and Q.chapter_id = ? and " +
					"A.chapter_id = ? and Q.question_id = A.question_id group by A.student_id) as X " +
					"on O.student_id = X.student_id" +
					") as R, tblChapter as B where B.chapter_id = ?)");
					
			pstmt = conn.prepareStatement(query.toString()) ;
			
			int i = 1 ;
			pstmt.setInt(i++, chapter_id);
			pstmt.setInt(i++, chapter_id);
			pstmt.setInt(i++, chapter_id);
			pstmt.setInt(i++, chapter_id);
			pstmt.setInt(i++, chapter_id);
			pstmt.setInt(i++, chapter_id);
			pstmt.setInt(i++, chapter_id);
			pstmt.setInt(i++, chapter_id);
			pstmt.setInt(i++, chapter_id);
			
						
			isOk = (pstmt.executeUpdate() == 1) ? true : false ;
	
			if(isOk) { conn.commit() ; }
			else { conn.rollback() ; }
	
		} catch(Exception ex) { 
			ex.printStackTrace() ;
			try {
				conn.rollback() ;
			} catch(SQLException se){}
		} finally {
			try {
				if(pstmt != null) pstmt.close() ;
			} catch(Exception ex) {
				ex.printStackTrace() ;
			}
			connector.freeConn(conn) ;
		}				
		return isOk ;
	}	
	
//	/*
//	 * 
//	 * Homepage Control
//	 * 
//	 */
//	public IdolEty getIdolById( int idol_id ) {
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet resultSet = null;
//		IdolEty idolEty = new IdolEty();
//
//		try {
//			conn = connector.getConn();
//
//			StringBuffer query = new StringBuffer();
//			
//			query.append(" select name, info, image, color, image_ok from tblIdol where id = ? ");
//			
//			pstmt = conn.prepareStatement(query.toString());
//			
//			int i = 1 ;
//			pstmt.setLong(i++, idol_id);
//			
//			resultSet = pstmt.executeQuery();
//
//			if( resultSet.next() ) {
//				idolEty.setName(resultSet.getString("name"));
//				idolEty.setInfo(resultSet.getString("info"));
//				idolEty.setImage(resultSet.getString("image"));
//				idolEty.setImage_ok(resultSet.getInt("image_ok"));
//				idolEty.setColor(resultSet.getString("color"));
//			}
//			conn.commit();
//			
//		} catch(Exception ex) { 
//			//ex.printStackTrace();
//			try {
//				conn.rollback();
//			} catch(SQLException se){}
//		} finally {
//			try {
//				if(resultSet != null) resultSet.close();
//				if(pstmt != null) pstmt.close();
//			} catch(Exception ex) {
//				//ex.printStackTrace();
//			}
//			connector.freeConn(conn);
//		}
//		return idolEty;
//	}
//	
//	public Vector <IdolEty> getIdolList() {
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet resultSet = null;
//		Vector <IdolEty> list = new Vector <IdolEty>();
//
//		try {
//			conn = connector.getConn();
//
//			StringBuffer query = new StringBuffer();
//			
//			query.append(" select id, name, info, image, image_ok, color from tblIdol ");
//			
//			pstmt = conn.prepareStatement(query.toString());
//			
//			resultSet = pstmt.executeQuery();
//
//			while( resultSet.next() ) {
//				IdolEty idolEty = new IdolEty();
//				idolEty.setId(resultSet.getLong("id"));
//				idolEty.setName(resultSet.getString("name"));
//				idolEty.setInfo(resultSet.getString("info"));
//				idolEty.setImage(resultSet.getString("image"));
//				idolEty.setImage_ok(resultSet.getInt("image_ok"));
//				idolEty.setColor(resultSet.getString("color"));
//				list.add(idolEty);
//			}
//			conn.commit();
//			
//		} catch(Exception ex) { 
//			try {
//				conn.rollback();
//			} catch(SQLException se){}
//		} finally {
//			try {
//				if(resultSet != null) resultSet.close();
//				if(pstmt != null) pstmt.close();
//			} catch(Exception ex) {
//				//ex.printStackTrace();
//			}
//			connector.freeConn(conn);
//		}
//		return list;
//	}
//
//	public boolean insertIdol( IdolEty idolEty ) {
//		Connection conn = null ;
//		PreparedStatement pstmt = null ;
//		boolean isOk = false ;
//
//		try {
//			conn = connector.getConn() ;
//			
//			StringBuffer query = new StringBuffer() ;
//			
//			query.append(" insert into tblIdol(name, info, image, image_ok, color) values (?, ?, ?, ?, ?) ");
//			pstmt = conn.prepareStatement(query.toString()) ;
//			
//			int i = 1 ;
//			pstmt.setString(i++, idolEty.getName());
//			pstmt.setString(i++, idolEty.getInfo());
//			pstmt.setString(i++, idolEty.getImage());
//			pstmt.setInt(i++, idolEty.getImage_ok());
//			pstmt.setString(i++, idolEty.getColor());
//						
//			isOk = (pstmt.executeUpdate() == 1) ? true : false ;
//
//			if(isOk) { conn.commit() ; }
//			else { conn.rollback() ; }
//
//		} catch(Exception ex) { 
//			ex.printStackTrace() ;
//			try {
//				conn.rollback() ;
//			} catch(SQLException se){}
//		} finally {
//			try {
//				if(pstmt != null) pstmt.close() ;
//			} catch(Exception ex) {
//				ex.printStackTrace() ;
//			}
//			connector.freeConn(conn) ;
//		}				
//		return isOk ;
//	}
//
//	public boolean updateIdolImage( IdolEty idolEty ) {
//		Connection conn = null ;
//		PreparedStatement pstmt = null ;
//		boolean isOk = false ;
//
//		try {
//			conn = connector.getConn() ;
//			
//			StringBuffer query = new StringBuffer() ;
//			
//			query.append(" update tblIdol set image = ?, image_ok = ? where id = ? ");
//			pstmt = conn.prepareStatement(query.toString()) ;
//			
//			int i = 1 ;
//			pstmt.setString(i++, idolEty.getImage());
//			pstmt.setInt(i++, idolEty.getImage_ok());
//			pstmt.setLong(i++, idolEty.getId());
//						
//			isOk = (pstmt.executeUpdate() == 1) ? true : false ;
//
//			if(isOk) { conn.commit() ; }
//			else { conn.rollback() ; }
//
//		} catch(Exception ex) { 
//			ex.printStackTrace() ;
//			try {
//				conn.rollback() ;
//			} catch(SQLException se){}
//		} finally {
//			try {
//				if(pstmt != null) pstmt.close() ;
//			} catch(Exception ex) {
//				ex.printStackTrace() ;
//			}
//			connector.freeConn(conn) ;
//		}				
//		return isOk ;
//	}
//
//	public boolean updateIdol( IdolEty idolEty ) {
//		Connection conn = null ;
//		PreparedStatement pstmt = null ;
//		boolean isOk = false ;
//
//		try {
//			conn = connector.getConn() ;
//			
//			StringBuffer query = new StringBuffer() ;
//			
//			query.append(" update tblIdol set name = ?, info = ?, image_ok = ?, color = ? where id = ? ");
//			pstmt = conn.prepareStatement(query.toString()) ;
//			
//			int i = 1 ;
//			pstmt.setString(i++, idolEty.getName());
//			pstmt.setString(i++, idolEty.getInfo());
//			pstmt.setInt(i++, idolEty.getImage_ok());
//			pstmt.setString(i++, idolEty.getColor());
//			pstmt.setLong(i++, idolEty.getId());
//						
//			isOk = (pstmt.executeUpdate() == 1) ? true : false ;
//
//			if(isOk) { conn.commit() ; }
//			else { conn.rollback() ; }
//
//		} catch(Exception ex) { 
//			ex.printStackTrace() ;
//			try {
//				conn.rollback() ;
//			} catch(SQLException se){}
//		} finally {
//			try {
//				if(pstmt != null) pstmt.close() ;
//			} catch(Exception ex) {
//				ex.printStackTrace() ;
//			}
//			connector.freeConn(conn) ;
//		}				
//		return isOk ;
//	}
//
//	public boolean deleteIdol( int id ) {
//		Connection conn = null ;
//		PreparedStatement pstmt = null ;
//		boolean isOk = false ;
//
//		try {
//			conn = connector.getConn() ;
//			
//			StringBuffer query = new StringBuffer() ;
//			
//			query.append(" delete from tblIdol where id = ? ");
//			pstmt = conn.prepareStatement(query.toString()) ;
//			
//			int i = 1 ;
//			pstmt.setLong(i++, id);
//						
//			isOk = (pstmt.executeUpdate() == 1) ? true : false ;
//
//			if(isOk) { conn.commit() ; }
//			else { conn.rollback() ; }
//
//		} catch(Exception ex) { 
//			ex.printStackTrace() ;
//			try {
//				conn.rollback() ;
//			} catch(SQLException se){}
//		} finally {
//			try {
//				if(pstmt != null) pstmt.close() ;
//			} catch(Exception ex) {
//				ex.printStackTrace() ;
//			}
//			connector.freeConn(conn) ;
//		}				
//		return isOk ;
//	}
//	
//	public AnniversaryEty getAnniById( int anni_id ) {
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet resultSet = null;
//		AnniversaryEty anniversaryEty = new AnniversaryEty();
//
//		try {
//			conn = connector.getConn();
//
//			StringBuffer query = new StringBuffer();
//			
//			query.append(" select id, idol_id, image, image_ok, year, date, title, content, provided, is_annual from tblAnniversary where id = ? ");
//			
//			pstmt = conn.prepareStatement(query.toString());
//			
//			int i = 1 ;
//			pstmt.setLong(i++, anni_id);
//			
//			resultSet = pstmt.executeQuery();
//
//			if( resultSet.next() ) {
//				anniversaryEty.setId(resultSet.getLong("id"));
//				anniversaryEty.setIdol_id(resultSet.getLong("idol_id"));
//				anniversaryEty.setImage(resultSet.getString("image"));
//				anniversaryEty.setImage_ok(resultSet.getInt("image_ok"));
//				anniversaryEty.setYear(resultSet.getInt("year"));
//				anniversaryEty.setDate(resultSet.getInt("date"));
//				anniversaryEty.setTitle(resultSet.getString("title"));
//				anniversaryEty.setContent(resultSet.getString("content"));
//				anniversaryEty.setProvided(resultSet.getString("provided"));
//				anniversaryEty.setIs_annual(resultSet.getString("is_annual"));
//			}
//			conn.commit();
//			
//		} catch(Exception ex) { 
//			//ex.printStackTrace();
//			try {
//				conn.rollback();
//			} catch(SQLException se){}
//		} finally {
//			try {
//				if(resultSet != null) resultSet.close();
//				if(pstmt != null) pstmt.close();
//			} catch(Exception ex) {
//				//ex.printStackTrace();
//			}
//			connector.freeConn(conn);
//		}
//		return anniversaryEty;
//	}
//	
//	public Vector <AnniversaryEty> getAnniList() {
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet resultSet = null;
//		Vector <AnniversaryEty> list = new Vector <AnniversaryEty>();
//
//		try {
//			conn = connector.getConn();
//
//			StringBuffer query = new StringBuffer();
//			
//			query.append(" select tblAnniversary.id, tblIdol.info, tblAnniversary.idol_id, tblAnniversary.image, tblAnniversary.image_ok, tblAnniversary.year, tblAnniversary.date, tblAnniversary.title, tblAnniversary.content, tblAnniversary.provided, tblAnniversary.is_annual from tblAnniversary join tblIdol on tblIdol.id = tblAnniversary.idol_id where tblAnniversary.is_del = 0 order by tblAnniversary.id desc ");
//			
//			pstmt = conn.prepareStatement(query.toString());
//			
//			resultSet = pstmt.executeQuery();
//
//			while( resultSet.next() ) {
//				AnniversaryEty anniversaryEty = new AnniversaryEty();
//				anniversaryEty.setId(resultSet.getLong("id"));
//				anniversaryEty.setColor(resultSet.getString("info"));
//				anniversaryEty.setIdol_id(resultSet.getLong("idol_id"));
//				anniversaryEty.setImage(resultSet.getString("image"));
//				anniversaryEty.setImage_ok(resultSet.getInt("image_ok"));
//				anniversaryEty.setYear(resultSet.getInt("year"));
//				anniversaryEty.setDate(resultSet.getInt("date"));
//				anniversaryEty.setTitle(resultSet.getString("title"));
//				anniversaryEty.setContent(resultSet.getString("content"));
//				anniversaryEty.setProvided(resultSet.getString("provided"));
//				anniversaryEty.setIs_annual(resultSet.getString("is_annual"));
//				list.add(anniversaryEty);
//			}
//			conn.commit();
//			
//		} catch(Exception ex) { 
//			try {
//				conn.rollback();
//			} catch(SQLException se){}
//		} finally {
//			try {
//				if(resultSet != null) resultSet.close();
//				if(pstmt != null) pstmt.close();
//			} catch(Exception ex) {
//				//ex.printStackTrace();
//			}
//			connector.freeConn(conn);
//		}
//		return list;
//	}
//
//	public boolean insertAnni( AnniversaryEty anniversaryEty ) {
//		Connection conn = null ;
//		PreparedStatement pstmt = null ;
//		boolean isOk = false ;
//
//		try {
//			conn = connector.getConn() ;
//			
//			StringBuffer query = new StringBuffer() ;
//			
//			query.append(" insert into tblAnniversary(idol_id, image, image_ok, year, date, title, content, provided, is_annual, is_del) values (?, ?, ?, ?, ?, ?, ?, ?, ?, 0) ");
//			pstmt = conn.prepareStatement(query.toString()) ;
//			
//			int i = 1 ;
//			pstmt.setInt(i++, (int)anniversaryEty.getIdol_id());
//			pstmt.setString(i++, anniversaryEty.getImage());
//			pstmt.setInt(i++, anniversaryEty.getImage_ok());
//			pstmt.setInt(i++, anniversaryEty.getYear());
//			pstmt.setInt(i++, anniversaryEty.getDate());
//			pstmt.setString(i++, anniversaryEty.getTitle());
//			pstmt.setString(i++, anniversaryEty.getContent());
//			pstmt.setString(i++, anniversaryEty.getProvided());
//			pstmt.setString(i++, anniversaryEty.getIs_annual());
//						
//			isOk = (pstmt.executeUpdate() == 1) ? true : false ;
//
//			if(isOk) { conn.commit() ; }
//			else { conn.rollback() ; }
//
//		} catch(Exception ex) { 
//			ex.printStackTrace() ;
//			try {
//				conn.rollback() ;
//			} catch(SQLException se){}
//		} finally {
//			try {
//				if(pstmt != null) pstmt.close() ;
//			} catch(Exception ex) {
//				ex.printStackTrace() ;
//			}
//			connector.freeConn(conn) ;
//		}				
//		return isOk ;
//	}
//
//	public boolean updateAnniImage( AnniversaryEty anniversaryEty ) {
//		Connection conn = null ;
//		PreparedStatement pstmt = null ;
//		boolean isOk = false ;
//
//		try {
//			conn = connector.getConn() ;
//			
//			StringBuffer query = new StringBuffer() ;
//			
//			query.append(" update tblAnniversary set image = ?, image_ok = ? where id = ? ");
//			pstmt = conn.prepareStatement(query.toString()) ;
//			
//			int i = 1 ;
//			pstmt.setString(i++, anniversaryEty.getImage());
//			pstmt.setInt(i++, anniversaryEty.getImage_ok());
//			pstmt.setLong(i++, anniversaryEty.getId());
//						
//			isOk = (pstmt.executeUpdate() == 1) ? true : false ;
//
//			if(isOk) { conn.commit() ; }
//			else { conn.rollback() ; }
//
//		} catch(Exception ex) { 
//			ex.printStackTrace() ;
//			try {
//				conn.rollback() ;
//			} catch(SQLException se){}
//		} finally {
//			try {
//				if(pstmt != null) pstmt.close() ;
//			} catch(Exception ex) {
//				ex.printStackTrace() ;
//			}
//			connector.freeConn(conn) ;
//		}				
//		return isOk ;
//	}
//
//	public boolean updateAnni( AnniversaryEty anniversaryEty ) {
//		Connection conn = null ;
//		PreparedStatement pstmt = null ;
//		boolean isOk = false ;
//
//		try {
//			conn = connector.getConn() ;
//			
//			StringBuffer query = new StringBuffer() ;
//			
//			query.append(" update tblAnniversary set idol_id = ?, year = ?, date = ?, title = ?, content = ?, provided = ?, is_annual = ?, image_ok = ? where id = ? ");
//			pstmt = conn.prepareStatement(query.toString()) ;
//			
//			int i = 1 ;
//			pstmt.setLong(i++, anniversaryEty.getIdol_id());
//			pstmt.setInt(i++, anniversaryEty.getYear());
//			pstmt.setInt(i++, anniversaryEty.getDate());
//			pstmt.setString(i++, anniversaryEty.getTitle());
//			pstmt.setString(i++, anniversaryEty.getContent());
//			pstmt.setString(i++, anniversaryEty.getProvided());
//			pstmt.setString(i++, anniversaryEty.getIs_annual());
//			pstmt.setInt(i++, anniversaryEty.getImage_ok());
//			pstmt.setLong(i++, anniversaryEty.getId());
//						
//			isOk = (pstmt.executeUpdate() == 1) ? true : false ;
//
//			if(isOk) { conn.commit() ; }
//			else { conn.rollback() ; }
//
//		} catch(Exception ex) { 
//			ex.printStackTrace() ;
//			try {
//				conn.rollback() ;
//			} catch(SQLException se){}
//		} finally {
//			try {
//				if(pstmt != null) pstmt.close() ;
//			} catch(Exception ex) {
//				ex.printStackTrace() ;
//			}
//			connector.freeConn(conn) ;
//		}				
//		return isOk ;
//	}
//
//	public boolean deleteAnni( int id ) {
//		Connection conn = null ;
//		PreparedStatement pstmt = null ;
//		boolean isOk = false ;
//
//		try {
//			conn = connector.getConn() ;
//			
//			StringBuffer query = new StringBuffer() ;
//			
//			query.append(" delete from tblAnniversary where id = ? ");
//			pstmt = conn.prepareStatement(query.toString()) ;
//			
//			int i = 1 ;
//			pstmt.setLong(i++, id);
//						
//			isOk = (pstmt.executeUpdate() == 1) ? true : false ;
//
//			if(isOk) { conn.commit() ; }
//			else { conn.rollback() ; }
//
//		} catch(Exception ex) { 
//			ex.printStackTrace() ;
//			try {
//				conn.rollback() ;
//			} catch(SQLException se){}
//		} finally {
//			try {
//				if(pstmt != null) pstmt.close() ;
//			} catch(Exception ex) {
//				ex.printStackTrace() ;
//			}
//			connector.freeConn(conn) ;
//		}				
//		return isOk ;
//	}
//	
//	public InfoEty getInfoById( int info_id ) {
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet resultSet = null;
//		InfoEty infoEty = new InfoEty();
//
//		try {
//			conn = connector.getConn();
//
//			StringBuffer query = new StringBuffer();
//			
//			query.append(" select id, title, subhead, content, date_format(l_date, '%y. %m. %d') as l_date, is_show from tblInfo where id = ? ");
//			
//			pstmt = conn.prepareStatement(query.toString());
//			
//			int i = 1 ;
//			pstmt.setLong(i++, info_id);
//			
//			resultSet = pstmt.executeQuery();
//
//			if( resultSet.next() ) {
//				infoEty.setId(resultSet.getLong("id"));
//				infoEty.setTitle(resultSet.getString("title"));
//				infoEty.setSubhead(resultSet.getString("subhead"));
//				infoEty.setContent(resultSet.getString("content"));
//				infoEty.setDate(resultSet.getString("l_date"));
//				infoEty.setIs_show(resultSet.getInt("is_show"));
//			}
//			conn.commit();
//			
//		} catch(Exception ex) { 
//			//ex.printStackTrace();
//			try {
//				conn.rollback();
//			} catch(SQLException se){}
//		} finally {
//			try {
//				if(resultSet != null) resultSet.close();
//				if(pstmt != null) pstmt.close();
//			} catch(Exception ex) {
//				//ex.printStackTrace();
//			}
//			connector.freeConn(conn);
//		}
//		return infoEty;
//	}
//	
//	public Vector <InfoEty> getInfoList() {
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet resultSet = null;
//		Vector <InfoEty> list = new Vector <InfoEty>();
//
//		try {
//			conn = connector.getConn();
//
//			StringBuffer query = new StringBuffer();
//			
//			query.append(" select id, title, subhead, content, date_format(l_date, '%y. %m. %d') as l_date, is_show from tblInfo order by l_date desc ");
//			
//			pstmt = conn.prepareStatement(query.toString());
//			
//			resultSet = pstmt.executeQuery();
//
//			while( resultSet.next() ) {
//				InfoEty infoEty = new InfoEty();
//				infoEty.setId(resultSet.getLong("id"));
//				infoEty.setTitle(resultSet.getString("title"));
//				infoEty.setSubhead(resultSet.getString("subhead"));
//				infoEty.setContent(resultSet.getString("content"));
//				infoEty.setDate(resultSet.getString("l_date"));
//				infoEty.setIs_show(resultSet.getInt("is_show"));
//				list.add(infoEty);
//			}
//			conn.commit();
//			
//		} catch(Exception ex) { 
//			try {
//				conn.rollback();
//			} catch(SQLException se){}
//		} finally {
//			try {
//				if(resultSet != null) resultSet.close();
//				if(pstmt != null) pstmt.close();
//			} catch(Exception ex) {
//				//ex.printStackTrace();
//			}
//			connector.freeConn(conn);
//		}
//		return list;
//	}
//
//	public boolean insertInfo( InfoEty infoEty ) {
//		Connection conn = null ;
//		PreparedStatement pstmt = null ;
//		boolean isOk = false ;
//
//		try {
//			conn = connector.getConn() ;
//			
//			StringBuffer query = new StringBuffer() ;
//			
//			query.append(" insert into tblInfo(title, subhead, content, l_date, is_show) values (?, ?, ?, now(), 0) ");
//			pstmt = conn.prepareStatement(query.toString()) ;
//			
//			int i = 1 ;
//			pstmt.setString(i++, infoEty.getTitle());
//			pstmt.setString(i++, infoEty.getSubhead());
//			pstmt.setString(i++, infoEty.getContent());
//						
//			isOk = (pstmt.executeUpdate() == 1) ? true : false ;
//
//			if(isOk) { conn.commit() ; }
//			else { conn.rollback() ; }
//
//		} catch(Exception ex) { 
//			ex.printStackTrace() ;
//			try {
//				conn.rollback() ;
//			} catch(SQLException se){}
//		} finally {
//			try {
//				if(pstmt != null) pstmt.close() ;
//			} catch(Exception ex) {
//				ex.printStackTrace() ;
//			}
//			connector.freeConn(conn) ;
//		}				
//		return isOk ;
//	}
//
//	public boolean updateInfo( InfoEty infoEty ) {
//		Connection conn = null ;
//		PreparedStatement pstmt = null ;
//		boolean isOk = false ;
//
//		try {
//			conn = connector.getConn() ;
//			
//			StringBuffer query = new StringBuffer() ;
//			
//			query.append(" update tblInfo set title = ?, subhead = ?, content = ? where id = ? ");
//			pstmt = conn.prepareStatement(query.toString()) ;
//			
//			int i = 1 ;
//			pstmt.setString(i++, infoEty.getTitle());
//			pstmt.setString(i++, infoEty.getSubhead());
//			pstmt.setString(i++, infoEty.getContent());
//			pstmt.setLong(i++, infoEty.getId());
//						
//			isOk = (pstmt.executeUpdate() == 1) ? true : false ;
//
//			if(isOk) { conn.commit() ; }
//			else { conn.rollback() ; }
//
//		} catch(Exception ex) { 
//			ex.printStackTrace() ;
//			try {
//				conn.rollback() ;
//			} catch(SQLException se){}
//		} finally {
//			try {
//				if(pstmt != null) pstmt.close() ;
//			} catch(Exception ex) {
//				ex.printStackTrace() ;
//			}
//			connector.freeConn(conn) ;
//		}				
//		return isOk ;
//	}
//
//	public boolean deleteInfo( int id ) {
//		Connection conn = null ;
//		PreparedStatement pstmt = null ;
//		boolean isOk = false ;
//
//		try {
//			conn = connector.getConn() ;
//			
//			StringBuffer query = new StringBuffer() ;
//			
//			query.append(" update tblInfo set is_show = 1 where id = ? ");
//			pstmt = conn.prepareStatement(query.toString()) ;
//			
//			int i = 1 ;
//			pstmt.setLong(i++, id);
//						
//			isOk = (pstmt.executeUpdate() == 1) ? true : false ;
//
//			if(isOk) { conn.commit() ; }
//			else { conn.rollback() ; }
//
//		} catch(Exception ex) { 
//			ex.printStackTrace() ;
//			try {
//				conn.rollback() ;
//			} catch(SQLException se){}
//		} finally {
//			try {
//				if(pstmt != null) pstmt.close() ;
//			} catch(Exception ex) {
//				ex.printStackTrace() ;
//			}
//			connector.freeConn(conn) ;
//		}				
//		return isOk ;
//	}
//	
//	
//	public boolean insertUser( memberEty memberety ) {
//		Connection conn = null ;
//		PreparedStatement pstmt = null ;
//		boolean isOk = false ;
//
//		try {
//			conn = connector.getConn() ;
//			
//			StringBuffer query = new StringBuffer() ;
//			
//			query.append(" insert into member(userid,password,name,email,homepage,comment,birth,picture,level,reg_date) values (?, password(?), ?, ?, ?, ?, ?, ?, 9, now()) ");
//			pstmt = conn.prepareStatement(query.toString()) ;
//			
//			int i = 1 ;
//			pstmt.setString(i++, memberety.getUserid());
//			pstmt.setString(i++, memberety.getPassword());
//			pstmt.setString(i++, memberety.getName());
//			pstmt.setString(i++, memberety.getEmail());
//			pstmt.setString(i++, memberety.getHomepage());
//			pstmt.setString(i++, memberety.getComment());
//			pstmt.setInt(i++, memberety.getBirth());
//			pstmt.setString(i++, memberety.getPicture());
//						
//			isOk = (pstmt.executeUpdate() == 1) ? true : false ;
//
//			if(isOk) { conn.commit() ; }
//			else { conn.rollback() ; }
//
//		} catch(Exception ex) { 
//			ex.printStackTrace() ;
//			try {
//				conn.rollback() ;
//			} catch(SQLException se){}
//		} finally {
//			try {
//				if(pstmt != null) pstmt.close() ;
//			} catch(Exception ex) {
//				ex.printStackTrace() ;
//			}
//			connector.freeConn(conn) ;
//		}				
//		return isOk ;
//	}
//	
//	public Vector <memberEty> getMemberlist() {
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet resultSet = null;
//		Vector <memberEty> list = new Vector <memberEty>();
//
//		try {
//			conn = connector.getConn();
//
//			StringBuffer query = new StringBuffer();
//			
//			query.append(" select userid, password, name, level, email, homepage, comment, birth, picture from member ");
//			
//			
//			pstmt = conn.prepareStatement(query.toString());
//			resultSet = pstmt.executeQuery();
//
//			while( resultSet.next() ) {
//				memberEty mbEty = new memberEty();
//				mbEty.setUserid(resultSet.getString("userid"));
//				mbEty.setPassword(resultSet.getString("password"));
//				mbEty.setName(resultSet.getString("name"));
//				mbEty.setLevel(resultSet.getInt("level"));
//				mbEty.setEmail(resultSet.getString("email"));
//				mbEty.setHomepage(resultSet.getString("homepage"));
//				mbEty.setComment(resultSet.getString("comment"));
//				mbEty.setBirth(resultSet.getInt("birth"));
//				mbEty.setPicture(resultSet.getString("picture"));
//				list.add(mbEty);
//			}
//			conn.commit();
//			
//		} catch(Exception ex) { 
//			//ex.printStackTrace();
//			try {
//				conn.rollback();
//			} catch(SQLException se){}
//		} finally {
//			try {
//				if(resultSet != null) resultSet.close();
//				if(pstmt != null) pstmt.close();
//			} catch(Exception ex) {
//				//ex.printStackTrace();
//			}
//			connector.freeConn(conn);
//		}
//		return list;
//	}
//	
//	public aboutEty getAboutEty() {
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet resultSet = null;
//		aboutEty tEty = new aboutEty();
//
//		try {
//			conn = connector.getConn();
//
//			StringBuffer query = new StringBuffer();
//			
//			query.append(" select intro, professor_image, professor_about from about ");
//			
//			
//			pstmt = conn.prepareStatement(query.toString());
//			resultSet = pstmt.executeQuery();
//
//			if( resultSet.next() ) {
//				tEty.setIntro(resultSet.getString("intro"));
//				tEty.setProfessor_image(resultSet.getString("professor_image"));
//				tEty.setProfessor_about(resultSet.getString("professor_about"));
//			}
//			conn.commit();
//			
//		} catch(Exception ex) { 
//			//ex.printStackTrace();
//			try {
//				conn.rollback();
//			} catch(SQLException se){}
//		} finally {
//			try {
//				if(resultSet != null) resultSet.close();
//				if(pstmt != null) pstmt.close();
//			} catch(Exception ex) {
//				//ex.printStackTrace();
//			}
//			connector.freeConn(conn);
//		}
//		return tEty;
//	}
//	
//	public boolean updateIntro( String textarea ) {
//		Connection conn = null ;
//		PreparedStatement pstmt = null ;
//		boolean isOk = false ;
//		
//		try {
//			conn = connector.getConn() ;
//			
//			StringBuffer query = new StringBuffer() ;
//
//			query.append(" update about set intro=?");
//			pstmt = conn.prepareStatement(query.toString()) ;
//			
//			int i = 1 ;
//			pstmt.setString(i++, textarea) ;
//						
//			isOk = (pstmt.executeUpdate() == 1) ? true : false ;
//
//			if(isOk) { conn.commit() ; }
//			else { conn.rollback() ; }
//
//		} catch(Exception ex) { 
//			ex.printStackTrace() ;
//			try {
//				conn.rollback() ;
//			} catch(SQLException se){}
//		} finally {
//			try {
//				if(pstmt != null) pstmt.close() ;
//			} catch(Exception ex) {
//				ex.printStackTrace() ;
//			}
//			connector.freeConn(conn) ;
//		}				
//		return isOk ;
//	}
//	
//	public boolean updateProfessor( String textarea, String imgName ) {
//		Connection conn = null ;
//		PreparedStatement pstmt = null ;
//		boolean isOk = false ;
//		
//		try {
//			conn = connector.getConn() ;
//			
//			StringBuffer query = new StringBuffer() ;
//
//			query.append(" update about set professor_about=?, professor_image=? ");
//			pstmt = conn.prepareStatement(query.toString()) ;
//			
//			int i = 1 ;
//			pstmt.setString(i++, textarea) ;
//			pstmt.setString(i++, imgName) ;
//						
//			isOk = (pstmt.executeUpdate() == 1) ? true : false ;
//
//			if(isOk) { conn.commit() ; }
//			else { conn.rollback() ; }
//
//		} catch(Exception ex) { 
//			ex.printStackTrace() ;
//			try {
//				conn.rollback() ;
//			} catch(SQLException se){}
//		} finally {
//			try {
//				if(pstmt != null) pstmt.close() ;
//			} catch(Exception ex) {
//				ex.printStackTrace() ;
//			}
//			connector.freeConn(conn) ;
//		}				
//		return isOk ;
//	}
//	
//	public boolean deleteProfessor() {
//		Connection conn = null ;
//		PreparedStatement pstmt = null ;
//		ResultSet resultSet = null;
//		boolean isOk = false ;
//		
//		try {
//			String file_name = "";
//			conn = connector.getConn() ;
//			
//			StringBuffer query = new StringBuffer() ;
//			
//			query.append(" select professor_image from about ");
//			pstmt = conn.prepareStatement(query.toString()) ;
//
//			resultSet = pstmt.executeQuery() ;
//				
//			if( resultSet.next() ) {
//				file_name = resultSet.getString("professor_image");
//				isOk = true;
//			}
//				
//			query.delete(0, query.length()) ;
//			
//			String	savePath	= "C://Documents and Settings//Kim//workspace//dev_caps//WebContent//main//about//img//";
//			File	file		= new File( savePath + file_name );
//			if( file.exists() ) { file.delete(); }
//			
//			query.append(" update about set professor_image=null ");
//			pstmt = conn.prepareStatement(query.toString()) ;
//						
//			isOk = isOk && (pstmt.executeUpdate() == 1) ? true : false ;
//
//			if(isOk) { conn.commit() ; }
//			else { conn.rollback() ; }
//
//		} catch(Exception ex) { 
//			ex.printStackTrace() ;
//			try {
//				conn.rollback() ;
//			} catch(SQLException se){}
//		} finally {
//			try {
//				if(pstmt != null) pstmt.close() ;
//			} catch(Exception ex) {
//				ex.printStackTrace() ;
//			}
//			connector.freeConn(conn) ;
//		}				
//		return isOk ;
//	}
//	
//	/*
//	 * 	about board
//	 */
//	public boolean insertBoard( String btype, String bname ) {
//		Connection conn = null ;
//		PreparedStatement pstmt = null ;
//		boolean isOk = false ;
//
//		try {
//			conn = connector.getConn() ;
//			
//			StringBuffer query = new StringBuffer() ;
//			
//			query.append(" insert into board_list values( 0, ?, ?, now() ) ");
//			pstmt = conn.prepareStatement(query.toString()) ;
//			
//			int i = 1 ;
//			pstmt.setString(i++, btype);
//			pstmt.setString(i++, bname);
//						
//			isOk = (pstmt.executeUpdate() == 1) ? true : false ;
//
//			if(isOk) { conn.commit() ; }
//			else { conn.rollback() ; }
//
//		} catch(Exception ex) { 
//			ex.printStackTrace() ;
//			try {
//				conn.rollback() ;
//			} catch(SQLException se){}
//		} finally {
//			try {
//				if(pstmt != null) pstmt.close() ;
//			} catch(Exception ex) {
//				ex.printStackTrace() ;
//			}
//			connector.freeConn(conn) ;
//		}				
//		return isOk ;
//	}
}