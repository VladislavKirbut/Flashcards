CREATE TABLE topic
(
    id BIGSERIAL PRIMARY KEY,
    title TEXT NOT NULL,
);

CREATE TABLE subtopic
(
    id BIGSERIAL PRIMARY KEY,
    topic_id BIGINT NOT NULL REFERENCES topic(id),
    title TEXT NOT NULL
);

CREATE TABLE card
(
    id BIGSERIAL PRIMARY KEY,
    subtopic_id BIGINT NOT NULL REFERENCES subtopic(id),
    question TEXT NOT NULL,
    answer TEXT NOT NULL,
    learned BOOLEAN NOT NULL
);
