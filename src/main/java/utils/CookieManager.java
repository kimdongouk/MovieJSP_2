package utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieManager {
	// 쿠키 set 
	public static void makeCookie(HttpServletResponse response, String cName, 
			String cValue, int cTime) {
		
		Cookie cookie = new Cookie("cNmae", "cValue");
		cookie.setPath("/");
		cookie.setMaxAge(cTime);
		response.addCookie(cookie);
	}
	
	// 쿠키 get
	public static String readCookie(HttpServletRequest request, String cName) {
		String cValue = "";
		
		Cookie[] cookies = request.getCookies();
	    if(cookies != null){
	    	for(Cookie c : cookies){
	    		String cookieName = c.getName();
	    		String cookieValue = c.getValue();
	    		if(cookieName.equals(cName)) {
	    			cValue = cookieValue;
	    		}
	    	}
	    }
	    return cValue;
	}
	
	// 쿠키 del
	public static void deleteCookie(HttpServletResponse response, String cName) {
		makeCookie(response, cName, "", 0);
	}
}
