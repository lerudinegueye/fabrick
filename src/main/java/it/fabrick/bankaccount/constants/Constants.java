package it.fabrick.bankaccount.constants;

public class Constants {
	public static final String API000 = "API000";
	public static final String ERROR_TECNICO_MONEY_TRANSFER = "Errore tecnico  La condizione BP049 non e' prevista per il conto id 14537780";
	public static final String ERROR_MESSAGGE = "{\r\n    \"code\": \"API000\",\r\n    \"description\": \"Errore tecnico  La condizione BP049 non e\' prevista per il conto id 14537780\"\r\n}";

	public enum HeaderList {
		AUTH_SCHEMA("S2S"), API_KEY("FXOVVXXHVCPVPBZXIJOBGUGSKHDNFRRQJP"), X_TIME_ZONE("Europe/Rome"),
		X_CORRELATION_ID("X-CorrelationID"), CONTENT_TYPE("application/json");

		private String value;

		HeaderList(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

	}

}
