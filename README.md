
<div align="center">
  <a href="https://www.numble.it/2dc7d971-6610-4b0a-8382-3cc46d264b98"><img src="https://user-images.githubusercontent.com/53372971/175763126-fcee4020-7392-4db4-8fe6-2cdb68ef877a.png" height="128px" alt="badge42 logo" ></a>
  <h1>Numble-Karrot</h1>
  <p>🏆[17일 챌린지] Spring 초기 당근마켓 서비스 구축 챌린지 TOP10%🏆</p>
  <a href="https://velog.io/@seungju0000/Spring%EB%B0%B1%EC%97%94%EB%93%9C-%EB%84%98%EB%B8%94-%EC%B1%8C%EB%A6%B0%EC%A7%80">
    <img alt="Velog" src ="https://img.shields.io/badge/Velog-20C997.svg?&style=for-the-badge&logo=Velog&logoColor=white"/>
  </a>
</div>

# 주요 기능

- 회원가입, 로그인, 회원수정
- 상품 등록, 상품 수정, 상품 열람
- 댓글, 관심 상품 등록
- 거래 완료, 거래중, 거래 가능 기능

## 로그인, 회원수정

<div align="center">
  <img width="33%" src="https://user-images.githubusercontent.com/53372971/153955817-2a17751a-11ef-4ed6-820b-9c1de06d6986.png">
  <img width="30%" src="https://user-images.githubusercontent.com/53372971/175763521-14da7bfe-bbcb-4bfa-8c5c-ba489bccabcb.png">
  <img width="33%" src="https://user-images.githubusercontent.com/53372971/175763496-f4a9015b-294b-4a73-9934-f7767719bde0.png">
</div>
<br>

- 홈 화면에서 로그인이나 회원가입 버튼을 눌렀을 경우, 해당 버튼에 맞는 페이지로 이동
- 빈칸이나 이메일 형식이 아닌경우 또는 아이디와 비밀번호가 맞지 않는 경우 다시 해당 폼으로 돌려보냄

<br>
<div align="center">
  <img width="30%" src="https://user-images.githubusercontent.com/53372971/175763582-841e015e-f135-4b09-94a1-97ee4eb9ffc4.png">
  <img width="30%" src="https://user-images.githubusercontent.com/53372971/175763616-c0a8bccc-5252-4b63-b4f4-317ba2e15c66.png">
  <img width="30%" alt="스크린샷 2022-06-25 오후 5 00 54" src="https://user-images.githubusercontent.com/53372971/175764380-ff24da2a-b706-4c3d-8622-9c2d7d4515b0.png">

</div>
<br>

- 로그인 성공 후에는 다른 사람들이 올린 상품들 확인 가능
  - 해당 상품의 이름, 장소, 가격
  - 거래 여부(거래 가능, 거래 중, 거래 완료)
  - 댓글, 관심 수
- 밑의 그림 토글을 이용하여 자신의 정보를 조회
  -  회원의 관심 상품
  -  회원의 판매 내역
  -  회원 정보 수정
  -  로그아웃
- 회원 수정
  - 닉네임과 회원 사진 변경가능 
<br>

## 상품 등록, 수정, 열람

<br>

<div align="center">
  <img width="30%" src="https://user-images.githubusercontent.com/53372971/175764138-fc6635b0-6794-47b2-a983-02ced7fd3874.png">
  <img width="30%" alt="스크린샷 2022-06-25 오후 4 52 04" src="https://user-images.githubusercontent.com/53372971/175764164-7e470af6-1724-4dbc-a272-64aba7cffbca.png">
  <img width="311" alt="스크린샷 2022-06-25 오후 4 52 33" src="https://user-images.githubusercontent.com/53372971/175764179-c38119f2-9c14-4ec9-97a7-53f5e3b8d8eb.png">
</div>

<br>

- 상품 등록
  - 제목, 본문, 사진, 카테고리, 가격 등 입력 후 완료
  - 사진을 제외하고 빈칸을 허용하지 않음
- 상품 수정
  - 사진에 나와있지는 않지만, 자신의 상품일 경우, 상품 상단의 홈 버튼 오른쪽에 수정버튼이 생김
  - 수정 버튼을 누를 경우, 이전 내용이 불러와지고 수정가능
- 상품 열람
  - 제목, 본문, 사진, 카테고리, 가격
  - 해당 상품 주인의 다른 판매 상품
  - 댓글 보기, 관심 등록 토글
<br>

## 댓글, 관심 상품

<br>

<div align="center">
  <img width="40%" alt="스크린샷 2022-06-25 오후 4 56 22" src="https://user-images.githubusercontent.com/53372971/175764273-1791b379-f136-4916-9bc9-ed1e2fb1cee8.png">
  <img width="40%" alt="스크린샷 2022-06-25 오후 4 57 16" src="https://user-images.githubusercontent.com/53372971/175764285-b5aae266-a9b9-44ab-a6f8-161e4d7c0a6f.png">

</div>

<br>

- 댓글
  - 댓글을 통해 거래 가능
  - 닉네임, 내용, 생성 날짜 등이 보여짐
- 관심 상품
  - 상품 하단에 하트 버튼을 통해 관심상품으로 등록 가능
  - 해당 상품은 [홈] -> [관심목록] 에서 리스트 확인 가능

<br>

## 거래 완료, 거래 중, 거래 가능

<br>
<div align="center">
<img width="40%" alt="스크린샷 2022-06-25 오후 5 02 07" src="https://user-images.githubusercontent.com/53372971/175764400-78320e84-7b44-47c2-9b1d-fe401aaba363.png">
</div>

- 거래 기능
  - 자신의 상품을 댓글을 통해 거래를 완료했다면, 거래 완료 버튼으로 상태 전환가능
  - 거래 완료 상품의 경우 거래 완료 상품에서 확인 가능
  - 그 외에 거래 중 버튼 활성 가능

<br>
