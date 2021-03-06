package org.cqfn.diktat.ruleset.chapter3

import com.pinterest.ktlint.core.LintError
import generated.WarningNames
import org.cqfn.diktat.common.config.rules.RulesConfig
import org.cqfn.diktat.ruleset.constants.Warnings.FILE_WILDCARD_IMPORTS
import org.cqfn.diktat.ruleset.constants.Warnings.FILE_CONTAINS_ONLY_COMMENTS
import org.cqfn.diktat.ruleset.constants.Warnings.FILE_INCORRECT_BLOCKS_ORDER
import org.cqfn.diktat.ruleset.constants.Warnings.FILE_NO_BLANK_LINE_BETWEEN_BLOCKS
import org.cqfn.diktat.ruleset.constants.Warnings.FILE_UNORDERED_IMPORTS
import org.cqfn.diktat.ruleset.rules.DIKTAT_RULE_SET_ID
import org.cqfn.diktat.ruleset.rules.files.FileStructureRule
import org.cqfn.diktat.util.LintTestBase
import org.cqfn.diktat.util.testFileName
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

class FileStructureRuleTest : LintTestBase(::FileStructureRule) {
    private val ruleId = "$DIKTAT_RULE_SET_ID:file-structure"

    private val rulesConfigListWildCardImport: List<RulesConfig> = listOf(
            RulesConfig(FILE_WILDCARD_IMPORTS.name, true,
                    mapOf("allowedWildcards" to "import org.cqfn.diktat.example.*"))
    )

    private val rulesConfigListWildCardImports: List<RulesConfig> = listOf(
            RulesConfig(FILE_WILDCARD_IMPORTS.name, true,
                    mapOf("allowedWildcards" to "import org.cqfn.diktat.example.*, import org.cqfn.diktat.ruleset.constants.Warnings.*"))
    )

    @Test
    @Tag(WarningNames.FILE_CONTAINS_ONLY_COMMENTS)
    fun `should warn if file contains only comments`() {
        lintMethod(
                """
                |package org.cqfn.diktat.example
                |
                |/**
                | * This file appears to be empty
                | */
                |
                |import org.cqfn.diktat.example.Foo
                |
                |// lorem ipsum
            """.trimMargin(),
                LintError(1, 1, ruleId, "${FILE_CONTAINS_ONLY_COMMENTS.warnText()} $testFileName", false)
        )
    }

    @Test
    @Tag(WarningNames.FILE_INCORRECT_BLOCKS_ORDER)
    fun `should warn if file annotations are not immediately before package directive`() {
        lintMethod(
                """
                |@file:JvmName("Foo")
                |
                |/**
                | * This is an example file
                | */
                |
                |package org.cqfn.diktat.example
                |
                |class Example { }
            """.trimMargin(),
                LintError(1, 1, ruleId, "${FILE_INCORRECT_BLOCKS_ORDER.warnText()} @file:JvmName(\"Foo\")", true),
                LintError(3, 1, ruleId, "${FILE_INCORRECT_BLOCKS_ORDER.warnText()} /**", true)
        )
    }

    @Test
    @Tag(WarningNames.FILE_UNORDERED_IMPORTS)
    fun `should warn if imports are not sorted alphabetically`() {
        lintMethod(
                """
                |package org.cqfn.diktat.example
                |
                |import org.junit.Test
                |import org.cqfn.diktat.example.Foo
                |
                |class Example { }
            """.trimMargin(),
                LintError(3, 1, ruleId, "${FILE_UNORDERED_IMPORTS.warnText()} $testFileName", true)
        )
    }

    @Test
    @Tag(WarningNames.FILE_WILDCARD_IMPORTS)
    fun `should warn if wildcard imports are used`() {
        lintMethod(
                """
                |package org.cqfn.diktat.example
                |
                |import org.cqfn.diktat.example.*
                |
                |class Example { }
            """.trimMargin(),
                LintError(3, 1, ruleId, "${FILE_WILDCARD_IMPORTS.warnText()} import org.cqfn.diktat.example.*", false)
        )
    }

    @Test
    @Tag(WarningNames.FILE_NO_BLANK_LINE_BETWEEN_BLOCKS)
    fun `should warn if blank lines are wrong between code blocks`() {
        lintMethod(
                """
                |/**
                | * This is an example
                | */
                |@file:JvmName("Foo")
                |
                |
                |package org.cqfn.diktat.example
                |import org.cqfn.diktat.example.Foo
                |class Example
            """.trimMargin(),
                LintError(1, 1, ruleId, "${FILE_NO_BLANK_LINE_BETWEEN_BLOCKS.warnText()} /**", true),
                LintError(4, 1, ruleId, "${FILE_NO_BLANK_LINE_BETWEEN_BLOCKS.warnText()} @file:JvmName(\"Foo\")", true),
                LintError(7, 1, ruleId, "${FILE_NO_BLANK_LINE_BETWEEN_BLOCKS.warnText()} package org.cqfn.diktat.example", true),
                LintError(8, 1, ruleId, "${FILE_NO_BLANK_LINE_BETWEEN_BLOCKS.warnText()} import org.cqfn.diktat.example.Foo", true)
        )
    }

    @Test
    @Tag(WarningNames.FILE_WILDCARD_IMPORTS)
    fun `wildcard imports are used but with config`() {
        lintMethod(
                """
                |package org.cqfn.diktat.example
                |
                |import org.cqfn.diktat.example.*
                |
                |class Example { }
            """.trimMargin(),rulesConfigList = rulesConfigListWildCardImport
        )
    }

    @Test
    @Tag(WarningNames.FILE_WILDCARD_IMPORTS)
    fun `wildcard imports are used but with several imports in config`() {
        lintMethod(
                """
                |package org.cqfn.diktat.example
                |
                |import org.cqfn.diktat.example.*
                |import org.cqfn.diktat.ruleset.constants.Warnings.*
                |
                |class Example { }
            """.trimMargin(),rulesConfigList = rulesConfigListWildCardImports
        )
    }
}
