set search_path to carschema;
drop table if exists q3 cascade;
/* Find the most frequently rented car model in Toronto */
create table q3(name VARCHAR(50));


create view Toronto_17_res as
  select RESERVATION.ID as res_id, RESERVATION.Car_ID, Car.Model_id
  from RESERVATION join Car on RESERVATION.Car_ID = car.ID
  join Rentalstation on Car.Station_Code = Rentalstation.Area_Code
  where Rentalstation.City = 'Toronto' and
   extract(year from From_Date) = 2017
   and extract(year from To_Date) = 2017
   and RESERVATION.Status = 'Completed';


create view num_models as
  select Model_id, count(*)
  from Toronto_17_res
  group by Model_id;


create view popular_models as
  select Model.Name, count
  from num_models join Model on num_models.Model_id = Model.ID;


create view model_ranks as
  select name, count, row_number() over (order by count desc, name) as rank
  from popular_models;


create view result3 as
  select name
  from model_ranks
  where rank = 1;

select * from result3;
