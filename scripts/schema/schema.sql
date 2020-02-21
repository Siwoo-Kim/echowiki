drop table revision;
drop table category_document_join;
drop table document;
drop table category;
drop table topic;


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
    id int identity (1, 1) primary key,
    next_id int,
    version varchar(100),
    document_id int not null,
    commit_by varchar(50) not null,
    message varchar(200) not null,
    created datetime default getdate(),
    updated datetime,
    deleted datetime,
    constraint revision_master_fk foreign key (next_id) references revision(id),
    constraint document_revision_fk foreign key (document_id) references document(id)
);

create table topic (
    id int identity (1, 1) primary key,
    topic_index varchar(100) not null,
    heading varchar(100) not null,
    parent_id int,
    document_id int not null,
    paragraph varchar(max),
    created datetime default getdate(),
    updated datetime,
    deleted datetime,
    constraint topic_parent_fk foreign key (parent_id) references topic(id),
    constraint topic_document_fk foreign key (document_id) references topic(id),
);
