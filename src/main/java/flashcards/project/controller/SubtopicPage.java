package flashcards.project.controller;

import flashcards.project.model.Subtopic;
import flashcards.project.service.SubtopicService;
import flashcards.project.service.TopicService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import static flashcards.project.controller.SubtopicPage.PATH;

@WebServlet(urlPatterns = PATH)
public class SubtopicPage extends HttpServlet {
    public static final String PATH = "/topicPage/subtopicPage";
    private SubtopicService subtopicService;
    private TopicService topicService;

    @Override
    public void init() {
        ServletContext context = getServletContext();
        subtopicService = (SubtopicService) context.getAttribute("SubtopicService");
        topicService = (TopicService) context.getAttribute("TopicService");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int topicId = Integer.parseInt(request.getParameter("topicId"));
        List<Subtopic> subtopicsList = subtopicService.getSubtopicList(topicId);

        request.setAttribute("subtopics", subtopicsList);
        request.setAttribute("topicId", topicId);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/subtopic.jsp");
        dispatcher.forward(request, response);
    }
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int topicId = Integer.parseInt(request.getParameter("topicId"));
        topicService.removeTopic(topicId);
        response.sendRedirect(request.getContextPath() + TopicPage.PATH);
    }
}