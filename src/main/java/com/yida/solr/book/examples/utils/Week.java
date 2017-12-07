package com.yida.solr.book.examples.utils;

import java.util.Calendar;

/**
 * 星期[枚举]
 * @author Lanxiaowei
 *
 */
public enum Week {
	SUNDAY {             //星期日
		int getValue(){
			return WEEK_SUNDAY;
		}
		String getChinese(){
			return "星期日";
		}
	},
	MONDAY {            //星期一
		int getValue(){
			return WEEK_MONDAY;
		}
		String getChinese(){
			return "星期一";
		}
	},
	TUESDAY {            //星期二
		int getValue(){
			return WEEK_TUESDAY;
		}
		String getChinese(){
			return "星期二";
		}
	},
	WEDNESDAY {            //星期三
		int getValue(){
			return WEEK_WEDNESDAY;
		}
		String getChinese(){
			return "星期三";
		}
	},
	THURSDAY {            //星期四
		int getValue(){
			return WEEK_THURSDAY;
		}
		String getChinese(){
			return "星期四";
		}
	},
	FRIDAY {            //星期五
		int getValue(){
			return WEEK_FRIDAY;
		}
		String getChinese(){
			return "星期五";
		}
	},
	SATURDAY {            //星期六
		int getValue(){
			return WEEK_SATURDAY;
		}
		String getChinese(){
			return "星期六";
		}
	};
	
	/**
	 * 
	 *方法摘要：获取星期的数字标识，1表示星期日，7表示星期六
	 *@return int
	 */
	abstract int getValue();
	
	/**
	 * 
	 *方法摘要：获取星期的中文名称
	 *@return String
	 */
	abstract String getChinese();
	
	/**星期日*/
	public static final int WEEK_SUNDAY = Calendar.SUNDAY;
	/**星期一*/
	public static final int WEEK_MONDAY = Calendar.MONDAY;
	/**星期二*/
	public static final int WEEK_TUESDAY = Calendar.TUESDAY;
	/**星期三*/
	public static final int WEEK_WEDNESDAY = Calendar.WEDNESDAY;
	/**星期四*/
	public static final int WEEK_THURSDAY = Calendar.THURSDAY;
	/**星期五*/
	public static final int WEEK_FRIDAY = Calendar.FRIDAY;
	/**星期六*/
	public static final int WEEK_SATURDAY = Calendar.SATURDAY;
}
