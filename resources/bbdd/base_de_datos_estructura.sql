# Tabla Uusarios. Clientes, Dueños de restaurantes y Admin
CREATE TABLE Usuarios (
	email VARCHAR(64) NOT NULL,
	password VARCHAR(255) NOT NULL,
	tipo_usuario CHAR(1) NOT NULL,
	nombre VARCHAR(32) NOT NULL,
	apellido VARCHAR(32) NOT NULL,
	fecha_nacimiento DATE NOT NULL,
	telefono VARCHAR(16) NOT NULL,
	ciudad VARCHAR(32) NOT NULL,
	provincia VARCHAR(32) NOT NULL,
	pais VARCHAR(32) NOT NULL,
	codigo_postal VARCHAR(5) NOT NULL,
	fecha_registro DATE NOT NULL,
	PRIMARY KEY (email)
);

# Tabla Restaurantes
CREATE TABLE Restaurantes (
	id_restaurante INTEGER NOT NULL AUTO_INCREMENT,
	nombre VARCHAR(32) NOT NULL,
	telefono VARCHAR(16) NOT NULL,
	direccion VARCHAR(32) NOT NULL,
	ciudad VARCHAR(32) NOT NULL,
	provincia VARCHAR(32) NOT NULL,
	pais VARCHAR(32) NOT NULL,
	codigo_postal VARCHAR(5) NOT NULL,
	tipo_cocina VARCHAR(16) NOT NULL,
	valoracion FLOAT NOT NULL,
	fecha_registro DATE NOT NULL,
	usuario_email VARCHAR(64) NOT NULL,
	PRIMARY KEY (id_restaurante),
	FOREIGN KEY (usuario_email)
	REFERENCES Usuarios (email) ON DELETE CASCADE ON UPDATE CASCADE
);

# Tabla Productos. Productos que ofrece cada restaurante
CREATE TABLE Productos (
	id_producto INTEGER NOT NULL AUTO_INCREMENT,
	nombre VARCHAR(64) NOT NULL,
	descripcion VARCHAR(256) NOT NULL,
	precio VARCHAR(8) NOT NULL,
	disponibilidad CHAR(1) NOT NULL,
	fecha_anadido DATE NOT NULL,
	restaurante_id INTEGER NOT NULL,
	PRIMARY KEY (id_producto),
	FOREIGN KEY (restaurante_id)
	REFERENCES Restaurantes (id_restaurante) ON DELETE CASCADE ON UPDATE CASCADE
);

#Tabla Favoritos. Restaurantes que cada cliente ha señalado como favorito
CREATE TABLE Favoritos (
	usuario_email VARCHAR(64) NOT NULL,
	restaurante_id INTEGER NOT NULL,
	fecha_favorito DATETIME NOT NULL,
	PRIMARY KEY (usuario_email, restaurante_id),
	FOREIGN KEY (usuario_email)
	REFERENCES Usuarios (email) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (restaurante_id)
	REFERENCES Restaurantes (id_restaurante) ON DELETE CASCADE ON UPDATE CASCADE
);

# Tabla Opiniones. Opiniones que los Usuarios escriben sobre los Restaurantes
CREATE TABLE Opiniones (
	restaurante_id INTEGER NOT NULL,
	usuario_email VARCHAR(64) NOT NULL,
	opinion VARCHAR(256) NOT NULL,
	fecha_opinion DATETIME NOT NULL,
	valoracion CHAR(1) NOT NULL,
	revisar CHAR(1) NOT NULL,
	PRIMARY KEY (usuario_email, restaurante_id),
	FOREIGN KEY (usuario_email)
	REFERENCES Usuarios (email) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (restaurante_id)
	REFERENCES Restaurantes (id_restaurante) ON DELETE CASCADE ON UPDATE CASCADE
);