package com.example.hackathon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PersonalizedCoupons {

	private static List<Map<String, Object>> personalizeCoupons(
			List<Map<String, Object>> coupons, List<String> preferredCategories) {
		
		List<Map<String, Object>> filtered =preferredCategories.stream().map(cat ->{
			return coupons.stream().filter(coupon -> coupon.get("category").equals(cat)).collect(Collectors.toList());
		}).flatMap(list ->list.stream()).collect(Collectors.toList());
		
		filtered.sort((m1,m2) -> {
			Float m1per=((Float)m1.get("couponAmount")/(Float)m1.get("itemPrice"))*100;
			Float m2per=((Float)m2.get("couponAmount")/(Float)m2.get("itemPrice"))*100;
			return Double.compare(m1per, m2per);
        });
		return  filtered.stream().limit(10).map(fil -> {
			fil.remove("code");
			return fil;
		}).collect(Collectors.toList());
	}

	public static void main(String[] args) throws IOException {
		Scanner input = new Scanner(System.in);
		List<String> preferredCategories = Arrays.asList(input.nextLine()
				.split(","));
		List<Map<String, Object>> coupons = new ArrayList<>();
		int lines = Integer.parseInt(input.nextLine());
		IntStream.range(0, lines).forEach(i -> coupons.add(readCoupon(input)));
		List<Map<String, Object>> personalizedCoupons = personalizeCoupons(
				coupons, preferredCategories);
		personalizedCoupons.stream().forEach(PersonalizedCoupons::printCoupon);
	}

	public static Map<String, Object> readCoupon(Scanner input) {
		String[] couponItems = input.nextLine().split(",");
		Map<String, Object> coupon = new HashMap<>();
		coupon.put("upc", couponItems[0]);
		coupon.put("code", couponItems[1]);
		coupon.put("category", couponItems[2]);
		coupon.put("itemPrice", Float.parseFloat(couponItems[3]));
		coupon.put("couponAmount", Float.parseFloat(couponItems[4]));
		return coupon;
	}

	public static void printCoupon(Map<String, Object> coupon) {
		System.out.print("{");
		System.out
				.print("\"couponAmount\":" + coupon.get("couponAmount") + ",");
		System.out.print("\"upc\":\"" + coupon.get("upc") + "\",");
		if (coupon.containsKey("code")) {
			System.out.print("\"code\":\"" + coupon.get("code") + "\",");
		}
		System.out.print("\"itemPrice\":" + coupon.get("itemPrice") + ",");
		System.out.println("\"category\":\"" + coupon.get("category") + "\"}");
	}

}
