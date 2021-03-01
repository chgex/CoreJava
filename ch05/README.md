【Java核心卷1】第五章

主要内容：

+ 超类、子类

+ 继承、多态

利用继承，可以在已有的类上构建一个新类。继承已存在的类就是复用这些类的方法和域，而且在此基础上，添加一些新的方法和域，以满足新的需求。

主要包括：

+ 类、超类、子类
+ Object类
+ 泛型数组列表
+ 自动装箱
+ 参数可变的方法
+ 枚举类

### 超类、子类

Manager类与Employee类之间有明显的“is a”关系：每个经理都是一名雇员。“is a”是继承的明显特征。

**定义子类**

继承Employee类来定义子类Manager，使用关键字`extends`来表示。比如：

```java
public class Manager extends Exployee
{
    private double bonus;
    //manager有奖金，而employee没有
    ...
    public void setBonus(double bonus){
        this.bonus=bonus;
    }
}
```

Note: 

+ java中使用关键字`extends`来定义继承类，C++中使用`:`
+ 在java中，所有的继承都是公有继承，而C++中有私有继承和保护继承
+ `setBonus()`方法不是在Employee类中定义的，所以Employee类对象不能访问该方法
+ 但属于Manager类的方法，Employee对象是一定可以使用的

**在设计类的时候，应该将通用的方法放在超类中，而将具有特殊用途的方法放在子类中。**

关键字`extends`表示正在构造的新类派生于已存在的类，已存在的类称为超类(superclass)、基类(base class)、父类(parent class)。

新构造的类称为子类(subclass)、派生类(derived class)、孩子类(child class)。

> 比如Manager类继承自Employee类，Employee类就是超类，Manager类就是子类。
>
> 子类比超类拥有更丰富的功能。在Manager类中封装了比Employee类更多的数据和功能。
>
> 因为一个Employee不一定是Manager，但一个Manager一定是Employee。

**覆盖方法**

Manager类中的`getSalary()`方法应该返回`salary+bonus`，所以需要提供一个新的方法来覆盖(override)超类中的这个方法。

来看一些覆盖方法的写法：

写法一：

```java
public class Manager extends Employee
{
    ...
    public double getSalary()
    {
        return salary+bonus;//not work
    }
}
```

这个方法不能运行，是因为Manager类的`getSalary()`方法不能直接访问超类的私有域。换句话说就是：虽然每个Manager对象都拥有一个叫做salary的域，但在Manager类中的`getSalary()`方法并不能直接访问salary域。

所以，如果子类的方法一定要访问超类的私有域，比就必须借助于公有接口。比如getSalary()就是这样的公有接口，它继承自超类。

于是，方法变为：

```java
public double getSalary()
{
    double baseSalary=getSalary();
    //会被识别为子类中的重载方法，于是陷入自己调用自己
    return baseSalary+bonus;
}
```

但还是会出错，因为会被识别为调用子类的重载之后的`getSalary()`方法。

解决：加上`super`关键字。

正确的方法应该为：

```java
public double getSalary()
{
    double baseSalary=super.getSalary();
    return baseSalary+bonus;
}
```

> 在C++中，调用超类的方法，格式为`Employee::getSalary()`

**子类的构造器**

子类构造器，使用super()来调用超类中的构造器，比如：

```java
public Manager(String name,double salary,int year,int month,int day)
{
    super(name,salary,year,month,day);
    bonus=0;
}
```

上述语句中的`super(n,s,year,month,day)` 是调用超类的构造器。

> 对比
>
> 关键字this的两个用途：
>
> 1. 引用隐式参数
> 2. 调用该类其它的构造器
>
> 关键字super的两个用途：
>
> 1. 调用超类方法
> 2. 调用超类的构造器

Note:

+ 如果子类的构造器没有显式的调用超类的构造器，则将自动的调用超类默认的构造器，就是没有参数的那个。
+ 如果超类的构造器都带有参数，但子类的构造器没有显式的调用超类的构造器，则编译器会报错。

**继承的层次**

由一个公共超类派生出的所有类的集合被称为继承层次(inheritance hierarchy)，好比数据结构中的树。而从某个特定的类到其祖先类的路径被称为该类的继承链(inheritance chain)。如下图：

