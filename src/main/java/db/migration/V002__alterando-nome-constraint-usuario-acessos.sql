SELECT CONSTRAINT_NAME 
FROM information_schema.constraint_column_usage 
WHERE TABLE_NAME = 'usuario_acessos' AND COLUMN_NAME = 'acesso_id' AND CONSTRAINT_NAME <> 'unique_acesso_usuario'

ALTER TABLE usuario_acessos DROP CONSTRAINT "uk_a3ang7rwed63kbywooruuu0pc"