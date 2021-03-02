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







