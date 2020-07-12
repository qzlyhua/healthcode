package cn.qzlyhua.healthcode.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class Response<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final int GENERAL_SUCCESS_CODE = 200;
    public static final String GENERAL_SUCCESS_MESSAGE = "Maskit general success";
    public static final int GENERAL_FAILURE_CODE = 400;
    public static final String GENERAL_FAILURE_MESSAGE = "Maskit general failure";
    @JsonIgnore
    private boolean success;
    private int code = 200;
    private String message = "Maskit general success";
    private T result = null;

    public Response(int code, String message, T data, boolean success) {
	this.code = code;
	this.message = message;
	this.result = data;
	this.success = success;
    }

    public Response(int code, String message, T data) {
	this.code = code;
	this.message = message;
	this.result = data;
	this.success = true;
    }

    public static <T> Response<T> yes() {
	return new Response(200, "Maskit general success", (Object) null);
    }

    public static <T> Response<T> yes(T data) {
	return new Response(200, "Maskit general success", data);
    }

    public static <T> Response<T> yes(T data, int code) {
	return new Response(code, "Maskit general success", data);
    }

    public static <T> Response<T> no() {
	return new Response(400, "Maskit general failure", (Object) null, false);
    }

    public static <T> Response<T> no(int code) {
	return new Response(code, "Maskit general failure", (Object) null, false);
    }

    public static <T> Response<T> no(T data, String message) {
	return new Response(400, message, data, false);
    }

    public static <T> Response<T> no(int code, String message, T data) {
	return new Response(code, message, data, false);
    }

    public String toString() {
	return "Response(success=" + this.isSuccess() + ", code=" + this.getCode() + ", message=" + this.getMessage() + ", result=" + this.getResult() + ")";
    }

    public boolean equals(Object o) {
	if(o == this) {
	    return true;
	} else if(!(o instanceof Response)) {
	    return false;
	} else {
	    Response<?> other = (Response) o;
	    if(!other.canEqual(this)) {
		return false;
	    } else if(this.isSuccess() != other.isSuccess()) {
		return false;
	    } else if(this.getCode() != other.getCode()) {
		return false;
	    } else {
		label40:
		{
		    Object this$message = this.getMessage();
		    Object other$message = other.getMessage();
		    if(this$message == null) {
			if(other$message == null) {
			    break label40;
			}
		    } else if(this$message.equals(other$message)) {
			break label40;
		    }

		    return false;
		}

		Object this$result = this.getResult();
		Object other$result = other.getResult();
		if(this$result == null) {
		    if(other$result != null) {
			return false;
		    }
		} else if(!this$result.equals(other$result)) {
		    return false;
		}

		return true;
	    }
	}
    }

    protected boolean canEqual(Object other) {
	return other instanceof Response;
    }

    public boolean isSuccess() {
	return this.success;
    }

    public Response<T> setSuccess(boolean success) {
	this.success = success;
	return this;
    }

    public int getCode() {
	return this.code;
    }

    public Response<T> setCode(int code) {
	this.code = code;
	return this;
    }

    public String getMessage() {
	return this.message;
    }

    public Response<T> setMessage(String message) {
	this.message = message;
	return this;
    }

    public T getResult() {
	return this.result;
    }

    public Response<T> setResult(T result) {
	this.result = result;
	return this;
    }
}
