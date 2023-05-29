package flashcards.project.service;

import flashcards.project.exception.IncorrectParameters;
import flashcards.project.model.Subtopic;
import flashcards.project.repository.SubtopicRepository;

import java.util.List;

public class SubtopicServiceImpl implements SubtopicService {
    private final SubtopicRepository repository;

    public SubtopicServiceImpl(SubtopicRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Subtopic> getSubtopicList(int topicId) {
        return repository.getSubtopicByTopicId(topicId);
    }

    @Override
    public void addSubtopic(int topicId, String subtopicTitle) {
        if (!subtopicTitle.isEmpty())
            repository.addSubtopicBySubtopicTitle(topicId, subtopicTitle);
        else throw new IncorrectParameters();
    }

    @Override
    public void removeSubtopic(int id) {
        repository.removeSubtopicById(id);
    }
}
