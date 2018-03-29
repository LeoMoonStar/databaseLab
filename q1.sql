set search_path to carschema;
drop table if exists q1 cascade;

/* find the top two customer with higest cancel ratio*/

create TABLE q1(
    Name VARCHAR(50),
    Email VARCHAR(50),
    cancel_ratio float
);

/* get the client email , reserveration id and status*/
create view client_res as 
select RESERVATION.STATUS, RESERVATION.ID,CUSTOMER_RESERVATION.Email
From RESERVATION join CUSTOMER_RESERVATION on CURSTOMER_RESERVATION.RESERVATION_ID=RESERVATION.ID;


/* get status=cancelled*/
create view cancelled_res as 
select Email,count(*) as cancel_num
from client_res c1 where RESERVATION.Status='Canacelled' and not exists(
    select * from client_res c2 where c2.Old_Reservation_ID =c1.ID
)
group by Email;

create view completed_res as 
select Email,count(*) as completed_num
from client_res where RESERVATION.Status!='Canacelled'
group by Email;


create view cancel_ratio as
  select  Email, case when (completed_res is null) then cancelled_res
  when (cancelled_res is null) then 0
  else cast(cancelled_res as float)/cast(completed_res as float) end as ratio
  from cancelled_res natural full join completed_res
  order by ratio desc;


create view all_cancel_ratio as 
  select Customer.email, 
  case when(select count(*) from cancel_ratio where cancel_ratio.Email = customer.Email) = 0 
  then 0 else cancel_ratio.ratio end as ratio
  from Customer natural full join cancel_ratio;

create view cancel_with_rank as select Email, ratio, 
  row_number() over(order by ratio desc, Email) as rank
  from all_cancel_ratio;

create view result1 as select Email, ratio 
  from cancel_with_rank where rank = 1 or rank = 2;

select * from result1;








