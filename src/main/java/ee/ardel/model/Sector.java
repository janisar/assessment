package ee.ardel.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Sector {

  @Id
  @GeneratedValue
  @Column(name = "id", unique = true, nullable = false)
  private Long id;

  @Column(name = "parent_sector_id")
  private Long parentId;

  @OneToMany(mappedBy = "parentId", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
  private Set<Sector> children = new HashSet<>();

  @Column(name = "value")
  private String name;
}
