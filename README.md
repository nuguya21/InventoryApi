# InventoryApi
인벤토리 관련 구문 작성에 도움을 주는 플러그인입니다.

## 사용법
- ```plugin.yml```의 ```depend``` 항목의 ```InventoryApi```를 추가하여 의존 플러그인으로써 작성합니다.
- ```InventoryApi``` 생성
  - ```InventoryApi```를 선언 후 ```.create``` 메소드를 통해 생성합니다.
  - 예시 코드:
```jave
InventoryApi api = new InventoryApi("example", null, 9);
api.create;
```
- 아이템 추가
  - ```InventoryItem``` 을 선언합니다.
  - ```InventoryItem``` 을 선언할 때 ```ItemStack```은 아이템을 설정하고 ```Consumer```은 해당 아이템을 클릭했을 때 실행되는 구문을 람다식으로 작성합니다.
  - 추가할 ```InventoryApi```의 ```.setInventoryItem``` 메소드를 통해 슬롯과 플레이어의 ```UUID```와 함께 추가합니다.
  - ```InventoryApi```는 플레이어 각자의 인벤토리가 나누어져 있기 때문에 플레이어에게 열 때 추가하는 것을 추천합니다
  - 예시 코드:
```java
InventoryItem item = new InventoryItem(itemStack, event -> {
  //클릭했을 때 실행될 구문
});
api.setInventoryItem(uuid, slot, item);
```
- 아이템을 클릭하지 않았을 때, ```InventoryDragEvent```가 발생했을 때 실행
  - 실행할 ```InventoryApi```의 ```.setClickAirEventConsumer```와 ```.setDragEventConsumer```을 통해 실행되는 구문을 람다식으로 작성합니다.
  - 예시 코드:
```java
api.setClickAirEventConsumer(event -> {
  //아이템을 클릭하지 않았을 때 실행될 구문
});
api.setDragEventConsumer(event -> {
  //InventoryDragEvent가 발생했을 때 실행될 구문
});
```
- 플레이어에게 인벤토리 열기와 닫기
  - ```InventoryManager```의 ```.open```와 ```.close``` 메소드를 통해 플레이어에게 생성된 ```InventoryApi```를 열고 닫을 수 있습니다.
  - 예시 코드:
```java
InventoryApiPlugin.getInventoryApiManager.open("example", player);
InventoryApiPlugin.getInventoryApiManager.close(player);
```
- 사용 예시(java 17)
  - 물양동이를 클릭했을 때 플레이어의 위치에 물 생성하는 인벤토리
```java
InventoryApi api = new InventoryApi("example", 9, "물 생성");
//onEnable 추천
api.create;
//플레이어에게 열 때(명령어 등)
ItemStack stack = new ItemStack(Material.WATER_BACKET);
InventoryItem item = new InventoryItem(stack, event -> {
  if (event.getWhoClicked() instanceof Player player) {
    event.setCancelled(true);
    Location location = player.getLocation;
    location.getBlock().setType(Material.WATER);
  }
});
api.setInventoryItem(player.getUniqueId(), 4, item);
api.update();
InventoryApiPlugin.getInventoryApiManager.open("example", player);
```
