 EdgentDevelopment源码

# providers

[^providers]: 结构目录，有三个子模块

![provier的结构目录](C:\Users\hulu\AppData\Roaming\Typora\typora-user-images\image-20201030153333251.png)

## development 子模块

package信息：在开发环境中执行topology流提供支持

**class：**有一个类

[^类名：DevelopmentProvider]: 继承 DirectProvdier.class

简介：provider用于开发，并且这个provider执行的topologies是DirectProvider中的topologies，并且继承于DirectPorvider。

[^properties]: 只有一个属性

```java
JMX_DOMAIN
```

类型：static，final

默认值：org.apache.edgent.providers.development

解释：用于注册Mbeans中的JMX域

[^function]: 有两个方法

1. ```java
   DevelopmentProvider()
   ```

   类型：构造函数

2. ```java
   submit(Topology, JsonObect)
   ```

   返回类型：Future<Job>

   参数：topology：Topology,  config：JsonObject

   解释：提交topology任务以执行

## direct子模块

![direct结构目录](C:\Users\hulu\AppData\Roaming\Typora\typora-user-images\image-20201030153612693.png)

package信息：直接执行topology流

**class：**有三个类

###### class1：DirectProvider

[^类名：DirectProvider]: 继承了`AbstractTopologyProvider`

简介：DirectProvider是一个TopologyProvider，它在当前虚拟机的线程中以 Job的形式运行提交的org.apache.edgent.org.apache.edgent.topology。

[^properties]: 有一个属性

```java
services
```

类型：private final ServiceContainer

默认值：无

解释：用于DirectProvider，getServices和DirectTopology方法中

[^function]: 有5个方法

1. ```java
   DirectProvider()
   ```

   类型：构造函数

2. ```java
   getServices()
   ```

   返回类型：ServiceContainer

   参数：无

   解释：返回service实例，在提交给此提供程序的所有作业之间共享。

3. ```java
   newTopology(String name)
   ```

   返回类型：DirectTopology

   参数：name 是topology的命名

   解释：根据topolog的名字new一个DirectTopology对象

4. ```java
   submit(Topology topology)
   ```

   返回类型：Future<Job>

   参数：topology 

   解释：提交该topology对象以执行

5. ```java
   submit(Topology topology, JsonObject config)
   ```

   返回类型：Future<Job>

   参数：topology topology对象

   ​			config 传入的topology对象的配置信息

   解释：topology根据config执行调用，在submit(Topology topology)中被调用

###### class2：DirectTester

[^DirectTester]: 继承`AbstractTester`

简介：用来测试

[^properties]: 有一个属性

```java
topology
```

类型：private final DirectTopology

默认值：无

解释：用于DirectTester，DirectTopology方法中

[^function]: 有6个方法

1. ```java
   DirectTester(DirectTopology topology)
   ```

   类型：构造函数

   参数：topology

2. ```java
   topology()
   ```

   返回类型：DirectTopology

   参数：无

   解释：获取topology

3. ```java
   tupleCount(TStream<?> stream, final long expectedCount)
   ```

   返回类型:Condition<Long>

   参数: stream topology流

   ​		  expectedCount 期望获得的数量

   解释:用来返回元组的期望的数量

4. ```java
   streamContents(TStream<T> stream, @SuppressWarnings("unchecked") T... values)
   ```

   返回类型:<T> Condition<List<T>>

   参数: stream  topology流

   ​		  values 用来与原来的contents比较

   解释:会返回一个Condition<List<T>>()对象,这个对象会根据values值和contents匹配返回, 或者直接返回contents

5. ```java
   atLeastTupleCount(TStream<?> stream, long expectedCount)
   ```

   返回类型:Condition<Long>

   参数: stream topology流

   ​		  expectedCount 期望获得的数量

   解释:返回>=expectedCount 的数量, 或者直接返回计数

6. ```java
   contentsUnordered(TStream<T> stream, @SuppressWarnings("unchecked") T... values)
   ```

   返回类型:<T> Condition<List<T>>

   参数: stream topology流

   ​		  values 用来与contents比较

   解释:valid()方法:values与contents的长度不等,或者contents中没有values 返回false,否则返回contents是否为空

   ​		getResult()方法的直接返回contents

###### class3：DirectTopology

[^类名：DirectTopology]: 继承`GraphTopology<DirectTester>`

