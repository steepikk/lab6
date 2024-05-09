package input;


import data.*;
import exceptions.*;

import java.util.NoSuchElementException;

/**
 * Класс для ввода дракона
 *
 * @author steepikk
 */

public class DragonInput {
    private final Console console;

    public DragonInput(Console console) {
        this.console = console;
    }

    /**
     * Создает дракона
     */
    public Dragon make() throws IncorrectScriptException, InvalidFormException {
        var dragon = new Dragon(1, inputDragonName(), inputCoordinates(), inputAge(), inputDescription(), inputColor(), inputDragonCharacter(), inputPerson());
        if (!dragon.validate()) throw new InvalidFormException();
        return dragon;
    }


    /**
     * Ввод характера дракона
     */
    private DragonCharacter inputDragonCharacter() throws IncorrectScriptException {
        var fileMode = InputSteamer.getFileMode();

        String strdragonCharacter;
        DragonCharacter dragonCharacter;
        while (true) {
            try {
                console.println("Список типов характера дракона - " + DragonCharacter.names());
                console.println("Введите тип характера дракона (или null):");
                console.ps2();

                strdragonCharacter = InputSteamer.getScanner().nextLine().trim();
                if (fileMode) console.println(strdragonCharacter);

                if (strdragonCharacter.isEmpty() || strdragonCharacter.equals("null")) return null;
                dragonCharacter = DragonCharacter.valueOf(strdragonCharacter.toUpperCase());
                break;
            } catch (NoSuchElementException exception) {
                console.printError("Тип дракона не распознан!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (IllegalArgumentException exception) {
                console.printError("Типа дракона нет в списке!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (IllegalStateException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return dragonCharacter;
    }

    /**
     * Ввод цвета дракона
     */
    private Color inputColor() throws IncorrectScriptException {
        var fileMode = InputSteamer.getFileMode();

        String strColor;
        Color color;
        while (true) {
            try {
                console.println("Список цветов - " + Color.names());
                console.println("Введите цвет:");
                console.ps2();

                strColor = InputSteamer.getScanner().nextLine().trim();
                if (fileMode) console.println(strColor);

                color = Color.valueOf(strColor.toUpperCase());
                break;
            } catch (NoSuchElementException exception) {
                console.printError("Цвет не распознан!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (IllegalArgumentException exception) {
                console.printError("Цвета нет в списке!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (IllegalStateException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return color;
    }

    /**
     * Ввод координат
     */
    private String inputDescription() throws IncorrectScriptException {
        String description;
        var fileMode = InputSteamer.getFileMode();
        while (true) {
            try {
                console.println("Введите комментарий дракона:");
                console.ps2();

                description = InputSteamer.getScanner().nextLine().trim();
                if (fileMode) console.println(description);
                if (description.equals("")) throw new MustBeNotEmptyException();
                break;
            } catch (NoSuchElementException exception) {
                console.printError("Описание не распознано!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (MustBeNotEmptyException exception) {
                console.printError("Описание не может быть пустым!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (IllegalStateException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }

        return description;
    }

    /**
     * Ввод возраста дракона
     */
    private long inputAge() throws IncorrectScriptException {
        var fileMode = InputSteamer.getFileMode();
        long age;
        while (true) {
            try {
                console.println("Введите возраст дракона:");
                console.ps2();

                var strAge = InputSteamer.getScanner().nextLine().trim();
                if (fileMode) console.println(strAge);

                age = Long.parseLong(strAge);
                long MIN_AGE = 0;
                if (age <= MIN_AGE) throw new InvalidRangeException();
                break;
            } catch (NoSuchElementException exception) {
                console.printError("Возраст дракона не распознан!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (InvalidRangeException exception) {
                console.printError("Возраст дракона должен быть больше нуля!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (NumberFormatException exception) {
                console.printError("Возраст дракона должен быть представлен числом!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return age;
    }

    /**
     * Ввод координат
     */
    private Coordinates inputCoordinates() throws IncorrectScriptException, InvalidFormException {
        Coordinates coordinates = new Coordinates(inputX(), inputY());
        if (!coordinates.validate()) throw new InvalidFormException();
        return coordinates;
    }

    /**
     * Ввод y координаты
     */
    private Integer inputY() throws IncorrectScriptException {
        Integer y;
        var fileMode = InputSteamer.getFileMode();
        while (true) {
            try {
                console.println("Введите координату Y:");
                console.ps2();
                var strY = InputSteamer.getScanner().nextLine().trim();
                if (fileMode) console.println(strY);
                y = Integer.parseInt(strY);
                if (y > 244) throw new InvalidRangeException("Значение Y должно быть меньше 244");
                break;
            } catch (NoSuchElementException exception) {
                console.printError("Координата Y не распознана!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (NumberFormatException exception) {
                console.printError("Координата Y должна быть представлена целым числом!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (IllegalStateException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            } catch (InvalidRangeException exception) {
                console.printError(exception.getMessage());
            }
        }
        return y;
    }

    /**
     * Ввод х координаты
     */
    private double inputX() throws IncorrectScriptException {
        double x;
        var fileMode = InputSteamer.getFileMode();
        while (true) {
            try {
                console.println("Введите координату X:");
                console.ps2();
                var strX = InputSteamer.getScanner().nextLine().trim();
                if (fileMode) console.println(strX);
                x = Double.parseDouble(strX);
                if (x > 826) throw new InvalidRangeException("Значение X должно быть меньше 826");
                break;
            } catch (NoSuchElementException exception) {
                console.printError("Координата X не распознана!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (NumberFormatException exception) {
                console.printError("Координата X должна быть представлена числом с плавающей точкой!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (IllegalStateException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            } catch (InvalidRangeException exception) {
                console.printError(exception.getMessage());
            }
        }
        return x;
    }


    /**
     * Ввод названия
     */
    private String inputDragonName() throws IncorrectScriptException {
        String name;
        var fileMode = InputSteamer.getFileMode();
        while (true) {
            try {
                console.println("Введите название дракона:");
                console.ps2();

                name = InputSteamer.getScanner().nextLine().trim();
                if (fileMode) console.println(name);
                if (name.equals("")) throw new MustBeNotEmptyException();
                break;
            } catch (NoSuchElementException exception) {
                console.printError("Название не распознано!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (MustBeNotEmptyException exception) {
                console.printError("Название не может быть пустым!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (IllegalStateException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }

        return name;
    }

    /**
     * Создание killer
     */
    private Person inputPerson() throws IncorrectScriptException, InvalidFormException {
        console.println("Введите no, чтобы не создавать владельца. Введите yes, чтобы создать владельца");
        while (true) {
            String inputStr = InputSteamer.getScanner().nextLine().trim();
            if (inputStr.equals("no"))
                return null;
            else if (inputStr.equals("yes")) {
                Person killer = new Person(
                        inputPersonName(),
                        inputWeight(),
                        inputEyeColor(),
                        inputHairColor());
                if (!killer.validate()) throw new InvalidFormException();
                return killer;
            } else
                console.println("Попробуйте еще раз");
        }
    }

    /**
     * Ввод цвета дракона
     */
    private Color inputEyeColor() throws IncorrectScriptException {
        var fileMode = InputSteamer.getFileMode();

        String strColor;
        Color color;
        while (true) {
            try {
                console.println("Список цветов глаз - " + Color.names());
                console.println("Введите цвет глаз:");
                console.ps2();

                strColor = InputSteamer.getScanner().nextLine().trim();
                if (fileMode) console.println(strColor);

                color = Color.valueOf(strColor.toUpperCase());
                break;
            } catch (NoSuchElementException exception) {
                console.printError("Цвет глаз не распознан!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (IllegalArgumentException exception) {
                console.printError("Цвета глаз нет в списке!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (IllegalStateException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return color;
    }

    /**
     * Ввод цвета дракона
     */
    private Color inputHairColor() throws IncorrectScriptException {
        var fileMode = InputSteamer.getFileMode();

        String strColor;
        Color color;
        while (true) {
            try {
                console.println("Список цветов волос - " + Color.names());
                console.println("Введите цвет волос:");
                console.ps2();

                strColor = InputSteamer.getScanner().nextLine().trim();
                if (fileMode) console.println(strColor);

                color = Color.valueOf(strColor.toUpperCase());
                break;
            } catch (NoSuchElementException exception) {
                console.printError("Цвет волос не распознан!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (IllegalArgumentException exception) {
                console.printError("Цвета волос нет в списке!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (IllegalStateException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return color;
    }

    /**
     * Ввод имя killer
     */
    private String inputPersonName() throws IncorrectScriptException {
        String name;
        var fileMode = InputSteamer.getFileMode();
        while (true) {
            try {
                console.println("Введите имя человека:");
                console.ps2();

                name = InputSteamer.getScanner().nextLine().trim();
                if (fileMode) console.println(name);
                if (name.equals("")) throw new MustBeNotEmptyException();
                break;
            } catch (NoSuchElementException exception) {
                console.printError("Имя не распознано!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (MustBeNotEmptyException exception) {
                console.printError("Имя не может быть пустым!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (IllegalStateException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }

        return name;
    }

    /**
     * Ввод веса
     */
    private long inputWeight() throws IncorrectScriptException {
        var fileMode = InputSteamer.getFileMode();
        long weight;
        while (true) {
            try {
                console.println("Введите вес:");
                console.ps2();

                var strWeight = InputSteamer.getScanner().nextLine().trim();
                if (fileMode) console.println(strWeight);

                weight = Long.parseLong(strWeight);
                long MIN_WEIGHT = 0;
                if (weight <= MIN_WEIGHT) throw new InvalidRangeException();
                break;
            } catch (NoSuchElementException exception) {
                console.printError("Вес не распознан!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (InvalidRangeException exception) {
                console.printError("Вес должен быть больше нуля!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (NumberFormatException exception) {
                console.printError("Вес должен быть представлен числом!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return weight;
    }


}
