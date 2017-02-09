/**
 * @author ADKN
 * @version 02 Mar 2016, 10:04 PM
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;


public class SortClass {

    public static void main(String[] args) {
        for (int i = 0; i < 1 ; i++) {
            try {
                pseudoMain(12000);
            } catch (FileNotFoundException e) {
                System.out.println(e);
            }
        }
    }

    public static void reverse(int[] data) {
        int left = 0;
        int right = data.length - 1;

        while( left < right ) {
            // swap the values at the left and right indices
            int temp = data[left];
            data[left] = data[right];
            data[right] = temp;

            // move the left and right index pointers in toward the center
            left++;
            right--;
        }
    }

    public static void pseudoMain(int param) throws FileNotFoundException {
        // random data
//        Random r = new Random();
//        int N = param;
//        int[] A = new int[N];
//        for (int i = 0; i < N; i++) {
//            A[i] = r.nextInt();
//        }

        //Ordered/Reverse
        Random r = new Random();
        int N = param;
        int[] A = new int[N];
        for (int i = 0; i < N; i++) {
            A[i] = r.nextInt();
        }
        Arrays.sort(A);
//        reverse(A);


        File temp = new File("src/temp.txt");
        File data = new File("src/RandomData.txt");
        if (data.exists()) {
            Scanner sc = new Scanner(data);
            PrintStream tempFile = new PrintStream(temp);
            while (sc.hasNextLine()) {
                tempFile.println(sc.nextLine());
            }
            //Copy contents of data trials to temp file
        }

        PrintStream output = new PrintStream(data);
        Scanner sc = new Scanner(temp);
        while (sc.hasNextLine()) {
            output.println(sc.nextLine());
        }

        // clone so that each method has to sort the same data
        int[] A_clone = A.clone();

//        // Selection Sort
        long st = System.nanoTime();
        selectionSort(A);
        long se = System.nanoTime() - st;
        output.println(se);

        // Insertion Sort
        A = A_clone.clone();
        st = System.nanoTime();
        insertionSort(A);
        se = System.nanoTime() - st;
        output.println(se);

        // Bubble Sort
        A = A_clone.clone();
        st = System.nanoTime();
        bubbleSort(A);
        se = System.nanoTime() - st;
        output.println(se);

        // Merge Sort (in place)
        A = A_clone.clone();
        st = System.nanoTime();
        mergeSort(A, 0, A.length-1);
        se = System.nanoTime() - st;
        output.println(se);

        // Merge Sort (extra memory)
        A = A_clone.clone();
        st = System.nanoTime();
        mergeSort2(A);
        se = System.nanoTime() - st;
        output.println(se);

        // Quick Sort
        A = A_clone.clone();
        st = System.nanoTime();
        quickSort(A, 0, A.length-1);
        se = System.nanoTime() - st;
        output.println(se);

    }

    public static void selectionSort(int[] arr) {
        int i, j, minIndex, tmp;
        int n = arr.length;
        for (i = 0; i < n - 1; i++) {
            minIndex = i;
            for (j = i + 1; j < n; j++)
                if (arr[j] < arr[minIndex])
                    minIndex = j;
            if (minIndex != i) {
                tmp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = tmp;
            }
        }
    }

    public static void bubbleSort(int[] arr) {
        boolean swapped = true;
        int j = 0;
        int tmp;
        while (swapped) {
            swapped = false;
            j++;
            for (int i=0; i < arr.length-j; i++){
                if (arr[i] > arr[i + 1]){
                    tmp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = tmp;
                    swapped = true;
                }
            }
        }
    }
    public static void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int valueToSort = arr[i];
            int j = i;
            while (j > 0 && arr[j - 1] > valueToSort) {
                arr[j] = arr[j - 1];
                j--;
            }
            arr[j] = valueToSort;
        }
    }
    public static void mergeSort(int array[],int lo, int n) {
        int low = lo;
        int high = n;
        if (low >= high) {
            return;
        }

        int middle = (low + high) / 2;

        mergeSort(array, low, middle);
        mergeSort(array, middle + 1, high);

        int end_low = middle;
        int start_high = middle + 1;

        while ((lo <= end_low) && (start_high <= high)) {
            if (array[low] < array[start_high]) {
                low++;
            } else {
                int Temp = array[start_high];
                for (int k = start_high- 1; k >= low; k--) {
                    array[k+1] = array[k];
                }
                array[low] = Temp;
                low++;
                end_low++;
                start_high++;
            }
        }
    }
    public static int partition(int arr[], int left, int right) {
        int i = left, j = right;
        int tmp;
        int pivot = arr[(left + right) / 2];

        while (i <= j) {
            while (arr[i] < pivot)
                i++;
            while (arr[j] > pivot)
                j--;
            if (i <= j) {
                tmp = arr[i];
                arr[i] = arr[j];
                arr[j] = tmp;
                i++;
                j--;
            }
        };

        return i;
    }

    public static void quickSort(int arr[], int left, int right) {
        int index = partition(arr, left, right);
        if (left < index - 1)
            quickSort(arr, left, index - 1);
        if (index < right)
            quickSort(arr, index, right);
    }

    public static int[] mergeSort2(int [] list) {
        if (list.length <= 1) {
            return list;
        }
        int[] first = new int[list.length / 2];
        int[] second = new int[list.length - first.length];
        System.arraycopy(list, 0, first, 0, first.length);
        System.arraycopy(list, first.length, second, 0, second.length);

        mergeSort2(first);
        mergeSort2(second);

        merge2(first, second, list);
        return list;
    }

    private static void merge2(int[] first, int[] second, int [] result) {
        int iFirst = 0;
        int iSecond = 0;
        int j = 0;

        while (iFirst < first.length && iSecond < second.length) {
            if (first[iFirst] < second[iSecond]) {
                result[j] = first[iFirst];
                iFirst++;
            } else {
                result[j] = second[iSecond];
                iSecond++;
            }
            j++;
        }

        System.arraycopy(first, iFirst, result, j, first.length - iFirst);
        System.arraycopy(second, iSecond, result, j, second.length - iSecond);
    }
}
