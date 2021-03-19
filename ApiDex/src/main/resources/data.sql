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

/*POKEMONS*/
INSERT INTO POKEMON(nombre, estrellas, ataque_Rapido, ataque_Cargado, pC, id_Pokedex, is_Ultimo, is_Fav, is_Capturado, is_Original, equipo_id, generacion_id, primer_Tipo_id, segundo_tipo_id, id) VALUES ('Bulbasaur', null, null, null, null, '#001', false, false, false, true, null, 1, 9, 10, NEXTVAL('hibernate_sequence'));
INSERT INTO POKEMON(nombre, estrellas, ataque_Rapido, ataque_Cargado, pC, id_Pokedex, is_Ultimo, is_Fav, is_Capturado, is_Original, equipo_id, generacion_id, primer_Tipo_id, segundo_tipo_id, id) VALUES ('Ivysaur', null, null, null, null, '#002', false, false, false, true, null, 1, 9, 10,NEXTVAL('hibernate_sequence'));
INSERT INTO POKEMON(nombre, estrellas, ataque_Rapido, ataque_Cargado, pC, id_Pokedex, is_Ultimo, is_Fav, is_Capturado, is_Original, equipo_id, generacion_id, primer_Tipo_id, segundo_tipo_id, id) VALUES ('Venasaur', null, null, null, null, '#003', true, false,false, true, null, 1, 9,10, NEXTVAL('hibernate_sequence'));
INSERT INTO POKEMON(nombre, estrellas, ataque_Rapido, ataque_Cargado, pC, id_Pokedex, is_Ultimo, is_Fav, is_Capturado, is_Original, equipo_id, generacion_id, primer_Tipo_id, segundo_tipo_id, id) VALUES ('Charmander', null, null, null, null, '#004', false,false, false, true, null, 1,11,null, NEXTVAL('hibernate_sequence'));
INSERT INTO POKEMON(nombre, estrellas, ataque_Rapido, ataque_Cargado, pC, id_Pokedex, is_Ultimo, is_Fav, is_Capturado, is_Original, equipo_id, generacion_id, primer_Tipo_id, segundo_tipo_id, id) VALUES ('Charmeleon', null, null, null, null, '#005', false,false, false, true, null, 1,11,null, NEXTVAL('hibernate_sequence'));
INSERT INTO POKEMON(nombre, estrellas, ataque_Rapido, ataque_Cargado, pC, id_Pokedex, is_Ultimo, is_Fav, is_Capturado, is_Original, equipo_id, generacion_id, primer_Tipo_id, segundo_tipo_id, id) VALUES ('Charizard', null, null, null, null, '#006', true,false, false, true, null, 1,11,26, NEXTVAL('hibernate_sequence'));
INSERT INTO POKEMON(nombre, estrellas, ataque_Rapido, ataque_Cargado, pC, id_Pokedex, is_Ultimo, is_Fav, is_Capturado, is_Original, equipo_id, generacion_id, primer_Tipo_id, segundo_tipo_id, id) VALUES ('Squirtle', null, null, null, null, '#007', false,false, false, true, null, 1,13,null, NEXTVAL('hibernate_sequence'));
INSERT INTO POKEMON(nombre, estrellas, ataque_Rapido, ataque_Cargado, pC, id_Pokedex, is_Ultimo, is_Fav, is_Capturado, is_Original, equipo_id, generacion_id, primer_Tipo_id, segundo_tipo_id, id) VALUES ('Wartotle', null, null, null, null, '#008', false,false, false, true, null, 1,13, null, NEXTVAL('hibernate_sequence'));
INSERT INTO POKEMON(nombre, estrellas, ataque_Rapido, ataque_Cargado, pC, id_Pokedex, is_Ultimo, is_Fav, is_Capturado, is_Original, equipo_id, generacion_id, primer_Tipo_id, segundo_tipo_id, id) VALUES ('Blastoise', null, null, null, null, '#009', true,false, false, true, null, 1,13,null, NEXTVAL('hibernate_sequence'));
INSERT INTO POKEMON(nombre, estrellas, ataque_Rapido, ataque_Cargado, pC, id_Pokedex, is_Ultimo, is_Fav, is_Capturado, is_Original, equipo_id, generacion_id, primer_Tipo_id, segundo_tipo_id, id) VALUES ('Dragonite', null, null, null, null, '#149', true, false,false, true, null, 1,15,26, NEXTVAL('hibernate_sequence'));
INSERT INTO POKEMON(nombre, estrellas, ataque_Rapido, ataque_Cargado, pC, id_Pokedex, is_Ultimo, is_Fav, is_Capturado, is_Original, equipo_id, generacion_id, primer_Tipo_id, segundo_tipo_id, id) VALUES ('Mewtwo', null, null, null, null, '#150', true,false, false, true, null, 1,22,null, NEXTVAL('hibernate_sequence'));
INSERT INTO POKEMON(nombre, estrellas, ataque_Rapido, ataque_Cargado, pC, id_Pokedex, is_Ultimo, is_Fav, is_Capturado, is_Original, equipo_id, generacion_id, primer_Tipo_id, segundo_tipo_id, id) VALUES ('Mew', null, null, null, null, '#151', true,false, false, true, null, 1, 22,null, NEXTVAL('hibernate_sequence'));



/*LIGAS*/
INSERT INTO LIGA(nombre, pc_maximos, id) VALUES('Liga Super Ball', 1500, NEXTVAL('hibernate_sequence'));
INSERT INTO LIGA(nombre,pc_maximos, id) VALUES('Liga Ultra Ball', 2500, NEXTVAL('hibernate_sequence'));
INSERT INTO LIGA(nombre,pc_maximos, id) VALUES('Liga Master Ball', null, NEXTVAL('hibernate_sequence'));