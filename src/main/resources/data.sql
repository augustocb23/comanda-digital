-- tenta inserir os dados do administrador
INSERT IGNORE INTO permissao(nome)
VALUES ('editar_usuarios');
SET @usuarios = (SELECT LAST_INSERT_ID());
INSERT IGNORE INTO permissao(nome)
VALUES ('editar_admin');
SET @admin = (SELECT LAST_INSERT_ID());
INSERT IGNORE INTO grupo(nome)
VALUES ('administrador');
SET @grupo = (SELECT LAST_INSERT_ID());
INSERT IGNORE INTO permissao(nome)
VALUES ('editar_permissoes');
SET @permissoes = (SELECT LAST_INSERT_ID());
INSERT IGNORE INTO grupo_permissao(grupo, permissao)
VALUES (@grupo, @usuarios);
INSERT IGNORE INTO grupo_permissao(grupo, permissao)
VALUES (@grupo, @admin);
INSERT IGNORE INTO grupo_permissao(grupo, permissao)
VALUES (@grupo, @permissoes);
INSERT IGNORE INTO funcionario(login, nome, senha, grupo)
VALUES ('admin', 'Administrador', '$2a$10$eVJzLDv.T5B9WIL46VsDK.9Vmq1xPzeICesGWUDHriHf.Xxq2v4ti', @grupo);