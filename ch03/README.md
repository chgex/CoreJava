## 【Java核心卷1】  第三章 Java的基本程序设计结构

本章主要包括一下内容：

+ 数据类型
+ 字符串
+ 输入输出
+ 控制流程
+ 大数值
+ 数组

以下为正文。

一个简单的`Java`应用程序：

```java
public class FirstSample{
    public static void main(String[] args){
        System.out.println("hello world");//这是带换行。区别于System.out.print()
    }
}
```

Note:

+ java区分大小写
+ main方法必须声明为public
+ 关键字public称为访问修饰符(access modifier)，控制程序的其他部分对该段代码的访问级别
+ 关键字class后面跟类名，类名以大写字母开头，驼峰命名法
+ 源代码的文件名必须与公共类的名字相同，且以`.java`为拓展名
+ 编译源代码后得到一个包含这个字节码的文件，编译器自动命名为`FirstSimple.class`，与源文件存在同一个目录下
+ 使用`java FirstSimple`命令来运行该程序 
+ 运行已经编译的程序时，java虚拟机将从指定类中的main函数开始执行，因此该类的源文件中必须包含一个main方法。可以将用户定义的方法添加到类中，并且在main函数中调用
+ Java的通用语法是`object.method(parameters)`，等价于函数调用
+ java中所有函数都属于某个类的方法（java类方法与c++的类函数是同一个东西），所以java中main方法必须有一个外壳类
+ java中main方法必须是静态的，无返回值

### 数据类型

在Java中一共有八种基本类型(primitive type)，其中4种整型，2种浮点类型，一种字符类型。

#### 4种整型：

| 类型  | 存储  | 取值           |
| ----- | ----- | -------------- |
| short | 2字节 | -32767 ~ 32768 |
| int   | 4字节 | 超过20亿       |
| long  | 8字节 |                |
| byte  | 1字节 | -128 ~ 127     |

Note:

+ 不同于C/C++，Java中整型的范围与运行Java的机器无关
+ 加前缀`0b`或者`0B`可以写二进制数，如0b1001为9
+ 可以在数字下加下划线，增加可读性，如1_000_000
+ Java中没有无符号类型 

#### 2种浮点类型

| 类型   | 存储  |
| ------ | ----- |
| float  | 4字节 |
| double | 8字节 |

Note:

+ float类型的数值有后缀F或f，没有后缀的浮点数值默认为double型

#### char类型

char类型的字面量值要用单引号括起来，如'A'是编码值65对应的字符常量，而“A”是字符串。尽量不要在程序中使用char类型。

#### boolean类型

boolean (布尔）类型有两个值：false 和 true, 用来判定逻辑条件 整型值和布尔值之间不能进行相互转换（与C不一样）。

#### 变量与常量

java中，每个变量都有一个类型(type)，变量名对大小写敏感。

声明变量时，变量的类型位于变量名之前。

声明一个变量之后，必须使用赋值语句来初始化变量。

在 Java 中， 利用关键字 `final` 指示常量，关键字` final` 表示这个变量只能被赋值一次。一旦被赋值之后，就不能够再更改了。习惯上，常量名使用全大写，示例代码：

```java
final double CM_PER_INCH = 2.54;
```

在 Java中，经常希望某个常量可以在一个类中的多个方法中使用，通常将这些常量称为类常量。可以使用关键字 `static  final`设置一个类常量，示例代码：

```java
public class Constants{
	public static final double CM_PER_INCH = 2.54;
public static void main(Stringn args){
    ...
	}
}
```

Note:

+ `const`是 `Java`保留的关键字，但目前并没有使用，所以必须使用 `final`定义常量
+ 类常量的定义必须在main方法外部

#### 枚举

当变量取值在有限的集合内时，使用枚举，示例：

```java
enum Size={S,M,L}
Size s=Size.M 
```

### 字符串

Java字符串就是Unicode字符序列，Java没有内置的字符串类型，而是在标准 Java 类库中提供了一个预定义类，叫做String。

所有用双引号括起来的字符串都是 String类的一个实例。

看代码：

```java
String e="";//空串
String greet="hello";
String s=greet.substring(0,3);//为hel
//取子串，范围为[0,3)

String message=greet+"java";
//支持+拼接
System.out.println("The answer is" + answer);//answer是变量


//把多个字符串放在一起,用定界符分割
String all=String.join("/","S","M","X","XL");
//为"S/M/X/XL"

//将hello修改为help
greet = greet.substring(0, 3) + "p";
```

Note:

+ 由于不能修改 Java 字符串中的字符，在Java文档中将String 类对象称为不可变字符串，“Hello”永远包含字符 H、 e、1、 1 和 o 的代码单元序列， 而不能修改其中的任何一个字符。可以修改字符串变量 greeting， 让它引用另外一个字符串。
+ 不可变字符串一个优点：编译器可以让字符串共享。

看几个函数：

1. 检测字符串是否相等

```java
s.equals(t)
//如果字符串 s 与字符串 t 相等， 则返回 true ; 否则， 返回 false。
 
s.equalsIgnoreCase(t)//检测两个字符串是否相等，而且不区分大小写    
```

Note:

+ 不要使用` ==` 运算符检测两个字符串是否相等,这个运算符只能够确定两个字串是否放置在同一个位置上.

2. 空串与Null串

空串 "" 是长度为 0 的字符串。空串是一个 Java 对象， 有自己的串长度（ 0 ) 和内容（空），可以调用以下代码检查一个字符串是否为空：

