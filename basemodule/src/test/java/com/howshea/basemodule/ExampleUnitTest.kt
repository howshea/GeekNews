package com.howshea.basemodule

import com.howshea.basemodule.extentions.otherwise
import com.howshea.basemodule.extentions.yes
import junit.framework.Assert
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class ExampleUnitTest {
    @Test
    fun testBoolean() {
        val result = (true).yes {
            1
        } otherwise {
            2
        }
        Assert.assertEquals(result, 1)
    }


}