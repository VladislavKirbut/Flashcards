package flashcards.project.controller;

import flashcards.project.service.SubtopicService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/topicPage/subtopicPage/add-subtopic")
public class AddSubtopicPage extends HttpServlet {

    private SubtopicService subtopicService;

    @Override
    public void init() {
        ServletContext context = getServletContext();
        subtopicService = (SubtopicService) context.getAttribute("SubtopicService");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subtopicName = request.getParameter("newSubtopicName");

        if (subtopicName != null) {
            int topicId = Integer.parseInt(request.getParameter("topicId"));
            subtopicService.addSubtopic(topicId, subtopicName);
            response.sendRedirect(request.getContextPath() + SubtopicPage.PATH + "?topicId=" + topicId);
        } else response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }
}