```java
if (str.length() = 0) 
//或
if (str.equals(""))
```

String 变量可以存放一个特殊的值， 名为 null, 这表示目前没有任何对象与该变量关联，要检查一个字符串是否为 null, 要使用以下条件：

```java
if (str == null)
```

有时要检查一个字符串既不是 null 也不为空串，这种情况下就需要使用以下条件：

```java
if (str != null && str.length()!= 0)
```

Note:

+ 要先检查 str 不为 null，否则在一个 null 值上调用方法， 会出现错误。

#### 码点与代码单元

Java 字符串由 char 值序列组成。char 数据类型是一个采用 UTF-16 编码表示 Unicode 码点的代码单元。

如果想要遍历一个字符串，并且依次査看每一个码点， 可以将它转换为一个数组再完成遍历。

```java
int[] codePoints = str.codePoints().toArray()；
```

反之，要把一个码点数组转换为一个字符串， 可以使用构造函数（构造函数和 new 操作符 )

```java
String str = new String(codePoints, 0, codePoints.length);
```

#### 常用的string API

 ```java
	char charAt (int index)
//返回给定位置的代码单元
	int codePointAt(int Index)
//返回从给定位置开始的码点 
	offsetByCodePoints(int startlndex, int cpCount) 
//返回从 startlndex 代码点开始，位移 cpCount 后的码点索引。 
	int compareTo(String other)
//按照字典顺序，如果字符串位于other之前，返回一个负数；如果字符串位于other之后，返回一个正数；如果两个字符串相等，返回 0。
	IntStream codePoints() 
//将这个字符串的码点作为一个流返回。调用toArray将它们放在一个数组中。 
	new String(int[] codePoints, int offset, int count)
//用数组中从offset开始的count个码点构造一个字符串。
	boolean equals(0bject other)
//如果字符串与other相等，返回 true。
	boolean equalsIgnoreCase(String other )
//如果字符串与other相等（忽略大小写) 返回 true。 
	boolean startsWith(String prefix ) 
	boolean endsWith(String suffix )
//如果字符串以suffix开头或结尾， 则返回 true。 
	int index0f(String str) 
    int index0f(String str, int fromlndex ) 
    int index0f(int cp) 
    int index0f(int cp, int fromlndex )
//返回与字符串 str 或代码点 cp 匹配的第一个子串的开始位置。这个位置从索引0或fromlndex开始计算。如果在原始串中不存在 str，返回-1。 
        int 1astIndex0f(String str) 
        int 1astIndex0f(String str, int fromlndex ) 
        int lastindex0f(int cp) 
        int 1astindex0f(int cp, int fromlndex )
//返回与字符串 str 或代码点 cp 匹配的最后一个子串的开始位置。这个位置从原始串尾端或 fromlndex 开始计算。 
        int 1ength( )
//返回字符串的长度。 
        int codePointCount(int startlndex , int endlndex )
//返回 startlndex 和 endludex-l之间的代码点数量。没有配成对的代用字符将计入代码点。
        String replace( CharSequence oldString,CharSequence newString)
//返回一个新字符串。这个字符串用 newString 代替原始字符串中所有的 oldString。可以用 String 或 StringBuilder 对象作为 CharSequence 参数。 
	String substring(int beginlndex ) 
    String substring(int beginlndex, int endlndex )
//返回一个新字符串。这个字符串包含原始字符串中从 beginlndex 到串尾或 endlndex-l的所有代码单元。
	String toLowerCase( ) 
    String toUpperCase( )
//返回一个新字符串。 这个字符串将原始字符串中的大写字母改为小写，或者将原始字符串中的所有小写字母改成了大写字母。 
	String trim( )
//返回一个新字符串。这个字符串将删除了原始字符串头部和尾部的空格。
	String join(CharSequence delimiter, CharSequence ... elements ) 
//返回一个新字符串， 用给定的定界符(delimiter)连接所有元素。     
 ```

