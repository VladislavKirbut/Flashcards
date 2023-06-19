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
import java.io.Writer;

@WebServlet(urlPatterns = "/topicPage/subtopicPage/trainingFirstPage")
public class TrainingFirstCard extends HttpServlet {
    private TrainingService trainingService;
    private static final String CARD_IS_MISSING = "Card is missing";

    @Override
    public void init() {
        ServletContext context = getServletContext();
        trainingService = (TrainingService) context.getAttribute("TrainingService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int subtopicId = Integer.parseInt(request.getParameter("subtopicId"));
        int topicId = Integer.parseInt(request.getParameter("topicId"));

        Card card = trainingService.getFirst(subtopicId).orElseThrow();

/*        response.sendRedirect(request.getContextPath() + SubtopicPage.PATH + "?topicId=" + topicId + "&subtopicId="
                + subtopicId);*/

        request.setAttribute("subtopicId", subtopicId);
        request.setAttribute("topicId", topicId);
        request.setAttribute("card", card);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/training.jsp");
        dispatcher.forward(request, response);
    }
}
