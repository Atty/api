DROP TABLE if exists cards;
DROP TABLE if exists bank_accounts;
DROP TABLE if exists clients;

CREATE TABLE clients
(
    id      IDENTITY     NOT NULL PRIMARY KEY,
    name    varchar(255) NOT NULL,
    version long         NOT NULL DEFAULT 1
);

CREATE INDEX clients_index on clients (name);

CREATE TABLE bank_accounts
(
    id        IDENTITY     NOT NULL PRIMARY KEY,
    number    varchar(255) NOT NULL,
    balance   int,
    id_client bigint,
    version   long         NOT NULL DEFAULT 1,
    FOREIGN KEY (id_client) references clients (id)
);

CREATE INDEX bank_accounts_index on bank_accounts (number, id_client);

CREATE TABLE cards
(
    id              IDENTITY NOT NULL PRIMARY KEY,
    number          bigint   NOT NULL,
    id_bank_account bigint,
    version         long     NOT NULL DEFAULT 1,
    FOREIGN KEY (id_bank_account) references bank_accounts (id)
);

CREATE INDEX cards_index on cards (number, id_bank_account);
