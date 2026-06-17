# Connect Four (Kotlin)

A multi-stage implementation of the classic Connect Four game in Kotlin.

## Stage 1: Input Game Parameters

In this first stage, the program focuses on setting up the game environment by collecting player information and defining the board dimensions.

### Key Concepts & Features

- **Player Identification**: Collects names for two players.
- **Dynamic Board Sizing**: 
    - The Default size is **6x7**.
    - Customizable dimensions ranging from **5x5 to 9x9**.
- **Robust Input Parsing**: 
    - Uses Regular Expressions to validate the `Rows x Columns` format.
    - Handles case-insensitivity (e.g., `6x7`, `6X7`).
    - Ignores all whitespaces (spaces and tabs) in the dimension input.
- **Input Validation**: 
    - Ensures dimensions are within the 5 to 9 range.
    - Re-prompts the user until valid input is provided.

### Technical Implementation

#### 1. Handling Whitespaces
To satisfy the requirement that "Whitespaces have no effect," the program strips all whitespace characters from the input string before validation.

```kotlin
val rawInput = readln()
val input = rawInput.replace("\\s".toRegex(), "")
```

#### 2. Format Validation with Regex
A Regular Expression is used to ensure the input follows the `<number>x<number>` pattern strictly.

```kotlin
val regex = Regex("(\\d+)[xX](\\d+)")
val match = regex.matchEntire(input)

if (match == null) {
    println("Invalid input")
    // ... continue loop
}
```

#### 3. Dimension Extraction and Range Check
If the format is correct, the numbers are extracted and checked against the allowed range (5 to 9).

```kotlin
val rows = match.groupValues[1].toInt()
val columns = match.groupValues[2].toInt()

if (rows !in 5..9) {
    println("Board rows should be from 5 to 9")
}
```

### Usage Example

```text
Connect Four
First player's name:
> Anna
Second player's name:
> Joan
Set the board dimensions (Rows x Columns)
Press Enter for default (6 x 7)
> 7 x 9
Anna VS Joan
7 X 9 board
```

## Stage 2: Game Board

In the second stage, the program draws the game board using box-drawing characters based on the dimensions provided.

### Key Concepts & Features

- **Column Numbering**: Automatically prints column numbers (1 to N) aligned with the board columns.
- **Dynamic Board Rendering**: 
    - Uses `║` for vertical walls.
    - Uses `╚`, `═`, `╩`, and `╝` for the bottom border.
- **Support for Variable Sizes**: The board structure adjusts perfectly to any valid dimensions (5x5 to 9x9).
- **Fallback Compatibility**: Although the program uses box-drawing characters, it can be easily adapted to use `|` and `=` if the console doesn't support UTF-8 characters.

### Technical Implementation

#### 1. Printing Column Numbers
Numbers are printed with a leading space to align with the board slots.

```kotlin
for (i in 1..columns) {
    print(" $i")
}
println()
```

#### 2. Drawing Rows and Walls
Each row consists of `columns + 1` vertical bars (`║`) to create the slots.

```kotlin
repeat(rows) {
    repeat(columns + 1) {
        print("║ ")
    }
    println()
}
```

#### 3. Constructing the Bottom Border
The bottom border is built dynamically to connect all vertical lines.

```kotlin
print("╚")
repeat(columns - 1) {
    print("═╩")
}
println("═╝")
```

### Usage Example

```text
Connect Four
First player's name:
> Sophia
Second player's name:
> John
Set the board dimensions (Rows x Columns)
Press Enter for default (6 x 7)
> 8 x 8
Sophia VS John
8 X 8 board
 1 2 3 4 5 6 7 8
║ ║ ║ ║ ║ ║ ║ ║ ║
║ ║ ║ ║ ║ ║ ║ ║ ║
║ ║ ║ ║ ║ ║ ║ ║ ║
║ ║ ║ ║ ║ ║ ║ ║ ║
║ ║ ║ ║ ║ ║ ║ ║ ║
║ ║ ║ ║ ║ ║ ║ ║ ║
║ ║ ║ ║ ║ ║ ║ ║ ║
║ ║ ║ ║ ║ ║ ║ ║ ║
╚═╩═╩═╩═╩═╩═╩═╩═╝
```

## Stage 3: Game Logic

In the third stage, the program adds the core game loop, handling player turns, disc placement, and input validation for moves.

### Key Concepts & Features

- **Turn-based Gameplay**: Alternates between the first player (`o`) and the second player (`*`).
- **Disc Placement**: Discs "fall" to the lowest available row in the selected column.
- **Input Validation**:
    - Validates that the input is a number.
    - Ensures the column number is within the board's range.
    - Checks if the selected column is full.
- **Graceful Termination**: Players can type `end` at any time to stop the game.

### Technical Implementation

