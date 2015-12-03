# fsmTestCompleteness
Implementation of algorithm that checks whether a given test suite is complete, according Simao, Adenilso and Petrenko, Alexandre (2010) - Checking completeness of Tests for Finite State Machines. DOI dx.doi.org/10.1109/TC.2010.17

Format input
------------
A -- x/n -> B; A is the source state (vertex), B is the target state, x is the input value of edge, and n is output value of edge.

Example

A -- y/0 -> C
C -- x/1 -> C
C -- y/1 -> D
D -- y/0 -> D
D -- x/1 -> B
B -- y/0 -> A
A -- x/1 -> B 

Program steps
-------------
1. Create Finite State Machine (FSM)
