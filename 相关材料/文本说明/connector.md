# connector

## command：命令/操作系统进程连接器

- CommandStream：连接器，用于从命令的或者操作系统进程的输出创建TStream,并且将TStream下沉到命令的或者操作系统的进程的输入

- public static List<String> tokenize(String cmdString)：以与执行相同的方式标记指定的cmdString运行时.exec（字符串）。此函数为创建ProcessBuilder以供其他CommandStreams方法使用提供了便利。 return the tokens

- public static Tstream<String> generate(Topology topology,ProcessBuilder cmd)：从长时间运行的命令的输出创建一个无止境的TStream<String>。

  提供的cmd用于启动命令。将为从命令输出中读取的每个UTF8行创建一个元组。如果cmd被配置为将stderr重定向到stdout，则元组包含stderr的输出。如果从命令的输出流读取返回EOF或错误，则重新启动该命令。

- public static TStream<List<String>> periodicSource(Topology topology,ProcessBuilder cmd,long period,TimeUnit units):从定期运行命令的输出创建TStream<String>。
  在命令开始时提供了cmd。命令的UTF8输出将被读取，直到EOF和一个List<String>元组被创建，其中包含收集的输出。如果stdour配置为包含stdour输出，则重定向到stdour。

- public static TSink<String> sink(TStream<String> stream,ProcessBuilder cmd):将TStream<String>放入命令的输入。
  提供的cmd用于启动命令。每个元组都被写成UTF8并刷新到命令的输入。如果写入操作遇到错误，将重新启动该命令。
  而每次写入后都会有一个flush（）函数，它只会帮助减少发现cmd失败并重新启动它所需的时间。假定“成功写入并刷新”的值不能保证cmd在重新启动时收到。

- public static Supplier<String> endlessCommandReader(ProcessBulider cmd):创建一个无止境的Supplier<String>来接收长时间运行的命令的输出。
  此方法在创建一个传感器或源连接器类时特别有用，该类隐藏了它使用命令的事实，使其能够像任何其他传感器/连接器一样使用。

- public static Supplier<List<String>> commandReadeList(ProcessBuilder cmd):创建一个Supplier<List<String>>来接收命令的输出。
  此方法在创建一个传感器或源连接器类时特别有用，该类隐藏了它使用命令的事实，使其能够像任何其他传感器/连接器一样使用。

- public static Consumer<String> commandWriter(ProcessBuilder cmd):创建一个Consumer<String>将UTF8字符串数据写入命令的输入。
  此方法在创建隐藏使用命令的接收器连接器时特别有用，使其能够像本机连接器一样使用。提供的cmd用于启动命令。每个accept调用（String）将一个UTF8字符串写入命令的输入。每次写入后都有一个刷新。如果写入操作遇到错误，将重新启动该命令。

## http

### HttpClients创建HTTP客户端。在运行时调用这些方法来为HttpStreams创建HTTP客户端。

- public static CloseableHttpClient noAuthentication():返回具有基本身份验证的HTTP客户端。
- public static CloseableHttpClient basic(String user,String password):创建一个带有用户名密码的http客户端
- public static CloseableHttpClient basic(Supplier<String> user,Supplier<String> password):创建基本身份验证HTTP客户端。当调用此方法以获取用户、密码和运行时时，将调用函数user和password。

### HttpStreams

- public static Tstream getJson():创建一个json格式的http get请求，方法专门处理JsonObjects。对于流中的每个JsonObject，httpget请求在提供的uri上执行。然后，响应被添加到响应TStream中。返回包含get请求响应的json数据流
- public static Tstream deleteJson():使用JsonObject发出HTTP delete请求。
  方法专门处理JsonObjects。对于每个请求，在HTTP请求中为每个JsonObject执行。因此，响应被添加到响应TStream中。
- postJson
- putJson
- public static <T,R> Tstream<R> request():为流中的每个元组发出HTTP请求。
  clientCreator被调用一次，以创建一个新的HTTP客户机来发出请求。
  方法为每个元组调用，以定义由元组驱动的HTTP请求使用的方法。固定方法可以使用以下函数声明：t->HttpGet.METHOD_名称
  对每个元组调用uri来定义要用于元组驱动的HTTP请求的uri。固定方法可以使用以下函数声明：t->“http://www.example.com"
  在每个未引发异常的请求之后调用响应。它被传递给输入元组和HTTP响应。函数必须完全使用响应的实体流。如果返回值非空，则此方法返回的流中存在返回值。空返回将导致返回流上没有元组。
