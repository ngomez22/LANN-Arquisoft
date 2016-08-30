package models;


import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Pozo extends Model{
    @Id
    @GeneratedValue
    private Long id;
}
