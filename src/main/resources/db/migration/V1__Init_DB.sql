create sequence hibernate_sequence start 1 increment 1;

create table app_user
(
    id                      serial       not null,
    birth_date              timestamp,
    email                   varchar(255) not null,
    email_confirm_uuid      varchar(255),
    enabled                 boolean,
    first_name              varchar(50),
    last_name               varchar(50),
    locked                  boolean,
    password                varchar(255),
    password_recovery_token varchar(255),
    username                varchar(30)  not null,
    primary key (id)
);
create table app_user_app_user_roles
(
    app_user_id    int4 not null,
    app_user_roles int4
);

create table post
(
    id      int8 not null,
    created timestamp,
    text    varchar(255),
    user_id int4 not null,
    primary key (id)
);

create table refresh_token
(
    id         bigserial    not null,
    uuid       varchar(255) not null,
    created    timestamp,
    expires_in int8,
    ip         varchar(255),
    user_agent varchar(255),
    user_id    int4         not null,
    primary key (id)
);

alter table app_user
    add constraint email_unique
        unique (email);

alter table app_user
    add constraint username_unique
        unique (username);

alter table app_user_app_user_roles
    add constraint app_user_fk1
        foreign key (app_user_id) references app_user;

alter table post
    add constraint app_user_fk2
        foreign key (user_id) references app_user;

alter table refresh_token
    add constraint app_user_fk3
        foreign key (user_id) references app_user;
