# Travelling-Salesman-Problem

In [Travelling Salesman Problem](https://en.wikipedia.org/wiki/Travelling_salesman_problem), the task is to find the shortest distance tour passing through each node of the graph exactly once. As input, you will be given the (x,y) coordinates of n cities, from which it is possible to compute the intercity distances as eucildean distance between two
cartesian coordinates. We assume a complete graph which means from any one vertex, it is possible to visit all the other (n-1) vertices using a single edge.

Two types of heuristic ways to find the solution.
(1) **Construction heuristics:** builds a solution from scratch (starting with nothing). Often called “greedy heuristics”.
(2) **Improvement heuristics (neighborhood search):** starts with a solution, and then tries to improve the solution, usually by making small changes in the current
solution.
