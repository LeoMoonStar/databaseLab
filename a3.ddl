/*     
1.
2.
*/


drop schema if exists carschema cascade;
create schema carschema;
set search_path to carschema;

create table Customer(
    Name varchar(25),
    Age integer,
    Email vchar(25) primary key;
)

create table Model(
    ID integer primary key,
    Name varchar(25),
    Vechical Type varchar(25),
    Model number integer reference Model(),
)
create table CUSTOMER_RESERVATION(
    Customer Email vchar(25) reference Customer(Email),
    Reservation ID integer reference RESERVATION(ID);
)

create table RESERVATION(
    ID integer primary key,
    From Date timestamp,
    To Date timestamp,
    Car ID integer reference CAR(ID),
    Old Reservation ID integer reference RESERVATION(ID),
    Status varchar(25);
)

create table CAR(
    ID integer primary key,
    License Plate Number varchar(25) unique,
    Station Code integer reference RENTALSTATION(Station Code),
    Model_id integer reference Model(ID);
) 

create table RENTALSTATION(
    Station Code integer primary key,
    Name text,
    Address varchar(25),
    Area Code varchar(25),
    City varchar(25);
)