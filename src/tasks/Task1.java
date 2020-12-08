package tasks;

import common.Person;
import common.PersonService;
import common.Task;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/*
Задача 1
Метод на входе принимает List<Integer> id людей, ходит за ними в сервис
(он выдает несортированный Set<Person>, внутренняя работа сервиса неизвестна)
нужно их отсортировать в том же порядке, что и переданные id.
Оценить асимпотику работы
 */

public class Task1 implements Task {

  /*
  Циклом прохожусь по Set<Person> для создания карты - O(n)
  Затем еще раз циклом прохожусь по List<Integer> id для получения
  сортированного значения. С учетом хэша, получатеся O(n).
  Итого: O(n) + O(n) = O(n)
   */
  private List<Person> findOrderedPersons(List<Integer> personIds) {
    Set<Person> persons = PersonService.findPersons(personIds);
    Map<Integer, Object> numberedPersons = new HashMap<>();
    for (Person elem : persons)
      numberedPersons.put(elem.getId(), elem);
    List<Person> sortedPersons = new LinkedList<>();
    for (Integer id : personIds)
      sortedPersons.add((Person) numberedPersons.get(id));
    return sortedPersons;
  }

  @Override
  public boolean check() {
    List<Integer> ids = List.of(1, 2, 3);

    return findOrderedPersons(ids).stream()
        .map(Person::getId)
        .collect(Collectors.toList())
        .equals(ids);
  }

}
