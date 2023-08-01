package it.fabrick.bankaccount.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TypeEnum {
	
	GBS_TRANSACTION_TYPE_0023("GBS_TRANSACTION_TYPE_0023"),
	GBS_TRANSACTION_TYPE_0015("GBS_TRANSACTION_TYPE_0015");

    private String value;
    
    TypeEnum(String value) {
        this.value = value;
      }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static TypeEnum fromValue(String text) {
      for (TypeEnum b : TypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
