package com.erge.animatorview.form

import android.text.Editable
import android.text.TextWatcher

/**
 * 需要解决的问题：
 * <ol>
 *      <li>
 *          当最后一项输入完成的时候，需要给出所有表单验证成功的结果回调
 *      </li>
 *      <li>
 *          每一项在改变输入的时候需要回调当前选项不是错误
 *      </li>
 *      <li>
 *          当焦点改变的时候上一个获取焦点的输入需要校验正确与否
 *      </li>
 *      <li>
 *          是给出一个总的回调还是只是错误项列表的回调
 *      </li>
 * </ol>
 * Created by eryemj 2021/5/31
 */
class FormValidator(
    var success: ((Boolean) -> Unit)? = null,
    var fail: ((MutableList<ValidateRuleInfo>) -> Unit)? = null
) {

    private val listRule = mutableListOf<ValidateRuleInfo>()
    private val errorListRule = mutableListOf<ValidateRuleInfo>()

    fun addRule(rule: ValidateRuleInfo) {
        if (!listRule.contains(rule)) {
            listRule.add(rule)
            rule.view.setOnFocusChangeListener { v, hasFocus ->

            }
            rule.view.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable?) {
                    if (errorListRule.size == 1 && errorListRule.contains(rule)) {
                        if (isCurrentValidate(rule)) {
                            errorListRule.clear()
                            success?.invoke(true)
                        } else {
                            errorListRule.add(rule)
                            fail?.invoke(errorListRule)
                        }
                    } else if (errorListRule.size == 0) {

                    }
                }
            })
        }
    }

    private fun isCurrentValidate(currentRule: ValidateRuleInfo): Boolean {
        return currentRule.validateRule.isValidate(currentRule.view.text.toString())
    }

    fun isAllValidate(): Boolean {
        var allValidate = true
        listRule.forEach {
            val ruleValidate = it.validateRule.isValidate(it.view.text.toString())
            it.isValidate = ruleValidate
            if (!ruleValidate) allValidate = false
        }
        return allValidate
    }

    fun checkAll() {
        val validate = isAllValidate()
        if (errorListRule.isEmpty()) {
            success?.invoke(validate)
        } else {
            fail?.invoke(errorListRule)
        }
    }

}