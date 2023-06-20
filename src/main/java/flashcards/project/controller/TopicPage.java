package flashcards.project.controller;

import flashcards.project.model.Topic;
import flashcards.project.service.TopicService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;
import java.util.List;

import static flashcards.project.controller.TopicPage.PATH;

@WebServlet(urlPatterns = PATH)
public class TopicPage extends HttpServlet {
    public static final String PATH = "/topicPage";

    private TopicService topicService;

    @Override
    public void init() {
        ServletContext context = getServletContext();
        topicService = (TopicService) context.getAttribute("TopicService");
    }
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Topic> topicsList = topicService.showTopicList();
        request.setAttribute("topics", topicsList);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/topic.jsp");
        dispatcher.forward(request, response);
    }
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String newTopic = request.getParameter("newTopic");
        topicService.addTheme(newTopic);
        response.sendRedirect(request.getContextPath() + PATH);
    }
}
