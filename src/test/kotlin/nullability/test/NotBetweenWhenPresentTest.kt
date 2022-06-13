package nullability.test

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class NotBetweenWhenPresentTest {
    @Test
    fun `Test That First Null Is OK`() {
        val sourceLines = """
            package temp.kotlin.test
            
            import examples.kotlin.mybatis3.canonical.PersonDynamicSqlSupport.id
            import examples.kotlin.mybatis3.canonical.PersonDynamicSqlSupport.person
            import org.mybatis.dynamic.sql.util.kotlin.mybatis3.countFrom

            fun testFunction() {
                countFrom(person) {
                    where { id isNotBetweenWhenPresent null and 4 }
                }
            }
        """.trimIndent().lines()

        val compilerErrorReports = compileIt(sourceLines)
        assertThat(compilerErrorReports).isEmpty()
    }

    @Test
    fun `Test That Second Null Is OK`() {
        val sourceLines = """
            package temp.kotlin.test
            
            import examples.kotlin.mybatis3.canonical.PersonDynamicSqlSupport.id
            import examples.kotlin.mybatis3.canonical.PersonDynamicSqlSupport.person
            import org.mybatis.dynamic.sql.util.kotlin.mybatis3.countFrom

            fun testFunction() {
                countFrom(person) {
                    where { id isNotBetweenWhenPresent 4 and null }
                }
            }
        """.trimIndent().lines()

        val compilerErrorReports = compileIt(sourceLines)
        assertThat(compilerErrorReports).isEmpty()
    }

    @Test
    fun `Test That Both Null Is OK`() {
        val sourceLines = """
            package temp.kotlin.test
            
            import examples.kotlin.mybatis3.canonical.PersonDynamicSqlSupport.id
            import examples.kotlin.mybatis3.canonical.PersonDynamicSqlSupport.person
            import org.mybatis.dynamic.sql.util.kotlin.mybatis3.countFrom

            fun testFunction() {
                countFrom(person) {
                    where { id isNotBetweenWhenPresent null and null }
                }
            }
        """.trimIndent().lines()

        val compilerErrorReports = compileIt(sourceLines)
        assertThat(compilerErrorReports).isEmpty()
    }

    @Test
    fun `Test That First Null In Elements Method Is OK`() {
        val sourceLines = """
            package temp.kotlin.test
            
            import examples.kotlin.mybatis3.canonical.PersonDynamicSqlSupport.id
            import examples.kotlin.mybatis3.canonical.PersonDynamicSqlSupport.person
            import org.mybatis.dynamic.sql.util.kotlin.mybatis3.countFrom
            import org.mybatis.dynamic.sql.util.kotlin.elements.isNotBetweenWhenPresent

            fun testFunction() {
                countFrom(person) {
                    where { id (isNotBetweenWhenPresent<Int>(null).and(4)) }
                }
            }
        """.trimIndent().lines()

        val compilerErrorReports = compileIt(sourceLines)
        assertThat(compilerErrorReports).isEmpty()
    }

    @Test
    fun `Test That Second Null In Elements Method Is OK`() {
        val sourceLines = """
            package temp.kotlin.test
            
            import examples.kotlin.mybatis3.canonical.PersonDynamicSqlSupport.id
            import examples.kotlin.mybatis3.canonical.PersonDynamicSqlSupport.person
            import org.mybatis.dynamic.sql.util.kotlin.mybatis3.countFrom
            import org.mybatis.dynamic.sql.util.kotlin.elements.isNotBetweenWhenPresent

            fun testFunction() {
                countFrom(person) {
                    where { id (isNotBetweenWhenPresent(4).and(null)) }
                }
            }
        """.trimIndent().lines()

        val compilerErrorReports = compileIt(sourceLines)
        assertThat(compilerErrorReports).isEmpty()
    }

    @Test
    fun `Test That Both Null In Elements Method Is OK`() {
        val sourceLines = """
            package temp.kotlin.test
            
            import examples.kotlin.mybatis3.canonical.PersonDynamicSqlSupport.id
            import examples.kotlin.mybatis3.canonical.PersonDynamicSqlSupport.person
            import org.mybatis.dynamic.sql.util.kotlin.mybatis3.countFrom
            import org.mybatis.dynamic.sql.util.kotlin.elements.isNotBetweenWhenPresent

            fun testFunction() {
                countFrom(person) {
                    where { id (isNotBetweenWhenPresent<Int>(null).and(null)) }
                }
            }
        """.trimIndent().lines()

        val compilerErrorReports = compileIt(sourceLines)
        assertThat(compilerErrorReports).isEmpty()
    }
}
