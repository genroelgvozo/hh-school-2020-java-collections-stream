package tasks;

import common.Person;
import common.PersonService;
import common.Task;

import java.util.*;
import java.util.stream.Collectors;

/*
Задача 1
Метод на входе принимает List<Integer> id людей, ходит за ними в сервис
(он выдает несортированный Set<Person>, внутренняя работа сервиса неизвестна)
нужно их отсортировать в том же порядке, что и переданные id.
Оценить асимпотику работы
 */
public class Task1 implements Task {

  // !!! Редактируйте этот метод !!!
  private List<Person> findOrderedPersons(List<Integer> personIds) {
    Set<Person> persons = PersonService.findPersons(personIds);
    // храним ключ - Person_id, Person
    // возможно можно создать map из set напрямую
    Map<Integer, Person> help = new HashMap<>(persons.size());
    // проходим по persons вынимаем id и Person, вставляем в help O(n)
    for(Person person : persons) {
      help.put(person.getId(), person);
    }
    // проходим по personIds находим нужный id в help, добавляем в лист ans
    // проход O(n)
    List<Person> ans = new ArrayList<>();
    for(Integer id : personIds) {
      ans.add(help.get(id));
    }
    return ans;
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
