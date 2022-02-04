/* notify.sql */
create table notify (
  idx     int not null auto_increment primary key,	/* 공지사항 고유번호 */
  name    varchar(20)  not null,										/* 공지사항 올린이 */
  title   varchar(100) not null,										/* 공지사항 제목 */
  content text not null,														/* 공지사항 상세내역 */
  startDate datetime default now(),									/* 공지사항 알림 시작일 */
  endDate   datetime default now(),									/* 공지사항 알림 종료일 */
  popupSw   char(1)  default 'N'										/* 공지사항 초기화면에 개시여부 */
);

desc notify;

insert into notify (name,title,content) values ('관리자','구정맞이 특가 이벤트 행사','2022년 구정을 맞이하여 특별가격 행사를 진행합니다.');
insert into notify values (default,'관리자','구정맞이 특가 이벤트 행사','2022년 구정을 맞이하여 특별가격 행사를 진행합니다.',default,default,default);

select * from notify order by idx desc;
