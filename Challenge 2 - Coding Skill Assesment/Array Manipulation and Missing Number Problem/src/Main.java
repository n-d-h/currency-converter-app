import java.util.List;

public class Main {

    static List<Integer> list = List.of(3,7,1,2,6,4);
    static Integer[] arr = {3,7,1,2,6,4};
    public static void main(String[] args) {
        // output: 5 (since 5 is the missing number)
        for (int i = 1; i <= list.size() + 1; i++) {
            if (!list.contains(i)) {
                System.out.printf("==> [LIST] missing number: %d\n", i);
                break;
            }
        }

        // output: 5 (since 5 is the missing number)
        for (int i = 1; i <= arr.length + 1; i++) {
            boolean found = false;
            for (int j = 0; j < arr.length; j++) {
                if (arr[j] == i) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.printf("==> [ARRAY] missing number: %d\n", i);
                break;
            }
        }
    }
}