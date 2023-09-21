CREATE TABLE respuestas(

id BIGINT NOT NULL AUTO_INCREMENT,
usuario VARCHAR(100) NOT NULL,
respuesta VARCHAR(500) NOT NULL,
fecha datetime NOT NULL,
topico_id BIGINT,
PRIMARY KEY (id),
FOREIGN KEY (topico_id) REFERENCES topicos(id)




);