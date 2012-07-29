package hello.jaxb.xsd2java;

import static org.junit.Assert.assertEquals;
import hello.jaxb.xsd2java.Greeting.Recipients;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

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

public class GreetingUnitTest
{

	private static Logger logger = LoggerFactory.getLogger(GreetingUnitTest.class);

	private ObjectFactory greetingFactory = new ObjectFactory();

	private Greeting greeting;

	@Before
	public void setup()
	{
		greeting = greetingFactory.createGreeting();
		greeting.setId(1);
		greeting.setSalutation("Howdy");

		final Recipients recipients = greetingFactory.createGreetingRecipients();
		recipients.getRecipient().addAll(Arrays.asList("John", "Paul", "George", "Ringo"));
		greeting.setRecipients(recipients);
	}

	@Test
	public void testMarshalAndUnMarshal() throws JAXBException, IOException, SAXException
	{
		final SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		final Schema schema = schemaFactory.newSchema(greeting.getClass().getClassLoader().getResource("hello-jaxb.xsd"));

		logger.info("Original greeting: " + greeting.getId() + " = " + greeting.getSalutation() + ", " + StringUtils.join(greeting.getRecipients().getRecipient(), " & "));

		final JAXBContext jaxbCtx = JAXBContext.newInstance(Greeting.class);
		final Marshaller marshaller = jaxbCtx.createMarshaller();
		marshaller.setSchema(schema);
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		final File tempFile = File.createTempFile(this.getClass().getSimpleName(), "xml");
		/*
		 * Note use of wrapper method here ... needed because
		 * hello.jaxb.xsd2java.Greeting is not annotated as an xml root element.
		 */
		marshaller.marshal(greetingFactory.createGreeting(greeting), tempFile);

		logger.info("Marshalled greeting: " + FileUtils.readFileToString(tempFile));

		final Unmarshaller unmarshaller = jaxbCtx.createUnmarshaller();
		unmarshaller.setSchema(schema);
		/*
		 * Note special handling for unmarshall of hello.jaxb.xsd2java.Greeting
		 * because it is not annotated as an xml root element
		 */
		final Greeting unmarshalledGreeting = unmarshaller.unmarshal(new StreamSource(tempFile), Greeting.class).getValue();

		logger.info("Unmarshalled greeting: " + unmarshalledGreeting.getId() + " = " + unmarshalledGreeting.getSalutation() + ", " + StringUtils.join(unmarshalledGreeting.getRecipients().getRecipient(), " & "));

		assertEquals(greeting.getId(), unmarshalledGreeting.getId());
		assertEquals(greeting.getSalutation(), unmarshalledGreeting.getSalutation());
		assertEquals(greeting.getRecipients().getRecipient(), unmarshalledGreeting.getRecipients().getRecipient());
	}

}
