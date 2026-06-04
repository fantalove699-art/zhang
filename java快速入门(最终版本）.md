# Java 快速入门

## 目录

- [1. Java 简介](#1-java-简介)
- [2. 环境搭建](#2-环境搭建)
- [3. 第一个程序：Hello World](#3-第一个程序hello-world)
- [4. 基本语法](#4-基本语法)
- [5. 数据类型与变量](#5-数据类型与变量)
- [6. 运算符](#6-运算符)
- [7. 流程控制](#7-流程控制)
- [8. 数组](#8-数组)
- [9. 面向对象编程](#9-面向对象编程)
- [10. 异常处理](#10-异常处理)
- [11. 常用字符串操作](#11-常用字符串操作)
- [12. 集合框架](#12-集合框架)
- [13. 文件 I/O](#13-文件-io)
- [14. 多线程基础](#14-多线程基础)
- [15. Lambda 表达式](#15-lambda-表达式)
- [16. 常用工具与学习资源](#16-常用工具与学习资源)

---

## 1. Java 简介

Java 是由 Sun Microsystems（现属 Oracle）于 1995 年推出的面向对象编程语言。其核心设计理念是 **"Write Once, Run Anywhere"（一次编写，到处运行）**，这得益于 Java 虚拟机（JVM）的跨平台特性。

### Java 的核心特点

| 特点 | 说明 |
|------|------|
| 面向对象 | 支持封装、继承、多态 |
| 跨平台 | 通过 JVM 实现平台无关性 |
| 自动垃圾回收 | 无需手动管理内存 |
| 强类型 | 编译时检查类型安全 |
| 多线程 | 内置多线程支持 |
| 丰富的标准库 | 拥有庞大的生态系统 |

### Java 的应用领域

- **企业级后端开发**：Spring Boot / Spring Cloud 微服务架构
- **Android 移动开发**：Android 原生应用
- **大数据处理**：Hadoop、Spark、Flink 等大数据框架
- **中间件与基础设施**：Kafka、Elasticsearch、Tomcat 等

---

## 2. 环境搭建

### 2.1 安装 JDK

JDK（Java Development Kit）是 Java 开发的必备工具包。推荐安装 **JDK 17**（LTS 长期支持版）或 **JDK 21**（最新 LTS 版本）。

**下载地址**：

- Oracle JDK：https://www.oracle.com/java/technologies/downloads/
- OpenJDK：https://adoptium.net/

### 2.2 配置环境变量

安装完成后，需配置以下环境变量：

| 变量名 | 值 | 说明 |
|--------|----|------|
| `JAVA_HOME` | JDK 安装路径，如 `C:\Program Files\Java\jdk-17` | 指向 JDK 根目录 |
| `Path` | 追加 `%JAVA_HOME%\bin`（Windows）或 `$JAVA_HOME/bin`（Linux/Mac） | 使 `java` 和 `javac` 命令全局可用 |

### 2.3 验证安装

```bash
java -version    # 查看 Java 运行时版本
javac -version   # 查看 Java 编译器版本
```

输出类似以下内容即表示安装成功：

```
java version "17.0.x" 2024-xx-xx LTS
Java(TM) SE Runtime Environment (build 17.0.x+x-x)
Java HotSpot(TM) 64-Bit Server VM (build 17.0.x+x-x, mixed mode, sharing)
```

### 2.4 推荐 IDE

| IDE | 特点 | 适用场景 |
|-----|------|----------|
| **IntelliJ IDEA** | 智能提示强大，生态完善 | 首选，适合所有场景 |
| **Eclipse** | 免费开源，插件丰富 | 传统企业项目 |
| **VS Code** | 轻量级，扩展灵活 | 轻量开发与脚本 |

---

## 3. 第一个程序：Hello World

### 3.1 编写代码

新建文件 `HelloWorld.java`，写入以下内容：

```java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}
```

### 3.2 编译与运行

```bash
javac HelloWorld.java   # 编译，生成 HelloWorld.class
java HelloWorld          # 运行，输出 Hello, World!
```

### 3.3 程序结构解析

```
public class HelloWorld {        // 类声明（类名须与文件名一致）
    public static void main(String[] args) {   // 主方法（程序入口）
        System.out.println("Hello, World!");    // 输出语句
    }
}
```

- `public`：访问修饰符，表示公开可见
- `class`：声明一个类
- `static`：静态方法，无需实例化即可调用
- `void`：无返回值
- `main`：方法名，JVM 固定入口
- `String[] args`：命令行参数

---

## 4. 基本语法

### 4.1 注释

```java
// 单行注释

/*
 * 多行注释
 * 可以跨越多行
 */

/**
 * 文档注释（可用于生成 API 文档）
 * @param args 命令行参数
 */
```

### 4.2 关键字

Java 有 50 多个保留关键字，以下为常用关键字分类：

| 分类 | 关键字 |
|------|--------|
| 访问控制 | `public`、`private`、`protected` |
| 类/接口/继承 | `class`、`interface`、`extends`、`implements`、`abstract` |
| 数据类型 | `int`、`long`、`double`、`float`、`boolean`、`char`、`byte`、`short` |
| 流程控制 | `if`、`else`、`for`、`while`、`do`、`switch`、`case`、`break`、`continue`、`return` |
| 异常处理 | `try`、`catch`、`finally`、`throw`、`throws` |
| 其他 | `static`、`final`、`void`、`new`、`this`、`super`、`null`、`true`、`false` |

### 4.3 命名规范

| 类型 | 规范 | 示例 |
|------|------|------|
| 类名 | 大驼峰（UpperCamelCase） | `HelloWorld`、`UserService` |
| 方法名/变量名 | 小驼峰（lowerCamelCase） | `getName`、`userAge` |
| 常量 | 全大写 + 下划线 | `MAX_SIZE`、`DEFAULT_TIMEOUT` |
| 包名 | 全小写 + 点分隔 | `com.example.project` |

---

## 5. 数据类型与变量

### 5.1 基本数据类型（8 种）

| 类型 | 大小 | 默认值 | 范围 | 示例 |
|------|------|--------|------|------|
| `byte` | 1 字节 | 0 | -128 ~ 127 | `byte b = 100;` |
| `short` | 2 字节 | 0 | -32768 ~ 32767 | `short s = 30000;` |
| `int` | 4 字节 | 0 | -2³¹ ~ 2³¹-1 | `int i = 100000;` |
| `long` | 8 字节 | 0L | -2⁶³ ~ 2⁶³-1 | `long l = 100000L;` |
| `float` | 4 字节 | 0.0f | IEEE 754 单精度 | `float f = 3.14f;` |
| `double` | 8 字节 | 0.0d | IEEE 754 双精度 | `double d = 3.14;` |
| `char` | 2 字节 | '\u0000' | 0 ~ 65535 | `char c = 'A';` |
| `boolean` | 1 位 | false | true / false | `boolean flag = true;` |

### 5.2 引用数据类型

- **字符串**：`String`（不可变）
- **数组**：`int[]`、`String[]` 等
- **类、接口、枚举**等自定义类型

### 5.3 变量声明与初始化

```java
// 声明并初始化
int age = 25;
String name = "张三";
double price = 99.9;

// 先声明后赋值
int score;
score = 90;

// 使用 var 关键字（Java 10+，局部变量类型推断）
var count = 10;        // 推断为 int
var message = "Hello"; // 推断为 String
```

### 5.4 类型转换

```java
// 自动类型转换（小 → 大）
int i = 100;
long l = i;       // int → long，自动
double d = l;     // long → double，自动

// 强制类型转换（大 → 小，可能丢失精度）
double pi = 3.14159;
int intPi = (int) pi;  // intPi = 3，小数部分被截断

// 字符串与基本类型互转
String s = String.valueOf(123);     // int → String
int n = Integer.parseInt("123");    // String → int
double d2 = Double.parseDouble("3.14"); // String → double
```

---

## 6. 运算符

### 6.1 算术运算符

```java
int a = 10, b = 3;
System.out.println(a + b);   // 13  加法
System.out.println(a - b);   // 7   减法
System.out.println(a * b);   // 30  乘法
System.out.println(a / b);   // 3   整数除法（截断小数）
System.out.println(a % b);   // 1   取模（取余）
System.out.println(10.0 / 3); // 3.333... 浮点除法
```

### 6.2 关系运算符

```java
int x = 10, y = 20;
x == y   // false  等于
x != y   // true   不等于
x > y    // false  大于
x < y    // true   小于
x >= y   // false  大于等于
x <= y   // true   小于等于
```

### 6.3 逻辑运算符

```java
boolean a = true, b = false;
a && b   // false  逻辑与（短路与）
a || b   // true   逻辑或（短路或）
!a       // false  逻辑非
```

### 6.4 赋值与三元运算符

```java
int a = 10;
a += 5;   // a = 15，等价于 a = a + 5
a -= 3;   // a = 12
a *= 2;   // a = 24
a /= 4;   // a = 6

// 三元运算符
int age = 20;
String result = (age >= 18) ? "成年" : "未成年";  // "成年"
```

---

## 7. 流程控制

### 7.1 条件语句

```java
// if-else
int score = 85;
if (score >= 90) {
    System.out.println("优秀");
} else if (score >= 80) {
    System.out.println("良好");    // 输出此行
} else if (score >= 60) {
    System.out.println("及格");
} else {
    System.out.println("不及格");
}

// switch（Java 14+ 支持箭头语法）
String day = "MONDAY";
switch (day) {
    case "MONDAY", "FRIDAY" -> System.out.println("工作日");
    case "SATURDAY", "SUNDAY" -> System.out.println("周末");
    default -> System.out.println("未知");
}
```

### 7.2 循环语句

```java
// for 循环
for (int i = 0; i < 5; i++) {
    System.out.print(i + " ");  // 0 1 2 3 4
}

// 增强 for 循环（for-each）
int[] nums = {1, 2, 3, 4, 5};
for (int num : nums) {
    System.out.print(num + " ");  // 1 2 3 4 5
}

// while 循环
int count = 0;
while (count < 3) {
    System.out.print(count + " ");  // 0 1 2
    count++;
}

// do-while 循环（至少执行一次）
int num;
do {
    num = (int)(Math.random() * 10);
} while (num < 5);
```

### 7.3 break 与 continue

```java
// break：跳出整个循环
for (int i = 0; i < 10; i++) {
    if (i == 5) break;
    System.out.print(i + " ");  // 0 1 2 3 4
}

// continue：跳过当前迭代
for (int i = 0; i < 10; i++) {
    if (i % 2 == 0) continue;
    System.out.print(i + " ");  // 1 3 5 7 9
}
```

---

## 8. 数组

### 8.1 数组的创建与使用

```java
// 声明与初始化
int[] arr1 = new int[5];              // 默认值全为 0
int[] arr2 = {1, 2, 3, 4, 5};        // 直接初始化
String[] names = new String[]{"Alice", "Bob", "Charlie"};

// 访问元素（索引从 0 开始）
System.out.println(arr2[0]);   // 1
System.out.println(arr2[4]);   // 5

// 修改元素
arr2[2] = 30;  // arr2 变为 {1, 2, 30, 4, 5}

// 数组长度
System.out.println(arr2.length);  // 5
```

### 8.2 二维数组

```java
int[][] matrix = {
    {1, 2, 3},
    {4, 5, 6},
    {7, 8, 9}
};

System.out.println(matrix[1][2]);  // 6

// 遍历二维数组
for (int i = 0; i < matrix.length; i++) {
    for (int j = 0; j < matrix[i].length; j++) {
        System.out.print(matrix[i][j] + " ");
    }
    System.out.println();
}
```

### 8.3 数组工具类 Arrays

```java
import java.util.Arrays;

int[] arr = {5, 2, 8, 1, 9};

Arrays.sort(arr);                            // 排序：{1, 2, 5, 8, 9}
System.out.println(Arrays.toString(arr));    // 打印：[1, 2, 5, 8, 9]

int index = Arrays.binarySearch(arr, 5);     // 二分查找：返回 2
int[] copied = Arrays.copyOf(arr, 8);        // 复制并扩展长度
Arrays.fill(arr, 0);                         // 全部填充为 0
```

---

## 9. 面向对象编程

### 9.1 类与对象

```java
public class Person {
    // 属性（字段）
    private String name;
    private int age;

    // 构造方法
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // 无参构造
    public Person() {
        this("未知", 0);
    }

    // Getter / Setter
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    // 方法
    public void introduce() {
        System.out.println("我叫" + name + "，今年" + age + "岁。");
    }
}

// 使用
Person p = new Person("张三", 25);
p.introduce();  // 我叫张三，今年25岁。
p.setAge(26);
System.out.println(p.getAge());  // 26
```

### 9.2 封装

封装的核心是**隐藏内部实现细节，对外提供公共接口**。

- 使用 `private` 修饰字段
- 提供 `public` 的 getter/setter 方法
- 在 setter 中可加入校验逻辑

```java
public void setAge(int age) {
    if (age < 0 || age > 150) {
        throw new IllegalArgumentException("年龄不合法");
    }
    this.age = age;
}
```

### 9.3 继承

```java
// 父类
public class Animal {
    protected String name;

    public Animal(String name) {
        this.name = name;
    }

    public void eat() {
        System.out.println(name + "在吃东西");
    }
}

// 子类
public class Dog extends Animal {
    private String breed;

    public Dog(String name, String breed) {
        super(name);  // 调用父类构造方法
        this.breed = breed;
    }

    public void bark() {
        System.out.println(name + "在汪汪叫");
    }
}

// 使用
Dog dog = new Dog("旺财", "柴犬");
dog.eat();   // 旺财在吃东西（继承自 Animal）
dog.bark();  // 旺财在汪汪叫（Dog 特有方法）
```

### 9.4 多态

```java
public class Animal {
    public void speak() {
        System.out.println("动物发出声音");
    }
}

public class Cat extends Animal {
    @Override
    public void speak() {
        System.out.println("喵喵喵");
    }
}

public class Dog extends Animal {
    @Override
    public void speak() {
        System.out.println("汪汪汪");
    }
}

// 多态：父类引用指向子类对象
Animal a1 = new Cat();
Animal a2 = new Dog();
a1.speak();  // 喵喵喵
a2.speak();  // 汪汪汪

// 利用多态的循环
Animal[] animals = {new Cat(), new Dog()};
for (Animal a : animals) {
    a.speak();  // 运行时动态绑定
}
```

### 9.5 抽象类与接口

```java
// 抽象类
public abstract class Shape {
    protected String color;

    public Shape(String color) {
        this.color = color;
    }

    // 抽象方法（子类必须实现）
    public abstract double area();

    // 具体方法
    public String getColor() {
        return color;
    }
}

// 接口
public interface Drawable {
    void draw();  // 默认 public abstract

    // Java 8+ 默认方法
    default void printInfo() {
        System.out.println("这是一个可绘制对象");
    }

    // Java 8+ 静态方法
    static void staticMethod() {
        System.out.println("接口静态方法");
    }
}

// 同时继承抽象类并实现接口
public class Circle extends Shape implements Drawable {
    private double radius;

    public Circle(String color, double radius) {
        super(color);
        this.radius = radius;
    }

    @Override
    public double area() {
        return Math.PI * radius * radius;
    }

    @Override
    public void draw() {
        System.out.println("绘制一个" + color + "的圆");
    }
}
```

### 9.6 访问修饰符总结

| 修饰符 | 同类 | 同包 | 子类 | 不同包 |
|--------|:----:|:----:|:----:|:------:|
| `public` | ✅ | ✅ | ✅ | ✅ |
| `protected` | ✅ | ✅ | ✅ | ❌ |
| 默认（无修饰符） | ✅ | ✅ | ❌ | ❌ |
| `private` | ✅ | ❌ | ❌ | ❌ |

---

## 10. 异常处理

### 10.1 异常体系

```
Throwable
├── Error（严重错误，不应捕获）
│   ├── OutOfMemoryError
│   └── StackOverflowError
└── Exception（可处理的异常）
    ├── IOException（受检异常，必须处理）
    ├── SQLException（受检异常，必须处理）
    └── RuntimeException（非受检异常，可选处理）
        ├── NullPointerException
        ├── ArrayIndexOutOfBoundsException
        ├── ClassCastException
        └── IllegalArgumentException
```

### 10.2 try-catch-finally

```java
try {
    int result = 10 / 0;  // 抛出 ArithmeticException
} catch (ArithmeticException e) {
    System.out.println("算术异常：" + e.getMessage());
} catch (Exception e) {
    System.out.println("其他异常：" + e.getMessage());
} finally {
    System.out.println("无论如何都会执行");  // 常用于关闭资源
}
```

### 10.3 try-with-resources（Java 7+）

自动关闭实现了 `AutoCloseable` 接口的资源：

```java
try (FileReader fr = new FileReader("test.txt");
     BufferedReader br = new BufferedReader(fr)) {
    String line;
    while ((line = br.readLine()) != null) {
        System.out.println(line);
    }
} catch (IOException e) {
    e.printStackTrace();
}
// fr 和 br 自动关闭，无需 finally
```

### 10.4 自定义异常与抛出

```java
// 自定义受检异常
public class BusinessException extends Exception {
    private int code;

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() { return code; }
}

// 抛出异常
public void withdraw(double amount) throws BusinessException {
    if (amount < 0) {
        throw new BusinessException(400, "取款金额不能为负数");
    }
    // 正常逻辑...
}
```

---

## 11. 常用字符串操作

```java
String s = "Hello, Java!";

// 基本操作
s.length()              // 12
s.charAt(1)             // 'e'
s.isEmpty()             // false
s.toLowerCase()         // "hello, java!"
s.toUpperCase()         // "HELLO, JAVA!"

// 查找
s.indexOf("Java")       // 7
s.contains("Java")      // true
s.startsWith("Hello")   // true
s.endsWith("!")         // true

// 截取与替换
s.substring(7)          // "Java!"
s.substring(0, 5)       // "Hello"
s.replace("Java", "World")  // "Hello, World!"
s.replaceAll("[0-9]", "*")  // 正则替换

// 分割与拼接
String[] parts = "a,b,c".split(",");  // ["a", "b", "c"]
String joined = String.join("-", parts);  // "a-b-c"

// 去除空白
"  hello  ".trim()      // "hello"
"  hello  ".strip()     // "hello"（Java 11+，支持 Unicode 空白）

// 格式化
String.format("姓名：%s，年龄：%d", "张三", 25)  // "姓名：张三，年龄：25"

// 字符串比较
"hello".equals("hello")       // true（值比较）
"hello".equalsIgnoreCase("HELLO")  // true（忽略大小写）
// ⚠️ 不要用 == 比较字符串内容！
```

> **注意**：`String` 是不可变对象，每次操作都会产生新字符串。频繁拼接请使用 `StringBuilder`：

```java
StringBuilder sb = new StringBuilder();
sb.append("Hello");
sb.append(", ");
sb.append("World");
String result = sb.toString();  // "Hello, World"

// 链式调用
String s2 = new StringBuilder()
    .append("A")
    .append("B")
    .append("C")
    .toString();  // "ABC"
```

---

## 12. 集合框架

### 12.1 集合体系概览

```
Collection（接口）
├── List（有序、可重复）
│   ├── ArrayList   — 动态数组，随机访问快
│   ├── LinkedList  — 双向链表，增删快
│   └── Vector      — 线程安全的动态数组（已过时）
├── Set（无序、不可重复）
│   ├── HashSet     — 哈希表，最快
│   ├── LinkedHashSet — 保持插入顺序
│   └── TreeSet     — 红黑树，自然排序
└── Queue（队列）
    ├── LinkedList  — 同时实现 List 和 Queue
    ├── PriorityQueue — 优先队列（堆）
    └── ArrayDeque  — 双端队列

Map（接口，键值对）
├── HashMap        — 哈希表，最快
├── LinkedHashMap  — 保持插入/访问顺序
├── TreeMap        — 红黑树，按键排序
└── Hashtable      — 线程安全（已过时，用 ConcurrentHashMap 替代）
```

### 12.2 List 常用操作

```java
import java.util.*;

List<String> list = new ArrayList<>();

// 添加
list.add("Java");
list.add("Python");
list.add(1, "C++");       // 在索引 1 处插入

// 访问
list.get(0);               // "Java"
list.size();               // 3

// 修改
list.set(1, "Go");         // 将索引 1 的元素替换为 "Go"

// 删除
list.remove(0);            // 按索引删除
list.remove("Python");     // 按值删除

// 查找
list.contains("Java");     // true
list.indexOf("Go");        // 0

// 遍历
for (String item : list) {
    System.out.println(item);
}

// 使用流式操作（Java 8+）
list.stream()
    .filter(s -> s.startsWith("J"))
    .forEach(System.out::println);
```

### 12.3 Map 常用操作

```java
Map<String, Integer> scores = new HashMap<>();

// 添加
scores.put("语文", 90);
scores.put("数学", 95);
scores.put("英语", 88);

// 访问
scores.get("语文");              // 90
scores.getOrDefault("物理", 0);  // 0（键不存在时返回默认值）

// 判断
scores.containsKey("语文");      // true
scores.containsValue(95);        // true

// 删除
scores.remove("英语");

// 遍历
for (Map.Entry<String, Integer> entry : scores.entrySet()) {
    System.out.println(entry.getKey() + ": " + entry.getValue());
}

// Java 8+ forEach
scores.forEach((k, v) -> System.out.println(k + " = " + v));

// 计算模式
scores.putIfAbsent("化学", 85);           // 键不存在时才放入
scores.computeIfAbsent("生物", k -> 80);  // 键不存在时计算并放入
```

### 12.4 Set 常用操作

```java
Set<String> set = new HashSet<>();

set.add("Java");
set.add("Python");
set.add("Java");       // 重复元素，不会添加
set.size();            // 2

set.contains("Java");  // true
set.remove("Python");

// 集合运算
Set<Integer> a = new HashSet<>(Arrays.asList(1, 2, 3, 4));
Set<Integer> b = new HashSet<>(Arrays.asList(3, 4, 5, 6));

a.addAll(b);        // 并集
a.retainAll(b);     // 交集
a.removeAll(b);     // 差集
```

---

## 13. 文件 I/O

### 13.1 读取文件

```java
import java.nio.file.*;

// 方式一：一次性读取所有行（适合小文件）
List<String> lines = Files.readAllLines(Paths.get("input.txt"));
lines.forEach(System.out::println);

// 方式二：流式读取（适合大文件，懒加载）
try (Stream<String> stream = Files.lines(Paths.get("input.txt"))) {
    stream.filter(line -> line.contains("error"))
          .forEach(System.out::println);
}

// 方式三：经典 BufferedReader
try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
    String line;
    while ((line = br.readLine()) != null) {
        System.out.println(line);
    }
}
```

### 13.2 写入文件

```java
import java.nio.file.*;

// 写入字符串
Files.writeString(Paths.get("output.txt"), "Hello, File!", StandardOpenOption.CREATE);

// 写入多行
List<String> data = List.of("第一行", "第二行", "第三行");
Files.write(Paths.get("output.txt"), data, StandardCharsets.UTF_8);

// 追加写入
Files.writeString(Paths.get("output.txt"), "\n追加内容", StandardOpenOption.APPEND);
```

### 13.3 文件与目录操作

```java
Path path = Paths.get("test.txt");

Files.exists(path);                    // 是否存在
Files.isDirectory(path);               // 是否是目录
Files.size(path);                      // 文件大小（字节）
Files.getLastModifiedTime(path);       // 最后修改时间

Files.copy(path, Paths.get("copy.txt"), StandardCopyOption.REPLACE_EXISTING);
Files.move(path, Paths.get("moved.txt"), StandardCopyOption.REPLACE_EXISTING);
Files.deleteIfExists(Paths.get("temp.txt"));

// 创建目录
Files.createDirectories(Paths.get("a/b/c"));
```

---

## 14. 多线程基础

### 14.1 创建线程的两种方式

```java
// 方式一：继承 Thread 类
class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " 运行中");
    }
}
new MyThread().start();

// 方式二：实现 Runnable 接口（推荐）
Thread t = new Thread(() -> {
    System.out.println(Thread.currentThread().getName() + " 运行中");
});
t.start();
```

### 14.2 线程同步

```java
// synchronized 关键字
public class Counter {
    private int count = 0;

    public synchronized void increment() {
        count++;
    }

    public synchronized int getCount() {
        return count;
    }
}

// 同步代码块
private final Object lock = new Object();
synchronized (lock) {
    // 临界区代码
}
```

### 14.3 线程池（推荐方式）

```java
import java.util.concurrent.*;

// 创建固定大小线程池
ExecutorService executor = Executors.newFixedThreadPool(4);

// 提交任务
executor.submit(() -> {
    System.out.println("任务在线程池中执行");
});

// 提交有返回值的任务
Future<Integer> future = executor.submit(() -> {
    return 42;
});
Integer result = future.get();  // 阻塞等待结果

// 关闭线程池
executor.shutdown();
executor.awaitTermination(10, TimeUnit.SECONDS);
```

---

## 15. Lambda 表达式

### 15.1 基本语法

```java
// 无参数
() -> System.out.println("Hello")

// 单参数（可省略括号）
x -> x * x

// 多参数
(a, b) -> a + b

// 多行代码块
(x, y) -> {
    int sum = x + y;
    return sum;
}
```

### 15.2 常见用法

```java
// 排序
List<String> names = Arrays.asList("Charlie", "Alice", "Bob");
names.sort((a, b) -> a.compareTo(b));
names.sort(String::compareTo);  // 方法引用，等价写法

// 集合遍历
names.forEach(name -> System.out.println(name));
names.forEach(System.out::println);  // 方法引用

// 替代匿名内部类
Runnable r = () -> System.out.println("运行中");
new Thread(r).start();

// 函数式接口
@FunctionalInterface
interface MathOperation {
    int operate(int a, int b);
}
MathOperation add = (a, b) -> a + b;
MathOperation mul = (a, b) -> a * b;
add.operate(3, 5);  // 8
mul.operate(3, 5);  // 15
```

### 15.3 Stream API 常用操作

```java
import java.util.stream.*;

List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

// 过滤 + 收集
List<Integer> evens = numbers.stream()
    .filter(n -> n % 2 == 0)
    .collect(Collectors.toList());  // [2, 4, 6, 8, 10]

// 映射
List<String> strs = numbers.stream()
    .map(n -> "数字" + n)
    .collect(Collectors.toList());

// 归约
int sum = numbers.stream()
    .reduce(0, Integer::sum);  // 55

// 统计
IntSummaryStatistics stats = numbers.stream()
    .mapToInt(Integer::intValue)
    .summaryStatistics();
stats.getMax();    // 10
stats.getMin();    // 1
stats.getAverage(); // 5.5
stats.getSum();    // 55

// 分组
List<String> fruits = List.of("apple", "banana", "avocado", "blueberry", "cherry");
Map<Character, List<String>> grouped = fruits.stream()
    .collect(Collectors.groupingBy(s -> s.charAt(0)));
// {a=[apple, avocado], b=[banana, blueberry], c=[cherry]}
```

---

## 16. 常用工具与学习资源

### 16.1 构建工具

| 工具 | 说明 | 官网 |
|------|------|------|
| Maven | 基于 XML 的项目管理和构建工具 | https://maven.apache.org/ |
| Gradle | 基于 Groovy/Kotlin DSL 的灵活构建工具 | https://gradle.org/ |

### 16.2 常用第三方库

| 领域 | 库名 | 说明 |
|------|------|------|
| JSON 处理 | Jackson / Gson | JSON 序列化与反序列化 |
| HTTP 客户端 | OkHttp / Apache HttpClient | 网络请求 |
| 日志 | SLF4J + Logback | 日志框架组合 |
| 单元测试 | JUnit 5 | 测试框架 |
| 数据库 | MyBatis / JPA (Hibernate) | ORM 框架 |
| Web 框架 | Spring Boot | 企业级应用开发首选 |

### 16.3 学习路线建议

```
Java 基础语法 → 面向对象 → 集合框架 → 异常处理 → I/O 操作
    → 多线程 → Lambda / Stream → 数据库 (JDBC)
    → Web 开发 (Spring Boot) → 微服务 (Spring Cloud)
```

### 16.4 推荐学习资源

- **官方教程**：https://docs.oracle.com/javase/tutorial/
- **菜鸟教程 Java 篇**：https://www.runoob.com/java/
- **《Java 核心技术》**（Core Java）— 入门经典教材
- **《Effective Java》** — 进阶必读，最佳实践指南
- **《Java 编程思想》**（Thinking in Java）— 深入理解 Java 设计哲学
