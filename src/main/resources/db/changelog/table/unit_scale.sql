--liquibase formatted sql

--changeset kilel:add_unit_scale_table
create table unit_scale
(
  type_id bigint           not null,
  unit_id bigint           not null,
  scale   decimal(100, 20) not null,
  dscr    longnvarchar     not null,

  constraint uk_unit_scales unique (type_id, unit_id)
)
