package test.chapter2.kdoc

class Example {

    /**
     * Empty function to test KDocs
     * @deprecated   Use testNew
     * @apiNote stuff
     * @implSpec   spam
     *
     * Another line of description
     * @param   a   useless integer

     * @throws RuntimeException never
     *
     * @return doubled value
     */

    fun test(a: Int): Int = 2 * a
}