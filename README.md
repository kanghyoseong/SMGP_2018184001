# 뱀파이어 서바이벌
스마트폰 게임 프로그래밍   
게임공학과 2018184001 강효성

## 게임 컨셉
<img width="30%" src="https://user-images.githubusercontent.com/104414105/229294409-1e70211e-e407-4a2a-9936-2fd5c970270d.jpg"/>

* 탑 뷰 슈터 게임
* 캐릭터가 가진 아이템에 따라 자동으로 공격한다.
* 유저는 캐릭터를 움직이고 자동으로 공격한다.
* 시간이 지남에 따라 적이 많아지고 강해진다.
* 살아남은 시간을 기록으로 한다.

## 게임 실행 흐름
<img width="60%" src="https://user-images.githubusercontent.com/104414105/229295085-40ba522a-3b27-4736-9eb9-dbfba32734c3.png"/>   

1. 적 생성
2. 플레이어 이동
3. 적 처치
4. 경험치 획득
5. 레벨업
6. 아이템 업그레이드
7. 더 많은 적 처치

## 개발 범위
### UI
<img width="100%" src="https://user-images.githubusercontent.com/104414105/229295083-47867948-6524-4297-a082-48f361bf73e0.png"/>

### 화면 비율
<img width="90%" src="https://user-images.githubusercontent.com/104414105/230758991-b4972e62-dc23-4ab2-8347-069e63d93bea.png"/>

* 화면의 크기는 1 x 1이다.
* 플레이어의 크기는 0.12 x 0.12이다.
* 플레이어는 항상 화면 중앙에 있다.
* 플레이어 이동시 배경이 움직인다.
* 화면 비율이 달라져도 1x1의 화면은 무조건 보이게 한다.
* 그 밖의 화면은 게임 배경으로 채운다.

### 플레이어
<img width="20%" src="https://user-images.githubusercontent.com/104414105/229296160-cae3c5cf-ca7f-4cc7-aae6-3deb92785c0b.png"/>

|수치|기본값|레벨업 시 변화|
|:---:|:---:|:---:|
|레벨|1|+1|
|레벨업에 필요한 경험치|5|+10|
|체력|20|+2|
|이동속도|1.0|-|
|무기|채찍|-|

#### 캐릭터 구현에 필요한 것
* 스프라이트 애니메이션
* 이동 방향에 따른 스프라이트 반전 (왼쪽, 오른쪽)
* 피격 시 색깔이 빨개짐

### 아이템
<img width="80%" src="https://user-images.githubusercontent.com/104414105/229296161-3abfef0e-826b-419b-851f-7d11455186c2.png"/>

### 아이템 레벨 업 효과
플레이어 레벨업 시 아이템 1개의 레벨을 올릴 수 있다.
|레벨|수치|
|:---:|:---:|
|1|아이템 획득|
|2|투사체 수 1 증가|
|3|공격력 10 증가|
|4|투사체 수 1 증가|
|5|공격력 20 증가|
|6|투사체 속도 25% 증가|
|7|공격력 10 증가|
|8|투사체 수 1 증가|

### 경험치
<img width="30%" src="https://user-images.githubusercontent.com/104414105/229296165-e180f32a-2cac-4b20-a99a-59b19d0b8686.png"/>

* 적을 죽이면 경험치가 드랍된다.
* 경험치를 얻어 레벨업하면 아이템을 업그레이드 할 수 있다.

### 적
<img width="80%" src="https://user-images.githubusercontent.com/104414105/229296167-886088fd-4b8b-401b-b956-218a0be70430.png"/>

### 웨이브
* 시작하자마자 웨이브 1이 시작된다.
* 30초가 지나면 다음 웨이브가 시작된다.
* 플레이어 화면에서 보이지 않는 곳에서 적이 생성된다. 
* 3번째 웨이브 마다 새로운 종류의 적이 생성된다. 
* 처음 생성되는 적의 수는 종류 당 15이고 다음 웨이브에서 5씩 추가된다.

## Class
### Class Sprite