简介：DirectTopology是一个图拓扑，它在当前虚拟机的线程中执行。

[^properties]: 有三个属性

```java
eg
```

类型：private final DirectGraph

默认值：无

解释：用于DirectTopology，graph方法中

```java
executable
```

类型：private final Executable

默认值：无

解释：用于getExecutable方法中

```java
job
```

类型：private Job

默认值：无

解释：这个任务在submit的时候创建，用于getCallable，executeCallable，execute方法中

[^function]: 有8个方法

1. ```java
   DirectTopology(String name, ServiceContainer container)
   ```

   类型：构造函数

   参数：name 是topology 的名称

   ​			container 提供运行时服务的容器

   解释：创建一个DirectTopology实例

   2. ```java
      graph()
      ```

      返回类型：Graph

      参数：无

      解释：返回eg属性

   3. ```java
      getExecutable()
      ```

      返回类型：Executable

      参数：无

      解释：返回executable属性

   4. ```java
      getRuntimeServiceSupplier()
      ```

      返回类型：Supplier<RuntimeServices>

      参数；无

      解释：调用返回getExecutable()方法

   5. ```java
      newTester()
      ```

      返回类型：DirectTester、

      参数：无

      解释：返回一个实例化的DirectTester对象

   6. ```java
      getCallable()
      ```

      返回类型：Callable<Job>

      参数：无

      解释：调用任务执行并返回该任务

   7. ```java
      executeCallable(JsonObject config)
      ```

      返回类型：Future<Job>

      参数：config 执行调用的配置信息

      解释：任务在这里创建并提交

   8. ```java
      execute()
      ```

      返回类型：void

      参数：无

      解释：任务的初始化和执行

## iot 子模块

![iot目录结构](C:\Users\hulu\AppData\Roaming\Typora\typora-user-images\image-20201102103700968.png)

package信息：允许多个应用程序共享一个Iot设备
**class：**有1个类

###### class1：IotProvider

[^类名：IotProvider]: 实现`TopologyProvider`和`DirectSubmitter`

简介：支持共享到物联网消息中心的单个连接的提供者

通过物联网设备命令访问控制mbean。

[^properties]: 有8个属性

```java
CONTROL_APP_NAME
```

参数类型：String

默认值：SYSTEM_APP_PREFIX + "IotCommandsToControl"

解释：物联网控制使用设备命令应用程序名称。

```java
name
```

参数类型：String

默认值：

解释：provider的名字，可以为null

```java
provider
```

参数类型：TopologyProvider

默认值：

解释：provider对象

```java
iotDeviceCreator
```

参数类型：Function<Topology, IotDevice>

默认值：

解释：用于创建IotDevice

```java
submitter
```

参数类型：DirectSubmitter<Topology, Job>

默认值：

解释：DirectSubmitter对象，用于提交任务

```java
systemApps
```

参数类型：List<String>

默认值：new ArrayList<>()

解释：用于暂存系统应用程序名称的List

```java
autoSubmitApps
```

参数类型：Map<String,JsonObject>

默认值：new HashMap<>()

解释：用于暂存自动提交应用程序名称的Map

```java
controlService
```

参数类型：JsonControlService

默认值：new JsonControlService<>()

解释：实例化一个控制服务对象



[^function]: 有24个方法

1. ```java
   IotProvider(Function<Topology, IotDevice> iotDeviceCreator)
   ```

   类型：构造函数

   参数：iotDeviceCreator iot设备创造器

   解释：创建一个使用自己的DirectProvider的Iotprovider。

2. ```java
   IotProvider(String name, Function<Topology, IotDevice> iotDeviceCreator)
   ```

   类型：构造函数

   参数：name provider的名称

   ​			iotDeviceCreator iot设备创造器

   解释：根据传入的name来创建一个使用自己的DirectProvider的Iotprovider。

3. ```java
   IotProvider(String name, DirectProvider provider, Function<Topology, IotDevice> iotDeviceCreator)
   ```

   类型：构造函数

   参数：name provider的名称

   ​			provider 是DirectProvider用于创建和提交topology

   ​			iotDeviceCreator iot设备创造器

   解释：创建一个使用自己的DirectProvider的Iotprovider。

