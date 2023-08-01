package it.fabrick.bankaccount.common.util;

public class ProjectException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private ProjectErrorMessage projectErrorMessage;
	private String debug;
	private String details;

	public ProjectException(ProjectErrorMessage projectErrorMessage, String details, String debug, Throwable cause) {
		super("[" + projectErrorMessage.getCode() + "] " + projectErrorMessage.getMessage() + " - " + details + " - "
				+ debug, cause);
		this.projectErrorMessage = projectErrorMessage;
		this.details = details;
		this.debug = debug;
	}

	public ProjectException(ProjectErrorMessage projectErrorMessage, String details, String debug) {
		this(projectErrorMessage, details, debug, null);
	}

	public ProjectException(ProjectErrorMessage projectErrorMessage, String debug) {
		this(projectErrorMessage, null, debug, null);
	}

	public ProjectException(ProjectErrorMessage projectErrorMessage) {
		this(projectErrorMessage, null, null, null);
	}

	public ProjectException(ProjectErrorMessage projectErrorMessage, String debug, Throwable ex) {
		this(projectErrorMessage, null, debug, ex);
	}

	public String getDebug() {
		return debug;
	}

	public String getDetails() {
		return details;
	}

	public ProjectErrorMessage getProjectErrorMessage() {
		return projectErrorMessage;
	}

}
