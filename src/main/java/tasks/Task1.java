package tasks;

import common.Person;
import common.PersonService;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/*
Задача 1
Метод на входе принимает List<Integer> id людей, ходит за ними в сервис
(он выдает несортированный Set<Person>, внутренняя работа сервиса неизвестна)
нужно их отсортировать в том же порядке, что и переданные id.
Оценить асимпотику работы
 */
public class Task1 {

  private final PersonService personService;

  public Task1(PersonService personService) {
    this.personService = personService;
  }

  public List<Person> findOrderedPersons(List<Integer> personIds) {
    Set<Person> persons = personService.findPersons(personIds);

    // Мапа нужна, чтобы быстро доставать индекс id-шки (за O(1))
    Map<Integer, Integer> idToIndex = IntStream.range(0, personIds.size())
        .boxed()
        .collect(Collectors.toMap(personIds::get, Function.identity()));

    // Сортируем стрим по индексам в исходном листе id-шек
    // Ассимптотика O(n*log(n))
    return persons.stream()
        .sorted(Comparator.comparingInt(person -> idToIndex.get(person.getId())))
        .toList();
  }
}
