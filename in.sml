class Main {
    def main() : int {
        1+2;
        new A().g(1);
        return 0;
    }
}

class NotMain{
    def f(x : int) : int{
        var arr : int[];
        var a : int;
        arr = new int[5];
        arr[x] = 3;
        a = new A().g(2,2);
        2+3;
        return arr[x];
    }
}

class A{
    var x : NotMain;
    def g(x: int, y:int) : A {
        return new A();
    }
    def h() :int{
        return 0;
    }
}

class B extends A {
    def x() : int {
        x = new NotMain();
        return x.f(5);
    }

}