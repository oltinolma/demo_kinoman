insert into status(id, name) values(1, 'active'), (2, 'inactive');
insert into role (id,name) values (1,'administrator'), (2,'guest');
insert into permission (id,name) values
  (1,'role.insert'), (2,'role.update'),(3,'role.delete'),(4,'role.info'),
  (5,'permission.insert'),(6,'permission.update'),(7,'permission.delete'),(8,'permission.info'),
  (9,'rp.insert'),(10,'rp.update'),(11,'rp.delete'),(12,'rp.info'),
  (13,'user.insert'),(14,'user.update'),(15,'user.delete'),(16,'user.info');
insert into role_permission (id_role, id_permission) values
  (1,1),(1,2),(1,3),(1,4),(1,5),(1,6),(1,7),
  (1,8),(1,9),(1,10),(1,11),(1,12),(1,13),(1,14),(1,15),(1,16);
insert into users (id, name, login, password, enable, id_role) values
  ('7174c9a6-3702-4832-89ef-c1d8d39623e2', 'admin','admin','$2a$10$.3dp.ruaAPkaIQpQ78sUt.o6jl7Qr3XLgeX3eeXrf.ArQQ6Rgkf0i',true,1),
  ('5840a38f-befb-48a5-a52b-f93233ab5e0a','user','user','$2a$10$.3dp.ruaAPkaIQpQ78sUt.o6jl7Qr3XLgeX3eeXrf.ArQQ6Rgkf0i',true,2);