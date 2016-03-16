package org.hmon.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.hmon.datamodel.Sample;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SampleDaoTest {
	private static SampleDao sampleDao = new SampleDao(DerbyTestHelper.getConnection());

	@BeforeClass
	public static void beforeClass() throws SQLException {
		sampleDao.init();
	}

	@Before
	public void before() throws SQLException {
		sampleDao.truncate();
	}

	@Test
	public void shouldCreate() throws SQLException {
		DateTime dateTime = new DateTime(2016, 1, 1, 1, 0, DateTimeZone.UTC);
		Sample expected = new Sample().setDateTime(dateTime).setElectricityReading(1.0).setGasReading(2.0);
		sampleDao.create(expected);
		Sample actual = sampleDao.read(dateTime);
		assertEquals(expected, actual);
	}

	@Test
	public void shouldFindAll() throws SQLException {
		List<Sample> samples = Arrays.asList(
				new Sample().setDateTime(new DateTime(2016, 1, 1, 1, 0, DateTimeZone.UTC)).setElectricityReading(1.0).setGasReading(9.0),
				new Sample().setDateTime(new DateTime(2016, 2, 1, 1, 0, DateTimeZone.UTC)).setElectricityReading(2.0).setGasReading(8.0),
				new Sample().setDateTime(new DateTime(2016, 3, 1, 1, 0, DateTimeZone.UTC)).setElectricityReading(3.0).setGasReading(7.0)
		);
		for (Sample sample : samples) {
			sampleDao.create(sample);
		}

		findAndCompare(samples.subList(0, 0), new DateTime(2016, 1, 1, 1, 0, DateTimeZone.UTC), new DateTime(2016, 1, 1, 1, 0, DateTimeZone.UTC));
		findAndCompare(samples.subList(0, 1), new DateTime(2016, 1, 1, 1, 0, DateTimeZone.UTC), new DateTime(2016, 2, 1, 1, 0, DateTimeZone.UTC));
		findAndCompare(samples.subList(0, 2), new DateTime(2016, 1, 1, 1, 0, DateTimeZone.UTC), new DateTime(2016, 3, 1, 1, 0, DateTimeZone.UTC));
		findAndCompare(samples.subList(0, 3), new DateTime(2016, 1, 1, 1, 0, DateTimeZone.UTC), new DateTime(2016, 4, 1, 1, 0, DateTimeZone.UTC));
		findAndCompare(samples.subList(1, 3), new DateTime(2016, 2, 1, 1, 0, DateTimeZone.UTC), new DateTime(2016, 4, 1, 1, 0, DateTimeZone.UTC));
		findAndCompare(samples.subList(2, 3), new DateTime(2016, 3, 1, 1, 0, DateTimeZone.UTC), new DateTime(2016, 4, 1, 1, 0, DateTimeZone.UTC));
		findAndCompare(samples.subList(3, 3), new DateTime(2016, 4, 1, 1, 0, DateTimeZone.UTC), new DateTime(2016, 4, 1, 1, 0, DateTimeZone.UTC));
	}

	private void findAndCompare(List<Sample> expected, DateTime start, DateTime end) throws SQLException {
		List<Sample> actual = sampleDao.findByDateRange(start, end);
		assertEquals(expected.size(), actual.size());
		for (Sample sample : expected) {
			assertTrue(actual.contains(sample));
		}
	}
}
