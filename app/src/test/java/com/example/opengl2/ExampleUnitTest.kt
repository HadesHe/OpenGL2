package com.example.opengl2

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        var subject = Subject("yuwem")
        var studentA = Student()
        studentA.apply {
            this.subject = subject
            name = "Lynn"
            age = 20
        }
        var studentB = studentA.clone() as Student
        studentB.apply {
            name = "Lily"
            age = 18
            this.subject?.name = "lishi"
        }
        System.out.println("studentA ${studentA.toString()}")
        System.out.println("studentB ${studentB.toString()}")


    }


}

class Student(var subject: Subject? = null, var name: String? = null, var age: Int = 0) :
    Cloneable {

    public override fun clone(): Any {
        return try {
            var student = super.clone() as Student
            student.subject = (subject?.clone()) as Subject
            return student
        } catch (e: CloneNotSupportedException) {
            e.printStackTrace()
        }
    }

    override fun toString(): String {
        return "Student(subject=$subject, name='$name', age=$age)"
    }


}

data class Subject(var name: String) : Cloneable {
    public override fun clone(): Any {
        return super.clone()
    }
}
