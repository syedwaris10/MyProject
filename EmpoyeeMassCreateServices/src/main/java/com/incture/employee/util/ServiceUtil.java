package com.incture.employee.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class ServiceUtil {

	public static Map<Integer, String> convertSetToMap(Set<String> inputSet) {
		Map<Integer, String> outputMap = new HashMap<>();
		int index = 0;
		if (inputSet != null) {
			for (String inputString : inputSet) {
				outputMap.put(++index, inputString);
			}
		}
		return outputMap;
	}

	public static List<String> convertSetToList(Set<String> inputSet) {
		List<String> list = new ArrayList<String>();
		if (inputSet != null) {
			list.addAll(inputSet);
		}
		return list;
	}

	/**
	 * This method returns UUID random unique number
	 * 
	 * @return
	 */
	public static String getUUIDRandomId() {
		UUID uid = UUID.randomUUID();
		return uid.toString();
	}

	/**
	 * check for array
	 * 
	 * @param objs
	 * @return
	 */
	public static boolean isEmpty(Object[] objs) {
		if (objs == null || objs.length == 0) {
			return true;
		}
		return false;
	}

	/**
	 * checks for Object
	 * 
	 * @param o
	 * @return
	 */
	public static boolean isEmpty(Object o) {
		if (o == null) {
			return true;
		}
		return false;
	}

	/**
	 * checks for collection object
	 * 
	 * @param o
	 * @return
	 */
	public static boolean isEmpty(Collection<?> o) {
		if (o == null || o.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * check for string
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if (str == null || str.trim().isEmpty()) {
			return true;
		}
		return false;
	}

	// SoC : fix for INC000098771063
	public static boolean isEmpty(Integer num) {
		if ((num == null) || (num == 0)) {
			return true;
		}
		return false;
	}
}
