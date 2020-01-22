DROP TABLE IF EXISTS addresses;
CREATE TABLE addresses
(
    id bigint NOT NULL AUTO_INCREMENT,
    city character varying(20) NOT NULL,
    street character varying(20) NOT NULL,
    home integer NOT NULL,
    flat integer,
    CONSTRAINT addresses_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS accounts;
CREATE TABLE accounts
(
    id bigint NOT NULL AUTO_INCREMENT,
    login character varying(20) NOT NULL,
    password character varying(20) NOT NULL,
    role character varying(10) NOT NULL,
    CONSTRAINT accounts_pkey PRIMARY KEY (id),
    CONSTRAINT account_login_unique UNIQUE (login)
);

DROP TABLE IF EXISTS users;
CREATE TABLE users
(
    id bigint NOT NULL AUTO_INCREMENT,
    firstname character varying(40) NOT NULL,
    lastname character varying(40) NOT NULL,
    phone character varying(15) NOT NULL,
    email character varying(40),
    address_id bigint NOT NULL,
    CONSTRAINT users_pkey PRIMARY KEY (id),
    CONSTRAINT user_account_id_fk 
        FOREIGN KEY (id) REFERENCES accounts (id),
    CONSTRAINT user_address_id_fk 
        FOREIGN KEY (address_id) REFERENCES addresses (id)
);

DROP TABLE IF EXISTS users_fines;
CREATE TABLE users_fines
(
    id bigint NOT NULL AUTO_INCREMENT,
    description character varying(100) NOT NULL,
    bill numeric(8,2) NOT NULL,
    user_id bigint NOT NULL,
    date timestamp NOT NULL,
    CONSTRAINT users_fines_pkey PRIMARY KEY (id),
    CONSTRAINT user_fines_user_id_fk 
        FOREIGN KEY (user_id) REFERENCES users (id)
);
 
DROP TABLE IF EXISTS engine;
CREATE TABLE engine
(
    id bigint NOT NULL AUTO_INCREMENT,
    volume numeric(2,1) NOT NULL,
    fuel character varying(10) NOT NULL,
    fuel_economy character varying(10) NOT NULL,
    CONSTRAINT engine_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS car_model;
CREATE TABLE car_model
(
    id bigint NOT NULL AUTO_INCREMENT,
    name character varying(20) NOT NULL,
    year integer,
    CONSTRAINT car_model_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS car;
CREATE TABLE car
(
    id bigint NOT NULL AUTO_INCREMENT,
    gearbox character varying(15) NOT NULL,
    drive character varying(15) NOT NULL,
    body character varying(15) NOT NULL,
    seats integer NOT NULL,
    model_id bigint NOT NULL,
    engine_id bigint NOT NULL,
    CONSTRAINT car_pkey PRIMARY KEY (id),
    CONSTRAINT car_car_model_id_fk 
        FOREIGN KEY (model_id) REFERENCES car_model (id),
    CONSTRAINT car_engine_id_fk 
        FOREIGN KEY (engine_id) REFERENCES engine (id)
);

DROP TABLE IF EXISTS car_info;
CREATE TABLE car_info
(
    id bigint NOT NULL AUTO_INCREMENT,
    car_id bigint NOT NULL,
    status boolean NOT NULL,
    hour_price numeric(5,2) NOT NULL,
    CONSTRAINT car_info_pkey PRIMARY KEY (id),
    CONSTRAINT car_info_car_id_fk 
        FOREIGN KEY (car_id) REFERENCES car (id)
);

DROP TABLE IF EXISTS rent_car;
CREATE TABLE rent_car
(
    id bigint NOT NULL AUTO_INCREMENT,
    start_date timestamp NOT NULL,
    finish_date timestamp NOT NULL,
    user_id bigint NOT NULL,
    car_id bigint NOT NULL,
    status character varying(10) NOT NULL,
    CONSTRAINT rent_car_pkey PRIMARY KEY (id),
    CONSTRAINT rent_car_car_id 
        FOREIGN KEY (car_id) REFERENCES car (id),
    CONSTRAINT rent_car_user_if_fk 
        FOREIGN KEY (user_id) REFERENCES users (id)
);

INSERT INTO accounts (id, login, password, role) VALUES (1, 'admin', 'admin', 'ROLE_ADMIN');