- public static <T,R> Tstream<R> requestWithBody():为每个元组生成一个带有body的HTTP请求。

### HttpResponders

- public static <T> BiFunction<> input200():在OK时返回输入元组。函数，如果HTTP响应不正常（200），则返回null（无输出），否则返回输入元组。响应中的HTTP实体被使用并丢弃。4
- public static <T> BiFunction<> inputOn():返回指定代码的输入元组。函数，如果HTTP响应不是代码之一，则返回null（无输出），否则返回输入元组。响应中的HTTP实体被使用并丢弃。
- public static <T> BiFunction<> json():application/json的HTTP响应处理程序。对于每个HTTP响应，将生成一个JSON对象，其中包含：
  请求-导致请求的原始输入元组
  response-包含关于响应的信息的JSON对象
  status—响应的状态代码（整数）
  entity-JSON响应实体（如果存在）

## Mqtt

### MqttConfig

- public static MqttConfig fromProperties(Properties properties):从属性创建新配置。
  每个属性都对应一个属性MqttConfig.set<name>（）方法。除非另有说明，否则属性的值是相应方法的参数类型的字符串。未指定的属性会产生如所述的配置值及其对应的集合<name>（）。
  将忽略除注释外的属性。
- getClientId()
- getActionTimeToWaitMillis()
- getPersistence()
- getConnectionTimeout()
- getIdleTimeout()
- getSubscriberIdleReconnectInterval()
- getKeepAliveInterval()
- getServerURLs()
- getWillDestination()
- getWillPayload()
- getWillQOS()
- getWillRetained()
- isCleanSession()
- getPassword()
- getUserName()
- getTrustStore()
- getTrustStorePassword()
- getKeyStore()
- getKeyPassword()
- options():仅供内部使用。

### MqttStreamMqttStreams是MQTT消息传递代理的连接器，用于发布和订阅主题。

- public TSink<String> publish():将TStream<String>stream的元组发布为MQTT消息。
  一个方便的功能。每个消息的有效负载是字符串元组的值，序列化为UTF-8。返回表示此流终止的TSink sink元素。
- public TStream<T> subscribe():订阅MQTT主题并创建T类型的元组流。每个消息的有效负载都需要是UTF-8编码的字符串。生成的元组只存在转换后的有效负载。
- public Topology topology():获取连接器相关的topology

### MqttDevice

- 基于MQTT的Edgent IoDevice连接器。
  MQTT IoDevice是MqttStreams连接器之上的抽象。
  尽管提供了缺省模式，但连接器并不假定设备MQTT“event”和“command”主题的特定模式。
  设备事件和设备命令的MQTT消息内容必须是JSON。JSON的内容由协作的MQTT客户机控制。通常是一个设备，用于定义其事件和命令模式，并相应地调整其他客户端。有关如何将MQTT消息转换为流元组和从流元组转换MQTT消息的说明，请参阅commands（String…）和events（）。
- public String eventTopic(String eventId):从设备事件中获取MQTT Topic
- public String commandTopic (String command):获取命令的Mqtt Topic
- public MqttConfig getMqttConfig() ：获取设备的配置信息
- public TSink events():将流的元组作为设备事件发布。
  每个元组作为设备事件发布，提供的函数提供事件标识符、有效负载和QoS。基于元组可以生成事件标识符和QoS。事件将发布到配置的MQTTmqttDevice.event.topic.pattern，如上面的类文档中所述，将eventId函数返回的值替换为模式中的“{eventId}”。MQTT消息的有效负载是JsonObject流元组的JSON表示。
- public Tstream commands(String commands):创建设备命令流作为JSON对象。发送到与命令匹配的设备的每个命令都将在流上生成一个元组。JSON对象有以下键：
  - device—命令的不透明目标设备的id字符串。
  - 命令-作为字符串的命令标识符
  - tsms—命令自1970/1/1纪元以来的时间戳（毫秒）。
  - format—命令的字符串格式
  - payload—命令的有效负载
  - 如果format是json，那么有效负载就是json
    否则有效载荷为字符串
- 订阅已配置的MQTTmqttDevice.command.topic.模式，如上述类文档中所述。接收到的MQTT消息的有效负载必须是JSON。消息的JSON负载被转换为JsonObject，并在流元组JsonObject中设置为有效负载键的值。
- public Topology topology():
- public String getDeviceType():获取设备的不透明设备类型标识符。此连接器不支持将设备类型作为其设备id模型的一部分的概念。返回空字符串。
- public String getDeviceId():获取设备的唯一不透明设备id。

