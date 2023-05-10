import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class Process {
    String name;
    int arrivalTime;
    int burstTime;
    int waitingTime;
    int endTime;
    double responseRatio;

    Process(String name, int arrivalTime, int burstTime) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.waitingTime = 0;
        this.endTime = 0;
        this.responseRatio = 0;
    }
}

public class HRRN {
    public static void main(String[] args) {
        List<Process> processes = new ArrayList<>();
        processes.add(new Process("A", 0, 7));
        processes.add(new Process("B", 2, 5));
        processes.add(new Process("C", 5, 6));
        processes.add(new Process("D", 8, 8));
        processes.add(new Process("E", 10, 5));
        processes.add(new Process("F", 15, 8));

        hrrnScheduling(processes);
    }

    private static void hrrnScheduling(List<Process> processes) {
        int currentTime = 0;
        int completedProcesses = 0;
        int n = processes.size();

        while (completedProcesses < n) {
            Process highestRRProcess = null;
            double maxResponseRatio = -1;

            for (Process process : processes) {
                if (process.arrivalTime <= currentTime && process.endTime == 0) {
                    process.waitingTime = currentTime - process.arrivalTime;
                    process.responseRatio = (double) (process.waitingTime + process.burstTime) / process.burstTime;

                    if (process.responseRatio > maxResponseRatio) {
                        maxResponseRatio = process.responseRatio;
                        highestRRProcess = process;
                    }
                }
            }

            if (highestRRProcess != null) {
                currentTime += highestRRProcess.burstTime;
                highestRRProcess.endTime = currentTime;
                completedProcesses++;
                System.out.println("Process " + highestRRProcess.name + " completed at time: " + currentTime);
            } else {
                currentTime++;
            }
        }
    }
}
