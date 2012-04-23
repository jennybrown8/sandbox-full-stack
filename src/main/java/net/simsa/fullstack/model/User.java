package net.simsa.fullstack.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {
	  @GeneratedValue @Id Long id;
	  String firstName;
	  String lastName;
	  String email;
}
