## 【Java核心卷1】第四章 对象与类

主要内容：

+ 面向对象程序设计

+ 创建标准java类库中的类对象

+ 编写自己的类

> 具体为：
>
> ​	预定义类、用户自定义类
>
> ​	静态域，静态方法、方法参数、对象构建
>
> ​	包、类路径、文档注释、类设计技巧

## 面向对象程序设计

面向对象的程序是由对象组成的， 每个对象包含对用户公开的特定功能部分和隐藏的实现部分。程序中的很多对象来自标准库，还有一些是自定义的。

从根本上说， 只要对象能够满足要求，就不必关心其功能的具体实现过程。

### 类

**类**（ class) 是构造对象的模板或蓝图。类构造（construct) 对象的过程称为创建类的实例(instance).

**封装**（ encapsulation ,或叫做数据隐藏），是与对象有关的概念。从形式上看，封装是将数据和行为组合在一个包中， 并对对象的使用者隐藏了数据的实现方式。

> 对象中的数据叫做实例域(instance field)
>
> 操纵数据的过程叫做方法(method)

对于每个特定的类实例（对象）都有一组特定的实例域值。这些值的集合就是这个对象的当前状态（ state )。无论何时，只要向对象发送一个消息，它的状态就有可能发生改变。

实现封装之后，程序只能通过对象方法来与对象数据交互，这是提高重用性和可靠性的关键。

在扩展一个已有的类时， 这个扩展后的新类具有所扩展的类的全部属性和方法。在新类中，只需提供适用于这个新类的新方法和数据域就可以了。通过扩展一个类来建立另外一个类的过程称为继承(inheritance)。

####  对象

对象具有三个特征：

+ 对象的行为：可以对对象施加的操作
+ 对象的状态：使用方法时，对象如何相应
+ 对象辨识：如何辨识具有相同行为和状态的不同对象

在OOP中，程序设计首先从设计类开始，然后往类中添加方法。一般来说，对象对应着名词，方法对应着动词。例子：设计订单处理系统，商品(Item)、订单(Order)、账户(Account)这些名词都表示一个对象，而添加、取消、支付这些动词则对应着类Order中的方法。

**类之间的关系**

在类之间， 最常见的关系有

+ 依赖（“ use a ”） 

> 如果一个类的方法操纵另一个类的对象，我们就说一个类依赖于另一个类。比如订单类需要访问账户这个类，来查看账户状态，所以构成依赖关系。

应该尽可能地将相互依赖的类减至最少。就是让类之间的耦合度最小。

+ 聚合（“ has a ”）

>  聚合关系意味着类 **A** 的对象包含类 **B** 的对象。
>
>  比如一个订单类包含一些商品类的对象，这就构成聚合关系。

+ 继承（“ is a ”）

>  如果类 **A** 扩展类 **B**, 类 **A** 不但包含从类 **B** 继承的方法，还会有额外的功能。

类关系的 UML 符号表示

