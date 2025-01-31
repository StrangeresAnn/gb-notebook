package notebook.model.dao.impl;

import notebook.model.dao.Operation;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileOperation implements Operation<String> {
    private final String fileName;

    public FileOperation(String fileName) {
        this.fileName = fileName;
        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * ����� ��� ���������� �� �����
     * @return ��������� ������ ����� �� �����
     */
    @Override
    public List<String> readAll() {
        List<String> lines = new ArrayList<>();
        File file = new File(fileName);
        try {
            //������� ������ FileReader ��� ������� File
            FileReader fr = new FileReader(file);
            //������� BufferedReader � ������������� FileReader ��� ����������� ����������
            BufferedReader reader = new BufferedReader(fr);
            // ������� ������� ������ ������
            String line = reader.readLine();
            if (line != null) {
                lines.add(line);
            }
            while (line != null) {
                line = reader.readLine();
                if (line != null) {
                    lines.add(line);
                }
            }
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    /**
     * ����� ������ � ����.
     * @param data
     */
    @Override
    public void saveAll(List<String> data) {
        try (FileWriter writer = new FileWriter(fileName, false)) {
            for (String line : data) {
                // ������ ���� ������
                writer.write(line); // ����� ���������� ������� � ����� ������
                // ������ �� ��������
                writer.append('\n');
            }
            writer.flush(); // ������� write
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
