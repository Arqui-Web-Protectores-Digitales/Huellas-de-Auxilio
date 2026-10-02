--ROLES
INSERT INTO rol (tipo_rol) VALUES ('ROLE_CIUDADANO');
INSERT INTO rol (tipo_rol) VALUES ('ROLE_ENTIDAD');

--USUARIOS (ambos tienen contraseña 123456)
INSERT INTO usuario (correo, telefono, contraseña, estado_usuario, id_rol) VALUES ('juan@gmail.com', '999999999', '$2a$12$1k34YdrmxBkVborQvZLh2OUvX1S80GVVQjZJ5H55y1eez7XV.nV06', true, 1);
INSERT INTO usuario (correo, telefono, contraseña, estado_usuario, id_rol) VALUES ('refugio@gmail.com', '888888888', '$2a$12$1k34YdrmxBkVborQvZLh2OUvX1S80GVVQjZJ5H55y1eez7XV.nV06', true, 2);

--PERFILES
INSERT INTO ciudadano (dni, distrito, nombre_completo, id_usuario) VALUES ('12345678', 'San Miguel', 'Juan Perez', 1);
INSERT INTO entidad (nombre_entidad, tipo_entidad, zona_atencion, id_usuario) VALUES ('Refugio Patitas', 'Refugio', 'San Miguel', 2);

--UBICACIÓN (para el reporte)
INSERT INTO ubicacion (direccion, distrito, referencia, latitud, longitud) VALUES ('Av. La Marina 2000', 'San Miguel', 'Frente al parque', -12.0765, -77.0943);

--REPORTE (de Juan para el Refugio)
INSERT INTO reporte (id_ubicacion, id_ciudadano, id_entidad, fecha_reporte, tipo_caso, nivel_urgencia, descripcion, estado) VALUES (1, 1, 1, '2026-10-15', 'M', 'AL', 'Perrito amarrado bajo el sol sin agua', 'R');

--MASCOTA (publicada por el Refugio)
INSERT INTO mascota (id_entidad, nombre, edad, sexo, distrito, tamaño, estado, especie, url_foto, descripcion) VALUES (1, 'Max', 'Adulto', 'Macho', 'Miraflores', 'Grande', true, 'Perro', 'max.png', 'Cariñoso, jugueton y muy activo');

--SOLICITUD DE ADOPCIÓN (de Juan queriendo adoptar a Max)
INSERT INTO solicitud (id_mascota, id_ciudadano, fecha_solicitud, tipo_vivienda, motivo, experiencia, estado_solicitud) VALUES (1, 1, '2026-10-16 10:30:00', 'Casa', 'Quiero un compañero', true, 'EN_REVISION');