CREATE TABLE tb_user (
    id ${type_serial} NOT NULL PRIMARY KEY,
    first_name   VARCHAR(50)  NOT NULL,
    last_name   VARCHAR(50)  NOT NULL,
    email  VARCHAR(50)  NOT NULL,
    password  VARCHAR(150) NOT NULL
);
