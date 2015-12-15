# fsmTestCompleteness

Released: Dezember 7, 2015

Written by [Carlos Diego Damasceno](mailto:carlos.damasceno08@gmail.com) and [Sidgley Camargo de Andrade](mailto:sidgleyandrade@utfpr.edu.br)

<b>fsmTestCompleteness</b> is a implementation of the algorithm that checks whether a given test suite is complete as it is described in [Simao, Adenilso and Petrenko, Alexandre, 2010]: Checking completeness of Tests for Finite State Machines. DOI <a href="http://dx.doi.org/10.1109/TC.2010.17">dx.doi.org/10.1109/TC.2010.17</a>

## General Information

Releases: https://github.com/sidgleyandrade/fsmTestCompleteness/releases
Java Archive: https://github.com/sidgleyandrade/fsmTestCompleteness/tree/master/fsmTestCompleteness/dist

#### Dependencies

- requires JDK 7 or later
- [JGraphT](http://jgrapht.org/) 

#### Contents

fsmTestCompleteness requires two inputs: a finite state machine, and a test set. Inside the data folder have both files.

###### Finite State Machine

The format input of finite state machine is

	A -- x/n -> B; 
	
, where A is the source state, B is the target state, x is the input transition value, and n is output transition value. For example:

	A -- y/0 -> C
	C -- x/1 -> C
	C -- y/1 -> D
	D -- y/0 -> D
	D -- x/1 -> B
	B -- y/0 -> A
	A -- x/1 -> B 

###### Test Set

The test set is consisted from a input test sequence. For example, for the finite state machine above an input test set is

	yxyyy
	xyyyy
	yyyyyy
	yyxyyy

#### Testing

        java -jar fsmTestCompleteness.jar <fsm-file> <sequence-file>
        
        Example: java -jar fsmTestCompleteness.jar ./fsm-class.txt ./seq-class.txt

#### DOT Files

fsmTestCompleteness generates two DOT files into data folder: a test tree and an distinguishability graph, respectively.

###### Example output DOT files

Distinguishing graph

![alt text](https://github.com/sidgleyandrade/fsmTestCompleteness/blob/master/fsmTestCompleteness/img/seq-class.graph-dist.png?raw=true "Distinguishing graph") 

Test tree

![alt text](https://github.com/sidgleyandrade/fsmTestCompleteness/blob/master/fsmTestCompleteness/img/seq-class.test-tree.png?raw=true "Test tree")
