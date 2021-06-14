create table departement (
    id int(10) AUTO_INCREMENT PRIMARY KEY,
    numero varchar(3) NOT NULL,
    id_region int(10)
);

create table region (
    id int(10) AUTO_INCREMENT PRIMARY KEY,
    code varchar(10) NOT NULL,
    nom varchar(100) NOT NULL
);

create table ville (
    id int(10) AUTO_INCREMENT PRIMARY KEY,
    code varchar(10) NOT NULL,
    nom varchar(100) NOT NULL,
    population int(10) NOT NULL,
    id_region int(10) NOT NULL,
    id_dept int(10) NOT NULL
);

alter table ville add constraint FK_VILLE_DEPT foreign key (ID_DEPT) REFERENCES DEPARTEMENT(ID); 
alter table ville add constraint FK_VILLE_REGION foreign key (ID_REGION) REFERENCES REGION(ID);
alter table departement add constraint FK_DEPT_REGION foreign key (ID_REGION) REFERENCES REGION(ID);  