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

import static flashcards.project.util.ServletsUtils.getResponse;

@WebServlet(urlPatterns = "/topicPage")
public class TopicPage extends HttpServlet {

    private TopicService topicService;

    public void init() {
        ServletContext context = getServletContext();
        topicService = (TopicService) context.getAttribute("topicService");
    }

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
}
