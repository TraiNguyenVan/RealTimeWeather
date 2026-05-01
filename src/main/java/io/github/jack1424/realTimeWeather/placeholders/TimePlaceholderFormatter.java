package io.github.jack1424.realTimeWeather.placeholders;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

public final class TimePlaceholderFormatter {
	private TimePlaceholderFormatter() {
	}

	public static String formatTime(TimeZone timeZone, String pattern) {
		if (timeZone == null || pattern == null || isBlank(pattern))
			return "";

		ZoneId zoneId = timeZone.toZoneId();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return ZonedDateTime.now(zoneId).format(formatter);
	}

	// Replacement for String.isBlank()
	private static boolean isBlank(String s) {
		return s == null || s.codePoints().allMatch(Character::isWhitespace);
	}
}