![image-20210228155240519](https://gitee.com/changyv/md-pic/raw/master/20210228230106.png)

在java中，没有类就无法做任何事情。但并不是所有的类都具有面向对象的特性。比如Math类，直接使用Math类中的方法，如`Math.random()`，只需要知道参数，而不必了解具体实现（因为是封装好了）,Math类也不需要隐藏数据，因为没有数据，所以使用时不需要生成对象和初始化实例域。

### 使用预定义的类

#### 对象与对象变量

要想使用对象，就必须先构造对象，然后，对构造出的对象来使用方法。在java中，使用构造器(constructor)来构造新实例。构造器的名字与类名相同，在构造器前使用操作符new，举个例子：

```java
//Data类的构造器是Data
//new Data()就可以构造一个Data对象


String s=new Data().toString();

//new Data() 表示构造一个Data对象，
//该对象被初始化为当前的日期和时间；
//toString()是Data类的一个方法，返回日期的字符串描述。
```

上面例子中构造出的对象使用了一次，如果要让构造出的对象多次使用，需要将构造出的对象放在一个变量中：

```java
Data birthday=new Data()
```

> `birthday`是一个==对象变量==，它引用一个对象。

内存变化如下图所示：

<img src="https://gitee.com/changyv/md-pic/raw/master/20210228230113.png" alt="image-20210228161831707" style="zoom:80%;" />

```java
Data data2;//定义一个对象变量，它可以引用Data类型的对象,但它不是一个对象，也没有引用对象，所以不能使用对象方法。
s=data2.toString(); //error
```

但可以初始化这个data2对象变量：

```java
data2=birthday;
//现在，这两个变量引用同一个对象
```

内存变化如图：

![image-20210228162342289](https://gitee.com/changyv/md-pic/raw/master/20210228230119.png)

Note:

+ 一个对象变量并没有实际包含一个对象，而仅仅是引用一个对象
+ 在java中，任何对象变量的值都是对一个对象的引用，`new`操作符的返回值也是一个引用
+ java中，可以显式的将对象变量设置为null，以表明这个对象变量目前没有引用任何对象
+ 设置为null的对象变量或没有任何引用的对象变量，无法使用对象方法
+ 所有的java对象都存储在堆中，当一个对象包含另一个对象变量时，这个对象就包含着指向另一个堆对象的指针。

#### localData类

`Data`类用来表示时间点，`LocalData` 类以日历形式来表示时间。创建对象过程：

```java
//方式一：构造一个新对象，时间为当前年月日。
LocalData newData = LocalData.now();
//构造一个新对象，时间为当前年月日。
//使用静态工厂方法，代表调用构造器。

//方式二：提供一个年月日参数
LocalData newData = LocalData.of(1999,12,12);

//LocalData的几个方法
int year=newData.getYear();
int month=newData.getMonthValue();
int day=newData.getDayOfMonth();

//打印日历：
LocalData data = LocalData.now();
int month = date.getMonthValue ();
int today = date.getDayOfMonth();

//将 date 设置为这个月的第一天， 并得到这一天为星期几。
date = date.minusDays (today - 1); 
DayOfWeek weekday = date.getDayOfWeek();
int value = weekday .getValue(); // 1表示Monday , .. . 7表示Sunday.
//打印日历
	System.out.println("Mon Tue Wed Thu Fri Sat Sun")；
	for (int i = 1; i < value ; i++)
		System,out .print(" ")；
	while (date.getMonthValue() == month) {
		System.out.printf("%3d" , date.getDayOfMonth());
		if (date.getDayOfMonth() == today)
			System.out.print("*")；
		else
			System.out.print(" ");
		date = date.plusDays(1);
		if (date.getDayOfWeek().getValue() = 1)
            System.out.println(); 
}

```

只访问对象而不修改对象的方法称为==访问器方法==，如`LocalData.getYear()`.方法。

### 使用自定义的类

主力类（workhorse  class）：用户自定义的类，即主力类，这些类没有 main 方法， 却有自己的实例域和实例方法。 

要想创建一个完整的程序， 应该将若干类组合在一起，而且 其中只有一个类有 main 方法。

一个自定义的类：

```java
class Employee
{
    //instance fields
    private String name;
    private double salary;
    private LocalData hireDay;
    
    //constructor
    public Employee(String n,double s,int year,int mouth,int day){
        name=n;
        salary=s;
        hereDay=LocalData.of(year,month,day);
    }
    
    // methods
    public String getName(){
        return name;
    }
    public double getSalary(){
        return salary;
    }
    public LocalData getHireDay(){
        return hireDay;
    }
    public void raiseSalary(double percent){
        double raise=salary*percent/100;
        salary+=raise;
    }
}
```

Note:

+ 关键字private确保只有Employee类自己的方法才能访问这些实例域，即name, salary, hireDay只能由Employee类自己的方法才能访问并修改
+ 类中可以包含某个类的实例域。hireDay就是LocalData类的实例。
+ 构造器与类同名，`public Employee(...)`就是Employee类的构造器，起到初始化实例域到指定状态的作用
+ 每个类可以有多个构造器，且可以有0个或多个参数
+ 构造器没有返回值
+ new Employee(args)之后，将调用构造器。即构造器总时伴随new操作符号，因为所有的java对象都是在堆中构造的。
+ 类方法中的变量应该避免域该类的实例域同名，比如不要在`public Employee(...)`中去定义一个name变量。

**隐式参数与显式参数**

在方法中：

```java
public void raiseSalary(double percent){
        double raise=salary*percent/100;
        salary+=raise;
```

使用`e.raiseSalary(5)`，该方法将salary设置为新值，其中percent是显式参数，而salary是隐式参数。隐式参数没有出现在方法声明中。关键字this表示隐式参数，上述写法等价于：

```java
public void raiseSalary(double percent){
        double raise=salary*percent/100;
        this.salary+=raise;
```

如果需要返回一个可变对象的引用，应该对它进行克隆，对象clone是指存放在另一个位置的对象副本。例子：

```java
Employee harry=...;
Data d=harry.getHireDay();
double days=10*365+24*60+60*1000;
d.setTime(d.getTime()-(long)days);//给harry增加工龄
//error
```

d和harry这两个变量指针，情形如下图：

![image-20210228171709609](https://gitee.com/changyv/md-pic/raw/master/20210228230127.png)

上述程序出错的原因是：d和harray.hireDay引用了同一个对象，于是同个d就可以修改这个Employee的私有数据域了。

解决：如果需要返回一个可变对象的引用，要先对它进行克隆。添加对象clone之后的代码如下：

```java
class Employee
{
    ...
    public Data getHireDay(){
        return (Data) hireDay().clone;
    }
}
```

**基于类的访问权限**

Note: 

+ 类中的方法，可以访问所调用对象的私有数据
+ 一个方法可以访问所属类的任何一个对象的私有域。

关于第2条，例子：

```java
class Employee
{
    ...
    public boolean equals(Employee other){
        return name.equals(other.name);
    }
}
```

这个equals方法，典型的调用方式为：`if(harray.equals(boss))`，该方法访问了harry的私有域，但是，还访问了boss的私有域。但这是合法的。

**私有方法**

在java中，实现一个私有的方法，只需要将public改为private即可。

**final实例域**

可以将实例域定义为final，但在构建对象时必须初始化这样的实例域。换句话说，就是在每一次构造器执行之后，这个域的值被设置，而且在之后操作中，这个值不能再做修改。例子：

```java
class Employee
{
    private final String name;
    ...
}
```

Note:

+ fianl修饰符大都应用于基本(private)类型域，或不可变(immutable)类的域。

  > 不可变的域是指类中的每个方法都不会改变它的对象，这种类就是不可变的类。

Employee类的实际应用：Test.java文件

```java
public class Test
{
    public static void main(String[] args)
    {
        //staff array,with 3 employee objects
        Employee[] staff=new Employee[3];
        
        staff[0]=new Employee("Carl",7500,1990,12,15);
        staff[0]=new Employee("Harry",7500,1990,12,15);
        staff[0]=new Employee("Tony",7500,1990,12,15);
        
        //call method
        for(Employee e:staff){
            System.out.println("name:"+e.getName()+"salary:"+e.getSalary());
        }
    }
}
```

Note:

+ 一个源文件中，只能有一个公有类，但可以有多个非共有类

+ 源文件的文件名必须与文件中public类的名字匹配

+ C++中常在类外部定义方法，如void Employee::raiseSalary()，但在java中，必须在类内部定义类的方法。


### 静态域域静态方法

**静态域**

如果给每个employee赋予唯一的标识符，每次构建新的对象的时候，该标识符加1，则可以使用静态域来实现：

```java
class Employee
{
    private static int nextId=1;
    private int id;
    ...
        
    //method
    public void setId(){
        id=nexrId;
        nextId++;
    }
}
```

Note: 

+ 如果将域定义为`static`，则类中只有一个这样的域，但每个对象都有该实例域的一份拷贝。

+ 如果有1000个Employee对象，则有1000个实例域id，但是，只有1个静态域nextId,
+ 即使没有一个Employee对象，静态域nextId也存在，它属于类，而不属于任何独立的对象。

标识码实现：

```java
harry.setId();
```

> 等价于：
>
> harrry.id=Employee,nextId();
>
> Employee.nextId++;

**静态常量**

静态常量用的比较多，比如在Math中定义的PI=3.14...

```java
public class Math
{
    public static final double PI=3.1415;
    ...
      
}
```

在程序中，可以使用Math.PI获得该常量值。

**静态方法**

静态方法是一种不向对象实施操作的方法，即静态方法不使用任何隐式参数，不适用任何类对象，比如Math类中的pow方法：`Math.pow(x,a)`计算$x^a$ 

Note: 

+ 静态方法是没有this参数的方法，因为它没有任何隐式参数

+ 类的静态方法不能访问自身类的实例域，但可以访问自身类中的静态域。

+ 不需要使用对象，直接使用类调用静态方法，如`Math.pow()`

  第2条的示例：

```java
public static int getNextId(){
  	return nextId;
      //return static field
}
...
 
//调用
int n=Employee.getNextId();
```

如果上述方法中`public static int getNextId()`去掉`static`，则需要使用对象来调用，而不是通过类来调用。

以下2中情况下使用静态方法：

+ 一个方法不需要访问对象，其所需的参数都是显式参数
+ 一个方法只需要访问类的静态域，如`Employee.getNextId()`

### 方法参数

在程序设计中，有两种参数传递方法：值传递(call by value)、引用传递(call by reference)。

> c++中，引用传递使用符号“&”，表示方法接收的变量地址，一个方法可以修改传递引用所对应的变量值。
>
> 但是，值传递的变量值，方法无法修改。

**java采用值调用，即方法得到的式参数值的一个拷贝，所以方法不能修改传递给它的任何参数变量的内容。**

比如，以下方法就没什么效果：

```java
//交换两个对象
class Employee
{
    ...
    public static void swap(Employee x,employee y)
    {
        Employee temp=x;
       	x=y;
        y=temp;
    }
    ...
}
...
//调用
Employee a=new Employee("Alice",...);
Employee b=new Employee("Bob",...);
Emloyee.swap(x,y) //not work
```

x和y并没有发生变化，所以java采用的是值引用。

## 对象构造

一个类可以有多个构造器，即名字相同，参数不用，这种特征叫做**重载(overloading)**。编译器通过比对参数，选择合适的构造器，这个过程叫做重载解析(overloading resolution).

Note:

+ java允许重载任何方法，不只是构造器
+ 完整的指出方法名以及参数类型，这叫做方法签名(signature)
+ 但返回类型不属于方法签名。换句话说，就是不能有两个名字相同、参数类型也相同，但返回类型值不同的方法。

**默认域的初始化**

+ 如果没有在构造器中显示的给域赋初值，则会被自动赋初值：数值为0，布尔型为false，对象引用为null。

+ 如果编写一个类的时候，没有编写无参数的构建器，则构造对象时，如果没有提供参数，就会报错

+ 如果类中提供了至少一个构造器，但没有提供无参数构造器，则构造时没有提供参数，则被视为不合法。
+ 仅当类没有提供任何构造器的时候，系统才会提供一个默认构造器。

**显式域的初始化**

在执行构造器之前，先执行赋值操作。这种方法，当一个类的所有构造器都希望把相同的值赋予某个特定的实例域时，这方法很有用，例子：

```java
class Employee
{
    private String name="";
    ...
}
```

上述是将一个值赋值给特定的实例域，但也可以通过方法进行初始化实例域，比如：

```java
class Employee
{
    private static int nextId;
    privte int id=assignId();
    ...
      
    private static int assignId(){
        int id=nextId;
        nextId++;
        return id;
    }
    ...
}
```

> 在C++中，不能直接初始化类的实例域，所有的域必须在构造器中设置。但可以通过以下方式初始化：
>
> ```c++
> Employee::Employee(String n,double s,...)
> :name(n),
>  salary(s),...
> {
>       ...
> }
> ```

**参数名**

可以在每个参数前加上前缀“a”，以达到区别实例域的目的。比如：

```java
public Employee(String aName,double aSalary)
{
    name=aNmae;
    salary=aSalary;
    ...
}
```

**调用另一个构造器**

如果构造器的第一行语句是`this(...)`，则这个构造器嗲用另一个构造器，比如：

```java
public Employee(double s)
{
    //call Employee(String,double)
    this("Employee #"+ nextId,s);
    nextId++;
}
```

> 在c++中，一个构造器不能调用另一个构造器。

**对象析构域finalize方法**

java有自动垃圾回收器，所以java不支持析构器。

但当某些对象使用了内存以外的其它资源，则回收就极为必要了。

可以为任何一个类添加finalize方法，回收资源。

### 包

java允许使用包(package)将类组织起来。

使用包的只要原因是确保类名的唯一性。

从编译器角度来说，嵌套的包之间，没有任何关系，每一个包都拥有唯一的类集合。

**导入包**

一个类可以使用所属包中所有的类，以及其它包中的公有类(public class)，使用以下两种方式访问另一个包中的所有类：

```java
java.time.LoalDate day=java.time.LocalDate.now();
```

另一种方法为：

```java
import java.util.*;
LocalDate day=LocalDate.now();
```

**静态导入**

import除了导入类，还可以导入静态方法和静态域的功能。比如：

```java
import static java.lang.system.*;
out.println("goodbye world");
exit(0);
```

**将类放入包中**

要想将一个类放入包中，就必须将包的名字放在源文件的开头，包中类的代码之前。比如：

```java
package com.corejava;

public class Employee
{
    ...
}
```

包中的文件，放在与包名匹配的子目录。比如`com.corejava`包中所有源文件的文件路径必须为`com/corejava/`。如果包与目录不匹配，虚拟机就找不到类。

**类注释**

类注释必须放在import语句之后，类定义之前。

方法注释必须放在所描述的方法之前。

**类路径**

类的路径必须与包名字匹配。为了使类能够比多个程序共享，需要做到：

+ 把类放在一个目录中
+ 将JAR文件放在一个目录中
+ 设置类路径。类路径是所有包含文件的路径的集合。

**类设计技巧**

1. 保证数据私有

2. 要对数据初始化

3. 不要在类中使用过多的基本类型。

   > Customer类中的实例域：
   >
   > ​	private String street;
   >
   > ​	private String city;
   >
   > ​	private String state;
   >
   > 可以使用一个新类Address来代替，该类包含以上3个实例域。 

4. 不是所有的域都需要独立的域访问器或修改器

   > 比如Employee中，id初始化之后，就不再需要修改器了。

5. 分解职责过多的类
6. 优先使用不可变的类


















