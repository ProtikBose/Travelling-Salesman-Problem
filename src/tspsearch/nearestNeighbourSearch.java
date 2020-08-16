/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tspsearch;

import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Stack;
import java.util.Vector;

/**
 *
 * @author PRANTO
 */
public class nearestNeighbourSearch {

    private double[][] adj;
    private int nodeNumber;
    Stack<Integer> lastSolution = new Stack<Integer>();
    int solutionLastcost;
    Vector<Integer> tempoSol = new Vector<>();
    Stack<Integer> bestCase = new Stack<>();
    Stack<Integer> worstCase = new Stack<>();
    double minValue;
    double maxValue;
    double average[];
    Stack<Integer> averageCase[] = new Stack[8];
    int[][] bestFiveNeighbors;
    Vector<Integer> validNeighbor;
    int[] visited;
    Vector<Integer> storeN;

    //int[][] bestFiveneighbors;
    public nearestNeighbourSearch(double[][] adj, int nodeNumber) {
        this.adj = adj;
        this.nodeNumber = nodeNumber;
        lastSolution = null;
        solutionLastcost = 0;
        minValue = 0.0;
        maxValue = 0.0;
        average = new double[8];

        // this.bestFiveneighbors = bestFiveneighbors;
    }

    public void findFiveBestNeighbor(int numberOfNodes, double[][] adjacent, int val, Vector<Integer> valN) {
        double first, second, third, fourth, fifth;
        int firstNode, secondNode, thirdNode, fourthNode, fifthNode;

        fifth = fourth = third = first = second = Double.MAX_VALUE;
        fifthNode = fourthNode = thirdNode = firstNode = secondNode = Integer.MAX_VALUE;
        for (int j = 0; j < valN.size(); j++) {
            /* If current element is smaller than 
            first*/

            if (adjacent[val][valN.get(j)] < first) {
                fifth = fourth;
                fourth = third;
                third = second;
                second = first;
                first = adjacent[val][valN.get(j)];

                fifthNode = fourthNode;
                fourthNode = thirdNode;
                thirdNode = secondNode;
                secondNode = firstNode;
                firstNode = valN.get(j);
            } /* If arr[i] is in between first and 
            second then update second  */ else if (adjacent[val][valN.get(j)] < second) {
                fifth = fourth;
                fourth = third;
                third = second;
                second = adjacent[val][valN.get(j)];

                fifthNode = fourthNode;
                fourthNode = thirdNode;
                thirdNode = secondNode;
                secondNode = valN.get(j);

            } else if (adjacent[val][valN.get(j)] < third) {
                fifth = fourth;
                fourth = third;
                third = adjacent[val][valN.get(j)];

                fifthNode = fourthNode;
                fourthNode = thirdNode;
                thirdNode = valN.get(j);

            } else if (adjacent[val][valN.get(j)] < fourth) {
                fifth = fourth;

                fourth = adjacent[val][valN.get(j)];

                fifthNode = fourthNode;
                fourthNode = valN.get(j);

            } else if (adjacent[val][valN.get(j)] < fifth) {

                fifth = adjacent[val][valN.get(j)];

                fifthNode = valN.get(j);

            }

        }

        bestFiveNeighbors[val][0] = firstNode;
        bestFiveNeighbors[val][1] = secondNode;
        bestFiveNeighbors[val][2] = thirdNode;
        bestFiveNeighbors[val][3] = fourthNode;
        bestFiveNeighbors[val][4] = fifthNode;

    }

    public void getValidNeighbor(int val) {

        validNeighbor = new Vector<>();
        for (int i = 0; i < 5; i++) {
            System.out.println(i);
            if (visited[bestFiveNeighbors[val][i]] == 0) {
                validNeighbor.add(bestFiveNeighbors[val][i]);
            }
        }
    }

    public double getCost(Stack<Integer> sc) {
        double cost = 0;
        for (int i = 0; i < nodeNumber; i++) {
            cost = cost + adj[sc.get(i) - 1][sc.get(i + 1) - 1];
        }
        return cost;
    }

