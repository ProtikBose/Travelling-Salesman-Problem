/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tspsearch;

import java.util.Collections;
import java.util.Scanner;
import java.util.Vector;

/**
 *
 * @author PRANTO
 */
public class TspSearch {

    /**
     * @param args the command line arguments
     */
    //static int[][] bestFiveNeighbors;

    public static double getDistance(double pX, double pY) {
        return Math.hypot((double) pX, (double) pY);
    }

    
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("Enter the number of Nodes : ");
        Scanner sc = new Scanner(System.in);
        int numberOfNodes = sc.nextInt();
        cityPosition[] cp = new cityPosition[numberOfNodes];
        double posx, posy;
        int x;
        double[][] adjacent = new double[numberOfNodes][numberOfNodes];
        //bestFiveNeighbors = new int[numberOfNodes][5];
        System.out.println("Enter the positions of cities : ");
        for (int i = 0; i < numberOfNodes; i++) {
            x = sc.nextInt();
            posx = sc.nextDouble();
            posy = sc.nextDouble();
            cp[i] = new cityPosition();
            cp[i].positionX = posx;
            cp[i].positionY = posy;
        }

        for (int i = 0; i < numberOfNodes; i++) {
            for (int j = 0; j < numberOfNodes; j++) {
                if (i != j && adjacent[i][j] == 0 && adjacent[j][i] == 0) {
                    adjacent[i][j] = getDistance(cp[i].positionX - cp[j].positionX, cp[i].positionY - cp[j].positionY);
                    adjacent[j][i] = adjacent[i][j];
                }
            }
        }

        System.out.println("The adjacent matrix is :");
        for (int i = 0; i < numberOfNodes; i++) {
            for (int j = 0; j < numberOfNodes; j++) {
                System.out.print(String.format("%.2f", adjacent[i][j]) + " ");
            }
            System.out.println("");
        } 
      /*  for (int i = 0; i < numberOfNodes; i++) {
            for (int j = 0; j < numberOfNodes; j++) {
                adjacent[i][j] = sc.nextDouble();
            }
        } */
        //findFiveBestNeighbor(numberOfNodes, adjacent);

        //nearestNeighbourSearch nS = new nearestNeighbourSearch(adjacent, numberOfNodes);
        //nS.findRoute();
        //nS.findRouteOne();
        //******************************************************************
        /*savingsAgain sH = new savingsAgain(adjacent, numberOfNodes);
        System.out.println("Task One");
        sH.findSavings();
        System.out.println("");
        System.out.println("Task Two");
        sH.findSavingsOne();
        sH.showResult();*/
        //******************************************************************
        Vector<Integer>v1=new Vector<>();
        Vector<Integer>v2[]=new Vector[3];
        v2[0]=new Vector<>();
        v2[1]=new Vector<>();
        v2[2]=new Vector<>();
        nearestNeighbourSearch nS = new nearestNeighbourSearch(adjacent, numberOfNodes);
        System.out.println("Task One");
        v1=nS.findRoute();
        nearestNeighbourSearch nS1 = new nearestNeighbourSearch(adjacent, numberOfNodes);
        System.out.println("Task two");
        v2=nS1.findRouteOne();
        System.out.println("Task three");
        Vector<Double>firstSol=new Vector<>();
        Vector<Double>bestSol=new Vector<>();
        twoOptImprovement tw=new twoOptImprovement(v1, adjacent);
        firstSol.add(tw.twoOptFunctionOne());
        twoOptImprovement tw2=new twoOptImprovement(v1, adjacent);
        bestSol.add(tw2.twoOptFunction());
        
        twoOptImprovement tw3=new twoOptImprovement(v2[0], adjacent);
        firstSol.add(tw3.twoOptFunctionOne());
        twoOptImprovement tw4=new twoOptImprovement(v2[0], adjacent);
        bestSol.add(tw4.twoOptFunction());
        
        twoOptImprovement tw5=new twoOptImprovement(v2[1], adjacent);
        firstSol.add(tw5.twoOptFunctionOne());
        twoOptImprovement tw6=new twoOptImprovement(v2[1], adjacent);
        bestSol.add(tw6.twoOptFunction());
        
        twoOptImprovement tw7=new twoOptImprovement(v2[2], adjacent);
        firstSol.add(tw7.twoOptFunctionOne());
        twoOptImprovement tw8=new twoOptImprovement(v2[2], adjacent);
        bestSol.add(tw8.twoOptFunction());
        
        Collections.sort(bestSol);
        Collections.sort(firstSol);
        double bestCost=0.0;
        double averageCost=0.0;
        double worstCount=0.0;
        
        for(int i=0;i<bestSol.size();i++){
            if(i==0) bestCost=bestSol.get(i);
            else if(i==(bestSol.size()-1))
                worstCount=bestSol.get(i);
            averageCost+=bestSol.get(i);
        }
        
        System.out.println("Best improvement Best Cost : "+bestCost);
        System.out.println("Best improvement Average Cost : "+averageCost/bestSol.size());
        System.out.println("Best improvement Worst Cost : "+worstCount);
        
        
        double bestCost1=0.0;
        double averageCost1=0.0;
        double worstCount1=0.0;
        
        for(int i=0;i<firstSol.size();i++){
            if(i==0) bestCost1=firstSol.get(i);
            else if(i==(firstSol.size()-1))
                worstCount1=firstSol.get(i);
            averageCost1+=firstSol.get(i);
        }
        
        System.out.println("First improvement Best Cost : "+bestCost1);
        System.out.println("First improvement Average Cost : "+averageCost1/bestSol.size());
        System.out.println("First improvement Worst Cost : "+worstCount1);
        //****************************************************************
    }

}
