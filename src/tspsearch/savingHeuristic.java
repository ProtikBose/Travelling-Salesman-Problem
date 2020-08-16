/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tspsearch;

import java.util.Random;
import java.util.Stack;
import java.util.Vector;

/**
 *
 * @author PRANTO
 */
public class savingHeuristic {

    private double[][] adj;
    private int nodeNumber;
    Stack<Integer> lastSolution = new Stack<Integer>();
    int solutionLastcost;
    Vector<Double> tempoSol = new Vector<>();
    Vector<String> detop = new Vector<>();
    Vector<Integer> finalSolution = new Vector<>();
    Vector<Integer> visited = new Vector<>();

    public savingHeuristic(double[][] adj, int nodeNumber) {
        this.adj = adj;
        this.nodeNumber = nodeNumber;
        lastSolution = null;
        solutionLastcost = 0;
        for (int i = 0; i < nodeNumber; i++) {
            visited.add(0);
        }
    }

    public double getCost(Vector<Integer> sc) {
        double cost = 0;
        for (int i = 0; i < sc.size() - 1; i++) {
            cost = cost + adj[sc.get(i) - 1][sc.get(i + 1) - 1];
        }
        cost = cost + adj[sc.get(sc.size() - 1) - 1][sc.get(0) - 1];
        return cost;
    }

    public void printSolutionVector() {
        System.out.println("The Final Savings Solution is : ");
        for (int k = 0; k < finalSolution.size(); k++) {
            System.out.print(finalSolution.get(k) + " ");
        }
        System.out.println(finalSolution.get(0));
        System.out.println("");
    }

    public void checkSavings(int checkValue) {
        for (int i = 0; i < nodeNumber; i++) {
            for (int j = 0; j < nodeNumber; j++) {
                if (checkValue != i && checkValue != j && i != j) {
                    double savings = adj[i][checkValue] + adj[checkValue][i] - adj[i][j];
                    tempoSol.add(savings);
                    detop.add(i + "," + j);
                }
            }
        }
    }

    public void makeSort() {

        String s = new String();
        s = "";

        for (int i = 0; i < tempoSol.size(); i++) {
            for (int j = 0; j < tempoSol.size(); j++) {
                if (tempoSol.get(j) < tempoSol.get(i)) {
                    double tmp = tempoSol.get(i);
                    tempoSol.set(i, tempoSol.get(j));
                    tempoSol.set(j, tmp);

                    s = detop.get(i);
                    detop.set(i, detop.get(j));
                    detop.set(j, s);
                }
            }
        }
    }

    public void makeSolution(int checkValue) {

        int n = 0, cnt = 0;
        finalSolution.add(checkValue);
        visited.set(checkValue - 1, 1);
        n++;
        String store = new String();
        store = "";
        String letter = new String();
        letter = "";
        String arr[]=new String[2];
        arr[0]=new String("");
        arr[1]=new String("");
       /* while (n < nodeNumber) {

            store = detop.get(cnt);
            arr=store.split(",");
            letter = arr[0];
            int a = Integer.parseInt(letter);
            letter = arr[1];
            int b = Integer.parseInt(letter);

            if (visited.get(a) == 0 && visited.get(b) == 0) {

                finalSolution.add(a + 1);
                finalSolution.add(b + 1);
                n = n + 2;
                visited.set(a, 1);
                visited.set(b, 1);

            } else if (visited.get(b) == 0) {
                int index = finalSolution.indexOf(a + 1);
                finalSolution.add(index + 1, b + 1);
                visited.set(b, 1);
                n++;
            } else if (visited.get(a) == 0) {
                int index = finalSolution.indexOf(b + 1);
                finalSolution.add(index - 1, a + 1);
                visited.set(a, 1);
                n++;
            }
            cnt++;
        }*/
       for(int i=0;i<tempoSol.size();i++){
           
            store = detop.get(i);
            arr=store.split(",");
            letter = arr[0];
            int a = Integer.parseInt(letter);
            letter = arr[1];
            int b = Integer.parseInt(letter);
            
            if(i==0){
                
               finalSolution.add(a+1);
               finalSolution.add(b+1);
               visited.set(a, 1);
               visited.set(b, 1);
            }
            else{
                if(a==finalSolution.get(finalSolution.size()-1)-1){
                    if(visited.get(b)==0){
                        finalSolution.add(b+1);
                        visited.set(b, 1);
                    }
                }
                else if(b==finalSolution.get(1)-1){
                    if(visited.get(a)==0){
                        finalSolution.add(1,a+1);
                        visited.set(a, 1);
                    }
                }
                else{
                    
                }
            }
       }
       

    }

    public void findSavings() {

        Random rand = new Random();
        int checkValue = ((int) (Math.random() * Math.random() / Math.random())) % nodeNumber;
        checkSavings(checkValue);
        makeSort();

        /* for(int i=0;i<tempoSol.size();i++){
            System.out.println(tempoSol.get(i));
            System.out.println(detop.get(i));
        }*/
        makeSolution(checkValue + 1);
        System.out.println("");
        System.out.println("Savings Heuristic Cost is: ");
        System.out.println(getCost(finalSolution));
        printSolutionVector();
        System.out.println("");
        twoOptImprovement twoOpt = new twoOptImprovement(finalSolution, adj);
        twoOpt.twoOptFunction();
    }
}
