# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table campos (
  id                        bigserial not null,
  jefe_de_campo_id          bigint,
  region                    varchar(255),
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

create table pozos (
  id                        bigserial not null,
  estado                    varchar(255),
  campo_id                  bigint,
  constraint pk_pozos primary key (id))
;

create table sensores (
  id                        bigserial not null,
  pozo_id                   bigint,
  tipo                      varchar(255),
  constraint uq_sensores_pozo_id unique (pozo_id),
  constraint pk_sensores primary key (id))
;

create table usuarios (
  id                        bigint not null,
  nombre                    varchar(255),
  nivel_acceso              integer,
  constraint pk_usuarios primary key (id))
;

create table zona (
  id                        bigserial not null,
  nombre                    varchar(255),
  area                      float,
  constraint pk_zona primary key (id))
;

create sequence usuariosId;

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
alter table sensores add constraint fk_sensores_pozo_7 foreign key (pozo_id) references pozos (id);
create index ix_sensores_pozo_7 on sensores (pozo_id);



# --- !Downs

drop table if exists campos cascade;

drop table if exists reportes_caudal cascade;

drop table if exists reportes_Emergencia cascade;

drop table if exists reportes_Energia cascade;

drop table if exists reportes_Temperatura cascade;

drop table if exists pozos cascade;

drop table if exists sensores cascade;

drop table if exists usuarios cascade;

drop table if exists zona cascade;

drop sequence if exists usuariosId;

