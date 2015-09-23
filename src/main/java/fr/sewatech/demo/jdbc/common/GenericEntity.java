package fr.sewatech.demo.jdbc.common;

public abstract class GenericEntity {
    private Long key;

    public GenericEntity() {
    }

    public GenericEntity(Long key) {
        this.key = key;
    }

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }


}
