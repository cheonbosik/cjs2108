/* board.sql */

show tables;

create table board (
	idx  int not null auto_increment,	/* 게시글의 고유번호 */
	nickName varchar(20)  not null,		/* 게시글 올린사람의 닉네임 */
	title    varchar(100) not null,		/* 게시글의 글 제목 */
	email 	 varchar(50),							/* 올린이의 메일주소 */
	homePage varchar(50),							/* 올린이의 홈페이지주소(블로그주소) */
	content  text not null,						/* 글 내용 */
	wDate    datetime default now(),  /* 글쓴 날짜(기본값:현재날짜/시간) */
	readNum  int default 0,						/* 글 조회수 */
	hostIp	 varchar(50) not null,		/* 접속 IP주소 */
	good		 int  default 0,					/* 좋아요 */
	mid			 varchar(20) not null,		/* 회원 아이디(게시글 조회시 사용) */
	primary  key(idx)									/* 기본키 : 글 고유번호 */
);
drop table board;
desc board;

alter table board change column goods good int default 0;

insert into board values (default,'관리맨','게시판 서비스를 시작합니다.','cjsk1126@naver.com','blog.daum.net/cjsk1126','이곳은 게시판입니다. 좋은글 많이 부탁드려요',default,default,'218.236.203.146',default,'admin');

/* 이전글 / 다음글 연습 */
select * from board where idx = 20;
select * from board where idx < 20 order by idx desc limit 1;	/* 이전글 */
select * from board where idx > 20 limit 1;		/* 다음글 */

select * from board order by idx desc;

/* 
  DATEDIFF(날짜1, 날짜2) : '날짜1 - 날자2'의 값을 반환처리 
  TIMESTAMPDIFF(단위, 날짜1, 날짜2) :
    단위 : YEAR(년)/QUARTER(분기)/MONTH(월)/WEEK(주)/DAY(일)/HOUR(시)/MINUTE(분)/SECOND(초)
*/
SELECT DATEDIFF('2021-12-28', '2015-10-25');
SELECT TIMESTAMPDIFF(YEAR, '2015-10-25', '2021-12-28');
SELECT TIMESTAMPDIFF(MONTH, '2020-10-25', '2021-12-28');
SELECT TIMESTAMPDIFF(DAY, '2020-12-28', '2021-12-28');
SELECT TIMESTAMPDIFF(HOUR, '2021-12-27', '2021-12-28');
SELECT TIMESTAMPDIFF(MINUTE, '2021-12-27', '2021-12-28') / (60 * 24);

/*
  타입 확인 : CAST() 
*/
SELECT 10 = 20;  /* 1:TRUE , 0:FALSE */
SELECT '10' = '20';
SELECT '10' = '10';
SELECT 'a' = 'A';					/* 결과 : TRUE */
SELECT BINARY 'a' = 'A';	/* 결과 : FALSE */
SELECT 2 = '2';
SELECT 2 = CAST('2' AS SIGNED);
SELECT 5 / '2';
SELECT 5 / CAST('2' AS SIGNED);
SELECT 5 / CAST('2' AS UNSIGNED);
SELECT CAST('12345' AS int(5));		/* 현재 버전에서는 사용 오류 */
SELECT CAST('12345' AS UNSIGNED);
SELECT CAST(12345 AS CHAR(5));
select cast(timestampdiff(hour, '2021-12-27 12:00:00', now()) as signed integer);
select cast(timestampdiff(minute, '2021-12-27 12:00:00', now())/60 as signed integer);
select idx,nickName,wDate,cast(timestampdiff(minute, wDate, now())/60 as signed integer) from board order by idx desc;
select *,cast(timestampdiff(minute, wDate, now())/60 as signed integer) as diffTime from board order by idx desc;

/*--------------------댓글처리---------------------------*/
create table boardReply (
  idx				int not null auto_increment primary key,	/* 댓글의 고유번호 */
  boardIdx	int not null,							/* 원본글의 고유번호(외래키지정) */
  mid				varchar(20) not null,			/* 올린이의 아이디 */
  nickName	varchar(20) not null,			/* 올린이의 닉네임 */
  wDate			datetime		default now(),/* 댓글 기록 날짜 */
  hostIp		varchar(50) not null,			/* 댓글쓴 PC의 IP */
  content		text				not null,			/* 댓글 내용 */
  level			int  not null default 0,	/* 댓글레벨 - 부모댓글의 레벨은 '0' */
  levelOrder int not null default 0,	/* 댓글의 순서 - 부모댓글의 levelOrder은 '0' */
  foreign key(boardIdx) references board(idx) 
  on update cascade 
  on delete restrict
);
drop table boardReply;
desc boardReply;

select * from boardReply order by idx desc;

select count(*) from boardReply where boardIdx = 33;