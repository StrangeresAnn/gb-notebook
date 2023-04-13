package notebook.model.repository.impl;

import notebook.model.User;
import notebook.model.dao.impl.FileOperation;
import notebook.model.repository.GBRepository;
import notebook.util.mapper.impl.UserMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository implements GBRepository<User, Long> {
    private final UserMapper mapper;
    private final FileOperation operation;

    public UserRepository(FileOperation operation) {
        this.mapper = new UserMapper();
        this.operation = operation;
    }

    /**
     *
     * @return Список users
     */
    @Override
    public List<User> findAll() { // Метод
        List<String> lines = operation.readAll(); // Запись считанных строк с консоли в список
        List<User> users = new ArrayList<>(); // Инициализация нового списка Array
        for (String line : lines) { // Цикл. Проходимся по списку
            users.add(mapper.toOutput(line)); // Добавляем каждый элемент в список users
        }
        return users;
    }

    @Override
    public User create(User user) {
        List<User> users = findAll();
        long max = 0L; // ????
        for (User u : users) {
            long id = u.getId();
            if (max < id){
                max = id;
            }
        }
        long next = max + 1;
        user.setId(next);
        users.add(user);
        List<String> lines = new ArrayList<>();
        for (User u: users) {
            lines.add(mapper.toInput(u));
        }
        operation.saveAll(lines);
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {

        return Optional.empty();
    }

    /**
     * Метод записи в файл всех экземпляров класса user
     * @param userId
     * @param update
     * @return
     */
    @Override
    public Optional<User> update(Long userId, User update) {
        try {
            List<User> users = findAll();
            User updateUser = users.stream().filter(u -> {
                return u.getId().equals(userId);
            }).findFirst().get();
            updateUser.setFirstName(update.getFirstName());
            updateUser.setLastName(update.getLastName());
            updateUser.setPhone(update.getPhone());
            List<String> list = new ArrayList<>();
            for(User user: users) {
                list.add(mapper.toInput(user));
            }
            operation.saveAll(list);
            return Optional.of(updateUser);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try {
            List<User> users = findAll();
            User removeUser = users.stream().filter(u -> u.getId().equals(id)).findFirst().get();
            users.remove(removeUser);
            List<String> list = new ArrayList<>();
            for (User user : users) {
                list.add(mapper.toInput(user));
            }
            operation.saveAll(list);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
