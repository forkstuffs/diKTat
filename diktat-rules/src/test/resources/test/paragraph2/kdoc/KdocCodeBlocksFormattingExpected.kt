package test.paragraph2.kdoc

/**
 * This is a test kDoc Comment
 */
class SomeClass {
/* block comment to func */
    fun testFunc() {
        val a = 5 // Right side comment good

        /* General if comment */
        if (a == 5) {

        }
        else {
// Some Comment
        }
    }

/**
     * This is a useless function
     */
    fun someUselessFunction() {
// This is a useless value
        val uselessValue = 1
    }
}