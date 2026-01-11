CREATE TABLE IF NOT EXISTS tb_tipos_estabelecimento (
    id BIGSERIAL PRIMARY KEY,
    codigo VARCHAR(50) NOT NULL UNIQUE,
    descricao VARCHAR(255) NOT NULL
);

INSERT INTO tb_tipos_estabelecimento (codigo, descricao) 
VALUES 
    ('RESTAURANTE', 'Restaurante'),
    ('BAR', 'Bar e Lanchonete'),
    ('MERCADO', 'Mercado e Supermercado')
ON CONFLICT (codigo) DO NOTHING;
