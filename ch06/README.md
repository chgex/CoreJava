## 【java核心卷1】第6章

第6章将介绍几种常用的高级技术。主要内容：

+ 接口技术(interface)

> 接口技术主要用来描述类具有什么功能，并不给出每个功能的具体实现。
>
> 一个类可以实现（ implement) —个或多个接口。

+ `lambda`表达式

> 用来表示回调或变量行为的简洁的代码

+ 内部类（ inner class) 机制

> 内部类定义在一个类的内部，
>
> 内部类的方法可以访问所在外部类的域。

### 接口

接口描述了一组需求，这些类遵从接口描述并做定义。

> 换句话说，如果某个类遵从一个特定接口，那么这个类就要实现这个接口所要求的需要。

例子：Array类中的sort方法可以对对象排序，但前提条件是：该对象所属的类必须实现了Comparable接口，这个接口是这样的：

```java
public interface Comparable<T>
{
    int compareTo(T other);
    //parameter is type T
}
```

就是说，这个对象的类，必须包含了`compareTo()`方法。

Note :

+ 接口中所有的方法自动属于`public`

> 上述代码`int compareTo(..)`等价于`public int comparaTo()`

+ 接口不能包含实例域

> 换句话说，接口中，只有方法或常量，而且这个方法一般不在接口定义时实现，而是在实现接口的那个类中来完成。

+ 接口不能包含实例域或静态方法，但可以包含常量，如`double PI=3.1415;//ok`，`double pi;//no`

+ 接口声明中的方法可以省略public，但在类中实现所有方法时，方法前必须声明public。

**实现**

让类实现接口中的方法，需要2步：

1. 类声明为实现给定的接口
2. 在类中，定义该接口中的所有方法。

示例：

```java
public Employee implements Comparable<Employee>
{
    private name;
    private salary
    public Employee(String name,double salary){
        ...
    }
    //method
    ...
        
        
    //实现接口中的方法
    //public is must
    public int compareTo(Employee other){
        return Double.compare(salary,other.salary);
    }
}
```

现在，我们知到，要让一个类实现Array的sort服务，该类就必须实现了`compareTo()`方法。

关于为什么不在Employee类中直接实现`compareTo()`方法，而是必须通过接口的方式来实现，原因是：java是强类型语言，在调用方法时，编译器会检查该方法是否存在。如下代码：

```java
if(a[i].compareTo(a[j])>0){
    ...
}
```

该语句默认a[i]有`compareTo()`方法，如果a是一个Comparable对象的数组，那么该方法就必然存在了。

**覆盖compareTo方法**

在Employee中定义了`compareTo()`方法为`public int compareTo(Employee other)`，Manager类继承Employee类并覆盖了该方法，如下：

```java
class Manager extends Employee
{
    ...
    //overloading    
    public int compareTo(Employee other){
        Manager otherManager=(Manager)other;//no
    }
}
```

以下写法将不符合“反对称”规则：雇员可以和经理比较，但反过来，经理不能和雇员比较。

与上一章的equals方法一样，有两种解决办法：

1. 禁止不同类之间的比较

这需要在`compareTo()`方法前加上类型判断：

```java
if(getClass()!=other.getClass())
	throw new ClassCastException();
```

2. 允许不同类之间的比较。这需要在超类中提供一个`compareTo()`方法，并声明为`final`

**接口特性**

+ 接口不是类，`x=new Comparable(..) `是错的。
+ 可以声明接口变量。`Comparable x ; //ok`
+ 接口变量必须引用实现了接口的类的对象，`x=new Employee(...);//ok`

+ 可以使用`insanceof()`来检查一个对象是否属于某个特定的接口，如`if(anObject instanceof Comparable){...}`

+ j接口中可以包含常量，` double pi=3,1415`等价于`public static final pi=3.1415`。因为接口中的域，会自动变为`public static final`

**接口与抽象类**

为什么不将接口变为抽象类，比如：

```java
abstract class Comparable
{
    public abstract int compareTo(Object other);
}

//and then
class Employee extends Comparable
{
    ...
    //overloading    
	public int compareTo(Object other){
        ...
    }   
}
```

