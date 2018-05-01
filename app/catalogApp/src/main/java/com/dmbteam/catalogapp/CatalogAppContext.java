package com.dmbteam.catalogapp;

import android.app.Application;

import com.onesignal.OneSignal;

/**
 * The Class CatalogAppContext.
 */
public class CatalogAppContext extends Application {
	
	/* (non-Javadoc)
	 * @see android.app.Application#onCreate()
	 */
	@Override
	public void onCreate() {
		super.onCreate();

		OneSignal.startInit(this).init();
	}
}
