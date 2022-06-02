package com.yousuf.fhirdemo.data


/**
 * Created by Android Studio.
 * User:  Yousuf
 * Email:
 * Date:  2/6/22
 * To change this template use File | Settings | File and Code Templates.
 *
 */

sealed class TaskStatus<out T> {
    object Idle : TaskStatus<Nothing>()
    object Running : TaskStatus<Nothing>()
    data class Finished<out T>(val value: T): TaskStatus<T>()
    data class Error(val throwable: Throwable, val message:String): TaskStatus<Nothing>()
}
