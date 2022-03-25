# 규칙

## Issue

- 구현할 기능 한글로 작성하고, 무슨 기능 구현할 것인지 Write에 쓴다.

![image](https://user-images.githubusercontent.com/82709044/160164752-4ad82fac-0128-4a24-9b25-3b3399df10f6.png)

우측 Assignees에 담당자를 추가한다.

기능구현이 대부분이니 enhancement 라벨을 이용하면 될듯..

![image](https://user-images.githubusercontent.com/82709044/160164816-b5f97e31-7ca9-47d3-a81b-30268aae0da7.png)

추가하고 이슈번호 확인!!! 

## Branch Naming

### feature/#[이슈번호]-[기능요약]

- 띄어쓰기마다 하이폰(-) 사용, 소문자만 사용하기

ex)

feature/#5-add-fcfs

feature/#7-add-rr

## Commit Message

### #[이슈번호] [커밋 숫자]st commit: [커밋 내용]

ex) 

#5 3rd commit: 인터페이스 기능 추가

#5 4th commit: FCFS running 기능 구현

#5 5th commit: FCFS running 기능 리팩토링

![image](https://user-images.githubusercontent.com/82709044/160164910-26bc7652-c5c3-44e4-85e7-88c6755cb8f5.png)

```dart
1st  11th  21st
2ed  12th  22nd
3rd  13th  23rd
4th  14th  24th
5th  15th  25th
6th  16th  26th
7th  17th  27th
8th  18th  28th
9th  19th  29th
10th 20th  30th
```

## Pull Request

- develop ← 자신이 만든 브랜치  : 이렇게 PR 등록해야함!!!!!
- 리뷰어 팀원 전부 넣고, 기능 내용 적은 후 PR 등록

![image](https://user-images.githubusercontent.com/82709044/160165077-e6c0e342-4f26-4cfd-ab08-f9b3686c3fde.png)

![image](https://user-images.githubusercontent.com/82709044/160165127-a9677037-ad28-4a1d-b30c-f73b3cf5e6c0.png)
- pr 등록 후 우측 하단의 Development에서 자신의 이슈를 등록한다.
    - 이래야 merge 후 이슈가 자동 삭제 됨
- 바로 머지하지 말고 PR 올린 후 팀원들과 토의 후 머지

## Naming Convention

- Class, Interface (파스칼 케이스)

```java
public class FCFS(){  }
```

- Method (동사, 카멜케이스)

```java
public int getName()
```

- Constant (대문자, 스네이크케이스)

```java
const int MAX_NUMBER = 10;
```

- Pakage (소문자만 사용)

## Design Pattern

MVC

MVP

둘 중 하나 고민 중
