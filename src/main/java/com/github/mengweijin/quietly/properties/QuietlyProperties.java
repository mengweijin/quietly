package com.github.mengweijin.quietly.properties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author mengweijin
 */
@Data
@ConfigurationProperties(prefix = "quietly")
public class QuietlyProperties {

	private String stepPackage;

	private Test test = new Test();

	@Getter @Setter
	public static class Test {
		private Datasource datasource = new Datasource();
	}

	@Getter @Setter
	public static class Datasource {

		/**
		 * JDBC URL of the database.
		 */
		private String url;

		/**
		 * Login username of the database.
		 */
		private String username;

		/**
		 * Login password of the database.
		 */
		private String password;
	}

}
