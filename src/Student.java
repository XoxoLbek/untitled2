public class Student extends Person{
    private double gpa;

    public Student(String name, String surname, double gpa){
        super(name,surname);
        setGpa(gpa);
    }

    public void setGpa(double gpa){
        this.gpa = gpa;
    }

    public double getGpa(){
        return gpa;
    }

    @Override
    public void tostring(){
        System.out.println("Student: " + id + " " + name + " " + surname);
    }
}
