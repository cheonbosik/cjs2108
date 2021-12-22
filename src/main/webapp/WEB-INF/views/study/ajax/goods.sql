/* ---- 대분류 ---- */
create table goods1(
  product1 varchar(50) not null primary key  /* 대분류명 */
);
/* drop table goods1; */
/* delete from goods1; */
insert into goods1 values ('전자제품');
insert into goods1 values ('신발류');
insert into goods1 values ('의류');

/* ------ 중분류 ------ */
create table goods2 (
  product1 varchar(50) not null,   /* 대분류명 */
  product2 varchar(50) not null primary key,  /* 중분류명 */
  foreign key(product1) references goods1(product1)
  on update cascade
  on delete restrict
);
/* drop table goods */
/* delete from goods */
insert into goods2 value ('전자제품','LG');
insert into goods2 value ('전자제품','삼성');
insert into goods2 value ('전자제품','현대');
insert into goods2 value ('신발류','나이키');
insert into goods2 value ('신발류','아디다스');
insert into goods2 value ('신발류','K2');
insert into goods2 value ('의류','크로커다일');
insert into goods2 value ('의류','노스페이스');
insert into goods2 value ('의류','블랙야크');
select * from goods2;

/* ----------- 소분류 ------------- */
create table goods3 (
  idx      int not null auto_increment primary key,
  product1 varchar(50) not null,   						/* 대분류명 */
  product2 varchar(50) not null,  						/* 중분류명 */
  product3 varchar(50) not null,    					/* 소분류명 */
  foreign key(product1) references goods1(product1)
  on update cascade
  on delete restrict,
  foreign key(product2) references goods2(product2)
  on update cascade
  on delete restrict
);
/* drop table goods3 */
/* delete from goods3 */
insert into goods3 values (default,'전자제품','LG','냉장고');
insert into goods3 values (default,'전자제품','LG','선풍기');
insert into goods3 values (default,'전자제품','LG','세탁기');
insert into goods3 values (default,'전자제품','LG','오디오');
insert into goods3 values (default,'전자제품','LG','청소기');
insert into goods3 values (default,'전자제품','삼성','냉장고');
insert into goods3 values (default,'전자제품','삼성','세탁기');
insert into goods3 values (default,'전자제품','삼성','스타일러');
insert into goods3 values (default,'전자제품','현대','세탁기');
insert into goods3 values (default,'전자제품','현대','건조기');
insert into goods3 values (default,'신발류','나이키','등산화');
insert into goods3 values (default,'신발류','나이키','샌달');
insert into goods3 values (default,'신발류','나이키','운동화');
insert into goods3 values (default,'신발류','아디다스','트래킹화');
insert into goods3 values (default,'신발류','아디다스','실내화');
insert into goods3 values (default,'신발류','아디다스','운동화');
insert into goods3 values (default,'신발류','K2','등산화');
insert into goods3 values (default,'신발류','K2','운동화');
insert into goods3 values (default,'신발류','K2','욕실화');
insert into goods3 values (default,'신발류','K2','실내화');
insert into goods3 values (default,'의류','크로커다일','언더웨어');
insert into goods3 values (default,'의류','크로커다일','스웨터');
insert into goods3 values (default,'의류','크로커다일','바지');
insert into goods3 values (default,'의류','크로커다일','와이셔츠');
insert into goods3 values (default,'의류','노스페이스','언더웨어');
insert into goods3 values (default,'의류','노스페이스','스웨터');
insert into goods3 values (default,'의류','노스페이스','패딩');
insert into goods3 values (default,'의류','노스페이스','바지');
insert into goods3 values (default,'의류','블랙야크','언더웨어');
insert into goods3 values (default,'의류','블랙야크','잠바');

select * from goods1;
select * from goods2;
select * from goods3;