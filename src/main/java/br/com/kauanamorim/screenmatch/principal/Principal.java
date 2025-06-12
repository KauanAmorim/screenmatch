package br.com.kauanamorim.screenmatch.principal;

import br.com.kauanamorim.screenmatch.model.Episode;
import br.com.kauanamorim.screenmatch.model.EpisodeData;
import br.com.kauanamorim.screenmatch.model.SeasonData;
import br.com.kauanamorim.screenmatch.model.SeriesData;
import br.com.kauanamorim.screenmatch.service.ApiConsumer;
import br.com.kauanamorim.screenmatch.service.DataConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private final Scanner scanner = new Scanner(System.in);
    private final ApiConsumer apiConsumer = new ApiConsumer();
    private final DataConverter dataConverter = new DataConverter();

    private final String API_ADDRESS = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=bfb95c90";

    public void exibeMenu() {

        String menu = """
                1 - Search series
                2 - Show episodes
                
                0 - Exit program
                """;

        System.out.println(menu);
        int option = scanner.nextInt();
        scanner.nextLine();

        switch (option) {
            case 1:
                searchSerie();
                break;

            case 2:
                searchEpisodeBySerie();
                break;

            case 0:
                System.out.println("Exiting...");
                break;

            default:
                System.out.println("Invalid option");
                break;
        }
    }

    private void searchSerie() {
        SeriesData seriesData = getSeriesData();
        System.out.println(seriesData);
    }

    private void searchEpisodeBySerie() {
        SeriesData seriesData = getSeriesData();
        List<SeasonData> seasons = new ArrayList<>();

        for (int i = 1; i < seriesData.totalSeasons(); i++) {
            String search_url_seasons = API_ADDRESS + seriesData.title().replace(" ", "+") + "&season=" + i + API_KEY;
            String json = apiConsumer.getData(search_url_seasons);
            SeasonData season = dataConverter.convert(json, SeasonData.class);
            seasons.add(season);
        }

        seasons.forEach(System.out::println);
    }

    private SeriesData getSeriesData() {
        System.out.println("Type a serie name to search");
        String nomeSerie = scanner.nextLine();

        String search_url = API_ADDRESS + nomeSerie.replace(" ", "+") + API_KEY;
        String json = apiConsumer.getData(search_url);
        return dataConverter.convert(json, SeriesData.class);
    }
}
