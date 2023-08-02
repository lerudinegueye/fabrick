package it.fabrick.bankaccount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties
@PropertySource("classpath:fabrick.properties")
public class RestProperties {
	@Autowired
	Environment env;

	Fabrick fabrick;

	public Environment getEnv() {
		return env;
	}

	public void setEnv(Environment env) {
		this.env = env;
	}

	public Fabrick getFabrick() {
		return fabrick;
	}

	public void setFabrick(Fabrick fabrick) {
		this.fabrick = fabrick;
	}

	public static class Fabrick {
		Long accountId;
		String baseUrl;
		String contextUrl;
		String url;
		String urlBalance;
		String urlMoneyTransfer;
		String urlTransaction;

		public Long getAccountId() {
			return accountId;
		}

		public void setAccountId(Long accountId) {
			this.accountId = accountId;
		}

		public String getBaseUrl() {
			return baseUrl;
		}

		public void setBaseUrl(String baseUrl) {
			this.baseUrl = baseUrl;
		}

		public String getContextUrl() {
			return contextUrl;
		}

		public void setContextUrl(String contextUrl) {
			this.contextUrl = contextUrl;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getUrlBalance() {
			return urlBalance;
		}

		public void setUrlBalance(String urlBalance) {
			this.urlBalance = urlBalance;
		}

		public String getUrlMoneyTransfer() {
			return urlMoneyTransfer;
		}

		public void setUrlMoneyTransfer(String urlMoneyTransfer) {
			this.urlMoneyTransfer = urlMoneyTransfer;
		}

		public String getUrlTransaction() {
			return urlTransaction;
		}

		public void setUrlTransaction(String urlTransaction) {
			this.urlTransaction = urlTransaction;
		}
	}
}
