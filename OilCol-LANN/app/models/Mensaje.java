package models;


import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Mensaje  extends Model {
    @Id
    @GeneratedValue
    private Long id;
}

