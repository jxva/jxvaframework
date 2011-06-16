println("Start script \r\n");

function printType(obj) {
    if (obj.getClass)
        println("    Java object: " + obj.getClass().name);
    else
        println("    JS object: " + obj.toSource());
    println("");
}

function demoFunction(a, b) {
    println("[JS] a: " + a);
    printType(a);
    println("[JS] b: " + b);
    printType(b);
    var c = a + b;
    println("[JS] c: " + c);
    printType(c);
    return c;
}

println("End script \r\n");