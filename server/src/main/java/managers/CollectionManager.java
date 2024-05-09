package managers;

import data.Dragon;
import data.DragonCharacter;
import main.App;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Класс для контроля коллекции
 *
 * @author steepikk
 */
public class CollectionManager {
    private HashSet<Dragon> collection = new HashSet<Dragon>();
    private LocalDateTime lastInitTime;
    private LocalDateTime lastSaveTime;
    private final DumpManager dumpManager;


    public CollectionManager(DumpManager dumpManager) {
        this.lastInitTime = null;
        this.lastSaveTime = null;
        this.dumpManager = dumpManager;

        loadCollection();
    }

    /**
     * Валидирует всех значений дракона
     */
    public void validateAll() {
        collection.stream()
                .filter(dragon -> !dragon.validate())
                .forEach(dragon -> App.logger.info("A dragon with id = " + dragon.getId()+ " has invalid fields."));
        App.logger.info("! Uploaded dragons are valid.");
    }

    /**
     * @return Коллекия
     */
    public HashSet<Dragon> getCollection() {
        return collection;
    }

    /**
     * @return Дата и время последней инициализации
     */
    public LocalDateTime getLastInitTime() {
        return lastInitTime;
    }

    /**
     * @return Дата и время последнего сохранения
     */
    public LocalDateTime getLastSaveTime() {
        return lastSaveTime;
    }

    /**
     * @return Тип коллекции
     */
    public String getType() {
        return collection.getClass().getName();
    }

    /**
     * @return Размер коллекции
     */
    public int getSize() {
        return collection.size();
    }

    /**
     * @return Последний элемент коллекции
     */
    public Dragon getLast() {
        if (collection.isEmpty()) return null;
        return collection.stream().reduce((one, two) -> two).get();
    }

    /**
     * Получает элемент коллекции по его айди
     *
     * @param id
     * @return элемент
     */
    public Dragon getById(int id) {
        return collection.stream()
                .filter(element -> element.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * @param id ID элемента.
     * @return Проверяет, существует ли элемент с таким ID.
     */
    public boolean checkExist(int id) {
        return getById(id) != null;
    }

    /**
     * @return Отсортированная коллекция.
     */
    public List<Dragon> sorted() {
        return new ArrayList<>(collection)
                .stream()
                .collect(Collectors.toList());
    }

    /**
     * Проверяет, что данный элемент самый большой в коллекции
     *
     * @param dragon элемент
     */
    public boolean greaterThanAll(Dragon dragon) {
        return collection.stream().allMatch(element -> dragon.compareTo(element) < 0);
    }

    /**
     * Удаляет из коллекции все элементы больше заданного
     *
     * @param dragon
     */
    public void removeGreater(Dragon dragon) {
        collection = collection.stream().filter(element -> dragon.compareTo(element) > 0).collect(Collectors.toCollection(HashSet::new));
    }

    /**
     * @return Коллекцию по возрастанию
     */
    public List<Dragon> printAscending() {
        Set<Dragon> tmpSet = new  HashSet<>(collection.stream().sorted().collect(Collectors.toList()));
        List<Dragon> tmpList = new ArrayList<Dragon>(tmpSet);
        return tmpList;
    }

    /**
     * @param character Характер дракона
     * @return Отфильтрованную коллекцию, где характер дракона меньше, чем указанный в подстроке
     */
    public List<Dragon> listLessThanCharacter(String character) {
        DragonCharacter dragonCharacter = DragonCharacter.valueOf(character);
        return collection.stream()
                .filter(element -> element.getCharacter().compareTo(dragonCharacter) < 0)
                .collect(Collectors.toList());
    }

    /**
     * @param age Характер дракона
     * @return Количество драконов возраст, которых меньше заданного в подстроке
     */
    public Integer countLessThenAge(String age) {
        Integer constantAge = Integer.parseInt(age);
        return (int) collection.stream()
                .filter(element -> element.getAge() < constantAge)
                .count();
    }


    /**
     * Добавляет элемент в коллекцию, обновляя id
     *
     * @param element Элемент для добавления
     */
    public int addToCollection(Dragon element) {
        var maxId = collection.stream().filter(Objects::nonNull)
                .map(Dragon::getId)
                .mapToInt(Integer::intValue).max().orElse(0);
        var newId = maxId + 1;
        collection.add(element.copy(newId));
        return newId;
    }

    /**
     * Удаляет элемент из коллекции.
     * @param id ID элемента для удаления.
     */
    public void remove(int id) {
        collection.removeIf(product -> product.getId() == id);
    }

    /**
     * Удаляет элемент коллекции
     *
     * @param element элемент
     */
    public void removeFromCollection(Dragon element) {
        collection.remove(element);
    }

    /**
     * Очищает коллекцию
     */
    public void clearCollection() {
        collection.clear();
    }

    /**
     * Сохраняет коллекцию в файл
     */
    public void saveCollection() {
        dumpManager.writeCollection(collection);
        lastSaveTime = LocalDateTime.now();
    }

    /**
     * Загружает коллекцию из файла.
     */
    private void loadCollection() {
        collection = (HashSet<Dragon>) dumpManager.readCollection();
        lastInitTime = LocalDateTime.now();
    }

    @Override
    public String toString() {
        if (collection.isEmpty()) return "Коллекция пуста!";
        var last = getLast();

        StringBuilder info = new StringBuilder();
        for (Dragon dragon : collection) {
            info.append(dragon);
            if (dragon != last) info.append("\n\n");
        }
        return info.toString();
    }
}
