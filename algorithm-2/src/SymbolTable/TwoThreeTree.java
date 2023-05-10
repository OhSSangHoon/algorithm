package SymbolTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TwoThreeTree {
    public static class Node {
        int[] keys = new int[2];
        Node[] children = new Node[3];
        int numKeys = 0;

        Node() {
        }

        static Node createNode() {
            return new Node();
        }
    }

    public static void main(String[] args) {
        int[] data = {1, 2, 3, 4, 5, 6, 7, 8};
        int n = data.length;

        List<int[]> permutations = generatePermutations(data);
        Set<String> treeShapes = new HashSet<>();

        for (int[] permutation : permutations) {
            Node root = null;
            for (int key : permutation) {
                root = insert(root, key);
            }
            treeShapes.add(serializeTree(root));
        }

        System.out.println("Generated " + treeShapes.size() + " unique 2-3 trees with " + n + " elements.");
    }

    static Node insert(Node node, int key) {
        if (node == null) {
            node = Node.createNode();
            node.keys[0] = key;
            node.numKeys = 1;
            return node;
        }

        if (node.numKeys == 2) {
            Node newNode = splitNode(node);
            if (key > node.keys[0]) {
                insertKey(newNode, key);
            } else {
                insertKey(node, key);
            }
            return newNode;
        }

        int i;
        for (i = 0; i < node.numKeys && node.keys[i] < key; ++i);
        Node newChild = insert(node.children[i], key);
        if (newChild != null) {
            insertKey(node, newChild.keys[0]);
            node.children[i + 1] = newChild;
        }
        return null;
    }

    static void insertKey(Node node, int key) {
        int i;
        for (i = node.numKeys - 1; i >= 0 && node.keys[i] > key; --i) {
            node.keys[i + 1] = node.keys[i];
        }
        node.keys[i + 1] = key;
        ++node.numKeys;
    }
    
    static String serializeTree(Node node) {
        if (node == null) {
            return "null";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(node.numKeys);
        for (int i = 0; i < node.numKeys; i++) {
            sb.append(",").append(node.keys[i]);
        }
        for (int i = 0; i <= node.numKeys; i++) {
            sb.append(";").append(serializeTree(node.children[i]));
        }
        return sb.toString();
    }


    static Node splitNode(Node node) {
        Node newNode = Node.createNode();
        newNode.keys[0] = node.keys[1];
        newNode.children[0] = node.children[1];
        newNode.children[1] = node.children[2];
        newNode.numKeys = 1;
        node.children[1] = node.children[2] = null;
        node.numKeys = 1;
        return newNode;
    }

    static List<int[]> generatePermutations(int[] data) {
        List<int[]> permutations = new ArrayList<>();
        generatePermutationsHelper
        (data, 0, data.length - 1, permutations);
        return permutations;
    }

    static void generatePermutationsHelper(int[] data, int l, int r, List<int[]> permutations) {
        if (l == r) {
            permutations.add(Arrays.copyOf(data, data.length));
        } else {
            for (int i = l; i <= r; i++) {
                swap(data, l, i);
                generatePermutationsHelper(data, l + 1, r, permutations);
                swap(data, l, i); // backtrack
            }
        }
    }

    static void swap(int[] data, int i, int j) {
        int temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }
}

