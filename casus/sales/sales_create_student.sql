-- ------------------------------------------------------------------------
-- Data & Persistency - Casus 'sales'
--
-- Hogeschool Utrecht
-- Tijmen Muller (tijmen.muller@hu.nl)
-- ------------------------------------------------------------------------

CREATE TABLE customers (
	customer_id serial NOT NULL,
	customer_name varchar(200) NOT NULL,
	bill_to_customer_id int4 NOT NULL,
	customer_category_id int4 NOT NULL,
	buying_group_id int4 NULL,
	primary_contact_person_id int4 NOT NULL,
	alternate_contact_person_id int4 NULL,
	delivery_method_id int4 NOT NULL,
	delivery_city_id int4 NOT NULL,
	postal_city_id int4 NOT NULL,
	credit_limit numeric(18,2) NULL,
	account_opened_date date NOT NULL,
	standard_discount_percentage numeric(18,3) NOT NULL,
	is_statement_sent bool NOT NULL,
	is_on_credit_hold bool NOT NULL,
	payment_days int4 NOT NULL,
	phone_number varchar(40) NOT NULL,
	fax_number varchar(40) NOT NULL,
	delivery_run varchar(10) NULL,
	run_position varchar(10) NULL,
	website_url varchar(512) NOT NULL,
	delivery_address_line_1 varchar(120) NOT NULL,
	delivery_address_line_2 varchar(120) NULL,
	delivery_postal_code varchar(20) NOT NULL,
	postal_address_line_1 varchar(120) NOT NULL,
	postal_address_line_2 varchar(120) NULL,
	postal_postal_code varchar(20) NOT NULL,
	last_edited_by int4 NOT NULL,
	CONSTRAINT pk_sales_customers PRIMARY KEY (customer_id)
);

CREATE TABLE orders (
	order_id serial NOT NULL,
	customer_id int4 NOT NULL,
	salesperson_person_id int4 NOT NULL,
	picked_by_person_id int4 NULL,
	contact_person_id int4 NOT NULL,
	backorder_order_id int4 NULL,
	order_date date NOT NULL,
	expected_delivery_date date NOT NULL,
	customer_purchase_order_number varchar(40) NULL,
	is_undersupply_backordered bool NOT NULL,
	"comments" text NULL,
	delivery_instructions text NULL,
	internal_comments text NULL,
	picking_completed_when timestamp NULL,
	last_edited_by int4 NOT NULL,
	last_edited_when timestamp NOT NULL DEFAULT 'now'::text::timestamp without time zone,
	CONSTRAINT pk_sales_orders PRIMARY KEY (order_id)
);


CREATE TABLE stock_items (
	stock_item_id serial NOT NULL,
	stock_item_name varchar(200) NOT NULL,
	supplier_id int4 NOT NULL,
	color_id int4 NULL,
	unit_package_id int4 NOT NULL,
	outer_package_id int4 NOT NULL,
	brand varchar(100) NULL,
	"size" varchar(40) NULL,
	lead_time_days int4 NOT NULL,
	quantity_per_outer int4 NOT NULL,
	is_chiller_stock bool NOT NULL,
	barcode varchar(100) NULL,
	tax_rate numeric(18,3) NOT NULL,
	unit_price numeric(18,2) NOT NULL,
	recommended_retail_price numeric(18,2) NULL,
	typical_weight_per_unit numeric(18,3) NOT NULL,
	marketing_comments text NULL,
	internal_comments text NULL,
	photo bytea NULL,
	custom_fields text NULL,
	tags text NULL,
	search_details text NOT NULL,
	last_edited_by int4 NOT NULL,
	CONSTRAINT pk_warehouse_stock_items PRIMARY KEY (stock_item_id)
);


CREATE TABLE order_lines (
	order_line_id serial NOT NULL,
	order_id int4 NOT NULL,
	stock_item_id int4 NOT NULL,
	description varchar(200) NOT NULL,
	package_type_id int4 NOT NULL,
	quantity int4 NOT NULL,
	unit_price numeric(18,2) NULL,
	tax_rate numeric(18,3) NOT NULL,
	picked_quantity int4 NOT NULL,
	picking_completed_when timestamp NULL,
	last_edited_by int4 NOT NULL,
	last_edited_when timestamp NOT NULL DEFAULT 'now'::text::timestamp without time zone,
	CONSTRAINT pk_sales_order_lines PRIMARY KEY (order_line_id)
);

