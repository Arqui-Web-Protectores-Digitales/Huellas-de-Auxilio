--ROLES
INSERT INTO rol (tipo_rol) VALUES ('ROLE_CIUDADANO');
INSERT INTO rol (tipo_rol) VALUES ('ROLE_ENTIDAD');

--USUARIOS, todas las contraseñas son: 123456
INSERT INTO usuario (correo, telefono, contraseña, estado_usuario, id_rol) VALUES ('juan@gmail.com', '999999999', '$2a$12$1k34YdrmxBkVborQvZLh2OUvX1S80GVVQjZJ5H55y1eez7XV.nV06', true, 1);
INSERT INTO usuario (correo, telefono, contraseña, estado_usuario, id_rol) VALUES ('maria@gmail.com', '988888888', '$2a$12$1k34YdrmxBkVborQvZLh2OUvX1S80GVVQjZJ5H55y1eez7XV.nV06', true, 1);
INSERT INTO usuario (correo, telefono, contraseña, estado_usuario, id_rol) VALUES ('refugiopatitas@gmail.com', '977777777', '$2a$12$1k34YdrmxBkVborQvZLh2OUvX1S80GVVQjZJ5H55y1eez7XV.nV06', true, 2);
INSERT INTO usuario (correo, telefono, contraseña, estado_usuario, id_rol) VALUES ('veterinaria@gmail.com', '966666666', '$2a$12$1k34YdrmxBkVborQvZLh2OUvX1S80GVVQjZJ5H55y1eez7XV.nV06', true, 2);

--PERFILES, ciudadanos y entidades
INSERT INTO ciudadano (dni, distrito, nombre_completo, id_usuario) VALUES ('12345678', 'San Miguel', 'Juan Perez', 1);
INSERT INTO ciudadano (dni, distrito, nombre_completo, id_usuario) VALUES ('87654321', 'Miraflores', 'Maria Lopez', 2);

INSERT INTO entidad (nombre_entidad, tipo_entidad, zona_atencion, id_usuario, latitud, longitud) VALUES ('Refugio Patitas', 'Refugio', 'San Miguel', 3, -12.0765, -77.0943);
INSERT INTO entidad (nombre_entidad, tipo_entidad, zona_atencion, id_usuario, latitud, longitud) VALUES ('Veterinaria Central', 'Veterinaria', 'Miraflores', 4, -12.1220, -77.0310);

--UBICACIONES DE REPORTES
INSERT INTO ubicacion (direccion, distrito, referencia, latitud, longitud) VALUES ('Av. La Marina 2000', 'San Miguel', 'Frente al parque', -12.0765, -77.0943);
INSERT INTO ubicacion (direccion, distrito, referencia, latitud, longitud) VALUES ('Ovalo Gutierrez', 'Miraflores', 'Cerca al cine', -12.1100, -77.0350);
INSERT INTO ubicacion (direccion, distrito, referencia, latitud, longitud) VALUES ('Parque Kennedy', 'Miraflores', 'En el centro', -12.1215, -77.0295);

--REPORTES DE MALTRATO/ABANDONO
-- Juan reporta al Refugio Patitas (Estado: Recibido)
INSERT INTO reporte (id_ubicacion, id_ciudadano, id_entidad, fecha_reporte, tipo_caso, nivel_urgencia, descripcion, estado) VALUES (1, 1, 1, '2026-10-15', 'M', 'AL', 'Perrito amarrado bajo el sol sin agua todo el dia', 'R');
-- Maria reporta a Veterinaria Central (Estado: En Revisión)
INSERT INTO reporte (id_ubicacion, id_ciudadano, id_entidad, fecha_reporte, tipo_caso, nivel_urgencia, descripcion, estado) VALUES (2, 2, 2, '2026-10-18', 'A', 'ME', 'Gatito abandonado en caja de carton', 'ER');
-- Juan reporta a Veterinaria Central (Estado: Atendido)
INSERT INTO reporte (id_ubicacion, id_ciudadano, id_entidad, fecha_reporte, tipo_caso, nivel_urgencia, descripcion, estado) VALUES (3, 1, 2, '2026-10-20', 'A', 'BA', 'Paloma con ala rota en el parque', 'AT');

--MASCOTAS EN ADOPCIÓN
--Refugio Patitas publica
INSERT INTO mascota (id_entidad, nombre, edad, sexo, distrito, tamaño, estado, especie, url_foto, descripcion) VALUES (1, 'Max', 'Adulto', 'Macho', 'San Miguel', 'Grande', true, 'Perro', 'https://images.dog.ceo/breeds/labrador/n02099712_7418.jpg', 'Cariñoso, jugueton y muy protector.');
INSERT INTO mascota (id_entidad, nombre, edad, sexo, distrito, tamaño, estado, especie, url_foto, descripcion) VALUES (1, 'Luna', 'Cachorro', 'Hembra', 'San Miguel', 'Pequeño', true, 'Gato', 'https://cdn2.thecatapi.com/images/MTY3ODIyMQ.jpg', 'Muy tranquila, ideal para departamentos.');
--Veterinaria Central publica
INSERT INTO mascota (id_entidad, nombre, edad, sexo, distrito, tamaño, estado, especie, url_foto, descripcion) VALUES (2, 'Rocky', 'Joven', 'Macho', 'Miraflores', 'Mediano', true, 'Perro', 'https://images.dog.ceo/breeds/beagle/n02088364_12124.jpg', 'Lleno de energia, necesita espacio para correr.');

--SOLICITUDES DE ADOPCIÓN
--Juan quiere adoptar a Luna (En revisión)
INSERT INTO solicitud (id_mascota, id_ciudadano, fecha_solicitud, tipo_vivienda, motivo, experiencia, estado_solicitud) VALUES (2, 1, '2026-10-25 10:30:00', 'Departamento', 'Busco compania', true, 'EN_REVISION');
--Maria quiere adoptar a Max (Aprobada)
INSERT INTO solicitud (id_mascota, id_ciudadano, fecha_solicitud, tipo_vivienda, motivo, experiencia, estado_solicitud) VALUES (1, 2, '2026-10-26 14:15:00', 'Casa', 'Tenemos espacio', true, 'APROBADA');
--Juan queria adoptar a Max pero fue Rechazado (porque Maria lo adoptó)
INSERT INTO solicitud (id_mascota, id_ciudadano, fecha_solicitud, tipo_vivienda, motivo, experiencia, estado_solicitud) VALUES (1, 1, '2026-10-26 09:00:00', 'Departamento', 'Me gusta la raza', false, 'RECHAZADA');