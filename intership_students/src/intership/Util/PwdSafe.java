package intership.Util;

	import java.util.regex.Pattern;

	public class PwdSafe {
	    public static int isPasswordStrong(String password) {
	       int hasNumeric = hasNumeric(password)?1:0;
	       int  hasUpperCase = hasUpperCase(password)?1:0;
	       int hasLowerCase = hasLowerCase(password)?1:0;
	       int hasSpecialChar = hasSpecialChar(password)?1:0;
	       return hasNumeric + hasUpperCase + hasLowerCase + hasSpecialChar;
	    }

	    public static boolean hasNumeric(String password) {
	        return Pattern.compile("\\d").matcher(password).find();
	    }

	    public static boolean hasUpperCase(String password) {
	        return Pattern.compile("[A-Z]").matcher(password).find();
	    }

	    public static boolean hasLowerCase(String password) {
	        return Pattern.compile("[a-z]").matcher(password).find();
	    }

	    public static boolean hasSpecialChar(String password) {
	    	return Pattern.compile("[`~!@#$%^&*()_\\-+=<>?:\"{}|,.\\/;'\\\\\\u00B7~！@#￥%……&*（）——\\-+={}|《》？：“”\\u3010-\\u3011、；‘’，。]").matcher(password).find();
	    }
	}
	

