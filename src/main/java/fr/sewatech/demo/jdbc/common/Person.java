package fr.sewatech.demo.jdbc.common;

public class Person extends GenericEntity {
    private String name;
    private String forname;

    public Person() {
        super();
    }

    public Person(String name, String forname) {
        this.name = name;
        this.forname = forname;
    }

    public Person(Long key, String name, String forname) {
        super(key);
        this.name = name;
        this.forname = forname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getForname() {
        return forname;
    }

    public void setForname(String forname) {
        this.forname = forname;
    }

    @Override
    public String toString() {
        return "Person{" + "key=" + getKey() + ",name='" + name + "'" + ",forname='" + forname + "'" + "}";
    }
}
