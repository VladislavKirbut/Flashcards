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
import java.nio.charset.StandardCharsets;

@WebServlet(urlPatterns = "/topicPage/subtopicPage/trainingPage")
public class TrainingPage extends HttpServlet {
    private TrainingService trainingService;
    private static final String CARD_IS_MISSING = "Card is missing";

    @Override
    public void init() {
        ServletContext context = getServletContext();
        trainingService = (TrainingService) context.getAttribute("TrainingService");
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        int subtopicId = Integer.parseInt(req.getParameter("subtopicId"));
        int offset = Integer.parseInt(req.getParameter("offsetValue"));

        String responseText = trainingService.getOneNotLearnedCard(subtopicId, offset)
                .map(Card::toString)
                .orElse(CARD_IS_MISSING);


        res.setContentType("text/plain");
        res.setCharacterEncoding(StandardCharsets.UTF_8.name());
        res.setStatus(HttpServletResponse.SC_OK);

        try (Writer writer = res.getWriter()) {
            if (responseText.equals(CARD_IS_MISSING)) {
                int topicId = Integer.parseInt(req.getParameter("topicId"));
                res.sendRedirect(req.getContextPath() + SubtopicPage.PATH + "?topicId=" + topicId);
            } else writer.write(responseText);
        }
    }
}
