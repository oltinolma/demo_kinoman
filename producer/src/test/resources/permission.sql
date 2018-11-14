CREATE TABLE permission
(
  id serial PRIMARY KEY,
  name VARCHAR(100) NOT NULL UNIQUE,
  notes VARCHAR(200)
);

insert into permission (name) values
('permission_1'), ('permission_2');