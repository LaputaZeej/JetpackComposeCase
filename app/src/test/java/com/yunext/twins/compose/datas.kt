package com.yunext.twins.compose

class A {
    var a: String = "hello"
}

open class B(val b: Int) {
    open val a: String = "hello"
    open var ccc: String = "hello"
}

class C( b: Int) {
    val c: String = "hello $b"
}

class BB(b: Int) :B(b){
    override var a:String = ""
    override var ccc:String = ""
}