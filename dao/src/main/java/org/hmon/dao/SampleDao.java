package org.hmon.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.hmon.datamodel.Sample;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

@Named
public class SampleDao extends AbstractBaseDao {
	private static final String TABLE_STATEMENT = "CREATE TABLE APP.SAMPLE (date TIMESTAMP NOT NULL, gas DOUBLE NOT NULL, electricity DOUBLE NOT NULL, PRIMARY KEY (date))";
	private static final String TRUNCATE_STATEMENT = "TRUNCATE TABLE APP.SAMPLE";
	private static final String CREATE_STATEMENT = "INSERT INTO APP.SAMPLE (date, gas, electricity) VALUES (?, ?, ?)";
	private static final String READ_STATEMENT = "SELECT date, gas, electricity FROM APP.SAMPLE WHERE date = ?";
	private static final String FIND_BY_DATE_RANGE_STATEMENT = "SELECT date, gas, electricity FROM APP.SAMPLE WHERE date >= ? AND date < ?";

	private PreparedStatement createStatement;
	private PreparedStatement readStatement;
	private PreparedStatement findByDateRange;

	@Inject
	public SampleDao(Connection dbConnection) {
		super(dbConnection);
	}

	@PostConstruct
	public void init() throws SQLException {
		createTable(TABLE_STATEMENT);
		createStatement = prepareStatement(CREATE_STATEMENT);
		readStatement = prepareStatement(READ_STATEMENT);
		findByDateRange = prepareStatement(FIND_BY_DATE_RANGE_STATEMENT);
	}

	public void truncate() throws SQLException {
		truncateTable(TRUNCATE_STATEMENT);
	}

	public void create(Sample sample) throws SQLException {
		createStatement.clearParameters();
		createStatement.setTimestamp(1, new java.sql.Timestamp(sample.getDateTime().getMillis()));
		createStatement.setDouble(2, sample.getGasReading());
		createStatement.setDouble(3, sample.getElectricityReading());
		createStatement.executeUpdate();
	}

	public Sample read(DateTime date) throws SQLException {
		readStatement.clearParameters();
		readStatement.setTimestamp(1, new java.sql.Timestamp(date.getMillis()));
		ResultSet resultSet = readStatement.executeQuery();
		try {
			return resultSet.next() ? readOneRow(resultSet) : null;
		}
		finally {
			resultSet.close();
		}
	}

	public List<Sample> findByDateRange(DateTime start, DateTime end) throws SQLException {
		findByDateRange.clearParameters();
		findByDateRange.setTimestamp(1, new java.sql.Timestamp(start.getMillis()));
		findByDateRange.setTimestamp(2, new java.sql.Timestamp(end.getMillis()));
		ResultSet resultSet = findByDateRange.executeQuery();
		try {
			List<Sample> shares = new ArrayList<>();
			while (resultSet.next()) {
				shares.add(readOneRow(resultSet));
			}
			return shares;
		}
		finally {
			resultSet.close();
		}
	}

	private Sample readOneRow(ResultSet resultSet) throws SQLException {
		return new Sample()
				.setDateTime(new DateTime(resultSet.getTimestamp(1), DateTimeZone.UTC))
				.setGasReading(resultSet.getDouble(2))
				.setElectricityReading(resultSet.getDouble(3));
	}
}
