package tasks;

import common.Person;
import common.PersonService;
import common.Task;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/*
Задача 1
Метод на входе принимает List<Integer> id людей, ходит за ними в сервис
(он выдает несортированный Set<Person>, внутренняя работа сервиса неизвестна)
нужно их отсортировать в том же порядке, что и переданные id.
Оценить асимпотику работы
 */

// Такая задача всплывает довольно часто. У нас есть запрос, например,  дай мне мои вакансии
// С пагинацией, с различным данными, для которых нужны разные джойны (которые могут быть one to many)
// И вопрос - как правильно написать пагинацию, one to many множит строки (хибернет потом свернет, вы еще изучите, но лимит с оффсетом на базе работают)
// Решение - сначала получить айдишки более простым запросом (который без джойнов one-to-many) а потом по айдишкам уже получить вакансии, обогатить данными и т.д.
// Но база выдаст их в рандомном порядке ( IN не гарантирует порядок) поэтому дальше обычно следует такой код
public class Task1 implements Task {

  // Вроде все кто делал ДЗ дошли до такого варианта в итоге
  // Из нюансов - можно использовать ссылки на методы там где это нужно
  // (банально меньше букв, читаемо, не надо заводить внутреннюю переменную, которая может конфликтовать с внешней)
  // Асимптотика O(n)
  private List<Person> findOrderedPersons(List<Integer> personIds) {
    Map<Integer, Person> personMap = PersonService.findPersons(personIds).stream()
      .collect(Collectors.toMap(Person::getId, Function.identity()));
    return personIds.stream()
        .map(personMap::get)
        .filter(Objects::nonNull) // Интересный момент, вроде никто об это не думал. Мы передаем id и ходим в сервис за поиском персон
                                  // Но надо понимать что персона может и не найтись.
                                  // И странно если findOrderedPersons среди нормальных еще и налл выдаст
        .collect(Collectors.toList());
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
