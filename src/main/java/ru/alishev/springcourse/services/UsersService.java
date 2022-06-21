package ru.alishev.springcourse.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alishev.springcourse.models.Assignment;
import ru.alishev.springcourse.models.User;
import ru.alishev.springcourse.repositories.UserRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UsersService {

    private final UserRepository userRepository;

    @Autowired
    public UsersService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll(boolean sortName) {
        if (sortName){
            return userRepository.findAll(Sort.by("name"));
        }
        else {
            return userRepository.findAll();
        }
    }
    public List<User> findWithPagination(Integer page, Integer usersPerPage, boolean sortByName){
        if (sortByName){
            return userRepository.findAll(PageRequest.of(page, usersPerPage, Sort.by("name"))).getContent();
        } else {
            return userRepository.findAll(PageRequest.of(page, usersPerPage)).getContent();
        }
    }

    public User findOne(Integer id){
        Optional<User> foundUser = userRepository.findById(id);
        return foundUser.orElse(null);
    }

    public List<User> search(String query){
        return userRepository.findBySurnameStartingWith(query);
    }

    @Transactional
    public void save(User user){
         userRepository.save(user);
    }

    @Transactional
    public void update(Integer id, User userUpdate){
        User userToBeUpdate = userRepository.findById(id).get();
        userUpdate.setId(id);
        userUpdate.setAssignment(userToBeUpdate.getAssignment());
        userRepository.save(userUpdate);
    }

    @Transactional
    public void delete(Integer id){
        userRepository.deleteById(id);
    }

    public Assignment getUserAssignment(Integer id) {
        return userRepository.findById(id).map(User::getAssignment).orElse(null);
    }

    @Transactional
    public void release(Integer id){
        userRepository.findById(id).ifPresent(user -> {
            user.setAssignment(null);
            user.setTakenAt(null);
        });
    }

    @Transactional
    public void assign(Integer id, Assignment selectedAssignment) {
        userRepository.findById(id).ifPresent(
                user -> {
                    user.setAssignment(selectedAssignment);
                    user.setTakenAt(new Date());
                }
        );
    }
}
