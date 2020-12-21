package tasks;

import common.Person;
import common.Task;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
А теперь о горьком
Всем придется читать код
А некоторым придется читать код, написанный мною
Сочувствую им
Спасите будущих жертв, и исправьте здесь все, что вам не по душе!
P.S. функции тут разные и рабочие (наверное), но вот их понятность и эффективность страдает (аж пришлось писать комменты)
P.P.S Здесь ваши правки желательно прокомментировать (можно на гитхабе в пулл реквесте)
 */
public class Task8 implements Task {

  //Не хотим выдывать апи нашу фальшивую персону, поэтому конвертим начиная со второй
  // Здесь skip разумеется верное решение. Поинты
  // 1) Идеологически менять входные параметры плохо. Пользователь функции не ожидает что будут пропадать персоны
  // 2) Технически - коллекция может быть неизменяемой! Принимая List ничего об изменяемости вы не говорите
  // 3) быстрее, чем удаление первого элемента если там ArrayList
  public List<String> getNames(List<Person> persons) {
    return persons.stream().skip(1).map(Person::getFirstName).collect(Collectors.toList());
  }

  //ну и различные имена тоже хочется
  // Ну здесь IDEA сама подсказывает на излишество)
  public Set<String> getDifferentNames(List<Person> persons) {
    return new HashSet<>(getNames(persons));
  }

  //Для фронтов выдадим полное имя, а то сами не могут
  // Здесь было слишком много технического кода (еще и с багом, мог быть пробел в начале)
  // В бизнес коде хочется видеть в двух слова ЧТО делается, а не КАК
  // По скорости как вы понимаете разницы не будет, а вот в читаемости...
  public String convertPersonToString(Person person) {
    return Stream.of(person.getSecondName(), person.getFirstName(), person.getMiddleName())
        .filter(Objects::nonNull)
        .collect(Collectors.joining(" "));
  }

  // словарь id персоны -> ее имя
  // Ну здесь циклы превращаем в коллект, разница с предыдущими тасками, что намекаем ифом, что могут быть дубликаты
  // В таком случае нужна mergeFunction (об этом догадались не все)
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    return persons.stream().collect(Collectors.toMap(
        Person::getId,
        this::convertPersonToString,
        (a, b) -> a
    ));
  }

  // есть ли совпадающие в двух коллекциях персоны?
  // Во-первых используя contains у HashSet ускоряем до O(n) хоть и жертвуя памятью
  // Во-вторых читаемо
  // Есть вариант через Collections.disjoint, но я бы не юзал, ибо нужно знать стандартную функцию и че она делает
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    return persons1.stream().anyMatch(new HashSet<>(persons2)::contains);
  }

  //Стрим может быть параллельным
  //Ну и никогда не принимайте стрим, это плохо, он может быть закрыт..
  //Но таску я пытался придумать именно про параллельные стримы
  public long countEven(Stream<Integer> numbers) {
    return numbers.filter(num -> num % 2 == 0).count();
  }

  @Override
  public boolean check() {
    System.out.println("Слабо дойти до сюда и исправить Fail этой таски?");
    boolean codeSmellsGood = true;
    boolean reviewerDrunk = true;
    return codeSmellsGood || reviewerDrunk;
  }
}
