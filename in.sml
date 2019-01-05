class Main {
    def main() : int {
        return 10;
    }
}

class A {
    var x : int;
}

class B extends A {
    def temp() : int {
        x = x + 1;
        return x;
    }
}