/**
 * @author ADKN.
 * @version 11 Feb 2016.
 */
public class StrLinkedList {
    private StrNode front;
    private int size;

    public StrLinkedList() {
        front = null;
        size = 0;
    }

    public StrLinkedList(StrLinkedList original) {
        /**
         * Shallow copy
         * front = original.front
         */

        size = 0;
        this.front = original.front;

        StrNode curr1 = original.front;
        StrNode curr2 = null;
        if (original.size > 0) {
            size++;
            this.front = new StrNode(curr1.item, null);
            curr2 = this.front;
        }

        while (curr1.next != null) {
            curr1 = curr1.next;
            curr2.next = new StrNode(curr1.item, null);
            size++;
            curr2 = this.front;
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        size = 0;
        front = null;
    }

    public StrNode getNode(int pos) {
        StrNode cursor = front;
        for (int i = 0; i < pos; i++) {
            cursor = cursor.next;
        }
        return cursor;
    }

    public int indexOf(String target) {
        int index = 0;
        StrNode cursor = front;
        while (index < size && !(cursor.item.equals(target))) {
            cursor = cursor.next;
            index++;
        }
        if (index == size) {    //if index == size, then we must have not found target and fallen off the end of the list
            return -1;
        } else {return index;}
    }

    public boolean contains(String target) {
        StrNode cursor = front;
        while ((cursor != null)) {
            if (cursor.item.equals(target)) {
                return true;
            }
            cursor = cursor.next;
        }
        return false;
    }

    /**
     * To a client, a list is just a list regardless of actual data structure. But under the hood, we may choose to
     * implement it using nodes or arrays or arraylists.
     */
    private class StrNode {
        String item; // string associated with this node StrNode next;
        StrNode next; // link to next StrNode, or null if no next node

        public StrNode(String data, StrNode n) {
            next = n;
            item = data;
        }
    }
    /**
     * Why use a linked list node system instead of an Array or an ArrayList? Suppose you have to add or remove
     * an element from a list at a certain index. In order to add an element, you have to shift all the elements
     * above that index upwards. To remove, you must shift everything above that index downwards. So a list is more
     * efficient should you need to add to the end. If you must modify something in the middle, but not remove or add,
     * then an Array or ArrayList are equally fine.
     *
     * But, if modifying, a linked list: adding is easy, you go to the index k-1 and you want to simply change that node's
     * reference to the "next" node, to point to the new data, and that new node's reference to point to the rest of the chain.
     * To remove, you simply remove node k-1's reference to the piece to be removed.
     */
}
