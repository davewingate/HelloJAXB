package hello.jaxb;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

public class GreetingIntegrationTest
{

	private static Logger logger = LoggerFactory.getLogger(GreetingIntegrationTest.class);

	private hello.jaxb.java2xsd.Greeting greeting;

	@Before
	public void setup()
	{
		greeting = new hello.jaxb.java2xsd.Greeting(new hello.jaxb.java2xsd.Salutation("Howdy"), "John", "Paul", "George", "Ringo");
	}

	@Test
	public void testMarshalAndUnMarshal() throws JAXBException, IOException, SAXException
	{
		final SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		final Schema schema = schemaFactory.newSchema(greeting.getClass().getClassLoader().getResource("hello-jaxb.xsd"));

		logger.info("Original greeting: " + greeting.toString());

		final Marshaller marshaller = JAXBContext.newInstance(hello.jaxb.java2xsd.Greeting.class).createMarshaller();
		marshaller.setSchema(schema);
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		final File tempFile = File.createTempFile(this.getClass().getSimpleName(), "xml");
		marshaller.marshal(greeting, tempFile);

		logger.info("Marshalled greeting: " + FileUtils.readFileToString(tempFile));

		final Unmarshaller unmarshaller = JAXBContext.newInstance(hello.jaxb.xsd2java.Greeting.class).createUnmarshaller();
		unmarshaller.setSchema(schema);
		/*
		 * Note special handling for unmarshall of hello.jaxb.xsd2java.Greeting
		 * because it is not annotated as an xml root element
		 */
		final hello.jaxb.xsd2java.Greeting unmarshalledGreeting = unmarshaller.unmarshal(new StreamSource(tempFile), hello.jaxb.xsd2java.Greeting.class).getValue();

		logger.info("Unmarshalled greeting: " + unmarshalledGreeting.getId() + " = " + unmarshalledGreeting.getSalutation() + ", " + StringUtils.join(unmarshalledGreeting.getRecipients().getRecipient(), " & "));

		assertEquals(greeting.getId(), unmarshalledGreeting.getId());
		assertEquals(greeting.getSalutation().getSalutation(), unmarshalledGreeting.getSalutation());
		assertEquals(greeting.getRecipients(), unmarshalledGreeting.getRecipients().getRecipient());
	}

}
