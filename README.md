# sudoku_solver
Project for the course Software Engineering
**Submit 3. Nov 2024 23:59**

This project is a Sudoku Solver. The goal is to solve a Sudoku grid using deduction rules. 
The user can input a grid and the program will solve it. The program will also check if the
grid is valid or not. The program will display the grid in a GUI.
The authors of this project are:
- Alexander Erzmann
- Nikita

## How to run the program
1. Clone the repository
2. Open the project in your favorite IDE
3. Run the Main class
4. The GUI will open
5. Click on the "Start" button
6. By clicking on the "Create Sudoku" button, you can create a new Sudoku
7. By clicking on the "Solve Sudoku" button, you can solve the Sudoku step by step
8. You can enter values in the cells by clicking on them and typing the number




PROGRESS:

- GitHub Mandatory! ✅
- Java ✅
- Simple Display / Interface ✅
- If we the solver has no more deduction to make and if the gris is not full, then the user is asked to fix a cell. If there is an inconsistancy then the user receiv the message: "please restart the solving" ✅
- The input file format is : text file, one line per line of the sudoku : digit separated with comma. 0 means no number. Does not contain any space
  ex:
  3,8,0,1,0,0,5,9,0
  2,3 ...
  THE INPUT File format must be implemented ✅

- Report (10 Pages PDF) | The report must describe the problems, present your solution and comment on the choices made
- How-To-Run/Use-The-File-Documentation (as markdown.txt)
- Send him GitHub-Link with access to the repository
- Grid Input Format must be an array of 81 elements with 0 when a cell is empty and numbers otherwise ❓
- The grid is linearized (lines follow each other in the table) ❓
- the GitHub repository web address must be sent by email to JC Regin (jcregin@gmail.com) before the end of the project.
- the subject of the email must only be "SE github"
- the name of the authors must be added in the GitHub and in the report




Use 4 Design Pattern:
- Singleton Pattern (Factories) ✅
- Strategy Pattern (Choose Deduction Rules) ✅
- Factory Pattern (Create Deduction Rules and Sudokus) ✅
- Observer Pattern (GUI and Checker observe the Sudoku) ✅
- Facade Pattern (GUI - Controller) ✅

Use 3 Deduction Rules:
Abstract Class DeductionRule
- Naked Single (DR1) ✅
- Hidden Single (DR2) ✅
- Naked Pair (DR3)
- Hidden Pair (DR4)
- Box-Line Reduction (DR5)
- X-Wing (DR6)
- Swordfish (DR7)
- Jellyfish (DR8)
- XY-Wing (DR9)
- XYZ-Wing (DR10)
- ...