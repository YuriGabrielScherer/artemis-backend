create table athlete_graduation (
    id uuid primary key,
    id_belt uuid not null,
    id_athlete uuid not null,
    id_graduation uuid,
    situation varchar not null,
    grade decimal,
    created_date timestamp NOT null,
    created_by varchar NOT null,
    last_modified_date timestamp null,
    last_modified_by varchar NULL
);

create table graduation_grades (
    id uuid primary key,
	id_graduation uuid not null,
	id_professor uuid not null,
	id_athlete uuid not null,
	grade decimal not null,
	description varchar,
	created_date timestamp NOT null,
    created_by varchar NOT null,
    last_modified_date timestamp null,
    last_modified_by varchar NULL
);

-- FK GraduationGrades - Professor
alter table graduation_grades add constraint fk_graduation_grades_professor foreign key (id_professor) references professors(id);

-- FK GraduationGrades - Athlete
alter table graduation_grades add constraint fk_graduation_grades_athlete foreign key (id_athlete) references athletes(id);

-- FK GraduationGrades - Graduation
alter table graduation_grades add constraint fk_graduation_grades_graduation foreign key (id_graduation) references graduations(id);

-- FK AthleteGraduation - Athlete
alter table athlete_graduation add constraint fk_graduation_athlete foreign key(id_athlete) references athletes(id);

-- FK AthleteGraduation - Belt
alter table athlete_graduation add constraint fk_graduation_belt foreign key(id_belt) references belts(id);

-- FK AthleteGraduation - Graduation
alter table athlete_graduation add constraint fk_graduation_graduation foreign key(id_graduation) references graduations(id);

-- UK AthleteGraduation - Athlete and Graduation
alter table athlete_graduation add constraint uk_athlete_graduation unique(id_athlete, id_graduation);

-- UK GraduationGrades - Graduation and Athlete and Professor
alter table graduation_grades add constraint uk_graduation_athlete_professor unique(id_graduation,id_professor,id_athlete);