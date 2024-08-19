package com.cos.gowalk.util;

import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author   : 윤기환
 * @Class    : MediaUtils.java
 * @Desc     : 이미지 유형 처리 파일
 * */
public class MediaUtils {
	private static Map<String, MediaType> mediaMap;
	
	static {
		mediaMap = new HashMap<>();
		mediaMap.put("JPG", MediaType.IMAGE_JPEG);
		mediaMap.put("GIF", MediaType.IMAGE_GIF);
		mediaMap.put("PNG", MediaType.IMAGE_PNG);
	}
	public static MediaType getMediaType(String type) {
		//이미지가 소문자로 들어올 수 있어서 toUppercase()로 대문자 변경처리
		return mediaMap.get(type.toUpperCase());
	}

}
