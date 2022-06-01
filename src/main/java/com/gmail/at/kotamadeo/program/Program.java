package com.gmail.at.kotamadeo.program;

import com.gmail.at.kotamadeo.lists.ToDo;
import com.gmail.at.kotamadeo.utils.Utils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Program {
    private final Scanner scanner = new Scanner(System.in);
    private final List<ToDo> toDoList = new ArrayList<>();

    public void start() {
        String input;
        String[] allInput;
        while (true) {
            try {
                printMenu();
                input = scanner.nextLine();
                if ("выход".equalsIgnoreCase(input) || "0".equals(input)) {
                    scanner.close();
                    break;
                } else {
                    switch (Integer.parseInt(input)) {
                        case 1:
                            System.out.println(Utils.ANSI_BLUE + "Введите что нужно сделать и к какому сроку " +
                                    "через (год, месяц, день, час, минуты) через \"_\":" + Utils.ANSI_RESET);
                            allInput = scanner.nextLine().split("_", 6);
                            var date = LocalDateTime.of(Integer.parseInt(allInput[1]),
                                    Integer.parseInt(allInput[2]), Integer.parseInt(allInput[3]),
                                    Integer.parseInt(allInput[4]), Integer.parseInt(allInput[5]));
                            toDoList.add(new ToDo(allInput[0], date));
                            break;
                        case 2:
                            printToDoList(toDoList);
                            break;
                        case 3:
                            System.out.println(Utils.ANSI_BLUE + "Чтобы удалить дело из списка выберите № этого дела:" +
                                    Utils.ANSI_RESET);
                            printToDoList(toDoList);
                            input = scanner.nextLine();
                            toDoList.remove(Integer.parseInt(input) - 1);
                            break;
                        default:
                            System.out.println(Utils.ANSI_RED + "Вы ввели неверный номер операции!" + Utils.ANSI_RESET);
                    }
                }
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.out.println(Utils.ANSI_RED + "Ошибка ввода!" + Utils.ANSI_RESET);
            }
        }
    }

    private static void printMenu() {
        System.out.println(Utils.ANSI_YELLOW + "Эта программа работает как записная книга!" + Utils.ANSI_RESET);
        System.out.println(Utils.ANSI_PURPLE + "Возможные команды программы:" + Utils.ANSI_RESET);
        System.out.println("0 или выход: выход из программы.");
        System.out.println("1: добавить дело в список.");
        System.out.println("2: вывести список дел.");
        System.out.println("3: удалить дело из списка.");
    }

    private static void printToDoList(List<ToDo> toDoList) {
        if (!toDoList.isEmpty()) {
            System.out.println(Utils.ANSI_BLUE + "Ваш список дел:" + Utils.ANSI_RESET);
            for (var i = 0; i < toDoList.size(); i++) {
                System.out.printf("%s%s. %s.%s%n", Utils.ANSI_CYAN, (i + 1), toDoList.get(i), Utils.ANSI_RESET);
            }
        } else {
            System.out.println(Utils.ANSI_RED + "список дел пуст!" + Utils.ANSI_RESET);
        }
    }
}