#### 1. Game Loop and Turn Switching
A `while(true)` loop keeps the game running, and a boolean flag `isFirstPlayerTurn` toggles after every valid move.

```kotlin
var isFirstPlayerTurn = true
while(true) {
    val currentPlayer = if (isFirstPlayerTurn) players.first else players.second
    val currentDisc = if (isFirstPlayerTurn) 'o' else '*'
    // ... get input and process move
    isFirstPlayerTurn = !isFirstPlayerTurn
}
```

#### 2. Finding the Available Row
The program iterates from the bottom row upwards to find the first empty slot (`' '`) in the chosen column.

```kotlin
fun findAvailableRow(col: Int, rows: Int, board: List<List<Char>>): Int {
    for (i in rows - 1 downTo 0) {
        if (board[i][col - 1] == ' ') return i
    }
    return -1
}
```

#### 3. Handling Input Errors
The program handles various input errors by providing specific feedback and re-prompting the current player.

```kotlin
val col = input.toIntOrNull()
if (col == null) {
    println("Incorrect column number")
    continue
}

if (col !in 1..dims.columns) {
    println("The column number is out of range (1 - ${dims.columns})")
    continue
}
```

### Usage Example

```text
Mia VS Bill
6 X 8 board
 1 2 3 4 5 6 7 8
║ ║ ║ ║ ║ ║ ║ ║ ║
║ ║ ║ ║ ║ ║ ║ ║ ║
║ ║ ║ ║ ║ ║ ║ ║ ║
║ ║ ║ ║ ║ ║ ║ ║ ║
║ ║ ║ ║ ║ ║ ║ ║ ║
║ ║ ║ ║ ║ ║ ║ ║ ║
╚═╩═╩═╩═╩═╩═╩═╩═╝
Mia's turn:
> 4
 1 2 3 4 5 6 7 8
║ ║ ║ ║ ║ ║ ║ ║ ║
║ ║ ║ ║ ║ ║ ║ ║ ║
║ ║ ║ ║ ║ ║ ║ ║ ║
║ ║ ║ ║ ║ ║ ║ ║ ║
║ ║ ║ ║ ║ ║ ║ ║ ║
║ ║ ║ ║o║ ║ ║ ║ ║
╚═╩═╩═╩═╩═╩═╩═╩═╝
Bill's turn:
> 4
...
Mia's turn:
> end
Game over!
```

## Stage 4: Winning Condition

In the fourth stage, the program implements the winning condition and draw logic, ensuring the game ends correctly when a player wins or the board is full.

### Key Concepts & Features

- **Winning Conditions**: A player wins by placing four discs of the same color in a row:
    - **Horizontally**
    - **Vertically**
    - **Diagonally** (both directions)
- **Draw Condition**: If the board is full and no player has won, the game ends in a draw.
- **Game Termination**: The program prints the result (`Player <Name> won` or `It is a draw`) followed by `Game over!` and terminates.

### Technical Implementation

#### 1. Win Check Algorithm
The program checks for four consecutive discs by iterating through each cell and checking all possible directions (horizontal, vertical, and two diagonals).

```kotlin
for (i in board.indices) {
    for (j in board[i].indices) {
        // Horizontal
        if (j + 3 < board[i].size && board[i][j] == disc && board[i][j + 1] == disc && board[i][j + 2] == disc && board[i][j + 3] == disc) return true
        // Vertical
        if (i + 3 < board.size && board[i][j] == disc && board[i + 1][j] == disc && board[i + 2][j] == disc && board[i + 3][j] == disc) return true
        // Diagonal down-right
        if (i + 3 < board.size && j + 3 < board[i].size && board[i][j] == disc && board[i + 1][j + 1] == disc && board[i + 2][j + 2] == disc && board[i + 3][j + 3] == disc) return true
        // Diagonal down-left
        if (i + 3 < board.size && j - 3 >= 0 && board[i][j] == disc && board[i + 1][j - 1] == disc && board[i + 2][j - 2] == disc && board[i + 3][j - 3] == disc) return true
    }
}
```

#### 2. Draw Check
The game checks if all cells on the board are occupied using the `all` function on the board lists.

```kotlin
if (board.all { row -> row.all { cell -> cell != ' ' } }) {
    return "It is a draw"
}
```

### Usage Example

```text
Paul's turn:
> 5
 1 2 3 4 5 6 7 8
║ ║ ║ ║ ║ ║ ║ ║ ║
║ ║ ║ ║ ║ ║ ║ ║ ║
║ ║ ║ ║ ║ ║ ║ ║ ║
║ ║ ║ ║ ║ ║ ║ ║ ║
║ ║*║*║*║ ║ ║ ║ ║
║ ║ο║ο║ο║ο║ ║ ║ ║
╚═╩═╩═╩═╩═╩═╩═╩═╝
Player Paul won
Game over!
```