    public double getCostVector(Vector<Integer> sc) {
        double cost = 0;
        for (int i = 0; i < nodeNumber; i++) {
            cost = cost + adj[sc.get(i) - 1][sc.get(i + 1) - 1];
        }
        return cost;
    }

    public void printSolutionStack(Stack<Integer> sc) {
        System.out.println("The Solution is : ");
        for (int k = 0; k < sc.size(); k++) {
            System.out.print(sc.get(k) + " ");
        }
        System.out.println("");
    }

    public void printSolutionVector() {
        System.out.println("The Solution is : ");
        for (int k = 0; k < tempoSol.size(); k++) {
            System.out.print(tempoSol.get(k) + " ");
        }
        System.out.println("");
    }

    public Stack<Integer> nearestNeighborAlgorithm(int i) {

        Stack<Integer> tempSolution = new Stack<Integer>();

        int[] visited = new int[nodeNumber + 1];
        visited[i - 1] = 1;
        tempSolution.push(i);
        int element = i - 1;
        double min = Integer.MAX_VALUE;
        boolean minFlag = false;
        int dst = i;
        int tempCost = 0;
        // System.out.print(i + "\t");
        while (tempSolution.size() != nodeNumber) {
            element = tempSolution.peek();
            int in = 0;
            min = (double) Integer.MAX_VALUE;
            while (in < nodeNumber) {
                if (adj[element - 1][in] > 0 && visited[in] == 0) {
                    if (min > adj[element - 1][in]) {
                        min = adj[element - 1][in];
                        dst = in + 1;
                        minFlag = true;
                    }
                }
                in++;
            }
            if (minFlag) {
                visited[dst - 1] = 1;
                tempSolution.push(dst);
                // System.out.print(dst + "\t");
                minFlag = false;
                continue;
            }
            // tempSolution.pop();
        }
        return tempSolution;
        /*  for(int k=0;k<tempSolution.size();k++)
            System.out.println(tempSolution.get(k)); */
    }

    public Stack<Integer> nearestNeighborAlgorithmOne(int i) {

        Stack<Integer> tempSolution = new Stack<Integer>();
        int checkValue = 0;
        bestFiveNeighbors = new int[nodeNumber][nodeNumber];
        visited = new int[nodeNumber + 1];
        visited[i] = 1;
        tempSolution.push(i + 1);
        int element = i;
        double min = Integer.MAX_VALUE;
        boolean minFlag = false;
        int dst = i;
        int tempCost = 0;
        // System.out.print(i + "\t");
        while (tempSolution.size() != nodeNumber) {
            storeN = new Vector<>();
            element = tempSolution.peek();
            // System.out.println(element);
            element = element - 1;
            for (int k = 0; k < nodeNumber; k++) {
                if (visited[k] == 0 && k != element) {
                    storeN.add(k);
                }
            }
            findFiveBestNeighbor(nodeNumber, adj, element, storeN);
            int in = 0;
            min = (double) Integer.MAX_VALUE;

            Random rand = new Random();
            if (storeN.size() <= 5) {
                checkValue = ((int) (Math.random() * Math.random() / Math.random())) % storeN.size();
            } else {
                checkValue = ((int) (Math.random() * Math.random() / Math.random())) % 5;
            }
            //  if(visited[checkValue]==1)
            //    continue;

            minFlag = true;
            tempSolution.push(bestFiveNeighbors[element][checkValue] + 1);
            // System.out.println("Found");

            if (minFlag) {
                //System.out.println(bestFiveNeighbors[element][checkValue]);
                visited[bestFiveNeighbors[element][checkValue]] = 1;
                // tempSolution.push(dst);
                // System.out.print(dst + "\t");
                minFlag = false;
                //continue;
            }
            // tempSolution.pop();
        }
        return tempSolution;
        /*  for(int k=0;k<tempSolution.size();k++)
            System.out.println(tempSolution.get(k)); */
    }

