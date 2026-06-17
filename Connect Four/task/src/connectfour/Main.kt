package connectfour

// Data class to keep player information together
data class Players(val first: String, val second: String)

// Data class for dimensions
data class Dimensions(val rows: Int, val columns: Int)

fun main() {
    // print title of the game - Connect Four
    println("Connect Four")

    // get players
    val players = getPlayerNames()
    val dimensions = getBoardDimensions()

    // print players and dimensions
    println("${players.first} VS ${players.second}")
    println("${dimensions.rows} X ${dimensions.columns} board")

    // initialize the board to keep track of state
    val board = MutableList(dimensions.rows) { MutableList(dimensions.columns) { ' ' } }
    printBoard(dimensions.rows, dimensions.columns, board)

    playGame(players, dimensions, board)

}

// Function to handle player naming
fun getPlayerNames(): Players {
    // ask for first player's name
    println("First player's name:")
    val p1 = readln()

    // ask for second player's name
    println("Second player's name:")
    val p2 = readln()
    // return players to be used in main function
    return Players(p1, p2)
}

// Function to handle dimension input and validation
fun getBoardDimensions(): Dimensions {
    val regex = Regex("(\\d+)\\s*[xX]\\s*(\\d+)") // regex pattern to match dimensions

    // use a while loop to set up the dimensions
    while(true) {
        println("Set the board dimensions (Rows x Columns)")
        println("Press Enter for default (6 x 7)")
        val rawInput = readln()
        // remove all spaces and tabs to handle "whitespaces have no effect"
        val input = rawInput.replace("\\s".toRegex(), "")

        // Validate input // if input is empty, user presses enter set default to 6 x 7
        if(input.isEmpty()) return Dimensions(6, 7)

        // make a variable to track for all input of the dimensions of the board
        val match = regex.matchEntire(input) // use matchEntire to check if the whole input matches the pattern
        // check if input matches the regex pattern
        if(match == null){
            // if no match, print "Invalid input"
            println("Invalid input")
            continue
        }
        // If there is a match, then extract the numbers for rows and columns
        val r = match.groupValues[1].toInt() // get rows
        val c = match.groupValues[2].toInt() // get columns
        // validate the range of rows and columns (5 to 9)
        if(r !in 5..9){
            println("Board rows should be from 5 to 9")
            continue
        }
        if(c !in 5..9){
            println("Board columns should be from 5 to 9")
            continue
        }
        // if valid, set the rows and columns and break the loop
        return Dimensions(r, c)
    }
}

// function to run the actual game loop
fun playGame(players: Players, dims: Dimensions, board: MutableList<MutableList<Char>>){
    // implement the game loop
    // make a boolean flag to track whose turn it is
    var isFirstPlayerTurn = true

    // use a while loop to go through game actions
    while(true){
        // get current Player for the turn
        val currentPlayer = if (isFirstPlayerTurn) players.first else players.second
        // get the current Disc to use for play
        val currentDisc = if (isFirstPlayerTurn) 'o' else '*'

        // print the current players turn
        println("$currentPlayer's turn:")
        // get input from user
        val input = readln().trim() // trim off the whitespace

        // if input is "end", then terminate the game
        if(input == "end") {
            println("Game over!")
            break
        }

        // check if input is an integer
        val col = input.toIntOrNull()
        if (col == null){
            println("Incorrect column number")
            continue
        }

        // check if column is within range
        if(col !in 1..dims.columns){
            println("The column number is out of range (1 - ${dims.columns})")
            continue
        }

        // check if the column is full and find the first available row
        val rowToPlace = findAvailableRow(col, dims.rows, board)
        if(rowToPlace == -1){
            println("Column $col is full")
            continue
        }

        // Update the board and print it
        board[rowToPlace][col - 1] = currentDisc
        printBoard(dims.rows, dims.columns, board)

        // Check for win condition after each move
        val winMessage = checkWinCondition(board, currentDisc, currentPlayer)
        if (winMessage.isNotEmpty()){
            println(winMessage)
            println("Game over!")
            return // terminate the game after a win or draw
        }

        // Switch turns
        isFirstPlayerTurn = !isFirstPlayerTurn
    }
}

// Helper function to find the first empty row in a column
fun findAvailableRow(col: Int, rows: Int, board: List<List<Char>>): Int {
    for (i in rows - 1 downTo 0) {
        if (board[i][col - 1] == ' ') return i
    }
    return - 1 // return -1 if no valid row
}

// print the board function to get the current state of the board
fun printBoard(rows: Int, columns: Int, board: List<List<Char>>){
    // Print column numbers
    for (i in 1..columns){
        print(" $i")
    }
    println() // print empty space for separation

    // print rows with vertical characters and board
    for (i in 0 until rows){
        for (j in 0 until columns){
            print("║${board[i][j]}")
        }
        println("║") // print right and left side borders
    }
    // print the bottom border
    print("╚") // print left side bottom corner
    // print middle bottom border
    for (i in 1 until columns){
        print("═╩")
    }
    println("═╝") // print right side bottom corner
}

// function to check for win condition
fun checkWinCondition(board: List<List<Char>>, disc: Char, playerName: String): String {

    // check horizontal, vertical, and diagonal for 4 in a row
    // if 4 discs of the same color exist in a row, horizontally, vertically or diagonally
    // then return "Player <Player's name> won"
    // use a for loop to check for horizontal, vertical and diagonal conditions
    for(i in board.indices){ // loop through rows
        for(j in board[i].indices){ // loop through columns
            // check horizontal
            if(j + 3 < board[i].size && board[i][j] == disc && board[i][j + 1] == disc && board[i][j + 2] == disc && board[i][j + 3] == disc){
                return "Player $playerName won"
            }
            // check vertical
            if(i + 3 < board.size && board[i][j] == disc && board[i + 1][j] == disc && board[i + 2][j] == disc && board[i + 3][j] == disc){
                return "Player $playerName won"
            }
            // check diagonal down-right
            if(i + 3 < board.size && j + 3 < board[i].size && board[i][j] == disc && board[i + 1][j + 1] == disc && board[i + 2][j + 2] == disc && board[i + 3][j + 3] == disc){
                return "Player $playerName won"
            }
            // check diagonal down-left
            if(i + 3 < board.size && j - 3 >= 0 && board[i][j] == disc && board[i + 1][j - 1] == disc && board[i + 2][j - 2] == disc && board[i + 3][j - 3] == disc){
                return "Player $playerName won"
            }
        }
    }
    // if the board is full and no winner, then return "It is a draw"
    if(board.all { row -> row.all { cell -> cell != ' ' } }){
        return "It is a draw"
    }
    return "" // game continues
}