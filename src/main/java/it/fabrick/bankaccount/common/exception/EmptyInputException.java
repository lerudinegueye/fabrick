package it.fabrick.bankaccount.common.exception;

import org.springframework.stereotype.Component;

@Component
public class EmptyInputException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code;
	private String errorCode;
	private String errorMessage;

	public EmptyInputException(String code, String errorCode, String errorMessage) {
		super();
		this.code = code;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public EmptyInputException() {
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Override
	public String toString() {
		return "EmptyInputException [code=" + code + ", errorCode=" + errorCode + ", errorMessage=" + errorMessage + "]";
	}

}
