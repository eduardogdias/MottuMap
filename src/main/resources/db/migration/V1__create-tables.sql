CREATE TABLE TB_PATIO (
    id_patio INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nome_patio VARCHAR(255) NOT NULL,
    endereco_patio VARCHAR(255) NOT NULL
);
 
CREATE TABLE TB_ZONA (
    id_zona INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    tipo_zona VARCHAR(255) NOT NULL,
    qtd_vaga_zona INT NOT NULL,
    fk_patio INT NOT NULL,
    CONSTRAINT fk_zona_patio FOREIGN KEY (fk_patio) REFERENCES TB_PATIO (id_patio) ON DELETE CASCADE
);
 
CREATE TABLE TB_MOTO (
    id_moto INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    placa_moto VARCHAR(7),
    chassi_moto VARCHAR(17),
    marca_moto VARCHAR(255),
    modelo_moto VARCHAR(20) NOT NULL
);
 
CREATE TABLE TB_SENSOR (
    id_sensor INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    localizacao_sensor VARCHAR(50) NOT NULL,
    data_sensor DATE NOT NULL,
    hora_sensor DATE NOT NULL
);
 
CREATE TABLE TB_HISTORICO (
    id_hist INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    posicao_hist INT NOT NULL,
    fk_moto INT NOT NULL,
    fk_zona INT NOT NULL,
    fk_sensor INT NOT NULL,
    CONSTRAINT fk_hist_moto FOREIGN KEY (fk_moto) REFERENCES TB_MOTO (id_moto),
    CONSTRAINT fk_hist_zona FOREIGN KEY (fk_zona) REFERENCES TB_ZONA (id_zona),
    CONSTRAINT fk_hist_sensor FOREIGN KEY (fk_sensor) REFERENCES TB_SENSOR (id_sensor)
);