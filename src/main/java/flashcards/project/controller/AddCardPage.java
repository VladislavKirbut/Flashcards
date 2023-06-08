package flashcards.project.controller;

import flashcards.project.service.CardService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/topicPage/subtopicPage/cardEditPage/add-card")
public class AddCardPage extends HttpServlet {
    private CardService cardService;

    @Override
    public void init() {
        ServletContext context = getServletContext();
        cardService = (CardService) context.getAttribute("CardService");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String question = request.getParameter("newCardQuestion");
        String answer = request.getParameter("newCardAnswer");

        if (question != null && answer != null) {
            int subtopicId = Integer.parseInt(request.getParameter("subtopicId"));
            int topicId = Integer.parseInt(request.getParameter("topicId"));

            cardService.addFlashcard(subtopicId, question, answer);
            response.sendRedirect(request.getContextPath() + CardPage.PATH + "?topicId=" + topicId + "&subtopicId=" + subtopicId);
        } else response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }
}
