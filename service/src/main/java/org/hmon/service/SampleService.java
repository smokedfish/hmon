package org.hmon.service;

import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.hmon.dao.SampleDao;
import org.hmon.datamodel.Sample;
import org.joda.time.DateTime;

@Named
public class SampleService {

	private final SampleDao sampleDao;

	@Inject
	public SampleService(SampleDao sampleDao) {
		this.sampleDao = sampleDao;
	}

	public List<Sample> getSamples(DateTime start, DateTime end) throws SQLException {
		return sampleDao.findByDateRange(start, end);
	}

	public Sample createSample(Sample sample) throws SQLException {
		sampleDao.create(sample);
		return sample;
	}
}
