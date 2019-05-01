create table application
(
    id            bigint not null auto_increment,
    date_time     datetime(6),
    user_id       bigint,
    vacancy_id    bigint,
    department_id bigint,
    interview_id  bigint,
    primary key (id)
);

create table department
(
    id              bigint not null auto_increment,
    department_name varchar(255),
    primary key (id)
);

create table interview
(
    id             bigint not null auto_increment,
    date_time      datetime(6),
    department_id  bigint,
    head_hunter_id bigint,
    candidate_id   bigint,
    primary key (id)
);

create table archive
(
    id             bigint not null auto_increment,
    date_time      datetime(6),
    is_success     bit,
    result         varchar(255),
    candidate_id   bigint,
    head_hunter_id bigint,
    vacancy_id     bigint,
    primary key (id)
);

create table successful_candidate
(
    id         bigint not null auto_increment,
    date_time  datetime(6),
    user_id    bigint,
    vacancy_id bigint,
    primary key (id)
);

create table failed_candidate
(
    id         bigint not null auto_increment,
    date_time  datetime(6),
    user_id    bigint,
    vacancy_id bigint,
    primary key (id)
);

create table user
(
    id              bigint not null auto_increment,
    username        varchar(255),
    password        varchar(255),
    email           varchar(255),
    first_name      varchar(255),
    last_name       varchar(255),
    phone           varchar(255),
    education       varchar(255),
    experience      varchar(255),
    activation_code varchar(255),
    active          bit    not null,
    department_id   bigint,
    primary key (id)
);

create table user_role
(
    user_id bigint not null,
    roles   varchar(255)
);

create table vacancy
(
    id            bigint not null auto_increment,
    description   varchar(255),
    salary        float,
    vacancy_name  varchar(255),
    department_id bigint,
    primary key (id)
);

alter table application
  add constraint application_user_fk
    foreign key (user_id) references user (id);

alter table application
    add constraint application_interview_fk
        foreign key (interview_id) references interview (id);

alter table application
  add constraint application_vacancy_fk
    foreign key (vacancy_id) references vacancy (id);

alter table application
    add constraint application_department_fk
        foreign key (department_id) references department (id);

alter table user
    add constraint user_department_fk
        foreign key (department_id) references department (id);

alter table user_role
  add constraint userRole_user_fk
    foreign key (user_id) references user (id);

alter table vacancy
  add constraint vacancy_department_fk
    foreign key (department_id) references department (id);

alter table interview
    add constraint interview_department_fk
        foreign key (department_id) references department (id);

alter table interview
    add constraint interview_headHunter_fk
        foreign key (head_hunter_id) references user (id);

alter table interview
    add constraint interview_candidate_fk
        foreign key (candidate_id) references user (id);

alter table archive
    add constraint archive_candidate
        foreign key (candidate_id) references user (id);

alter table archive
    add constraint archive_headHunter
        foreign key (head_hunter_id) references user (id);

alter table archive
    add constraint archive_vacancy
        foreign key (vacancy_id) references vacancy (id);

alter table failed_candidate
    add constraint failed_candidate_user
        foreign key (user_id) references user (id);

alter table failed_candidate
    add constraint failed_candidate_vacancy
        foreign key (vacancy_id) references vacancy (id);

alter table successful_candidate
    add constraint successful_candidate_user
        foreign key (user_id) references user (id);

alter table successful_candidate
    add constraint successful_candidate_vacancy
        foreign key (vacancy_id) references vacancy (id);