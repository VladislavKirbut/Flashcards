package flashcards.project.controller;

import flashcards.project.model.Subtopic;
import flashcards.project.service.SubtopicService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static flashcards.project.util.ServletsUtils.getResponse;

@WebServlet(urlPatterns = "/topicPage/subtopicPage")
public class SubtopicPage extends HttpServlet {
    private SubtopicService subtopicService;

    public void init() {
        ServletContext context = getServletContext();
        subtopicService = (SubtopicService) context.getAttribute("subtopicService");
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        int topicId = Integer.parseInt(req.getParameter("topicId"));

        List<Subtopic> subtopicList = subtopicService.getSubtopicList(topicId);
        String responseText = getResponse(subtopicList);
        res.setContentType("text/plain");
        res.setCharacterEncoding(StandardCharsets.UTF_8.name());
        res.setStatus(HttpServletResponse.SC_OK);

        try (Writer writer = res.getWriter()) {
            writer.write(responseText);
        }
    }
}
