# TODO 
## 프로젝트 설명
할 일 관리 어플리케이션

### 기능
#### 할 일
- 할 일 생성
- 할 일 목록 조회
  - 작성자 이름으로 조회
  - 작성일 기준으로 정렬 
- 할 일 상세 조회
  - 할 일 상태 변경  
- 할 일 수정
- 할 일 삭제
#### 댓글 
- 댓글 작성
- 댓글 수정 
- 댓글 삭제

#### 인증
- 일반 회원가입, 로그인
  - 이메일, 패스워드 이용 
- 소셜 로그인
  - 최초 로그인 시 자동 회원 가입
 
#### 인가
- jwt accessToken을 이용한 인가
- 할 일, 댓글 생성 시 로그인 한 사용자만 가능
- 할 일 수정, 삭제 시 해당 할일 작성자만 가능
- 댓글 수정, 삭제 시 해당 댓글 작성자만 가능

## UseCase Diagram
<img width="623" alt="image" src="https://github.com/devitssu/sparta-todo/assets/63135789/d081ca8c-cdb7-49d7-899e-e676d71415a4">

## ERD
<img width="937" alt="image" src="https://github.com/devitssu/sparta-todo/assets/63135789/821ecadd-6c0c-4d58-b60c-6a5a0bfbe9d0">

## API 명세
https://itssu.notion.site/To-Do-268e44f288c7407ead51ad59917b93eb