![image-20210301172431863](https://gitee.com/changyv/md-pic/raw/master/20210301172444.png)

Note:

+ java不支持多继承

+ java中以接口方式，实现多继承相似的功能。

**多态**

先看一段代码：

```java
//define a Manager object
Manager boss=new Manager("Carl",8000,2021,02,01);
boss.setBonus(5000);

//define 3 Employee array
Employee[] staff=new Employee[3];

//init array
staff[0]=boss;  //Manager
staff[1]=new Employee("Harry",5000,2020,12,12);
staff[2]=new Employee("Tony",5500,2020,11,11);

//output every staff's salary
for(Employee e:staff){
    System.out.println(e.getName()+" "+ e.getSalary());
}
```

以上程序输出正常，输出了Manager对象的salary+bonus，输出了Employee对象的salary。但程序调用的方法都是`e.getSalary()` ，而且e为Employee对象类型，但它可以引用Manager类型，这种现象就叫做**多态**。

即一个对象变量，可以指示多种实际类型的现象被称为多态(polymorphism)。在运行时能够自动地选择调用那个方法的现象称为**动态绑定(dynamic binding)**。

> 在java中，不需要将方法声明为虚拟方法，因为动态绑定是默认处理方式。如果不希望让一个方法具有虚拟特征，可以将它标记为final。

是否达到"is a"，是判断是否设计继承关系的简单规则。比如每个Manager都是Employee，所以将Manager设计为Employee类是明显的。

在java中，对象变量是多态的：一个Employee变量既可以引用一个Employee对象，也可以引用一个Employee类的任何一个子类的对象，比如上文示例中的Manager对象。

java中，一个子类数组的引用可以转换为超类数组的引用，而不用采用强制类型转换。比如：

```java
Manager[] managers=new Manager[10];

//transfer to array of Employee
Employee[] staff=managers;
//ok

//but 
staff[0]=new Employee("harry",...);
// ok,but a employee into manager array,
//then, manager[0].setBonus(1000); will occur error.
```

上述程序中，staff[]与managers[]指向同一个对象。最后一行语句，将Employee放到了Manager类型的数组中，但之后调用`manager[0].setBonus(1000);`会发生`ArrayStoreException`异常。所以，要尽量避免这种情况。

**方法调用过程**

调用`x.f(args)`的过程如下：

1. 编译器查看对象的声明类型和方法名。

至此，编译器获得所有可能被调用的候选方法。

> 可能有多个方法叫做f（即有多个同名的方法名）但是参数类型、数量不一样，这些都是候选方法。

2. 编译器查看调用方法的参数类型

> 如果在候选方法中找到参数类型完全匹配的方法，就选择这个方法，这个过程叫做重载解析(overloading resolution)。

至此，编译器获得了需要调用的方法名和参数类型。

Note:

+ 方法的名字和参数列表叫做方法的签名
+ 返回类型不是签名的一部分，所以覆盖方法时，要保证返回类型的兼容性：返回类型可以是原返回类型的子类型

第2条，示例：

```java
public Employee getBuddy(){
    ...
}
//buddy:朋友，搭档

//overload this method
public Manager getBuddy(){ // ok
    ...
}
```

关于静态绑定：

> 如果是private方法，static方法，final方法或者构造器，编译器可以准确的知到应该调用那个方法，这种调用方式叫做静态绑定。

关于动态绑定：

> 在运行时能够自动地选择调用那个方法的现象称为动态绑定(dynamic binding)。
>
> 换句话说，一个方法不是private方法，static方法或final方法时，将采用动态绑定。编译器为类生成方法表。

如果采用动态绑定，每次调用方法时都要进行搜索，时间开销大，所以，虚拟机预先为每个类创建了一个方法表(method table)，其中列出了所有方法的签名和实际调用的方法。于是，在真正调用的时候，虚拟机仅查找这个表就可以了。

> 若调用`super.f(param)，`编译器将搜索超类的方法表。

Employee类的方法表如下：

![image-20210301190445041](https://gitee.com/changyv/md-pic/raw/master/20210301190446.png)

Note: 

+ 在覆盖一个方法时，子类方法不能低于超类方法的可见性。特别是，如果超类方法是public，则子类方法一定要声明为public。

**final类和方法**

不允许扩展的类被称为final类。示例：

```java
public final class Executive extends Manager
{
    ...
}
```

Note:

+ 类中的特定方法也可以申明为final
+ 申明为final的方法，子类就不能覆盖这个方法了。
+ 一个类被定义为final，则类中所有方法自动变成final，但是域不变为final。

> 类中的域可以被申明为final，但构造对象之后，不允许改变这个域的值了。
>
> 比如：
>
> private final String corporate="xx";

+ 将方法或类声明为final的主要目的：确保它们不会在子类中改变语义。

String类也是final类，意味着不允许任何人定义String的子类。

**强制类型转换**

将一个类型强制转换为另一个类型的过程叫做类型转换。也可以将某个类的对象引用转换为另一个类的对象引用，比如：

```java
Manager boss=(Manager) staff[0];
```

Note:

+ 将一个子类的引用赋给一个超类变量，是允许的

  > 相当于将一个Manager直接降为Employee是允许的。

+ 但是将一个超类的引用赋给子类变量，必须进行类型转换。

> 相当于将一个Employee升为Manager必须经过类型转换。

+ 在类类型转换之前，使用`instanceof()`检查一下是否可以成成功转换，比如：

```java
if(staff[1] instanceof Manager)
{
    boss=(Manager) staff[1];
}
```

+ 通过类型转换调整对象不是一种好的做法，一般情况下，应该少用类型转换和`instanceof`运算符。

**抽象类**

 在继承层次中上移，位于上层的类更育有通用性，甚至更加抽象。比如：

![image-20210301195159258](https://gitee.com/changyv/md-pic/raw/master/20210301195200.png)

Employee类和Student类都是是Person的子类。它们都有name这个属性，因此，可以将`getName` 方法放置在位于较高层次的通用超类Person类中，示例：

```java
public abstract class Person
{
    private String name;
    
    public Person(String name){
        this.name=name;
    }
    
    public abstract String getDescription();
    
    public String getName(){
        return name;
    }
}
```

其中，`public abstract String getDescription();`是抽象方法，充当占位的角色，它的具体实现是在子类中。

Note: 

1. 一个类即使不含有抽象方法，也可以声明为抽象类。

2. 抽象类不能被实例化，就是说，`Person p=new Person("Harry")`这是错误的，不能创建抽象类的对象。

3. 但是可以创建抽象类的对象变量，但是它只能引用非抽象子类的对象，比如

```java
Person p=new Student("Harry");
```

> C++中，没有表示抽象类的专有修饰符；
>
> C++中，只要有一个纯虚函数，这个类就是抽象类。
>
> C++中，纯虚函数，形式为：`virtual string getDescription()=0;`

抽象类Person的扩展：Student类：

```java
public class Student extends Person
{
    //域
    private String major;
    
    public Student(String name,String major){
        super(name);
        this.major=major;
    }
    
    public String getDescription(){
        return "a student majoring in"+major;
    }
}
```

在Person类中，`getDescription()`方法是抽象的，在Student类中定义了`getDescription()`方法，于是Student类不再是抽象的。

关于上述Note的第3条：可以创建抽象类的对象变量，但是它只能引用非抽象子类的对象。代码：

```java
//抽象类的对象变量
Person[] people=new Person[2];

people[0]=new Employee(..);
people[1]=new Student(..);

//输出
for(Person p:people){
	System.out.println(p.getName());
 	System.out.println(p.getDescription());
}
```

输出是没有问题的。虽然不能构造Person抽象类的对象，但可以创建抽象类的对象变量。对象变量P不会引用Person对象，而是引用具体子类对象。

在java中，抽象方法是一个重要的概念，在接口中，将会看到更多的抽象方法。

**受保护的访问**

有些时候，允许子类访问超类中的方法或某个域，为此，可以将这些方法或者域声明为protected。

Note:

+ 实际应用中，要谨慎使用protected属性，避免破坏封装原则
+ 如果需要限制某个方法的使用，可以将它申明为protected，表明子类受到信任，可以访问超类中的方法。

### Object类—所有类的超类

Object类是Java中所有类的始祖，在Java中，每个类都是由它扩展而来的。

如果没有明确指出超类，Object就被认为是这个类的超类。

可以使用Object类型的变量引用任何类型的对象。如：

```java
Object obj=new Employee("harry",...);
```

如果要对Employee对象的做操作，需要进行类型转换：

```java
Employee e=(Employee) obj;
```

java中，只有基本类型，如数值、字符、布尔类型的值，不是对象。

所有的数组类型，不论是对象数组还是基本类型的数组，都扩展了Object类。如：

```java
Employee[] staff=new Employee[10];
obj=staff; //ok
obj=new int[10];//ok
```

> C++中，没有所有类的基类，但每个指针都可以转换为void* 指针。

**equals方法**

Object类中的equals方法用于检测一个对象是否等于另一个对象，这个方法将判断两个对象是否具有相同的引用。

如果两个对象的状态相等，就认为这两个对象 是相等的。

equals方法既可以在超类中定义，也可以在其子类中定义。

在超类中定义equals，代码：

```java
public class Employee
{
   ...
   public boolean equals(Object otherObject){
   		// a quick test to see if the objects are identical
      if (this == otherObject) return true;

      // must return false if the explicit parameter is null
      if (otherObject == null) return false;

      // if the classes don't match, they can't be equal
      if (getClass() != otherObject.getClass()) return false;

      // now we know otherObject is a non-null Employee
      Employee other = (Employee) otherObject;

      // test whether the fields have identical values
      return Objects.equals(name, other.name) && salary == other.salary && Objects.equals(hireDay, other.hireDay);
   }
}
```

> 其中，`getClass()`方法返回一个对象所属的类。在检测中，只有在两个对象属于同一个类时，它们才有可能相等。

Note:

超类中定义Employee的equlas方法，步骤：

1. 判断两个对象是否具有相同引用
2. 检测两个对象状态是否相等。

在子类中定义equals方法，代码：

```java
public class Manager extends Employee
{
    public boolean equals(Object otherObject){
      if (!super.equals(otherObject)) return false;
      Manager other = (Manager) otherObject;
      // super.equals checked that this and other belong to the same class
      return bonus == other.bonus;
   }
}
```

Note:

子类中定义Employee的equlas方法，步骤：

1. 首先调用超类的equals方法，否则，
2. 比较子类中的实例域。

如果隐式参数和显式参数不属于同一个类，equals方法将如何处理，？P186

**hashCode方法**

散列码(hash code)是由对象导出的一个整型值。

`hashCode`方法是定义在Object类中的，所以每个对象都有一个默认的散列码，它的值为对象的存储地址。

**toString方法**

在Object中还有一个重要的方法，就是`toString()`方法，它用于返回表示对象值的字符串。

示例：Employee类的`toSring()`方法实现

```java
public class Employee
{
    ...
    public String toString(){
    	return getClass().getName()
            +"[name:"+name
            +"salary:"+salary
            +"hireday:"+hireDay
            +"]";
            
	}
}

//子类
public class Manager extends Employee
{
    ...
    public String toString(){
        return super.toString()
            +"[bonus:"+bonus
            +"]";
    }
}
```

> 其中，getClass().getName()方法获取类名的字符串。

Note:

+ 设计子类的程序员，应该定义自己的`toString()`方法，并加入子类域的描述。
+ `System.out.println(Employee)`中，`println`将直接调用`Employee`的`toString()`方法。

+ `toString()`方法是很有用的调试工具。应该为每个类都定义一个该方法。

### 泛型数组列表——ArrayList类

C++语言中，必须在编译时就确定震哥哥数组的大小，但在java中，允许在运行时确定数组的大小。

`ArrayList`类（有点像数组），它在添加和删除元素时，具有自动调节数组容量的功能。

`ArrayList`是一个采用类型参数(type parameter)的**泛型类(generic class)**。 构造一个保存Employee对象的数组列表，代码：

```java
ArrayList<Employee> staff=new ArrayList<Employee>();
```

可以省略右边的`<>`中参数：

```java
ArrayList<Employee> staff=new ArrayList<>();
```

**add()方法**

`ArrayList`的的`add()`方法，示例：

```java
staff.add(new Employee("Harry,..."));
```

如果条用`add()`方法的时，内部数组空间已满，则数组列表将自动创建一个更大的数组，并将原对象拷贝到新的大数组中。

**ensureCapacity()方法**

如果已经知道了数组要存储的元素数量，就可以在填充数组之前（即现在的数组还没有添加任何元素），调用该方法，直接定义数组的大小。

```java
staff.ensureCapacity(100);
```

> 等价于将初始容量传递给ArrayList构造器：`ArrayList<Employee> staff=new ArrayList<>(100);`

**size()方法**

该方法返回数组列表中包含的实际元素个数。

```java
staff.size();
```

**trimToSize()方法**

一旦确认了数组大小不再发生变化，就是用该方法，将存储区的大小调整为当前存储的元素，所需要的存储空间数目。垃圾回收机制将回收多余的空间。

**访问数组列表元素**

使用`get()`和`set()`方法，实现访问和改变数组元素的操作。

> 而不是[]操作形式。

设置第i个元素：

```java
staff.set(i,harry);
```

> 等价于a[i]=harry;

Note:

+ `set()`方法只能替换数组中已存在的元素内容。

获取第i个元素：

```java
Employee e=staff.get(i);
```

> 等价于Employee e=a[i];

### 对象包装器

所有的基本类型都有一个与之对应的类，比如Integer类对应于基本类型int。这些基本类型对应的类称为包装器。

> 其它对象包装器：
>
> Long、Float、Double、Short、Byte、character、Boolean。

Note:

+ 对象包装器不可改变
+ 对象包装器是final，所以不能定义它们的子类

如果要定义一个整型数组列表，格式`ArrayList<int>`是错误的，因为类型参数不允许是基本类型。所以需要使用类型包装器，如：

```java
ArrayList<Integer> list=new ArrayList<>(); //ok
```

> 上述语句的执行效率低于`int[]`，
>
> 上述语句适合用于构造小型集合。

**自动装箱**

在整型数组列表中添加元素：

```java
list.add(3);
```

该语句当自动变为：

```java
list.add(Inteeger.valueOf(3));
```

这种改变就叫做自动装箱(autobosing)。

**自动拆箱**

当一个Interge对象赋给int对象时，将会自动拆箱，代码：

```java
int n=list.get(i);
```

该语句当自动变为：

```java
int n =list.get(i).intValue();
```

这种改变就叫做自动拆箱。

Note:

+ 由于包装器类引用可以为null，所以自动装箱可能抛出一个`NullPointerException`异常

示例：

```java
Integer n=null;
System.out.println(n);//throws exception
```

+ 如果在条件表达式中混用了Integer和Double类型，Integer值就会拆箱，提升为double，再装箱为Double

示例：

```java
Interger n=1;
Double d=2;
System.out.println(true? n:x);//1.0
```

+ 装箱和拆箱是编译器认可的，而不是虚拟机。
+ 要想将字符串转换为整型，可以使用语句`int x=Integer.parseInt(s);`

> `parseInt()`是一个静态方法，只是放在了Integer类中。

### 参数个数可变的方法

先看2个方法调用：

```java
System.out.printf("%d",n);

System.out.printf("%d %s",n,"string");
```

两个方法调用前者有2个参数，后者有3个参数，但它们调用的都是同一个方法。

关于`printf`，是这样定义的：

```java
public class PrintStream(){
    public PrintStream printf(String fmt,Object... args){
        return format(fmt,args);
    }
}
```

其中`...`是java代码的一部分，表明这个方法可以接受任意数量的对象。上述`Object... args`等价于`Object[]`。

Note:

+ 允许将一个数组传递给可变参数方法的最后一个参数。

示例：

```java
System.out.printf("%d %s",new Object[] {new Integer(1),"string" });
```

### 捕获异常

并不是所有的异常都是可以避免的如果无法避免，编译器要求提供一个处理器。格式如下：

```java
try{
    statements that might throw exceptions;
}catch(Exception e){
    handle action;
}
```

### 继承的设计技巧

1. 将公共造作和域，放在超类
2. 不适用受保护的域
3. 使用继承实现"is a"的关系
4. 除非所有继承的方法都有意义，否则不要使用继承
5. 再覆盖方法时，不要改变预期行为
6. 使用多态，而不是类型信息

### 其它

本章体跳过的内容：

+ 枚举
+ 反射

