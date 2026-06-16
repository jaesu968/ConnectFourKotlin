package connectfour

fun main() {
    // print title of the game - Connect Four
    println("Connect Four")
    // ask for first player's name
    println("First player's name:")
    val player1 = readln()
    
    // ask for second player's name
    println("Second player's name:")
    val player2 = readln()
    // get the dimensions of the board
    // create variables for rows and columns
    var rows: Int
    var columns: Int
    // read input and parse dimensions
    // use a while loop to get valid input for dimensions
    while(true) {
        println("Set the board dimensions (Rows x Columns)")
        println("Press Enter for default (6 x 7)")
        val rawInput = readln()
        // remove all spaces and tabs to handle "whitespaces have no effect"
        val input = rawInput.replace("\\s".toRegex(), "")

        // Validate input // if input is empty, user presses enter set default to 6 x 7
        if(input.isEmpty()){
            rows = 6
            columns = 7
            break
        }

        // use a Regex to check format <number> x <number> (Case insensitive)
        val regex = Regex("(\\d+)\\s*[xX]\\s*(\\d+)") // regex pattern to match dimensions
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
        rows = r
        columns = c
        break
    }
    // lastly output the following message;
    // <1st player's name> VS <2nd player's name>
    // <Rows> X <Columns> board
    println("$player1 VS $player2")
    println("$rows X $columns board")
}