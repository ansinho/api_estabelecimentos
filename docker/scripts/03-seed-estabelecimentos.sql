CREATE TABLE IF NOT EXISTS tb_estabelecimentos (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome VARCHAR(150) NOT NULL,
    cnpj VARCHAR(14) NOT NULL UNIQUE,
    tipo_codigo VARCHAR(50) NOT NULL REFERENCES tb_tipos_estabelecimento(codigo),
    geom GEOMETRY(Point, 4326) NOT NULL
);

INSERT INTO tb_estabelecimentos (nome, cnpj, tipo_codigo, geom) 
VALUES 
    ('Meu Bar Asa Sul', '98765432000188', 'BAR', ST_SetSRID(ST_MakePoint(-47.8910, -15.8120), 4326)),
    ('Supermercado Lago Norte', '11222333444455', 'MERCADO', ST_SetSRID(ST_MakePoint(-47.8730, -15.7490), 4326)),
    ('Lanchonete UNB Asa Norte', '55667788990011', 'BAR', ST_SetSRID(ST_MakePoint(-47.9020, -15.7840), 4326)),
    ('Farmácia Popular', '99887766554433', 'FARMACIA', ST_SetSRID(ST_MakePoint(-47.9350, -15.8010), 4326))
ON CONFLICT (cnpj) DO NOTHING;
