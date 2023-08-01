package it.fabrick.bankaccount.common.util;

public enum StandardErrorMessage {
	GENERIC_ERROR(500,"01000","Generic error"),
	FIELD_INVALID_FORMATV2(400,"02001","The field {0} has an invalid format"),
	FIELD_MISSING(400,"02000","The field {0} is not filled in"),
	JSON_MAPPING_EXCEPTION(500,"01001","Exception during the mapping of the response"),
	ERROR_TECNICO_MONEY_TRANSFER(500,"API000","Errore tecnico  La condizione BP049 non e' prevista per il conto id 14537780"),
	
	
	;
	
	public final int httpCode;
	public final String code;
	public final String message;


	StandardErrorMessage(int httpCode, String code, String message) {
		this.httpCode = httpCode;
		this.code = code;
		this.message = message;
	}
}
