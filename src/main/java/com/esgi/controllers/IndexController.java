package com.esgi.controllers;

import com.esgi.utils.MovieUtils;
import com.esgi.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.esgi.model.MovieEntity;

import javax.json.JsonObject;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

@Controller
public class IndexController {

    @Autowired
    private MovieService movieService;

    @RequestMapping("/")
    public String getListMovies(Model model) {
        model.addAttribute("movieUtils", new MovieUtils());
<<<<<<< HEAD

        ArrayList<MovieEntity> listMovies = new ArrayList();

        MovieEntity movie = new MovieEntity();
        listMovies.add(movie);

        model.addAttribute(listMovies);
=======
        model.addAttribute("lastMovies", movieService.getLastMovies());
>>>>>>> 87770a047097c7df13947c807cf36538c9df29b4
        return ("index");
    }
}