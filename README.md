# sudoku_solver
Project for the course Software Engineering
Authors: Alexander Erzmann, Mikita Sidarenka

This project is a Sudoku Solver. The goal is to solve a Sudoku grid using deduction rules. 
The user can create a sudoku and the program will solve it step by step.

## How to run the program
1. Clone the repository
2. Open the project in your favorite IDE or in the folder where you cloned the repository
3. Run the Main class or the SudokuSolver-1.0-SNAPSHOT.jar (target folder)
4. The GUI will open
5. Click on the "Start" button
6. By clicking on the "Create Sudoku" button, you can create a new Sudoku considering the chosen difficulty
7. Alternatively, you can upload a Sudoku from a file by clicking on the "Upload Sudoku" button 
   the file should be a .txt document in the following format:

3,0,1,0,8,6,5,0,4
0,4,6,5,2,1,0,7,0
5,0,0,0,0,0,0,0,1
4,0,0,8,0,0,0,0,2
0,8,0,3,4,7,9,0,0
0,0,9,0,5,0,0,3,8
0,0,4,0,9,0,2,0,0
0,0,8,7,3,4,0,9,0
0,0,7,2,0,8,1,0,3

0,4,8,3,0,1,5,6,0
3,6,0,0,0,8,0,9,0
9,1,0,6,7,0,0,0,3
0,2,0,0,0,0,9,3,5
5,0,9,0,1,0,2,0,0
6,7,0,0,2,0,0,1,0
0,0,4,0,0,2,1,0,7
0,9,0,1,0,0,0,0,8
1,5,0,8,3,4,0,2,9

In case the file contains more than one Sudoku, the program will choose a random one to solve. So make sure to have 
only one Sudoku in the file. If a specific Sudoku is desired to be solved.

8. By clicking on the "Solve Sudoku" button, you can solve the Sudoku step by step
9. You can enter values in the cells by clicking on them and typing the number
10. You are forced to enter a value in the cells whenever the deduction rules are not able to continue solving the Sudoku
11. As soon as the Sudoku is solved or has noticed that there is an inconsistency, you can restart by creating a new sudoku

Enjoy!