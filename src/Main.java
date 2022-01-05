import model.Task;

public class Main {
    public static void main(String[] args) {
        System.out.println("Пришло время практики!");
        Manager manager = new Manager();
        final Task simpleTaskParam = new Task("Простая задача", 1);
        final Task simpleTask = manager.createTask(simpleTaskParam);
//        if (simpleTask.getId() != simpleTaskParam.getId().intValue()) {
            if (!simpleTask.equals(simpleTaskParam)) {
            System.out.println("не работает метод создать задачу");
        } else {
                System.out.println("Успешно работает метод создать задачу");
            }



        /*
        final Менеджер менеджер = new Менеджер();
        final Задача простаяЗадачаUI = new Задача("Простая задача", 1);

        final Задача задача = менеджер.создатьЗадачу(простаяЗадачаUI);
        задача.setНазвание("Новое название");
        задача.setСтатуc("Новое название");
        менеджер.обновитьЗадачу(задача);

        простаяЗадачаUI.setСтатуc(DONE);
        final Задача простаяЗадача = менеджер.найтиЗадачуПоИД(простаяЗадачаUI.getИд());
//        if (!простаяЗадача.equals(простаяЗадачаПараметр)) {
        if (!простаяЗадача.getСтатуc().equals(NEW)) {
            System.out.println("не работает метод создатьЗадачу");
        }
        */
    }
}
