package flashcards.project.controller;

import flashcards.project.model.Card;
import flashcards.project.service.CardService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static flashcards.project.util.ServletsUtils.getResponse;

@WebServlet(urlPatterns = "/topicPage/subtopicPage/cardEditPage")
public class CardPage extends HttpServlet {
    private CardService cardService;

    public void init() {
        ServletContext context = getServletContext();
        cardService = (CardService) context.getAttribute("CardService");
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        int subtopicId = Integer.parseInt(req.getParameter("subtopicId"));

        List<Card> cardList = cardService.getCards(subtopicId);
        String responseText = getResponse(cardList);

        res.setContentType("text/plain");
        res.setCharacterEncoding(StandardCharsets.UTF_8.name());
        res.setStatus(HttpServletResponse.SC_OK);

        try (Writer writer = res.getWriter()) {
            writer.write(responseText);
        }
    }
}
