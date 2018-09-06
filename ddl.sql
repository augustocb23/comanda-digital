CREATE DATABASE comanda
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_0900_ai_ci;
USE comanda;

CREATE TABLE funcionario (
  cod_func   INT AUTO_INCREMENT PRIMARY KEY,
  nome_func  VARCHAR(255)           NOT NULL,
  tipo_func  CHAR                   NOT NULL,
  ativo_func TINYINT(1) DEFAULT '1' NOT NULL
);

CREATE TABLE comanda (
  cod_com   INT AUTO_INCREMENT PRIMARY KEY,
  mesa_com  INT                                NOT NULL,
  nomes_com VARCHAR(255)                       NULL,
  data_com  DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
  cod_func  INT                                NOT NULL,
  CONSTRAINT comanda_funcionario_cod_func_fk FOREIGN KEY (cod_func) REFERENCES funcionario (cod_func)
);

CREATE TABLE ingrediente (
  cod_ing     INT AUTO_INCREMENT PRIMARY KEY,
  nome_ing    VARCHAR(255)                  NOT NULL,
  estoque_ing INT                           NULL,
  unidade_ing ENUM ('Kg', 'Lt', 'Un', 'Cx') NOT NULL
);

CREATE TABLE conversao (
  cod_conv   INT AUTO_INCREMENT PRIMARY KEY,
  cod_ing    INT     NOT NULL,
  unid_conv  CHAR(2) NOT NULL,
  fator_conv DOUBLE  NOT NULL,
  CONSTRAINT conversao_ingrediente_cod_ing_fk FOREIGN KEY (cod_ing) REFERENCES ingrediente (cod_ing)
);

CREATE TABLE produto (
  cod_prod     INT AUTO_INCREMENT PRIMARY KEY,
  nome_prod    VARCHAR(255)    NOT NULL,
  estoque_prod INT DEFAULT '0' NULL,
  foto_prod    VARCHAR(255)    NULL
);

CREATE TABLE pedido (
  cod_ped    INT AUTO_INCREMENT PRIMARY KEY,
  cod_com    INT                                                                                       NOT NULL,
  cod_prod   INT                                                                                       NOT NULL,
  quant_ped  INT DEFAULT '1'                                                                           NOT NULL,
  status_ped ENUM ('Solicitado', 'Preparando', 'Pronto', 'Entregue', 'Cancelado') DEFAULT 'Solicitado' NOT NULL,
  CONSTRAINT pedido_comanda_cod_com_fk FOREIGN KEY (cod_com) REFERENCES comanda (cod_com),
  CONSTRAINT pedido_produto_cod_prod_fk FOREIGN KEY (cod_prod) REFERENCES produto (cod_prod)
);

CREATE TABLE produto_ingrediente (
  cod_prod INT NOT NULL,
  cod_ing  INT NOT NULL,
  cod_conv INT NULL,
  PRIMARY KEY (cod_prod, cod_ing),
  CONSTRAINT produto_ingrediente_conversao_cod_conv_fk FOREIGN KEY (cod_conv) REFERENCES conversao (cod_conv)
    ON DELETE SET NULL,
  CONSTRAINT produto_ingrediente_ingrediente_cod_ing_fk FOREIGN KEY (cod_ing) REFERENCES ingrediente (cod_ing),
  CONSTRAINT produto_ingrediente_produto_cod_prod_fk FOREIGN KEY (cod_prod) REFERENCES produto (cod_prod)
);

