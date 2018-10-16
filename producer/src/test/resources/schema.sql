CREATE TABLE IF NOT EXISTS status
(
  id serial primary key,
  name VARCHAR(100) NOT NULL,
  notes VARCHAR(200) NULL
);
CREATE TABLE IF NOT EXISTS role (
  id INT PRIMARY KEY,
  name VARCHAR(100) NOT NULL UNIQUE
);
CREATE TABLE IF NOT EXISTS permission
(
  id INT PRIMARY KEY,
  name VARCHAR(100) NOT NULL UNIQUE,
  notes VARCHAR(200)
);
CREATE TABLE IF NOT EXISTS users
(
  id UUID PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  login VARCHAR(100) NOT NULL UNIQUE,
  password VARCHAR(100) NOT NULL,
  enable boolean,
  id_role INT NOT NULL,
  FOREIGN KEY (id_role)
  REFERENCES public.role (id)
);
CREATE TABLE IF NOT EXISTS login_attempts
(
  id integer auto_increment,
  login VARCHAR(45) UNIQUE,
  attempt int NOT NULL,
  last_attempt_time TIMESTAMP NOT NULL DEFAULT now()
);
CREATE TABLE IF NOT EXISTS role_permission
(
  id INT PRIMARY KEY,
  id_role INT NOT NULL,
  id_permission INT NOT NULL,
  FOREIGN KEY (id_permission)
  REFERENCES public.permission (id),
  FOREIGN KEY (id_role)
  REFERENCES public.role (id)
);
CREATE OR REPLACE view view_permission_login
  as SELECT e.login, p.name AS permission_name,
  p.id as permission_id,
  p.notes as permission_notes
     FROM (((role_permission rp
       JOIN users e ON ((e.id_role = rp.id_role)))
       JOIN permission p ON ((p.id = rp.id_permission)))
       JOIN role r ON ((r.id = e.id_role)));
CREATE OR REPLACE view view_user
  as SELECT e.id,
       e.name,
       e.login,
       e.password,
       e.enable,
       e.id_role,
       r.name AS role_name
     FROM (users e
       JOIN role r ON ((e.id_role = r.id)));
CREATE OR REPLACE view view_role_permission
  as SELECT cfr.id,
       c.id AS id_permission,
       r.id AS id_role,
       c.name AS permission_name,
       r.name AS role_name
     FROM ((role_permission cfr
       JOIN permission c ON ((cfr.id_permission = c.id)))
       JOIN role r ON ((cfr.id_role = r.id)));
CREATE OR REPLACE view view_notes
  as SELECT e.name AS e_name,
       e.login,
            r.name AS r_name
     FROM (users e
       JOIN role r ON ((e.id_role = r.id)));