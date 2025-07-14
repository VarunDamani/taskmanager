INSERT INTO task_group
(name, created_at, updated_at)
VALUES('HR Tasks', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO task_group
(name, created_at, updated_at)
VALUES('Finance Tasks', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO task
(name, description, created_at, updated_at, task_group_id)
VALUES('Ethics', 'Training', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1);

INSERT INTO task
(name, description, created_at, updated_at, task_group_id)
VALUES('Data', 'Training', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1);

INSERT INTO task
(name, description, created_at, updated_at, task_group_id)
VALUES('Compliance', 'Training', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2);

INSERT INTO task
(name, description, created_at, updated_at, task_group_id)
VALUES('Security', 'Training', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2);
