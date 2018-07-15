package seung.spring;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class SApp {

	public static void main(String[] args) {
		new SpringApplicationBuilder(SApp.class)
			.web(WebApplicationType.NONE)
			.run(args)
			;
	}
}
