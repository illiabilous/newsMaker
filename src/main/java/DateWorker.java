import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateWorker {

static String currentDateWithTime() {
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(new Date());
	}
	
	static String currentDateGrinvichTime() throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		return dateFormat.format(new Date());
	}
	
	static String getCurrentDateWithoutTimeForPublishedHeaders() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM");
		return dateFormat.format(new Date());
	}
	
	static String yesterdayCurrentDateWithoutTimeForPublishedHeaders(String dateToday) {
		String yesterdayDay=dateToday.substring(0,2);
		int yesterdayD=Integer.parseInt(yesterdayDay)-1;
		String yesterdayMonth=dateToday.substring(2,5);
		String yesterdayDate= yesterdayD + yesterdayMonth;
		
		if(Integer.toString(yesterdayD).length()==1){
		yesterdayDate= "0"+yesterdayD + yesterdayMonth;
		}
		return yesterdayDate;
		}
		
		static int getCurrentWeekend() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.DAY_OF_WEEK);
		
	}
	
	public static void main(String []args){
		System.out.println(getCurrentWeekend());
	
	}
}