insert into delivery_area (area_id,latitude,longitude,radius) values
('20000', 11.0, 11.0, 4300),
('20001', 10.0, 10.0, 6800),
('20002', 12.0, 12.0, 6800),
('20003', 11.0, 11.0, null),
('20004', 11.5, 11.0, null),
('20005', 12.0, 11.5, null),
('20006', 11.0, 11.0, null),
('20007', 11.01, 11.0, null);

insert into restaurant(restaurant_id,restaurant_name,delivery_area_id) values
('10000', 'Ciucini', '20000'),
('10001', 'Vitara', '20001'),
('10002', 'Republica', '20002');

insert into food (food_id,food_name,preparation_minutes,description,weight_grams,picture_url,restaurant_id) values
('30000', 'Salata Cesar Cu Pui', 20, 'Salata', 350, 'http://www.abc.com','10000'),
('30001', 'Tagliotini cu pesto', 25, 'Paste', 250, 'http://www.abc.com','10000'),
('30002', 'Supa Luscos', 10, 'Supa', 300,'http://www.abc.com','10000'),
('30003', 'Ricota Brownie', 0, 'Desert', 300, 'http://www.abc.com','10001'),
('30004', 'Fetucini', 8, 'Paste', 280, 'http://www.abc.com','10001'),
('30005', 'Nopolitana rucola', 5, 'Pizza', 420, 'http://www.abc.com','10002'),
('30006', 'Capricoasa', 5, 'Pizza', 400, 'http://www.abc.com','10002'),
('30007', 'Bismark', 5, 'Pizza', 380, 'http://www.abc.com','10002');

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

insert into riders (id,vehicle,delivery_area_id) values
('3001', 'Autoturism','20005'),
('3002', 'Bicicleta','20006'),
('3003', 'Scuter','20007');

insert into orders (order_id,created_customer_id,assigned_chef_id,assigned_rider_id,delivery_estimation_timestamp,delivery_area_id,restaurant_id,order_status) values
('70001','1001','2001','3001',1672143200000,'20007','10000','IN_DELIVERY'),
('70002','1002','2002','3002',1672143200000,'20004','10000','IN_PREPARATION');

insert into order_item(order_item_id,food_id,order_id,amount) values
('90000','30000','70001',2),
('90001','30001','70001',2),
('90002','30002','70001',2),
('90003','30000','70002',2),
('90004','30001','70002',3),
('90005','30002','70002',1);