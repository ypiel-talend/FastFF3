module com.github.ypiel.fastff3 {
    requires java.net.http;
    requires javafx.controls;
    requires lombok;
    requires org.json;
    requires com.google.gson;
    requires org.apache.logging.log4j;
    requires spring.context;
    requires spring.beans;
    requires spring.core;

    exports com.github.ypiel.fastff3;

    opens com.github.ypiel.fastff3.model to javafx.base;
    opens com.github.ypiel.fastff3.spring to spring.core,spring.beans,spring.context;
    opens com.github.ypiel.fastff3.service to spring.core,spring.beans,spring.context;
}