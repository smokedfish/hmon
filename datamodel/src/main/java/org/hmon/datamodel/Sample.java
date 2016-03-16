package org.hmon.datamodel;

import org.joda.time.DateTime;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public class Sample {
	private double gasReading;
	private double electricityReading;
	private DateTime dateTime;

	public Sample() {
	}

	public double getGasReading() {
		return gasReading;
	}

	public Sample setGasReading(double gasReading) {
		this.gasReading = gasReading;
		return this;
	}

	public double getElectricityReading() {
		return electricityReading;
	}

	public Sample setElectricityReading(double electricityReading) {
		this.electricityReading = electricityReading;
		return this;
	}

	public DateTime getDateTime() {
		return dateTime;
	}

	public Sample setDateTime(DateTime dateTime) {
		this.dateTime = dateTime;
		return this;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(gasReading, electricityReading, dateTime);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Sample that = (Sample) obj;
		return Objects.equal(this.gasReading, that.gasReading)
				&& Objects.equal(this.electricityReading, that.electricityReading)
				&& Objects.equal(this.dateTime, that.dateTime);
	}

	public String toString() {
		return MoreObjects.toStringHelper(this.getClass())
				.add("gasReading", gasReading)
				.add("electricityReading", electricityReading)
				.add("lastFetch", dateTime)
				.toString();
	}
}
