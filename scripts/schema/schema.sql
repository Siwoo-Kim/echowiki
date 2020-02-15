drop table revision;
drop table category_document_join;
drop table document;
drop table category;

-- CATEGORY TABLE
create table category (
      id int identity(1, 1) primary key,
      title varchar(50) not null unique,
      parent_id int,
      created datetime default getdate(),
      updated datetime,
      deleted datetime,
      constraint category_parent_fk foreign key (parent_id) references category(id)
);

create table document (
      id int identity (1, 1) primary key,
      title varchar(250) not null,
      category_id int not null,
      parent_id int,
      created datetime default getdate(),
      updated datetime,
      deleted datetime,
      constraint document_category_fk foreign key (category_id) references category(id),
);

create table category_document_join (
    document_id int,
    category_id int,
    primary key (document_id, category_id),
    constraint category_document_join_document_fk foreign key (document_id) references document(id),
    constraint category_document_join_category_fk foreign key (category_id) references category(id)
);

create table revision (
    version varchar(100) primary key,
    master_version varchar(100),
    document_id int not null,
    commit_by varchar(50) not null,
    created datetime default getdate(),
    updated datetime,
    deleted datetime,
    constraint revision_master_fk foreign key (master_version) references revision(version),
    constraint document_revision_fk foreign key (document_id) references document(id)
);

insert into category VALUES ('TEST-1', null, null, null, null);
insert into category VALUES ('TEST-1-1', 1, null, null, null);
insert into category VALUES ('TEST-1-2', 1, null, null, null);
insert into category VALUES ('TEST-1-3', 1, null, null, null);
insert into category VALUES ('TEST-1-4', 1, null, null, null);
insert into category VALUES ('TEST-1-5', 1, null, null, null);
insert into category VALUES ('TEST-1-6', 1, null, null, null);

insert into category VALUES ('TEST-1-1-1', 2, null, null, null);
insert into category VALUES ('TEST-1-1-2', 2, null, null, null);
insert into category VALUES ('TEST-1-1-3', 2, null, null, null);
insert into category VALUES ('TEST-1-2-1', 3, null, null, null);
insert into category VALUES ('TEST-1-2-2', 3, null, null, null);
insert into category VALUES ('TEST-1-2-3', 3, null, null, null);
