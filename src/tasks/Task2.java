package tasks;

import common.Person;
import common.Task;

import java.time.Instant;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
Задача 2
На вход принимаются две коллекции объектов Person и величина limit
Необходимо объеденить обе коллекции
отсортировать персоны по дате создания и выдать первые limit штук.
 */
public class Task2 implements Task {

  // Асимптотика О(n log n)
  private static List<Person> combineAndSortWithLimit(Collection<Person> persons1,
                                                      Collection<Person> persons2,
                                                      int limit) {
    return Stream.concat(persons1.stream(), persons2.stream()) // Можно так же Stream.of(persons1, persons).flatMap(Collection::stream)
        .sorted(Comparator.comparing(Person::getCreatedAt))
        .limit(limit)
        .collect(Collectors.toList());
  }
  // Было интересное решение через очеред с приоритетом
  // Там асиптотика O(n log limit) и да это побыстрее
  // Но читаемость решения страдает (в том решение правда еще пару лишнего было, его можно было красивее написать)
  // В реальных задачах такое ускорение не сыграет роли вообще
  // (к сожалению у нас не та сфера, я все жду когда я начну прогать суффиксные автоматы и кучи Фибонначи, зачем я это все учил :pepe-sad: :trollface:)
  // И всегда нужно еще учитывать данные - обычно такая задача возникает в таком случае
  // У вас есть страница (новости, история, или еще что-то), в которой есть пагинация, но данные идут из разных источников в одном потоке
  // И таков алгоритм, запрошу я по 50 штук каждых данных, солью в одну, и выберу 50 ранних, т.е. limit это N/k где k маленькое число)) (тут два)
  // и тут уже очередь с приоритетом совсем не дает профита (но идея красивая, мне понравилась, будь мы на ICPC было бы гуд)

  @Override
  public boolean check() {
    Instant time = Instant.now();
    Collection<Person> persons1 = Set.of(
        new Person(1, "Person 1", time),
        new Person(2, "Person 2", time.plusSeconds(1))
    );
    Collection<Person> persons2 = Set.of(
        new Person(3, "Person 3", time.minusSeconds(1)),
        new Person(4, "Person 4", time.plusSeconds(2))
    );
    return combineAndSortWithLimit(persons1, persons2, 3).stream()
        .map(Person::getId)
        .collect(Collectors.toList())
        .equals(List.of(3, 1, 2))
        && combineAndSortWithLimit(persons1, persons2, 5).stream()
        .map(Person::getId)
        .collect(Collectors.toList())
        .equals(List.of(3, 1, 2, 4));
  }
}
