/* 대분류(categoryMain) */
create table categoryMain (
  categoryMainCode  char(1)  not null,				/* 대분류코드(A,B,C,... => 영문 대문자 1자 */
  categoryMainName  varchar(20) not null,			/* 대분류명(회사명... => 삼성/현대/LG) */
  primary key(categoryMainCode)
);

/* 중분류(categoryMiddle) */
create table categoryMiddle (
  categoryMainCode   char(1)  not null,				/* 대분류코드를 외래키로 지정 */
  categoryMiddleCode char(2)  not null,				/* 중분류코드(01,02,03,... => 숫자 2자리를 문자형태로) */
  categoryMiddleName varchar(20) not null,		/* 중분류명(제품분류 - 전자제품/의류/신발류) */
  primary key(categoryMiddleCode),
  foreign key(categoryMainCode) references categoryMain(categoryMainCode) on update cascade on delete restrict
);

/* 소분류(categorySub) */
create table categorySub (
  categoryMainCode    char(1) not null,			/* 대분류코드를 외래키로 지정 */
  categoryMiddleCode  char(2) not null,			/* 중분류코드를 외래키로 지정 */
  categorySubCode			char(3) not null,			/* 소분류코드(001,002,003,... => 숫자 3자리를 문자형으로) */
  categorySubName			varchar(20) not null,	/* 소분류명(상품구분 - 냉장고/에어컨/오디오/TV */
  primary key(categorySubCode),
  foreign key(categoryMainCode)   references categoryMain(categoryMainCode)     on update cascade on delete restrict,
  foreign key(categoryMiddleCode) references categoryMiddle(categoryMiddleCode) on update cascade on delete restrict
);

/* 상품 테이블(dbProduct) */
create table dbProduct (
  idx  int  not null auto_increment,			/* 상품 고유번호 */
  /*categoryMainCode   char(1)  not null,		 대분류코드 외래키지정 */
  /*categoryMiddleCode char(2)  not null,		 중분류코드 외래키지정 */
  /*categorySubCode    char(3)  not null,		 소분류코드 외래키지정 */
  productCode varchar(20)  not null,			/* 상품고유코드(대분류코드+중분류코드+소분류코드+고유번호) */
  productName	varchar(50)  not null,			/* 상품명(상품코드-모델명) - 세분류 */
  detail			varchar(100) not null,			/* 상품의 간단설명(초기화면 출력) */
  mainPrice		int not null,								/* 상품의 기본가격 */
  fName				varchar(50)	 not null,			/* 상품 기본사진(1장만 처리)-필수입력 */
  fSName			varchar(100) not null,			/* 서버에 저장될 상품의 고유이름 */
  content			text not null,							/* 상품의 상세설명 - ckeditor을 이용한 이미지 처리 */
  primary key(idx, productCode)
  /*
  foreign key(categoryMainCode)   references categorySub(categoryMainCode)   on update cascade on delete restrict,
  foreign key(categoryMiddleCode) references categorySub(categoryMiddleCode) on update cascade on delete restrict,
  foreign key(categorySubCode)    references categorySub(categorySubCode)    on update cascade on delete restrict
  */
);

/* 상품 옵션(dbOption) */
create table dbOption (
  idx 			  int not null auto_increment primary key,	/* 옵션 고유번호 */
  productIdx  int  not null,							/* dbProduct테이블의 고유번호 */
  optionName  varchar(50)  not null,			/* 옵션 이름 */
  optionPrice int not null default 0,			/* 옵션 가격 */
  foreign key(productIdx) references dbProduct(idx) on update cascade on delete restrict
);

drop table categoryMain;
drop table categoryMiddle;
drop table categorySub;
drop table dbProduct;
drop table dbOption;

select middle.*,main.categoryMainName as categoryMainName from categoryMiddle middle, categoryMain main where middle.categoryMainCode=main.categoryMainCode order by middle.categoryMiddleCode;

