package flashcards.project;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import flashcards.project.exception.IncorrectCommand;
import flashcards.project.model.Card;
import flashcards.project.model.Subtopic;
import flashcards.project.model.Topic;
import flashcards.project.repository.*;

import java.util.List;
import java.util.Scanner;
public class FlashcardsApp {
    private final static int GET_TOPIC_COMMAND_SIZE = 1;
    private final static int GET_SUBTOPIC_COMMAND_SIZE = 2;
    private final static int ADD_TOPIC_COMMAND_SIZE = 2;
    private final static int REMOVE_TOPIC_COMMAND_SIZE = 2;
    private final static int SHOW_ONE_CARD_COMMAND_SIZE = 3;
    private final static int GET_CARDS_COMMAND_SIZE = 2;
    private final static int ADD_SUBTOPIC_COMMAND_SIZE = 3;
    private final static int REMOVE_SUBTOPIC_COMMAND_SIZE = 2;
    private final static int ADD_CARD_COMMAND_SIZE = 5;
    private final static int REMOVE_CARD_COMMAND_SIZE = 2;
    private final static int UPDATE_CARD_COMMAND_SIZE = 3;
    private final static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        HikariConfig config = new HikariConfig();
        config.setUsername(System.getenv("POSTGRES_DB_USER"));
        config.setPassword(System.getenv("POSTGRES_DB_PASSWORD"));
        config.setJdbcUrl(System.getenv("POSTGRES_DB_URL"));

        try (HikariDataSource database = new HikariDataSource(config)) {
            TopicRepository topicrepository = new TopicJdbcRepository(database);
            SubtopicRepository subtopicRepository = new SubtopicJdbcRepository(database);
            CardRepository cardRepository = new CardJdbcRepository(database);

            System.out.print("""
                    TOPIC
                    - getAllTopicList
                    - getSubtopicByTopicId    <topicId>
                    - addTopic                <topicTitle>
                    - removeTopic             <id>
                                        
                    SUBTOPIC
                    - getCardsBySubtopicId    <subtopicId>
                    - addSubtopic             <topicId> <subtopicTitle>
                    - removeSubtopic          <id>
                                        
                    CARD
                    - addCard                 <subtopicId> <question> <answer> <learned>
                    - removeCard              <cardId>
                    - updateCard              <id> <learned>
                    - showOneNotLearnedCard   <subtopicId> <offset>
                    """);

            while (true) {

                String input = scanner.nextLine();
                String[] inputParts = input.split("\\s+");

                switch (inputParts[0]) {
                    case "getAllTopicList" -> {
                        if (inputParts.length != GET_TOPIC_COMMAND_SIZE) throw new IncorrectCommand();
                        List<Topic> topicList = topicrepository.getAllTopicList();
                        printList(topicList);
                    }
                    case "getSubtopicByTopicId" -> {
                        if (inputParts.length != GET_SUBTOPIC_COMMAND_SIZE) throw new IncorrectCommand();

                        int topicId = Integer.parseInt(inputParts[1]);
                        List<Subtopic> subtopicList = topicrepository.getSubtopicByTopicId(topicId);
                        printList(subtopicList);
                    }
                    case "addTopic" -> {
                        if (inputParts.length != ADD_TOPIC_COMMAND_SIZE) throw new IncorrectCommand();
                        topicrepository.addTopic(inputParts[1]);
                    }
                    case "removeTopic" -> {
                        if (inputParts.length != REMOVE_TOPIC_COMMAND_SIZE) throw new IncorrectCommand();
                        topicrepository.removeTopic(Integer.parseInt(inputParts[1]));
                    }
                    case "getCardsBySubtopicId" -> {
                        if (inputParts.length != GET_CARDS_COMMAND_SIZE) throw new IncorrectCommand();
                        List<Card> cardsList = subtopicRepository.getCardsBySubtopicId(Integer.parseInt(inputParts[1]));
                        printList(cardsList);
                    }
                    case "addSubtopic" -> {
                        if (inputParts.length != ADD_SUBTOPIC_COMMAND_SIZE) throw new IncorrectCommand();
                        subtopicRepository.addSubtopic(Integer.parseInt(inputParts[1]), inputParts[2]);
                    }
                    case "removeSubtopic" -> {
                        if (inputParts.length != REMOVE_SUBTOPIC_COMMAND_SIZE) throw new IncorrectCommand();
                        subtopicRepository.removeSubtopic(Integer.parseInt(inputParts[1]));
                    }
                    case "addCard" -> {
                        if (inputParts.length != ADD_CARD_COMMAND_SIZE) throw new IncorrectCommand();
                        cardRepository.addCard(Integer.parseInt(inputParts[1]),
                                inputParts[2], inputParts[3], Boolean.parseBoolean(inputParts[4]));
                    }
                    case "removeCard" -> {
                        if (inputParts.length != REMOVE_CARD_COMMAND_SIZE) throw new IncorrectCommand();
                        cardRepository.removeCard(Integer.parseInt(inputParts[1]));
                    }
                    case "updateCard" -> {
                        if (inputParts.length != UPDATE_CARD_COMMAND_SIZE) throw new IncorrectCommand();
                        cardRepository.updateCard(Integer.parseInt(inputParts[1]), Boolean.parseBoolean(inputParts[2]));
                    }
                    case "showOneNotLearnedCard" -> {
                        if (inputParts.length != SHOW_ONE_CARD_COMMAND_SIZE) throw new IncorrectCommand();
                        String result = cardRepository.showOneNotLearnedCard(
                                        Integer.parseInt(inputParts[1]), Integer.parseInt(inputParts[2]))
                                .map(card -> "" + card)
                                .orElse("Cards absent");
                        System.out.println(result);
                    }
                    default -> throw new IncorrectCommand();
                }
            }
        }
    }
    private static <T> void printList(List<T> list) {
        for (T element : list) {
            System.out.println(element);
        }
    }
}
