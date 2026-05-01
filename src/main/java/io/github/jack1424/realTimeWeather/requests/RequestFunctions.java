package io.github.jack1424.realTimeWeather.requests;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

public class RequestFunctions {
	private static final int CONNECT_TIMEOUT_MS = 5000;
	private static final int READ_TIMEOUT_MS = 10000;

	public static Object makeRequest(String URLString) throws IOException, HTTPResponseException, ParseException, URISyntaxException {
		HttpURLConnection con = openConnection(URLString);
		try {
			int responseCode = con.getResponseCode();
			if (responseCode > 399)
				throw new HTTPResponseException(responseCode);

			StringBuilder response = new StringBuilder();
			try (InputStream input = con.getInputStream();
				 Scanner scanner = new Scanner(input)) {
				while (scanner.hasNextLine())
					response.append(scanner.nextLine());
			}

			return new JSONParser().parse(response.toString());
		} finally {
			con.disconnect();
		}
	}

	public static int getResponseCode(String URLString) throws IOException, URISyntaxException {
		HttpURLConnection con = openConnection(URLString);
		try {
			return con.getResponseCode();
		} finally {
			con.disconnect();
		}
	}

	private static HttpURLConnection openConnection(String URLString) throws IOException, URISyntaxException {
		URL url = new URI(URLString).toURL();
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setConnectTimeout(CONNECT_TIMEOUT_MS);
		con.setReadTimeout(READ_TIMEOUT_MS);
		return con;
	}

	public static String getLatestVersion() throws Exception {
		return ((JSONObject) ((JSONArray) makeRequest("https://api.modrinth.com/v2/project/WRA6ODcm/version")).get(0)).get("version_number").toString();
	}

	public static class HTTPResponseException extends Exception {
		public HTTPResponseException(int responseCode) {
			super(String.valueOf(responseCode));
		}
	}
}
