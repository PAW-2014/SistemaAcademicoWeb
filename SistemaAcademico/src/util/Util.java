package util;

import java.util.Collection;

public class Util {
	
	/**
	 * Because it's a static class
	 */
	protected Util(){
	}
	
	public static boolean startsWithSpace(String string){
		if(isNotValid(string))
			return false;
		
		return (string.charAt(0) == ' ');
	}
	
	public static boolean isNotValid(Collection<? extends Object> list){
		return !isValid(list);
	}
	
	public static boolean isValid(Collection<? extends Object> list){
		return (!list.isEmpty());
	}
	
	public static boolean isNotValid(Object object){
		return !isValid(object);
	}

	public static boolean isValid(Object object){
		return (object != null && !"".equals(object.toString()));
	}
	
}
