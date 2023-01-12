package bstmap;

import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private class BSTNode {
        K key;
        V value;
        BSTNode left;
        BSTNode right;

        BSTNode() {
            key = null;
            value = null;
            left = null;
            right = null;
        }

        BSTNode(K key, V value) {
            this.key = key;
            this.value = value;
            left = null;
            right = null;
        }

    }

    private BSTNode bstNode;
    private int size;
    public BSTMap() {
        bstNode = new BSTNode();
        size = 0;
    }

    @Override
    public void clear(){
        bstNode = new BSTNode();
        size = 0;
    }

    @Override
    public boolean containsKey(K key){
        return containsKey(key, bstNode);
    }

    private boolean containsKey(K key, BSTNode N) {
        if (N == null || N.key == null) {
            return false;
        }
        if (N.key.equals(key)) {
            return true;
        }
        if (key.compareTo(N.key) < 0) {
            return containsKey(key, N.left);
        } else {
            return containsKey(key, N.right);
        }
    }

    @Override
    public V get(K key){
        return get(key, bstNode);
    }

    private V get(K key, BSTNode N) {
        if (N == null || N.key == null) {
            return null;
        }
        if (key.equals(N.key)) {
            return N.value;
        }
        if (key.compareTo(N.key) < 0) {
            return get(key, N.left);
        } else {
            return get(key, N.right);
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value){
        put(key, value, bstNode);
    }

    private void put(K key, V value, BSTNode N) {
        if (N.key == null) {
            N.key = key;
            N.value = value;
            size += 1;
            return;
        }
        if (key.compareTo(N.key) < 0) {
            if (N.left == null) {
                N.left = new BSTNode(key, value);
                size += 1;
                return;
            }
            put(key, value, N.left);
        } else if (key.compareTo(N.key) > 0) {
            if (N.right == null) {
                N.right = new BSTNode(key, value);
                size += 1;
                return;
            }
            put(key, value, N.right);
        } else if (key.compareTo(N.key) == 0) {
            N.value = value;
        }

    }

    public void printInOrder() {
        printInOrder(bstNode);
    }

    private void printInOrder(BSTNode N) {
        if (N == null) {
            return;
        }
        printInOrder(N.left);
        System.out.print(N.key + " ");
        printInOrder(N.right);
    }




    @Override
    public Set<K> keySet(){
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key){
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value){
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator(){
        throw new UnsupportedOperationException();
    }

}
