package br.com.kauanamorim.screenmatch;

import br.com.kauanamorim.screenmatch.model.SeriesData;
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
		ApiConsumer apiConsumer = new ApiConsumer();
		String json = apiConsumer.getData("https://www.omdbapi.com/?t=gilmore+girls&apikey=bfb95c90");
		System.out.println(json);
		DataConverter dataConverter = new DataConverter();
		SeriesData data = dataConverter.convert(json, SeriesData.class);
		System.out.println(data);
	}
}
