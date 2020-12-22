package tasks;

import common.Person;
import common.Task;

import java.time.Instant;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/*
Задача 3
Отсортировать коллекцию сначала по фамилии, по имени (при равной фамилии), и по дате создания (при равных фамилии и имени)
 */
public class Task3 implements Task {

  // Задача просто на поиск возможностей Comparator, тут ничего хитрого
  // Были приемы сортировать сначала по createdAt, потом по фамилии и потом по имени ОТДЕЛЬНЫМИ сортировками
  // И это работает только в том случае, если сортировка стабильная (означает что не переставляет элементы равные)
  // Но как видно завязываться на это нет смысла, есть намного проще вариант
  // O(n log n)
  private List<Person> sort(Collection<Person> persons) {
    return persons.stream()
        .sorted(Comparator.comparing(Person::getSecondName)
            .thenComparing(Person::getFirstName)
            .thenComparing(Person::getCreatedAt)
        )
        .collect(Collectors.toList());
  }

  @Override
  public boolean check() {
    Instant time = Instant.now();
    List<Person> persons = List.of(
        new Person(1, "Oleg", "Ivanov", time),
        new Person(2, "Vasya", "Petrov", time),
        new Person(3, "Oleg", "Petrov", time.plusSeconds(1)),
        new Person(4, "Oleg", "Ivanov", time.plusSeconds(1))
    );
    List<Person> sortedPersons = List.of(
        new Person(1, "Oleg", "Ivanov", time),
        new Person(4, "Oleg", "Ivanov", time.plusSeconds(1)),
        new Person(3, "Oleg", "Petrov", time.plusSeconds(1)),
        new Person(2, "Vasya", "Petrov", time)
    );
    return sortedPersons.equals(sort(persons));
  }
}
