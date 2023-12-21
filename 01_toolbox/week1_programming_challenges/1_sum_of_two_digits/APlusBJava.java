import java.util.Scanner;
import static java.lang.System.*;

class APlusBJava {
    static int sumOfTwoDigits(int first_digit, int second_digit) {
        return first_digit + second_digit;
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(in);
        int a = s.nextInt();
        int b = s.nextInt();
        out.println(sumOfTwoDigits(a, b));
    }
}