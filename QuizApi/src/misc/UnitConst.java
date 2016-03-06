package misc;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 *	constant class
 */
public class UnitConst {
	public static final DateFormat DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:00");
	public static final DateFormat DATE_FORMAT_JSON = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss Z");


	public static final String INIT_DATETIME		= "2011-09-01 00:00:00";

	public static final float INIT_PRICE_INDEX		= 100;
	public static final int MAX_OPTION				= 2;
	public static final int INIT_PRICE				= 5000;
}
