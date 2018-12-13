package com.bruno.springboot.resources.exception;

import java.io.Serializable;

public class StandardError implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer status;
	private String ms;
	private Long TimeStamp;
	
	public StandardError(Integer status, String ms, Long timeStamp) {
		super();
		this.status = status;
		this.ms = ms;
		TimeStamp = timeStamp;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMs() {
		return ms;
	}

	public void setMs(String ms) {
		this.ms = ms;
	}

	public Long getTimeStamp() {
		return TimeStamp;
	}

	public void setTimeStamp(Long timeStamp) {
		TimeStamp = timeStamp;
	}
	
	
	
	
}
