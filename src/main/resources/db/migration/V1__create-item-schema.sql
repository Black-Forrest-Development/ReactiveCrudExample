CREATE SEQUENCE item_seq;
CREATE TABLE item
(
    id   BIGINT       NOT NULL PRIMARY KEY DEFAULT nextval('item_seq'::regclass),
    name VARCHAR(255) NOT NULL
);
