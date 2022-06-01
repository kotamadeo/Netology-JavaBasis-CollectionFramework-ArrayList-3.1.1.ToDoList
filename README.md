# **Задача № 1 Записная книга**

## **Цель**:
1. Создать программу,  представляющую собой менеджер списка дел. Списки дел можно хранить в массивах и списках. Массивы имеют фиксированную длину, и после того как массив создан, он не может расти или уменьшаться. ```ArrayList``` (одна из имплементаций списка) может менять свой размер во время исполнения программы, при этом необязательно указывать размерность при создании объекта. Кроме того, вы без проблем можете вставить новый элемент в середину коллекции, а также спокойно удалить элемент из любого места. Поэтому очень удобно использовать ```List``` для ведения списка дел, ведь часто бывает, что нужно добавить какие-то дела в середине дня или же что-то удалить.
2. Иметь возможность сделать запрос списка задач/дел у пользователя;
3. Иметь возможность добавить задачу в список;
4. Иметь возможность удалить задачу из списка;
5. Иметь возможность вывода всех задач с их номерами (для пользователя нумеровать с 1).

### *Пример*:
``` Пример 1
Выберите действие:
1. Добавить задачу
2. Вывести список задач
3. Удалить задачу
0. Выход
1 <enter>
Введите задачу для планирования:
Почитать про ArrayList

1 <enter>
Введите задачу для планирования:
Повторить примитивные типы данных

2 <enter>
Список задач:
1. Почитать про ArrayList
2. Повторить примитивные типы данных

0 <enter>
```

### **Моя реализация**:
1. Реализация осуществлена в парадигме ООП.
2. Создал структуру классов:

* **Program** - класс, отвечающий за запуск программы, путем инициирования метода *start()* с инициированием внутри себя
  вспомогательных ```void``` методов: 
  * *printMenu()* - выводит меню команд программы на экран;
  * *printToDoList()* - выводит список дел на экран.  

#### Класс **Program**:
``` java
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
```

* **ToDo** - класс, описывающий задачу. имеет переопреденный *toString()*.

#### Класс **ToDo**:
``` java   
public class ToDo {
    private LocalDateTime date;
    private String toDo;

    public ToDo(String toDo, LocalDateTime date) {
        this.toDo = toDo;
        this.date = date;
    }

    @Override
    public String toString() {
        return toDo + " нужно сделать к: " + date.getHour() + ":" + date.getMinute() + " " +
                date.format(DateTimeFormatter.ofPattern("d:MMM:yyyy")) + " г";
    }
}
```

2. Использовал кодирование цвета текста (ANSI).

#### Класс **Utils**:
``` java
public class Utils {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static void printDelim() {
        System.out.println(ANSI_GREEN + "*********************************************" + ANSI_RESET);
    }
}
```

3. Использовал ```try-catch```, чтобы избежать падение программы в исключения.

#### Метод *main()* в классе **Main**:
``` java
public class Main {
    public static void main(String[] args) {
        var program = new Program();
        program.start();
    }
}
```

## *Вывод в консоль*:

* меню:
``` 
Эта программа работает как  записная книга!
Возможные команды программы:
0 или выход: выход из программы.
1: добавить дело в список.
2: вывести список дел.
3: удалить дело из списка.
```

* Демонстрация работы:
```
1
Введите что нужно сделать и к какому сроку через (год, месяц, день, час, минуты) через "_":
Дополнить конспект о дженериках_2022_04_05_18_30

2
Ваш список дел:
1. Дополнить конспект о дженериках нужно сделать к: 18:30 5.4.2022 г.
2. Пройти первое собеседование на java-developer нужно сделать к: 16:0 30.6.2022 г.

3
Чтобы удалить дело из списка выберите № этого дела:
Ваш список дел:
1. Дополнить конспект о дженериках нужно сделать к: 18:30 5.4.2022 г.
2. Пройти первое собеседование на java-developer нужно сделать к: 16:0 30.6.2022 г.
2

2
Ваш список дел:
1. Дополнить конспект о дженериках нужно сделать к: 18:30 5.4.2022 г.
```