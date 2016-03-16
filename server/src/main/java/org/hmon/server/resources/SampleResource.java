package org.hmon.server.resources;

import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hmon.datamodel.Sample;
import org.hmon.service.SampleService;
import org.joda.time.DateTime;

@Named
@Path("/samples")
public class SampleResource {

	private final SampleService sampleService;

	@Inject
	public SampleResource(SampleService sampleService) {
		this.sampleService = sampleService;
	}

	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Sample> get(@QueryParam("start") DateTime startDate, @QueryParam("end") DateTime endDate) throws SQLException {
		return sampleService.getSamples(startDate, endDate);
	}

	@POST
    @Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
    public Sample create(Sample sample) throws SQLException {
		return sampleService.createSample(sample);
	}
}
