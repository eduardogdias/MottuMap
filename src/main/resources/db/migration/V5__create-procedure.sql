CREATE OR REPLACE PROCEDURE atualizar_email_usuario (
    p_id IN NUMBER,
    p_email IN VARCHAR2
)
AS
BEGIN
    UPDATE tb_usuario
    SET email = p_email
    WHERE id = p_id;

    COMMIT;
END;
/
