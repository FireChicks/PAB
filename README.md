# Spring Boot를 활용한 부품견적사이트 개발 프로젝트

## 목차
1.회원가입폼<br><br>
2.견적 생성 기능<br><br>
  * 견적
  * 부품
3.게시판<br><br><br>
---


##### 1.회원가입폼

![회원가입폼](https://github.com/FireChicks/PAB/assets/113798364/4e021f58-fc92-4893-9aa0-ff2db2fcbb76)
간단한 형태의 회원가입 폼입니다. 프로필 사진을 저장하기 전 바로 확인할 수 있고 닉네임과 아이디의 중복확인 기능을 컨트롤러쪽에 포함시켜놨습니다.

##### 2.견적<br>
###### 2-1.견적
로그인 후 저장해놨던 견적을 확인할 수 있습니다.
![견적리스트](https://github.com/FireChicks/PAB/assets/113798364/6f6ced75-5ea7-4f81-8bc1-7bbbb97976ac)
조회 버튼을 클릭하면 해당 견적을 확인할 수 있습니다.
![견적확인창](https://github.com/FireChicks/PAB/assets/113798364/3b19cf85-08ed-4dfa-a4e3-26960db1cf7a)
수정 버튼이나 추가 버튼을 누르면 부품 검색 페이지로 이동이 됩니다.

###### 2-2.부품
![부품검색](https://github.com/FireChicks/PAB/assets/113798364/5f1f36e0-1204-4cb4-882e-88b3970c5b8c)
라디오 버튼을 이용해서 카테고리별 검색또한 가능합니다.
![부품검색페이지_1](https://github.com/FireChicks/PAB/assets/113798364/78c06c63-1c08-4d11-815e-b9fed140d5a6)![부품검색페이지_2](https://github.com/FireChicks/PAB/assets/113798364/b91bfd3d-a83c-41a0-832c-c56aadbf9e0e)
이러한 검색에 반응하여 전체 페이지 수도 동적으로 변화합니다.


##### 3.게시판
![대화창](https://github.com/FireChicks/PAB/assets/113798364/ea20c171-9c3f-4465-8019-d75970735440)
게시판은 대화창과 비슷한 느낌으로 서로간의 정보를 공유한다는 컨셉으로 제작했습니다. 대화창에 부품 정보를 보낼수도 있고 이를 클릭해서 확인하는것또한 가능합니다.
![게시판_견적확인](https://github.com/FireChicks/PAB/assets/113798364/6d23dac5-af06-44d7-8980-571a5723336a)
앞서 저장해놨던 견적을 이용해서 글을 작성할 때 견적을 같이 올릴 수 있습니다.
![게시판_견적확인_부품확인](https://github.com/FireChicks/PAB/assets/113798364/f24e9e50-feef-4abf-b7cc-fb9967fd3e07)
물론 견적에 추가되어있는 각 부품의 정보를 확인하는것 또한 가능합니다.
