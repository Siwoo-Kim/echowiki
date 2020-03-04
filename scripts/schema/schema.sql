drop table namespace;
drop table document_commit;
drop table document;
drop table category_join;
drop table category;


create table namespace (
    name varchar(255) primary key
);

-- CATEGORY TABLE
create table category (
      id int identity(1, 1) primary key,
      name varchar(50) not null,
      namespace varchar(255) not null,
      created datetime default getdate(),
      updated datetime,
      deleted datetime,
);

-- Unique for two columns
create unique index category_name_unique on category (namespace, name);

create table category_join (
    parent_id int references category(id),
    child_id int references category(id),
    primary key (parent_id, child_id),
    constraint ck_category_ref_itself check (parent_id <> child_id)
)

create table document (
      id int identity (1, 1) primary key,
      name varchar(250) not null,   --should be null but handled in applicaiton level
      content varchar(max),
      category_id int,
      created datetime default getdate(),
      updated datetime,
      deleted datetime,
      constraint fk_document_category foreign key (category_id) references category(id)
);

create table document_commit (
    id int identity (1, 1) primary key,
    commit_index int,
    previous_id int,
    document_id int not null,
    trunk bit not null,
    created datetime default getdate(),
    updated datetime,
    deleted datetime,
    constraint fk_commit_previous foreign key (previous_id) references document_commit(id),
    constraint fk_document_commit foreign key (document_id) references document(id)
);

insert into namespace values ('일반');
insert into namespace values ('사용자');
insert into namespace values ('에코위키');
insert into namespace values ('템플릿');
