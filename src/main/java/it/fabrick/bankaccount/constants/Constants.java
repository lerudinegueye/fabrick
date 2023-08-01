package it.fabrick.bankaccount.constants;

public class Constants {

	public static final long ACCOUNT_ID = 14537780;

	public static final String API000 = "API000";
	public static final String ERROR_TECNICO_MONEY_TRANSFER = "Errore tecnico  La condizione BP049 non e' prevista per il conto id 14537780";
	public static final String ERROR_MESSAGGE = "{\r\n    \"code\": \"API000\",\r\n    \"description\": \"Errore tecnico  La condizione BP049 non e\' prevista per il conto id 14537780\"\r\n}";

	// ******URL HEADERS variables
	public static class HEADERS {
		public static final String AUTH_SCHEMA = "S2S";
		public static final String API_KEY = "FXOVVXXHVCPVPBZXIJOBGUGSKHDNFRRQJP";
		public static final String X_TIME_ZONE = "Europe/Rome";

	}
	
	public enum HeaderList{
		AUTH_SCHEMA("S2S"),
		API_KEY("FXOVVXXHVCPVPBZXIJOBGUGSKHDNFRRQJP"),
		X_TIME_ZONE("Europe/Rome"),
		X_CORRELATION_ID("X-CorrelationID"),
		CONTENT_TYPE("application/json")
		;
		
		
        private String value;
        HeaderList(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

    }

	// *****URL variables
//	public static class RESTURL {
//		public static final String BASE_URL = "https://sandbox.platfr.io";
//		public static final String CONTEXT_URL = "/api/gbs/banking/v4.0/accounts/{accountId}";
//		public static final String URL = BASE_URL.concat(CONTEXT_URL);
//		public static final String URL_BALANCE = URL.concat("/balance");
//		public static final String URL_MONEY_TRANSFER = URL.concat("/payments/money-transfers");
//		public static final String URL_TRANSACTION = URL.concat("/transactions");
//
//	}
}
