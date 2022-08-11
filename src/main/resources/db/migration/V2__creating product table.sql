create table product
(
    id    bigserial
        constraint product_pk
            primary key,
    name  varchar(100) not null,
    price bigint       not null
);

create unique index product_name_uindex
    on product (name);

