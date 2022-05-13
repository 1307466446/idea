public class Student  extends Person{
    String name;
    int age;

    public Student() {
        System.out.println("pppppppppppp");
    }

    public Student(String name) {
        this.name = name;
    }

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void info(){
        System.out.println("info");
    }

    public static void main(String[] args) {
        Student student = new Student("wwww");
    }
}
