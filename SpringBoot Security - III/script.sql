select * from accounts;
select * from customer;
insert into accounts (create_dt,account_number,customer_id,account_type,branch_address)
values (CURRENT_DATE(),123456,1,"saving","Mumbai");
insert into customer(create_dt,customer_id,email,mobile_number,name,pwd,role)
values (current_date(),1,"happy@example.com",9999999999,"Example","111","USER");

update customer
set pwd="{noop}111"
where customer_id=1;

select * from authorities;
truncate table authorities;
insert into authorities(customer_id,id,name) values (1,1,"ROLE_USER");