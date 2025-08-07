class Animal {
    void eat() {
        System.out.println("Animal eats food");
    }
}

class Dog extends Animal {
    void bark() {
        System.out.println("Dog barks");
    }
}

class Puppy extends Dog {
    void weep() {
        System.out.println("Puppy weeps");
    }
}
class Cat extends Animal {
    void meow() {
        System.out.println("Cat meows");
    }
}
interface Printable {
    void print();
}

interface Showable {
    void show();
}

class Demo implements Printable, Showable {
    public void print() {
        System.out.println("Printing...");
    }

    public void show() {
        System.out.println("Showing...");
    }
}

public class InheritanceDemo {
    public static void main(String[] args) {
        // Single & Multilevel Inheritance
        Puppy puppy = new Puppy();
        puppy.eat();    // Animal
        puppy.bark();   //  Dog
        puppy.weep();   // Puppy

        System.out.println();

        // Hierarchical Inheritance
        Cat cat = new Cat();
        cat.eat();      // Animal
        cat.meow();     // Cat

        System.out.println();

        // Multiple Inheritance Interface
        Demo demo = new Demo();
        demo.print();
        demo.show();
    }
}
