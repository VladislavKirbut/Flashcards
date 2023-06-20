package flashcards.project.controller;

import flashcards.project.model.Card;
import flashcards.project.service.TrainingService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static flashcards.project.controller.TrainingPage.PATH;

@WebServlet(urlPatterns = PATH)
public class TrainingPage extends HttpServlet {
    private TrainingService trainingService;
    public static final String PATH = "/topicPage/subtopicPage/trainingPage";

    @Override
    public void init() {
        ServletContext context = getServletContext();
        trainingService = (TrainingService) context.getAttribute("TrainingService");
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        int subtopicId = Integer.parseInt(req.getParameter("subtopicId"));
        int previousCard = Integer.parseInt(req.getParameter("previousCard"));
        int topicId = Integer.parseInt(req.getParameter("topicId"));

        Card card = trainingService.getOneNotLearnedCard(subtopicId, previousCard)
                .orElse(null);

        if (card == null) {
            res.sendRedirect(req.getContextPath() + SubtopicPage.PATH + "?topicId=" + topicId);
        } else {
            req.setAttribute("card", card);
            req.setAttribute("topicId", topicId);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/training.jsp");
            dispatcher.forward(req, res);
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        int cardId = Integer.parseInt(req.getParameter("cardId"));
        int topicId = Integer.parseInt(req.getParameter("topicId"));
        int subtopicId = Integer.parseInt(req.getParameter("subtopicId"));

        res.sendRedirect(req.getContextPath() + TrainingPage.PATH + "?topicId=" + topicId + "&subtopicId=" + subtopicId +
                "&previousCard=" + cardId);
    }
}
