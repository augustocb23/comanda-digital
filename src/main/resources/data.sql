-- tenta inserir os dados do administrador
INSERT IGNORE INTO autorizacao(autorizacao)
VALUES ('editar_usuarios');
SET @autorizacao = (SELECT LAST_INSERT_ID());
INSERT IGNORE INTO grupo(nome)
VALUES ('administrador');
SET @grupo = (SELECT LAST_INSERT_ID());
INSERT IGNORE INTO grupo_autorizacao(grupo, autorizacao)
VALUES (@grupo, @autorizacao);
INSERT IGNORE INTO funcionario(login, nome, senha, grupo)
VALUES ('admin', 'Administrador', '$2a$10$eVJzLDv.T5B9WIL46VsDK.9Vmq1xPzeICesGWUDHriHf.Xxq2v4ti', @grupo);