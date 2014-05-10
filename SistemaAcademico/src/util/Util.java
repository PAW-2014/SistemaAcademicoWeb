package util;

import java.util.Collection;

public class Util {
	
	/**
	 * Because it's a static class
	 */
	protected Util(){
	}
	
	public static boolean isNotValid(Collection<? extends Object> list){
		return !isValid(list);
	}
	
	public static boolean isValid(Collection<? extends Object> list){
		return (!list.isEmpty());
	}
	
	public static boolean isNotValid(String string){
		return !isValid(string);
	}

	public static boolean isValid(String string){
		return (string != null && !"".equals(string));
	}
	
}
