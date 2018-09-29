CREATE TABLE status
(
  id serial primary key,
  name VARCHAR(100) NOT NULL,
  notes VARCHAR(200) NULL
);
CREATE TABLE roles (
  id INT PRIMARY KEY,
  name VARCHAR(100) NOT NULL UNIQUE,
  id_status INT
);
CREATE TABLE permissions
(
  id INT PRIMARY KEY,
  name VARCHAR(100) NOT NULL UNIQUE,
  info VARCHAR(200),
  id_parent INT,
  FOREIGN KEY (id_parent)
  REFERENCES public.permissions(id)
);
CREATE TABLE employees
(
  id UUID PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  login VARCHAR(100) NOT NULL UNIQUE,
  password VARCHAR(100) NOT NULL,
  enable boolean,
  imei VARCHAR(30),
  id_roles INT NOT NULL,
  FOREIGN KEY (id_roles)
  REFERENCES public.roles (id)
);
CREATE TABLE login_attempts
(
  id integer auto_increment,
  login VARCHAR(45) UNIQUE,
  attempt int NOT NULL,
  last_attempt_time TIMESTAMP NOT NULL DEFAULT now()
);
CREATE TABLE roles_permissions
(
  id INT PRIMARY KEY,
  id_role INT NOT NULL,
  id_permission INT NOT NULL,
  FOREIGN KEY (id_permission)
  REFERENCES public.permissions (id),
  FOREIGN KEY (id_role)
  REFERENCES public.roles (id)
);
create view  view_permission_login
  as SELECT e.login, p.name AS permission_name,
  p.id as permission_id, p.info as permission_info, p.id_parent as id_parent_permission
     FROM (((roles_permissions rp
       JOIN employees e ON ((e.id_roles = rp.id_role)))
       JOIN permissions p ON ((p.id = rp.id_permission)))
       JOIN roles r ON ((r.id = e.id_roles)));
create view view_employees
  as SELECT e.id,
       e.name,
       e.login,
       e.password,
       e.enable,
       e.id_roles,
       r.name AS role_name,
       e.imei
     FROM (employees e
       JOIN roles r ON ((e.id_roles = r.id)));
create view view_role_permissions
  as SELECT cfr.id,
       c.id AS id_permission,
       r.id AS id_role,
       c.name AS permission_name,
       r.name AS role_name
     FROM ((roles_permissions cfr
       JOIN permissions c ON ((cfr.id_permission = c.id)))
       JOIN roles r ON ((cfr.id_role = r.id)));
create view view_info
  as SELECT e.name AS e_name,
       e.login,
            r.name AS r_name
     FROM (employees e
       JOIN roles r ON ((e.id_roles = r.id)));