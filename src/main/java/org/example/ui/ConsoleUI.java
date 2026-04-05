package org.example.ui;

import org.example.dao.UserDaoImpl;
import org.example.entity.UserEntity;
import org.example.service.UserService;
import org.example.service.UserServiceImpl;

import java.util.List;
import java.util.Scanner;

public class ConsoleUI {

    private final UserService service = new UserServiceImpl(new UserDaoImpl());
    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        while (true) {
            printMenu();
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> createUser();
                case 2 -> showAll();
                case 3 -> findById();
                case 4 -> updateUser();
                case 5 -> deleteUser();
                case 0 -> {
                    System.out.println("Exit");
                    return;
                }
                default -> System.out.println("Invalid choice");
            }
        }
    }

    private void printMenu() {
        System.out.println("""
                1. Create user
                2. Show all users
                3. Find by id
                4. Update user
                5. Delete user
                0. Exit
                """);
    }

    private void createUser() {
        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Age: ");
        int age = Integer.parseInt(scanner.nextLine());

        service.createUser(name, email, age);
    }

    private void showAll() {
        List<UserEntity> users = service.getAllUsers();
        users.forEach(System.out::println);
    }

    private void findById() {
        System.out.print("ID: ");
        Long id = Long.parseLong(scanner.nextLine());

        UserEntity user = service.getUser(id);
        System.out.println(user);
    }

    private void updateUser() {
        System.out.print("ID: ");
        Long id = Long.parseLong(scanner.nextLine());

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Age: ");
        int age = Integer.parseInt(scanner.nextLine());

        service.updateUser(id, name, email, age);
    }

    private void deleteUser() {
        System.out.print("ID: ");
        Long id = Long.parseLong(scanner.nextLine());

        service.deleteUser(id);
    }
}