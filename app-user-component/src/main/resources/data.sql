insert into app_user (id,first_name,last_name,phone_number,email,password,role,app_user_id) values
('1', 'Admin','Admin','000', 'admin','$2a$05$GIny7PaavzM8fcWL3ewJDeEFvTzd/L4lty7XxxclNeiFLfopL3rq2','ADMIN','10'),
('40001', 'Laurentiu','Pop','0799999', 'customer@gmail.com','$2a$05$GIny7PaavzM8fcWL3ewJDeEFvTzd/L4lty7XxxclNeiFLfopL3rq2','CUSTOMER','1001'),
('40002', 'Iulian','Valsuian','0799998', 'iuli@gmail.com','$2a$05$GIny7PaavzM8fcWL3ewJDeEFvTzd/L4lty7XxxclNeiFLfopL3rq2','CUSTOMER','1002'),
('40003', 'Mihai','Dumitrescu','07949996', 'chef@gmail.com','$2a$05$GIny7PaavzM8fcWL3ewJDeEFvTzd/L4lty7XxxclNeiFLfopL3rq2','CHEF','2001'),
('40004', 'George','Paun','07999697', 'geopa@gmail.com','$2a$05$GIny7PaavzM8fcWL3ewJDeEFvTzd/L4lty7XxxclNeiFLfopL3rq2','CHEF','2002'),
('40005', 'Darius','Misoccko','07543125', 'misoko@gmail.com','$2a$05$GIny7PaavzM8fcWL3ewJDeEFvTzd/L4lty7XxxclNeiFLfopL3rq2','CHEF','2003'),
('40006', 'Marius','Lazar','07432497', 'gasd@gmail.com','$2a$05$GIny7PaavzM8fcWL3ewJDeEFvTzd/L4lty7XxxclNeiFLfopL3rq2','CHEF','2004'),
('40007', 'Valentin','Manea','0799995', 'rider@gmail.com','$2a$05$GIny7PaavzM8fcWL3ewJDeEFvTzd/L4lty7XxxclNeiFLfopL3rq2','RIDER','3001'),
('40008', 'Victor','Nazdravan','0799994', 'vinaz@gmail.com','$2a$05$GIny7PaavzM8fcWL3ewJDeEFvTzd/L4lty7XxxclNeiFLfopL3rq2','RIDER','3002'),
('40009', 'Petru','Las','07976534', 'lasp@gmail.com','$2a$05$GIny7PaavzM8fcWL3ewJDeEFvTzd/L4lty7XxxclNeiFLfopL3rq2','RIDER','3003');

insert into admins (id) values
('10');

insert into customers (id,street_address) values
('1001', 'Maciesilor 14'),
('1002', 'Trandafirilor 9');

insert into chefs (id,restaurant_id) values
('2001', '10000'),
('2002', '10000'),
('2003', '10001'),
('2004', '10002');

insert into riders (id,vehicle) values
('3001', 'Autoturism'),
('3002', 'Bicicleta'),
('3003', 'Scuter');
