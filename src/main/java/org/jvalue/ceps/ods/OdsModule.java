package org.jvalue.ceps.ods;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import retrofit.RestAdapter;
import retrofit.converter.JacksonConverter;

public class OdsModule extends AbstractModule {

	public static final String ODS_BASE_URL = "odsBaseUrl";


	@Override
	protected void configure() {
		// nothing to do yet ...
	}


	@Provides
	@Singleton
	OdsDataSourceService provideDataSourceService(RestAdapter restAdapter) {
		return restAdapter.create(OdsDataSourceService.class);
	}


	@Provides
	@Singleton
	OdsNotificationService provideNotificationService(RestAdapter restAdapter) {
		return restAdapter.create(OdsNotificationService.class);
	}


	@Provides
	@Singleton
	RestAdapter provideRestAdapter(@Named(ODS_BASE_URL) String odsBaseUrl) {
		return new RestAdapter.Builder()
				.setConverter(new JacksonConverter(new ObjectMapper()))
				.setEndpoint(odsBaseUrl)
				.build();
	}
}
