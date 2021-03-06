package org.jvalue.ceps.rest;


import org.jvalue.ceps.GitConstants;
import org.jvalue.commons.rest.VersionInfo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/version")
@Produces(MediaType.APPLICATION_JSON)
public final class VersionApi {

	private static final VersionInfo version = new VersionInfo(
			GitConstants.VERSION,
			"https://github.com/jvalue/cep-service/commit/" +  GitConstants.COMMIT_HASH);


	@GET
	public VersionInfo getVersion() {
		return version;
	}

}
