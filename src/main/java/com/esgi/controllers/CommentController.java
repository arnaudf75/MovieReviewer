package com.esgi.controllers;

import com.esgi.model.*;
import com.esgi.services.CommentService;
import com.esgi.services.MovieService;
import com.esgi.utils.SearchUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.math.BigDecimal;


@Controller
@RequestMapping("/movies/comments")
public class CommentController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private CommentService commentService;

    @RequestMapping(method = RequestMethod.POST)
    public String addComment(@ModelAttribute Comment comment, Model model) {
        commentService.addComment(comment);
        Movie movie = movieService.getDetailMovie(comment.getIdmovie());
        boolean hasReviewed = false;
        BigDecimal rating = new BigDecimal(0.0);
        if (movie.getListReviews().size() > 0) {
            for (Review review : movie.getListReviews()) {
                rating = rating.add(review.getRating());
                if (review.getIduser().equals(SessionUser.getIduser())) {
                    hasReviewed = true;
                }
            }
            movie.setNote(rating.divide(new BigDecimal(movie.getListReviews().size())));
        }
        model.addAttribute("review", new Review());
        model.addAttribute("hasReviewed", !hasReviewed);
        model.addAttribute("comment", new Comment());
        model.addAttribute("movie", movie);
        model.addAttribute("searchUtils", new SearchUtils());
        model.addAttribute("user", new User(SessionUser.getIduser(), SessionUser.getFirstName(), SessionUser.getName(), SessionUser.getPseudo(), SessionUser.getToken()));
        return ("detailMovie");
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateComment(@ModelAttribute Comment comment, Model model) {
        commentService.updateComment(comment);
        return ("detailMovie");
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public String deleteComment(@ModelAttribute Comment comment, Model model) {
        commentService.deleteComment(comment);
        return ("detailMovie");
    }


}
