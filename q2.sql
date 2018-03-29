set search_path to carschema;
drop table if exists q2 cascade;

create table q2(
  email VARCHAR(50),
  num_shared_reservations INT
);


/* find top two user who rent car most offen

*/


create view complete_res as
select RESERVATION.ID,Email,STATUS
from RESERVATION join CUSTOMER_RESERVATION 
on CUSTOMER_RESERVATION.ID=RESERVATION.ID
where status!='Cancelled';

create view single_res as select * from  complete_res c1
where not exists(select *from complete_res c2
where c1.ID=c2.ID and c1.Email!=c2.Email);

create view mulitiple_res as(select * from complete_res) except 
select* from single_res;

create view multiple_count as 
select Email, count(*) from mulitiple_res group by Email;

create view multiple_rank as select Email,count,row_number() over(
order by count desc,Email) from multiple_count;

create view result2 as
select Email,count as shared_reservation from multiple_rank where rank =1 or rank = 2
order by shared_reservation desc,Email;

select * from result2; 
