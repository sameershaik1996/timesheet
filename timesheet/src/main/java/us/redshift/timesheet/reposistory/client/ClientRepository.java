package us.redshift.timesheet.reposistory.client;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import us.redshift.timesheet.domain.client.Client;
import us.redshift.timesheet.domain.client.ClientStatus;

import java.util.List;


@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> findAllByStatusOrderByIdAsc(ClientStatus status);

    Client findByClientCode(String clientCode);

    @Query(value = "SELECT distinct(id) from pss_clients where (COALESCE(name,'') LIKE %:name%)",nativeQuery = true)
    List<Long> findIdByNameLike(@Param("name") String clientName);


    @Query(value = "SELECT * FROM pss_clients WHERE (COALESCE(client_code,'') LIKE %:clientCode% OR COALESCE(name,'') LIKE %:name%)", nativeQuery = true)
    Page<Client> searchClients(@Param("clientCode")String clientCode, @Param("name")String name, Pageable pageable);

}
