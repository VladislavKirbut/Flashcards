package flashcards.project.controller;

import flashcards.project.model.Card;
import flashcards.project.service.TrainingService;
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int subtopicId = Integer.parseInt(req.getParameter("subtopicId"));
        int topicId = Integer.parseInt(req.getParameter("topicId"));

        String responseText = trainingService.getFirst(subtopicId)
                .map(Card::toString)
                .orElse(CARD_IS_MISSING);

        try (Writer writer = resp.getWriter()) {
            if (responseText.equals(CARD_IS_MISSING))
                resp.sendRedirect(req.getContextPath() + SubtopicPage.PATH + "?topicId=" + topicId + "&subtopicId="
                        + subtopicId);
            else writer.write(responseText);
        }
    }
}
