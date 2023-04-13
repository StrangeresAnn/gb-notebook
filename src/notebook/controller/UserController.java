package notebook.controller;

import notebook.log.Logger;
import notebook.model.User;
import notebook.model.repository.GBRepository;

import java.util.List;
import java.util.Objects;


public class UserController {
    private final GBRepository<User, Long> repository;
    private Logger logger;

    public UserController(GBRepository<User, Long> repository, Logger logger) {
        this.repository = repository;
        this.logger = logger;
    }

    public void saveUser(User user) {
        repository.create(user);
        logger.log("Создал нового пользователя: " +
                user.getId() + " " + user.getFirstName() + " " +
                user.getLastName() + " " + user.getPhone());
    }

    public User readUser(Long userId) throws Exception {
        List<User> users = repository.findAll();
        for (User user : users) {
            if (Objects.equals(user.getId(), userId)) {
                return user;
            }
        }

        throw new RuntimeException("User not found");
    }
    public User findUserById(long id) {
        return repository.findById(id).orElseThrow(()-> new RuntimeException("User not found"));
    }
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public boolean updateUser(Long id, User update) {
        try {
            repository.update(id, update);
            logger.log("Обновлённый пользователь: " + update.getFirstName() + " " +
                    update.getLastName() + " " + update.getPhone());
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deteleUser(Long id) {
        try {
            logger.log("Удалил пользователя с id: " + id);
            return repository.delete(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
