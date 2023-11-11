package com.hermes.talaria.global.jsonparser;

import java.util.Map;

import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class JsonParser {

	public static Map<String, Object> parser(String rawJson) {

		String jsonString = rawJson.replaceAll("&quot;", "\"")
			.replaceAll("&amp;", "&")
			.replaceAll("&lt;", "<")
			.replaceAll("&gt;", ">")
			.replaceAll("&#39;", "'");

		// ObjectMapper 생성
		ObjectMapper objectMapper = new ObjectMapper();

		Map<String, Object> oas = null;

		try {
			oas = objectMapper.readValue(jsonString, Map.class);
		} catch (Exception e) {

		}

		return oas;
	}
}
