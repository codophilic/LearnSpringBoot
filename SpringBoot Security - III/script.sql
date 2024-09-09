select * from accounts;
select * from customer;
insert into accounts (create_dt,account_number,customer_id,account_type,branch_address)
values (CURRENT_DATE(),123456,1,"saving","Mumbai");
insert into customer(create_dt,customer_id,email,mobile_number,name,pwd,role)
values (current_date(),1,"happy@example.com",9999999999,"Example","111","USER");