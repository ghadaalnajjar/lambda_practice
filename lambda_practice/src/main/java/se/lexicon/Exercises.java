package se.lexicon;

import se.lexicon.data.DataStorage;
import se.lexicon.model.Gender;
import se.lexicon.model.Person;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Exercises {

    private final static DataStorage storage = DataStorage.INSTANCE;

    /*
       1.	Find everyone that has firstName: “Erik” using findMany().
    */
    public static void exercise1(String message) {
        System.out.println(message);
        //Write your code here

        Predicate<Person> filterNameErik = (Person person) -> person.getFirstName().equalsIgnoreCase("Erik");
        List<Person> personListWithErikName = storage.findMany(filterNameErik);
        personListWithErikName.forEach(System.out::println);

        System.out.println("----------------------");
    }

    /*
        2.	Find all females in the collection using findMany().
     */
    public static void exercise2(String message) {
        System.out.println(message);
        //Write your code here

        //storage.findMany(person -> person.getGender() == Gender.FEMALE).forEach(System.out::println);
        Predicate<Person> filterFemales = (Person person) -> person.getGender() == Gender.FEMALE;
        List<Person> personListWithFemales = storage.findMany(filterFemales);
        personListWithFemales.forEach(System.out::println);

        System.out.println("----------------------");
    }

    /*
        3.	Find all who are born after (and including) 2000-01-01 using findMany().
     */
    public static void exercise3(String message) {
        System.out.println(message);
        //Write your code here

        Predicate<Person> filterAfter = (Person person) -> person.getBirthDate().isAfter(LocalDate.parse("2000-01-01"));
        List<Person> personListWithAfter = storage.findMany(filterAfter);
        personListWithAfter.forEach(System.out::println);

        System.out.println("----------------------");
    }

    /*
        4.	Find the Person that has an id of 123 using findOne().
    */
    public static void exercise4(String message) {
        System.out.println(message);
        //Write your code here

        System.out.println(storage.findOne(person -> person.getId() == 123));

        System.out.println("----------------------");

    }

    /*
        5.	Find the Person that has an id of 456 and convert to String with following content:
            “Name: Nisse Nilsson born 1999-09-09”. Use findOneAndMapToString().
     */
    public static void exercise5(String message) {
        System.out.println(message);
        //Write your code here

        Predicate<Person> filterById = p -> p.getId() == 456;
        Function<Person, String> personSToStringFunction = person -> "Name: " + person.getFirstName() + " " + person.getLastName() + " born " + person.getBirthDate();
        List<String> strings = storage.findManyAndMapEachToString(filterById, personSToStringFunction);
        strings.forEach(System.out::println);

        System.out.println("----------------------");
    }

    /*
        6.	Find all male people whose names start with “E” and convert each to a String using findManyAndMapEachToString().
     */
    public static void exercise6(String message) {
        System.out.println(message);
        //Write your code here

        Predicate<Person> filterByMaleAndStartWithE = p -> p.getGender() == Gender.MALE && p.getFirstName().startsWith("E");
        Function<Person, String> personSToStringFunction = person -> "Name: " + person.getFirstName();
        List<String> strings = storage.findManyAndMapEachToString(filterByMaleAndStartWithE, personSToStringFunction);
        strings.forEach(System.out::println);

        System.out.println("----------------------");
    }

    /*
        7.	Find all people who are below age of 10 and convert them to a String like this:
            “Olle Svensson 9 years”. Use findManyAndMapEachToString() method.
     */
    public static void exercise7(String message) {
        System.out.println(message);
        //Write your code here

        LocalDateTime localDateTime = LocalDateTime.now();
        Predicate<Person> filterByBelowAge = p -> localDateTime.getYear() - p.getBirthDate().getYear() < 10;
        Function<Person, String> personSToStringFunction = person -> "Name: " + person.getFirstName() + " " + person.getLastName() + " " + (localDateTime.getYear() - person.getBirthDate().getYear()) + " years";
        List<String> strings = storage.findManyAndMapEachToString(filterByBelowAge, personSToStringFunction);
        strings.forEach(System.out::println);

        System.out.println("----------------------");
    }

    /*
        8.	Using findAndDo() print out all people with firstName “Ulf”.
     */
    public static void exercise8(String message) {
        System.out.println(message);
        //Write your code here

        Predicate<Person> filter = person -> person.getFirstName().equalsIgnoreCase("Ulf");
        Consumer<Person> consumer = c -> System.out.println(c.getFirstName() + " " + c.getLastName() + " " + c.getGender() + " " + c.getBirthDate());
        storage.findAndDo(filter, consumer);

        System.out.println("----------------------");
    }

    /*
        9.	Using findAndDo() print out everyone who have their lastName contain their firstName.
     */
    public static void exercise9(String message) {
        System.out.println(message);
        //Write your code here

        Predicate<Person> filter = person -> person.getLastName().contains(person.getFirstName());
        Consumer<Person> consumer = c -> System.out.println(c.getFirstName() + " " + c.getLastName() + " " + c.getGender() + " " + c.getBirthDate());
        storage.findAndDo(filter, consumer);

        System.out.println("----------------------");
    }

    /*
        10.	Using findAndDo() print out the firstName and lastName of everyone whose firstName is a palindrome.
     */
    public static void exercise10(String message) {
        System.out.println(message);
        //Write your code here

        Predicate<Person> filter = person -> {
            int startIndex = 0;
            int endIndex = person.getFirstName().length() - 1;
            while (startIndex < endIndex) {
                if (person.getFirstName().toLowerCase().charAt(startIndex) != person.getFirstName().toLowerCase().charAt(endIndex))
                    return false;
                startIndex++;
                endIndex--;
            }
            return true;
        };
        Consumer<Person> consumer = c -> System.out.println(c.getFirstName() + " " + c.getLastName());
        storage.findAndDo(filter, consumer);

        System.out.println("----------------------");
    }

    /*
        11.	Using findAndSort() find everyone whose firstName starts with A sorted by birthdate.
     */
    public static void exercise11(String message) {
        System.out.println(message);
        //Write your code here
        Predicate<Person> filterNameStartWithA = person -> person.getFirstName().startsWith("A");
        Comparator<Person> personListSortBirthDate = Comparator.comparing(Person::getBirthDate);
        List<Person> personListWithNameStartWithA = storage.findAndSort(filterNameStartWithA, personListSortBirthDate);
        personListWithNameStartWithA.forEach(System.out::println);

        System.out.println("----------------------");
    }


    /*
        12.	Using findAndSort() find everyone born before 1950 sorted reversed by lastest to earliest.
     */
    public static void exercise12(String message) {
        System.out.println(message);
        //Write your code here

        Predicate<Person> filterBeforeDate = person -> person.getBirthDate().isBefore(LocalDate.parse("1950-01-01"));
        Comparator<Person> personListSortBirthDate = Comparator.comparing(Person::getBirthDate);
        List<Person> personListWithBefore = storage.findAndSort(filterBeforeDate, personListSortBirthDate.reversed());
        personListWithBefore.forEach(System.out::println);

        System.out.println("----------------------");
    }


    /*
        13.	Using findAndSort() find everyone sorted in following order: lastName > firstName > birthDate.
     */
    public static void exercise13(String message) {
        System.out.println(message);
        //Write your code here

        Comparator<Person> personListSort = Comparator.comparing(Person::getLastName).thenComparing(Person::getFirstName).thenComparing(Person::getBirthDate);
        List<Person> personList = storage.findAndSort(personListSort);
        personList.forEach(System.out::println);

        System.out.println("----------------------");
    }
}
