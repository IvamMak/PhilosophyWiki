package service;

import java.util.Map;

public class ConsoleHelper {

    public static void printResult(Map<String, Integer> map, int generation) {
        if (map.containsValue(generation)) {
            System.out.println("Статьи с количеством переходов " + generation);
            map.forEach((key, value) -> {
                if (value == generation) System.out.println(key + " " + value);
            });
            System.out.println("***********************************");
        } else
            System.out.println("Статьи с количество переходов " + generation + " не найдены");
    }

    public static void printMaxResult(Map<String, Integer> map) {
        int max = map.values().stream().max(Integer::compareTo).get();
        System.out.println("Максимальное число переходов: " + max);
        printResult(map, max);
    }
}
