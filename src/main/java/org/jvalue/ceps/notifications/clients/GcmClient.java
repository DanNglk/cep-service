package org.jvalue.ceps.notifications.clients;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public final class GcmClient extends Client {

	/**
	 * The deviceId is represented through the GCM id.
	 */
	@JsonCreator
	public GcmClient(
			@JsonProperty("clientId") String clientId,
			@JsonProperty("deviceId") String gcmId,
			@JsonProperty("eplStmt") String eplStmt) {

		super(clientId, gcmId, eplStmt);
	}


	@Override
	public <P,R> R accept(ClientVisitor<P,R> visitor, P param) {
		return visitor.visit(this, param);
	}

}
