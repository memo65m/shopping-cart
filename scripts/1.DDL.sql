-- Create tables

CREATE TABLE tb_user (
    id INT PRIMARY KEY NOT NULL,
    username CHARACTER VARYING(50) NOT NULL,
    first_name CHARACTER VARYING(255) NOT NULL,
    last_name CHARACTER VARYING(255) NOT NULL
);

CREATE TABLE tb_product (
    id INT PRIMARY KEY NOT NULL,
    name CHARACTER VARYING(255) NOT NULL,
    unit_price double precision	 NOT NULL,
    stock INT NOT NULL
);

CREATE TABLE tb_coupon (
    id INT PRIMARY KEY NOT NULL,
    code CHARACTER VARYING(50) NOT NULL,
    discount_percentage INTEGER NOT NULL,
    start_date DATE NOT NULL,
    expiration_date DATE NOT NULL,
    beneficiary INTEGER NOT NULL
);

CREATE TABLE tb_discount (
    id INT PRIMARY KEY NOT NULL,
    product INTEGER NOT NULL,
    discount_percentage INTEGER NOT NULL,
    start_month INTEGER NOT NULL,
    expiration_month INTEGER NOT NULL
);

CREATE TABLE tb_cart (
    id INT PRIMARY KEY NOT NULL,
    user_creator INTEGER NOT NULL,
    coupon INTEGER NOT NULL
);

CREATE TABLE tb_cart_product (
    id INT PRIMARY KEY NOT NULL,
    cart INTEGER NOT NULL,
    product INTEGER NOT NULL,
    quantity INTEGER NOT NULL
);

-- Create constraints

ALTER TABLE ONLY tb_user
    ADD CONSTRAINT user_username_key UNIQUE (username);

ALTER TABLE ONLY tb_coupon
    ADD CONSTRAINT coupon_code_key UNIQUE (code);
ALTER TABLE ONLY tb_coupon
    ADD CONSTRAINT coupon_beneficiary_fkey FOREIGN KEY (beneficiary) REFERENCES tb_user(id) ON DELETE RESTRICT;

ALTER TABLE ONLY tb_discount
    ADD CONSTRAINT discount_product_fkey FOREIGN KEY (product) REFERENCES tb_product(id) ON DELETE RESTRICT;

ALTER TABLE ONLY tb_cart
    ADD CONSTRAINT cart_usercreator_fkey FOREIGN KEY (user_creator) REFERENCES tb_user(id) ON DELETE RESTRICT;
ALTER TABLE ONLY tb_cart
    ADD CONSTRAINT cart_coupon_fkey FOREIGN KEY (coupon) REFERENCES tb_coupon(id) ON DELETE RESTRICT;

ALTER TABLE ONLY tb_cart_product
    ADD CONSTRAINT cart_product_cart_fkey FOREIGN KEY (cart) REFERENCES tb_cart(id) ON DELETE RESTRICT;
ALTER TABLE ONLY tb_cart_product
    ADD CONSTRAINT cart_product_product_fkey FOREIGN KEY (product) REFERENCES tb_product(id) ON DELETE RESTRICT;

-- Create sequences

CREATE SEQUENCE user_id_seq 
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
ALTER SEQUENCE user_id_seq OWNED BY tb_user.id;
ALTER TABLE ONLY tb_user ALTER COLUMN id SET DEFAULT nextval('user_id_seq'::regclass);

CREATE SEQUENCE product_id_seq 
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
ALTER SEQUENCE product_id_seq OWNED BY tb_product.id;
ALTER TABLE ONLY tb_product ALTER COLUMN id SET DEFAULT nextval('product_id_seq'::regclass);

CREATE SEQUENCE coupon_id_seq 
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
ALTER SEQUENCE coupon_id_seq OWNED BY tb_coupon.id;
ALTER TABLE ONLY tb_coupon ALTER COLUMN id SET DEFAULT nextval('coupon_id_seq'::regclass);

CREATE SEQUENCE discount_id_seq 
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
ALTER SEQUENCE discount_id_seq OWNED BY tb_discount.id;
ALTER TABLE ONLY tb_discount ALTER COLUMN id SET DEFAULT nextval('discount_id_seq'::regclass);

CREATE SEQUENCE cart_id_seq 
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
ALTER SEQUENCE cart_id_seq OWNED BY tb_cart.id;
ALTER TABLE ONLY tb_cart ALTER COLUMN id SET DEFAULT nextval('cart_id_seq'::regclass);

CREATE SEQUENCE cart_product_id_seq 
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
ALTER SEQUENCE cart_product_id_seq OWNED BY tb_cart_product.id;
ALTER TABLE ONLY tb_cart_product ALTER COLUMN id SET DEFAULT nextval('cart_product_id_seq'::regclass);