package test.chapter3.empty_block

fun foo () {
    try {
        doSome()
    } catch (ex: Exception){}
}

fun goo () {
    var x = 10
    if (x == 10) return else println(10)
    val y = listOf<Int>().map {}
    for(x in 0..10) println(x)
    while (x > 0)
        --x
}