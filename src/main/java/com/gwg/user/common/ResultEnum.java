package com.gwg.user.common;

/**
 * @ClassName: ResultEnum
 */
public enum ResultEnum {
	SUCESS("00000", "successful!"),
	FAILURE("00001", "failure!"),
	UNKNOW_ERROR("00002", "unknow error!");


	private String code;
	private String msg;

	ResultEnum(String code, String msg){
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
