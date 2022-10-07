package com.csvdb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Response {
	private String msg;
	private Object object;
	private HttpStatus status;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public Response(String msg, Object object, HttpStatus status) {
		super();
		this.status = status;
		this.msg = msg;
		this.object = object;
	}

	public static ResponseEntity<Response> generateResponse(String msg, HttpStatus code, Object obj) {
		Response res = new Response(msg, obj, code);
		return new ResponseEntity<Response>(res, code);
	}
}
