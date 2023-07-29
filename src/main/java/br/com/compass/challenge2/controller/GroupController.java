package br.com.compass.challenge2.controller;

import br.com.compass.challenge2.entity.Group;
import br.com.compass.challenge2.entity.Student;
import br.com.compass.challenge2.service.GroupService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/group")
public class GroupController {

    private GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Group>> findAll(){
        List<Group> groups = groupService.findAll();
        return new ResponseEntity<>(groups, HttpStatus.OK);
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<Group> findGroupById(@PathVariable Long groupId){
        Group group = groupService.findById(groupId);
        if(group != null){
            return new ResponseEntity<>(group, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /*
    @GetMapping("/{groupName}")
    public ResponseEntity<Group> findGroupByName(@PathVariable String groupName){
        Group group = groupService.findDistinctByName(groupName);
        if(group == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(group, HttpStatus.OK);
    }*/

    @PostMapping
    public ResponseEntity<Group> createGroup(@Valid @RequestBody Group postGroup){
        postGroup.setId(0L);
        Group newGroup = groupService.save(postGroup);
        return new ResponseEntity<>(newGroup, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Group> updateGroup(@Valid @PathVariable Long id, @RequestBody Group putGroup){
        Group existingGroup = groupService.findById(id);
        if(putGroup != null){
            putGroup.setId(id);
            Group updatedGroup = groupService.update(existingGroup);
            return new ResponseEntity<>(updatedGroup, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{groupId}")
    public ResponseEntity<Void> deleteGroupById(@PathVariable Long groupId){
        Group checkGroup = groupService.findById(groupId);
        if(checkGroup != null){
            groupService.deleteById(groupId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /*
    @DeleteMapping("/{groupName}")
    public ResponseEntity<Void> deleteGroupByName(@PathVariable String groupName){
        Group checkGroup = groupService.findDistinctByName(groupName);
        if(checkGroup != null){
            groupService.deleteByName(groupName);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }*/

}
