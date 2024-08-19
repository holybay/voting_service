package by.it_academy.jd2.golubev_107.voting_service.controller;

import by.it_academy.jd2.golubev_107.voting_service.service.IVoteService;
import by.it_academy.jd2.golubev_107.voting_service.service.dto.VoteInptDto;
import by.it_academy.jd2.golubev_107.voting_service.service.dto.VotesResult;
import by.it_academy.jd2.golubev_107.voting_service.service.impl.VoteServiceImpl;
import by.it_academy.jd2.golubev_107.voting_service.storage.entity.Comment;
import by.it_academy.jd2.golubev_107.voting_service.storage.entity.EArtist;
import by.it_academy.jd2.golubev_107.voting_service.storage.entity.EGenre;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/votes")
public class VotingServlet extends HttpServlet {

    private static final String ENCODING = "UTF-8";
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
    private static final String ARTIST_PARAM = "artistName";
    private static final String GENRE_PARAM = "genre";
    private static final String COMMENT_PARAM = "comment";
    private final IVoteService voteService = VoteServiceImpl.getInstance();
    private final List<String> initArtists = new ArrayList<>();
    private final List<String> initGenres = new ArrayList<>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        voteService.init();
        for (EArtist artist : EArtist.values()) {
            initArtists.add(artist.name());
        }
        for (EGenre genre : EGenre.values()) {
            initGenres.add(genre.name());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setEncodingContentType(req, resp);
        try {
            VotesResult result = voteService.calculate(toVoteInputDto(req));
            req.setAttribute("artists", result.getArtistVotes());
            req.setAttribute("genres", result.getGenreVotes());
            req.setAttribute("comments", result.getAllComments());
            req.getRequestDispatcher("jsp/result.jsp").forward(req, resp);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setEncodingContentType(req, resp);
        req.setAttribute("artists", initArtists);
        req.setAttribute("genres", initGenres);
        req.getRequestDispatcher("jsp/vote_form.jsp").forward(req, resp);
    }

    private VoteInptDto toVoteInputDto(HttpServletRequest req) {
        VoteInptDto inputDto = new VoteInptDto();
        inputDto.setArtistName(req.getParameter(ARTIST_PARAM));
        inputDto.setGenres(req.getParameterValues(GENRE_PARAM));
        Comment comment = new Comment(req.getParameter(COMMENT_PARAM), LocalDateTime.now());
        inputDto.setComment(comment);
        return inputDto;
    }

    private void setEncodingContentType(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
        req.setCharacterEncoding(ENCODING);
        resp.setContentType(CONTENT_TYPE);
    }

}