4. ```java
   IotProvider(String name, TopologyProvider provider, DirectSubmitter<Topology, Job> submitter, Function<Topology, IotDevice> iotDeviceCreator)
   ```

   类型：构造函数

   参数：name provider的名称

   ​			provider 是DirectProvider用于创建topology

   ​			submitter 用于提交topologies

   ​			iotDeviceCreator iot设备创造器

   解释：创建一个使用自己的DirectProvider的Iotprovider。

5. ```java
   getName()
   ```

   返回类型：String

   参数：

   解释：返回name属性

6. ```java
   getApplicationService()
   ```

   返回类型；ApplicationServcie

   参数：

   解释：获取application服务，调用者可以使用它去注册由此提供程序执行的应用程序。

7. ```java
   getServices()
   ```

   返回类型：ServiceContainer

   参数：

   解释：服务的访问

8. ```java
   newTopology()
   ```

   返回类型：Topology

   参数：

   解释：使用自动生成的名称创建一个新的拓扑

9. ```java
   newTopology(String name)
   ```

   返回类型：Topology

   参数：name 拓扑的命名

   解释：使用传入的名称创建一个新的拓扑

10. ```java
    submit(Topology topology)
    ```

    返回类型：Future<Job>

    参数：topology 拓扑对象

    解释：提交一个可执行的拓扑

11. ```java
    submit(Topology topology, JsonObject config)
    ```

    返回类型：Future<Job>

    参数：topology 拓扑对象

    ​			config 提交的上下文信息

    解释：提交一个可执行的拓扑

12. ```java
    registerControlService()
    ```

    返回类型：void

    参数：

    解释：注册控制服务

13. ```java
    registerApplicationService()
    ```

    返回类型：void

    参数：

    解释：注册应用程序服务

14. ```java
    registerPublishSubscribeService()
    ```

    返回类型：void

    参数：

    解释：注册发布者和订阅者的服务

15. ```java
    registerPreferencesService()
    ```

    返回类型：void

    参数：

    解释：注册优先服务

16. ```java
    getPreferences(String providerName)
    ```

    返回类型：Preferences

    参数：providerName 将被传递到IotProvider构造函数中name参数的值

    解释：获取将使用指定名称用于IotProvider的首选项节点。

17. ```java
    createIotDeviceApp()
    ```

    返回类型：void

    参数：

    解释：创建连接到消息中心的应用程序。

18. ```java
    createJobMonitorApp()
    ```

    返回类型：void

    参数：

    解释：创建一个作业监控程序

19. ```java
    createIotCommandToControlApp()
    ```

    返回类型：void

    参数：

    解释：创建应用程序连接edgentControl设备命令到控制服务。

20. ```java
    start()
    ```

    返回类型：void

    参数：

    解释：通过启动该提供者的系统应用程序和任何支持自动提交的注册应用程序来启动该提供者。

21. ```java
    createMessageHubDevice(Topology topology)
    ```

    返回类型：IotDevice

    参数：topology 包含IotDevice的拓扑

    解释：创建到消息中心的连接

22. ```java
    registerTopology(String applicationName, BiConsumer<IotDevice, JsonObject> builder)
    ```

    返回类型：void

    参数：applicationName 应用程序的名称

    解释：注册一个使用IotDevice的应用程序

23. ```java
    registerTopology(String applicationName, BiConsumer<IotDevice, JsonObject> builder, boolean autoSubmit, JsonObject config)
    ```

    返回类型：void

    参数：applicationName 应用程序的名称

    ​			builder 用于构建topology的函数

    ​			autoSubmit 当start()方法被调用的时候自动提交该应用程序

    ​			config 自动提交应用程序的配置，可以为null

    解释：注册一个使用IotDevice的应用程序

24. ```java
    submitApplication(String appName, JsonObject config)
    ```

    返回类型：void

    参数：appName 该注册的应用程序的名称

    ​			config 提交的应用程序的配置

    解释：提交已注册的应用程序



# runtime

[^runtime]: 结构目录，里面有5个子模块

![runtime结构目录](C:\Users\hulu\AppData\Roaming\Typora\typora-user-images\image-20201030102828631.png)

### 1. appservice子模块

[^appservice]: 子模块结构目录

![appservice结构目录](C:\Users\hulu\AppData\Roaming\Typora\typora-user-images\image-20201030103015530.png)

**class：**有两个类

###### class1：AppService

[^类名：AppService]: 实现 ApplicationService

简介：应用程序服务于一个TopologyProvider，应用程序的注册以及通过被控制器提交以执行