#### 변수
|  자료형   |             이름             |                                      설명
|----------|------------------------------|--------------------------------------------------------------------------------------------
|__Bitmap__|__bitmapFrame[]__             |애니메이션을 위한 비트맵들, 한 장의 애니메이션 스프라이트를 spriteCount만큼 쪼개서 배열로 저장한다.
|__int__   |__curFrame__                  |현재 프레임 숫자, 1부터 시작한다.
|__int__   |__frameCount__                |프레임 총 개수
|__float__ |__secToNextFrame__            |다음 프레임으로 넘어가는데 걸리는 시간
|__int__   |__spriteCountX, spriteCountY__|입력된 스프라이트 이미지가 나눠져 있는 개수
|__float__ |__elapsedTime__               |프레임이 넘어간 후 지난 시간. 이것이 secToNextFrame보다 크면 다음 프레임으로 넘어간다.
|__RectF__ |__dstRect__                   |그림을 그리기 위한 Rect, Player의 RectF와 같은 객체를 가리킨다.

#### 함수
|               이름                                             |                           설명                  
|----------------------------------------------------------------|----------------------------------------------------------------------
|__public void update(float eTime)__                             |elapsedTime을 증가시킨 뒤 secToNextFrame보다 크면 다음 프레임으로 넘어간다.
|__public void draw(Canvas canvas)__                             |dstRect 크기만큼 bitmapFrame에서 curFrame 그림을 꺼내어 그린다.
|__private void toNextFrame()__                                  |curFrame을 증가시키고 frameCount보다 크면 1로 초기화 시킨다.
|__public void setBitmapList(int resId, int countX, int countY)__|리소스를 countX, countY 만큼 분리시킨 뒤 bitmapFrame에 저장한다.
|__public void setDstRect(RectF rect)__                          |Sprite의 dstRect가 rect를 가리키도록 한다.

### Class Player

#### 변수
Sprite Animation
|  자료형  |       이름      |                설명                  
|----------|----------------|--------------------------------------
|__Sprite__|__sprite__      |플레이어 애니메이션 이미지
|__RectF__ |__dstRect__     |플레이어를 그리거나 충돌을 판정할때 사용
|__float__ |__posX, posY__  |플레이어의 화면상 위치
|__float__ |__sizeX, sizeY__|플레이어의 화면상 크기

Game Information
|  자료형  |           이름           |               설명
|---------|--------------------------|--------------------------------
|__int__  |__level__                 |현재 레벨
|__int__  |__expToLevelUp__          |레벨업을 하기위해 필요한 경험치 양
|__int__  |__expToLevelUp_increment__|레벨업시 expToLevelUp의 증가량
|__int__  |__curHp__                 |현재 HP
|__int__  |__maxHp__                 |최대 HP
|__int__  |__maxHp_increment__       |레벨업시 maxHp의 증가량
|__float__|__movementSpeed__         |이동속도
|__Item__ |__items[]__               |가지고 있는 아이템

#### 함수
|              이름                      |                           설명                  
|----------------------------------------|----------------------------------------------------------------------
|__public void draw(Canvas canvas)__     |sprite.draw()를 호출한다. (애니메이션 스프라이트를 그린다.)
|__public void setPos(float x, float y)__|posX, posY를 x, y로 설정하고 dstRect를 재설정한다.
|__public void move(float dx, float dy)__|dx, dy와 frameTime을 곱하여 posX, posY에 더한다. 그리고 dstRect를 재설정한다.
|__private void reconstructRect()__      |dstRect를 posX, posY위치에 sizeX, sizeY 크기가 되도록 설정한다.
|__public void update(float eTime)__     |sprite.update()를 호출한다.


## 일정
|기간|시작일|내용|
|-----|------|-----------|
|1주차|4월 4일|플레이어 구현|
|2주차|4월 11일|조이스틱 적용, 배경 제작|
|3주차|4월 18일|적 구현|
|4주차|4월 25일|아이템 구현|
|5주차|5월 2일|경험치, 레벨, 아이템 업그레이드 구현|
|6주차|5월 9일|게임 플레이 UI 구현|
|7주차|5월 16일|나머지 UI 구현|
|8주차|5월 23일|게임 테스트, 버그 수정|
|9주차|5월 30일|게임 테스트, 버그 수정|