#### 构建字符串

用许多小段的字符串构建一个字符串：

```java
//第一步：建一个空的字符串构建器：
StringBuilder builder = new StringBuilder();

//第二步：调用 append 方法，添加内容
builder.append(ch); // appends a single character
bui1der.append(str); // appends a string

//第三步：凋用 toString 方法，来得到一个 String 对象， 
String completedString = builder.toString();
```

如上，在构建字符串的时候引入了 StringBuilder 类。该类中的重要方法有：

```java
StringBuilder() //构造一个空的字符串构建器

int length() //返回构建器或缓冲器中的代码单元数量。
    
StringBui1der append(String str) //追加一个字符串并返回 this
    
StringBui1der append(char c) //追加一个代码单元并返回 this 
StringBui1der appendCodePoint(int cp) //追加一个代码点，并将其转换为一个或两个代码单元并返回 this

void setCharAt(int i ,char c) //将第 i 个代码单元设置为 c
    
StringBui1der insert(int offset,String str) //在 offset 位置插入一个字符串并返回 this。 
    
StringBuilder insert(int offset,Char c) //在offset 位置插入一个代码单元并返回 this。 
StringBui1der delete(int startindex,int endlndex)
//删除偏移量从 startindex 到 endlndex-1 的代码单元并返回 this。 

String toString() //返回一个与构建器或缓冲器内容相同的字符串
```



### 输入输出

要想通过控制台进行输入，首先需要构造一个Scanner对象，并且与标准输入流System.in关联。

读取输入的方法：

```java
import java.util.*;
//Scanner 类定义在java.util 包中。 当使用的类不是定义在基本java.lang 包中时，一定要使用 import 指示字将相应的包加载进来。

//第一步：构造一个 Scanner 对象，并与“ 标准输人流” System.in 关联
Scanner in = new Scanner(System.in);

//第二步：利用Scanner类的各种方法实现输入操作

String name = in.nextLine();   //读取一行
String firstName = in.next()； //读取一个单词（以空白符作为分隔符）
int age = in.nextlnt(); 	//读取一个整数
int num = in.nextDouble(); 	//读取一个浮点数
```

Scanner 类不适用于从控制台读取密码，因为其输入是可见的。

所以引入了 Console 类实现这个目的，采用下列代码：

```java
Console cons = System.console();

String username = cons.readLine("User name: ");

char[] passwd = cons.readPassword("Password:");
```

返回的密码存放在一维字符数组中， 而不是字符串中。在对密码进行处理之后，应该马上用一个填充值覆盖数组元素

采用 Console 对象处理输入不如采用 Scanner 方便。每次只能读取一行输入， 而没有能够读取一个单词或一个数值的方法。 

Scanner类中的中重要方法有：

```java
Scanner (InputStream in) //用给定的输人流创建一个 Scanner 对象。
    String nextLine() //读取输入的下一行内容。 
    String next() //读取输入的下一个单词（以空格作为分隔符。) 
    int nextlnt() 
    double nextDouble()
//读取并转换下一个表示整数或浮点数的字符序列。 
    boolean hasNext()//检测输人中是否还有其他单词
    boolean hasNextInt() 
    boolean hasNextDouble()
//检测是否还有表示整数或浮点数的下一个字符序列。
```

#### 格式化输出

```java
System.out.printf("%8.2.f, x);
//8个字符的宽度和小数点后两个字符的精度打印x

//在 printf中，可以使用多个参数
System.out.printf("Hello, %s. Next year, you'll be %d", name, age);
//每一个以 % 字符开始的格式说明符都用相应的参数替换。 
                  
//格式说明符尾部的转换符将指示被格式化的数值类型：f 表示浮点数，s 表示字符串，d 表示十进制整数
```



#### 文件输入与输出

**文件读取**

对文件进行读取，需要用 File 对象构造一个 Scanner 对象：

```java
Scanner in = new Scanner(Paths.get("myflle.txt"), "UTF-8");
```

如果文件名中包含反斜杠符号，就要记住在每个反斜杠之前再加一个额外的反斜杠：  `c:\\mydirectory\\myfile.txt`

读取一个文本文件时，要知道它的字符编码，在这里指定了 UTF-8 字符编码。

**文件写入**

要想写入文件， 就需要构造一个 PrintWriter 对象。在构造器中，只需要提供文件名：

```java
PrintWriter out = new PrintWriter("myfile.txt", "UTF-8");
//如果文件不存在，创建该文件。 
```

**IOException异常的处理**

