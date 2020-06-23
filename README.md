# Order Service

### 功能

需要对外暴露的API和业务功能：

* 创建Order - createOrder
* 撤销Order - cancelOrder
* 支付Order - pay
* 签收Order - signForReceive
* 确认收货Order - confirmForReceive
* 修改Order中Product的数量 - changeProductCount
* 修改Order中的地址信息 - changeAddress

### 分层架构示意图

* api - 需要对外暴露的接口 （技术实现）
* application - 业务动作编排
* domain - 领域模型与业务逻辑
* infrastructure - 基础设施（技术实现）

![分层架构](/layer_arch_gyb.jpg)
