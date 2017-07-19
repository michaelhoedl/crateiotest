
drop table orderline ;
drop table orders ;
drop table shopuser ;
drop table person ;
drop table product ;
drop table creditcard ;


create table creditcard (
    creditcard_id   integer PRIMARY KEY,
    cardname        string not null,
    cardnumber      string not null,
    expiry_date     timestamp
);

create table product (
    product_id integer PRIMARY KEY,
    product_name string,
    product_description string,
    price float,
    in_stock float
);


create table person (
    person_id   integer PRIMARY KEY,
    firstname   string not null,
    lastname    string not null,
    birthdate   timestamp,
    phone       string,
    country     string,
    city        string,
    zip         string,
    street      string,
    creditcard_id   integer
);

create table shopuser (
    username        string PRIMARY KEY,
    email           string not null,
    pwd             string not null,
    created_date    timestamp,
    locked_date     timestamp,
    person_id       integer
);



create table orders (
    order_id integer PRIMARY KEY,
    order_date timestamp,
    username string
);

create table orderline (
    orderline_id integer PRIMARY KEY,
    order_id integer,
    product_id integer,
    amount float
);