方法一：在 main 方法中用 throws 子句标记：

```java
public static void main(String[] args) throws IOException
{
Scanner in = new Scanner(Paths.get("myfi1e.txt"), "UTF-8");
}
```

方法二：当采用命令行方式启动一个程序时， 可以利用 Shell 的重定向语法将任意文件关联到 System.in 和 System.out:

```java
java MyProg < myfile.txt > output.txt
```

关联之后，就不必担心处理 IOException 异常了。



### 控制流程

块（即复合语句）是指由 { ... } 括起来的若干条简单的 Java 语句。块确定了变量的作用域。一个块可以嵌套在另一个块中。不能在嵌套的两个块中声明同名的变量。

条件语句的格式为

`if (condition) statement`

while 循环一般格式为

`while (condition ) statement`

for 循环语句是支持迭代的一种通用结构，如下：

`for (int i = 1; i <= 10; i++) System.out.println(i);`

多重选择：switch 语句

Java 有一个与 C/C++ 完全一样的switch 语句。例如：

```java
Scanner in = new Scanner(System.in);
System.out
.print("Select an option (1, 2, 3, 4) ");
int choice = in.nextlnt();
switch (choice){
	case 1:
		...
		break;
	case 2:
		...
        break;
	case 3:
		...
		break;
	case 4:
		...
		break;
	default:    // bad input
		...
        break; 
}
```

switch语句将从与选项值相匹配的 case 标签处开始执行直到遇到 break 语句，或者执行到switch i吾句的结束处为止。

**中断控制流程语句—break**

不带标签的 break 语句。 例如：

```java
while (years <= 100) {
	balance += payment;
	double interest = balance * interestRate / 100;
	balance += interest;
	if (balance >= goal ) break;
	years++ ; 
}
```

带标签的 break语句，用于跳出多重嵌套的循环语句，例如：

```java
Scanner in = new Scanner(System.in);
int n;
read_data:	//label
while (. . .){ 
	for (. . .){
		Systen.out.print("Enter a number >= 0: ");
        n = in.nextln();
        if (n < 0)
		break read.data; // break out of readjata loop
        ...
	}
}
```



### 大数值

java.math 包中的两个类：Biglnteger 和 BigDecimal ，这两个类可以处理包含任意长度数字序列的数值。

Biglnteger 类实现了任意精度的整数运算， BigDecimal 实现了任意精度的浮点数运算。

使用静态的 `valueOf()` 方法可以将普通的数值转换为大数值：

```java
Biglnteger a = Biglnteger.valueOf(100);
```

与 C++ 不同， Java 没有提供运算符重载功能。 程序员无法重定义 + 和 * 运算符。大数值的运算，需要使用大数值类中的 add 和 multiply 方法：

```java
Biglnteger c = a.add(b);
Biglnteger d = c.multipiy(b.add(Biglnteger.valueOf(2))); 
```



### 数组

数组是一种数据结构， 用来存储同一类型值的集合。

通过一个整型下标可以访问数组中的每一个值。例如 a[i] 就是数组中下标为 i 的整数。

在声明数组变量时， 需要指出数组类型，如声明一个整型数组：`int[] a;`或者`int a[];`

初始化为一个真正的数组，需要使用 new 运算符创建数组：

`int[] a = new int[100];` 下标0~99。

或者重新初始化一个已经存在的数组：`a=new int []{1,2,3,4};`

可以在创建数组对象的同时赋予初始值，这种形式下，不需要调用new，例如：

`int[] a={1,2,3,4};`

java中有一种功能很强的循环结构，可以用来依次处理数组中的每个元素而不必指定下标值：`for(variable:collection) statement` 例如：

```java
int[] a = new int[100];
...
for(int element : a)
	System.out.println(element):
```

在 Java 中，允许将一个数组变量拷贝给另一个数组变量。这时， 两个变量将引用同一个数组：

```java
int[] a={1,2,3,4};
int[] b=a;//两个变量将引用同一个数组：
b[0]=5;   //现在a[0]也为5
```

如果将一个数组的所有值拷贝到一个新的数组中去，就要使用 Arrays 类的 copyOf方法：

```java
int b=Arrays.copyOf(a,a.length);
```

第 2 个参数是新数组的长度，如果改为 2*length，则：

+ 如果数组元素是数值型，那么多余的元素将被赋值为 0 ;

+ 如果数组元素是布尔型，则将赋值为 false。
+ 如果长度小于原始数组的长度，则只拷贝最前面的数据元素。

对数值型数组进行排序， 可以使用 Arrays 类中的 sort 方法：

```java
int[] a = new int[100];
Arrays.sort(a)
```




