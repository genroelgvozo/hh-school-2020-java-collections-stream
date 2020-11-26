package tasks;

import common.Area;
import common.Person;
import common.Task;

import java.time.Instant;
import java.util.*;

/*
Имеются
- коллекция персон Collection<Person>
- словарь Map<Integer, Set<Integer>>, сопоставляющий каждой персоне множество id регионов
- коллекция всех регионов Collection<Area>
На выходе хочется получить множество строк вида "Имя - регион". Если у персон регионов несколько, таких строк так же будет несколько
 */
public class Task6 implements Task {

  private Set<String> getPersonDescriptions(Collection<Person> persons,
                                            Map<Integer, Set<Integer>> personAreaIds,
                                            Collection<Area> areas) {
    Map<Integer, String> areasIds = new HashMap<>();
    for (Area area : areas) {
      areasIds.put(area.getId(), area.getName());
    }
    Map<Integer, String> personIds = new HashMap<>();
    for (Person person: persons) {
      personIds.put(person.getId(), person.getFirstName());
    }
    Set<String> answer = new HashSet<>();
    for (Map.Entry<Integer, Set<Integer>> entry: personAreaIds.entrySet()) {
      for (Integer AreaId : entry.getValue()) {
        answer.add(personIds.get(entry.getKey()) + " - " + areasIds.get(AreaId));
      }
    }
    return answer;
  }

  @Override
  public boolean check() {
    List<Person> persons = List.of(
        new Person(1, "Oleg", Instant.now()),
        new Person(2, "Vasya", Instant.now())
    );
    Map<Integer, Set<Integer>> personAreaIds = Map.of(1, Set.of(1, 2), 2, Set.of(2, 3));
    List<Area> areas = List.of(new Area(1, "Moscow"), new Area(2, "Spb"), new Area(3, "Ivanovo"));
    return getPersonDescriptions(persons, personAreaIds, areas)
        .equals(Set.of("Oleg - Moscow", "Oleg - Spb", "Vasya - Spb", "Vasya - Ivanovo"));
  }
}
