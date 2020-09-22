package org.cqfn.diktat.ruleset.chapter3

import com.pinterest.ktlint.core.LintError
import generated.WarningNames
import org.cqfn.diktat.ruleset.rules.DIKTAT_RULE_SET_ID
import org.cqfn.diktat.ruleset.constants.Warnings.NULLABLE_PROPERTY_TYPE
import org.cqfn.diktat.ruleset.rules.NullableTypeRule
import org.cqfn.diktat.util.LintTestBase
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

class NullableTypeRuleWarnTest : LintTestBase(::NullableTypeRule) {

    private val ruleId = "$DIKTAT_RULE_SET_ID:nullable-type"

    @Test
    @Tag(WarningNames.NULLABLE_PROPERTY_TYPE)
    fun `check simple property`() {
        lintMethod(
                """
                    |val a: List<Int>? = null
                    |val a: Int? = null
                    |val b: Double? = null
                    |val c: String? = null
                    |val a: MutableList<Int>? = null
                """.trimMargin(),
                LintError(1, 21, ruleId, "${NULLABLE_PROPERTY_TYPE.warnText()} initialize explicitly", true),
                LintError(2, 15, ruleId, "${NULLABLE_PROPERTY_TYPE.warnText()} initialize explicitly", true),
                LintError(3, 18, ruleId, "${NULLABLE_PROPERTY_TYPE.warnText()} initialize explicitly", true),
                LintError(4, 18, ruleId, "${NULLABLE_PROPERTY_TYPE.warnText()} initialize explicitly", true),
                LintError(5, 28, ruleId, "${NULLABLE_PROPERTY_TYPE.warnText()} initialize explicitly", true)
        )
    }

    @Test
    @Tag(WarningNames.NULLABLE_PROPERTY_TYPE)
    fun `check property in object`() {
        lintMethod(
                """
                    |class A {
                    |   companion object {
                    |       val a: Int? = null
                    |       val b: Int? = 0
                    |       val c: Boolean? = true
                    |   }
                    |}
                """.trimMargin(),
                LintError(3, 22, ruleId, "${NULLABLE_PROPERTY_TYPE.warnText()} initialize explicitly", true),
                LintError(4, 15, ruleId, "${NULLABLE_PROPERTY_TYPE.warnText()} don't use nullable type", false),
                LintError(5, 15, ruleId, "${NULLABLE_PROPERTY_TYPE.warnText()} don't use nullable type", false)
        )
    }

    @Test
    @Tag(WarningNames.NULLABLE_PROPERTY_TYPE)
    fun `check nullable type with initialize`() {
        lintMethod(
                """
                    |class A {
                    |   companion object {
                    |       val a: Int? = 0
                    |       val b: Int? = null
                    |       val c: Boolean? = false
                    |   }
                    |}
                """.trimMargin(),
                LintError(3, 15, ruleId, "${NULLABLE_PROPERTY_TYPE.warnText()} don't use nullable type", false),
                LintError(4, 22, ruleId, "${NULLABLE_PROPERTY_TYPE.warnText()} initialize explicitly", true),
                LintError(5, 15, ruleId, "${NULLABLE_PROPERTY_TYPE.warnText()} don't use nullable type", false)
        )
    }
}
