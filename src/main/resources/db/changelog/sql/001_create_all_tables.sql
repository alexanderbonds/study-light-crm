CREATE TABLE users (
    id                  BIGSERIAL PRIMARY KEY,
    login               VARCHAR(50) UNIQUE,
    password            VARCHAR(255) NOT NULL,
    enabled             BOOLEAN DEFAULT TRUE
);

CREATE TABLE staff_units (
    id                  BIGSERIAL PRIMARY KEY,
    name                VARCHAR(50) NOT NULL
);

CREATE TABLE file_infos (
    id                  BIGSERIAL PRIMARY KEY,
    name                VARCHAR(255),
    type                VARCHAR(50),
    size                BIGINT,
    key_name            VARCHAR(255),
    upload_date         TIMESTAMP
);

CREATE TABLE profiles (
    id                  BIGSERIAL PRIMARY KEY,
    firstname           VARCHAR(50),
    lastname            VARCHAR(50),
    middlename          VARCHAR(50),
    sex                 VARCHAR(50),
    phone               VARCHAR(50),
    email               VARCHAR(50),
    about               TEXT,
    birthdate           DATE,
    employment_date     DATE,
    dismissal_date      DATE,
    user_id             BIGINT REFERENCES users(id),
    staff_unit_id       BIGINT REFERENCES staff_units(id),
    photo_id            BIGINT REFERENCES file_infos(id),
    preview_id          BIGINT REFERENCES file_infos(id)
);

CREATE TABLE departments (
    id                  BIGSERIAL PRIMARY KEY,
    name                VARCHAR(50),
    description         VARCHAR(255),
    leader_id           BIGINT REFERENCES profiles(id),
    lead_department_id  BIGINT
);

CREATE TABLE departments_profiles (
    profile_id          BIGINT NOT NULL,
    department_id       BIGINT NOT NULL,
    PRIMARY KEY (profile_id, department_id),
    FOREIGN KEY (profile_id) REFERENCES profiles(id),
    FOREIGN KEY (department_id) REFERENCES departments(id)
);

CREATE TABLE companies (
    id                  BIGSERIAL PRIMARY KEY,
    name                VARCHAR(50),
    type                BOOLEAN,
    inn                 BIGINT,
    bill_number         BIGINT,
    phone_number        VARCHAR(50),
    email               VARCHAR(50)
);

CREATE TABLE companies_managers (
    profile_id          BIGINT NOT NULL,
    company_id       BIGINT NOT NULL,
    PRIMARY KEY (profile_id, company_id),
    FOREIGN KEY (profile_id) REFERENCES profiles(id),
    FOREIGN KEY (company_id) REFERENCES companies(id)
);

CREATE TABLE contacts (
    id                  BIGSERIAL PRIMARY KEY,
    name                VARCHAR(50),
    post                VARCHAR(50),
    phone               VARCHAR(50),
    email               VARCHAR(50),
    description         VARCHAR(255),
    company_id          BIGINT REFERENCES companies(id)
);

CREATE TABLE comments (
    id                  BIGSERIAL PRIMARY KEY,
    author_id           BIGINT REFERENCES profiles(id),
    created_date        TIMESTAMP,
    text                VARCHAR(255)
);

CREATE TABLE projects (
    id                  BIGSERIAL PRIMARY KEY,
    name                VARCHAR(50),
    description         VARCHAR(255),
    manager_id          BIGINT REFERENCES profiles(id),
    company_id          BIGINT REFERENCES companies(id)
);

CREATE TABLE task_states (
    id                  BIGSERIAL PRIMARY KEY,
    name                VARCHAR(50)
);

CREATE TABLE tasks (
    id                      BIGSERIAL PRIMARY KEY,
    title                   VARCHAR(50),
    description             VARCHAR(255),
    producer_id             BIGINT REFERENCES profiles(id),
    responsible_id          BIGINT REFERENCES profiles(id),
    start_date              TIMESTAMP,
    end_date                TIMESTAMP,
    deadline                TIMESTAMP,
    task_state_id           BIGINT REFERENCES task_states(id),
    allow_change_deadline   BOOLEAN,
    project_id              BIGINT REFERENCES projects(id),
    expired                 BOOLEAN DEFAULT FALSE,
    company_id              BIGINT REFERENCES companies(id),
    FOREIGN KEY (company_id) REFERENCES companies(id)
);

