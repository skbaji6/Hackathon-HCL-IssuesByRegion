package com.example.hackathon;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class LogValidation {

	static String regexPattern = "\"type\":\"([A-Z]+)\",\"userID\":\"([0-9]+)\",\"messageID\":\"([0-9A-Za-z]+)\",\"statusCode\":([0-9]+)";

	static Pattern pattern = Pattern.compile(regexPattern);

	private static Map<String, List<String>> validateLogs(List<Log> logs) {
		List<Log> filteredlogs = logs
				.stream()
				.filter(log -> log.type != null)
				.map(uiLog -> {
					if (Integer.valueOf(uiLog.userID.substring(uiLog.userID
							.length() - 1)) <= 0) {
						uiLog.type = "ERROR";
					} else if (!(Integer.valueOf(uiLog.userID.substring(0, 2))
							% Integer.valueOf(uiLog.userID
									.substring(uiLog.userID.length() - 1)) == 0)) {
						uiLog.type = "ERROR";
					}
					if (uiLog.statusCode >= 400) {
						uiLog.type = "ERROR";
					}
					return uiLog;
				}).collect(Collectors.toList());

		return filteredlogs.stream().collect(
				Collectors.groupingBy(
						log -> log.type,
						Collectors.mapping(log -> log.messageID,
								Collectors.toList())));
	}

	private static class Log {
		String type;
		String userID;
		String messageID;
		int statusCode;

	}

	public static void main(String args[]) throws Exception {
		Scanner input = new Scanner(System.in);
		try {
			List<Log> logs = new ArrayList<Log>();

			do {

				Log log = new Log();

				Matcher matcher = pattern.matcher(input.nextLine());

				while (matcher.find()) {

					log.type = matcher.group(1);

					log.userID = matcher.group(2);

					log.messageID = matcher.group(3);

					log.statusCode = Integer.parseInt(matcher.group(4));

				}

				logs.add(log);

			} while (input.hasNext());

			printLogResults(validateLogs(logs));
		} finally {
			input.close();
		}

	}

	public static void printLogResults(Map<String, List<String>> processedLogs) {

		StringBuffer sb = new StringBuffer();

		sb.append("{")
				.append("\"ERROR\":[\"")
				.append(processedLogs.get("ERROR").stream()
						.collect(Collectors.joining("\",\""))).append("\"],");

		sb.append("\"INFO\":[\"")
				.append(processedLogs.get("INFO").stream()
						.collect(Collectors.joining("\",\""))).append("\"],");

		sb.append("\"PERFORMANCE\":[\"")
				.append(processedLogs.get("PERFORMANCE").stream()
						.collect(Collectors.joining("\",\""))).append("\"]");

		sb.append("}");

		System.out.println(sb.toString());

	}

}