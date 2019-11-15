create table product (
    id  bigserial not null,
    date date,
    description varchar(1024),
    filename varchar(255),
    name varchar(255),
    price numeric(19, 2),
    weight float8,
    primary key (id)
);

create table product_type (
    product_id int8 not null,
    types varchar(255)
);

create table user_role (
    user_id int8 not null,
    roles varchar(255)
);

create table usr (
    id  bigserial not null,
    activation_code varchar(255),
    active boolean not null,
    email varchar(255),
    first_name varchar(255),
    last_name varchar(255),
    password varchar(255),
    phone varchar(255),
    username varchar(255),
    primary key (id)
);

alter table if exists product_type
    add constraint productType_fk_product
    foreign key (product_id) references product;

alter table if exists user_role
    add constraint userRole_fk_user
    foreign key (user_id) references usr;