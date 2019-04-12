create table application
(
  id         bigint not null auto_increment,
  date       date   not null,
  user_id    bigint,
  vacancy_id bigint,
  primary key (id)
);

create table department
(
  id              bigint       not null auto_increment,
  department_name varchar(255) not null,
  primary key (id)
);

create table head_hunter
(
  id            bigint not null auto_increment,
  department_id bigint,
  user_id       bigint,
  primary key (id)
);

create table hibernate_sequence
(
  next_val bigint
);
insert into hibernate_sequence
values (1);

create table user
(
  id              bigint       not null auto_increment,
  username        varchar(255) not null,
  password        varchar(255) not null,
  first_name      varchar(255),
  last_name       varchar(255),
  email           varchar(255),
  phone           varchar(20),
  education       varchar(255),
  experience      float,
  activation_code varchar(255),
  active          bit,
  head_hunter_id  bigint,
  primary key (id)
);

create table user_role
(
  user_id bigint,
  roles   varchar(255)
);

create table vacancy
(
  id            bigint       not null auto_increment,
  description   varchar(512),
  salary        float        not null,
  vacancy_name  varchar(255) not null,
  department_id bigint,
  primary key (id)
);

alter table application
  add constraint application_user_fk
    foreign key (user_id) references user (id);

alter table application
  add constraint application_vacancy_fk
    foreign key (vacancy_id) references vacancy (id);

alter table head_hunter
  add constraint headHunter_department_fk
    foreign key (department_id) references department (id);

alter table head_hunter
  add constraint headHunter_user_fk
    foreign key (user_id) references user (id);

alter table user
  add constraint user_headHunter_fk
    foreign key (head_hunter_id) references head_hunter (id);

alter table user_role
  add constraint userRole_user_fk
    foreign key (user_id) references user (id);

alter table vacancy
  add constraint vacancy_department_fk
    foreign key (department_id) references department (id);