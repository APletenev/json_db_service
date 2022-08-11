create table customer
(
    id         bigserial
        constraint customer_pk
            primary key,
    last_name  varchar(15) not null,
    first_name varchar(15) not null
);