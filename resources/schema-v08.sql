
CREATE SEQUENCE public.system_user_id_seq;

CREATE TABLE public.system_user (
                id BIGINT NOT NULL DEFAULT nextval('public.system_user_id_seq'),
                username VARCHAR(10) NOT NULL,
                password VARCHAR(50) NOT NULL,
                CONSTRAINT system_user_pk PRIMARY KEY (id)
);


ALTER SEQUENCE public.system_user_id_seq OWNED BY public.system_user.id;

CREATE SEQUENCE public.item_id_seq;

CREATE TABLE public.item (
                id BIGINT NOT NULL DEFAULT nextval('public.item_id_seq'),
                description VARCHAR(200) NOT NULL,
                code VARCHAR(10) NOT NULL,
                barcode VARCHAR(20),
                minimun_stock INTEGER DEFAULT 0,
                CONSTRAINT item_pk PRIMARY KEY (id)
);


ALTER SEQUENCE public.item_id_seq OWNED BY public.item.id;

CREATE INDEX idx_asc_item_description
 ON public.item
 ( description ASC );

CREATE SEQUENCE public.supplier_id_seq;

CREATE TABLE public.supplier (
                id BIGINT NOT NULL DEFAULT nextval('public.supplier_id_seq'),
                name VARCHAR(100) NOT NULL,
                ruc VARCHAR(20),
                address VARCHAR(200),
                telephone VARCHAR(20),
                contact_name VARCHAR(50),
                CONSTRAINT supplier_pk PRIMARY KEY (id)
);


ALTER SEQUENCE public.supplier_id_seq OWNED BY public.supplier.id;

CREATE INDEX idx_asc_supplier_name
 ON public.supplier
 ( name ASC );

CREATE SEQUENCE public.invoice_id_seq;

CREATE TABLE public.invoice (
                id BIGINT NOT NULL DEFAULT nextval('public.invoice_id_seq'),
                invoice_date DATE NOT NULL,
                number VARCHAR(50) NOT NULL,
                total_amount NUMERIC(8,2) NOT NULL,
                total_iva05 NUMERIC(8,2) NOT NULL,
                total_iva10 NUMERIC(8,2) NOT NULL,
                total_exempt NUMERIC(8,2) NOT NULL,
                processed BOOLEAN DEFAULT false NOT NULL,
                supplier BIGINT NOT NULL,
                CONSTRAINT invoice_pk PRIMARY KEY (id)
);


ALTER SEQUENCE public.invoice_id_seq OWNED BY public.invoice.id;

CREATE INDEX idx_asc_invoice_number
 ON public.invoice
 ( number ASC );

CREATE SEQUENCE public.invoice_detail_id_seq;

CREATE TABLE public.invoice_detail (
                id BIGINT NOT NULL DEFAULT nextval('public.invoice_detail_id_seq'),
                invoice BIGINT NOT NULL,
                item BIGINT NOT NULL,
                amount INTEGER NOT NULL,
                unit_price NUMERIC(8,2) NOT NULL,
                iva_05 NUMERIC(8,2),
                iva_10 NUMERIC(8,2),
                exempt NUMERIC(8,2),
                CONSTRAINT invoice_detail_pk PRIMARY KEY (id)
);


ALTER SEQUENCE public.invoice_detail_id_seq OWNED BY public.invoice_detail.id;

ALTER TABLE public.invoice_detail ADD CONSTRAINT item_invoice_detail_fk
FOREIGN KEY (item)
REFERENCES public.item (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.invoice ADD CONSTRAINT supplier_invoice_fk
FOREIGN KEY (supplier)
REFERENCES public.supplier (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.invoice_detail ADD CONSTRAINT invoice_invoice_detail_fk
FOREIGN KEY (invoice)
REFERENCES public.invoice (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;
