CREATE TABLE `fbconnectiontokens` (
  `user_id` int(50) NOT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `access_token` varchar(255) DEFAULT NULL,
  `long_lived_token` varchar(255) DEFAULT NULL,
  `expiry_time` int(11) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) 

//for postgre
CREATE TABLE public.fbconnections
(
    user_id integer NOT NULL,
    expires_in bigint,
    long_token character varying(250) COLLATE pg_catalog."default",
    short_token character varying(250) COLLATE pg_catalog."default",
    user_name character varying(100) COLLATE pg_catalog."default",
    CONSTRAINT fbconnections_pkey PRIMARY KEY (user_id)
)

TABLESPACE pg_default;

ALTER TABLE public.fbconnections
    OWNER to postgres;