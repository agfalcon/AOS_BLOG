grant all privileges on  *.* to 'root'@'%' identified by 'smile1234'; // 권한 설정
delete from mysql.user where host="localhost" and user="root";
flush privileges;
select host,user,plugin,authentication_string from mysql.user;

###############################################
DROP DATABASE IF EXISTS blogdb; 

CREATE DATABASE IF NOT EXISTS blogdb
  DEFAULT CHARACTER SET utf8 
  DEFAULT COLLATE utf8_general_ci;

USE blogdb;

CREATE TABLE edit ( 
  id INT NOT NULL AUTO_INCREMENT,
  title VARCHAR(255) NOT NULL,
  date VARCHAR(32) NOT NULL,
    content VARCHAR(5000) NOT NULL,
    comment_num INT NOT NULL,
  PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET utf8 
  DEFAULT COLLATE utf8_general_ci;
  
  CREATE TABLE comment ( 
  id INT NOT NULL AUTO_INCREMENT,
    edit_id INT NOT NULL,
    name VARCHAR(100) NOT NULL,
  content VARCHAR(1000) NOT NULL,
  date VARCHAR(32) NOT NULL,
  PRIMARY KEY (id),
      FOREIGN KEY(edit_id) REFERENCES edit(id)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET utf8 
  DEFAULT COLLATE utf8_general_ci;

###############################################

INSERT INTO edit (title, date, content, comment_num) VALUES 
  ('안드로이드 개발 시작!!', '2022-12-01', '안드로이드 개발을 시작했다.', 3),
  ('시험 기간은 힘들다 ㅠㅠ', '2022-12-07', '시험 언제 끝나는지 너무 힘이 든다....', 0),
  ('블로그 예시1', '2022-12-12', '안드로이드는 구글에서 만든 스마트폰용 운영체제입니다. 
   운영체제와 미들웨어, 사용자 인터페이스, 어플리케이션, MMS 서비스 등을 하나로 묶어 서비스를 제공하며 다양한 어플리케이션을 만들어 설치하면 실행될 수 
   있도록 구성된 어플리케이션 플랫폼이라고도 볼 수 있습니다. 많은 사람들이 iOS(애플 운영체제)에 견주어 스마트폰과 태블릿으로 안드로이드 운영체제를 사용하면서, 
   안드로이드는 세계 모바일 시장에서 가장 성공한 OS라는 평가를 받고있습니다. 안드로이드는 리눅스(Linux)를 기반으로 제작되었고 언어는 자바를 사용합니다.', 0),
  ('블로그 예시2', '2022-12-17', '안드로이 대는 2005년 구글이 앤디 루빈이라는 사람이 설립한 안드로이드라는 회사를 인수하면서 시작합니다. 
   작은 소프트웨어 회사에 불과했던 안드로이드사는 구글이 인수하면서부터 운영체제 개발을 시작하면서 없어서는 안될  운영체제로 거듭나기 시작합니다. 
   2007년 11월에 안드로이드 플랫폼을 모바일 기기 운영체제로서 무료 공개한다고 발표하였습니다. 
   초창기 모바일 시장에서는 아이폰에 대한중의 반응은 뜨거웠기 때문에 안드로이드라는 OS를 보유하고 있는 구글은 큰 위협을 느꼈습니다. 
   이는 노키아나 삼성도 마찬가지였습니다. 그리하여 애플을 대항하는 반애플 집단이 형성되는데 그 집단의 이름이 OHA입니다. OHA는 리눅스 커널 2.6을 기반으로 한 
   그들의 첫 번째 모바일 기기 플랫폼 \'안드로이드\'를 발표하게 되었으며 여러가지 안드로이드의 장점과 많은 회사의 협력덕분에 현재의 독보적인 OS라는 위치까지 오르게됩니다.' , 0),
  ('블로그 예시3', '2022-12-20', '1. 자바기반 : 안드로이드를 개발할 떄 사용하는 언어는 자바입니다. 따라서 전 세계의 많은 자바 개발자들이 안드로이드 앱 개발에 뛰어들 수 있었고 안드로이드가 빠르게 성장할 수 있는 계기가 되었습니다.

2. 오픈소스 : 안드로이드는 오픈소스를 기반으로 하고 있으며 아파치 2.0라이선스를 사용합니다.

3. 완벽한 컴포넌트 : 안드로이드는 오픈소스임에도 불구하고 스마트폰을 위한 완벽한 컴포넌트들을 제공합니다. 핵심적인 커널 부분만 제공하고 다른 부분들은 개발자에게 맡기는 방식이 아니라 제공된 소스를 사용하면 스마트폰이라는 전화기가 그대로 만들어지므로 대부분의 개발자들은 그 위에 올라가는 앱만 만들면 됩니다.

4. 높은 점유율 : 위에서 언급한 3가지를 주축으로 안드로이드는 빠르게 성장하여 애플의 IOS를 제치고 80%가 넘는 점유율을 가지게 되었으며 심지어 최근에는 Windows의 점유율마저 넘어섰다는 보도가 나오고 있습니다.

5. 높은 이식성 : 안드로이드는 리눅스를 기반으로 높은 이식성을 가지고 있으며 새로운 하드웨어의 기능을 쉽게 연동할 수 있습니다. 모바일 뿐만이 아니라 태블릿, PMP 등의 각종 통신 기기에 사용될 수 있습니다. 나아가 가전 기기 심지어는 자동차까지도 적용할 수 있는 연동성을 갖추어 인베디드시스템의 새로운 패러다임을 제시하고 있습니다.

6. 쉬운연동 : 안드로이드로 만든 앱은 다른 앱에서 제공하는 기능을 쉽게 사용할 수 있도록 만들어져 있습니다. 사진을 찍기 위해 단말의 사진 앱을 연동하면 카메라 미리보기 화면을 직접 만들지 않아도 됩니다.

 ', 0);
 
 INSERT INTO comment (Edit_ID, date, name, content) VALUES 
  (1, '2022-12-03', '스마일스톤', '안드로이드 개발 화이팅!!!'),
  (1, '2022-12-10', '대구토박이', '재밌어용~!'),
  (1, '2022-12-13', '지나가는 나그네', '잘 보구 갑니다~')
  
  ;