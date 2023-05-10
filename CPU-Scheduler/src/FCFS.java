import java.util.*;

public class FCFS {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // 프로세스 개수 입력받기
        System.out.print("프로세스 개수를 입력하세요: ");
        int n = input.nextInt();

        // 프로세스 정보를 저장할 배열 초기화
        int[] arrivalTimes = new int[n];
        int[] burstTimes = new int[n];
        int[] completionTimes = new int[n];
        int[] turnaroundTimes = new int[n];
        int[] waitingTimes = new int[n];

        // 사용자 입력을 통해 프로세스 도착시간과 실행시간을 받음
        for (int i = 0; i < n; i++) {
            System.out.print("프로세스 " + (i+1) + "의 도착시간과 실행시간을 입력하세요: ");
            arrivalTimes[i] = input.nextInt();
            burstTimes[i] = input.nextInt();
        }

        // 완료시간, 반환시간, 대기시간 계산
        int currentTime = 0;
        for (int i = 0; i < n; i++) {
            // 현재 시간보다 늦게 도착한 프로세스일 경우 대기
            if (currentTime < arrivalTimes[i]) {
                currentTime = arrivalTimes[i];
            }

            // 프로세스가 실행을 시작함
            currentTime += burstTimes[i];

            // 완료시간, 반환시간, 대기시간 저장
            completionTimes[i] = currentTime;
            turnaroundTimes[i] = currentTime - arrivalTimes[i];
            waitingTimes[i] = turnaroundTimes[i] - burstTimes[i];
        }

        // 평균 반환시간, 대기시간 계산
        double avgTurnaroundTime = 0.0;
        double avgWaitingTime = 0.0;
        for (int i = 0; i < n; i++) {
            avgTurnaroundTime += turnaroundTimes[i];
            avgWaitingTime += waitingTimes[i];
        }
        avgTurnaroundTime /= n;
        avgWaitingTime /= n;

        // 결과 출력
        System.out.println("프로세스\t도착시간\t실행시간\t완료시간\t반환시간\t대기시간");
        for (int i = 0; i < n; i++) {
            System.out.println((i+1) + " \t" + arrivalTimes[i] + " \t" + burstTimes[i] + " \t" + completionTimes[i] + " \t" + turnaroundTimes[i] + " \t" + waitingTimes[i]);
        }
        System.out.println("평균 반환시간: " + avgTurnaroundTime);
        System.out.println("평균 대기시간: " + avgWaitingTime);
    }
}
