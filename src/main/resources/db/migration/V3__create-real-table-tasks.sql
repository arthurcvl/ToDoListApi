CREATE TABLE tasks(
    id BIGINT PRIMARY KEY NOT NULL UNIQUE AUTO_INCREMENT,
    task_name VARCHAR(20) NOT NULL,
    task_description VARCHAR(200) NOT NULL,
    task_state VARCHAR(10) NOT NULL,
    task_priority TINYINT NOT NULL,
    user_id BIGINT NOT NULL,

    FOREIGN KEY(user_id) REFERENCES users(id)

);