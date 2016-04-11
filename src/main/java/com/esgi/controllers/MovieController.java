package com.esgi.controllers;

import com.esgi.model.MovieEntity;
import com.esgi.model.MovieUtils;
import com.esgi.repository.HibernateConfig;
import com.esgi.services.MovieService;
import com.esgi.services.MovieServiceImpl;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

@Controller
@RequestMapping("/movies")
public class MovieController {

    private MovieServiceImpl movieService;

    private String apiAllocineUrl = "http://api.allocine.fr/rest/v3/search?count=500&filter=movie&format=json&page=1&partner=YW5kcm9pZC12Mg&profile=medium&q=";

    @Autowired
    public MovieController(MovieServiceImpl movieService) {
        this.movieService = movieService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getMovies(Model model) {
        model.addAttribute("movieUtils", new MovieUtils());
        System.out.println(movieService.getDetailMovie(1).getDateRelease());
        //Query query = HibernateConfig.getSession().createQuery("from MovieEntity");
        return("index");
    }

    @RequestMapping(method = RequestMethod.POST)
    public String searchMovies(@ModelAttribute MovieUtils movieUtils, Model model) {
        if (!movieUtils.getResearch().equals("")) {
            try {
                InputStream is = new URL(apiAllocineUrl+movieUtils.getResearch()).openStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                JsonObject body = Json.createReader(new StringReader(parseJsonFromReader(rd))).readObject();
                ArrayList<MovieEntity> listMovies = parseMovieListFromAPI(body.getJsonObject("feed").getJsonArray("movie"));
                model.addAttribute("listMovies", listMovies);
            }
            catch (IOException error) {
                System.out.println(error);
            }
        }
        return ("movies");
    }

    private String parseJsonFromReader(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    private ArrayList<MovieEntity> parseMovieListFromAPI(JsonArray listMovieFromApi) {
        ArrayList<MovieEntity> listMovies = new ArrayList();
        for (int i = 0; i<listMovieFromApi.size(); i++) {
            JsonObject movieJson = listMovieFromApi.getJsonObject(i);
            MovieEntity movie = new MovieEntity();
            movie.setTitle(movieJson.getString("originalTitle"));
            if (movieJson.get("poster") != null) {
                movie.setImageUrl(((JsonObject)movieJson.get("poster")).getString("href"));
            }
            //movie.setDateRelease(new Date(((JsonObject)movieJson.get("release")).getString("releaseDate")));
            movie.setCodeAllocine(movieJson.getJsonNumber("code").intValue());
            listMovies.add(movie);
        }
        return (listMovies);
    }
}