因为：使用抽象类来表示通用属性，存在一个问题：java中每个类只能扩展一个类。下面这种扩展两个类的写法是错误的：

```java
class Employee extends Person,Comparable  //error
{
    ...
}
```

**静态方法**

允许在接口中添加静态方法，通常的做法是将静态方法放在伴随类中。

接口中的静态方法，示例：

```java
public interface Path
{
    public static Path get(String first,String... more){
        return FileSystems.getDefault().getPath(first,more);
    }
    ...
}
```

**默认方法**

可以为接口提供一个默认方法，必须使用修饰符`default`，比如：

```java
public interface Comparable<T>
{
    default int comparable(T other){
        return 0;
    }
}
```

实际中，这种default方法用处不大，因为大多数会被覆盖掉。

**默认解决方法冲突**

如果在一个接口中定义了默认方法，然后在超类或另一个接口中定义了通用的方法，规则：

+ 超类优先
+ 接口冲突：超接口中提供了一个方法，另一个接口中提供了同名同参数类型的方法，则必须覆盖，以解决冲突。

第2点，示例：

一个接口，拥有`getName()`方法：

```java
public interface Named
{
    default String getName(){
        return getClass().getName();
    }
}
```

另一个接口，也拥有`getName()`方法：

```java
public interface Person
{
    default String getName(){
        return getClass().getName();
    }
}
```

现在，有一个类同时实现了这2个接口：

```java
class Student implements Person,Name
{
    ...
}
```

这个时候，该类会继承两个接口提供的两个方法，java编译器会报错，让程序员来解决这种二义性。

通过指定，解决二义性：

```java
class Student implements Person,Name
{
    ...
    public String getName(){
        return Person.getClass().getName();
	}
}
```

### 接口示例

**接口与回调**

回调(callbck)，指出当某个特定事件发生时，要采取的动作。比如按下鼠标时应该采取申明动作。

> 回调是一种程序设计模式。

`java.swing.Timer`类，可以被用来在指定时间间隔内发出通告。`java.awt.event`包中的``ActionListener`接口定义如下：

```java
//java.awt.event包中的ActionListener接口的定义
public interface ActionListener
{
    void actionPerformed(ActionEvent event);
}
```

定义`TimerPrinter.calss`，使其每隔5秒，打印出一个信息，代码：

```java
class TimerPrinter implements ActionListener
{
    //overwrite method
    public void actionPerformed(ActionEvent event){
        System.out.println("at the tone,the time is:"+new Date());
        Toolkit.getDefaultToolkit().beep();
    }
}
```

接下来，构造这个对象，并启动：

```java
ActionListener listener=new TimePrinter();
//传递给Timer构造器
Timer t=new Timer(1000,listener);

//start
t.start();
```

为什么不使用一个函数，让定时器周期性的调用这个函数，而是使用了一个类呢？

> 因为，在java标准库中的类采用的是面向对象方法：它将某个类的对象传递给定时器，然后，定时器调用这个对象的方法，所以比传递一个函数要灵活。

**Comparator接口**

String类已经实现了`compareTo()` 方法，是按照字典序的。如果要让它按照字符串长度序，这个时候就需要用到`Arrays.sort()`方法的第二个版本：数组和比较器(comparator)参数。

> 其中比较器是实现了Comparator接口的类的实例。

比较器使用了Comparator接口，它是这样定义的：

```java
public interface Comparator<T>
{
    int compare(T first,T second); 
}
```

于是，比较器代码为：

```java
class LengthCmp implements Comparator<Sting>
{
    public int compare(String first,String second){
        return first.length()-second.length();
    }
}
```

使用比较器，示例：

```java
Comparator<String> cmp=new LengthCmp();

