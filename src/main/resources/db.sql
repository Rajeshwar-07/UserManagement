insert into country_master values(1,'India');
insert into country_master values(2,'USA');


insert into state_master(state_id,state_name,country_id) values(1,'MH',1);
insert into state_master(state_id,state_name,country_id) values(2,'TG',1);

insert into state_master(state_id,state_name,country_id) values(3,'RI',2);
insert into state_master(state_id,state_name,country_id) values(4,'NJ',2);


insert into city_entity(city_id,city_name,state_id) values(1,'Mumbai',1);
insert into city_entity(city_id,city_name,state_id) values(2,'Pune',1);
insert into city_entity(city_id,city_name,state_id) values(3,'Ongole',2);
insert into city_entity(city_id,city_name,state_id) values(4,'Guntur',2);
insert into city_entity(city_id,city_name,state_id) values(5,'Providence',3);
insert into city_entity(city_id,city_name,state_id) values(6,'New Port',3);
insert into city_entity(city_id,city_name,state_id) values(7,'Trenton',4);
insert into city_entity(city_id,city_name,state_id) values(8,'Newark',4);

