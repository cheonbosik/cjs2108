create table area(
  address1  varchar(50) not null,		/* 도(경기도/강원도) */
  address2  varchar(20) not null,		/* 시(강릉시/동해시) */
  latitude  double not null,				/* 위도 */
  longitude double not null					/* 경도 */
);

desc area;

select * from area;

select distinct address1 from area;

select distinct address1, address2, latitude, longitude from area where address1 = '강원도' and address2 = '강릉시';

/* =================================================== */

create table address (
  address     varchar(30) not null,		/* 지점명 */
  latitude  double not null,		/* 위도 */
  longitude double not null		/* 경도 */
);
select * from address;

