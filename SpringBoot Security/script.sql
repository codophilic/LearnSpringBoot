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