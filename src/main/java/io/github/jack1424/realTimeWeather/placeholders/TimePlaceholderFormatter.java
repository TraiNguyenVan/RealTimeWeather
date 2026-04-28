package io.github.jack1424.realTimeWeather.placeholders;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

public final class TimePlaceholderFormatter {
	private TimePlaceholderFormatter() {
	}

	public static String formatTime(TimeZone timeZone, String pattern) {
		if (timeZone == null || pattern == null || pattern.isBlank())
			return "";

		ZoneId zoneId = timeZone.toZoneId();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return ZonedDateTime.now(zoneId).format(formatter);
	}
}
