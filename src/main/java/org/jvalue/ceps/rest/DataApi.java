package org.jvalue.ceps.rest;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;

import org.jvalue.ceps.data.DataSink;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/data")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public final class DataApi {

	private final DataSink dataSink;

	@Inject
	public DataApi(DataSink dataSink) {
		this.dataSink = dataSink;
	}


	@POST
	public void onNewData(SourceData sourceData) {
		dataSink.onNewData(sourceData.sourceId, sourceData.data);
	}


	private static final class SourceData {

		private final String sourceId;
		private final JsonNode data;

		@JsonCreator
		public SourceData(
				@JsonProperty("sourceId") String sourceId,
				@JsonProperty("data") JsonNode data) {

			this.sourceId = sourceId;
			this.data = data;
		}

	}

}
