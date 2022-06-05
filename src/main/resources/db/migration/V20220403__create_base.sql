create table associations (
    id uuid not null primary key,
    code int not null,
    name varchar not null,
    city varchar,
    since timestamp,
    id_manager uuid not null,
    created_date timestamp NOT null,
    created_by varchar NOT null,
    last_modified_date timestamp null,
    last_modified_by varchar NULL
);
alter table associations add constraint uk_association_code unique(code);

create table people (
    id uuid primary key,
    code int not null,
    name varchar not null,
    document varchar(11),
    gender varchar(6) not null,
    birth timestamp,
    id_association uuid,
    created_date timestamp NOT null,
    created_by varchar NOT null,
    last_modified_date timestamp null,
    last_modified_by varchar NULL
);
alter table people add constraint uk_people_code unique(code);
alter table people add constraint uk_people_document unique(document);

create table athletes (
    id uuid primary key,
    since timestamp not null,
    id_person uuid not null unique,
    created_date timestamp NOT null,
    created_by varchar NOT null,
    last_modified_date timestamp null,
    last_modified_by varchar NULL
);

create table belts (
    id uuid primary key,
    belt varchar not null unique,
    min_months int not null,
    sequence int not null,
    created_date timestamp NOT null,
    created_by varchar NOT null,
    last_modified_date timestamp null,
    last_modified_by varchar NULL
);

create table graduations (
    id uuid primary key,
    title varchar not null,
    description varchar,
    place varchar,
    code int not null,
    "date" timestamp not null,
    created_date timestamp NOT null,
    created_by varchar NOT null,
    last_modified_date timestamp null,
    last_modified_by varchar NULL
);
alter table graduations add constraint uk_graduations_code unique(code);

create table graduation_history (
    id uuid primary key,
    situation varchar not null,
    id_graduation uuid not null,
    created_date timestamp NOT null,
    created_by varchar NOT null,
    last_modified_date timestamp null,
    last_modified_by varchar NULL
);

create table professors (
	id uuid primary key,
	id_person uuid not null unique,
	created_date timestamp NOT null,
    created_by varchar NOT null,
    last_modified_date timestamp null,
    last_modified_by varchar NULL
);

create table graduation_professors (
	id_professor uuid not null,
	id_graduation uuid not null,
	primary key (id_professor, id_graduation)
);

-- FK EventProfessors - Professor
alter table graduation_professors add constraint fk_graduation_professor_professor  foreign key (id_professor) references professors(id);

-- FK EventProfessors - Event
alter table graduation_professors add constraint fk_graduation_professor_event  foreign key (id_graduation) references graduations(id);

-- FK Professors - Person
alter table professors add constraint fk_person foreign key(id_person) references people(id);

-- FK Athletes - Person
alter table athletes add constraint fk_person foreign key(id_person) references people(id);

-- FK Associations - Person
alter table associations add constraint fk_manager foreign key(id_manager) references people(id);

-- FK People - Association
alter table people add constraint fk_association foreign key(id_association) references associations(id);

-- FK GraduationHistory - Graduation
alter table graduation_history add constraint fk_graduation_history_graduation foreign key(id_graduation) references graduations(id);

-- UK EventProfessors
alter table graduation_professors add constraint uk_graduation_professor unique(id_professor, id_graduation);

-- Default Belt Values
insert into belts (id, belt, min_months, sequence) values
('df9a7d2d-a9ae-4d8d-aa13-51433f070acc','WHITE', 3, 0),
('4f93ed79-d833-4e22-8be9-81a94ef54268','YELLOW', 6, 1),
('f005299f-ab8c-4cb6-8283-3edae27d8d57','RED', 9,2),
('2b418976-6687-4a73-986b-66d89872c2b8','ORANGE', 12,3),
('d7c89e0c-c670-4e07-9222-d94b44b3cec0','GREEN', 15,4),
('c620788f-f87c-4c12-b3ca-2af32e30ad0c','PURPLE', 18,5),
('d0db2306-832d-4b00-b5a0-844a4c3bba49','BROWN', 24,6),
('06624fe0-0669-48de-9bfd-0fba432aaf27','BLACK', 30,7),
('272c281f-3801-44c5-8318-34866a3391f0','BLACK_2',30,8),
('7bb9b1b4-415b-491c-9ab0-49d6e84bda07','BLACK_3',30,9),
('b38dd39c-15df-4421-81af-ad52fa557347','BLACK_4',30,10),
('2aa5e891-5295-46b5-a7fb-8614826f1fac','BLACK_5',30,11),
('6881a0bd-a1dd-425c-9e8d-3c8212ef0110','BLACK_6',30,12),
('bd073cc1-1b9b-4bb2-9522-93a16b80186b','BLACK_7',30,13),
('83d90d3f-390d-4622-b18c-1f6f867bf288','BLACK_8',30,14),
('e2e8ae54-b3ab-4142-9f97-c0f826aa03ab','BLACK_9',30,15),
('fa075adb-0338-4f88-be2b-4f52985e1493','BLACK_10',30,16);