SELECT sub.*,main.categoryMainName as categoryMainName,middle.categoryMiddleName as categoryMiddleName
	  	FROM categorySub sub, categoryMiddle middle, categoryMain main WHERE sub.categoryMiddleCode=middle.categoryMiddleCode and sub.categoryMainCode=main.categoryMainCode
	  	ORDER BY sub.categoryMiddleCode;

/* select * from categorySub where categoryMainCode = #{categoryMainCode} and categoryMiddleCode = #{categoryMiddleCode}; */
select * from categorySub where categoryMainCode = 'A' and categoryMiddleCode = '01';

select idx from dbProduct order by idx desc;

SELECT product.*, main.categoryMainName, middle.categoryMiddleName, sub.categorySubName
	    FROM dbProduct product, categoryMain main, categoryMiddle middle, categorySub sub 
	    WHERE productName='비스포크 냉장고 4도어' ORDER BY idx DESC LIMIT 1;

select * from dbOption;


/* ================ 상품 주문 시작시에 사용하는 테이블들~ ==================== */

/* 장바구니 테이블 */
create table dbCartList (
  idx   int not null auto_increment,			/* 장바구니 고유번호 */
  cartDate datetime default now(),				/* 장바구니에 상품을 담은 날짜 */
  mid   varchar(20) not null,							/* 장바구니를 사용한 사용자의 아이디 - 로그인한 회원 아이디이다. */
  productIdx  int not null,								/* 장바구니에 구입한 상품의 고유번호 */
  productName varchar(50) not null,				/* 장바구니에 담은 구입한 상품명 */
  mainPrice   int not null,								/* 메인상품의 기본 가격 */
  thumbImg		varchar(100) not null,			/* 서버에 저장된 상품의 메인 이미지 */
  optionIdx	  varchar(50)	 not null,			/* 옵션의 고유번호리스트(여러개가 될수 있기에 문자열 배열로 처리한다.) */
  optionName  varchar(100) not null,			/* 옵션명 리스트(배열처리) */
  optionPrice varchar(100) not null,			/* 옵션가격 리스트(배열처리) */
  optionNum		varchar(50)  not null,			/* 옵션수량 리스트(배열처리) */
  totalPrice  int not null,								/* 구매한 모든 항목(상품과 옵션포함)에 따른 총 가격 */
  primary key(idx, mid),
  foreign key(productIdx) references dbProduct(idx) on update cascade on delete restrict,
  foreign key(mid) references member2(mid) on update cascade on delete cascade
);
drop table dbCartList;
desc dbCartList;
delete from dbCartList;
select * from dbCartList;

/* 주문 테이블 -  */
create table dbOrder (
  idx         int not null auto_increment, /* 고유번호 */
  orderIdx    varchar(15) not null,   /* 주문 고유번호(새롭게 만들어 주어야 한다.) */
  mid         varchar(20) not null,   /* 주문자 ID */
  productIdx  int not null,           /* 상품 고유번호 */
  orderDate   datetime default now(), /* 실제 주문을 한 날짜 */
  productName varchar(50) not null,   /* 상품명 */
  mainPrice   int not null,				    /* 메인 상품 가격 */
  thumbImg    varchar(60) not null,   /* 썸네일(서버에 저장된 메인상품 이미지) */
  optionName  varchar(100) not null,  /* 옵션명    리스트 -배열로 넘어온다- */
  optionPrice varchar(100) not null,  /* 옵션가격  리스트 -배열로 넘어온다- */
  optionNum   varchar(50)  not null,  /* 옵션수량  리스트 -배열로 넘어온다- */
  totalPrice  int not null,					  /* 구매한 상품 항목(상품과 옵션포함)에 따른 총 가격 */
  /* cartIdx     int not null,	*/		/* 카트(장바구니)의 고유번호 */ 
  primary key(idx, orderIdx),
  /* foreign key(mid) references member2(mid), */
  foreign key(productIdx) references dbProduct(idx)  on update cascade on delete cascade
);
drop table dbOrder;
desc dbOrder;
delete from dbOrder;
select * from dbOrder;

