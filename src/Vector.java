public class Vector {
    public double x, y;
    public Vector (double x, double y) {
        this.x = x;
        this.y = y;
    }
    @Override
    public String toString() {
        return "{" + String.valueOf(x) + ", " + String.valueOf(y) + "}";
    }
    public void add(Vector b) {
        this.x += b.x;
        this.y += b.y;
    }
    public void mul(double a) {
        this.x *= a;
        this.y *= a;
    }
    public double scalar (Vector b) {
        return this.x * b.x + this.y * b.y;
    }
    public double pseudo_scalar (Vector b) {
        return this.x * b.y - this.y * b.x;
    }
}
