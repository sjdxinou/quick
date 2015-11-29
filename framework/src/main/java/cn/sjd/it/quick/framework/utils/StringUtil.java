package cn.sjd.it.quick.framework.utils;

public class StringUtil {
	public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }
	
	public static boolean isNotEmpty(String str) {
        return str != null && str.trim().length() > 0;
    }
	
	public static void main(String[] args) {
		System.err.println(StringUtil.isNotEmpty(null));
	}
}
