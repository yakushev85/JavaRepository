drop table hripsite_objects;
drop table hripsite_object_types;
drop table hripsite_attributes;
drop table hripsite_parameters;

create table hripsite_objects (
object_id number(8) primary key,
object_type_id number(8),
name varchar2(200),
order_num number(8)
);

create table hripsite_object_types (
object_type_id number(8) primary key,
name varchar2(200),
description varchar2(500)
);

create table hripsite_attributes (
attribute_id number(8) primary key,
name varchar2(200),
description varchar2(500)
);

create table hripsite_parameters (
parameter_id number(8) primary key,
object_id number(8) not null,
attribute_id number(8),
reference_id number(8),
data_value varchar2(2000),
date_value date
);

CREATE SEQUENCE hripsite_objects_seq 
START WITH 100
INCREMENT BY 1
NOCACHE
NOCYCLE;

CREATE SEQUENCE hripsite_parameters_seq 
START WITH 100
INCREMENT BY 1
NOCACHE
NOCYCLE;

CREATE SEQUENCE hripsite_attributes_seq 
START WITH 100
INCREMENT BY 1
NOCACHE
NOCYCLE;

CREATE SEQUENCE hripsite_object_types_seq 
START WITH 100
INCREMENT BY 1
NOCACHE
NOCYCLE;

insert into hripsite_object_types values (1, 'Menu Item', 'Object type for menu items');
insert into hripsite_object_types values (2, 'Material', 'Object type for materials');
insert into hripsite_object_types values (3, 'Admin user', 'Administrator user account');

insert into hripsite_attributes values (1, 'NameEN', 'Name for english');
insert into hripsite_attributes values (2, 'Created', 'Created when');
insert into hripsite_attributes values (3, 'Author', 'Author for material');
insert into hripsite_attributes values (4, 'Content', 'Content for material');
insert into hripsite_attributes values (5, 'RefMaterials', 'Reference to materials for menu item');
insert into hripsite_attributes values (6,'Password','Password for users');

insert into hripsite_objects values (1, 1, 'News', 1);
insert into hripsite_objects values (2, 1, 'Contacts', 2);
insert into hripsite_objects values (3, 2, 'MatNews1', 0);
insert into hripsite_objects values (4, 2, 'MatNews2', 0);
insert into hripsite_objects values (5, 2, 'MatContacts', 0);
insert into hripsite_objects values (6, 3, 'admin', 0);

/* object 1 menu item */
insert into hripsite_parameters values (1, 1, 1, 0, 'News', null);
insert into hripsite_parameters values (2, 1, 5, 3, null, null);
insert into hripsite_parameters values (3, 1, 5, 4, null, null);
/* object 2 menu item */
insert into hripsite_parameters values (4, 2, 1, 0, 'Contacts', null);
insert into hripsite_parameters values (5, 2, 5, 5, null, null);
/* object 3 material */
insert into hripsite_parameters values (6, 3, 2, 0, null, sysdate);
insert into hripsite_parameters values (7, 3, 3, 0, 'Alex', null);
insert into hripsite_parameters values (8, 3, 4, 0, 'Some materials 1!Some materials 1!', null);
/* object 4 material */
insert into hripsite_parameters values (9, 4, 2, 0, null, sysdate);
insert into hripsite_parameters values (10, 4, 3, 0, 'Blex', null);
insert into hripsite_parameters values (11, 4, 4, 0, 'Some materials 2!Some materials 2!', null);
/* object 5 material */
insert into hripsite_parameters values (12, 5, 2, 0, null, sysdate);
insert into hripsite_parameters values (13, 5, 3, 0, 'Alex', null);
insert into hripsite_parameters values (14, 5, 4, 0, 'Ukraine Sumy Kharkivska 78', null);
/* object 6 admin user */
insert into hripsite_parameters values (15, 6, 6, 0, 'admin', sysdate);

commit;