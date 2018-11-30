class Main {
    def main() : int {
        return x;
    }
}

class d{
    var y : string;
    def g() : string {
        return "Res";
    }
}

class c extends d{
    var y : string;
    def a() : int {
        return 3;
    }
}




class a extends d{
    var x : int[];
    def y(z: int, b: int[]) : int {
        var v: int;
        v = new int[1];
        return 4;
    }
}


class Test{
    def main(): int{
        writeln((new BabyTest().testMethod(1,2)));
        return 0;
    }
}

class BabyTest{
    var test1: int[];
    var test2: boolean;
    def testMethod(f1: int, f2: int) : int {
        var i: int;
        i = 0;
        test1 = new int[10];
        while(i <> 10){
            test1[i] = i;
        }
        if(test[1]==1)then
            test2=true;
        else{
            test2= false;
        }
        return test2;
    }
}

#class Test{
#    def main(): int{
#        writeln((new Class1()).testMethod("hi there!"));
#        return 0;
#        # Comment!
#    }
#}

class MainClass{
    def main(): int{
        return new Test2().method2();
    }
}

class Test1{
    var i: int;
    def method1(): string{
        var j: string;
        j = "hello world!";
        return k;
    }
}
class Test2 extends Test1{
    def method2(): int{
        i = 10;
        return i;
    }
}