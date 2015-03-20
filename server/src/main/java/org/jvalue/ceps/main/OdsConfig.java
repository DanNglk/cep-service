package org.jvalue.ceps.main;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public final class OdsConfig {

	@NotNull private final String url;
	@NotNull private final String username;
	@NotNull private final String password;

	@JsonCreator
	public OdsConfig(
			@JsonProperty("url") String url,
			@JsonProperty("username") String username,
			@JsonProperty("password") String password) {

		this.url = url;
		this.username = username;
		this.password = password;
	}


	public String getUrl() {
		return url;
	}


	public String getUsername() {
		return username;
	}


	public String getPassword() {
		return password;
	}

}
