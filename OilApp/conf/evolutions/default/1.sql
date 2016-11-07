# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table campos (
  id                        bigserial not null,
  jefe_de_campo_id          bigint,
  region                    varchar(255),
  localidad                 varchar(255),
  constraint uq_campos_jefe_de_campo_id unique (jefe_de_campo_id),
  constraint pk_campos primary key (id))
;

create table reportes_caudal (
  id                        bigserial not null,
  pozo_id                   bigint,
  fecha_envio               timestamp,
  caudal                    float,
  constraint pk_reportes_caudal primary key (id))
;

create table reportes_Emergencia (
  id                        bigserial not null,
  fecha_envio               timestamp,
  pozo_id                   bigint,
  emergencia                varchar(255),
  constraint pk_reportes_Emergencia primary key (id))
;

create table reportes_Energia (
  id                        bigserial not null,
  pozo_id                   bigint,
  fecha_envio               timestamp,
  consumo_energia           float,
  constraint pk_reportes_Energia primary key (id))
;

create table reportes_Temperatura (
  id                        bigserial not null,
  pozo_id                   bigint,
  fecha_envio               timestamp,
  temperatura               float,
  constraint pk_reportes_Temperatura primary key (id))
;

create table permiso (
  id                        bigserial not null,
  value                     varchar(255),
  constraint pk_permiso primary key (id))
;

create table pozos (
  id                        bigserial not null,
  latitud                   float,
  longitud                  float,
  estado                    varchar(10),
  campo_id                  bigint,
  constraint ck_pozos_estado check (estado in ('ABIERTO','PRODUCCION','PARADO','CLAUSURADO')),
  constraint pk_pozos primary key (id))
;

create table rol (
  id                        bigserial not null,
  name                      varchar(255),
  constraint pk_rol primary key (id))
;

create table usuarios (
  id                        bigserial not null,
  username                  varchar(255),
  password                  varchar(255),
  nombre                    varchar(255),
  avatar                    varchar(255),
  edad                      integer,
  cargo                     varchar(255),
  constraint pk_usuarios primary key (id))
;


create table usuarios_rol (
  usuarios_id                    bigint not null,
  rol_id                         bigint not null,
  constraint pk_usuarios_rol primary key (usuarios_id, rol_id))
;

create table usuarios_permiso (
  usuarios_id                    bigint not null,
  permiso_id                     bigint not null,
  constraint pk_usuarios_permiso primary key (usuarios_id, permiso_id))
;
alter table campos add constraint fk_campos_jefeDeCampo_1 foreign key (jefe_de_campo_id) references usuarios (id);
create index ix_campos_jefeDeCampo_1 on campos (jefe_de_campo_id);
alter table reportes_caudal add constraint fk_reportes_caudal_pozo_2 foreign key (pozo_id) references pozos (id);
create index ix_reportes_caudal_pozo_2 on reportes_caudal (pozo_id);
alter table reportes_Emergencia add constraint fk_reportes_Emergencia_pozo_3 foreign key (pozo_id) references pozos (id);
create index ix_reportes_Emergencia_pozo_3 on reportes_Emergencia (pozo_id);
alter table reportes_Energia add constraint fk_reportes_Energia_pozo_4 foreign key (pozo_id) references pozos (id);
create index ix_reportes_Energia_pozo_4 on reportes_Energia (pozo_id);
alter table reportes_Temperatura add constraint fk_reportes_Temperatura_pozo_5 foreign key (pozo_id) references pozos (id);
create index ix_reportes_Temperatura_pozo_5 on reportes_Temperatura (pozo_id);
alter table pozos add constraint fk_pozos_campo_6 foreign key (campo_id) references campos (id);
create index ix_pozos_campo_6 on pozos (campo_id);



alter table usuarios_rol add constraint fk_usuarios_rol_usuarios_01 foreign key (usuarios_id) references usuarios (id);

alter table usuarios_rol add constraint fk_usuarios_rol_rol_02 foreign key (rol_id) references rol (id);

alter table usuarios_permiso add constraint fk_usuarios_permiso_usuarios_01 foreign key (usuarios_id) references usuarios (id);

alter table usuarios_permiso add constraint fk_usuarios_permiso_permiso_02 foreign key (permiso_id) references permiso (id);

# --- !Downs

drop table if exists campos cascade;

drop table if exists reportes_caudal cascade;

drop table if exists reportes_Emergencia cascade;

drop table if exists reportes_Energia cascade;

drop table if exists reportes_Temperatura cascade;

drop table if exists permiso cascade;

drop table if exists pozos cascade;

drop table if exists rol cascade;

drop table if exists usuarios cascade;

drop table if exists usuarios_rol cascade;

drop table if exists usuarios_permiso cascade;

