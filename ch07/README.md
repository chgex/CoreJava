## 第7章 异常、断言、日志

主要内容：

+ java异常
+ 使用断言，进行有选择的检测
+ 标准的java日志框架

具体内容有：

> 处理错误、捕获异常，使用异常机制，
>
> 断言，日志，调试。

### 异常

在java中，异常对象都是派生于`Throwable`类的一个实例。

java异常，层次结构图如下：

<img src="https://gitee.com/changyv/md-pic/raw/master/20210303213543.png" alt="image-20210303213540530" style="zoom: 50%;" />

> 可见，所有的异常都是从`Throwable`类继承而来，但在下一层分为Error和Exception。

Note:

+ Error类，描述了java运行时系统内部错误和资源耗尽错误。
+ Exception类有两个分支：由程序错误导致的异常属于`RuntimeException`，而程序没有问题，但由于像IO错误的问题导致的异常输入`IOException`。

重点关注的是`RuntimeException`，该异常包含以下情况：

+ 错误的类型转换
+ 数组访问越界
+ 访问null数组

`IOException`异常包含以下问题：

+ 试图在文件末尾后面读取数据
+ 试图打开一个不存在的文件。

+ 试图根据给定字符串查找Class对象，但没找到(不存在)。

java语言规范，将派生于`Error`类或`RuntimeException`类的所有异常称为非受查异常(unchecked)。之外的称为受查(checked)异常。

> 编译器将检查是否为所有的受查异常，提供异常处理器。

**声明受查异常**

一个方法不仅需要告诉编译器需要返回什么值，还要告诉编译器可能发生的错误。比如：

```java
public FileInputStream(String name) throws FileNotFoundException
```

> 这个声明表示，可能会发生`FileNotFoundException`异常。

在遇见以下情况时，应该抛出异常(即应该定义`throws xxException`)：

1. 调用一个抛出受查异常的方法。

比如上述`FileInputStream`方法。

2. 程序运行过程中发现错误，且利用`throws`语句抛出一个异常。

3. java运行时，库出现内部错误。

> 如果出现上述前2种情况，就需要告诉调用了这个方法的程序员，及时处理。

Note:

+ 对于可可能有异常的方法，应该在这个方法的首部，声明这个方法可能抛出的异常。
+ 可以同时声明多个异常，使用`,`隔开。

比如：

```java
public MyClass
{
    ...
    public Image loadImage(String s)throws FileNotFoundException, EOFException   
    ...
}
```

一个方法，必须声明所有可能抛出的异常。

**如果throw一个异常**

示例：

```java
throw new EOFException();
```

或者

```java
EOFException e=new EOFException();
throw e;
```

完整的示例：

```java
Sring readData(Scanner in)throws EOFException
{
    ...
    while(...){
        if(!in.hasNext()){//EOF encountered
        if(n<len)
            	throw new EOFException();
        }
        
    }
    ...
    return s;
}
```

上述`EOFException`是一个已经存在（java中已经定义好了）的异常类，所以直接创建就可以抛出了。

但有些时候，需要自定义异常，(这个异常派生于`Exception`类，或者其子类)。

> java中，只能抛出`Throwable`子类的对象（参照上面的异常对照图），而在C++中，可以抛出任何类型的值。

**创建异常类**

自定义异常：比如定义一个派生于`Exception`的类，那么定义的类应该包含两个构造器，一个是默认构造器，另一个是带有详细描述信息的构造器(超类`Throwable`的`toString()`方法将会打印这些详细信息)。

示例：自定义异常类型`FileFormatException`：

```java
class FileFormatException extends IOExceptioon
{
    public FileFormatException(){}
    public FileFormatException(String gripe){
        super(gripe);
    }
}
```

抛出该异常：

```java
String readData(BufferedReader in)throws FileFormatException
{
    ...
    while(...){
        if(ch==-1)//EOF encounter
        {
            if(n<len)
        		throws new FileFormat
		}
        ...
    }
    return s;
}
```

