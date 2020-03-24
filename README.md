# oki general App
NFC와 FCM를 통해 매장에서 주문하는 키오스크를 App으로 만든 프로젝트 입니다.
General App, Merchant App, Web Server 총 3개의 프로젝트 중 Merchant App의 Repository입니다.


## 파일구조
```
└─orderspot_merchant
    ├─controller
    │      ChartActivity.java
    │      HomeActivity.java
    │      LoginActivity.java
    │      OrderActivity.java
    │      OrderListActivity.java
    │
    ├─domain
    │      OrderVO.java
    │      ProductVO.java
    │      SalesHistoryVO.java
    │      UserVO.java
    │
    ├─service
    │  │  ChartService.java
    │  │  FirebaseMessaginService.java
    │  │  OrderListService.java
    │  │  UserService.java
    │  │
    │  └─Adepter
    │          OrderListRecyclerViewAdepter.java
    │          OrderRecyclerViewAdepter.java
    │
    └─Util
            HttpRequest.java
            Util.java
```

## 개발환경
- Android Studio 3.5.3
	- Firebase Cloud Messaging
	- MpAndroidChart

## 프로젝트 설명
- Web Server와 Rest API 형식으로 통신합니다.
- FCM을 사용하여 Push Message를 전송합니다.
- Server에서 전송한 Push Message를 수신합니다.
