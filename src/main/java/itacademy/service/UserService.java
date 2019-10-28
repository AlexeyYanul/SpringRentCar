package itacademy.service;

import itacademy.model.User;
import itacademy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {

    private  UserRepository userRepository;

    @Autowired
    public UserService(UserRepository repository) {
        this.userRepository = repository;
    }

    public User findByLoginAndPassword(String login, String password){
        return userRepository.findByLoginAndPassword(login, password);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }



}
