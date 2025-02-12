package design_pattern.factory;

public class JavaConsumer {
    public static void main(String[] args) {
        ShapeFactory.getShape("CIRCLE").draw();
    }
}
