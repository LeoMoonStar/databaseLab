set search_path to carschema;
drop table if exists q4 cascade;

/*Find the list of all customers younger than 30 years old 
who changed at least 2 reservations in the past 18 months.*/

create view yonge_res as 
select Customer.Name, Customer.Age, Customer.Email, Reservation_ID 
from Customer join CUSTOMER_RESERVATION on Customer.Email=CUSTOMER_RESERVATION.Email
where Customer.Age<30;


create view full_res as 
select * from RESERVATION 
JOIN CUSTOMER_RESERVATION on CUSTOMER_RESERVATION.Reservation_ID=RESERVATION.ID
;



create view user_change_res as
select count(*) as num_change, Customer_Email from full_res
where Old_Reservation_ID !=null
group by Customer_Email;


create view yonge_change_user as 
select yonge_res.Name,yonge_res.Email,user_change_res.num_change
from yonge_res join user_change_res on yonge_res.Email=user_change_res.Customer_Email
where user_change_res.num_change>=2;

