show tables;

create table member2 (
	idx		int not null auto_increment,	/* 회원 고유번호 */
	mid		varchar(20)   not null,				/* 회원 아이디(중복불허) */
	pwd		varchar(100)		not null,			/* 비밀번호(입력시 9자로 제하처리할것) */
	nickName  varchar(20) not null,			/* 별명(중복불허) */
	name			varchar(20) not null,			/* 성명 */
	gender    varchar(10) default '남자',/* 성별 */
	birthday	datetime		default now(),/* 생일 */
	tel				varchar(15),							/* 연락처 */
	address		varchar(50),							/* 주소 */
	email			varchar(50)	not null,			/* 이메일(아이디/비밀번호 분실시 필요) */
	homePage	varchar(50),							/* 홈페이지(블로그)주소 */
	job				varchar(20),							/* 직업 */
	hobby			varchar(60),							/* 취미 */
	photo			varchar(100) default 'noimage.jpg', 	/* 회원사진 */
	content   text,											/* 자기소개 */
	userInfor char(6) default '공개',		/* 회원 정보 공개여부(공개/비공개) */
	userDel		char(2) default 'NO',			/* 회원 탈퇴 신청 여부(OK:탈퇴신청한회원, NO:현재가입중인회원) */
	point     int default 100,					/* 포인트(최초가입회원은 100, 한번 방문시마다 10 */
	level			int default 4,						/* 1:특별회원, 2:우수회원, 3:정회원, 4:준회원, 0:관리자 */
	visitCnt	int default 0,						/* 방문횟수 */
	startDate datetime default now(),		/* 최초 가입일 */
	lastDate  datetime default now(),		/* 마지막 접속일 */
	todefault,dayCnt  int default 0,						/* 오늘 방문한 횟수 */
	primary key(idx, mid)								/* 키본키 : 고유번호, 아이디 */
);

/* drop table member2; */
desc member2;

insert into member2 values (default,'admin','1234','관리맨','관리자',default,default,'010-3423-2704','경기도 안성시','cjsk1126@naver.com','blog.daum.net/cjsk1126','프리랜서','등산/바둑',default,'관리자입니다.',default,default,default,default,default,default,default,default);

update member2 set level = 0 where mid = 'admin';
update member2 set pwd = '127420962', pwdKey=15, tel='010/3423/2704', address='경기도 안성시///' where mid = 'admin';
delete from member2 where mid='hkd1234';

select * from member;
select * from member2;
select * from member2 order by idx desc;

select count(*) from guest where name like '%홍길동%' or name like '%hkd1234%' or name like '%홍장군%';

