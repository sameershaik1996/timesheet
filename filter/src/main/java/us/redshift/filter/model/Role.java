package us.redshift.filter.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;



@Getter
@Setter
public class Role implements Serializable {

   private Long id;

   private String name;
}
