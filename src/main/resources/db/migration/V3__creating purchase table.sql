create table purchase
(
    id   bigserial
        constraint purchase_pk
            primary key
        constraint purchase_customer_id_fk
            references customer
        constraint purchase_product_id_fk
            references product,
    date date not null
);

