/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tspsearch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Vector;

/**
 *
 * @author PRANTO
 */
public class savingsAgain {

    private double[][] adj;
    private int nodeNumber;
    Vector<Vector<Integer>> initialRoute;
    int storeCheckValue;
    PriorityQueue<Vector<Integer>> pQueue;
    Vector<Integer>taskOneSol=new Vector<>();
    Vector<Integer>taskTwoSolOne=new Vector<>();
    Vector<Integer>taskTwoSolTwo=new Vector<>();
    Vector<Integer>taskTwoSolThree=new Vector<>();

    public savingsAgain(double[][] adj, int nodeNumber) {
        this.adj = adj;
        this.nodeNumber = nodeNumber;

    }

    public void printRoute() {
        for (int i = 0; i < initialRoute.size(); i++) {
            System.out.println(initialRoute.get(i));
        }
    }

    public double getCost(Vector<Integer> vec) {
        double cost = 0.0;
        for (int i = 0; i < vec.size() - 1; i++) {
            cost = cost + adj[vec.get(i)][vec.get(i + 1)];
        }
        return cost;
    }

    public double getCostSavings(Vector<Integer> vec) {
        double cost = 0.0;
        cost += adj[storeCheckValue][vec.get(1)] + adj[storeCheckValue][vec.get(2)]
                - adj[vec.get(1)][vec.get(2)];
        return cost;
    }

    public void printRouteVector(Vector<Integer> vec) {
        for (int i = 0; i < vec.size(); i++) {
            System.out.print(vec.get(i) + " ");
        }
        System.out.println("");
    }

    class priorityHelp implements Comparator<Vector<Integer>> {

