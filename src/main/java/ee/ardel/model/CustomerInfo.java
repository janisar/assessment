package ee.ardel.model;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Entity
@Builder
public class CustomerInfo {

  @Id
  @GeneratedValue
  private Long id;

  @NotNull
  @Size(min = 2, max = 100)
  @Column(name = "name")
  private String name;

  @NotEmpty
  @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinTable(name = "customer_sector", joinColumns = {
    @JoinColumn(name = "customer_id", nullable = false, updatable = false)},
    inverseJoinColumns = {@JoinColumn(name = "sector_id",
      nullable = false, updatable = false)})
  private List<Sector> sectors;

  @AssertTrue
  @Column(name = "tc")
  private boolean tc;
}