    public Vector<Integer> findRoute() {

        /* for(int i=0;i<nodeNumber;i++){
            lastSolution=findSearchBestRoute(i+1);
            lastSolution.push(i+1);
            printSolution();
            System.out.println("Cost : "+String.format("%.2f",getCost(lastSolution)));
        } */
        int averageCount = 0;
        for (int i = 0; i < 10; i++) {
            Random rand = new Random();
            int checkValue = ((int) (Math.random() * Math.random() / Math.random())) % nodeNumber + 1;
            lastSolution = nearestNeighborAlgorithm(checkValue);
            lastSolution.push(checkValue);
            if (i == 0) {
                minValue = getCost(lastSolution);
                bestCase = lastSolution;
            } else if (i == 1) {
                double value = getCost(lastSolution);
                if (value < minValue) {
                    maxValue = minValue;
                    worstCase = bestCase;
                    minValue = value;
                    bestCase = lastSolution;
                } else {
                    maxValue = value;
                    worstCase = lastSolution;
                }
            } else {
                double value = getCost(lastSolution);
                if (value < minValue) {
                    average[averageCount] = minValue;
                    averageCase[averageCount] = new Stack<>();
                    averageCase[averageCount] = bestCase;
                    averageCount++;
                    minValue = value;
                    bestCase = lastSolution;
                } else if (value > maxValue) {
                    average[averageCount] = maxValue;
                    averageCase[averageCount] = new Stack<>();
                    averageCase[averageCount] = worstCase;
                    averageCount++;
                    maxValue = value;
                    worstCase = lastSolution;
                } else {
                    average[averageCount] = value;
                    averageCase[averageCount] = new Stack<>();
                    averageCase[averageCount] = lastSolution;
                    averageCount++;
                }
            }
        }

        System.out.println("Best Solution");
       // printSolutionStack(bestCase);
        System.out.println("Cost : " + getCost(bestCase));
        System.out.println("");

        System.out.println("Average Cases");
        double totAvgCost = 0.0;
        for (int i = 0; i < 3; i++) {
          //  printSolutionStack(averageCase[i]);
          //  System.out.println("Cost : " + getCost(averageCase[i]));
            totAvgCost += getCost(averageCase[i]);
            System.out.println("");
        }
        System.out.println("Total Average Cost : " + totAvgCost / 8);
        System.out.println("");

        System.out.println("Worst Solution");
       // printSolutionStack(worstCase);
        System.out.println("Cost : " + getCost(worstCase));
        System.out.println("");

        /*   System.out.println("");
        System.out.println("Nearest Neighbor Solution : ");
        System.out.println("Cost : " + String.format("%.2f", getCost(lastSolution)));
        //  printSolutionStack(); */
        for (int i = 0; i < lastSolution.size(); i++) {
            tempoSol.add(bestCase.get(i));
        }
        //printSolutionVector();
        System.out.println("");
        tempoSol.remove(tempoSol.size() - 1);

        Vector<Integer> b = new Vector<>();
        for (int i = 0; i < bestCase.size(); i++) {
            b.add(bestCase.get(i));
        }

        // twoOptImprovement twoOpt = new twoOptImprovement(tempoSol, adj);
        //twoOpt.twoOptFunction();
        return b;

    }

    class priorityHelpTwo implements Comparator<Stack<Integer>> {

