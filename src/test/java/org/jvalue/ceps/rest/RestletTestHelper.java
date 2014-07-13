package org.jvalue.ceps.rest;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.Method;
import org.restlet.data.Reference;
import org.restlet.data.Status;


final class RestletTestHelper {

	private final Restlet restlet;
	private final Set<String> optParams;
	private final Set<String> reqParams;

	public RestletTestHelper(Restlet restlet) {
		this(restlet, new HashSet<String>(), new HashSet<String>());
	}

	public RestletTestHelper(Restlet restlet, Set<String> optParams, Set<String> reqParams) {
		this.restlet = restlet;
		this.optParams = optParams;
		this.reqParams = reqParams;
	}


	public void assertInvalidMethod(Method method) {
		assertMethod(method, Status.CLIENT_ERROR_BAD_REQUEST);
	}


	public void assertValidMethod(Method method) {
		assertMethod(method, Status.SUCCESS_OK);
	}

	
	private void assertMethod(Method method, Status status) {
		Request request = createRequestWithParams(method);
		Response response = new Response(request);
		restlet.handle(request, response);
		assertEquals(status, response.getStatus());
	}


	public void assertMissingParams(Method method) {
		Request request = createRequestNoParams(method);
		Response response = new Response(request);
		restlet.handle(request, response);

		assertEquals(Status.CLIENT_ERROR_BAD_REQUEST, response.getStatus());
	}


	public void assertStatus(Request request, Status status) {
		Response response = new Response(request);
		restlet.handle(request, response);

		assertEquals(status, response.getStatus());
	}


	public Request createRequestWithParams(Method method) {
		Request request = createRequestNoParams(method);
		for (String param : reqParams) {
			request.getResourceRef().addQueryParameter(param, param);
		}
		for (String param : optParams) {
			request.getResourceRef().addQueryParameter(param, param);
		}
		return request;
	}


	public Request createRequestNoParams(Method method) {
		return new Request(method, new Reference());
	}

}
