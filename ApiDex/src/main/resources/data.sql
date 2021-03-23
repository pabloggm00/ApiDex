/*GENERACIONES*/
INSERT INTO POKEDEX(generacion, nombre, id) VALUES(1, 'Kanto', NEXTVAL('hibernate_sequence'));
INSERT INTO POKEDEX(generacion, nombre, id) VALUES(2, 'Johto', NEXTVAL('hibernate_sequence'));
INSERT INTO POKEDEX(generacion, nombre, id) VALUES(3, 'Hoenn', NEXTVAL('hibernate_sequence'));
INSERT INTO POKEDEX(generacion, nombre, id) VALUES(4, 'Sinnoh', NEXTVAL('hibernate_sequence'));
INSERT INTO POKEDEX(generacion, nombre, id) VALUES(5, 'Teselia', NEXTVAL('hibernate_sequence'));
INSERT INTO POKEDEX(generacion, nombre, id) VALUES(6, 'Kalos', NEXTVAL('hibernate_sequence'));
INSERT INTO POKEDEX(generacion, nombre, id) VALUES(7, 'Alola', NEXTVAL('hibernate_sequence'));
INSERT INTO POKEDEX(generacion, nombre, id) VALUES(8, 'Galar', NEXTVAL('hibernate_sequence'));

/*TIPOS*/
INSERT INTO TIPO(nombre_Tipo, id) VALUES('Planta', NEXTVAL('hibernate_sequence'));
INSERT INTO TIPO(nombre_Tipo, id) VALUES('Veneno', NEXTVAL('hibernate_sequence'));
INSERT INTO TIPO(nombre_Tipo, id) VALUES('Fuego', NEXTVAL('hibernate_sequence'));
INSERT INTO TIPO(nombre_Tipo, id) VALUES('Acero', NEXTVAL('hibernate_sequence'));
INSERT INTO TIPO(nombre_Tipo, id) VALUES('Agua', NEXTVAL('hibernate_sequence'));
INSERT INTO TIPO(nombre_Tipo, id) VALUES('Bicho', NEXTVAL('hibernate_sequence'));
INSERT INTO TIPO(nombre_Tipo, id) VALUES('Dragón', NEXTVAL('hibernate_sequence'));
INSERT INTO TIPO(nombre_Tipo, id) VALUES('Eléctrico', NEXTVAL('hibernate_sequence'));
INSERT INTO TIPO(nombre_Tipo, id) VALUES('Fantasma', NEXTVAL('hibernate_sequence'));
INSERT INTO TIPO(nombre_Tipo, id) VALUES('Hada', NEXTVAL('hibernate_sequence'));
INSERT INTO TIPO(nombre_Tipo, id) VALUES('Hielo', NEXTVAL('hibernate_sequence'));
INSERT INTO TIPO(nombre_Tipo, id) VALUES('Lucha', NEXTVAL('hibernate_sequence'));
INSERT INTO TIPO(nombre_Tipo, id) VALUES('Normal', NEXTVAL('hibernate_sequence'));
INSERT INTO TIPO(nombre_Tipo, id) VALUES('Psíquico', NEXTVAL('hibernate_sequence'));
INSERT INTO TIPO(nombre_Tipo, id) VALUES('Roca', NEXTVAL('hibernate_sequence'));
INSERT INTO TIPO(nombre_Tipo, id) VALUES('Siniestro', NEXTVAL('hibernate_sequence'));
INSERT INTO TIPO(nombre_Tipo, id) VALUES('Tierra', NEXTVAL('hibernate_sequence'));
INSERT INTO TIPO(nombre_Tipo, id) VALUES('Volador', NEXTVAL('hibernate_sequence'));

