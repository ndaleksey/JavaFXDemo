import java.util.ArrayList;

/**
 * Created by Shishkov A.V. on 18.07.2017.
 */
public class Test {
    private static final String SPACE = " ";

    /**
     * @param args
     */
    public static void main(String[] args) {

        Thread thread = new Thread(() -> incrementation());

        thread.start();

        for(int i = 0; i < 5; i++)
        {
            System.out.println("Курица");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Конец программы");
    }

    private static void incrementation(){
        for(int i = 0; i < 10; i++)
        {
            System.out.println("Яйцо");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void displayArray(int[] array) {
        for (int i : array) {
            System.out.println(i);
        }
    }

    private static int[] createArray() {
        int[] array = new int[5];

        for (int i : array) {
            i = 1;
        }

        return array;
    }

    private static void displayList(ArrayList<int[]> list) {
        for (int[] m : list) {
            for (int n : m) {
                System.out.println(n);
            }
        }
    }


    private static ArrayList<int[]> createList() {
        ArrayList<int[]> listMas = new ArrayList<int[]>();
        int[] m1 = new int[5];
        int[] m2 = new int[2];
        int[] m3 = new int[0];

        listMas.add(m1);
        listMas.add(m2);
        listMas.add(m3);

        for (int[] m : listMas)
            for (int n : m) {
                n = 123;
            }
        return listMas;
    }

    private static void outPyramide(int n) {
        for (int i = 0; i < n; i++) {
            System.out.println(getString(i, n));
        }
    }

    private static String getString(int i, int n) {
        String edge = getEdge(n - i - 1);
        String middle = getMiddle(i + 1);

        return edge + middle + edge;
    }

    private static String getMiddle(int level) {
        StringBuilder s = new StringBuilder();

        for (int i = 1; i <= level; i++)
            s.append("X" + SPACE);

        return s.delete(s.length() - 1, s.length()).toString();
    }

    private static String getEdge(int n) {
        StringBuilder s = new StringBuilder();

        for (int i = 0; i < n; i++)
            s.append(SPACE);

        return s.toString();
    }
}
