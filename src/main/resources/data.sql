CREATE TABLE client(
                       id SERIAL NOT NULL PRIMARY KEY,
                       username VARCHAR(20) NOT NULL,
                       password VARCHAR(20) NOT NULL,
                       birth_date DATE NOT NULL,
                       name VARCHAR(50) NOT NULL,
                       surname VARCHAR(50) NOT NULL
);

CREATE TABLE account(
                        id SERIAL NOT NULL PRIMARY KEY,
                        balance DECIMAL NOT NULL,
                        open_date DATE NOT NULL,
                        close_date DATE NOT NULL ,
                        status VARCHAR(20),
                        client_id INT NOT NULL,
                        FOREIGN KEY (client_id) REFERENCES client(id) ON DELETE CASCADE
);

INSERT INTO client(username, password, birth_date, name, surname) VALUES ('andrey','andrey', '1990-11-15','Andrey', 'Andreev');
INSERT INTO client(username, password, birth_date, name, surname) VALUES ('vadim','vadim', '1970-05-12','Vadim', 'Vadimov');
INSERT INTO client(username, password, birth_date, name, surname) VALUES ('zhenya','zhenya', '2000-07-19','Zhenya', 'Zhenev');
INSERT INTO client(username, password, birth_date, name, surname) VALUES ('lena','lena', '1998-04-28','Lena', 'Lenova');
INSERT INTO client(username, password, birth_date, name, surname) VALUES ('ivan','ivan', '1985-06-15','Ivan', 'Ivanov');
INSERT INTO client(username, password, birth_date, name, surname) VALUES ('vasya','vasya', '1975-04-23','Vasya', 'Vasev');

INSERT INTO account(balance, open_date, close_date, status, client_id) VALUES ( 10000.00, '2021-05-20', '2024-05-05', 'OPEN', 1);
INSERT INTO account(balance, open_date, close_date, status, client_id) VALUES ( 20000.00, '2021-05-20', '2024-05-05', 'SUSPEND', 1);
INSERT INTO account(balance, open_date, close_date, status, client_id) VALUES ( 30000.00, '2021-05-20', '2024-05-05', 'OPEN', 2);
INSERT INTO account(balance, open_date, close_date, status, client_id) VALUES ( 40000.00, '2018-05-20', '2021-05-20', 'CLOSED', 3);
INSERT INTO account(balance, open_date, close_date, status, client_id) VALUES ( 50000.00, '2021-05-20', '2024-05-05', 'OPEN', 4);
INSERT INTO account(balance, open_date, close_date, status, client_id) VALUES ( 60000.00, '2021-05-20', '2024-05-05', 'OPEN', 5);
INSERT INTO account(balance, open_date, close_date, status, client_id) VALUES ( 70000.00, '2018-05-20', '2021-05-20', 'CLOSED', 6);
