----- Marca ------

INSERT INTO marca (id, nome, paisorigem, anofundacao, logo, siteoficial)
VALUES (1, 'Sony Interactive Entertainment', 'Japão', 1946,
        'https://upload.wikimedia.org/wikipedia/commons/2/21/Sony_logo.svg',
        'https://www.playstation.com');

INSERT INTO marca (id, nome, paisorigem, anofundacao, logo, siteoficial)
VALUES (2, 'Microsoft Xbox', 'Estados Unidos', 1975,
        'https://upload.wikimedia.org/wikipedia/commons/4/4f/Microsoft_logo.svg',
        'https://www.xbox.com');

INSERT INTO marca (id, nome, paisorigem, anofundacao, logo, siteoficial)
VALUES (3, 'Nintendo', 'Japão', 1889,
        'https://upload.wikimedia.org/wikipedia/commons/0/0d/Nintendo.svg',
        'https://www.nintendo.com');

INSERT INTO marca (id, nome, paisorigem, anofundacao, logo, siteoficial)
VALUES (4, '8BitDo', 'China', 2013,
        'https://upload.wikimedia.org/wikipedia/commons/8/8e/8bitdo-logo.png',
        'https://www.8bitdo.com');


----- Plataformas ------

INSERT INTO plataforma (id, nome, fabricante, geracao, tipo, anolancamento)
VALUES (1, 'PlayStation 5', 'Sony', '9ª Geração', 'Console', 2020);

INSERT INTO plataforma (id, nome, fabricante, geracao, tipo, anolancamento)
VALUES (2, 'PlayStation 4', 'Sony', '8ª Geração', 'Console', 2013);

INSERT INTO plataforma (id, nome, fabricante, geracao, tipo, anolancamento)
VALUES (3, 'Xbox Series X|S', 'Microsoft', '9ª Geração', 'Console', 2020);

INSERT INTO plataforma (id, nome, fabricante, geracao, tipo, anolancamento)
VALUES (4, 'Xbox One', 'Microsoft', '8ª Geração', 'Console', 2013);

INSERT INTO plataforma (id, nome, fabricante, geracao, tipo, anolancamento)
VALUES (5, 'Nintendo Switch', 'Nintendo', '8ª Geração', 'Console Híbrido', 2017);

INSERT INTO plataforma (id, nome, fabricante, geracao, tipo, anolancamento)
VALUES (6, 'Nintendo Switch OLED', 'Nintendo', '8ª Geração', 'Console Híbrido', 2021);

------ Modelos ------   

INSERT INTO modelo (
    anolancamento,
    ativo,
    id_marca,
    id_plataforma,
    codigoreferencia,
    descricao,
    nome,
    versao
) VALUES
(2020, true, 1, 1, 'CFI-ZCT1W', 'Controle oficial do PlayStation 5', 'DualSense', 'v1'),

(2013, true, 1, 2, 'CUH-ZCT2', 'Controle oficial do PlayStation 4', 'DualShock 4', 'v2'),

(2020, true, 2, 3, '1914', 'Controle oficial do Xbox Series X|S', 'Xbox Wireless Controller', 'v3'),

(2013, true, 2, 4, '1537', 'Controle oficial do Xbox One', 'Xbox One Controller', 'v2'),

(2017, true, 3, 5, 'HAC-013', 'Controle Pro do Nintendo Switch', 'Switch Pro Controller', 'v1'),

(2017, true, 3, 5, 'HAC-015', 'Joy-Con esquerdo do Nintendo Switch', 'Joy-Con Left', 'v1'),

(2017, true, 3, 5, 'HAC-016', 'Joy-Con direito do Nintendo Switch', 'Joy-Con Right', 'v1');



----- Cores ------

INSERT INTO cor (id, nome, descricao) VALUES (1, 'Midnight Black', 'Cor oficial do DualSense PS5');
INSERT INTO cor (id, nome, descricao) VALUES (2, 'Cosmic Red', 'Versão especial do DualSense PS5 em vermelho vibrante');
INSERT INTO cor (id, nome, descricao) VALUES (3, 'Galactic Purple', 'DualSense PS5 roxo metálico premium');
INSERT INTO cor (id, nome, descricao) VALUES (4, 'Stellar Blue', 'DualSense PS5 azul profundo');
INSERT INTO cor (id, nome, descricao) VALUES (5, 'Volt', 'Cor neon exclusiva dos controles Xbox Series');
INSERT INTO cor (id, nome, descricao) VALUES (6, 'Shock Blue', 'Azul vibrante do Xbox Series X/S');
INSERT INTO cor (id, nome, descricao) VALUES (7, 'Carbon Black', 'Cor padrão dos controles Xbox Series X/S');
INSERT INTO cor (id, nome, descricao) VALUES (8, 'Neon Red', 'Cor oficial do Joy-Con esquerdo');
INSERT INTO cor (id, nome, descricao) VALUES (9, 'Neon Blue', 'Cor oficial do Joy-Con direito');
INSERT INTO cor (id, nome, descricao) VALUES (10, 'Smash Black Edition', 'Switch Pro Controller edição temática Super Smash Bros');



