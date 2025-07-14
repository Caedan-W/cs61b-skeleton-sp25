import deque.ArrayDeque61B;

import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDeque61BTest {

//     @Test
//     @DisplayName("ArrayDeque61B has no fields besides backing array and primitives")
//     void noNonTrivialFields() {
//         List<Field> badFields = Reflection.getFields(ArrayDeque61B.class)
//                 .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
//                 .toList();
//
//         assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
//     }

    @Test
    @DisplayName("addFirst on proper index of ArrayDeque")
    void addFirstTest() {
        ArrayDeque61B<Integer> ad = new ArrayDeque61B<>();
        ad.addFirst(7);
        assertThat(ad.get(0)).isEqualTo(7);

        ad.addFirst(6);
        assertThat(ad.get(0)).isEqualTo(6);

        ad.addFirst(5);
        assertThat(ad.get(0)).isEqualTo(5);
    }

    @Test
    @DisplayName("get on index such that index + nextFirst > size - 1")
    void addFirstCircularTest() {
        ArrayDeque61B<Integer> ad = new ArrayDeque61B<>();
        for (int i=7; i>=0; i--){
            ad.addFirst(i);
        }
        assertThat(ad.get(4)).isEqualTo(4);
    }

    @Test
    @DisplayName("get on index such that index == -1, size, size+1")
    void getOnImproperIndexTest() {
        ArrayDeque61B<Integer> ad = new ArrayDeque61B<>();
        for (int i=7; i>=0; i--){
            ad.addFirst(i);
        }
        assertThat(ad.get(-1)).isNull();
        assertThat(ad.get(8)).isNull();
        assertThat(ad.get(9)).isNull();
    }

    @Test
    @DisplayName("addLast on proper index")
    void addLastTest() {
        ArrayDeque61B<Integer> ad = new ArrayDeque61B<>();
        ad.addLast(0);
        ad.addLast(1);
        assertThat(ad.get(0)).isEqualTo(0);
        assertThat(ad.get(1)).isEqualTo(1);
    }

    @Test
    @DisplayName("isEmpty() test")
    void isEmptyTest() {
        ArrayDeque61B<Integer> ad = new ArrayDeque61B<>();
        assertThat(ad.isEmpty()).isTrue();

        ad.addFirst(0);
        assertThat(ad.isEmpty()).isFalse();
    }

    @Test
    @DisplayName("size() test")
    void sizeTest() {
        ArrayDeque61B<Integer> ad = new ArrayDeque61B<>();
        assertThat(ad.size()).isEqualTo(0);

        ad.addFirst(0);
        assertThat(ad.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("toList test")
    void toListTest() {
        ArrayDeque61B<Integer> ad = new ArrayDeque61B<>();
        assertThat(ad.toList()).isEmpty();

        ad.addLast(0);
        ad.addLast(1);
        ad.addLast(2);
        assertThat(ad.toList()).containsExactly(0,1,2).inOrder();
    }

    @Test
    @DisplayName("remove on empty ArrayDeque")
    void removeOnNullArrayDequeTest() {
        ArrayDeque61B<Integer> ad = new ArrayDeque61B<>();
        assertThat(ad.removeFirst()).isNull();
        assertThat(ad.removeLast()).isNull();
    }

    @Test
    @DisplayName("remove on non-empty ArrayDeque")
    void removeAtProperIndexTest() {
        ArrayDeque61B<Integer> ad = new ArrayDeque61B<>();
        for (int i=7; i>=0; i--){
            ad.addFirst(i);
        }
        assertThat(ad.removeFirst()).isEqualTo(0);
        assertThat(ad.toList()).containsExactly(1,2,3,4,5,6,7).inOrder();

        assertThat(ad.removeLast()).isEqualTo(7);
        assertThat(ad.toList()).containsExactly(1,2,3,4,5,6).inOrder();
    }

    @Test
    @DisplayName("resizeUp should double capacity when full")
    void resizeUpTest() {
        ArrayDeque61B<Integer> ad = new ArrayDeque61B<>();
        // 初始 capacity = 8
        assertThat(ad.capacity()).isEqualTo(8);

        // 填满 8 个槽
        for (int i = 0; i < 8; i++) {
            ad.addLast(i);
        }
        assertThat(ad.size()).isEqualTo(8);
        assertThat(ad.capacity()).isEqualTo(8);

        // 第 9 次 addLast 应该触发扩容到 16
        ad.addLast(8);
        assertThat(ad.size()).isEqualTo(9);
        assertThat(ad.capacity()).isEqualTo(16);

        // 确认所有元素都在正确位置
        for (int i = 0; i < 9; i++) {
            assertThat(ad.get(i)).isEqualTo(i);
        }
    }

    @Test
    @DisplayName("resizeDown should half capacity once usage ≤25% (for capacity ≥16)")
    void resizeDownTest() {
        ArrayDeque61B<Integer> ad = new ArrayDeque61B<>();
        // 先触发一次扩容到 16
        for (int i = 0; i < 8; i++) {
            ad.addFirst(i);
        }
        ad.addLast(8);
        assertThat(ad.capacity()).isEqualTo(16);
        assertThat(ad.size()).isEqualTo(9);

        // 先删除 5 次：size 从 9→4，始终 size > 25%*16(=4) 或刚好等于 4 但按规则 shrink 在 remove 之前
        for (int i = 0; i < 5; i++) {
            ad.removeLast();
        }
        // 此时 size == 4，但尚未触发下一次 remove，capacity 仍旧 16
        assertThat(ad.size()).isEqualTo(4);
        assertThat(ad.capacity()).isEqualTo(16);

        // 第 6 次 remove：因为 before-remove 时 size == 4 ≤ 4，先 shrink 到 8，然后再真正 remove
        ad.removeLast();
        assertThat(ad.capacity()).isEqualTo(8);
        assertThat(ad.size()).isEqualTo(3);
    }

}
