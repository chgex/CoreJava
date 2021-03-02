/*
 * @Author: liubai
 * @Date: 2021-03-02
 * @LastEditTime: 2021-03-02
 */
package abstractClass;

public abstract class Person {
    
    private String name;
    
    //constructor
    public Person(String name){
        this.name=name;
    }

    //abstract method
    public abstract String getDescription();

    public String getName(){
        return name;
    }
    
}

