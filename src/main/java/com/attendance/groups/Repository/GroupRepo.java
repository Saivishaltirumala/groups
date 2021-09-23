package com.attendance.groups.Repository;

import com.attendance.groups.Model.Group;
import com.attendance.groups.Model.GroupView;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GroupRepo extends CrudRepository<Group,Integer>
{
    @Query(value = "select * from inst_groups where name= ?1 and instituteid = ?2",nativeQuery = true)
    public List<Group> findByNameAndInstitutionId(String name, String institution_id);
    @Query(value = "select * from inst_groups where instituteid = ?1 and id= ?2",nativeQuery = true)
    public List<Group> findByInstitutionIdAndGroupId(String insid,String gid);
    @Query(value = "select * from inst_groups where instituteid = ?1 and id= ?2",nativeQuery = true)
    public List<Group> findByIdAndInstitutionId(String insid,String gId);
    @Query(value = "select * from inst_groups where instituteid = ?1",nativeQuery = true)
    public List<Group> findByInstitutionId(String instituteId);
    @Query(value = "select * from inst_groups where instituteid = ?1 and id = ?2",nativeQuery = true)
    public Optional<Group> findByInstitutionIdAndId(String id, String name);




}
