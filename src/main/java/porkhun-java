import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Перевірка числа
        System.out.print("Enter a number: ");
        int number = scanner.nextInt();
        if (number > 7) {
            System.out.println("Hello");
        }

        // Перевірка імені
        System.out.print("Enter a name: ");
        scanner.nextLine();
        String name = scanner.nextLine();
        if ("John".equals(name)) {
            System.out.println("Hello, John");
        } else {
            System.out.println("There is no such name");
        }

        // Масив і кратні 3
        int[] array = {3, 5, 9, 12, 14, 15, 20}; 
        System.out.println("Numbers divisible by 3:");
        for (int num : array) {
            if (num % 3 == 0) {
                System.out.println(num);
            }
        }

        scanner.close();
    }
}