INSERT INTO IMAGEN(data_id, delete_hash, id) VALUES('Zz7qHNj', 'h2H8fHa7qYAZuRy', NEXTVAL('hibernate_sequence'));
INSERT INTO IMAGEN(data_id, delete_hash, id) VALUES('yPV4XgX', 'MH8Gy809rhwanhD', NEXTVAL('hibernate_sequence'));
INSERT INTO IMAGEN(data_id, delete_hash, id) VALUES('ALoewPT', 'L5RmHHAgv4h9kKo', NEXTVAL('hibernate_sequence'));
INSERT INTO IMAGEN(data_id, delete_hash, id) VALUES('S4XdgD9', 'dNfzR6JUfw7EHre', NEXTVAL('hibernate_sequence'));
INSERT INTO IMAGEN(data_id, delete_hash, id) VALUES('9JBaL78', 'vK4hm5bA5PMOnfD', NEXTVAL('hibernate_sequence'));
INSERT INTO IMAGEN(data_id, delete_hash, id) VALUES('pc9scvX', 'o11p8O1oKN1XajG', NEXTVAL('hibernate_sequence'));
INSERT INTO IMAGEN(data_id, delete_hash, id) VALUES('7jA7Sqw', '6nESk3DxiIgWgqF', NEXTVAL('hibernate_sequence'));
INSERT INTO IMAGEN(data_id, delete_hash, id) VALUES('4qk5WKd', 'shd1yqmo60Eo6Dy', NEXTVAL('hibernate_sequence'));
INSERT INTO IMAGEN(data_id, delete_hash, id) VALUES('vBS1Tar', 'pQi0BuJ1ab9ZoIY', NEXTVAL('hibernate_sequence'));
INSERT INTO IMAGEN(data_id, delete_hash, id) VALUES('R2sYiCB', 'cutpRKkYW5tuftt', NEXTVAL('hibernate_sequence'));
INSERT INTO IMAGEN(data_id, delete_hash, id) VALUES('lMBNim8', 'LCuohmGukH0q9IS', NEXTVAL('hibernate_sequence'));
INSERT INTO IMAGEN(data_id, delete_hash, id) VALUES('ivKhCSI', 'FvXZL1ivGfJjlOJ', NEXTVAL('hibernate_sequence'));

