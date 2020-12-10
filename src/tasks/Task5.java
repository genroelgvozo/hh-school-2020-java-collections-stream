package tasks;

import common.ApiPersonDto;
import common.Person;
import common.Task;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
Задача 4
Список персон класса Person необходимо сконвертировать в список ApiPersonDto
(предположим, что это некоторый внешний формат)
Конвертер для одной персоны уже предоставлен
FYI - DTO = Data Transfer Object - распространенный паттерн, можно погуглить
 */
/*
Задача 5
Расширим предыдущую задачу
Есть список персон, и словарь сопоставляющий id каждой персоны и id региона
Необходимо выдать список персон ApiPersonDto, с правильно проставленными areaId
Конвертер одной персоны дополнен!
 */
public class Task5 implements Task {

  // !!! Редактируйте этот метод !!!
  // точно также как и в предыдущей с добавлением map
  private List<ApiPersonDto> convert(List<Person> persons, Map<Integer, Integer> personAreaIds) {

    return persons.stream().map(person -> convert(person, personAreaIds.get(person.getId())))
            .collect(Collectors.toList());
  }

  private static ApiPersonDto convert(Person person, Integer areaId) {
    ApiPersonDto dto = new ApiPersonDto();
    dto.setCreated(person.getCreatedAt().toEpochMilli());
    dto.setId(person.getId().toString());
    dto.setName(person.getFirstName());
    dto.setAreaId(areaId);
    return dto;
  }

  @Override
  public boolean check() {
    Person person1 = new Person(1, "Name", Instant.now());
    Person person2 = new Person(2, "Name", Instant.now());
    Map<Integer, Integer> personAreaIds = Map.of(1, 1, 2, 2);
    return List.of(convert(person1, 1), convert(person2, 2))
        .equals(convert(List.of(person1, person2), personAreaIds));
  }
}
