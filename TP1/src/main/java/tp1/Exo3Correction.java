package tp1;

public class Exo3Correction {
   
        public static int binarySearch(int[] array, int element) {
            if (array == null) {
                throw new NullPointerException("Array must not be null");
            }
            int low = 0;
            int high = array.length - 1;
            // BUG CORRIGÉ: l'original avait while (low < high) → rate l'élément unique ou dernier
            while (low <= high) {
                int mid = (low + high) / 2;
                if (array[mid] == element) {
                    return mid;
                } else if (array[mid] < element) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
            return -1;
        }
    }