/* 배송테이블 */
create table dbBaesong (
  idx     int not null auto_increment,
  oIdx    int not null,								/* 주문테이블의 고유번호를 외래키로 지정함 */
  orderIdx    varchar(15) not null,   /* 주문 고유번호 */
  orderTotalPrice int     not null,   /* 주문한 모든 상품의 총 가격 */
  mid         varchar(20) not null,   /* 회원 아이디 */
  name				varchar(20) not null,   /* 배송지 받는사람 이름 */
  address     varchar(100) not null,  /* 배송지 (우편번호)주소 */
  tel					varchar(15),						/* 받는사람 전화번호 */
  message     varchar(100),						/* 배송시 요청사항 */
  payment			varchar(10)  not null,	/* 결재도구 */
  payMethod   varchar(50)  not null,  /* 결재도구에 따른 방법(카드번호) */
  orderStatus varchar(10)  not null default '결제완료', /* 주문순서(결제완료->배송중->배송완료->구매완료) */
  primary key(idx),
  foreign key(oIdx) references dbOrder(idx) on update cascade on delete cascade
);
SHOW CREATE TABLE dbOrder;
desc dbBaesong;
drop table dbBaesong;
delete from dbBaesong;
select * from dbBaesong;

select * from dbOrder;
select a.*,b.* from dbOrder a join dbBaesong b using(orderIdx) where orderIdx='202103092';
select a.*,b.* from dbOrder a join dbBaesong b using(orderIdx) where b.mid='hkd1234';
select count(*) from dbOrder a join dbBaesong b using(orderIdx) where date(orderDate) >= date('2022-01-10') and date(orderDate) <= date('2022-01-10') order by orderDate desc;
select a.*,b.* from dbOrder a join dbBaesong b using(orderIdx) where date(orderDate) >= date('2022-01-10') and date(orderDate) <= date('2022-01-10') and b.orderStatus='배송중' order by orderDate desc;

/* SUBDATE() - 날짜를 빼줄 수 있는 함수, 사용방법은 2가지 : DATE_ADD(), DATE_SUB()와 유사. */
select * from dborder where date(orderDate) <= date(subdate(now(), INTERVAL 11 DAY));
select * from dborder where date(orderDate) >= date(subdate(now(), INTERVAL 1 DAY)) and date(orderDate) <= date(now()) order by orderDate desc;

select count(*) from dbOrder a join dbBaesong b using(orderIdx);
select count(*) from dbOrder a join dbBaesong b using(orderIdx) where b.mid='hkd1234' and b.orderStatus='결제완료';
select oder.*,baesong.* from dbOrder oder join dbBaesong baesong using(orderIdx) where baesong.mid='hkd1234' order by baesong.idx desc limit 0,5;
select a.*,b.* from dbOrder a join dbBaesong b using(orderIdx) where date(orderDate) >= date('2022-01-01') and date(orderDate) <= date('2021-01-07') order by orderDate desc;
select a.*,b.* from dbOrder a join dbBaesong b using(orderIdx) where (date_format(orderDate,'%Y-%m-%d') between '2022-01-07' and '2022-01-07') and (b.orderStatus='결제완료') order by orderDate desc;

select count(*) from dbOrder a join dbBaesong b using(orderIdx) where (a.mid='kms1234') and date(orderDate) >= date(subdate(now(), INTERVAL '2022-01-07' DAY)) and date(orderDate) <= date(now()) order by orderDate desc;
select a.*,b.* from dbOrder a join dbBaesong b using(orderIdx) where (a.mid='hkd1234') and date(orderDate) >= date(subdate(now(), INTERVAL '2022-01-07' DAY)) and date(orderDate) <= date(now()) order by orderDate desc;
select count(*) from dbOrder a join dbBaesong b using(orderIdx) where a.mid='kms1234';