public class LSD {

    public static void sort(String[] a, int W) { //��� ������ ���� W
        int N = a.length; //�迭 ����
        int R = 256; //��� = R()
        String[] aux = new String[N];

        for (int d = W - 1; d >= 0; d--) { //�ڿ�������
            int[] count = new int[R];
            
            for (int i = 0; i < N; i++)
                count[a[i].charAt(d)]++; //d�ڸ� ���ڵ��� �󵵼� ���
            
            for (int r = 1; r < R; r++)
                count[r] += count[r - 1];
            
            for (int i = N - 1; i >= 0; i--) //�� ���ڿ����� �ڿ��� ����
                aux[--count[a[i].charAt(d)]] = a[i];
           
            for (int i = 0; i < N; i++)
                a[i] = aux[i];
        }
    }

    public static void main(String[] args) {
        String[] a = {"car", "bar", "bat", "cat", "cab", "rat", "tab", "bag"};

        // �迭�� �ִ� ���̸� ã�´�.
        int maxLen = 0;
        for (String s : a) {
            if (s.length() > maxLen) {
                maxLen = s.length();
            }
        }

        sort(a, maxLen);

        // ���ĵ� �迭 ���
        for (String s : a) {
            System.out.print(s + " ");
        }
    }
}