class Main {
    def main() : int {
        return x;
    }
}


class d {
    def b() : String {
        return "Salam";
    }
}

class c extends d{
    def a() : int {
        return 0;
    }
}

class a extends c{
    var x : int[];
    def b(c : int, e : d) : int {
        return c - this.f()[5];
    }
    def f() : int {
        return 0;
    }
}