### 捕获异常

如果一个异常没有在任何地方进行捕获，那么程序就会终止执行，并在控制台上打印出异常信息：包括异常的类型和堆栈的内容。

使用`try caych`语句块，来捕获异常：

```java
try
{
    code;
}
catch(ExceptionType e)
{
    handle e;
}
```

Note:

+ 如果try语句块中的某行代码，抛出了一个异常类（该异常类在catch子句中已经说明），那么程序将跳过try语句块剩下的代码，转而执行catch语句块的处理代码。
+ 如果try语句块没有抛出异常，那么程序就会跳过catch子句。
+ 如果try语句块中抛出的异常，在catch中没有声明，程序就会立即退出。

> 通常，应该捕获那些知到如何处理的异常，而传递那些不知道怎么处理的异常。

+ 在一个try语句，可以捕获多个不同类型的异常，并做不同的处理。

示例：

```java
try
{
    code;
}
catch(FileNotFoundException e)
{
    action;
    //get exception info
    e.getClass().getName();
}
catch(IOException e)
{
    action;
    //get exception info
    e.getClass().getName();
}
```

**包装异常**

一个捕获异常并抛出，示例：

```java
try
{
    access the database;
}
catch(SQLException e)
{
    throw new ServletException("database error:"+e.getMessage());
}
```

> 上述`SQLException e`使用带有异常信息文本的构造器来构建。

但是，可以将原始异常，通过“包装”技术，来将其设置为“原因”进行传递，比如：

```java
try
{
    access the database;
}
catch(SQLException e)
{
    Throwable se=new ServletException("database error");
    se.initCause(e);
    throw se;
}
```

当捕获到异常的时候，就可以使用以下语句，重新得到原始异常：

```java
Throwable e=se.getCause();
```

建议使用“包装”技术，方便查看原始异常的细节。

如果在一个方法中，发生一个受查异常，但不允许抛出，就可以使用包装技术，将它写道日志里，示例：

```java
try
{
    access the database;
}
catch(Exception e)
{
    logger.log(level,message,e);
    throw e;
}
```

**finally子句**

前面已经知道，当代码抛出一个异常的时候，就不再执行之后的代码，如果该代码之前获得了本地资源，就面临资源回收的问题。而java中的finally子句，就可以解决这种问题：不管是否有异常被捕获，finally子句中的代码都会被执行。

示例：

```java
InputStreat in =new FileInputStream(...);
try
{
    code might throw exception;
}
catch(IOException e)
{
    show error msg;
}
finally 
{
    in.close();
}
```

Note：

+ 如果try块中代码没有抛出异常，则执行try块全部代码，然后执行finally块代码；
+ 如果try块中抛出一个catch子句捕获的异常，则执行完catch块中处理异常的代码之后，执行finally块代码。
+ 如果try块中抛出一个异常，但该异常不是由catch子句捕获的，则直接执行finally子句。

总之，无论try块中是否抛出异常，或抛出的异常是否能够被catch子句捕获，finally子句最终都是会执行的。

`try`语句可以只有`finally`子句，而没有`catch`子句。

上述示例中，如果`finally`子句也抛出异常，那就没法处理了，所以可以使用以下写法：解耦合`try / catch`和`try/finally`语句，即让catch捕获所有可能的异常：

```java
InputStream in=...;
try
{
    try
    {
        code might throw exception
    }
    finally
    {
        in.close();
    }   
}
catch(IOException e)
{
    show error msg;
}
```

内层`try/finally`，负责关闭输入流，外层`try/catch`负责报告出现的错误（这个错误，可包含`finally`子句可能出现的错误）。

**带资源的try语句**

带资源的try语句，形式如下：

```java
try(Resouce res=...)
{
    work with res;
}
```

这时，当try语句退出时，就会自动调用`res.close()`。

示例：

