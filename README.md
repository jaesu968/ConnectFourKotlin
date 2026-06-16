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
