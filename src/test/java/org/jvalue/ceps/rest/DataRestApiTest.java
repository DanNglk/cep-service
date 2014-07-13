package org.jvalue.ceps.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;
import org.jvalue.ceps.data.DataChangeListener;
import org.jvalue.ceps.data.DataManager;
import org.jvalue.ceps.data.DummyDataManager;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.data.Status;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.TextNode;


public final class DataRestApiTest {

	private static final JsonNode DATA = new TextNode("data");

	private int onNewDataCount = 0;

	@Test
	public final void testNewDataRestlet() throws Exception {
		DataManager manager = DummyDataManager.createInstance();
		Restlet restlet = new DataRestApi(manager)
			.getRoutes()
			.get(OdsRestHook.URL_NOTIFY_SOURCE_CHANGED);
		assertNotNull(restlet);

		RestletTestHelper helper = new RestletTestHelper(
				restlet, 
				new HashSet<String>(Arrays.asList(OdsRestHook.PARAM_SOURCE)), 
				new HashSet<String>());
		helper.assertInvalidMethod(Method.GET);
		helper.assertMissingParams(Method.POST);

		Request request = helper.createRequestWithParams(Method.POST);
		request.setEntity(DATA.toString(), MediaType.APPLICATION_JSON);

		manager.registerDataListener(new DummyDataChangeListener());
		Response response = new Response(request);
		restlet.handle(request, response);

		assertEquals(Status.SUCCESS_OK, response.getStatus());
		assertEquals(1, onNewDataCount);
	}


	private class DummyDataChangeListener implements DataChangeListener {

		@Override
		public void onNewDataType(String dataName, JsonNode dataSchema) { }

		@Override
		public void onNewData(String dataName, JsonNode data) {
			assertEquals(OdsRestHook.PARAM_SOURCE, dataName);
			assertEquals(DATA, data);
			onNewDataCount++;
		}
	}

}
