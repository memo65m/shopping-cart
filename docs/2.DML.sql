-- Insert data

INSERT INTO public.tb_user (id, username, first_name, last_name) VALUES (1, 'john_doe', 'John', 'Doe');
INSERT INTO public.tb_user (id, username, first_name, last_name) VALUES (2, 'will_smith', 'Will', 'Smith');

INSERT INTO public.tb_product (id, name, unit_price, stock) VALUES (1, 'Apple', 1.0, 100);
INSERT INTO public.tb_product (id, name, unit_price, stock) VALUES (2, 'Banana', 0.5, 200);
INSERT INTO public.tb_product (id, name, unit_price, stock) VALUES (3, 'Orange', 1.5, 150);
INSERT INTO public.tb_product (id, name, unit_price, stock) VALUES (4, 'Grape', 2.0, 120);
INSERT INTO public.tb_product (id, name, unit_price, stock) VALUES (5, 'Watermelon', 5.0, 50);
INSERT INTO public.tb_product (id, name, unit_price, stock) VALUES (6, 'Pineapple', 3.0, 80);
INSERT INTO public.tb_product (id, name, unit_price, stock) VALUES (7, 'Strawberry', 4.0, 100);

INSERT INTO public.tb_coupon (id, code, discount_percentage, start_date, expiration_date, beneficiary) VALUES (1, 'SUMMER2024', 2, '2024-06-01', '2024-12-31', 1);

INSERT INTO public.tb_discount (id, product, discount_percentage, start_month, expiration_month) VALUES (1, 1, 5, 1, 6);
INSERT INTO public.tb_discount (id, product, discount_percentage, start_month, expiration_month) VALUES (2, 2, 15, 7, 12);

INSERT INTO public.tb_cart (id, user_creator, coupon) VALUES (1, 1, NULL);

INSERT INTO public.tb_cart_product (id, cart, product, quantity) VALUES (1, 1, 1, 10);
INSERT INTO public.tb_cart_product (id, cart, product, quantity) VALUES (2, 1, 2, 20);
INSERT INTO public.tb_cart_product (id, cart, product, quantity) VALUES (3, 1, 3, 30);
INSERT INTO public.tb_cart_product (id, cart, product, quantity) VALUES (4, 1, 4, 40);

-- Reset sequences

SELECT pg_catalog.setval('user_id_seq', 2, true);
SELECT pg_catalog.setval('product_id_seq', 7, true);
SELECT pg_catalog.setval('coupon_id_seq', 1, true);
SELECT pg_catalog.setval('discount_id_seq', 2, true);
SELECT pg_catalog.setval('cart_id_seq', 1, true);
SELECT pg_catalog.setval('cart_product_id_seq', 4, true);