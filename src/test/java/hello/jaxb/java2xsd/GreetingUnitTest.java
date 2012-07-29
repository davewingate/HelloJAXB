package hello.jaxb.java2xsd;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

public class GreetingUnitTest
{
	
	private static Logger logger = LoggerFactory.getLogger(GreetingUnitTest.class);
	
	private Greeting greeting;

	@Before
	public void setup()
	{
		greeting = new Greeting(new Salutation("Howdy"), "John", "Paul", "George", "Ringo");
	}

	@Test
	public void testMarshalAndUnMarshal() throws JAXBException, IOException, SAXException
	{
		final SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		final Schema schema = schemaFactory.newSchema(greeting.getClass().getClassLoader().getResource("hello-jaxb.xsd"));
		
		logger.info("Original greeting: " + greeting.toString());
		
		final JAXBContext jaxbCtx = JAXBContext.newInstance(Greeting.class);
		final Marshaller marshaller = jaxbCtx.createMarshaller();
		marshaller.setSchema(schema);
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		final File tempFile = File.createTempFile(this.getClass().getSimpleName(), "xml");
		marshaller.marshal(greeting, tempFile);
		
		logger.info("Marshalled greeting: " + FileUtils.readFileToString(tempFile));

		final Unmarshaller unmarshaller = jaxbCtx.createUnmarshaller();
		unmarshaller.setSchema(schema);
		final Greeting unmarshalledGreeting = (Greeting) unmarshaller.unmarshal(tempFile);
		
		logger.info("Unmarshalled greeting: " + unmarshalledGreeting.toString());

		assertEquals(greeting.toString(), unmarshalledGreeting.toString());
		assertEquals(greeting, unmarshalledGreeting);
	}

}
