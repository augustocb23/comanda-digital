-- permissões
INSERT IGNORE INTO permissao(nome)
VALUES ('editar_usuarios');
SET @usuarios = (SELECT last_insert_id());
INSERT IGNORE INTO permissao(nome)
VALUES ('editar_admin');
SET @admin = (SELECT last_insert_id());
INSERT IGNORE INTO permissao(nome)
VALUES ('editar_permissoes');
SET @permissoes = (SELECT last_insert_id());
INSERT IGNORE INTO permissao(nome)
VALUES ('editar_produtos');
SET @produto = (SELECT last_insert_id());
INSERT IGNORE INTO permissao(nome)
VALUES ('gerar_relatorios');
SET @relatorios = (SELECT last_insert_id());
-- cria o grupo administrador
INSERT IGNORE INTO grupo(nome)
VALUES ('administrador');
SET @grupo = (SELECT codigo
              FROM grupo
              WHERE nome = 'administrador');
-- cria o grupo atendente (sem permissões avançadas)
INSERT IGNORE INTO grupo(nome)
VALUES ('atendente');
-- associa as permissões ao grupo
INSERT IGNORE INTO grupo_permissao(grupo, permissao)
VALUES (@grupo, @usuarios);
INSERT IGNORE INTO grupo_permissao(grupo, permissao)
VALUES (@grupo, @admin);
INSERT IGNORE INTO grupo_permissao(grupo, permissao)
VALUES (@grupo, @permissoes);
INSERT IGNORE INTO grupo_permissao(grupo, permissao)
VALUES (@grupo, @produto);
INSERT IGNORE INTO grupo_permissao(grupo, permissao)
VALUES (@grupo, @relatorios);
-- insere o admin
INSERT IGNORE INTO funcionario(login, nome, senha, grupo)
VALUES ('admin', 'Administrador', '$2a$10$eVJzLDv.T5B9WIL46VsDK.9Vmq1xPzeICesGWUDHriHf.Xxq2v4ti', @grupo);