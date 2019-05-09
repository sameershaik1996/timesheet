package us.redshift.timesheet.reposistory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import us.redshift.timesheet.domain.Client;


@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Page<Client> findAll(Pageable pageable);


    @Transactional
    @Modifying
    @Query(value = "update Client c set c = :client where c.name=:name")
    int saveByName(@Param("name") String name, @Param("client") Client client);
}
