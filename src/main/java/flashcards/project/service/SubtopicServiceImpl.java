package flashcards.project.service;

import flashcards.project.exception.IncorrectParameters;
import flashcards.project.model.Subtopic;
import flashcards.project.repository.SubtopicRepository;
import flashcards.project.repository.TopicRepository;

import java.util.List;

public class SubtopicServiceImpl implements SubtopicService {
    private final SubtopicRepository subtopicRepository;
    private final TopicRepository topicRepository;

    public SubtopicServiceImpl(SubtopicRepository subtopicRepository, TopicRepository topicRepository) {
        this.subtopicRepository = subtopicRepository;
        this.topicRepository = topicRepository;
    }

    @Override
    public List<Subtopic> getSubtopicList(int topicId) {
        validateTopicExists(topicId);
        return subtopicRepository.getSubtopicByTopicId(topicId);
    }

    @Override
    public void addSubtopic(int topicId, String subtopicTitle) {
        validateTopicExists(topicId);

        if (!subtopicTitle.isEmpty())
            subtopicRepository.addSubtopicBySubtopicTitle(topicId, subtopicTitle);
        else throw new IncorrectParameters("Subtopic title is invalid");
    }

    @Override
    public void removeSubtopic(int subtopicId) {
        boolean isExist = subtopicRepository.existsBySubtopicId(subtopicId);
        if (!isExist)
            throw new IncorrectParameters("Subtopic id not found");

        subtopicRepository.removeSubtopicById(subtopicId);
    }

    private void validateTopicExists(int topicId) {
        if (!topicRepository.isExistsByTopicId(topicId))
            throw new IncorrectParameters("Topic doesn't exist");
    }
}