/*POKEMONS*/
INSERT INTO POKEMON(nombre, estrellas, ataque_Rapido, ataque_Cargado, pC, id_Pokedex, is_Ultimo, is_Fav, is_Capturado, is_Original, equipo_id, generacion_id, imagen_id, primer_Tipo_id, segundo_tipo_id, id) VALUES ('Bulbasaur',  3, 'Ataque Rápido', 'Ataque Cargado', 1, '001', false, false, false, true, null, 1, 27, 9, 10,    NEXTVAL('hibernate_sequence'));
INSERT INTO POKEMON(nombre, estrellas, ataque_Rapido, ataque_Cargado, pC, id_Pokedex, is_Ultimo, is_Fav, is_Capturado, is_Original, equipo_id, generacion_id, imagen_id, primer_Tipo_id, segundo_tipo_id, id) VALUES ('Ivysaur',    3, 'Ataque Rápido', 'Ataque Cargado', 1, '002', false, false, false, true, null, 1, 28, 9, 10,    NEXTVAL('hibernate_sequence'));
INSERT INTO POKEMON(nombre, estrellas, ataque_Rapido, ataque_Cargado, pC, id_Pokedex, is_Ultimo, is_Fav, is_Capturado, is_Original, equipo_id, generacion_id, imagen_id, primer_Tipo_id, segundo_tipo_id, id) VALUES ('Venusaur',   3, 'Ataque Rápido', 'Ataque Cargado', 1, '003', true,  false, false, true, null, 1, 29, 9, 10,    NEXTVAL('hibernate_sequence'));
INSERT INTO POKEMON(nombre, estrellas, ataque_Rapido, ataque_Cargado, pC, id_Pokedex, is_Ultimo, is_Fav, is_Capturado, is_Original, equipo_id, generacion_id, imagen_id, primer_Tipo_id, segundo_tipo_id, id) VALUES ('Charmander', 3, 'Ataque Rápido', 'Ataque Cargado', 1, '004', false, false, false, true, null, 1, 30, 11, null, NEXTVAL('hibernate_sequence'));
INSERT INTO POKEMON(nombre, estrellas, ataque_Rapido, ataque_Cargado, pC, id_Pokedex, is_Ultimo, is_Fav, is_Capturado, is_Original, equipo_id, generacion_id, imagen_id, primer_Tipo_id, segundo_tipo_id, id) VALUES ('Charmeleon', 3, 'Ataque Rápido', 'Ataque Cargado', 1, '005', false, false, false, true, null, 1, 31, 11, null, NEXTVAL('hibernate_sequence'));
INSERT INTO POKEMON(nombre, estrellas, ataque_Rapido, ataque_Cargado, pC, id_Pokedex, is_Ultimo, is_Fav, is_Capturado, is_Original, equipo_id, generacion_id, imagen_id, primer_Tipo_id, segundo_tipo_id, id) VALUES ('Charizard',  3, 'Ataque Rápido', 'Ataque Cargado', 1, '006', true,  false, false, true, null, 1, 32, 11, 26,   NEXTVAL('hibernate_sequence'));
INSERT INTO POKEMON(nombre, estrellas, ataque_Rapido, ataque_Cargado, pC, id_Pokedex, is_Ultimo, is_Fav, is_Capturado, is_Original, equipo_id, generacion_id, imagen_id, primer_Tipo_id, segundo_tipo_id, id) VALUES ('Squirtle',   3, 'Ataque Rápido', 'Ataque Cargado', 1, '007', false, false, false, true, null, 1, 33, 13, null, NEXTVAL('hibernate_sequence'));
INSERT INTO POKEMON(nombre, estrellas, ataque_Rapido, ataque_Cargado, pC, id_Pokedex, is_Ultimo, is_Fav, is_Capturado, is_Original, equipo_id, generacion_id, imagen_id, primer_Tipo_id, segundo_tipo_id, id) VALUES ('Wartotle',   3, 'Ataque Rápido', 'Ataque Cargado', 1, '008', false, false, false, true, null, 1, 34, 13, null, NEXTVAL('hibernate_sequence'));
INSERT INTO POKEMON(nombre, estrellas, ataque_Rapido, ataque_Cargado, pC, id_Pokedex, is_Ultimo, is_Fav, is_Capturado, is_Original, equipo_id, generacion_id, imagen_id, primer_Tipo_id, segundo_tipo_id, id) VALUES ('Blastoise',  3, 'Ataque Rápido', 'Ataque Cargado', 1, '009', true,  false, false, true, null, 1, 35, 13, null, NEXTVAL('hibernate_sequence'));
INSERT INTO POKEMON(nombre, estrellas, ataque_Rapido, ataque_Cargado, pC, id_Pokedex, is_Ultimo, is_Fav, is_Capturado, is_Original, equipo_id, generacion_id, imagen_id, primer_Tipo_id, segundo_tipo_id, id) VALUES ('Dragonite',  3, 'Ataque Rápido', 'Ataque Cargado', 1, '149', true,  false, false, true, null, 1, 36, 15, 26,   NEXTVAL('hibernate_sequence'));
INSERT INTO POKEMON(nombre, estrellas, ataque_Rapido, ataque_Cargado, pC, id_Pokedex, is_Ultimo, is_Fav, is_Capturado, is_Original, equipo_id, generacion_id, imagen_id, primer_Tipo_id, segundo_tipo_id, id) VALUES ('Mewtwo',     3, 'Ataque Rápido', 'Ataque Cargado', 1, '150', true,  false, false, true, null, 1, 37, 22, null, NEXTVAL('hibernate_sequence'));
INSERT INTO POKEMON(nombre, estrellas, ataque_Rapido, ataque_Cargado, pC, id_Pokedex, is_Ultimo, is_Fav, is_Capturado, is_Original, equipo_id, generacion_id, imagen_id, primer_Tipo_id, segundo_tipo_id, id) VALUES ('Mew',        3, 'Ataque Rápido', 'Ataque Cargado', 1, '151', true,  false, false, true, null, 1, 38, 22, null, NEXTVAL('hibernate_sequence'));




