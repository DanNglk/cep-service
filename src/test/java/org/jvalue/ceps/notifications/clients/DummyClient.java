package org.jvalue.ceps.notifications.clients;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public final class DummyClient extends Client {

	@JsonCreator
	public DummyClient(
			@JsonProperty("clientId") String clientId,
			@JsonProperty("eplStmt") String eplStmt) {

		super(clientId, eplStmt);
	}

}