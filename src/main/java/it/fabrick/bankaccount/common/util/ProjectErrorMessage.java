package it.fabrick.bankaccount.common.util;

public class ProjectErrorMessage {
	private int httpCode;
	private String code;
	private String message;

	public int getHttpCode() {
		return httpCode;
	}

	public void setHttpCode(int httpCode) {
		this.httpCode = httpCode;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	ProjectErrorMessage(int httpCode, String code, String message) {
		this.httpCode = httpCode;
		this.code = code;
		this.message = message;
	}

	public ProjectErrorMessage(StandardErrorMessage standardErrorMessage) {
		this.httpCode = standardErrorMessage.httpCode;
		this.code = standardErrorMessage.code;
		this.message = standardErrorMessage.message;
	}

}
