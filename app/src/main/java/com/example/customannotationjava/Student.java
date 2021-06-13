package com.example.customannotationjava;

@SerializableJSON
public class Student {
    @JsonField
    private String lastName;
    @JsonField
    private String firstName;
    @JsonField(key = "fullName")
    private String name;
    @JsonField(key = "home")
    private String address;
    @JsonField
    private int age;

    private String school;

    public Student(){}
    public Student(String lastName,String firstName,String address,int age,String school){
        this.lastName = lastName;
        this.firstName = firstName;
        this.address = address;
        this.age = age;
        this.school = school;
    }

    @BeforeSerializable
    public void initFullName(){
        name = lastName +" "+firstName+"@";
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
