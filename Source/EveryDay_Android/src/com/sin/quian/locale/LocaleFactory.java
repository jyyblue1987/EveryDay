package com.sin.quian.locale;

public class LocaleFactory {
	static Locale locale = null;
	public static Locale selectLocale(int lang) {
		synchronized (Locale.class) {
			if( lang == 0 )
				locale = new Locale();
			else
				locale = new Chinese();
		}		
		
		locale.changeLocale();
		
		return locale;
	}
	
	public static Locale getLocale()
	{
		return locale;
	}
}