if(cmp.compare(words[i],words[j])>0){
    ...
}
```

**对象克隆**

先看一段代码：

```java
Employee original=new Employee("alice");
Employrr copy=original;
```

上述代码中两个对象的状态如下：

![image-20210302185427095](https://gitee.com/changyv/md-pic/raw/master/20210302185436.png)

如果希望copy是个新的对象，只是拥有和original一样的状态，那么就需要用到clone方法：

```java
Employee original=new Employee("alice");
Employee copy=original.clone();
```

现在，两个对象的状态如下：

![image-20210302185728417](https://gitee.com/changyv/md-pic/raw/master/20210302185730.png)

Note：

+ 默认的clone方法是浅拷贝，它并不能clone对象中引用的其它对象，比如Employee对象中的`Date hireDay`域，这是一个可变域。

上述两个对象，浅拷贝（即clone）之后，完整状态如下：

<img src="https://gitee.com/changyv/md-pic/raw/master/20210302190125.png" alt="image-20210302190123124" style="zoom: 50%;" />

要使得对象中的子对象也被clone，就需要重新定义clone方法，来克隆所有子对象。

> 这种重定义clone方法，使克隆所有子对象的方式，叫做深拷贝。

在java中，接口`Cloneable`，用来给一个类提供一个安全的clone方法。所以定义深拷贝的代码为：

```java
class Employee implements Cloneable
{
    ...
    public Employee clone throws CloneNotSupportedException
    {
        //call Object.clone
        Employee cloned=(Employee)super.clone();
        
        //clone mutable fields
        cloned.hireDay=(Date) hireDay().clone();
        return cloned;
    }
    ...
}
```

> 示例中的Employee的hireDay域是可变型的Data类型。

Note: 

+ 所有数组类型都有一个public型的clone方法，而不是protected。使用该方法可以建立副本。示例：

```java
int[] nums={1,2,3,4,5};
int[] cloned=nums.clone();//包含nums数组中所有元素的副本

