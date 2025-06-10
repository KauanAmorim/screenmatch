package br.com.kauanamorim.screenmatch.principal;

import br.com.kauanamorim.screenmatch.model.Episode;
import br.com.kauanamorim.screenmatch.model.EpisodeData;
import br.com.kauanamorim.screenmatch.model.SeasonData;
import br.com.kauanamorim.screenmatch.model.SeriesData;
import br.com.kauanamorim.screenmatch.service.ApiConsumer;
import br.com.kauanamorim.screenmatch.service.DataConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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
        String search_url = API_ADDRESS + nomeSerie.replace(" ", "+") + API_KEY;
        String json = apiConsumer.getData(search_url);
        SeriesData seriesData = dataConverter.convert(json, SeriesData.class);
        System.out.println(seriesData);

        // Search Season Data
        List<SeasonData> seasons = new ArrayList<>();
        for (int i = 1; i < seriesData.totalSeasons(); i++) {
            String search_url_seasons = API_ADDRESS + nomeSerie.replace(" ", "+") + "&season=" + i + API_KEY;
            json = apiConsumer.getData(search_url_seasons);
            SeasonData season = dataConverter.convert(json, SeasonData.class);
            seasons.add(season);
        }
        seasons.forEach(System.out::println);

        seasons.forEach(
                t -> t.episodes().forEach(
                        e -> System.out.println(e.title())));

        List<EpisodeData> episodeData = seasons.stream()
                .flatMap(s -> s.episodes().stream())
                .collect(Collectors.toList());

        episodeData.stream()
                .filter(e -> !e.rating().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(EpisodeData::rating).reversed())
                .limit(5)
                .forEach(System.out::println);

        List<Episode> episodes = seasons.stream()
                .flatMap(s -> s.episodes().stream()
                        .map(e -> new Episode(s.number(), e)))
                .collect(Collectors.toList());

        episodes.forEach(System.out::println);

        System.out.println("Enter the year you want to star searching from");
        int year = scanner.nextInt();
        scanner.nextLine();

        LocalDate searchDate = LocalDate.of(year, 1, 1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        episodes.stream()
                .filter(e -> e.getReleaseDate() != null)
                .filter(e -> e.getReleaseDate().isAfter(searchDate))
                .forEach(e -> System.out.println(
                        "Season: " + e.getSeason() +
                                " Episode: " + e.getTitle() +
                                " Release Date: " + e.getReleaseDate().format(formatter)
                ));
    }
}
