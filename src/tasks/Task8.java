package tasks;

import common.Person;
import common.Task;

import java.util.*;
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
  public List<String> getNames(List<Person> persons) {
    return persons.stream()
            .skip(1)
            .map(Person::getFirstName)
            .collect(Collectors.toList());
  }/*нет смысла проверять лист на пустоту, или удалять из него персону под нулевым индексом, можно собрать в поток и просто пропустить.
  если лист пустой, то и поток будет пустым, а метод вернет пустой список*/

  //ну и различные имена тоже хочется
  public Set<String> getDifferentNames(List<Person> persons) {
    return new HashSet<>(getNames(persons));
  }//если нам нужен набор имен различных,не имеет смысла собирать стрим, можно сделать HashSet из List<Person> использовав метод getNames

  //Для фронтов выдадим полное имя, а то сами не могут
  public String convertPersonToString(Person person) {
    return Stream.of(person.getSecondName(),person.getFirstName(),person.getMiddleName()) //порядок: ФИО
            .filter(Objects::nonNull)
            .collect(Collectors.joining(" "));
  }//если мы не обрабатываем ошибку, когда у нас не указано что-либо из ФИО, предполагая, что поступившие данные корректны, тогда можно стримом через joining

  // словарь id персоны -> ее имя
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    Map<Integer, String> map = new HashMap<>(1);
    persons.stream()
            .filter(person -> !map.containsKey(person.getId()))
            .forEach(person -> map.put(person.getId(),
                    convertPersonToString(person)));
    return map;
  }//можно и расширенным for( он же foreach), но стримами код удобнее выглядит

  // есть ли совпадающие в двух коллекциях персоны?
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    return persons1.stream().anyMatch(persons2::contains);
  }//так проще)

  //...
  public long countEven(Stream<Integer> numbers) {
    return numbers.filter(num -> num % 2 == 0).count();
  }//глобальная переменная count не нужна

  @Override
  public boolean check() {
    System.out.println("Слабо дойти до сюда и исправить Fail этой таски?");
    /*Здесь же просто одну из переменных на true поменять -  выдаст Success
    так понимаю, что это оценка будет нам за Task8*/
    boolean codeSmellsGood = false;
    boolean reviewerDrunk = false;
    return codeSmellsGood || reviewerDrunk;
  }
}
