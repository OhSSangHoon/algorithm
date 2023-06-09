public class LSD {

    public static void sort(String[] a, int W) { //모든 문자의 길이 W
        int N = a.length; //배열 길이
        int R = 256; //기수 = R()
        String[] aux = new String[N];

        for (int d = W - 1; d >= 0; d--) { //뒤에서부터
            int[] count = new int[R];
            
            for (int i = 0; i < N; i++)
                count[a[i].charAt(d)]++; //d자리 문자들의 빈도수 계산
            
            for (int r = 1; r < R; r++)
                count[r] += count[r - 1];
            
            for (int i = N - 1; i >= 0; i--) //뒤 문자열부터 뒤에서 저장
                aux[--count[a[i].charAt(d)]] = a[i];
           
            for (int i = 0; i < N; i++)
                a[i] = aux[i];
        }
    }

    public static void main(String[] args) {
        String[] a = {"car", "bar", "bat", "cat", "cab", "rat", "tab", "bag"};

        // 배열의 최대 길이를 찾는다.
        int maxLen = 0;
        for (String s : a) {
            if (s.length() > maxLen) {
                maxLen = s.length();
            }
        }

        sort(a, maxLen);

        // 정렬된 배열 출력
        for (String s : a) {
            System.out.print(s + " ");
        }
    }
}
