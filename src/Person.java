public abstract class Person implements Payable, Comparable<Person>{
    int id;
    private static int idCount = 1;
    String name;
    String surname;
    String toStr()
    {
        return id + ". " + name + " " + surname;
    }
    int getId()
    {
        return id;
    }
    String getName()
    {
        return name;
    }
    void setName(String nameInput)
    {
        name = nameInput;
    }
    String getSurname()
    {
        return surname;
    }
    void setSurname(String surnameInput)
    {
        surname = surnameInput;
    }
    Person()
    {
        this.id = idCount;
        idCount++;
    }
    Person(String nameC, String surnameC)
    {
        this.id = idCount;
        idCount++;
        setName(nameC);
        setSurname(surnameC);
    }
    public double getPaymentAmount()
    {
        return 0;
    }
    String getPosition()
    {
        if (this.getClass() == Employee.class)
        {
            return this.getPosition();
        }
        return "Student";
    }

    public int compareTo(Person o) {
        if (this.getPaymentAmount() > o.getPaymentAmount())
        {
            return 1;
        }
        else
        {
            return -1;
        }
    }

    public abstract void tostring();
}
