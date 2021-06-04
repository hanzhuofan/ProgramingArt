CREATE SCHEMA IF NOT EXISTS spring_security;

CREATE TABLE IF NOT EXISTS spring_security.sys_user
(
    id       serial8       NOT NULL,
    username varchar(1024) NOT NULL unique,
    encode_password varchar(1024) NOT NULL,
    age int4 NOT NULL,
    CONSTRAINT sys_user_pkey PRIMARY KEY (id)
)
    WITH (
        OIDS= FALSE
    );

insert into spring_security.sys_user
values (1, 'admin', '{noop}abc123', 18);
