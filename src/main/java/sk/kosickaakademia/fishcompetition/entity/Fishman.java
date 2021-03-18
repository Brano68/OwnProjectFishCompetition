package sk.kosickaakademia.fishcompetition.entity;

public class Fishman {
    private int id;
    private String fname;
    private String lname;
    private int age;
    private String nameOfTeam;

    public Fishman(int id, String fname, String lname, int age, String nameOfTeam) {
        this(fname, lname, age, nameOfTeam);
        this.id = id;
    }

    public Fishman(String fname, String lname, int age, String nameOfTeam) {
        this.fname = fname;
        this.lname = lname;
        this.age = age;
        this.nameOfTeam = nameOfTeam;
    }

    public int getId() {
        return id;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public int getAge() {
        return age;
    }

    public String getNameOfTeam() {
        return nameOfTeam;
    }
}
