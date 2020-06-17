package com.star.common.enums;

public enum MailContentTypeEnum {
	HTML(1, "text/html;charset=UTF-8"),
	TEXT(2, "text");

	private Integer type;
	
	private String value;
	
	MailContentTypeEnum(Integer type, String value) {
		this.type = type;
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static String getValue(Integer type) {
		MailContentTypeEnum[] contentEnums = values();
		for(MailContentTypeEnum contentEnum : contentEnums) {
			if(contentEnum.type.equals(type)) {
				return contentEnum.value;
			}
		}
		return "";
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
