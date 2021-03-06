CREATE TABLE assignment
(
    id               int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    subject_order    varchar(100) NOT NULL,
    sign_control     varchar(100) NOT NULL,
    sign_performance varchar(100) NOT NULL,
    text_order       text         NOT NULL
);

CREATE TABLE "user"
(
    id            int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    surname       varchar(100) NOT NULL,
    name          varchar(100) NOT NULL,
    last_name     varchar(100) NOT NULL,
    job_title     varchar(100) NOT NULL,
    assignment_id int          REFERENCES assignment (id) ON DELETE SET NULL,
    taken_at      timestamp
);
