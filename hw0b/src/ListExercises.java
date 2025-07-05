import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ListExercises {

    /** Returns the total sum in a list of integers */
    public static int sum(List<Integer> L) {
        // TODO: Fill in this function.
        return L.stream().reduce(0, (acc, cur) -> acc+cur);
    }

    /** Returns a list containing the even numbers of the given list */
    public static List<Integer> evens(List<Integer> L) {
        // TODO: Fill in this function.
        return L.stream().filter(n -> n%2 == 0).toList();
    }

    /** Returns a list containing the common item of the two given lists */
    public static List<Integer> common(List<Integer> L1, List<Integer> L2) {
        // TODO: Fill in this function.
        Set<Integer> set2 = new HashSet<>(L2);
//        List<Integer> result = new ArrayList<>();
//        for(int x : L1){
//            if(set2.contains(x) && !result.contains(x)){
//                result.add(x);
//            }
//        }
//        return result;
        return L1.stream()
                .filter(set2::contains)
                .distinct()
                .toList();
    }


    /** Returns the number of occurrences of the given character in a list of strings. */
    public static int countOccurrencesOfC(List<String> words, char c) {
        // TODO: Fill in this function.
//        int count = 0 ;
//        for(String word : words){
//            for(int i=0; i<word.length(); i++){
//                if(word.charAt(i) == c){
//                    count++;
//                }
//            }
//        }
//        return count;

        return words.stream()
                .mapToInt(
                        s -> (int) s.chars()
                                            .filter(ch -> ch==c)
                                            .count()
                )
                .sum();
    }
}

/*
* String.chars() 会把字符串 s 转成一个 IntStream，
* 流中的每个元素都是该字符串对应字符（UTF-16 code unit）的 Unicode 码点值。
*
* IntStream.count() 是一个终端操作，返回一个 long，表示在这个字符串中出现特定字符的次数。
*
* 因为 count() 返回一个 long，而 mapToInt 需要返回 int，需要进行强制类型转换
*
* sum() 是整个管道的最终终端操作，作用是在得到的 IntStream 上执行求和，返回一个 int
* */