package com.gwg.user.config.log;

public enum FlowType {
	DEPOSIT("1", "充值"), PAYMENT("2", "代付"), RECEIPT("3", "代扣");

	private final String type;
	private final String desc;

	private FlowType(String type, String desc) {
		this.type = type;
		this.desc = desc;
	}

	public String type() {
		return type;
	}

	public String desc() {
		return desc;
	}
}
