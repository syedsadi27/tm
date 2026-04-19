--liquibase formatted sql

--changeset dev:001
CREATE TABLE categories (
    id          VARCHAR(255) PRIMARY KEY,
    slug        VARCHAR(255) NOT NULL UNIQUE,
    label       VARCHAR(255) NOT NULL,
    description NVARCHAR(MAX),
    image_url   NVARCHAR(MAX),
    banner_url  NVARCHAR(MAX),
    color       VARCHAR(50),
    sort_order  INT DEFAULT 0,
    created_at  DATETIME2
);

CREATE TABLE products (
    id                  VARCHAR(255) PRIMARY KEY,
    name                VARCHAR(255) NOT NULL,
    slug                VARCHAR(255) NOT NULL UNIQUE,
    category_id         VARCHAR(255),
    price               DECIMAL(10,2) NOT NULL,
    original_price      DECIMAL(10,2),
    discount_percent    INT,
    currency            VARCHAR(10) DEFAULT 'USD',
    stock               INT DEFAULT 0,
    badge               VARCHAR(100),
    description         NVARCHAR(MAX),
    weight              VARCHAR(100),
    dimensions          VARCHAR(100),
    material            VARCHAR(100),
    is_bulk_eligible    BIT DEFAULT 0,
    bulk_min_qty        INT,
    bulk_contact_phone  VARCHAR(50),
    created_at          DATETIME2,
    updated_at          DATETIME2,
    CONSTRAINT fk_products_category FOREIGN KEY (category_id) REFERENCES categories(id)
);

CREATE TABLE product_images (
    id           BIGINT IDENTITY(1,1) PRIMARY KEY,
    product_id   VARCHAR(255),
    image_url    NVARCHAR(MAX) NOT NULL,
    is_thumbnail BIT DEFAULT 0,
    sort_order   INT DEFAULT 0,
    CONSTRAINT fk_product_images_product FOREIGN KEY (product_id) REFERENCES products(id)
);

CREATE TABLE reviews (
    id               VARCHAR(255) PRIMARY KEY,
    product_id       VARCHAR(255),
    author_name      VARCHAR(255),
    author_initials  VARCHAR(10),
    rating           INT,
    title            VARCHAR(255),
    text             NVARCHAR(MAX),
    verified         BIT DEFAULT 0,
    created_at       DATETIME2,
    CONSTRAINT fk_reviews_product FOREIGN KEY (product_id) REFERENCES products(id)
);

CREATE TABLE pincodes (
    pincode         VARCHAR(20) PRIMARY KEY,
    deliverable     BIT DEFAULT 1,
    estimated_days  VARCHAR(50),
    shipping_charge DECIMAL(10,2) DEFAULT 0,
    cod_available   BIT DEFAULT 1
);

CREATE TABLE product_stats (
    product_id     VARCHAR(255) PRIMARY KEY,
    average_rating DECIMAL(3,2),
    review_count   INT DEFAULT 0,
    CONSTRAINT fk_product_stats_product FOREIGN KEY (product_id) REFERENCES products(id)
);
