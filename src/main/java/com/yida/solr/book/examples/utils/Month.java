package com.yida.solr.book.examples.utils;

import java.util.Calendar;

/**
 * 月份[枚举]
 * @author Lanxiaowei
 *
 */
public enum Month {
	JANUARY {                 //一月
		public int getValue(){
			return MONTH_JANUARY;
		}
		public String getChinese(){
			return "一月";
		}
		public String getEnglish(){
			return "January";
		}
	},
	FEBRUARY {                 //二月
		public int getValue(){
			return MONTH_FEBRUARY;
		}
		public String getChinese(){
			return "二月";
		}
		public String getEnglish(){
			return "February";
		}
	},
	MARCH {                 //三月
		public int getValue(){
			return MONTH_MARCH;
		}
		public String getChinese(){
			return "三月";
		}
		public String getEnglish(){
			return "MARCH";
		}
	},
	APRIL {                 //四月
		public int getValue(){
			return MONTH_APRIL;
		}
		public String getChinese(){
			return "四月";
		}
		public String getEnglish(){
			return "April";
		}
	},
	MAY {                 //五月
		public int getValue(){
			return MONTH_MAY;
		}
		public String getChinese(){
			return "五月";
		}
		public String getEnglish(){
			return "May";
		}
	},
	JUNE {                 //六月
		public int getValue(){
			return MONTH_JUNE;
		}
		public String getChinese(){
			return "六月";
		}
		public String getEnglish(){
			return "June";
		}
	},
	JULY {                 //七月
		public int getValue(){
			return MONTH_JULY;
		}
		public String getChinese(){
			return "七月";
		}
		public String getEnglish(){
			return "July";
		}
	},
	AUGUST {                 //八月
		public int getValue(){
			return MONTH_AUGUST;
		}
		public String getChinese(){
			return "八月";
		}
		public String getEnglish(){
			return "August";
		}
	},
	SEPTEMBER {                 //九月
		public int getValue(){
			return MONTH_SEPTEMBER;
		}
		public String getChinese(){
			return "九月";
		}
		public String getEnglish(){
			return "September";
		}
	},
	OCTOBER {                 //十月
		public int getValue(){
			return MONTH_OCTOBER;
		}
		public String getChinese(){
			return "十月";
		}
		public String getEnglish(){
			return "October";
		}
	},
	NOVEMBER {                 //十一月
		public int getValue(){
			return MONTH_NOVEMBER;
		}
		public String getChinese(){
			return "十一月";
		}
		public String getEnglish(){
			return "November";
		}
	},
	DECEMBER {                 //十二月
		public int getValue(){
			return MONTH_DECEMBER;
		}
		public String getChinese(){
			return "十二月";
		}
		public String getEnglish(){
			return "December";
		}
	};
	
	/**
	 * 
	 *方法摘要：获取月份的数字标识，1表示一月，12表示十二月
	 *@return int
	 */
	public abstract int getValue();
	
	/**
	 * 
	 *方法摘要：获取月份的中文名称
	 *@return String
	 */
	public abstract String getChinese();
	/**
	 * 
	 *方法摘要：获取月份的英文名称
	 *@return String
	 */
	public abstract String getEnglish();
	
	/**一月*/
	public static final int MONTH_JANUARY = Calendar.JANUARY;
	/**二月*/
	public static final int MONTH_FEBRUARY = Calendar.FEBRUARY;
	/**三月*/
	public static final int MONTH_MARCH = Calendar.MARCH;
	/**四月*/
	public static final int MONTH_APRIL = Calendar.APRIL;
	/**五月*/
	public static final int MONTH_MAY = Calendar.MAY;
	/**六月*/
	public static final int MONTH_JUNE = Calendar.JUNE;
	/**七月*/
	public static final int MONTH_JULY = Calendar.JULY;
	/**八月*/
	public static final int MONTH_AUGUST = Calendar.AUGUST;
	/**九月*/
	public static final int MONTH_SEPTEMBER = Calendar.SEPTEMBER;
	/**十月*/
	public static final int MONTH_OCTOBER = Calendar.OCTOBER;
	/**十一月*/
	public static final int MONTH_NOVEMBER = Calendar.NOVEMBER;
	/**十二月*/
	public static final int MONTH_DECEMBER = Calendar.DECEMBER;
}
