package org.hmon.server;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

@Provider
public class DateTimeParamConverterProvider implements ParamConverterProvider {

	@SuppressWarnings("unchecked")
	@Override
	public <T> ParamConverter<T> getConverter(Class<T> type, Type genericType, Annotation[] annotations) {
		if (type.equals(DateTime.class)) {
			return (ParamConverter<T>) new DateTimeParamConverter();
		} else {
			return null;
		}
	}

	private static class DateTimeParamConverter implements ParamConverter<DateTime> {
		@Override
		public DateTime fromString(String value) {
			try {
				return ISODateTimeFormat.dateTimeNoMillis().parseDateTime(value);
			} catch (IllegalArgumentException e) {
				return ISODateTimeFormat.dateTime().parseDateTime(value);
			}
		}

		@Override
		public String toString(DateTime value) {
			return value.toString();
		}

	}
}