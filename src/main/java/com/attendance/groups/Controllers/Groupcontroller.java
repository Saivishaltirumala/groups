package com.attendance.groups.Controllers;



import com.attendance.groups.Exceptions.ResponseDto;
import com.attendance.groups.Exceptions.UnauthorizedIns;
import com.attendance.groups.Exceptions.Validation2001;
import com.attendance.groups.Exceptions.Validation2002;
import com.attendance.groups.Model.*;
import com.attendance.groups.Service.Groupsservice;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.ValidationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/groups")
public class Groupcontroller
{
    @Autowired
    private Groupsservice groupsservice;
    Logger log= LoggerFactory.getLogger(Groupcontroller.class);

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseDto getGroups(@RequestHeader(value="institutionId") String institutionId) throws UnauthorizedIns {
        log.warn(institutionId+" ENTERED");
        Institute l= restTemplate.getForObject("http://institute/institutions/"+institutionId,Institute.class);
        if(l==null)
        {
            throw new UnauthorizedIns("YOU ARE NOT A REGISTERED INSTITUTION");
        }
        Map<String,Object> mp=new HashMap<>();
        List<GroupView> lg=groupsservice.get(institutionId);
        mp.put("total",lg.size());
        mp.put("Groups",lg);
        /*Allgroupsview allgroupsview=new Allgroupsview(0, ImmutableMap.of("Groups", groupsservice.get(institutionId)));
        allgroupsview.setTotal(allgroupsview.getGroups().get("Groups").size());*/
        return new ResponseDto(mp,null);
    }
    private static boolean countt(String str)
    {
        for(int i=0;i<str.length();i++)
        {
            if(str.charAt(i)!=' ')
            {
                return true;
            }
        }
        return false;
    }
    private static boolean isNameValid(String str)
    {
        return ((!str.equals("")) && (str != null) && (str.matches("^[a-zA-Z ]*$")) &&(countt(str)));
    }

    @RequestMapping("{id}")
    public ResponseDto getById(@PathVariable("id") String itemid,@RequestHeader(value="institutionId") String institutionId) throws Validation2001, UnauthorizedIns {
        Institute l= restTemplate.getForObject("http://institute/institutions/"+institutionId,Institute.class);
        if(l==null)
        {
            throw new UnauthorizedIns("YOU ARE NOT A REGISTERED INSTITUTION");
        }

            return new ResponseDto(groupsservice.getByid(institutionId,itemid),null);

    }

    @Autowired
    private RestTemplate restTemplate;
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto addGroups(@RequestBody Group group,@RequestHeader(value="institutionId") String institutionId) throws Validation2001, UnauthorizedIns {
        log.warn(institutionId+" ENTERED");
       Institute l= restTemplate.getForObject("http://institute/institutions/"+institutionId,Institute.class);
       if(l==null)
       {
             throw new UnauthorizedIns("YOU ARE NOT A REGISTERED INSTITUTION");
       }
        group.setInstitute(l);
        if(group.getName()==null  || group.getDescription()==null)
        {
            System.out.println("here");
            throw new NullPointerException("Please check your input");
        }
        if(group.getName().length()==0)
        {
            throw new Validation2001("NAME IS IN INVALID FORMAT");
        }
        if(!isNameValid(group.getName()))
        {
            throw new Validation2001("NAME IS IN INVALID FORMAT");
        }
        if(group.getName().charAt(0)==' ' || group.getName().charAt(group.getName().length()-1)==' ')
        {
            throw new Validation2001("NAME IS IN INVALID FORMAT");
        }

        Map<String,String> mp=new HashMap<>();
        mp.put("id",groupsservice.add(group));
        return new ResponseDto(mp,null);
    }


    @Autowired
    UpdateView updateView;

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseDto updteGroups(@RequestBody Group group, @RequestHeader(value="institutionId") String institutionId) throws Validation2001, Validation2002, UnauthorizedIns {
        log.warn(institutionId+" ENTERED");
        Institute ins= restTemplate.getForObject("http://institute/institutions/"+institutionId,Institute.class);
        if(ins==null)
        {
            throw new UnauthorizedIns("YOU ARE NOT A REGISTERED INSTITUTION");
        }
        if(group.getId()==null)
        {
            System.out.println("here");
            throw new NullPointerException("Please check your input");
        }

        Optional<Group> l=groupsservice.findbygrpidansinsid(institutionId,group.getId());
        if(l.isPresent())
        {
            Group existinggroup=l.get();
            if((group.getDescription()==null || group.getDescription().length()==0) && (group.getName()==null || group.getName().length()==0))
            {

                throw new NullPointerException("Please check your input");

            }
            else
            {

                if(group.getName()!=null && group.getName().length()>0)
                {
                    if(!isNameValid(group.getName()))
                    {
                        throw new Validation2001("NAME IS IN INVALID FORMAT");
                    }
                    if(group.getName().charAt(0)==' ' || group.getName().charAt(group.getName().length()-1)==' ')
                    {
                        throw new Validation2001("NAME IS IN INVALID FORMAT");
                    }
                    if(groupsservice.findgrpofinst(group.getName(),institutionId).size()>0)
                    {
                        throw new Validation2002("Group name already exists");

                    }
                    else
                    {
                        existinggroup.setName(group.getName());
                    }
                }
                if(group.getDescription()!=null && group.getDescription().length()>0)
                {
                    existinggroup.setDescription(group.getDescription());
                }
                groupsservice.saving(existinggroup);//these leading to probelm
                updateView.setMessage("You have successfully renamed the group");
                updateView.setId(existinggroup.getId());
                updateView.setName(existinggroup.getName());
                updateView.setDescription(existinggroup.getDescription());
            }
        }
        else
        {
            throw new ValidationException("Group ID does not exist.");

        }

        return new ResponseDto(updateView,null);
    }
    //  groups/groups/getByIdandInstituteId/{groupId}/{insId}
    @RequestMapping("/getByIdandInstituteId/{groupId}/{insId}")
    public Group getByIDandInsid(@PathVariable("groupId") String id,@PathVariable("insId") String ins) throws Validation2001
    {
        System.out.println("endered");
        List<Group> l=groupsservice.getByidandins(id,ins);
        if(l.size()>0)
        {
            System.out.println(l.get(0));
            return l.get(0);
        }
        return null;

    }

}
