import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapExercises {
    /** Returns a map from every lower case letter to the number corresponding to that letter, where 'a' is
     * 1, 'b' is 2, 'c' is 3, ..., 'z' is 26.
     */
    public static Map<Character, Integer> letterToNum() {
        // TODO: Fill in this function.
        Map<Character, Integer> map = new HashMap<>();
        for(char ch = 'a'; ch <= 'z'; ch++){
            map.put(ch, ch - 'a' + 1);
        }
        return map;
    }

    /** Returns a map from the integers in the list to their squares. For example, if the input list
     *  is [1, 3, 6, 7], the returned map goes from 1 to 1, 3 to 9, 6 to 36, and 7 to 49.
     */
    public static Map<Integer, Integer> squares(List<Integer> nums) {
        // TODO: Fill in this function.
        Map<Integer, Integer> map = new HashMap<>();
        for(int num : nums){
            map.put(num, num*num);
        }
        return map;
    }

    /** Returns a map of the counts of all words that appear in a list of words. */
    public static Map<String, Integer> countWords(List<String> words) {
        // TODO: Fill in this function.
        Map<String, Integer> map = new HashMap<>();
//        for(String word : words){
//            if(!map.containsKey(word)){
//                map.put(word, 1);
//            }else{
//                map.put(word, map.get(word)+1);
//            }
//        }
//        return map;
        for (String word : words) {
            // 取出当前计数（若不存在则为 0），再加 1
            int prev = map.getOrDefault(word, 0);
            map.put(word, prev + 1);
        }
        return map;
    }
}
