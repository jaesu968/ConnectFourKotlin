fun main() {
    val report = readLine()!!
    //write your code here.
    // sample input: "20 wrong answers" , sample output: false
    // sample input 2: "1 wrong answer"  , sample output 2: true
    // regex for if a student gave 9 or less wrong answers
    val regex = Regex("[0-9] wrong answers?") // regex for if a student gave 9 or less wrong answers
    println(regex.matches(report)) // print true if the report matches the regex, false otherwise


}