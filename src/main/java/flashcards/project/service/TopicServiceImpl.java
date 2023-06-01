package flashcards.project.service;

import flashcards.project.exception.IncorrectParameters;
import flashcards.project.model.Topic;
import flashcards.project.repository.TopicRepository;

import java.util.List;

public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;

    public TopicServiceImpl(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @Override
    public List<Topic> showTopicList() {
        return topicRepository.getAllTopicList();
    }

    @Override
    public void addTheme(String topicTitle) {
        if (!topicTitle.isEmpty())
            topicRepository.addTopic(topicTitle);
        else throw new IncorrectParameters("Topic title is invalid");
    }

    @Override
    public void removeTopic(int topicId) {
        topicRepository.isExistsByTopicId(topicId);
        topicRepository.removeTopicById(topicId);
    }
}
