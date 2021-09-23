package com.attendance.groups.Service;

import com.attendance.groups.Exceptions.Validation2001;
import com.attendance.groups.Model.Group;
import com.attendance.groups.Model.GroupView;
import com.attendance.groups.Repository.GroupRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class Groupsservice
{
    @Autowired
    private GroupRepo groupRepo;
    Logger log= LoggerFactory.getLogger(Groupsservice.class);
    public String add(Group group) throws Validation2001
    {

       List<Group> l=findgrpofinst(group.getName(), group.getInstitute().getId());
       if(l.size()>0)
       {
           log.error("Given Group name already exists");
           throw new ValidationException("Group name already exists");
       }
        UUID newGid= UUID.randomUUID();
        group.setId(newGid.toString());
       return groupRepo.save(group).getId();
    }
    public String saving(Group group)
    {

        return groupRepo.save(group).getId();
    }
    public List<GroupView> get(String institudeid)
    {
        List<GroupView> gv=new ArrayList<>();
        for(Group group:groupRepo.findByInstitutionId(institudeid))
        {
            gv.add(new GroupView(group.getId(),group.getName(),group.getDescription(),group.isActive()));
        }
        return gv;
    }
    public List<Group> findgrpofinst(String groupName,String institudeid)
    {
       return groupRepo.findByNameAndInstitutionId(groupName,institudeid);
    }
    public Optional<Group> findbygrpidansinsid(String insid, String id)
    {
        return groupRepo.findByInstitutionIdAndId(insid,id);
    }

    public GroupView getByid(String insid,String gid) throws Validation2001 {
       List<Group> group=groupRepo.findByInstitutionIdAndGroupId(insid,gid);
       if(group.size()>0)
       {
           return new GroupView(group.get(0).getId(),group.get(0).getName(),group.get(0).getDescription(),group.get(0).isActive());

       }
        throw new Validation2001("GROUP ID NOT PRESENT");

    }

    public List<Group> getByidandins(String id, String ins)
    {
        return groupRepo.findByInstitutionIdAndGroupId(ins,id);
    }
}
