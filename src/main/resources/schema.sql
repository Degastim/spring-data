CREATE TABLE user_accounts
(
    user_account_id BIGSERIAL,
    money           DECIMAL(8, 2) NOT NULL,
    PRIMARY KEY (user_account_id)
);

CREATE TABLE users
(
    user_id BIGSERIAL,
    name    VARCHAR(20) NOT NULL,
    email   VARCHAR(256),
    user_account_id BIGINT,
    PRIMARY KEY (user_id),
    FOREIGN KEY(user_account_id) REFERENCES user_accounts(user_account_id)
);

CREATE TABLE events
(
    event_id BIGSERIAL,
    title    VARCHAR(20) NOT NULL,
    date     TIMESTAMP   NOT NULL,
    PRIMARY KEY (event_id)
);



CREATE TABLE tickets
(
    ticket_id    BIGSERIAL,
    user_id      BIGINT        NOT NULL,
    event_id     BIGINT        NOT NULL,
    place        INT           NOT NULL,
    ticket_price DECIMAL(4, 2) NOT NULL,
    category     VARCHAR(20)   NOT NULL,
    PRIMARY KEY (ticket_id),
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (event_id) REFERENCES events (event_id)
);

