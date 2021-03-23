第八章 泛型程序设计

本节主要内容：

+ 泛型设计的好处
+ 类型变量的限定
+ 泛型代码、类型擦除
+ 通配符类型

### 使用泛型程序设计的理由

+ 使用泛型机制编写代码，要比使用Object变量然后进行强制类型转换的代码，更具安全性和可读性。
+ 泛型对于集类，尤其有用。比如集合类ArrayList.
+ 使用泛型编写的代码，可以被不同对象重用。

实例：

```java
ArrayList<String> files=new ArrayList<String>();
```

> java se 7之后，可以可以省略泛型类型。
>
> 即: `ArrayList<String> files=new ArrayList<>();`

上述示例中，构造器中的`String`就是泛型类型。

### 定义一个简单的泛型类

一个泛型类就是具有一个或多个类型变量的类。以Pair为例：

```java
public class Pair<T>
{
    private T first;
    private T second;
    
    public Pair(){
        first=null;
        second=null;
    }
    public Pair(T first,T second){
        this.first=first;
        this.second=second;
    }
    
    public T getFirst(){
        return first;
    }
    public T getSecond(){
        return second;
    } 
    
    public setFirst(T newValue){
        first=newValue;
    }
    public setSecond(T newvalue){
        second=newValue;
    }
}
```

Note:

+ T为类型变量，使用<>括起来，在类名的后面，

+ 泛型类可以有多个变量，比如``public class Pair<T,U>{...}`

+ 用具体的类型替代泛型的类型变量，可以实例化泛型类型。

  ```java
  Pair<String>();
  
  //or
  Pair<String>(String,String);
  
  //method
  String getFirst();
  void setFirst(String);
  ```

### 定义一个简单的泛型方法

除了定义泛型类，还可以定义带有泛型参数的方法，例：

```java
class ArrayAlg
{
    public static <T> T getMiddle(T... a){
        return a[a.length/2];
    }
}
```

> 带有泛型参数的方法`getMiddle()`是在普通类中定义的，但这是一个泛型方法。

Note:

+ 泛型方法可以定义在普通的类中，
+ 也可以定义在泛型类中，
+ 调用一个泛型方法时，需要在`<>`中放入具体类型，
+ 泛型类型变量`<T>`，要放在修饰符(比如`public static` )的后面，返回类型(比如 T)的前面。

### 限定类型变量T

先看一个例子：

```java
class ArrayAlg
{
    public static <T> T min(T[] a)
    {
        if(a==null || a.length==0)
            return null;
        T smallest=a[0];
        for(int i =1,i < a.length;i++){
            if (smallest.compareTo(a[i])>0)
                smallest=a[i];
       	return smallest;
        }
    }
}
```

有一个问题：`smallest.compareTo(a[i])`中的`samllest`类型为T，怎么确保T所属的类，拥有`compareTo()`方法呢？

解决：对类型变量T设置限定（bound）

```java
...
public static <T extends Comparable> T min(T[] a){...}
```

Note: 

+ 设置限定的一般格式：`<T extends BoundingType>`，其中T是BoundingType的子类型。BoundingType可以是类，也可以是接口。
+ 关键字`extends`，是因为更接近子类。
+ 可以有多个限定，如：`T extends Comparable & Serializable`

## 泛型代码和虚拟机

对于虚拟机来说，不存在泛型类型对象，换句话说，所有对象都属于普通类。

**类型擦除**

但凡定义泛型类型，都自动提供一个相应的原始类型(raw type)，原始类型的名字就是删去类型参数后的泛型类型名。

会擦除类型变量，替换为选定类型。

> 如果没有限定类型，则使用Object代替。

上文示例Pair的类型擦除：

```java
public class Pair
{
    private Object first;
    private Object second;
    
    public Pair(){
        first=null;
        second=null;
    }
    public Pair(Object first,Object second){
        this.first=first;
        this.second=second;
    }
    
    public Object getFirst(){
        return first;
    }
    public Object getSecond(){
        return second;
    } 
    
