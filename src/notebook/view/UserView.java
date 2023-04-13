package notebook.view;

import notebook.controller.UserController;
import notebook.model.User;
import notebook.util.Commands;

import java.util.List;
import java.util.Scanner;

public class UserView {
    private final UserController userController;

    public UserView(UserController userController) {
        this.userController = userController;
    }

    public void run(){
        Commands com;
        while (true) { // Цикл
            String command = prompt("Введите команду: "); // Инициализация и присваивание для переменной команды пользователя
            com = Commands.valueOf(command); // Присваивание значения для переменной , метод преобразует String в Long объект
            if (com == Commands.EXIT) return; // Условие для выхода . Return не должен быть false???????
            switch (com) {
                case CREATE:
                    String firstName = prompt("Имя: ");
                    String lastName = prompt("Фамилия: ");
                    String phone = prompt("Номер телефона: ");
                    userController.saveUser(new User(firstName, lastName, phone));
                    break;
                case READ:
                    String id = prompt("Идентификатор пользователя: ");
                    try {
                        User user = userController.readUser(Long.parseLong(id));
                        System.out.println(user);
                        System.out.println();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case LIST:
                    List<User> users = userController.getAllUsers();
                    for(User user: users) {
                        System.out.println(user);
                    }
                    break;
                case UPDATE:
                    long userId = Long.parseLong(prompt("Введите id пользователя: "));
                    String updateName = prompt("Имя: ");
                    String updateLastName = prompt("Фамилия: ");
                    String updatePhone = prompt("Номер телефона: ");
                    User updatedUser = new User(updateName, updateLastName, updatePhone);
                    userController.updateUser(userId, updatedUser);
                    break;
                case DELETE:
                    userId = Long.parseLong(prompt("Enter user id: "));
                    userController.deteleUser(userId);
                    break;
            }
        }
    }

    /**
     * Метод принимающий текст через терминал и возвращающий этот текст.
     * @param message Сообщение для вывода.
     */
    private String prompt(String message) { //
        Scanner in = new Scanner(System.in);
        System.out.print(message);
        return in.nextLine();
    }

    /**
     * Возвращает новый экземлпяр класса User.
     */
    private User createUser() { //
        String firstName = prompt("Имя: ");
        String lastName = prompt("Фамилия: ");
        String phone = prompt("Номер телефона: ");
        return new User(firstName, lastName, phone);
    }
}
