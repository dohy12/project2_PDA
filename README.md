# project2_PDA
나중에 수정 부탁드립니다

## DB
도커를 통해 구축을 했습니다.

    docker pull mysql:latest
  
위의 명령어를 통해 mysql 최신 이미지를 불러왔으며

    docker-compose up -d
를 통해 프로젝트의 DB 폴더에 있는 docker-compose.yml 파일의 설정대로 실행시켰습니다.

## WebView 관련
__1. WebView에 사용할 html 파일들은 HTML 폴더 안에서 관리하면 됩니다.__  

+ github 웹호스팅 주소를 이용해서 폴더 안에 있는 html 파일을 웹 브라우저에서 열 수 있습니다.
+ ex) <https://dohy12.github.io/project2_PDA/HTML/Payment.html>  

__2. Url형태는 다음과 같습니다.__  

+ <u>"https://dohy12.github.io/project2_PDA/HTML/파일이름.html"</u>  

__3. URL을 WebView 객체의 loadUrl() 메소드에 인자로 전달하면 html 파일과 연결 가능합니다.__
+ ex) payWebView.loadUrl("https://dohy12.github.io/project2_PDA/HTML/Payment.html");
