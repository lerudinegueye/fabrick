package it.fabrick.bankaccount.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

public class ProjectUtil {

	public static HttpEntity<Object> fillHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Auth-Schema", "S2S");
		headers.set("Api-Key", "FXOVVXXHVCPVPBZXIJOBGUGSKHDNFRRQJP");
		headers.set("Content-Type", "application/json");
		headers.set("X-Time-Zone", "Europe/Rome");
		HttpEntity<Object> entity = new HttpEntity<>(headers);
		return entity;
	}

	public static Map<String, Object> fillQueryParam(Date fromAccountingDate, Date toAccountingDate) {
		Map<String, Object>  queryParamsFilter = new HashMap<String, Object>();
		queryParamsFilter.put("fromAccountingDate", fromAccountingDate);
		queryParamsFilter.put("toAccountingDate", toAccountingDate);
		return queryParamsFilter;
	}

	public static String fillPathVariable(String url, Long accountId) {
		url = url.replace("{accountId}", accountId.toString());
		return url;
	}

	// Put the header X-Time-Zone into the response.If we want to add in future some headers, we can add them in the condition
	public static MultiValueMap<String, String> getResponseHeaders(HttpHeaders header) {
		MultiValueMap<String, String> headersResponse = new LinkedMultiValueMap<>();
		header.forEach((key, value) -> {
			if (key.equalsIgnoreCase("X-Time-Zone") || key.equalsIgnoreCase("Content-Type")) {
				headersResponse.addAll(key, value);
			}
		});
		return headersResponse;
	}
	
	public static Map<String, String> fillUrlParameters() {
		Map<String, String> urlParams = new HashMap<>();
		urlParams.put("X-Time-Zone", "Europe/Rome");
		urlParams.put("Auth-Schema", "S2S");
		urlParams.put("Api-Key", "FXOVVXXHVCPVPBZXIJOBGUGSKHDNFRRQJP");
		return urlParams;
	}
	
	public static UriComponentsBuilder createBuilder(Date fromAccountingDate, Date toAccountingDate, String url)
			throws UnsupportedEncodingException {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
		        // Add query parameter
		        .queryParam("fromAccountingDate", URLEncoder.encode(ProjectUtil.convertDateToString(fromAccountingDate),"UTF-8"))
		        .queryParam("toAccountingDate", URLEncoder.encode(ProjectUtil.convertDateToString(toAccountingDate),"UTF-8"));
		return builder;
	}
	
	public static ProjectErrorMessage fillErrorData(StandardErrorMessage standardErrorMessage, String s0){
		ProjectErrorMessage projectErrorMessage = new ProjectErrorMessage(standardErrorMessage);
		String message = projectErrorMessage.getMessage();
		int index0 = message.indexOf("{0}");
		StringBuilder builder = new StringBuilder(message.substring(0,index0));
		builder.append(s0).append(message.substring(index0+3));
		projectErrorMessage.setMessage(builder.toString());
		return projectErrorMessage;
	}
	public static ProjectErrorMessage fillErrorData(StandardErrorMessage standardErrorMessage, String s0, String s1){
		ProjectErrorMessage projectErrorMessage = new ProjectErrorMessage(standardErrorMessage);
		String message = projectErrorMessage.getMessage();
		int index0 = message.indexOf("{0}");
		int index1 = message.indexOf("{1}");
		StringBuilder builder = new StringBuilder(message.substring(0,index0));
		builder.append(s0).append(message.substring(index0+3,index1));
		builder.append(s1).append(message.substring(index1+3));
		projectErrorMessage.setMessage(builder.toString());
		return projectErrorMessage;
	}
	
	public static String convertDateToString(Date date) {
		Format formatter = new SimpleDateFormat("yyyy-MM-dd");
		String s = formatter.format(date);
		return s;
	}

}