CREATE TABLE tasks_coexecutors (
    task_id             BIGINT NOT NULL,
    profile_id          BIGINT NOT NULL,
    PRIMARY KEY (task_id, profile_id),
    FOREIGN KEY (task_id) REFERENCES tasks(id),
    FOREIGN KEY (profile_id) REFERENCES profiles(id)
);

CREATE TABLE tasks_spectators (
    task_id             BIGINT NOT NULL,
    profile_id          BIGINT NOT NULL,
    PRIMARY KEY (task_id, profile_id),
    FOREIGN KEY (task_id) REFERENCES tasks(id),
    FOREIGN KEY (profile_id) REFERENCES profiles(id)
);

CREATE TABLE companies_comments (
    company_id          BIGINT NOT NULL,
    comment_id          BIGINT NOT NULL,
    PRIMARY KEY (company_id, comment_id),
    FOREIGN KEY (company_id) REFERENCES companies(id),
    FOREIGN KEY (comment_id) REFERENCES comments(id)
);

CREATE TABLE tasks_comments (
    task_id            BIGINT NOT NULL,
    comment_id          BIGINT NOT NULL,
    PRIMARY KEY (task_id, comment_id),
    FOREIGN KEY (task_id) REFERENCES tasks(id),
    FOREIGN KEY (comment_id) REFERENCES comments(id)
);

CREATE TABLE employees_projects (
    profile_id          BIGINT NOT NULL,
    project_id          BIGINT NOT NULL,
    PRIMARY KEY (profile_id, project_id),
    FOREIGN KEY (profile_id) REFERENCES profiles(id),
    FOREIGN KEY (project_id) REFERENCES projects(id)
);

CREATE TABLE roles (
    id                  BIGSERIAL PRIMARY KEY,
    name                VARCHAR(50),
    visible_name        VARCHAR(50)
);

CREATE TABLE priorities (
    id                  BIGSERIAL PRIMARY KEY,
    name                VARCHAR(50),
    visible_name        VARCHAR(50)
);

CREATE TABLE roles_priorities (
    role_id             BIGINT NOT NULL,
    priority_id         BIGINT NOT NULL,
    PRIMARY KEY (role_id, priority_id),
    FOREIGN KEY (role_id) REFERENCES roles(id),
    FOREIGN KEY (priority_id) REFERENCES priorities(id)
);

CREATE TABLE users_priorities (
    user_id             BIGINT NOT NULL,
    priority_id         BIGINT NOT NULL,
    PRIMARY KEY (user_id, priority_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (priority_id) REFERENCES priorities(id)
);

CREATE TABLE staff_units_roles (
    staff_unit_id       BIGINT NOT NULL,
    role_id             BIGINT NOT NULL,
    PRIMARY KEY (staff_unit_id, role_id),
    FOREIGN KEY (staff_unit_id) REFERENCES staff_units(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

CREATE TABLE tags (
    id                  BIGSERIAL PRIMARY KEY,
    name                VARCHAR(200)
);

CREATE TABLE chat_message (
    id              BIGSERIAL PRIMARY KEY,
    chat_id         VARCHAR(50) NOT NULL,
    sender_id       BIGINT NOT NULL,
    recipient_id    BIGINT NOT NULL,
    sender_name     VARCHAR(25) NOT NULL,
    recipient_name  VARCHAR(25) NOT NULL,
    content         TEXT NOT NULL,
    timestamp       TIMESTAMP NOT NULL,
    message_status  INTEGER NOT NULL
);

CREATE TABLE chat_room (
    id              BIGSERIAL PRIMARY KEY,
    chat_id         VARCHAR(50) NOT NULL,
    sender_id       BIGINT NOT NULL,
    recipient_id    BIGINT NOT NULL
)
