module com.github.ypiel.fastff3 {
    requires javafx.controls;
    requires lombok;

    exports com.github.ypiel.fastff3;

    opens com.github.ypiel.fastff3.model to javafx.base;
}