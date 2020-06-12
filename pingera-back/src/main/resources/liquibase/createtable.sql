CREATE TABLE IF NOT EXISTS users
(
    id integer NOT NULL,
    name character varying(255),
    email character varying(255),
    password character varying(255),
    CONSTRAINT users_pkey PRIMARY KEY (id)
);



