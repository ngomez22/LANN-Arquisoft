package models;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Mensaje  {
    @Id
    @GeneratedValue
    private Long id;
}

