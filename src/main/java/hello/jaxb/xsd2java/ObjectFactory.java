//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-146 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.07.29 at 03:36:38 PM CDT 
//


package hello.jaxb.xsd2java;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the hello.jaxb.xsd2java package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Greeting_QNAME = new QName("http://www.big-oh.net/hello/jaxb", "greeting");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: hello.jaxb.xsd2java
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Greeting }
     * 
     */
    public Greeting createGreeting() {
        return new Greeting();
    }

    /**
     * Create an instance of {@link Greeting.Recipients }
     * 
     */
    public Greeting.Recipients createGreetingRecipients() {
        return new Greeting.Recipients();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Greeting }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.big-oh.net/hello/jaxb", name = "greeting")
    public JAXBElement<Greeting> createGreeting(Greeting value) {
        return new JAXBElement<Greeting>(_Greeting_QNAME, Greeting.class, null, value);
    }

}