        @Override
        public int compare(Stack<Integer> node1, Stack<Integer> node2) {

            int result = 0;
            double compare1 = getCost(node1);
            double compare2 = getCost(node2);
            if (compare1 > compare2) {
                result = 1;
            } else if (compare1 < compare2) {
                result = -1;
            } else {
                result = 0;
            }
            return result;
            //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

    public Vector<Integer>[] findRouteOne() {

        /* for(int i=0;i<nodeNumber;i++){
            lastSolution=findSearchBestRoute(i+1);
            lastSolution.push(i+1);
            printSolution();
            System.out.println("Cost : "+String.format("%.2f",getCost(lastSolution)));
        } */
        //System.out.println("enter");
        int averageCount = 0;
        priorityHelpTwo orderedList1 = new priorityHelpTwo();
        PriorityQueue<Stack<Integer>> pQueue1 = new PriorityQueue<Stack<Integer>>(orderedList1);
        for (int i = 0; i < 10; i++) {
            Random rand = new Random();
            int checkValue = ((int) (Math.random() * Math.random() / Math.random())) % nodeNumber;
            lastSolution = nearestNeighborAlgorithmOne(checkValue);
            lastSolution.push(checkValue + 1);
            pQueue1.add(lastSolution);
            //printSolutionStack(lastSolution);

            if (i == 0) {
                minValue = getCost(lastSolution);
                bestCase = lastSolution;
            } else if (i == 1) {
                double value = getCost(lastSolution);
                if (value < minValue) {
                    maxValue = minValue;
                    worstCase = bestCase;
                    minValue = value;
                    bestCase = lastSolution;
                } else {
                    maxValue = value;
                    worstCase = lastSolution;
                }
            } else {
                double value = getCost(lastSolution);
                if (value < minValue) {
                    average[averageCount] = minValue;
                    averageCase[averageCount] = new Stack<>();
                    averageCase[averageCount] = bestCase;
                    averageCount++;
                    minValue = value;
                    bestCase = lastSolution;
                } else if (value > maxValue) {
                    average[averageCount] = maxValue;
                    averageCase[averageCount] = new Stack<>();
                    averageCase[averageCount] = worstCase;
                    averageCount++;
                    maxValue = value;
                    worstCase = lastSolution;
                } else {
                    average[averageCount] = value;
                    averageCase[averageCount] = new Stack<>();
                    averageCase[averageCount] = lastSolution;
                    averageCount++;
                }
            }
        }

        System.out.println("Best Solution");
       // printSolutionStack(bestCase);
        System.out.println("Cost : " + getCost(bestCase));
        System.out.println("");

        double totAvgCost = 0.0;
        for (int i = 0; i < 3; i++) {
           // printSolutionStack(averageCase[i]);
          //  System.out.println("Cost : " + getCost(averageCase[i]));
            totAvgCost += getCost(averageCase[i]);
            System.out.println("");
        }
        System.out.println("Total Average Cost : " + totAvgCost / 8);
        System.out.println("");

        System.out.println("Worst Solution");
       // printSolutionStack(worstCase);
        System.out.println("Cost : " + getCost(worstCase));
        System.out.println("");

        /*   System.out.println("");
        System.out.println("Nearest Neighbor Solution : ");
        System.out.println("Cost : " + String.format("%.2f", getCost(lastSolution)));
        //  printSolutionStack(); */
        for (int i = 0; i < lastSolution.size(); i++) {
            tempoSol.add(bestCase.get(i));
        }
        //printSolutionVector();
        System.out.println("");
        tempoSol.remove(tempoSol.size() - 1);

        //twoOptImprovement twoOpt = new twoOptImprovement(tempoSol, adj);
        //twoOpt.twoOptFunction();
        Stack<Integer> tempo = new Stack<>();

        Vector<Integer> b[] = new Vector[3];
        b[0] = new Vector<>();
        b[1] = new Vector<>();
        b[2] = new Vector<>();
        bestCase = pQueue1.remove();
        tempo = pQueue1.remove();
        worstCase = pQueue1.remove();

        for (int i = 0; i < bestCase.size(); i++) {
            b[0].add(bestCase.get(i));
        }
        for (int i = 0; i < tempo.size(); i++) {
            b[1].add(tempo.get(i));
        }
        for (int i = 0; i < worstCase.size(); i++) {
            b[2].add(worstCase.get(i));
        }
        return b;
    }

}
