create table users(username varchar(50) not null primary key,password varchar(500) not null,enabled boolean not null);
create table authorities (username varchar(50) not null,authority varchar(50) not null,constraint fk_authorities_users foreign key(username) references users(username));
create unique index ix_auth_username on authorities (username,authority);

INSERT INTO `users` VALUES ('user3', '{noop}u$er3P@ss123', '1');
INSERT INTO `authorities` VALUES ('user3', 'read');

INSERT INTO `users` VALUES ('user4', '{bcrypt}$2a$12$u9OWWnVS0oAil3235w60eejI93UKXoz7vRm4Rpj30KIdOZKRkICxi', '1');
INSERT INTO `authorities` VALUES ('user4', 'admin');

select * from users;
select * from authorities;
select * from customer_table;
select * from customer_table_seq;
select * from customer_authorities;

/*
Password -> codophillic@123
Bcrypt value -> $2a$12$eKPg4/gKmdNjrCuorO/KKuvFnTXLP162Ii/Ze51PXUCf.dBdDQtOC
*/

insert into customer_table(id,customer_active,customer_emailid,customer_password,customer_role)
values (2,"yes","abc@gmail.com", "$2a$12$eKPg4/gKmdNjrCuorO/KKuvFnTXLP162Ii/Ze51PXUCf.dBdDQtOC","ROLE_ADMIN");

insert into customer_authorities(foreignkey_cust_id,uniqueid,authority) values(2,1,"VIEWACCOUNTS");
insert into customer_authorities(foreignkey_cust_id,uniqueid,authority) values(2,2,"VIEWBALANCE");
insert into customer_authorities(foreignkey_cust_id,uniqueid,authority) values(2,3,"UPDATEBALANCE");
