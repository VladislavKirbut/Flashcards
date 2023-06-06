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

    private static final String HIKARI_DATA_SOURCE = "HikariDataSource";
    private static final String TOPIC_REPOSITORY = "TopicRepository";
    private static final String SUBTOPIC_REPOSITORY = "SubtopicRepository";
    private static final String CARD_REPOSITORY = "CardRepository";
    private static final String CARD_SERVICE = "CardService";
    private static final String SUBTOPIC_SERVICE = "SubtopicService";
    private static final String TOPIC_SERVICE = "TopicService";
    private static final String TRAINING_SERVICE = "TrainingService";

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
        context.setAttribute(HIKARI_DATA_SOURCE, database);
        context.setAttribute(TOPIC_REPOSITORY, topicRepository);
        context.setAttribute(SUBTOPIC_REPOSITORY, subtopicRepository);
        context.setAttribute(CARD_REPOSITORY, cardRepository);
        context.setAttribute(CARD_SERVICE, cardService);
        context.setAttribute(SUBTOPIC_SERVICE, subtopicService);
        context.setAttribute(TOPIC_SERVICE, topicService);
        context.setAttribute(TRAINING_SERVICE, trainingService);
    }
    @Override
    public void contextDestroyed(ServletContextEvent event) {
        ServletContext context = event.getServletContext();
        HikariDataSource dataSource = (HikariDataSource) context.getAttribute("HikariDataSource");
        dataSource.close();
    }
}
