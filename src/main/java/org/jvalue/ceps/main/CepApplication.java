package org.jvalue.ceps.main;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jvalue.ceps.data.DataManager;
import org.jvalue.ceps.data.DataSource;
import org.jvalue.ceps.event.EventManager;
import org.jvalue.ceps.notifications.NotificationManager;
import org.jvalue.ceps.rest.DefaultRestlet;
import org.jvalue.ceps.rest.HelpRestApi;
import org.jvalue.ceps.rest.RestApi;
import org.jvalue.ceps.rest.data.DataRestApi;
import org.jvalue.ceps.rest.data.OdsRestHook;
import org.jvalue.ceps.rest.debug.DebugRestApi;
import org.jvalue.ceps.rest.event.EventRestApi;
import org.jvalue.ceps.rest.notifications.NotificationRestApi;
import org.jvalue.ceps.utils.RestException;
import org.jvalue.ceps.utils.Restoreable;
import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;


public final class CepApplication extends Application {

	static final int SERVER_PORT = 8080;
	private static final String SERVER_NAME = "http://faui2o2f.cs.fau.de:" 
		+ SERVER_PORT + "/cep-service";

	private static final String ODS_SERVER = "http://faui2o2f.cs.fau.de:8080/open-data-service";


	private final List<RestApi> apis = new LinkedList<RestApi>();

	public CepApplication() {
		apis.add(new DataRestApi(DataManager.getInstance()));
		apis.add(new NotificationRestApi(NotificationManager.getInstance()));
		apis.add(new EventRestApi(EventManager.getInstance()));
		apis.add(new DebugRestApi(
					NotificationManager.getInstance(),
					DataManager.getInstance()));

		List<String> apiCalls = new LinkedList<String>();
		for (RestApi api : apis) apiCalls.addAll(api.getRoutes().keySet());
		apis.add(new HelpRestApi(apiCalls));
	}


	@Override
	public Restlet createInboundRoot() {
		Router router = new Router(getContext());

		for (RestApi api : apis) attachRoutes(router, api);
		router.attachDefault(new DefaultRestlet());

		return router;
	}


	private void attachRoutes(Router router, RestApi restApi) {
		for (Map.Entry<String, Restlet> entry : restApi.getRoutes().entrySet()) {
			router.attach(entry.getKey(), entry.getValue());
		}
	}


	@Override
	public void start() throws Exception {
		super.start();
		restoreState();
		startSourceMonitoring();
	}


	private static void restoreState() {
		List<Restoreable> restoreables = Arrays.asList(
				DataManager.getInstance(),
				NotificationManager.getInstance());

		for (Restoreable restoreable : restoreables) restoreable.restoreState();
	}


	private static void startSourceMonitoring() {
		DataSource source = new DataSource(
				"de-pegelonline", 
				ODS_SERVER,
				"ods/de/pegelonline/stations/$class");
		DataManager manager = DataManager.getInstance();

		if (manager.isBeingMonitored(source)) return;

		try {
			manager.startMonitoring(
					source,
					SERVER_NAME + OdsRestHook.URL_NOTIFY_SOURCE_CHANGED,
					OdsRestHook.PARAM_SOURCE);

		} catch (RestException re) {
			throw new IllegalStateException(re);
		}
	}

}