    public setFirst(Object newValue){
        first=newValue;
    }
    public setSecond(Object newvalue){
        second=newValue;
    }
}
```



**翻译泛型表达式**

Note: 当程序调用泛型方法时，如果擦除返回类型，则编译器会插入强制类型转换，比如：

```java
Pair<Employee> buddies=...;
Employee buddy=buddies.getFirst();
```

编译器对于第二条方法调用的语句，翻译为以下两条虚拟机指令：

+ 调用原始方法`Pair.getFirst()`

+ 将返回的Object类型，强制转换为Employee类型。

**翻译泛型方法**

类型擦除也会出现在泛型方法中。看一个示例：

```java
//泛型方法：
public static <T extends Comparable> T min(T[] a)
```

擦除类型之后：

```java
public static Comparable min(Comparable[] a) 
```

类型参数T被擦除，限定类型Comparable被留下。

当类型擦除与多态发生冲突时，需要编译器在生成一个桥方法(bridge method)。

P319.

### 约束与局限性

使用java泛型需要考虑一些限制，而这些限制大都由类型擦除而引起的。

**1 不能使用类型参数代替基本类型**

示例：

```java
Pair<double> //error
Pair<Double> //ok    
```

原因在于，类型擦除之后，Pair类含有Object域，而Object域不能存放double值。

**2 所有的类型查询，只产生原始类型**

虚拟机中的对象，只有一个特定的非泛型类型。所以，所有的类型查询只产生原始类型。

+ 使用instanceof查询，对象类型是否属于某个泛型类型，会发生Error。

示例：

```java
//查询某个对象，是否属于某个泛型类型时，
if(a instanceof Pair<String>)//error

//使用强制类型转换时，
Pair<String>  P = (Pair<String>) a;//warning
```

+ 同样的，使用getClass方法，也是返回原始类型。

示例：

```java
Pair<String> stringPair=...;
Pair<String> employeePair=...;

if(stringPair.getClass()==employeePair.getClass())  //they are equal
```

上述调用getClass()方法，返回的结果相同，都为Pair.class。

**3 不能实例化参数化类型的数组**

error示例：

```java
Pair<String>[] a=new Pair<String>[10];//error
```

 Note:

+ 声明类型为`Pair<String>[]`的变量，是合法的，
+ 但申明之后，无法使用`new Pair<String>[10]`来初始化这个变量。

> 不能初始化这个变量，那声明这个变量，纯属浪费空间了。

+ 可以声明通配类型的数组，然后使用类型转换，这样就可以用了。

  ```java
  Pair<String>[] a=new Pair<?>[10];//ok
  ```

+ 如果一定需要参数化示类型对象，只有一种安全办法：`ArrayList:ArrayList<Pair<String>>`

**4  也不能实例化泛型数组**

暂时跳过。

**5 泛型类中的静态上下文中，类型变量无效**

示例：

```java
public class Singleton<T>
{
    private static T singleInstance;//error
    
    //constructor
    public static T getSingleInstance()//error
    {
        if(singleInstance==null)
            return singleInstance;
    }
}
```

**6 不能抛出，不能铺货，泛型类的对象**

而且，泛型类扩展Throwable都是不合法的。

### 泛型类的继承规则

上文中的`Pair<Manager>`和`Pair<Employee>`不具有子类关系。因为Pair类之间，不具有继承关系（虽然Employee类和Manager类之间具有继承关系）。

### 通配符类型

通配符类型示例：

```java
Pair<? extends Employee>
```

上述示例表示，类型参数是Employee的子类，比如`Pair<Manager>`。

换句话说，类型`Pair<Manager>`是`Pair<? extends Employee>`的子类型。关系示例图如下：

<img src="https://gitee.com/changyv/md-pic/raw/master/20210323122404.png" alt="image-20210323122340606" style="zoom:65%;" />

**通配符的超类型限定**

相比于类型变量限定，通配符可以指定一个超类型限定。示例：

```java
? super Manager
```

表示该通配符限制为Manager的所有超类型。

完整示例：

```java
public static void minmaxBonus(Manager[] a,Pair<? super Manager> result)
{
    if(a.length==0)	return;
   	Manager min=a[0];
    Manager max=a[0];
    for(int i=1;i<a.length;i++){
        if(min.getBonus()>a[i].getBonus)	min=a[i];
        if(max.getBonus()<a[i].getBonus)	max=a[i];
    }
    result.setFirst(min);
    result.setSecond(max);
}
```

超类型限定的另一种应用：泛型类型的接口。



## 其它

第九节反射与泛型，暂时跳过。













