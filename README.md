# project2_PDA
 동문회 등의 단체에서 회원 관리를 목적으로 사용 가능한 전자수첩 개발 프로젝트입니다.

## DB
DB는 MySQL을 사용합니다. 도커를 사용해서 DB를 구축했습니다.

**구축 방법**

    docker pull mysql:latest
  
위의 명령어를 통해 mysql 최신 이미지를 불러왔으며

    docker-compose up -d
를 통해 프로젝트의 DB 폴더에 있는 docker-compose.yml 파일의 설정대로 실행시켰습니다.

## WebView 관련
__1. WebView에 사용할 html 파일들은 웹 서버의 api 콜 추가 후 url 형태로 요청합니다.__

__2. api 콜 추가 없이 테스트 해보려면 HTML 폴더 안에 html 파일을 저장하고 github 웹 호스팅 주소를 사용할 수도 있습니다.__  

+ github 웹호스팅 주소를 이용해서 폴더 안에 있는 html 파일을 웹 브라우저에서 열 수 있습니다.
  + ex) <https://dohy12.github.io/project2_PDA/HTML/Payment.html>  

+ Url형태는 다음과 같습니다  
   + <u>"https://dohy12.github.io/project2_PDA/HTML/파일이름.html"</u>  

+ URL을 WebView 객체의 loadUrl() 메소드에 인자로 전달하면 html 파일과 연결 가능합니다.__
  + ex) payWebView.loadUrl("https://dohy12.github.io/project2_PDA/HTML/Payment.html");
