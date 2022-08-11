create table purchase
(
    id bigserial constraint purchase_pk primary key,
    customer_id bigserial not null,
    product_id bigserial not null,
    date date NOT NULL,
    foreign key (customer_id) references customer (id),
    foreign key (product_id) references product (id)
);
