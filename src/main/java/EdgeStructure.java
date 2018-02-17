public class EdgeStructure {
    private String to;
    private String from;

    public EdgeStructure(String from, String to) {
        this.from = from;
        this.to = to;
    }

    public String to() {
        return to;
    }

    public String from() {
        return from;
    }
}