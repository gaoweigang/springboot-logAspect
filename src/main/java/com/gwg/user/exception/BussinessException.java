package com.gwg.user.exception;

import com.gwg.user.common.ResultEnum;

public class BussinessException extends RuntimeException {

	private static final long serialVersionUID = 7886949843506451745L;

	private String code = ResultEnum.FAILURE.getCode();

	public BussinessException(String msg) {
		super(msg);
	}

	public BussinessException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public String getCode() {
		return code;
	}
}
