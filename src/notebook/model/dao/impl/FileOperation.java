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
     * Метод для считывания из файла
     * @return Возвращет список строк из файла
     */
    @Override
    public List<String> readAll() {
        List<String> lines = new ArrayList<>();
        File file = new File(fileName);
        try {
            //создаем объект FileReader для объекта File
            FileReader fr = new FileReader(file);
            //создаем BufferedReader с существующего FileReader для построчного считывания
            BufferedReader reader = new BufferedReader(fr);
            // считаем сначала первую строку
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
     * Метод записи в файл.
     * @param data
     */
    @Override
    public void saveAll(List<String> data) {
        try (FileWriter writer = new FileWriter(fileName, false)) {
            for (String line : data) {
                // запись всей строки
                writer.write(line); // Метод записывает элемент в поток вывода
                // запись по символам
                writer.append('\n');
            }
            writer.flush(); // Очистка write
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
