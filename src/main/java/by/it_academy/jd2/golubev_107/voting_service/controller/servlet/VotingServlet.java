package by.it_academy.jd2.golubev_107.voting_service.controller.servlet;

import by.it_academy.jd2.golubev_107.voting_service.service.IArtistService;
import by.it_academy.jd2.golubev_107.voting_service.service.IGenreService;
import by.it_academy.jd2.golubev_107.voting_service.service.IVoteService;
import by.it_academy.jd2.golubev_107.voting_service.service.dto.artist.ArtistVotingDtoSimple;
import by.it_academy.jd2.golubev_107.voting_service.service.dto.genre.GenreVotingDtoSimple;
import by.it_academy.jd2.golubev_107.voting_service.service.dto.vote.VoteInptDto;
import by.it_academy.jd2.golubev_107.voting_service.service.dto.vote.VotesResult;
import by.it_academy.jd2.golubev_107.voting_service.service.impl.ArtistServiceImpl;
import by.it_academy.jd2.golubev_107.voting_service.service.impl.GenreServiceImpl;
import by.it_academy.jd2.golubev_107.voting_service.service.impl.VoteServiceImpl;
import by.it_academy.jd2.golubev_107.voting_service.storage.entity.Comment;
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
    private static final String ARTIST_PARAM = "artistId";
    private static final String GENRE_PARAM = "genreId";
    private static final String COMMENT_PARAM = "comment";
    private final IVoteService voteService = VoteServiceImpl.getInstance();
    private final IArtistService artistService = ArtistServiceImpl.getInstance();
    private final IGenreService genreService = GenreServiceImpl.getInstance();

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
        req.setAttribute("artists", artistService.getAll());
        req.setAttribute("genres", genreService.getAll());
        req.getRequestDispatcher("jsp/vote_form.jsp").forward(req, resp);
    }

    private VoteInptDto toVoteInputDto(HttpServletRequest req) {
        VoteInptDto inputDto = new VoteInptDto();
        inputDto.setArtist(toArtistVotingDtoSimple(req));
        inputDto.setGenres(getVotedGenres(req));
        Comment comment = new Comment(req.getParameter(COMMENT_PARAM), LocalDateTime.now());
        inputDto.setComment(comment);
        return inputDto;
    }

    private ArtistVotingDtoSimple toArtistVotingDtoSimple(HttpServletRequest req) {
        ArtistVotingDtoSimple artistVotingDtoFull = new ArtistVotingDtoSimple();
        artistVotingDtoFull.setId(Long.parseLong(req.getParameter(ARTIST_PARAM)));
        return artistVotingDtoFull;
    }

    private List<GenreVotingDtoSimple> getVotedGenres(HttpServletRequest req) {
        List<GenreVotingDtoSimple> genreDtos = new ArrayList<>();
        for (String genreIdRaw : req.getParameterValues(GENRE_PARAM)) {
            genreDtos.add(new GenreVotingDtoSimple(Long.parseLong(genreIdRaw)));
        }
        return genreDtos;
    }

    private void setEncodingContentType(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
        req.setCharacterEncoding(ENCODING);
        resp.setContentType(CONTENT_TYPE);
    }

}
