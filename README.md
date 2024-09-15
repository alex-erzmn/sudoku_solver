# sudoku_solver
Project for the course Software Engineering

Rules:
- Submit 3. Nov 2024 23:59
- GitHub Mandatory! ✅
- Report (10 Pages PDF) | The report must describe the problems, present your solution and comment on the choices made
- How-To-Run/Use-The-File-Documentation (as markdown.txt)
- Send him GitHub-Link with access
- Java ✅
- Simple Display / Interface ✅
- Grid Input Format must be an array of 81 elements with 0 when a cell is empty and numbers otherwise ❓
- The grid is linearized (lines follow each other in the table) ❓
- the GitHub repository web address must be sent by email to JC Regin (jcregin@gmail.com) before the end of the project.
- the subject of the email must only be "SE github"
- the name of the authors must be added in the GitHub and in the report
  Two precisions :
- If we the solver has no more deduction to make and if the gris is not full, then the user is asked to fix a cell. If there is an inconsistancy then the user receiv the message: "please restart the solving" ✅
- The input file format is : text file, one line per line of the sudoku : digit separated with comma. 0 means no number. Does not contain any space
  ex:
  3,8,0,1,0,0,5,9,0
  2,3 ...
  THE INPUT File format must be implemented ✅



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