[^properties]: 有四个属性

```Java
logger
```

类型：static， final

默认值：LoggerFactory.getLogger(ApplicationService.class);

解释：用于该类中诸多方法的日志记录

```java
applications
```

类型： final

默认值：Collections.synchronizedMap(new HashMap<>())

解释：在registerTopology，getApplicationNames和getBuilder方法中被调用

```java
provider
```

类型： final

默认值：无

解释：变量声明，在 AppService 和 getProvider 方法中有调用

```java
submitter
```

类型： final

默认值：无

解释：变量声明，在 AppService 和 getSubmitter方法中有调用

[^function]: 有9个方法

1. ```java
   createAndRegister(TopologyProvider provider, DirectSubmitter<Topology, Job> submitter)
   ```

   返回类型：ApplicationService

   参数：provider 提供程序来创建实例

   ​			submitter 为应用程序提交注册申请

   解释：创建一个注册应用程序服务，返回一个应用服务实例

2. ```java
   AppService(TopologyProvider provider, DirectSubmitter<Topology, Job> submitter, String alias)
   ```

   类型：构造函数

   参数：provider 提供程序来创建实例

   ​			submitter 为应用程序提交注册申请

   ​			alias 用于注册控制MBean

3. ```java
   registerTopology(String applicationName, BiConsumer<Topology, JsonObject> builder)
   ```

   返回类型：void

   参数：applicationName 注册的应用程序名

   ​			builder 建立者配置信息

   解释：注册topology到applications中】

4. ```java
   registerJar(String jarURL, String jsonConfig)
   ```

   返回类型：void

   参数：jarURL 一个url，要用来获取url的协议以及jar的下载url

   ​			jsonConfig 序列化为JSON字符串的JsonObject配置。Null或空字符串等价于空JSON对象

   解释：为jar创建一个新的类加载器，并注册所有已经注册为服务提供者的topology应用程序

5. ```java
   downloadJar(URL url)
   ```

   返回类型：URL，本地文件的url

   参数：url 你想从该url下载东西

   解释：在registerJar中被调用，作用是下载一个HTTP URL到本地文件

6. ```java
   getApplicationNames()
   ```

   返回类型：Set<String>

   参数：无

   解释：获取应用程序名称

7. ```java
   getBuilder(String applicationName)
   ```

   返回类型：BiConsumer<Topology, JsonObject>

   参数：applicationName 应用程序名称

   解释：根据应用程序名称，返回该应用程序的Builer信息

8. ```java
   getProvider()
   ```

   返回类型：TopologyProvider

   参数：无

   解释：获取provider

9. ```java
   getSubmitter()
   ```

   返回类型：DirectSubmitter<Topology, Job>

   参数：无

   解释：获取submitter



###### class2：AppServiceControl

[^类名：AppServiceControl]: 实现Application ServiceMXBean
[^properties]: 有2个属性

```java
service
```

类型：AppService

默认值：无

解释：声明appService

```java
logger
```

类型：Logger

默认值：LoggerFactory.getLogger(ApplicationService.class);

解释：用于该类中诸多方法的日志记录

[^function]: 有3个方法

1. ```java
   AppServiceControl(AppService service)
   ```

   类型：构造方法

   参数：service AppService对象

2. ```java
   submit(String applicationName, String jsonConfig)
   ```

   返回类型：void

   参数：applicationName 应用程序的名称

   ​			jsonConfig 提交的配置信息

   解释：提交在应用程序服务中注册的应用程序。

3. ```java
   registerJar(String jarURL, String jsonConfig)
   ```

   返回类型：void

   参数：jarURL 一个url，要用来获取url的协议以及jar的下载url

   ​			jsonConfig 序列化为JSON字符串的JsonObject配置。Null或空字符串等价于空JSON对象

   解释：向应用程序服务注册一个包含应用程序的jar文件。

### 2. etiao子模块

![etiao结构目录](C:\Users\hulu\AppData\Roaming\Typora\typora-user-images\image-20201103092913440.png)

模块信息：一个执行Edgent流拓扑的运行时，被设计为一个可嵌入的库，这样它就可以在一个简单的Java应用程序中执行。

###### class1：AbstractContext

[^类名：AbstractContext]: 实现OpletContext

简介：提供OpletContex接口t的框架实现

[^properties]: 有3个属性

