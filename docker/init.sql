CREATE TABLE IF NOT EXISTS public.t_drone
(
    serial_number VARCHAR(100) NOT NULL,
    model VARCHAR(50)  NOT NULL,
    weight_limit VARCHAR(5) NOT NULL,
    battery_capacity NUMERIC(3,2),
    state numeric(1) NOT NULL,
    CONSTRAINT t_drone_pkey PRIMARY KEY (serial_number)
)


TABLESPACE pg_default;

ALTER TABLE public.t_drone
    OWNER to postgres;


CREATE TABLE IF NOT EXISTS public.t_medication
(
    code VARCHAR(50) NOT NULL,
    name VARCHAR(50) NOT NULL,
    weight VARCHAR(10) NOT NULL,
    image text,
    CONSTRAINT t_medication_pkey PRIMARY KEY (code)
)

TABLESPACE pg_default;

ALTER TABLE public.t_medication
    OWNER to postgres;


CREATE TABLE IF NOT EXISTS public.t_medication_load_details
(
    id_load_details int4 NOT NULL generated by default as identity,
    start_point VARCHAR(50) NOT NULL,
    end_point VARCHAR(50) NOT NULL,
    time_to_load TIMESTAMP NOT NULL,
    fk_serial_number VARCHAR(100),
    fk_code VARCHAR(50),
    CONSTRAINT t_medication_load_details_pkey PRIMARY KEY (id_load_details),
    CONSTRAINT t_medication_code_fkey FOREIGN KEY (fk_code)
    REFERENCES public.t_medication (code),
    CONSTRAINT t_drone_serial_number_fkey FOREIGN KEY (fk_serial_number)
    REFERENCES public.t_drone (serial_number)
    )

TABLESPACE pg_default;

ALTER TABLE public.t_medication_load_details
    OWNER to postgres;



CREATE TABLE IF NOT EXISTS public.t_medication_delivery_details
(
    id_delivery_details int4 NOT NULL generated by default as identity,
    delivered_time TIMESTAMP NOT NULL,
    fk_id_load_details int4 NOT NULL,
    CONSTRAINT t_medication_delivery_details_pkey PRIMARY KEY (id_delivery_details),
    CONSTRAINT t_medication_load_details_fkey FOREIGN KEY (fk_id_load_details)
        REFERENCES public.t_medication_load_details (id_load_details)
)


TABLESPACE pg_default;

ALTER TABLE public.t_medication_delivery_details
    OWNER to postgres;


CREATE TABLE IF NOT EXISTS public.t_state_dict
(
    id_state NUMERIC NOT NULL,
    state_name varchar(20) NOT NULL,
    CONSTRAINT t_state_dict_pkey PRIMARY KEY (id_state)
)


TABLESPACE pg_default;

ALTER TABLE public.t_state_dict
    OWNER to postgres;


create index t_drone_state_idx on public.t_drone using btree (state);

create sequence t_medication_load_details_seq
    increment by 1
    minvalue 1
    maxvalue 2147483647
    start 1
    cache 1
    no cycle;




create sequence t_medication_delivery_details_seq
    increment by 1
    minvalue 1
    maxvalue 2147483647
    start 1
    cache 1
    no cycle;