/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tspsearch;

import java.util.Collections;
import java.util.Stack;
import java.util.Vector;

/**
 *
 * @author PRANTO
 */
public class twoOptImprovement {

    Vector<Integer> prevSol;
    Vector<Integer> newSol;
    Vector<Integer>firstImprovement;
    Vector<Integer>bestImprovement;
    int size;
    double[][] adj;

    public twoOptImprovement(Vector<Integer> prevSol, double[][] adj) {
        this.prevSol = prevSol;
        newSol = new Vector<>();
        size = prevSol.size();
        this.adj = adj;
        firstImprovement=new Vector<>();
        bestImprovement=new Vector<>();
    }

    public double getCost(Vector<Integer> sc) {
        double cost = 0;
        for (int i = 0; i < sc.size() - 1; i++) {
            cost = cost + adj[sc.get(i) - 1][sc.get(i + 1) - 1];
        }
        cost = cost + adj[sc.get(sc.size() - 1) - 1][sc.get(0)-1];
        return cost;
    }

    public void printSolutionVector() {
        System.out.println("The Final TwoOpt Solution is : ");
        for (int k = 0; k < prevSol.size(); k++) {
            System.out.print(prevSol.get(k) + " ");
        }
        System.out.println(prevSol.get(0));
        System.out.println("");
    }
    
    public void printSolutionVectorFinal(Vector<Integer>temp) {
        System.out.println("The Final TwoOpt Solution is : ");
        for (int k = 0; k < temp.size(); k++) {
            System.out.print(temp.get(k) + " ");
        }
        System.out.println(prevSol.get(0));
        System.out.println("");
    }

    public void twoOptSwap(int i, int k) {
        //int size = tour.TourSize();

        // 1. take route[0] to route[i-1] and add them in order to new_route
        for (int c = 0; c <= i - 1; c++) {
            // new_tour.SetCity(c, tour.GetCity(c));
            newSol.add(prevSol.get(c));
        }

        // 2. take route[i] to route[k] and add them in reverse order to new_route
        int dec = 0;
        for (int c = i; c <= k; c++) {
            // new_tour.SetCity(c, tour.GetCity(k - dec));
           // System.out.println(prevSol.get(k - dec));
            newSol.add(prevSol.get(k - dec));
            dec++;
        }

        // 3. take route[k+1] to end and add them in order to new_route
        for (int c = k + 1; c < size; ++c) {
            newSol.add(prevSol.get(c));
        }
        // newSol.add(prevSol.get(prevSol.size()-1));
    }

    public double twoOptFunction() {
        int improve = 0;
        boolean ifImproved=true;
        int iteration=0;
        double worstSolution=0.0;
        double averageSolution=0.0;
        
       // printSolutionVector();
        while (ifImproved) {
            ifImproved=false;
            double best_distance = getCost(prevSol);

            for (int i = 0; i < size - 1; i++) {
                for (int k = i + 1; k < size; k++) {
                  //  System.out.println(i+" "+k);
                  iteration++;
                  
                    twoOptSwap(i, k);

                    double new_distance = getCost(newSol);

                    if (new_distance < best_distance) {
                        // Improvement found so reset
                        improve = 0;
                        prevSol = newSol;
                        best_distance = new_distance;
                        firstImprovement=newSol;
                        ifImproved=true;
                    }
                    else if(new_distance > worstSolution){
                        worstSolution=new_distance;
                        
                    }
                    averageSolution+=new_distance;
                 //   printSolutionVector();
                    newSol=new Vector<>();
                }
            }
            bestImprovement=prevSol;
            

            //improve++;
        }
        System.out.println("***********************************************");
        System.out.println("");
        System.out.println("Worst Cost : "+worstSolution);
        System.out.println("");
        System.out.println("Average Cost: "+averageSolution/iteration);
        System.out.println("");
        System.out.println("Final TwoOpt Cost is : ");
        System.out.println(getCost(prevSol));
        printSolutionVector();
        System.out.println("");
        System.out.println("***********************************************");
        return getCost(bestImprovement);
    }
    
     public double twoOptFunctionOne() {
        int improve = 0;
        boolean ifImproved=true;
        int iteration=0;
        double worstSolution=0.0;
        double averageSolution=0.0;
        worstSolution=getCost(prevSol);
       // printSolutionVector();
        while (ifImproved) {
            ifImproved=false;
            double best_distance = getCost(prevSol);
            //System.out.println(best_distance);
            for (int i = 0; i < size - 1; i++) {
                for (int k = i + 1; k < size; k++) {
                  //  System.out.println(i+" "+k);
                  iteration++;
                    twoOptSwap(i, k);

                    double new_distance = getCost(newSol);
                    
                    if (new_distance < best_distance) {
                        // Improvement found so reset
                        improve = 0;
                        prevSol = newSol;
                        best_distance = new_distance;
                        firstImprovement=newSol;
                        ifImproved=true;
                       
                    }
                    else if(new_distance > worstSolution){
                        worstSolution=new_distance;
                        
                    }
                    averageSolution+=new_distance;
                    if(ifImproved) break;
                 //   printSolutionVector();
                    newSol=new Vector<>();
                }
                if(ifImproved) break;
            }
            if(ifImproved) break;
            bestImprovement=prevSol;
            

            //improve++;
        }
        System.out.println("***********************************************");
        System.out.println("");
        System.out.println("Worst Cost : "+worstSolution);
        System.out.println("");
        System.out.println("Average Cost: "+averageSolution/iteration);
        System.out.println("");
        System.out.println("Final TwoOpt Cost is : ");
        System.out.println(getCost(firstImprovement));
        printSolutionVector();
        System.out.println("");
        System.out.println("***********************************************");
        return getCost(firstImprovement);
    }
    
    public void showResult(){
        System.out.println("First Improvement");
        printSolutionVectorFinal(firstImprovement);
        System.out.println("");
        System.out.println("Cost : "+getCost(firstImprovement));
        System.out.println("***********************************************");
        
        System.out.println("Best Improvement");
        printSolutionVectorFinal(bestImprovement);
        System.out.println("");
        System.out.println("Cost : "+getCost(bestImprovement));
        System.out.println("**********************************************");
    }

    

}
