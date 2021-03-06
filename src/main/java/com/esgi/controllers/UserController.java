package com.esgi.controllers;

import com.esgi.model.Movie;
import com.esgi.model.Review;
import com.esgi.model.SessionUser;
import com.esgi.model.User;
import com.esgi.services.MovieService;
import com.esgi.services.UserService;
import com.esgi.utils.SearchUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;


/**
 * Created by Arnaud on 17/04/2016.
 */
@Controller
public class UserController {

    private UserService _userService;

    @Autowired
    private MovieService movieService;

    @Autowired
    public UserController(UserService userService) {
        _userService = userService;
    }

    @RequestMapping("/login")
    public String login(@ModelAttribute User user, Model model) {
        user = _userService.authenticateUser(user.getPseudo(), user.getPassword());
        if (user != null) {
            setSessionUser(user);
        }
        List<Movie> listMovies = movieService.getLastMovies();
        for (Movie movie : listMovies) {
            BigDecimal rating = new BigDecimal(0.0);
            if (movie.getListReviews().size() > 0) {
                for (Review review : movie.getListReviews()) {
                    rating = rating.add(review.getRating());
                }
                movie.setNote(rating.divide(new BigDecimal(movie.getListReviews().size())));
            }
        }

        model.addAttribute("user", user);
        model.addAttribute("listMovies", listMovies);
        model.addAttribute("searchUtils", new SearchUtils());
        return ("index");
    }

    @RequestMapping("/register")
    public String registerView(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("searchUtils", new SearchUtils());
        return("register");
    }

    @RequestMapping("/registerUser")
    public String register(@ModelAttribute User user, Model model) {
        model.addAttribute("searchUtils", new SearchUtils());
        _userService.registerUser(new User(user.getName(), user.getFirstName(), user.getPseudo(), user.getPassword()));
        User authenticateUser = _userService.authenticateUser(user.getPseudo(), user.getPassword());
        setSessionUser(authenticateUser);
        List<Movie> listMovies = movieService.getLastMovies();
        for (Movie movie : listMovies) {
            BigDecimal rating = new BigDecimal(0.0);
            if (movie.getListReviews().size() > 0) {
                for (Review review : movie.getListReviews()) {
                    rating = rating.add(review.getRating());
                }
                movie.setNote(rating.divide(new BigDecimal(movie.getListReviews().size())));
            }
        }
        model.addAttribute("user", new User(SessionUser.getIduser(), SessionUser.getFirstName(), SessionUser.getName(), SessionUser.getPseudo(), SessionUser.getToken()));
        model.addAttribute("listMovies", listMovies);
        return("index");
    }

    private void setSessionUser(User user) {
        SessionUser.setIduser(user.getIduser());
        SessionUser.setName(user.getName());
        SessionUser.setFirstName(user.getFirstName());
        SessionUser.setPseudo(user.getPseudo());
        SessionUser.setToken(user.getToken());
    }
}