----- Especificação tecnica ------

INSERT INTO especificacaotecnica (id, autonomiabateria, peso, conectividade, dimensoes, material, sensoresextras)
VALUES
(1, 12, 0.280, 'Bluetooth 5.1 / USB-C', '160x66x106 mm', 'Plástico ABS', 'Giroscópio, Acelerômetro, Trackpad, Feedback Háptico'),

(2, 8, 0.210, 'Bluetooth 2.1 / USB', '161x57x100 mm', 'Plástico ABS', 'Giroscópio, Acelerômetro'),

(3, 40, 0.287, 'Bluetooth / USB-C', '153x60x100 mm', 'Plástico ABS', 'Impulso Triggers, Vibração Avançada'),

(4, 30, 0.260, 'Bluetooth / USB', '150x62x98 mm', 'Plástico ABS', 'Vibração'),

(5, 40, 0.246, 'Bluetooth', '157x106x60 mm', 'Plástico ABS', 'NFC, Giroscópio, HD Rumble'),

(6, 20, 0.052, 'Bluetooth', '102x35x28 mm', 'Plástico ABS', 'HD Rumble, Acelerômetro, Giroscópio'),

(7, 20, 0.052, 'Bluetooth', '102x35x28 mm', 'Plástico ABS', 'HD Rumble, Acelerômetro, Giroscópio');



------ Categoria ------

INSERT INTO categoria (nome, descricao, datacadastro, dataalteracao)
VALUES ('Edição Padrão', 'Categoria padrão Edição L', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO categoria (nome, descricao, datacadastro, dataalteracao)
VALUES ('Controle PRO', 'Categoria Controle PRO', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO categoria (nome, descricao, datacadastro, dataalteracao)
VALUES ('Edição Limitada', 'Categoria de edições limitadas', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO categoria (nome, descricao, datacadastro, dataalteracao)
VALUES ('Controle Série', 'Categoria de controles da linha Série', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO controle (
    id, nome, cor, descricao, preco, estoque, datalancamento,
    id_marca, id_modelo, id_plataforma, id_especificacao_tecnica
) VALUES (
    1, 'Xbox Wireless Controller – Carbon Black', 'Carbon Black',
    'Controle oficial Xbox Series X|S com textura aprimorada e conexão USB-C.',
    379.90, 20, '2023-05-01',
    2, 3, 3, 3
);

INSERT INTO controle (
    id, nome, cor, descricao, preco, estoque, datalancamento,
    id_marca, id_modelo, id_plataforma, id_especificacao_tecnica
) VALUES (
    2, 'DualSense – Galactic Purple', 'Galactic Purple',
    'Controle oficial PS5 com feedback háptico e gatilhos adaptáveis.',
    469.90, 18, '2023-02-15',
    1, 1, 1, 1
);

INSERT INTO controle (
    id, nome, cor, descricao, preco, estoque, datalancamento,
    id_marca, id_modelo, id_plataforma, id_especificacao_tecnica
) VALUES (
    3, 'Switch Pro Controller – Smash Edition', 'Smash Black Edition',
    'Edição especial Super Smash Bros com bateria de longa duração.',
    499.90, 10, '2022-11-10',
    3, 5, 5, 5
);

INSERT INTO controle_categoria (id_controle, id_categoria) VALUES (1, 4);
INSERT INTO controle_categoria (id_controle, id_categoria) VALUES (1, 2);

INSERT INTO controle_categoria (id_controle, id_categoria) VALUES (2, 1);
INSERT INTO controle_categoria (id_controle, id_categoria) VALUES (2, 3);

INSERT INTO controle_categoria (id_controle, id_categoria) VALUES (3, 3);
INSERT INTO controle_categoria (id_controle, id_categoria) VALUES (3, 2);


SELECT setval('cor_id_seq', 10, true);


        -- =========================
-- USUÁRIOS
-- =========================
-- senha plaintext: 123456
-- hash: +RMra81+PVL2HQWuh7xAkSohHzzzq62hw4zuaEpFHXbE0+pX+fzwOpTqmmuDA19zusgadv4fnMnHqLd2S32aXQ==

INSERT INTO usuario (id, nome, login, senha, perfil, datacadastro, dataalteracao) VALUES
(1, 'Maximoff', 'admin',
 '+RMra81+PVL2HQWuh7xAkSohHzzzq62hw4zuaEpFHXbE0+pX+fzwOpTqmmuDA19zusgadv4fnMnHqLd2S32aXQ==',
 1, now(), now()),  -- ADM

(2, 'Hirai', 'user',
 '+RMra81+PVL2HQWuh7xAkSohHzzzq62hw4zuaEpFHXbE0+pX+fzwOpTqmmuDA19zusgadv4fnMnHqLd2S32aXQ==',
 2, now(), now()),  -- USER

(3, 'Minatozaki', 'user2',
 '+RMra81+PVL2HQWuh7xAkSohHzzzq62hw4zuaEpFHXbE0+pX+fzwOpTqmmuDA19zusgadv4fnMnHqLd2S32aXQ==',
 2, now(), now());  -- USER 2 (NOVO)