```java
job
```

类型：JobContext

默认值：

```java
services
```

类型：RuntimeServices

默认值：

```java
stateMap
```

类型：HashMap<State, EnumSet<State>>

默认值：

解释：状态转换图。每个条目都将一个状态与一组系统可以转换到的可到达状态关联起来

[^function]: 有4个方法

1. ```java
   AbstractContext(JobContext job, RuntimeServices services)
   ```

   类型：构造方法

   参数：job 作业对象

   ​			services 运行的服务

2. ```java
   getService(Class<T> serviceClass)
   ```

   返回类型：<T> T

   参数：serviceClass 所需服务的类型

   返回值：如果运行此调用的容器支持该服务，则实现serviceClass类型的服务，否则为空。

   解释：获取此调用的服务。

3. ```java
   getJobContext()
   ```

   返回类型：JobContext

   参数：

   解释：获取一个作业来主控这个oplet

4. ```java
   uniquify(String name)
   ```

   返回类型：String

   参数：name 

   返回值：当前运行时上下文中的唯一名称。

   解释：在当前运行时的上下文中创建唯一名称。

###### class2：EtiaoJob

[^类名：EtiaoJob]: 继承AbstractGraphJob和实现JobContext

简介：Etiao运行时实现Job接口。

[^properties]: 有10个属性

```java
graph
```

类型：DirectGraph

```java
id
```

类型：String

```java
ID_PREFIX
```

类型：String

默认值：JOB_

解释：作业唯一标识符使用的前缀

```java
topologyName
```

类型：String

```java
name
```

类型：String

```java
containerServices
```

类型：ServiceContainer

```java
jobs
```

类型：JobRegistryService

```java
jobID
```

类型：AtomicInteger

默认值：new AtomicInteger(0);

```java
completer
```

类型：Thread

```java
completerNotify
```

类型：boolean

[^function]: 有17个方法

1. ```java
   EtiaoJob(DirectGraph graph, String topologyName, String jobName, ServiceContainer container)
   ```

   类型：构造函数

   参数：graph

   ​			topologyName

   ​			jobName

   ​			container

2. ```java
   getName()
   ```

   返回类型：String

   参数：

   返回值：EtiaoJob.name，正在执行的应用程序的作业名称。

3. ```java
   getId()
   ```

   返回类型：String

   参数：

   返回值：EtiaoJob.id，正在执行的应用程序的作业id。

4. ```java
   getContainerServices()
   ```

   返回类型：ServiceContainer

   参数：

   返回值：EtiaoJob.containerServices

5. ```java
   stateChange(Action action)
   ```

   返回类型：void

   参数：action 触发状态改变的行为

6. ```java
   executable()
   ```

   返回类型：Executable

   参数：无

   返回值：graph.exectable

7. ```java
   setNext(State desiredState, Action cause)
   ```

   返回类型：State

   参数：desiredState 期望的状态

   ​			cause 发生改变的行为原因

   返回值：当前的状态

8. ```java
   isReachable(State desiredState)
   ```

   返回类型：boolean

   参数：desiredState 期望的状态

9. ```java
   completeTransition()
   ```

   返回类型：void

   参数：无

   解释：调用上层的方法，完成状态转换，并更新注册信息

10. ```java
    onActionComplete()
    ```

    返回类型：void

    参数：无

    解释：调用compleTransition方法

11. ```java
    complete()
    ```

    返回类型：void

    参数：无

    解释：如果当前状态或下一状态不是关闭，那么就调用awaitComplete(Long.MAX_VALUE); 等待

12. ```java
    complete(long timeout, TimeUnit unit)
    ```

    返回类型；void

    参数：timeout 等待超时的时间

    ​			unit 超时参数的时间单位

13. ```java
    completeClosing(long timeout, TimeUnit unit)
    ```

    返回类型：void

    参数：timeout 等待超时的时间

    ​			unit 超时参数的时间单位

    解释：完整的工作结束。此方法可以在作业关闭后调用

14. ```java
    awaitComplete(long millis)
    ```

    返回类型：Boolean

    参数：millis 等待的时间设置单位是毫秒

15. ```java
    graph()
    ```

    返回类型：DirectGraph

    参数：无

    返回值：EtiaoJob.graph

16. ```java
    updateRegistry()
    ```

    返回类型：void

    参数：无

    解释：作业状态转换之后都要调用一次，保证作业的状态的更新

