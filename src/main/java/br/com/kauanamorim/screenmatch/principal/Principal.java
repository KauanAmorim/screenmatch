package br.com.kauanamorim.screenmatch.principal;

import br.com.kauanamorim.screenmatch.model.SeasonData;
import br.com.kauanamorim.screenmatch.model.SeriesData;
import br.com.kauanamorim.screenmatch.service.ApiConsumer;
import br.com.kauanamorim.screenmatch.service.DataConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class Principal {

    private final Scanner scanner = new Scanner(System.in);
    private final ApiConsumer apiConsumer = new ApiConsumer();
    private final DataConverter dataConverter = new DataConverter();

    private final String API_ADDRESS = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=bfb95c90";

    public void exibeMenu() {
        System.out.println("Type a serie name to search");
        String nomeSerie = scanner.nextLine();

        // Search Serie Data
        String json = apiConsumer.getData(API_ADDRESS + nomeSerie.replace(" ", "+") + "&apikey=" + API_KEY);
        SeriesData seriesData = dataConverter.convert(json, SeriesData.class);
        System.out.println(seriesData);

        // Search Season Data
        List<SeasonData> seasons = new ArrayList<>();
        for (int i = 1; i < seriesData.totalTemporadas(); i++) {
            json = apiConsumer.getData(API_ADDRESS + nomeSerie.replace(" ", "+") + "$season=" + i + "&apikey=" + API_KEY);
            SeasonData season = dataConverter.convert(json, SeasonData.class);
            seasons.add(season);
        }
        seasons.forEach(System.out::println);

        seasons.forEach(
                t -> t.episodios().forEach(
                        e -> System.out.println(e.titulo())));
    }
}
