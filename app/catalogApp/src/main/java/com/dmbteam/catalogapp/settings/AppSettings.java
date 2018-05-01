package com.dmbteam.catalogapp.settings;

import com.dmbteam.catalogapp.util.ThemesManager.APP_THEMES;

import static com.dmbteam.catalogapp.util.ThemesManager.APP_THEMES.ThemeGreen;
import static com.dmbteam.catalogapp.util.ThemesManager.APP_THEMES.ThemePurple;

/**
 * The Class AppSettings.
 */
public class AppSettings {

	/** Theme of the app 
	 * 
	 * Replace the value with any of these:
	 * 
	 * ThemeGreen, ThemeBlue, ThemeOrange, ThemePurple, ThemeDarkBlue, ThemeNeutral, ThemePink
	 * 
	 * */
	public static final APP_THEMES CURRENT_THEME = ThemePurple;

	/** The Constant CURRENCY. */
	public static final String CURRENCY = "$";

	/** The Constant VAT. */
	public static final double VAT = 0.2;

	/**
	 * The Constant XMLResourcePath. Here you can either have some network xml
	 * resource or local file in assets folder.
	 */
	public static final String XMLResourcePath = "catalog.xml";
	 //public static final String XMLResourcePath =
	 //"http://dmb-team.com/apps/catalog/mobile_shop_v22.xml";

	/** The Constant CATALOG_NAME. */
	public static final String CATALOG_NAME = "winemeTEAM";

	/** The Constant MAIL. */
	public static final String MAIL = "store@wine-me.com";

	/** The Constant PHONE. */
	public static final String PHONE = "555-555-33";

	/** The Constant SKYPE. */
	public static final String SKYPE = "wine.me";

	/** The Constant FACEBOOK. */
	public static final String FACEBOOK = "facebook.com/chris.yonov";
}
