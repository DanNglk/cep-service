package org.jvalue.ceps.notifications.sender;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import com.squareup.okhttp.mockwebserver.RecordedRequest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.jvalue.ceps.api.notifications.HttpClient;

import java.util.HashMap;

import mockit.integration.junit4.JMockit;


@RunWith(JMockit.class)
public final class HttpSenderTest {

	private static final String CLIENT_ID = "someClientId";

	private MockWebServer server;

	@Before
	public void startMockServer() throws Exception {
		server = new MockWebServer();
		server.enqueue(new MockResponse().setResponseCode(200));
		server.play();
	}


	@After
	public void stopMockServer() throws Exception {
		server.shutdown();
	}


	@Test
	public final void testSuccess() throws Throwable {
		String path = "/foo/bar/data/";
		String callbackUrl = server.getUrl(path).toString();
		HttpSender sender = new HttpSender();
		HttpClient client = new HttpClient(CLIENT_ID, callbackUrl, "someEplAdapterId", new HashMap<String, Object>(), "someUserId");

		SenderResult result = sender.sendEventUpdate(client);
		Assert.assertEquals(SenderResult.Status.SUCCESS, result.getStatus());

		ObjectNode sentData = new ObjectNode(JsonNodeFactory.instance);
		sentData.put("clientId", CLIENT_ID);
		RecordedRequest request = server.takeRequest();
		Assert.assertEquals(path, request.getPath());
		Assert.assertEquals(sentData.toString(), request.getUtf8Body());
	}

}
