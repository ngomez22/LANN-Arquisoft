package models;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Pozo {
    @Id
    @GeneratedValue
    private Long id;
}
