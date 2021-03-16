INSERT INTO TIPO(nombre_Tipo, id) VALUES('Planta', NEXTVAL('hibernate_sequence'));
INSERT INTO TIPO(nombre_Tipo, id) VALUES('Veneno', NEXTVAL('hibernate_sequence'));
INSERT INTO TIPO(nombre_Tipo, id) VALUES('Fuego', NEXTVAL('hibernate_sequence'));

INSERT INTO POKEDEX(generacion, nombre, id) VALUES(1, 'Kanto', NEXTVAL('hibernate_sequence'));

INSERT INTO POKEMON(nombre, estrellas, ataque_Rapido, ataque_Cargado, pC, id_Pokedex, is_Ultimo, is_Capturado, evolucion_id, equipo_id, generacion_id, primer_Tipo_id, id) VALUES ('Bulbasaur', null, null, null, null, '#001', false, false, null, null, 4, 1, NEXTVAL('hibernate_sequence'));
INSERT INTO POKEMON(nombre, estrellas, ataque_Rapido, ataque_Cargado, pC, id_Pokedex, is_Ultimo, is_Capturado, evolucion_id, equipo_id, generacion_id, primer_Tipo_id, id) VALUES ('Ivysaur', null, null, null, null, '#002', false, false,null, null, 4, 1,NEXTVAL('hibernate_sequence'));
INSERT INTO POKEMON(nombre, estrellas, ataque_Rapido, ataque_Cargado, pC, id_Pokedex, is_Ultimo, is_Capturado, evolucion_id, equipo_id, generacion_id, primer_Tipo_id, id) VALUES ('Venasaur', null, null, null, null, '#003', true, false,null, null, 4, 1,NEXTVAL('hibernate_sequence'));
INSERT INTO POKEMON(nombre, estrellas, ataque_Rapido, ataque_Cargado, pC, id_Pokedex, is_Ultimo, is_Capturado, evolucion_id, equipo_id, generacion_id, primer_Tipo_id, id) VALUES ('Charmander', null, null, null, null, '#004', false, false, null, null, 4,3, NEXTVAL('hibernate_sequence'));