        @Override
        public int compare(Vector<Integer> node1, Vector<Integer> node2) {

            int result = 0;
            double compare1 = getCostSavings(node1);
            double compare2 = getCostSavings(node2);
            if (compare1 < compare2) {
                result = 1;
            } else if (compare1 > compare2) {
                result = -1;
            } else {
                result = 0;
            }
            return result;
            //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
    
    class priorityHelpOne implements Comparator<Vector<Integer>> {

        @Override
        public int compare(Vector<Integer> node1, Vector<Integer> node2) {

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

    public void initialSavings(int val) {
        for (int i = 0; i < nodeNumber; i++) {
            if (val != i) {
                Vector<Integer> tempStore = new Vector<>();
                tempStore.add(val);
                tempStore.add(i);
                tempStore.add(val);
                initialRoute.add(tempStore);
            }
        }
    }

    public void makeSort(PriorityQueue<Vector<Integer>> pQueue) {

        // System.out.println(initialRoute.size());
        for (int i = 0; i < initialRoute.size(); i++) {
            pQueue.add(initialRoute.get(i));
        }
        // Vector<Integer>v[]=(Vector<Integer>[]) pQueue.toArray();
        /* System.out.println("**************************************");
         int size=pQueue.size();
        for(int i=0;i<size;i++){
           // System.out.println(pQueue.peek());
            Vector<Integer>v=new Vector<>();
            v=pQueue.remove();
            System.out.println(v);
            System.out.println(getCostSavings(v));
        }
        System.out.println("**********************************************");*/
        //System.out.println(pQueue.size());
    }

    public void secondaryRoute(int val) {
        Vector<Integer> tempStore;
        for (int i = 0; i < nodeNumber; i++) {
            for (int j = 0; j < nodeNumber; j++) {
                if (i != j && j != val && i != val) {

                    // System.out.println(i);
                    //System.out.println("Yes");
                    Vector<Integer> tempStoreAgain = new Vector<>();
                    //int index = initialRoute.indexOf(tempStore);
                    // initialRoute.remove(tempStore);
                    tempStoreAgain.add(val);
                    tempStoreAgain.add(i);
                    tempStoreAgain.add(j);
                    tempStoreAgain.add(val);

                    initialRoute.add(tempStoreAgain);

                }
                tempStore = new Vector<>();
                tempStore.add(val);
                tempStore.add(i);
                tempStore.add(val);
                initialRoute.remove(tempStore);
            }
        }
    }

    public boolean routeCheck(Vector<Integer> subTour, Vector<Integer> tempSubTour,
            int subtourCount) {
        boolean flag = true;

        int a = tempSubTour.get(1);
        int b = tempSubTour.get(2);

        if (subTour.contains(a) && subTour.contains(b)) {
            flag = false;
        }

        return flag;
    }

    public int getIndexofAddPos(Vector<Integer> subTour, Vector<Integer> tempSubTour,
            int subtourCount) {
        int index = -1;

        int a = tempSubTour.get(1);
        int b = tempSubTour.get(2);

        if (subTour.get(1) == b) {
            index = 1;
        } else if (subTour.get(subTour.size() - 2) == a) {
            index = subTour.size() - 2;
        }
        return index;
    }

    public boolean hamiltonianCheck(Vector<Integer> subTour) {
        boolean flag = true;
        int count = 0;
        for (int i = 0; i < nodeNumber; i++) {
            if (subTour.contains(i)) {
                count++;
            }
        }
        if (count == nodeNumber) {
            flag = true;
        } else {
            flag = false;
        }
        return flag;
    }

    public int visitedCheck(Vector<Integer> subTour[], int subtourCount) {
        int index = -1;
        for (int i = 0; i < subtourCount; i++) {
            if (hamiltonianCheck(subTour[i])) {
                index = i;
                break;
            }
        }

        return index;
    }

    public Vector<Integer> finalRoute(PriorityQueue<Vector<Integer>> pQueue) {

        int lastIndex = 0;
        Vector<Integer> lastSolution = new Vector<>();
        Vector<Integer> subTour[] = new Vector[pQueue.size()];
        Vector<Integer> tempSubTour = new Vector();
        int subtourCount = 0;
        int size = pQueue.size();
        int flag = 0;

        for (int i = 0; i < size; i++) {
            flag = 0;
            if (i == 0) {
                subTour[i] = (pQueue.remove());
                subtourCount++;
                //System.out.println(i);
                //printRouteVector(subTour[i]);
            } else {
                tempSubTour = pQueue.remove();
                for (int k = 0; k < subtourCount; k++) {

                    if (routeCheck(subTour[k], tempSubTour, subtourCount)) {
                        int index = getIndexofAddPos(subTour[k], tempSubTour, subtourCount);
                        if (index == 1) {
                            subTour[k].add(index, tempSubTour.get(1));
                        } else if (index == (subTour[k].size() - 2)) {
                            subTour[k].add(index + 1, tempSubTour.get(2));
                        }
                        if (index != -1) {
                            flag = 1;
                        }
                    }

                    //printRouteVector(tempSubTour);
                    //System.out.println(k);
                    //printRouteVector(subTour[k]);
                }
                if (flag == 0) {
                    subTour[subtourCount] = tempSubTour;
                    subtourCount++;
                }
                //subtourCount++;
            }
            // System.out.println("OK");
            int visited = visitedCheck(subTour, subtourCount);
            if (visited != -1) {
                lastIndex = visited;
                // System.out.println("OK");
                break;
            }

        }

        /*  for(int i=0;i<subtourCount;i++){
            printRouteVector(subTour[i]);
        } */
        //System.out.println(lastIndex);
        return subTour[lastIndex];
    }

    public void findSavings() {
        priorityHelpOne orderedList1 = new priorityHelpOne();
        PriorityQueue<Vector<Integer>> pQueue1 = new PriorityQueue<Vector<Integer>>(orderedList1);

        for (int i = 0; i < 10; i++) {
            Random rand = new Random();
            int checkValue = ((int) (Math.random() * Math.random() / Math.random())) % nodeNumber;
            initialRoute = new Vector<>();
            initialSavings(checkValue);
            //System.out.println(checkValue);
            //printRoute();
            secondaryRoute(checkValue);
            storeCheckValue = checkValue;
            priorityHelp orderedList = new priorityHelp();
            pQueue = new PriorityQueue<Vector<Integer>>(orderedList);
            makeSort(pQueue);
            Vector<Integer> sol = finalRoute(pQueue);
            pQueue1.add(sol);
        }
        int sz=pQueue1.size();
        
        Vector<Integer>sol=new Vector<>();
        double bestSoluton=0.0;
        double worstSolution=0.0;
        double averageSolution=0.0;
        
        for(int j=0;j<sz;j++){
            sol=pQueue1.remove();
            if(j==0){
                taskOneSol=sol;
            }
            else if(j==(sz-1)){
                worstSolution=getCost(sol);
            }
            //System.out.println(getCost(sol));
            averageSolution=averageSolution+getCost(sol);
        }
        System.out.println("Best Solution :");
        printRouteVector(taskOneSol);
        System.out.println("Best Cost : "+getCost(taskOneSol));
        System.out.println("Average Cost: "+averageSolution/sz);
        System.out.println("Worst Cost : "+worstSolution);
        
        
      /*  System.out.println("");
        printRouteVector(sol);
        System.out.println(getCost(sol));

        for (int i = 0; i < sol.size(); i++) {
            sol.set(i, sol.get(i) + 1);
        }
        sol.remove(sol.size() - 1);
        twoOptImprovement twoOpt = new twoOptImprovement(sol, adj);
        twoOpt.twoOptFunction();
        twoOpt.showResult();*/
        //printRoute();
        // System.out.println(initialRoute.size());
    }

    public Vector<Integer> finalRouteOne(PriorityQueue<Vector<Integer>> pQueue) {

        int lastIndex = 0;
        Vector<Integer> lastSolution = new Vector<>();
        Vector<Integer> subTour[] = new Vector[pQueue.size()];
        Vector<Integer> tempSubTour = new Vector();
        int subtourCount = 0;
        int size = pQueue.size();
        int flag = 0;
        ArrayList<Vector> arr = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            arr.add(pQueue.remove());
        }
        int count = 0;
        while (arr.size() != 0) {
            flag = 0;
            if (subtourCount == 0) {
                Random rand = new Random();
                int checkValue = ((int) (Math.random() * Math.random() / Math.random())) % 5;
                subTour[subtourCount] = arr.get(checkValue);
                arr.remove(checkValue);
                subtourCount++;
                //System.out.println(i);
                //printRouteVector(subTour[i]);
            } else {
                Random rand = new Random();
                int checkValue = ((int) (Math.random() * Math.random() / Math.random())) % arr.size();
                tempSubTour = arr.get(checkValue);
                arr.remove(checkValue);
                for (int k = 0; k < subtourCount; k++) {

                    if (routeCheck(subTour[k], tempSubTour, subtourCount)) {
                        int index = getIndexofAddPos(subTour[k], tempSubTour, subtourCount);
                        if (index == 1) {
                            subTour[k].add(index, tempSubTour.get(1));
                        } else if (index == (subTour[k].size() - 2)) {
                            subTour[k].add(index + 1, tempSubTour.get(2));
                        }
                        if (index != -1) {
                            flag = 1;
                        }
                    }

                    //printRouteVector(tempSubTour);
                    //System.out.println(k);
                    //printRouteVector(subTour[k]);
                }
                if (flag == 0) {
                    subTour[subtourCount] = tempSubTour;
                    subtourCount++;
                }
                //subtourCount++;
            }
            // System.out.println("OK");
            int visited = visitedCheck(subTour, subtourCount);
            if (visited != -1) {
                lastIndex = visited;
                // System.out.println("OK");
                break;
            }

        }

        /*  for(int i=0;i<subtourCount;i++){
            printRouteVector(subTour[i]);
        } */
        //System.out.println(lastIndex);
        return subTour[lastIndex];
    }

    public void findSavingsOne() {

        priorityHelpOne orderedList1 = new priorityHelpOne();
        PriorityQueue<Vector<Integer>> pQueue1 = new PriorityQueue<Vector<Integer>>(orderedList1);

        for (int i = 0; i < 10; i++) {
            Random rand = new Random();
            int checkValue = ((int) (Math.random() * Math.random() / Math.random())) % nodeNumber;
            initialRoute = new Vector<>();
            initialSavings(checkValue);
            //System.out.println(checkValue);
            //printRoute();
            secondaryRoute(checkValue);
            storeCheckValue = checkValue;
            priorityHelp orderedList = new priorityHelp();
            pQueue = new PriorityQueue<Vector<Integer>>(orderedList);
            makeSort(pQueue);
            Vector<Integer> sol = finalRouteOne(pQueue);
            pQueue1.add(sol);
        }
        int sz=pQueue1.size();
        
        Vector<Integer>sol=new Vector<>();
        double bestSoluton=0.0;
        double worstSolution=0.0;
        double averageSolution=0.0;
        
        for(int j=0;j<sz;j++){
            sol=pQueue1.remove();
            if(j==0){
                taskTwoSolOne=sol;
            }
            else if(j==1){
                taskTwoSolTwo=sol;
            }
            else if(j==2){
                taskTwoSolThree=sol;
            }
            else if(j==(sz-1)){
                worstSolution=getCost(sol);
            }
           // System.out.println(getCost(sol));
            averageSolution=averageSolution+getCost(sol);
        }
        System.out.println("Best Solution :");
        printRouteVector(taskTwoSolOne);
        System.out.println("Best Cost : "+getCost(taskTwoSolOne));
        System.out.println("Average Cost: "+averageSolution/10);
        System.out.println("Worst Cost : "+worstSolution);
        //printRoute();
        // System.out.println(initialRoute.size());
    }
    
    
    
    public void showResult(){
        Vector<Double>firstSol=new Vector<>();
        Vector<Double>bestSol=new Vector<>();
        for (int i = 0; i < taskOneSol.size(); i++) {
            taskOneSol.set(i, taskOneSol.get(i) + 1);
           
        }
        for (int i = 0; i < taskTwoSolOne.size(); i++) {
            
            taskTwoSolOne.set(i, taskTwoSolOne.get(i) + 1);
            
        }
        for (int i = 0; i < taskTwoSolTwo.size(); i++) {
           
            taskTwoSolTwo.set(i, taskTwoSolTwo.get(i) + 1);
            
        }
        for (int i = 0; i < taskTwoSolThree.size(); i++) {
           
            taskTwoSolThree.set(i, taskTwoSolThree.get(i) + 1);
        }
        taskOneSol.remove(taskOneSol.size() - 1);
        taskTwoSolOne.remove(taskTwoSolOne.size() - 1);
        taskTwoSolTwo.remove(taskTwoSolTwo.size() - 1);
        taskTwoSolThree.remove(taskTwoSolThree.size() - 1);
        
        twoOptImprovement tw=new twoOptImprovement(taskOneSol, adj);
        firstSol.add(tw.twoOptFunctionOne());
        twoOptImprovement tw2=new twoOptImprovement(taskOneSol, adj);
        bestSol.add(tw2.twoOptFunction());
        
        twoOptImprovement tw3=new twoOptImprovement(taskTwoSolOne, adj);
        firstSol.add(tw3.twoOptFunctionOne());
        twoOptImprovement tw4=new twoOptImprovement(taskTwoSolOne, adj);
        bestSol.add(tw4.twoOptFunction());
        
        twoOptImprovement tw5=new twoOptImprovement(taskTwoSolTwo, adj);
        firstSol.add(tw5.twoOptFunctionOne());
        twoOptImprovement tw6=new twoOptImprovement(taskTwoSolTwo, adj);
        bestSol.add(tw6.twoOptFunction());
        
        twoOptImprovement tw7=new twoOptImprovement(taskTwoSolThree, adj);
        firstSol.add(tw7.twoOptFunctionOne());
        twoOptImprovement tw8=new twoOptImprovement(taskTwoSolThree, adj);
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
        //System.out.println("");
       
    }
}
