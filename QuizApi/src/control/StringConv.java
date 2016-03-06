package control;

public class StringConv {
	public static String getDefixedString(String str) {
		try {
			if(str != null)
				str = new String(str.getBytes("utf-8"), "iso-8859-1") ;
			else 
				str = "" ;
		} catch(Exception ex) {
			ex.printStackTrace() ;
		}
		
		return str ;
	}
	
	public static String getFixedString(String str) {
		try {
			if(str != null)
				str = new String(str.getBytes("iso-8859-1"), "utf-8") ;
			else 
				str = "" ;
		} catch(Exception ex) {
			ex.printStackTrace() ;
		}
		
		return str ;
	}
	
	public static String fixedHTML(String str) {
		String [] origin = {"<", ">"} ;
		String [] change = {"&lt;", "&gt;"} ;
		
		for( int i = 0 ; i < origin.length ; i++ ) {
			str = str.replaceAll(origin[i], change[i]) ;
		}
		return str ;
	}
}
