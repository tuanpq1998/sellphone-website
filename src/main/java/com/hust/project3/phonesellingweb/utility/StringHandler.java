package com.hust.project3.phonesellingweb.utility;

public class StringHandler {
	
	public static boolean isEmpty(String s) {
		return s == null || s.equals("");
	}
	
	public static double[] toPriceRangeValue(String priceRange) {
		double[] res = {0, 0};
		if (priceRange.equals(ConstantVariable.PRICE_RANGE_UNDER_2M)) 
			res[1] = 2_000_000;
		else if (priceRange.equals(ConstantVariable.PRICE_RANGE_2M_TO_5M)) {
			res[0] = 2_000_000;
			res[1] = 5_000_000;
		} else if (priceRange.equals(ConstantVariable.PRICE_RANGE_5M_TO_10M)) {
			res[0] = 5_000_000;
			res[1] = 10_000_000;
		} else if (priceRange.equals(ConstantVariable.PRICE_RANGE_10M_TO_20M)) {
			res[0] = 10_000_000;
			res[1] = 20_000_000;
		} else if (priceRange.equals(ConstantVariable.PRICE_RANGE_HIGHER_20M)) 
			res[0] = 20_000_000;
		return res;
	}
}
