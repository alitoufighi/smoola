class Main {
    def main() : int {
        1+2;
        new a().b();
        return 0;
    }
}

class NotMain{
    def f(x : int) : string{
        new A().g(2, 3);
        return "Salam";
    }

}

class A{
    var x : string;
    def g(x: int, y:int) : int {
        return 0;
    }
}