```java
try(Scanner in=new Scanner(new FileInputStream("./words")),"UTF-8");
{
    while(in.hasNext())
        System.out.println(in.next());
}
```

> 上述try块正常退出的时候，会自动调用`in.close()`方法，等价于使用了`finally`块。

**分析堆栈轨迹元素**

堆栈轨迹(stack trace)是一个方法调用过程的列表，包含程序职系那个过程中方法调用的特定位置。

可以调用`Throwable`类的`printStackTrack()`方法，访问堆栈轨迹的文本描述信息。

示例：

```java
Throwable t=new Throwable();

StringWriter out=new StringWriter();
//trace
t.printStackTrace(new PrintWriter(out));
//string 
String description=out.toString();
```

也可以使用`getStackTrace()`方法，它会得到`StackTraceElement`对象的一个数组。

示例：

```java
Throwable t=new Throwable();

StackTraceElement[] frames=t.getStackTrace();

for(StackTraceElement frame:frames){
    analyze frame;
}
```

静态的`Thread.getAllStackTrace()`方法，它可以产生所有现程的堆栈轨迹。

**使用异常机制的技巧**

1. 不要过分细化异常。否则会导致代码膨胀
2. 不要压制异常。

但是发生异常时，可以选择忽略：

```java
public Image loadImage(String s)
{
    try
    {
        code may throw exception;
    }
    catch(Exception e)
    {}  //so, you can ignore 
}
```

### 断言

断言机制允许在测试期间，向代码插入一些检查语句。当代码发布时，这些检测语句会被自己动移走。

java语言断言的关键字：`assert`。它有两种形式：

1. `assert 条件`；
2. `assert 条件：表达式;`

第2种形式中，表达式部分的唯一目的是产生一个消息字符串。

示例：

```java
assert x>=0;

//
assert x>=0:x;
```

**启用和关闭断言**

java中，有3种处理系统错误的机制：

1. 抛出一个异常
2. 日志
3. 使用断言

关于断言：

+ 断言为false，则不可恢复
+ 断言检查只用于开发和测试阶段，以确定程序内部的错误位置。

默认情况下，断言被禁用，可以在运行程序时，用`-enableassertions`或`-ea`选项启动。示例：

```java
java -ea Test
```

可以在某个类或整个包中使用断言。示例：

```java
java -ea:TestClass -ea:com.company.lib... App
```

禁用断言：`-disableassertions`或`-da`。

### 日志

**基本日志**

要生成简单的日志记录，可以使用全局日志记录器(global logger)，并调用器info方法。示例：

```java
Logger.getGlobal().info("File->open menu item");
```

**高级日志**

在一个专业的引用程序中，不要将所有的日志都记录在一个全局日志记录中，而是可以自定义日志记录器。示例：

```java
private static final Logger myLogger=Logger.getLogger("com.company.app");
```

> 之所以声明为static final，是因为java中未被任何变量引用的日志记录器，会被当做垃圾回收。

Note:

+ 于包名类似，日志记录器也具有层次结构。
+ 通常情况下，日志有7个级别。

之后的内容，暂时跳过。P314。

### 调试技巧

1. 使用以下方式，打印或记录任意变量的值：

```java
//print
System.out.println("x:"+x);

//or log
Logger.getGlobal().info("x:"+x);
```

> 如果x是个值，就会被转为字符串，如果x是一个对象，java会调用这个对象的`toString()`方法。

2. 可以在每个类中，放置一个单独的main方法，这样就可以对每一个类进行单元测试。

比如：

```java
public class Myclass
{
    method and fileds;
    ...
    public static void main(String[] args){
        test code;
    }
}
```

> 可以为每个类，保留一个main方法，这样就可以分别对每个文件进行测试。
>
> 在运行applet引用程序的时候，这些main方法不会被调用，在运行应用程序的时候，java虚拟机只会调用启动类的main方法。

3. 使用`Throwable`类提供的`printStackTrace()`方法，可以从任何一个异常对象中获得堆栈情况。

以上。



