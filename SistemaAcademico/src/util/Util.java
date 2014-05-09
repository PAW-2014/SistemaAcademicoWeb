package util;

import java.util.Collection;

public class Util {
	
	public static boolean isValid(Collection<? extends Object> list){
		return (!list.isEmpty());
	}

	public static boolean isValid(String string){
		return (string != null && !"".equals(string));
	}
	
}
