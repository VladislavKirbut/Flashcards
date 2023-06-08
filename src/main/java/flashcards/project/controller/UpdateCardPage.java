package flashcards.project.controller;

import flashcards.project.service.TrainingService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/topicPage/subtopicPage/trainingPage/update-card")
public class UpdateCardPage extends HttpServlet {
    private TrainingService trainingService;

    public void init() {
        ServletContext context = getServletContext();
        trainingService = (TrainingService) context.getAttribute("TrainingService");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int cardId = Integer.parseInt(request.getParameter("cardId"));
        int offsetValue = Integer.parseInt(request.getParameter("offsetValue"));
        int topicId = Integer.parseInt(request.getParameter("topicId"));
        int subtopicId = Integer.parseInt(request.getParameter("subtopicId"));


            trainingService.clickKnow(cardId);
            response.sendRedirect(request.getContextPath() + TrainingPage.PATH + "?topicId=" + topicId + "&subtopicId=" + subtopicId +
                    "&offsetValue=" + (offsetValue));
    }
}
