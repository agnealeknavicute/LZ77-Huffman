import java.util.HashMap;
import java.util.PriorityQueue;

public class Huffman_coding_Tree {
    private Node root;

    static class Node implements Comparable<Node> {
        byte data;
        int frequency;
        Node left, right;

        public Node(byte data, int frequency) {
            this.data = data;
            this.frequency = frequency;
        }

        @Override
        public int compareTo(Node o) {
            return this.frequency - o.frequency;
        }
    }

    public void Create_Huffman_Tree(byte[] information) {
        HashMap<Byte, Integer> frequencyMap = new HashMap<>();
        for (byte b : information) {
            frequencyMap.put(b, frequencyMap.getOrDefault(b, 0) + 1);
        }

        PriorityQueue<Node> pq = new PriorityQueue<>();
        for (byte b : frequencyMap.keySet()) {
            pq.add(new Node(b, frequencyMap.get(b)));
        }

        while (pq.size() > 1) {
            Node left = pq.poll();
            Node right = pq.poll();
            root = new Node((byte) 0, left.frequency + right.frequency);
            root.left = left;
            root.right = right;
            pq.add(root);
        }

    }

    public void read_Huffman_Tree(byte[] tree) {
    }

    public void printHuffmanTree() {
        printHuffmanTree(root);
    }

    private void printHuffmanTree(Node node) {
        if (node == null) {
            return;
        }
        printHuffmanTree(node.left);
        System.out.println("Data: " + node.data + ", Frequency: " + node.frequency);
        printHuffmanTree(node.right);
    }
}
