package flashcards.project.controller;

import flashcards.project.model.Topic;
import flashcards.project.service.TopicService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static flashcards.project.controller.TopicPage.PATH;
import static flashcards.project.util.ServletsUtils.getResponse;

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
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        List<Topic> topicList = topicService.showTopicList();
        String responseText = getResponse(topicList);

        res.setContentType("text/plain");
        res.setCharacterEncoding(StandardCharsets.UTF_8.name());
        res.setStatus(HttpServletResponse.SC_OK);

        try (Writer writer = res.getWriter()) {
            writer.write(responseText);
        }
    }
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String newTopic = request.getParameter("newTopic");
        topicService.addTheme(newTopic);
        response.sendRedirect(request.getContextPath() + PATH);
    }
}
