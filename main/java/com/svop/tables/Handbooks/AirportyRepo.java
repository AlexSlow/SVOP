package com.svop.tables.Handbooks;

import com.svop.tables.Handbooks.Airporty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AirportyRepo extends CrudRepository<Airporty,Long> {

    Page<Airporty> findAll(Pageable page);
    Airporty findById(Integer integer);
   void deleteByIdIn(List<Integer> ids);
    List<Airporty> findByNameRu(String name);
    List<Airporty> findAll();
    /*
    @Modifying
    @Query("UPDATE Airporty e SET e.nameRu = :nameRu, e.nameEng = :nameEng,e.nameCh=:nameCh,e.gmt=:GMT,e.icao=:ICAO, e.iata=:IATA where e.id = :id")
    int updateAirport(@Param("id") Integer id, @Param("nameRu") String nameRu,
                       @Param("nameEng") String nameEng,@Param("nameCh")  String nameCh,
                       @Param("GMT") String GMT, @Param("ICAO") String ICAO,@Param("IATA") String IATA );
*/



}
