create table mainImage(
  idx   int not null,							/* 메인이미지관리 고유번호 */
  part  varchar(20) not null,			/* 어디에 사용될것인지 기록되는곳 */
  mainFName varchar(100) not null	/* 서버에 저장될 메인 이미지명 */
);

select * from mainImage order by idx desc;
select max(idx) from mainImage; 
select * from mainImage where idx = (select max(idx) from mainImage); 
select * from mainImage group by idx;
select * from mainImage group by idx order by part, idx desc;
