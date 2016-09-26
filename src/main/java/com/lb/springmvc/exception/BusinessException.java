package com.lb.springmvc.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * 描述：业务异常，在软件业务进行中，出现用户恶意破坏需要抛出的异常给用户提示
 * 
 * @author libo
 */
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String errorMethod;
	private String errorCategory;
	private Throwable cause;

	public BusinessException(String errorCategory, String errorMethod, String message) {
		super(message);
		this.errorCategory = errorCategory;
		this.errorMethod = errorMethod;
	}

	public BusinessException(String message, Throwable ex) {
		super(message, ex);
		this.cause = ex;
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(Throwable ex) {
		this.cause = ex;
	}

	public Throwable getCause() {
		return (this.cause == null ? null : this.cause);
	}

	public String getMessage() {
		String message = super.getMessage();
		Throwable cause = getCause();
		if (cause != null) {
			message = message + ";businessException 源 is " + cause;
		}
		return message;
	}

	public void printStackTrace(PrintStream ps) {
		if (getCause() == null) {
			super.printStackTrace(ps);
		} else {
			ps.println(this);
			getCause().printStackTrace(ps);
		}
	}

	public void printStackTrace(PrintWriter pw) {
		if (getCause() == null) {
			super.printStackTrace(pw);
		} else {
			pw.println(this);
			getCause().printStackTrace(pw);
		}
	}

	public void printStackTrace() {
		printStackTrace(System.err);
	}

	public String getErrorCategory() {
		return errorCategory;
	}

	public void setErrorCategory(String errorCategory) {
		this.errorCategory = errorCategory;
	}

	public String getErrorMethod() {
		return errorMethod;
	}

	public void setErrorMethod(String errorMethod) {
		this.errorMethod = errorMethod;
	}

}