```

+ 对于每个类，先确认默认的clone方法(即浅拷贝)是否满足需求。
+ 如果需要深拷贝，使用接口：`implements Cloneable`，然后重新定义clone方法，并指定为public。

### lambda表达式

lambda表达式采用一种简洁的语法定义代码块，它是一种可以传递的代码块。

why lambda？

+ java是一种面向对象语言，所以不能直接传递代码段。通常做法是构建一个对象，这个对象的类有一个方法，这个方法包含这段所需的代码段。lambda表达式提供了这种传递代码块的简洁方式。

**lambda表达式的语法**

lambda表达式，就是一个代码块，所以要有传入代码的变量规范。示例：

```java
(String first,String second)
->frist.length()-second.length()
```

> 这是一个排序的例子，用来检查一个字符串比另一个字符串段。

lambda表达式的语法：`(paraments)->{表达式}`。

Note:

+ 即使lambda表达式没有参数，但也不能省略`()`

示例：`()->{for(int i=10;i>=0;i--) System.out.println(i);}`

+ 如果可以推导出lambda表达式的参数类型，则可以省略其类型。

示例：`Comparator<String>cmp=(String first,String second)->first.length()-second.length();`

等价于`Comparator<String>cmp=(first,second)->first.length()-second.length();`

+ 如果只有一个参数，而且该参数类型可以被推导出来，这个时候，就可以省略`()`

示例：`ActionListener listener=event->System.out.println("time:"+new Date());`，只有一个参数event，且可以推导出其类型，所以省略了`()`

+ 不需要指出lambda表达式的返回类型。

> 返回类型总是会由上下文推导出来。

+ 如果lambda表达式只在一个分支返回了值，而在另一个分支不返回值，这是不合法的。

error示例:

`(int x)->{if(x>=0) return -1;}`

正确示例：

`(int x)->{if(x>=0) return -1;else return 0;}`

**函数式接口**

java中有许多接口，比如Comparator接口，而lambda表达式与这些接口是兼容的：如果一个接口只有一个抽象方法，当需要这种接口的对象时，就可以通过提供一个lambda来表示。

> 上述这种接口，就叫做函数式接口。

示例：

`Arrays.sort()`方法，它的第二个参数，需要一个`Comparator`实例，

> 这个`Comparator`接口只有一个方法，所以可以通过提供一个lambda表达式的方式来表示。

以lambda表示：

```java
Arrays.sort(words,(first,second)->first.length()-second.length());
```

> 最好将lambda表达式看作一个函数，而不是一个对象。
>
> 另外，lambda表达式可以传递到函数式接口。

**方法引用**

如果只要出现定时器时事件，就打印这个事件对象，可以写为：

```java
Timer t=new Timer(1000,event->System.out.prinln(event));
```

上述写法使用了一个lambda表达式，但是，以下方式更加简洁：

```java
Timer t=new Timer(1000,System.out::println);
```

代码中，`System.out.println`是一个**方法引用(method reference)**，它等价于lambda表达式`x->System.out.println(x);`

方法引用示例：

```java
Arrays.sort(strings,String::compareToIgnoreCase)
```

> 实现对字符串排序，而不考虑字母的大小写。

Note:

+ 方法引用可以使用this参数，也可以使用super参数。

+ 方法引用中，`::`操作符用于分割方法名与对象，或类名。

**构造器引用**

构造器引用与方法引用类似，但需要方法名`new`。

*先跳过。P254。*

**变量作用域**

Note:

+ lambda可以捕获外围作用域中变量的值，但只能引用值不会发生改变的那些变量。

+ lambda表达式不能修改捕获的变量。

正确示例：

```java
public static void repeatMesg(String text,int delay)
{
    ActionListener listener=event->{
        System.out.println(text);
        Toolkit.getDefaultToolkit.beep();
    } ;
    new Timer(delay,listener).start();
}
//ok
```

error 示例：

```java
public static void countDown(int start,int delay)
{
    ActionListener listener=event->{
        start--;//error can't mutable captured variable
        System.out.println(start);
    };
    new Timer(delay,listener).start();
}
//no
```

+ 如果在lambda表达式中引用了变量，但是这个变量可能在外部发生改变，这也是不合法的。

error 示例：

```java
public static void repeat(String text,int count)
{
    for(int  i=1;i<=count;i++){
        ActionListener listener=event->{ 
        System.out.println(i);
        //error:can't refer to changing i 
        }; 
        new Timer(1000,listener).start();
    }
}
```

+ 规则：lambda表达式中捕获的必须是最终变量(effectively final)。即这个变量在初始化之后，就不再被赋新值。

+ lambda表达式中声明与一个局部变量同名的参数，是不合法的。

+ 在一个lambda表达式中使用this参数，是指创建这个lambda表达式的方法的this 参数。示例如下：

```java
public class Application
{
    public void init(){
        ActionListener listener =event->{
            System.out.println(this.toSring());
        }
        ...
    }
}
```

上述程序中的`this.toString()`会调用`Application`对象的`toString()`方法。

### 内部类

内部类是定义在另一个类中的类。

为什么使用内部类？

+ 内部类可以访问该类所在的外部类的作用域中的数据，包括私有数据
+ 内部类可以对同一个包中的所有其它类，隐藏起来
+ 像定义一个回调函数，但不想编写大量代码时，使用匿名内部类。

一个简单的内部类示例：

``` java
public class TalkingClock
{
    private int interval;
    private boolean beep;
    
    public TalkingClock(int interval,boolean beep){...}
    public void start(){...}
	
