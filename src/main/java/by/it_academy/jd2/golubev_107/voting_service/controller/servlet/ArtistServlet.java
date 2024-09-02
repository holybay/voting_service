package by.it_academy.jd2.golubev_107.voting_service.controller.servlet;

import by.it_academy.jd2.golubev_107.voting_service.service.IArtistService;
import by.it_academy.jd2.golubev_107.voting_service.service.dto.ArtistCreateDto;
import by.it_academy.jd2.golubev_107.voting_service.service.impl.ArtistServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@WebServlet(urlPatterns = "/artists")
public class ArtistServlet extends HttpServlet {
    private static final String ENCODING = "UTF-8";
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
    public static final String PARAM_ARTIST_NAME = "artistName";
    private final IArtistService artistService = ArtistServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setEncodingContentType(req, resp);
        req.getRequestDispatcher("/jsp/create_artist_form.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setEncodingContentType(req, resp);
        try {
            artistService.create(toInDto(req));
            resp.sendRedirect(req.getContextPath() + "/jsp/create_artist_form.jsp");
        } catch (IllegalArgumentException e) {
            req.setAttribute("message", printError(e.getMessage()));
            doGet(req, resp);
        }
    }

    private ArtistCreateDto toInDto(HttpServletRequest req) {
        ArtistCreateDto inDto = new ArtistCreateDto();
        inDto.setName(req.getParameter(PARAM_ARTIST_NAME));
        return inDto;
    }

    private void setEncodingContentType(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
        req.setCharacterEncoding(ENCODING);
        resp.setContentType(CONTENT_TYPE);
    }

    private String printError(String errorMessages) {
        return errorMessages.replace("\n", "<br>");
    }
}
