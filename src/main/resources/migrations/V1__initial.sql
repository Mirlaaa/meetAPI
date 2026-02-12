CREATE TABLE IF NOT EXISTS users (
    id UUID NOT NULL PRIMARY KEY,
    name VARCHAR(255),
    created_at TIMESTAMP,
    modified_at TIMESTAMP,
    active BOOLEAN,
    email VARCHAR(255),
    password VARCHAR(255),
);

CREATE TABLE IF NOT EXISTS topic (
    id UUID NOT NULL PRIMARY KEY,
    title VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS event (
    id UUID NOT NULL PRIMARY KEY,
    title VARCHAR(255),
    description VARCHAR(1000),
    created_at TIMESTAMP,
    modified_at TIMESTAMP,
    active BOOLEAN,
);

CREATE TABLE IF NOT EXISTS event_organization (
    id UUID NOT NULL PRIMARY KEY,
    created_at TIMESTAMP,
    modified_at TIMESTAMP,
    active BOOLEAN,

    organizer_id UUID NOT NULL,
    event_id UUID NOT NULL,
    CONSTRAINT fk_event_org_user FOREIGN KEY (organizer_id) REFERENCES "user"(id),
    CONSTRAINT fk_event_org_id FOREIGN KEY (event_id) REFERENCES event(id),

    CONSTRAINT uk_event_org
    UNIQUE (event_id, organizer_id)
);

CREATE TABLE IF NOT EXISTS event_subscription (
    id UUID NOT NULL PRIMARY KEY,
    created_at TIMESTAMP,
    modified_at TIMESTAMP,
    active BOOLEAN,

    participant_id UUID NOT NULL,
    event_id UUID NOT NULL,
    CONSTRAINT fk_event_sub_user FOREIGN KEY (participant_id) REFERENCES "user"(id),
    CONSTRAINT fk_event_sub_event FOREIGN KEY (event_id) REFERENCES event(id),

    CONSTRAINT uk_event_sub
    UNIQUE (event_id, participant_id)

);

CREATE TABLE IF NOT EXISTS activity (
    id UUID NOT NULL PRIMARY KEY,
    title VARCHAR(255),
    description VARCHAR(1000),
    created_at TIMESTAMP,
    modified_at TIMESTAMP,
    active BOOLEAN,

    event_id UUID NOT NULL,
    CONSTRAINT fk_activity_event FOREIGN KEY (event_id) REFERENCES event(id)
);


CREATE TABLE IF NOT EXISTS event_subscription_activity (
    id UUID NOT NULL PRIMARY KEY,
    created_at TIMESTAMP,
    modified_at TIMESTAMP,
    active BOOLEAN,

    attended BOOLEAN,

    subscription_id UUID NOT NULL,
    activity_id UUID NOT NULL,
    certificate_id UUID UNIQUE,

    CONSTRAINT fk_sub_act_subscription
    FOREIGN KEY (subscription_id)
    REFERENCES event_subscription(id)
    ON DELETE CASCADE,

    CONSTRAINT fk_sub_act_activity
    FOREIGN KEY (activity_id)
    REFERENCES activity(id),

    CONSTRAINT fk_sub_act_certificate
    FOREIGN KEY (certificate_id)
    REFERENCES certificate(id),

    CONSTRAINT uk_subscription_activity
    UNIQUE (subscription_id, activity_id)
    );

CREATE INDEX ix_event_org_user
    ON event_organization(organizer_id);

CREATE INDEX ix_event_org_event
    ON event_organization(event_id);

CREATE INDEX ix_event_sub_user
    ON event_subscription(participant_id);

CREATE INDEX ix_event_sub_event
    ON event_subscription(event_id);

