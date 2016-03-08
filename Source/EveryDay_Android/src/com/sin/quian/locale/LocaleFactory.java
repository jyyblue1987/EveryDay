package com.sin.quian.locale;

public class LocaleFactory {
	static Locale locale = null;
	static int		g_nLanguage = 0;
	public static Locale selectLocale(int lang) {
		synchronized (Locale.class) {
			if( lang == 0 )
				locale = new Locale();
			else
				locale = new Chinese();
		}		
		
		locale.changeLocale();
		g_nLanguage = 0;
		
		return locale;
	}
	
	public static Locale getLocale()
	{
		return locale;
	}
	
	public static int getLanguage()
	{
		return g_nLanguage;
	}
}
