package flashcards.project.controller;

import flashcards.project.model.Card;
import flashcards.project.service.CardService;
import flashcards.project.service.SubtopicService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import static flashcards.project.controller.CardPage.PATH;

@WebServlet(urlPatterns = PATH)
public class CardPage extends HttpServlet {
    public static final String PATH = "/topicPage/subtopicPage/cardEditPage";
    private CardService cardService;
    private SubtopicService subtopicService;

    @Override
    public void init() {
        ServletContext context = getServletContext();
        cardService = (CardService) context.getAttribute("CardService");
        subtopicService = (SubtopicService) context.getAttribute("SubtopicService");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int subtopicId = Integer.parseInt(request.getParameter("subtopicId"));
        int topicId = Integer.parseInt(request.getParameter("topicId"));

        List<Card> cardsList = cardService.getCards(subtopicId);

        request.setAttribute("cards", cardsList);
        request.setAttribute("subtopicId", subtopicId);
        request.setAttribute("topicId", topicId);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/card.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int subtopicId = Integer.parseInt(request.getParameter("subtopicId"));
        int topicId = Integer.parseInt(request.getParameter("topicId"));
        subtopicService.removeSubtopic(subtopicId);
        response.sendRedirect(request.getContextPath() + SubtopicPage.PATH + "?topicId=" + topicId);
    }
}
