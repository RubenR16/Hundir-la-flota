create database hundir_la_flota;
-- drop database hundir_la_flota;
use hundir_la_flota;
create table usuarios(
cod int auto_increment primary key,
nombre_usuario varchar(20) unique not null,
password_usuario varchar(50) not null
);
select * from usuarios;