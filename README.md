# LUBID-LIVE


1. LUBID 라이브와 관련된 프로젝트입니다.



---
### 사용 기술
1. REACTIVE 몽고DB
2. WEBFLUX
3. SSE 방식의 푸시 알림과 동시에 실시간 채팅 기능 사용방법입니다.
4. WebSocket 방식은 구현중입니다.



### 사용 방법

1. Spring Security 정책은 auth 를 붙인 모든 url 들은 통과 하도록 하였습니다.
   2. /lubid-live/auth/* 모두접근이 가능하며
   3. /lubid-live/auth/sample 만들어둔 컨트롤러 엔드포인트 url

2. 샘플 에서 사용될 객체는 아래와 같습니다.
   3. SampleRoomModel : 몽고 DB의 Document, 테이블 입니다. 구조는 아래왁 같습니다. 해당 구조는 샘플이여서 언제든지 수정 및 변경될 수 있습니다.

```json
// 백엔드 서버와 전송하는 json 샘플 구조입니다.
// SampleRoomModel
{
   "id": 1,
   "roomNum": 1,
   "rommTitle": "제품 판매합니다.",
   "videoUrl": "https://www.youtube.com/watch?v=FbDUQuo4sHY",
   "thumbnail": "https://i.namu.wiki/i/Y0wCL15ZRFj40VsxvLxxi6pe177AzPwtmjZubgqTaqtcPbZs3xZuQN5YNzqczdU3zOo5t5KcaQ2_f_VFFHCvXg_lZq47KB6jaJ9-Sbd6cxgBM0FIMJ4u7etLT_-LNnXwMtlpcvOYTi3-1ymCp-rrLw.webp",
   "chiefId": 21,
   "chief": "방장",
   "createAt": NOW()
}

// SampleChatModel
{
   // (autoincrement)
   "id": 1,
   // 전송 하는 사람
   "sender": 202,
   // 받는 사람
   "receiver": 100,
   // 작은 채팅칸에서 사용될 썸네일(삭제 예정)
   "msg": "테스트 메세지 전송하겠습니다.",
   "profile": "",
   "roomNum": 1
}
```

## API DOCUMENT

### 1. 메세지 전송 API 설명
### Path : "/lubid-live/auth/sample/send-message"

description : 몽고 DB 측에 메세지를 전송해 저장하는 request url

##### Headers

| name |  type  | description | required |
| :---: |:------:| :---: | :---: |
| id | String | parameter description(optional) | Optional or Required |

```json
{
 headers: {
   'Content-Type': 'application/json',
   Connection: 'keep-alive',
 },
 withCredentials: true,
}
```
<br/>

##### Body

| name |  type  |    description     | required  |
| :---: |:------:|:------------------:|:---------:|
| id | Object |  SampleChatModel   | Required  |

```json
// SampleChatModel
{
   // (autoincrement)
   "id": 1,
   // 전송 하는 사람
   "sender": 202,
   // 받는 사람
   "receiver": 100,
   // 작은 채팅칸에서 사용될 썸네일(삭제 예정)
   "msg": "테스트 메세지 전송하겠습니다.",
   "profile": "",
   "roomNum": 1
}

```


#### Response

  <details markdown="1">
  <summary>200 OK : description</summary>

  ```
  {
    // Response
  }
  ```
  </details>

  <details markdown="1">
  <summary>201 Created: description</summary>

  ```
  {
    // Response
  }
  ```
  </details>

</details>
<br>

---

### 2. 채팅 방 만들기
### Path : "/lubid-live/auth/sample/make-room"

description : 요청을 통해 방을 만들어 줍니다.

##### Headers

| name |  type  | description | required |
| :---: |:------:| :---: | :---: |
| id | String | parameter description(optional) | Optional or Required |

```json
{
 headers: {
   'Content-Type': 'application/json',
   Connection: 'keep-alive',
 },
 withCredentials: true,
}
```
<br/>

##### Body

| name |  type  |    description     | required  |
| :---: |:------:|:------------------:|:---------:|
| id | Object |  SampleChatModel   | Required  |

```json
// SampleRoomModel

{
   id: 1,
   roomNum: 1,
   rommTitle: 'test',
   videoUrl: '',
   thumbnail: '',
   chiefId: 1,
   chief: '1',
}


```


#### Response

  <details markdown="1">
  <summary>200 OK : description</summary>

  ```
  {
    // Response
  }
  ```
  </details>

  <details markdown="1">
  <summary>201 Created: description</summary>

  ```
  {
    // Response
  }
  ```
  </details>

</details>
<br>

---

### 2. 채팅 방과 연결 하기 (SSE 통신 방식)
### Path : "/lubid-live/auth/sample/live/request-room/[id]"

description : SSE 통신으로 서버와 연결합니다. 샘플이라 별다른 인증 과정이 필요 업습니다.

##### Headers

| name |  type  | description | required |
| :---: |:------:| :---: | :---: |
| id | String | parameter description(optional) | Optional or Required |

```json
{
 headers: {
   'Content-Type': 'application/json',
   Connection: 'keep-alive',
 },
 withCredentials: true,
}
```
<br/>

##### Body

| name |  type  | description | required |
| :---: |:------:|:-----------:|:--------:|
| id | Object |    Optional     |   Optional   |



#### Response

  <details markdown="1">
  <summary>200 OK : description</summary>

  ```
  {
    // Response
  }
  ```
  </details>

  <details markdown="1">
  <summary>201 Created: description</summary>

  ```
  {
    // Response
  }
  ```
  </details>

</details>
<br>

