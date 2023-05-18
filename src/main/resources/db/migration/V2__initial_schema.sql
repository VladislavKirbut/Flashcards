INSERT INTO topic (title)
VALUES ('English'),
       ('Programming'),
       ('Sport');

INSERT INTO subtopic(topic_id, title)
VALUES (1, 'Food'),
       (1, 'Transport'),
       (1, 'Colors'),
       (1, 'Animals'),
       (2, 'OOP'),
       (2, 'SOLID'),
       (3, 'Football'),
       (3, 'Tennis');

INSERT INTO card(subtopic_id, question, answer, learned)
VALUES (1, 'Juice', 'Сок', false),
       (1, 'Cucumber', 'Огурец', false),
       (1, 'Milk', 'Молоко', false),
       (2, 'Car', 'Машина', false),
       (2, 'Bus', 'Автобус', false),
       (2, 'Bike', 'Мотоцикл', false),
       (3, 'Red', 'Красный', false),
       (3, 'Green', 'Зелёный', false),
       (3, 'Blue', 'Синий', false),
       (4, 'Cat', 'Кот', false),
       (4, 'Dog', 'Собака', false),
       (4, 'Lion', 'Лев', false),
       (5, 'Abstraction', 'Абстракция', false),
       (5, 'Inheritance', 'Наследование', false),
       (5, 'Encapsulation', 'Инкапсуляция', false),
       (5, 'Polymorphism', 'Полиморфизм', false),
       (6, 'Principle', 'Принцип', false),
       (6, 'Responsibility', 'Ответственность', false),
       (6, 'Segregation', 'Разделение', false),
       (7, 'Football player', 'Футболист', false),
       (7, 'Corner', 'Угловой', false),
       (7, 'Whistle', 'Свисток', false),
       (8, 'Field', 'Поле', false),
       (8, 'Racket', 'Ракетка', false);

-- РЕДАКТИРОВАНИЕ / ДОБАВЛЕНИЕ
-- при нажатии добавить (в таблице topic)
INSERT INTO topic(title)
VALUES (?);

-- при нажатии редактировать (в таблице topic)
SELECT id,
       topic_id,
       title
FROM subtopic
WHERE topic_id = ?;

-- при нажатии удалить тему (набор) (в таблице subtopic)
DELETE FROM topic
WHERE id = ?;

-- при нажатии добавить (в таблице подтема)
INSERT INTO subtopic(topic_id, title)
VALUES (?, ?);

-- при нажатии редактировать (в таблице подтема)
SELECT subtopic_id,
       question,
       answer,
       learned
FROM card
WHERE subtopic_id = ?;

-- при нажатии удалить подтему (набор) (в таблице карточки)
DELETE FROM subtopic
WHERE id = ?;

-- при нажатии добавить (в таблице карточки)
INSERT INTO card (subtopic_id, question, answer, learned)
VALUES (?, ?, ?, ?);

-- при нажатии удалить карточку (в таблиц карточки)
DELETE FROM card
WHERE id = ?;

-- ТРЕНИРОВКА

-- при нажатии на набор (переход на подтемы - таблица subtopic)
SELECT subtopic.id                                           AS id,
       subtopic.topic_id                                     AS topic_id,
       subtopic.title                                        AS title,
       count(c.id)                                           AS total_cards_count,
       count(c.learned) FILTER ( WHERE c.learned = true)     AS learned_cards_count
FROM subtopic
        LEFT JOIN card c ON subtopic.id = c.subtopic_id
WHERE topic_id = ?
GROUP BY subtopic.id;

--при нажатии на подтему (переход в режим тренировки) или при нажатии знаю / не знаю
SELECT id,
       subtopic_id,
       question,
       answer,
       learned
FROM card
WHERE subtopic_id = ? AND learned = false
ORDER BY id
OFFSET ? LIMIT 1;

-- при нажатии знаю / не знаю на определённой карточке
UPDATE card
SET learned = ?
WHERE id = ?;