CREATE OR ALTER PROCEDURE atualizar_email_usuario
    @p_id INT,
    @p_email VARCHAR(100)
AS
BEGIN
    UPDATE tb_usuario
    SET email = @p_email
    WHERE id = @p_id;
END;
GO