17. ```java
    updateHealth(Throwable t)
    ```

    返回类型：void

    参数：t 异常

    解释：如果t！=null 设置为不健康状况，并且记录错误，同时也要调用updateRegistry()更新Job信息

###### class3：Executable

[^类名：Executable]: 实现了RuntimeServices

简介：执行并向可执行图形元素（oplets和funcions）提供运行时服务

[^properties]: 有11个属性

```java
job
```

类型：EtiaoJob

解释：在拓扑提交时实例化

```java
containerServices
```

类型：ServiceContainer

```java
controlThreads
```

类型：ThreadFactory

```java
completionHandler
```

类型：BiConsumer<Object, Throwable>

```java
userThreads
```

类型：ThreadFactoryTracker

```java
controlScheduler
```

类型：TrackingScheduledExecutor

```java
userScheduler
```

类型：TrackingScheduledExecutor

```java
lastError
```

类型：Throwable

```java
logger
```

类型：Logger

默认值：LoggerFactory.getLogger(Executable.class)

```java
jobServices
```

类型：ServiceContainer

默认值：new ServiceContainer()

解释：特定于该作业的服务

```java
invocations
```

类型：List<Invocation<? extends Oplet<?, ?>, ?, ?>>

默认值：new ArrayList<>()

[^function]: 有16个方法

1. ```java
   Executable(String name, ServiceContainer containerServices)
   ```

   类型：构造函数

   参数：name 可执行文件的名字

   ​			containerServices 由容器提供的运行时服务

   解释：为指定的拓扑名称创建新的可执行文件，它使用给定的线程工厂创建用于执行oplet的新线程。

2. ```java
   getThreads()
   ```

   返回类型：ThreadFactory

   参数：无

   返回值：Executable.userThreads

3. ```java
   getScheduler()
   ```

   返回类型：ScheduledExecutorService

   参数：无

   返回值：Executable.userScheduler

   解释：返回用于运行可执行图形元素的ScheduledExecutorService。

4. ```java
   getService(Class<T> serviceClass)
   ```

   返回类型：<T> T

   参数：serviceClass 服务类

   解释：充当图中可执行元素的服务提供者，首先查找特定于此作业的服务，然后查找来自容器的服务。

5. ```java
   addOpletInvocation(T oplet, int inputs, int outputs)
   ```

   返回类型：<T extends Oplet<I, O>, I, O> Invocation<T, I, O>

   参数：oplet oplet对象

   ​			inputs 调用的输入

   ​			outputs 调用的输出

   返回值：给定oplet的新调用

   解释：创建一个与指定的oplet关联的新调用

6. ```java
   initialize()
   ```

   返回类型：void

   参数：无

   解释：初始化这个调用

7. ```java
   start()
   ```

   返回类型：void

   参数：无

   解释：启动所有的调用

8. ```java
   close()
   ```

   返回类型：void

   参数：无

   解释：关闭用户调度器和线程工厂，关闭所有调用，然后关闭控制调度器。

9. ```java
   getTimeoutValue(long timeout, TimeUnit units)
   ```

   返回类型：long

   参数：timeout 超时设置

   ​			units 时间单位

   返回值：超时时间

10. ```java
    invokeAction(Consumer<Invocation<?, ?, ?>> action)
    ```

    返回类型：void

    参数是：action 你想要调用的操作

11. ```java
    cleanup()
    ```

    返回类型：void

    参数：无

    解释：在发生错误的时候调用，关闭程序和线程

12. ```java
    hasActiveTasks()
    ```

    返回类型：boolean

    参数：无

    解释：检查是否还有任务活跃着，是返回true

13. ```java
    getLastError()
    ```

    返回类型：Throwable

    参数：无

    解释：获取最近的错误信息

14. ```java
    complete(long timeoutMillis) 
    ```

    返回类型：boolean

    参数：timeoutMillis 以毫秒为单位的超时时间

    解释：等待未完成的用户线程或任务
    
15. ```java
    notifyCompleter()
    ```

    返回类型：void

    参数：无

    解释：告知完成

16. ```java
    executionException(Throwable t)
    ```

    返回类型：ExecutionException

    参数：t 异常

###### class4：Invocation

[^类名：Invocation]: 继承Oplet 实现AutoCloseable

简介：运行时上下文中的一个Oplet调用



