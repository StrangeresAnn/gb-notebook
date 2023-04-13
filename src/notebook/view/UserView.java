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
        while (true) { // ����
            String command = prompt("������� �������: "); // ������������� � ������������ ��� ���������� ������� ������������
            com = Commands.valueOf(command); // ������������ �������� ��� ���������� , ����� ����������� String � Long ������
            if (com == Commands.EXIT) return; // ������� ��� ������ . Return �� ������ ���� false???????
            switch (com) {
                case CREATE:
                    String firstName = prompt("���: ");
                    String lastName = prompt("�������: ");
                    String phone = prompt("����� ��������: ");
                    userController.saveUser(new User(firstName, lastName, phone));
                    break;
                case READ:
                    String id = prompt("������������� ������������: ");
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
                    long userId = Long.parseLong(prompt("������� id ������������: "));
                    String updateName = prompt("���: ");
                    String updateLastName = prompt("�������: ");
                    String updatePhone = prompt("����� ��������: ");
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
     * ����� ����������� ����� ����� �������� � ������������ ���� �����.
     * @param message ��������� ��� ������.
     */
    private String prompt(String message) { //
        Scanner in = new Scanner(System.in);
        System.out.print(message);
        return in.nextLine();
    }

    /**
     * ���������� ����� ��������� ������ User.
     */
    private User createUser() { //
        String firstName = prompt("���: ");
        String lastName = prompt("�������: ");
        String phone = prompt("����� ��������: ");
        return new User(firstName, lastName, phone);
    }
}
