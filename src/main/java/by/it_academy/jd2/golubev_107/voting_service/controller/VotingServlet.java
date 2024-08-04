package by.it_academy.jd2.golubev_107.voting_service.controller;

import by.it_academy.jd2.golubev_107.voting_service.repository.entity.Comment;
import by.it_academy.jd2.golubev_107.voting_service.service.IVoteService;
import by.it_academy.jd2.golubev_107.voting_service.service.dto.VoteInptDto;
import by.it_academy.jd2.golubev_107.voting_service.service.dto.VotesResult;
import by.it_academy.jd2.golubev_107.voting_service.service.impl.VoteServiceImpl;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = "/votes")
public class VotingServlet extends HttpServlet {

    private static final String ENCODING = "UTF-8";
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
    private static final String ARTIST_PARAM = "artistName";
    private static final String GENRE_PARAM = "genre";
    private static final String COMMENT_PARAM = "comment";
    private static final IVoteService voteService = VoteServiceImpl.getInstance();

    @Override
    public void init(ServletConfig config) throws ServletException {
        voteService.init();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setEncodingContentType(req, resp);
        try (PrintWriter writer = resp.getWriter()) {
            try {
                VotesResult result = voteService.calculate(toVoteInputDto(req));
                printResult(writer, result);
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
                printError(writer, e.getMessage());
            }
        } catch (Exception e) {
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setEncodingContentType(req, resp);
    }

    private VoteInptDto toVoteInputDto(HttpServletRequest req) {
        VoteInptDto inputDto = new VoteInptDto();
        inputDto.setArtistName(req.getParameter(ARTIST_PARAM));
        inputDto.setGenres(req.getParameterValues(GENRE_PARAM));
        Comment comment = new Comment(req.getParameter(COMMENT_PARAM), LocalDateTime.now());
        inputDto.setComment(comment);
        return inputDto;
    }


    private void printResult(PrintWriter writer, VotesResult result) {
        List<String> printResults = new ArrayList<>();

        String artistsToPrint = printCalculatedVotes(result.getArtistVotes());
        String genresToPrint = printCalculatedVotes(result.getGenreVotes());
        String commentToPrint = printComments(result.getAllComments());

        printResults.add(artistsToPrint);
        printResults.add(genresToPrint);
        printResults.add(commentToPrint);

        printResults.forEach(writer::println);
        writer.println("<a href=\"./votingPage.html\">Back to voting</a>");
    }

    private void printError(PrintWriter writer, String errorMessages) {
        writer.println(errorMessages.replace("\n", "<br>"));
        writer.println("<br><a href=\"./votingPage.html\">Back to voting</a>");
    }

    private void setEncodingContentType(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
        req.setCharacterEncoding(ENCODING);
        resp.setContentType(CONTENT_TYPE);
    }

    private <T> String printCalculatedVotes(Map<T, Integer> results) {
        StringBuilder out = new StringBuilder();
        out.append("Vote results: \n");
        for (Map.Entry<T, Integer> result : results.entrySet()) {
            out.append(String.format("<p> %s : %d </p>\n", result.getKey(), result.getValue()));
        }
        return out.append("\n").toString();
    }

    private String printComments(List<Comment> comments) {
        StringBuilder out = new StringBuilder();
        out.append("The comments received: \n");
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy  HH:mm");
        for (Comment comment : comments) {
            out.append(String.format("<p> %s : %s </p>\n", dateFormat.format(comment.getDateVoted()), comment.getTextComment()));
        }
        return out.toString();
    }

}
