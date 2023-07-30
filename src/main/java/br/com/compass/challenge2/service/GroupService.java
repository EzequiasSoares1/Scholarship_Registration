package br.com.compass.challenge2.service;

import br.com.compass.challenge2.entity.Group;
import br.com.compass.challenge2.entity.Organizer;
import br.com.compass.challenge2.repository.GroupRepository;
import br.com.compass.challenge2.repository.OrganizerRepository;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class GroupService implements CrudService<Group> {

    private final GroupRepository groupRepository;
    private final OrganizerRepository organizerRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository, OrganizerRepository organizerRepository) {
        this.groupRepository = groupRepository;
        this.organizerRepository = organizerRepository;
    }

    @Override
    public Group findById(Long id) {
        try {
            return groupRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException("Group does not exist with id: " + id);
        }
    }

    @Override
    public List<Group> findAll() {
        return groupRepository.findAll();
    }

    @Transactional
    @Override
    public Group save(Group group) {

        Group savedGroup = groupRepository.save(group);
        List<Organizer> organizersInGroup = new ArrayList<>(savedGroup.getOrganizers());

        for(Organizer org : organizersInGroup){
            org.getGroups().add(savedGroup);
        }

        organizerRepository.saveAll(organizersInGroup);

        return savedGroup;
    }

    @Override
    public Group update(Group group) {
        if (groupRepository.existsById(group.getId())) {
            return groupRepository.save(group);
        } else {
            throw new EntityNotFoundException("Group with id " + group.getId() + " not found.");
        }
    }

    @Override
    public Group deleteById(Long id) {
        Group group;
        try {
            group = groupRepository.findById(id).get();
            groupRepository.deleteById(id);
        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException("Group does not exist with id: " + id);
        }

        return group;
    }
}
