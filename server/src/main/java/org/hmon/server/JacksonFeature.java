package org.hmon.server;

import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

public class JacksonFeature implements Feature {

	@Override
	public boolean configure(FeatureContext context) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JodaModule());
		// We want ISO dates, not Unix timestamps!:
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

		JacksonJaxbJsonProvider provider = new JacksonJaxbJsonProvider();
		provider.setMapper(mapper);

		context.register(provider);
		return true;
	}
}