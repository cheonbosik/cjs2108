/* 포토 갤러리 */
create table photo (
  idx   int  not null  auto_increment primary key,
  name  varchar(20) not null,			/* 올린 사람(닉네임) */
  part  varchar(20) not null,			/* 이미지 분류 */
  title varchar(100) not null,		/* 갤러리 제목 */
  content text not null,					/* 갤러리 내용(사진이 주 목정) */
  thumbnail varchar(150) not null,/* 썸네일 이미지 */
  firstFile varchar(150) not null,/* 첫번째 이미지 */
  wDate   datetime default now(),	/* 올린 날짜 */
  hostIp  varchar(50) not null,		/* 접속 사이트 */
  readNum	int	default 0 					/* 조회수 */
);

drop table photo;

desc photo;

select * from photo order by idx desc;

select year(now());
select month(now());
select day(now());

select * from photo;

SELECT wDate,date_add(wDate,INTERVAL 1 MONTH) FROM photo where year(wDate) = '2021' and month(wDate) = '12';
SELECT wDate,date_add(wDate,INTERVAL 1 DAY) FROM photo where year(wDate) = '2021' and month(wDate) = '12' and DAY(wDate) = '07';

update photo set wDate = DATE_ADD(wDate, INTERVAL 4 MONTH) where year(wDate) = '2021' and month(wDate) = '08';
update photo set wDate = DATE_ADD(wDate, INTERVAL 15 DAY) where year(wDate) = '2021' and month(wDate) = '12' and DAY(wDate) = '07';
