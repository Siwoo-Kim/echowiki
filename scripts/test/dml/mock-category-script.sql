-- Category for echo wiki
insert into category (name, namespace, updated, deleted) VALUES ('에코위키', '에코위키',  null, null);

insert into category (name, namespace, updated, deleted) VALUES ('에코위키의 도움말', '에코위키', null, null);

insert into category_join (parent_id, child_id)
    (
        select parent.id, child.id from category child cross join category parent
        where parent.name = '에코위키' and parent.namespace ='에코위키' and child.name = '에코위키의 도움말' and child.namespace ='에코위키'
    );


insert into category (name, namespace, updated, deleted) VALUES ('도움말', '에코위키',  null, null);

insert into category_join (parent_id, child_id)
    (
        select parent.id, child.id from category child cross join category parent
        where parent.name = '에코위키의 도움말' and parent.namespace ='에코위키' and child.name = '도움말' and child.namespace ='에코위키'
    );


insert into category (name, namespace, updated, deleted)  VALUES ('역사', '일반', null, null);
insert into category (name, namespace, updated, deleted)  VALUES ('아시아사', '일반', null, null);
insert into category (name, namespace, updated, deleted)  VALUES ('유럽사', '일반', null, null);
insert into category (name, namespace, updated, deleted)  VALUES ('동아시아사', '일반', null, null);

-- Category for normal
insert into category_join (parent_id, child_id)
    (
        select parent.id, child.id from category child cross join category parent
        where parent.name = '역사' and parent.namespace = '일반' and  child.name = '아시아사' and child.namespace = '일반'
    );

insert into category_join (parent_id, child_id)
    (
        select parent.id, child.id from category child cross join category parent
        where parent.name = '역사' and parent.namespace = '일반' and  child.name = '유럽사' and child.namespace = '일반'
    );

insert into category_join (parent_id, child_id)
    (
        select parent.id, child.id from category child cross join category parent
        where parent.name = '역사' and parent.namespace = '일반' and  child.name = '동아시아사' and child.namespace = '일반'
    );

insert into category (name, namespace, updated, deleted)  VALUES ('한국사', '일반', null, null);
insert into category (name, namespace, updated, deleted)  VALUES ('일본사', '일반', null, null);
insert into category (name, namespace, updated, deleted)  VALUES ('중국사', '일반', null, null);


insert into category_join (parent_id, child_id)
    (
        select parent.id, child.id from category child cross join category parent
        where parent.name = '동아시아사' and parent.namespace = '일반' and  child.name = '한국사' and child.namespace = '일반'
    );

insert into category_join (parent_id, child_id)
    (
        select parent.id, child.id from category child cross join category parent
        where parent.name = '동아시아사' and parent.namespace = '일반' and  child.name = '일본사' and child.namespace = '일반'
    );

insert into category_join (parent_id, child_id)
    (
        select parent.id, child.id from category child cross join category parent
        where parent.name = '동아시아사' and parent.namespace = '일반' and  child.name = '중국사' and child.namespace = '일반'
    );

insert into category (name, namespace, updated, deleted)  VALUES ('조선', '일반', null, null);
insert into category (name, namespace, updated, deleted)  VALUES ('고려', '일반', null, null);
insert into category (name, namespace, updated, deleted)  VALUES ('고조선', '일반', null, null);

insert into category_join (parent_id, child_id)
    (
        select parent.id, child.id from category child cross join category parent
        where parent.name = '한국사' and parent.namespace = '일반' and  child.name = '조선' and child.namespace = '일반'
    );

insert into category_join (parent_id, child_id)
    (
        select parent.id, child.id from category child cross join category parent
        where parent.name = '한국사' and parent.namespace = '일반' and  child.name = '고려' and child.namespace = '일반'
    );

insert into category_join (parent_id, child_id)
    (
        select parent.id, child.id from category child cross join category parent
        where parent.name = '한국사' and child.name = '고조선' and child.namespace = '일반'
    );

insert into category (name, namespace, updated, deleted)  VALUES ('조선 의병', '일반', null, null);
insert into category (name, namespace, updated, deleted)  VALUES ('조선/건축', '일반', null, null);
insert into category (name, namespace, updated, deleted)  VALUES ('조선/인물', '일반', null, null);
insert into category (name, namespace, updated, deleted)  VALUES ('조선의 관직', '일반', null, null);
insert into category (name, namespace, updated, deleted)  VALUES ('조선의 역사', '일반', null, null);


insert into category_join (parent_id, child_id)
    (
        select parent.id, child.id from category child cross join category parent
        where parent.name = '조선' and parent.namespace = '일반' and  child.name = '조선 의병' and child.namespace = '일반'
    );

insert into category_join (parent_id, child_id)
    (
        select parent.id, child.id from category child cross join category parent
        where parent.name = '조선' and parent.namespace = '일반' and  child.name = '조선/건축' and child.namespace = '일반'
    );

insert into category_join (parent_id, child_id)
    (
        select parent.id, child.id from category child cross join category parent
        where parent.name = '조선' and parent.namespace = '일반' and  child.name = '조선/인물' and child.namespace = '일반'
    );

insert into category_join (parent_id, child_id)
    (
        select parent.id, child.id from category child cross join category parent
        where parent.name = '조선' and parent.namespace = '일반' and  child.name = '조선의 관직' and child.namespace = '일반'
    );

insert into category_join (parent_id, child_id)
    (
        select parent.id, child.id from category child cross join category parent
        where parent.name = '조선' and parent.namespace = '일반' and  child.name = '조선의 역사' and child.namespace = '일반'
    );

