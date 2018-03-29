/*     
1.
2.
*/

drop schema if exists carschema cascade;
create schema carschema;
set search_path to carschema;

create type Reservation_status as ENUM(
    'Ongoing','Completed','Cancelled','Confirmed'
);

create table Customer(
    Name varchar(50) not null,
    Age INT not null
    check (age>=18),
    Email vchar(50) primary key
);

create table Model(
    ID INT primary key,
    Name varchar(50) not null unique,
    Vechical_Type varchar(50) not null,
    Model_number INT unique,
    capacity INT ,
    check(capacity>=2),
);

create table CUSTOMER_RESERVATION(
    Customer_Email vchar(50) reference Customer(Email),
    Reservation_ID INT reference RESERVATION(ID);
)

create table RESERVATION(
    ID INT primary key,
    From_Date timestamp NOT NULL,
    To_Date timestamp NOT NULL,
    Car_ID INT reference CAR(ID) NOT NULL,
    Old_Reservation_ID INT reference RESERVATION(ID) default NULL,
    Status Reservation_status not null,
    check ((data_part('day',To_Date-From_Date)*24+
            date_part('day',To_Data-From_Date))>=2)
);

create table CAR(
    ID INT primary key,
    License_Plate_Number varchar(50) unique not null,
    Station_Code INT reference RENTALSTATION(Station_Code) unique,
    Model_id  reference Model(ID)
); 

create table Rentalstation(
    Station_Code INT primary key,
    Name varchar(100),
    Address varchar(100),
    Area_Code varchar(100),
    City varchar(50)
);