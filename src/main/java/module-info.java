module com.github.ypiel.fastff3 {
    requires java.net.http;
    requires javafx.controls;
    requires lombok;
    requires org.json;
    requires com.google.gson;
    requires org.apache.logging.log4j;

    exports com.github.ypiel.fastff3;

    opens com.github.ypiel.fastff3.model to javafx.base;
}