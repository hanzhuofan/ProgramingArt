CREATE SCHEMA IF NOT EXISTS spring_security;

CREATE TABLE IF NOT EXISTS spring_security.sys_user
(
    id       serial8       NOT NULL,
    username varchar(1024) NOT NULL unique,
    password varchar(1024) NOT NULL,
    CONSTRAINT sys_user_pkey PRIMARY KEY (id)
)
    WITH (
        OIDS= FALSE
    );