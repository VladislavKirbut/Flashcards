package flashcards.project;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import flashcards.project.repository.*;
import flashcards.project.service.*;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class FlashcardsApp implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        HikariConfig config = new HikariConfig();
        config.setUsername(System.getenv("POSTGRES_DB_USER"));
        config.setPassword(System.getenv("POSTGRES_DB_PASSWORD"));
        config.setJdbcUrl(System.getenv("POSTGRES_DB_URL"));
        config.setDriverClassName("org.postgresql.Driver");

        HikariDataSource database = new HikariDataSource(config);
        TopicRepository topicRepository = new TopicJdbcRepository(database);
        SubtopicRepository subtopicRepository = new SubtopicJdbcRepository(database);
        CardRepository cardRepository = new CardJdbcRepository(database);
        CardService cardService = new CardServiceImpl(cardRepository, subtopicRepository);
        SubtopicService subtopicService = new SubtopicServiceImpl(subtopicRepository, topicRepository);
        TopicService topicService = new TopicServiceImpl(topicRepository);
        TrainingService trainingService = new TrainingModeService(cardRepository, subtopicRepository);

        ServletContext context = event.getServletContext();
        context.setAttribute("HikariDataSource", database);
        context.setAttribute("topicRepository", topicRepository);
        context.setAttribute("subtopicRepository", subtopicRepository);
        context.setAttribute("cardRepository", cardRepository);
        context.setAttribute("cardService", cardService);
        context.setAttribute("subtopicService", subtopicService);
        context.setAttribute("topicService", topicService);
        context.setAttribute("trainingService", trainingService);
    }
    @Override
    public void contextDestroyed(ServletContextEvent event) {
        ServletContext context = event.getServletContext();
        HikariDataSource dataSource = (HikariDataSource) context.getAttribute("HikariDataSource");
        dataSource.close();
    }
}
