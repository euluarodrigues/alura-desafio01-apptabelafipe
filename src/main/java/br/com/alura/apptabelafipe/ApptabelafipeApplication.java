package br.com.alura.apptabelafipe;

import br.com.alura.apptabelafipe.principal.Principal;
import br.com.alura.apptabelafipe.service.ConsumoApi;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApptabelafipeApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ApptabelafipeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.menuPrincipal();
	}
}