    //内部类
    public class TimePrinter implements ActionListener
	{
    	public void actionPerformed(ActionEvent event){
      		System.out.prinln("time:"new Date());
       		if(beep) Toolkit.getDefaultToolkit().beep();
    	}
	}
}
```

> 构造了一个语音时钟`TalkingClock`类，有两个参数：时间间隔、开关铃声的标志。

Note:

+ 内部类既可以访问自身的数据域，也可以访问创建它的外部类对象的私有数据。

> 比如，上述示例代码中的`if(beep)`，beep是外部类的数据域。

+ 内部类对象总有一个隐式引用，它指向创建它的外部类对象。

内部类`TimerPrinter`对象，与外部类`TalkingClock`的关系，如下图：

![image-20210303145920931](C:%5CUsers%5Cjgang%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20210303145920931.png)

注：outer实际并不存在，只是为了说明隐式引用。那条线实际等价于隐式引用。

+ 内部类可以被声明为private，但之后，只能使用外部类的方法，才能构造这个内部类对象。

> 比如上述程序中，如果将内部类`TimerPrinter`声明为private，则只有外部类`TalkingClock`类的方法才能构造`TimerPrinter`对象。

上述Note第2条，补充：

+ 外部类的引用是在构造器中进行设置的。

> 内部类`TimerPrinter`没有定义构造器，所以编译器会为这个类生成一个默认的构造器，就好比上述关系图中的那条隐式的线（outer实际是不存在的），在`new TimerPrinter`后，就会默认生成。

**局部内部类**

上述示例中，`TimerPrinter`类的`start()`方法，完整代码如下：

```java
public void start(){
    ActionListener listener=new TimePrinter(this);
    Timer t=new Timer(interval,listerner);
    t.start();
}
```

可见，`TimerPrinter`类的名字，在创建`start()`方法的时候，只使用了一次。这种情况下，可以直接使用局部类（即直接将这个内部类，定义在这个方法里）：

```java
public void start(){
    public class TimePrinter implements ActionListener
	{
    	public void actionPerformed(ActionEvent event){
      		System.out.prinln("time:"new Date());
       		if(beep) Toolkit.getDefaultToolkit().beep();
    	}
    
    }
    ActionListener listener=new TimePrinter();
    Timer t=new Timer(interval,listerner);
    t.start();
}
```

> 局部类是直接定义在方法中的类。

Note:

+ 局部类不能用public或private访问说明符进行声明
+ 局部类的作用域被限定在声明这个局部类的块中。
+ 局部类有一个优势：对外部，完全隐藏。

**由外部方法访问变量**

Note 续:

+ 局部类不仅可以访问包含它们的外部类，还可以访问局部变量，但是局部变量必须为final。

直接看示例：

```java
//该示例，将TaikingClock类的2个参数放到了start()方法中
pulic void start(int interval,boolean beep)
{
    class TimerPrinter implemants ActionListener
    {
        public void actionPerformed(ActionEvent event){        System.out.println("time:"+new Date());
        if(beep) Toolkit.getDefaultToolkit().beep();
	}
 }
ActionListener listener =new TimePrinter();
Time t=new Timer(interval,listener);
t.start();
}
```

上述程序中，beep作为局部变量，被局部类`actionPerformed`所访问，这个beep变量应该声明为final，但SE8之后，可以不明确的声明。

**匿名内部类**

在局部类的基础上，如果只创建这个类的一个对象，那么，就不需要命名了。这种没命名的类，叫做匿名类。

匿名类示例：

```java
public void start(int interval,boolean beep)
{
    ActionListener listener=new ActionListener()
    {
        public void actionPerformed(ActionEvent event){
            System.out.println("time:"+new Date());
        if(beep) Toolkit.getDefaultToolkit().beep();
    };
}
```

可见，相比于局部类，匿名类直接取消了名字，取而代之的是将需要实现的方法，定义在了`{}`内。

匿名类通用语法：

```java
new superType(construction parameters){
inner class methods and data
}
```

`superType`可以是接口，但这样的话，就需要在`{}`中实现这个接口。`superType`也可以是一个类，但这样的话，就需要在`{}`中扩展它。

Note:

+ 匿名类没有类名，所以匿名类就没有构造器。
+ 但是匿名类可以有构造器参数，这些参数会传递给超类(superclass)构造器。
+ 在内部类实现接口的时候，不能有任何构造器参数，即只是`()`。

示例：

```java
new InterfaceType()
{
    methods and data
}
```

+ 其它：java程序员习惯做法是用匿名类实现事件监听和其它回调，但现在，最好使用lambda表达式。

示例：本节开始的`start()`方法中的匿名类，使用lambda表达式实现，则很简洁：

```java
pulic void start(int interval,boolean beep)
{
    Timer t=new Timer(interval,event->
                      {
                          System.out.prinln("time:"+new Date());
                          if(beep) Toolkit.getDefaultToolkit().beep();
                      });
	t.start();
}
```

**静态内部类**

如果使用内部类，只是为了将类隐藏在另一个类的内部，而不需要内部类引用外围类对象，则可以将这个内部类声明为static，以便于取消产生的引用。

Note:

+ 只有内部类可以声明为static
+ 静态内部类的对象，除了没有对生成它的外围类对象的引用特权外，其它方面与内部类完全一样。
+ 静态内部类可以有静态域和方法
+ 声明在接口中额内部类，会自动成为static和public类。

### 代理

暂时跳过。



