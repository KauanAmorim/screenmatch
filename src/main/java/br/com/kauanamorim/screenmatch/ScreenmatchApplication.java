package br.com.kauanamorim.screenmatch;

import br.com.kauanamorim.screenmatch.model.SeriesData;
import br.com.kauanamorim.screenmatch.principal.Principal;
import br.com.kauanamorim.screenmatch.service.ApiConsumer;
import br.com.kauanamorim.screenmatch.service.DataConverter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.exibeMenu();
	}
}
