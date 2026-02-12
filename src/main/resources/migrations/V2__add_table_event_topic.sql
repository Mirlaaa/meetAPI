CREATE TABLE IF NOT EXISTS  event_topic (
    event_id UUID NOT NULL,
    topic_id UUID NOT NULL,

    CONSTRAINT pk_event_topic PRIMARY KEY (event_id, topic_id),

    CONSTRAINT fk_event_topic_event
    FOREIGN KEY (event_id)
    REFERENCES event (id)
    ON DELETE CASCADE,

    CONSTRAINT fk_event_topic_topic
    FOREIGN KEY (topic_id)
    REFERENCES topic (id)
    ON DELETE CASCADE
);
