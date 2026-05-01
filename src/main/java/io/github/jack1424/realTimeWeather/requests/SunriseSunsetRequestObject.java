package io.github.jack1424.realTimeWeather.requests;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import javax.naming.ConfigurationException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class SunriseSunsetRequestObject {
	private String sunriseTime, sunsetTime;

	public SunriseSunsetRequestObject(TimeZone timeZone, String lat, String lon) throws IOException, ParseException, ConfigurationException, URISyntaxException {
		JSONObject response;
		try {
			response = (JSONObject) ((JSONObject) RequestFunctions.makeRequest(String.format("https://api.sunrisesunset.io/json?lat=%s&lng=%s&timezone=UTC", lat, lon))).get("results");
		} catch (RequestFunctions.HTTPResponseException e) {
			throw new IOException("Server/client error (HTTP error " + e.getMessage() + ")");
		}

		sunriseTime = response.get("sunrise").toString();
		sunsetTime = response.get("sunset").toString();

		// Normalize whitespace: some APIs return a non-breaking space between the time and AM/PM
		// which DateTimeFormatter won't match against a literal space. Replace NBSP with normal space
		// and collapse any repeated whitespace to a single space.
		sunriseTime = sunriseTime.replace('\u00A0', ' ').replaceAll("\\s+", " ").trim();
		sunsetTime = sunsetTime.replace('\u00A0', ' ').replaceAll("\\s+", " ").trim();

		if (sunriseTime.equalsIgnoreCase("null") || sunsetTime.equalsIgnoreCase("null"))
			throw new ConfigurationException("Time(s) returned null. Check the sunrise/sunset longitude and latitude.");

		// Use an explicit locale and pattern. Locale.ENGLISH ensures AM/PM text is parsed predictably.
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm:ss a", Locale.ENGLISH);
		LocalDate currentDate = LocalDate.now(ZoneId.of("UTC"));
		sunriseTime = ZonedDateTime.of(currentDate, LocalTime.parse(sunriseTime, timeFormatter), ZoneId.of("UTC")).withZoneSameInstant(timeZone.toZoneId()).format(timeFormatter);
		sunsetTime = ZonedDateTime.of(currentDate, LocalTime.parse(sunsetTime, timeFormatter), ZoneId.of("UTC")).withZoneSameInstant(timeZone.toZoneId()).format(timeFormatter);
	}

	public String getSunriseTime() {
		return sunriseTime;
	}

	public String getSunsetTime() {
		return sunsetTime;
	}
}
