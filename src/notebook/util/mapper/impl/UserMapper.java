package notebook.util.mapper.impl;

import notebook.util.mapper.Mapper;
import notebook.model.User;


public class UserMapper implements Mapper<User, String> {
    /**
     *
      * @param user ��������� ������
     * @return ��������������� ������ � ������ ���������� ������.
     */
    @Override
    public String toInput(User user) {
        return String.format("%s,%s,%s,%s", user.getId(), user.getFirstName(), user.getLastName(), user.getPhone());
    }

    /**
     *
     * @param s
     * @return ����� ��������� ������ User.
     */
    @Override
    public User toOutput(String s) {
        String[] lines = s.split(",");
        long id;
        if (isDigit(lines[0])) {
            id = Long.parseLong(lines[0]);
            return new User(id, lines[1], lines[2], lines[3]);
        }
        throw new NumberFormatException("Id must be a large number");
    }

    /**
     *
     * @param s
     * @return ��������� ������������� � ���� Long ( true or false) .
     * @throws NumberFormatException
     */
    private boolean isDigit(String s) throws NumberFormatException {
        try {
            Long.parseLong(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
