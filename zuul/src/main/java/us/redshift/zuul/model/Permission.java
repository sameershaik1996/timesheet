package us.redshift.zuul.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class Permission implements Serializable {

    private Long id;

    private String name;


}
