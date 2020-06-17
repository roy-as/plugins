package com.star.common.utils;

import java.util.List;

public class CollectionUtils {
	
	public static boolean isNullOrEmpty(List<?> list) {
		return null == list || list.size() == 0;
	}

}
