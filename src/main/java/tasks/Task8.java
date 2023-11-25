package tasks;

import common.Person;

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
public class Task8 {

  //Не хотим выдывать апи нашу фальшивую персону, поэтому конвертим начиная со второй
  // 1) Плохая практика удалять объекты из внешних коллекций, поэтому убрал удаление первой персоны
  // 2) Вместо удаления первого объекта, добавил метод skip(1), чтобы пропустить первый объект
  // 3) Также удалил проверку на пустой список, вроде как она и не нужна, код ниже итак вернет пустой список
  public List<String> getNames(List<Person> persons) {
    return persons.stream()
        .skip(1)
        .map(Person::getFirstName)
        .toList();
  }

  //ну и различные имена тоже хочется
  // А тут сама IDEA подсказала
  // 1) Метод distinct не нужен, Set итак содержит уникальные элементы
  // 2) Да и стрим не нужен, можно просто вызвать конструктор HashSet
  public Set<String> getDifferentNames(List<Person> persons) {
    return new HashSet<>(getNames(persons));
  }

  //Для фронтов выдадим полное имя, а то сами не могут
  // 1) В некоторых случаях, после проверок на null, в начало результата могли попасть разделители, решил проблему
  // классом StringJoiner
  // 2) Дважды вызывался метод getSecondName(), второй вызов изменил на getMiddleName()
  public String convertPersonToString(Person person) {
    StringJoiner result = new StringJoiner(" ");
    if (person.getSecondName() != null) {
      result.add(person.getSecondName());
    }

    if (person.getFirstName() != null) {
      result.add(person.getFirstName());
    }

    if (person.getMiddleName() != null) {
      result.add(person.getMiddleName());
    }
    return result.toString();
  }

  // словарь id персоны -> ее имя
  // 1) У начальной мапы был указан initialCapacity = 1, выглядело странно
  // 2) Реализация метода была изменена на стримы, судя по изначальной проверке в коллекции могли быть повторяющиеся
  // персоны, поэтому в коллектор мапы была добавлена mergeFunction
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    return persons.stream()
        .collect(Collectors.toMap(Person::getId, this::convertPersonToString, (p1, p2) -> p1));
  }

  // есть ли совпадающие в двух коллекциях персоны?
  // 1) Если оставлять изначальную логику, то внутри блока if стоит добавить break
  // 2) Есть метод Collections.disjoint(c1, c2), который делает тоже самое (не уверен, может есть какие-то нюансы)
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    return !Collections.disjoint(persons1, persons2);
  }

  //...
  // 1) Непонятно зачем переменная вынесена в область видимости класса, все равно каждый раз обнуляется
  // 2) forEach не нужен, легче вызвать терминальный метод count()
  public long countEven(Stream<Integer> numbers) {
    return numbers
        .filter(num -> num % 2 == 0)
        .count();
  }
}
