package flashcards.project.service;

import flashcards.project.exception.IncorrectParameters;
import flashcards.project.model.Topic;
import flashcards.project.repository.TopicRepository;

import java.util.List;

public class TopicServiceImpl implements TopicService {

    private final TopicRepository repository;

    public TopicServiceImpl(TopicRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Topic> showTopicList() {
        return repository.getAllTopicList();
    }

    @Override
    public void addTheme(String topicTitle) {
        if (!topicTitle.isEmpty())
            repository.addTopic(topicTitle);
        else throw new IncorrectParameters();
    }

    @Override
    public void removeTopic(int topicId) {
        repository.removeTopicById(topicId);
    }
}
